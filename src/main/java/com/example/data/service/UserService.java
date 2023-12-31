package com.example.data.service;


import com.example.data.entity.Role;
import com.example.data.entity.User;
import com.example.data.exeption.InvalidPasswordException;
import com.example.data.exeption.UserAlreadyExists;
import com.example.data.exeption.UserNotFoundException;
import com.example.data.repository.UserRepository;
import com.example.views.admin.AdminView;
import com.example.views.logout.LogoutView;
import com.example.views.reset_password.ResetPasswordView;
import com.example.views.user.EditUserView;
import com.example.views.user.UserView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final EmailSenderService emailSenderService;

    private final HashMap<String, VaadinSession> usersSession = new HashMap<>();

    public UserService(UserRepository userRepository, PasswordService passwordService, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.emailSenderService = emailSenderService;

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByUsernameOrElseThrowUserNotFoundException(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public void login(String username, String password) {
        User user = findByUsernameOrElseThrowUserNotFoundException(username);

        boolean isPasswordMatch = passwordService.matches(password, user.getPassword());
        boolean isTempPasswordMatch = user.getTempPassword() != null && passwordService.matches(password, user.getTempPassword());

        if (isPasswordMatch || isTempPasswordMatch) {
            if (isTempPasswordMatch) {
                user.setPassword(null);
                user.setMustChangePassword(true);
                userRepository.save(user);
            }

            VaadinSession.getCurrent().setAttribute(User.class, user);
            usersSession.put(username, VaadinSession.getCurrent());
            createRoutes(user);
        } else {
            throw new InvalidPasswordException("Password does not match!");
        }
    }

    public void register(User entity) {
        if (entity.getPassword().isEmpty()) {
            throw new InvalidPasswordException("User already exists!");
        }
        if (userRepository.findByUsername(entity.getUsername()).isEmpty()) {
            entity.setPassword(passwordService.hashPassword(entity.getPassword()));
            userRepository.save(entity);
        } else {
            throw new UserAlreadyExists("User already exists!");
        }
    }

    public void delete(User user) {
        logOutUserSession(user.getUsername());
        usersSession.remove(user.getUsername());
        userRepository.delete(user);
    }

    public void saveOrUpdateUserViaAdmin(User user) {
        if (findByUsername(user.getUsername()).isEmpty()) {
            String newPassword = passwordService.generateRandomPassword();

            user.setMustChangePassword(true);
            user.setTempPassword(passwordService.hashPassword(newPassword));

            userRepository.save(user);
            emailSenderService.sendEmail(user.getEmail(), newPassword);
        } else {
            userRepository.save(user);
        }
    }

    private void createRoutes(User user) {
        if (user.isMustChangePassword()) {
            RouteConfiguration.forSessionScope().setRoute("reset-password", ResetPasswordView.class);
            return;
        }

        getAuthorizedRoutes(user.getRole()).stream()
                .filter(route -> !RouteConfiguration.forSessionScope().isRouteRegistered(route.view))
                .forEach(route -> RouteConfiguration.forSessionScope().setRoute(route.route, route.view));
    }

    public Set<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        Set<AuthorizedRoute> routes = new HashSet<>();

        switch (role) {
            case ADMIN:
                routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
            case USER:
                routes.add(new AuthorizedRoute("user", "UserView", UserView.class));
                routes.add(new AuthorizedRoute("edit", "EditUserView", EditUserView.class));
                routes.add(new AuthorizedRoute("reset-password", "ResetPasswordView", ResetPasswordView.class));

            case BLOCKED:
                routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        }

        return routes;
    }

    public void updateUserRole(User updatedUser, Role newRole) {
        User user = findByUsernameOrElseThrowUserNotFoundException(updatedUser.getUsername());

        user.setRole(newRole);
        userRepository.save(user);
        logOutUserSession(updatedUser.getUsername());

    }

    public void resetPassword(User editedUser) {
        User user = findByUsernameOrElseThrowUserNotFoundException(editedUser.getUsername());

        if(user.isMustChangePassword()){
            return;
        }
        user.setPassword(null);
        user.setMustChangePassword(true);

        String newPassword = passwordService.generateRandomPassword();
        user.setTempPassword(passwordService.hashPassword(newPassword));

        emailSenderService.sendEmail(user.getEmail(), newPassword);
        userRepository.save(user);

        logOutUserSession(editedUser.getUsername());
    }

    private void logOutUserSession(String username) {
        VaadinSession session = usersSession.get(username);
        if (session != null && session.getSession() != null) {
            session.getSession().invalidate();
        }
    }

    public void sendTempPassword(String username) {
        User user = findByUsernameOrElseThrowUserNotFoundException(username);

        String tempPassword = passwordService.generateRandomPassword();
        user.setTempPassword(passwordService.hashPassword(tempPassword));

        emailSenderService.sendEmail(user.getEmail(), tempPassword);
        userRepository.save(user);
    }

    public String generateConfirmPasswordAndSendByEmail(User user) {
        String confirmPassword;
        if (user != null && user.getEmail() != null) {
            confirmPassword = passwordService.generateRandomConfirmationPassword();
            emailSenderService.sendEmail(user.getEmail(), confirmPassword);
            return confirmPassword;
        }

        return null;
    }

    public void update(String username, User updatedUser) {
        User user = findByUsernameOrElseThrowUserNotFoundException(username);

        Field[] fields = User.class.getDeclaredFields();


        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object updatedValue = field.get(updatedUser);
                if (updatedValue != null && !String.valueOf(updatedValue).isEmpty()) {
                    field.set(user, updatedValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Handling an exception when accessing a field");
            }
        }


        userRepository.save(user);
    }

    public void updatePassword(String username, User updatedUser) {
        User user = findByUsernameOrElseThrowUserNotFoundException(username);

        if (user.getTempPassword() != null && !user.getTempPassword().isEmpty() && user.isMustChangePassword()) {
            user.setTempPassword(null);
            user.setMustChangePassword(false);
        }

        user.setPassword(passwordService.hashPassword(updatedUser.getPassword()));
        createRoutes(user);

        userRepository.save(user);
    }

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {
    }
}
