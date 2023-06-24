package com.example.data.service;


import com.example.data.entity.Role;
import com.example.data.entity.User;
import com.example.data.repository.UserRepository;
import com.example.exeption.InvalidPasswordException;
import com.example.exeption.UserAlreadyExists;
import com.example.exeption.UserNotFoundException;
import com.example.views.AdminView;
import com.example.views.LogoutView;
import com.example.views.UserView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

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

    public void login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (passwordService.matches(password, user.getPassword())) {
            VaadinSession.getCurrent().setAttribute(User.class, user);
            usersSession.put(username, VaadinSession.getCurrent());
            createRoutes(user.getRole());
        } else if (user.getTempPassword() != null && passwordService.matches(password, user.getTempPassword())) {
            VaadinSession.getCurrent().setAttribute(User.class, user);
            usersSession.put(username, VaadinSession.getCurrent());
            createRoutes(Role.MUST_CHANGE_PASSWORD);
            user.setRole(Role.MUST_CHANGE_PASSWORD);
            userRepository.save(user);
        } else {
            throw new InvalidPasswordException("Password does not match!");
        }
    }

    public void register(User entity) {
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
            emailSenderService.sendEmail(user.getEmail(), newPassword);
            userRepository.save(new User(user.getUsername(), passwordService.hashPassword(newPassword),
                    user.getName(), user.getEmail()));
        } else {
            userRepository.save(user);
        }
    }

    private void createRoutes(Role role) {
        getAuthorizedRoutes(role)
                .forEach(route -> RouteConfiguration.forSessionScope().setRoute(route.route, route.view));
    }

    public Set<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        Set<AuthorizedRoute> routes = new HashSet<>();

        switch (role) {
            case ADMIN:
                routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
            case USER:
                routes.add(new AuthorizedRoute("user", "UserView", UserView.class));
            case MUST_CHANGE_PASSWORD:

            case BLOCKED:
                routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        }

        return routes;
    }

    public void updateUserRole(User updatedUser, Role newRole) {
        Optional<User> user = findByUsername(updatedUser.getUsername());
        if (user.isPresent()) {
            user.get().setRole(newRole);
            userRepository.save(user.get());
            logOutUserSession(updatedUser.getUsername());
        }
    }

    public void resetPassword(User editedUser) {
        Optional<User> user = findByUsername(editedUser.getUsername());
        if (user.isPresent()) {
            String newPassword = passwordService.generateRandomPassword();
            user.get().setPassword(passwordService.hashPassword(newPassword));
            emailSenderService.sendEmail(user.get().getEmail(), newPassword);
            userRepository.save(user.get());
            logOutUserSession(editedUser.getUsername());
        }
    }

    private void logOutUserSession(String username) {
        VaadinSession session = usersSession.get(username);
        session.getSession().invalidate();
    }

    public void sendTempPassword(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found!"));
        String tempPassword = passwordService.generateRandomPassword();
        user.setTempPassword(passwordService.hashPassword(tempPassword));

        emailSenderService.sendEmail(user.getEmail(), tempPassword);

        userRepository.save(user);

    }

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {
    }
}
