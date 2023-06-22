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


    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {
    }

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Login failed"));

        if (user.getPassword().equals(password)) {
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRoles());
        }

    }

    public void register(User entity) {
        if (userRepository.findByUsername(entity.getUsername()).isEmpty()) {
            userRepository.save(entity);
        } else{
            throw new RuntimeException("Register failed");
        }
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void save(User user) {
        if(findByUsername(user.getUsername()).isEmpty()){
            userRepository.save(new User(user.getUsername(), user.getPassword(), user.getName(), user.getEmail()));
        } else{
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
            if (role.equals(Role.ADMIN)) {
                routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
            } else if (role.equals(Role.USER)) {
                routes.add(new AuthorizedRoute("user", "UserView", UserView.class));
            }
        });
        routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));

        return routes;
    }


    public void addAdminRights(User editedUser){
        Optional<User> user = findByUsername(editedUser.getUsername());
        if(user.isPresent()){
            user.get().addRole(Role.ADMIN);
            userRepository.save(user.get());
        }

    }
}
