package com.example.data.service;


import com.example.data.entity.Role;
import com.example.data.entity.User;
import com.example.data.repository.UserRepository;
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
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Login failed"));

        if (passwordService.matches(password, user.getPassword())) {
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRoles());
        }
    }

    public void register(User entity) {
        if (userRepository.findByUsername(entity.getUsername()).isEmpty()) {
            entity.setPassword(passwordService.hashPassword(entity.getPassword()));
            userRepository.save(entity);
        }
    }

    public void delete(User user) {
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

    private void createRoutes(Set<Role> roles) {
        getAuthorizedRoutes(roles)
                .forEach(route -> RouteConfiguration.forSessionScope().setRoute(route.route, route.view));

    }

    public Set<AuthorizedRoute> getAuthorizedRoutes(Set<Role> roles) {
        Set<AuthorizedRoute> routes = new HashSet<>();

        roles.forEach(role -> {
            switch (role) {
                case ADMIN:
                    routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
                case USER:
                    routes.add(new AuthorizedRoute("user", "UserView", UserView.class));
                case BLOCKED:
                    routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
            }
        });
        return routes;
    }


    public void addAdminRights(User editedUser) {
        Optional<User> user = findByUsername(editedUser.getUsername());
        if (user.isPresent()) {
            user.get().addRole(Role.ADMIN);
            userRepository.save(user.get());
        }
    }

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {
    }
}
