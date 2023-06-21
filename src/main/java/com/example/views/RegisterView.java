package com.example.views;

import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "register")
@Tag("register-view")
@PageTitle("Register")
public class RegisterView extends Div {
    private UserService authService;

    public RegisterView(UserService authService) {
        this.authService = authService;
        setId("register-view");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        TextField name = new TextField("Name");
        EmailField email = new EmailField("Email");

        FormLayout formLayout = new FormLayout();
        formLayout.add(username, name, email, password);

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
                );
        add(formLayout);

        Button registerButton = new Button("Register", event -> {
            try {
                authService.register(new User(username.getValue(), password.getValue(), name.getValue(), email.getValue()));
                UI.getCurrent().navigate("home");
            } catch (Exception e) {
                Notification.show("Wrong credentials.");
            }
        });
        RouterLink registerLink = new RouterLink("Login", LoginView.class);

        formLayout.add(registerButton);
        formLayout.add(registerLink);

    }
}
