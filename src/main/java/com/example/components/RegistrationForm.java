package com.example.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.PasswordField;

public class RegistrationForm extends FormLayout {
    private H3 title;
    private UserForm userForm;
    private PasswordField password;
    private PasswordField passwordConfirm;
    private Button register;
    private Span errorMessageField;

    private Button loginLink;


    public RegistrationForm() {
        addClassName("register-form");

        title = new H3("Register form");
        userForm = new UserForm();
        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm password");

        loginLink = new Button("Login", event->{
            UI.getCurrent().getPage().setLocation("login");
        });

        errorMessageField = new Span();
        register = new Button("Register");


        setStyles();
        add(title, userForm.getUsernameTextField(), userForm.getNameTextField(), userForm.getEmailTextField(), password,
                passwordConfirm, errorMessageField,
                register, loginLink);
    }

    private void setStyles() {


        getStyle().setMargin("0 auto");

        setMaxWidth("500px");

        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        setColspan(title, 2);
        setColspan(userForm.getEmailTextField(), 2);
        setColspan(errorMessageField, 2);
        setColspan(register, 2);
        setColspan(loginLink, 2);
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

    public Button getLoginLink() {
        return loginLink;
    }

    public void setLoginLink(Button loginLink) {
        this.loginLink = loginLink;
    }

    public PasswordField getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(PasswordField passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
