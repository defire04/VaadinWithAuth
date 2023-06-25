package com.example.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;

public class ResetPasswordForm extends FormLayout {
    private PasswordField password;
    private PasswordField passwordConfirm;
    private Button save;
    private Button cancel;
    private Span errorMessageField;
    public ResetPasswordForm() {
        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm password");
        save = new Button("Save", VaadinIcon.CHECK.create());
        cancel = new Button("Cancel");
        errorMessageField = new Span();
        setStyles();
        add(password, passwordConfirm, save, cancel,errorMessageField);
    }

    private void setStyles() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        getStyle().setMargin("0 auto");

        setMaxWidth("500px");

        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("500px", 2, ResponsiveStep.LabelsPosition.TOP));
    }

    public PasswordField getPassword() {
        return password;
    }
    public void setPassword(PasswordField password) {
        this.password = password;
    }
    public PasswordField getPasswordConfirm() {
        return passwordConfirm;
    }
    public void setPasswordConfirm(PasswordField passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    public Button getSave() {
        return save;
    }
    public void setSave(Button save) {
        this.save = save;
    }
    public Button getCancel() {
        return cancel;
    }
    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }
    public Span getErrorMessageField() {
        return errorMessageField;
    }
    public void setErrorMessageField(Span errorMessageField) {
        this.errorMessageField = errorMessageField;
    }
}
