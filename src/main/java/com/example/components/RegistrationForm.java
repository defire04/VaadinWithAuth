package com.example.components;

import com.example.views.LoginView;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.RouterLink;

import java.util.stream.Stream;

public class RegistrationForm extends FormLayout {
    private H3 title;
    private UserForm userForm;

    private PasswordField password;
    private Button register;
    private Span errorMessageField;

    private RouterLink loginLink;

    public RegistrationForm() {

        title = new H3("Signup form");
        userForm = new UserForm();
        password =  new PasswordField("Password");
        loginLink = new RouterLink("Login", LoginView.class);
        errorMessageField = new Span();
        register = new Button("Register");

        add(title, userForm, password, register, loginLink, errorMessageField);


//        setRequiredIndicatorVisible(userForm.getUsernameTextField(), userForm.getNameTextField(), userForm.getEmailTextField(), password);


    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    public H3 getTitle() {
        return title;
    }

    public void setTitle(H3 title) {
        this.title = title;
    }

    public UserForm getUserForm() {
        return userForm;
    }

    public void setUserForm(UserForm userForm) {
        this.userForm = userForm;
    }

    public String getPassword() {
        return password.getValue();
    }
    public PasswordField getPasswordPasswordField() {
        return password;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    public Button getRegister() {
        return register;
    }

    public void setRegister(Button register) {
        this.register = register;
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    public void setErrorMessageField(Span errorMessageField) {
        this.errorMessageField = errorMessageField;
    }
}
