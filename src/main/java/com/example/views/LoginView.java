package com.example.views;

import com.example.data.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

@Route(value = "login")
@Tag("login-view")
@PageTitle("Login")
public class LoginView extends Div {

    public LoginView(UserService authService) {
        setId("login-view");
        TextField  username = new TextField("Username");
        PasswordField  password = new PasswordField("Password");

        FormLayout formLayout = new FormLayout();
        formLayout.add(username, password);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2));
        add(formLayout);

        Button loginButton = new Button("Login", event -> {
            try {
                authService.login(username.getValue(), password.getValue());
                UI.getCurrent().navigate("home");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Notification.show("Wrong credentials.");
            }
        });

        RouterLink registerLink = new RouterLink("Register", RegisterView.class);

        formLayout.add(loginButton);
        formLayout.add(registerLink);

    }
}