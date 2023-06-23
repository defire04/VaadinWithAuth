package com.example.views;

import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

@Route(value = "login")
@Tag("login-view")
@PageTitle("Login")
public class LoginView extends Div {

    public LoginView(UserService authService) {

        UI.getCurrent().getElement().getThemeList().add("dark");


        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(User.class) != null) {
            UI.getCurrent().getPage().setLocation("login");
            session.close();
        }


        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        LoginForm loginForm = new LoginForm();


        loginForm.addLoginListener(event -> {
            try {
                authService.login(event.getUsername(), event.getPassword());
                UI.getCurrent().navigate("home");
            } catch (RuntimeException e) {
                loginForm.setError(true);
//                Notification.show(e.getMessage());
            }
        });

        Button register = new Button("Register", event -> {
            UI.getCurrent().navigate("register");
        });
        Button logout = new Button("Log out", event -> {
            UI.getCurrent().navigate("logout");
        });


        horizontalLayout.add(register, logout);
        verticalLayout.add(loginForm, horizontalLayout);

        loginForm.getStyle()
                .setMargin("0 auto");
        horizontalLayout.getStyle()
                .setMargin("0 auto");

        add(verticalLayout);
    }
}