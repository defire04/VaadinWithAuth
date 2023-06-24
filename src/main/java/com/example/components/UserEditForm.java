package com.example.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class UserEditForm extends FormLayout {

    private H3 username;
    private TextField name;
    private EmailField email;
    private Span errorMessageField;
    private Button changePasswordButton;

    private Button save;
    private Button cancel;


    public UserEditForm() {
        addClassName("admin-edit-form");

        username = new H3();
        name = new TextField("Name");
        email = new EmailField("Email");
        errorMessageField = new Span();
        changePasswordButton = new Button("Change password");
        save = new Button("Save", VaadinIcon.CHECK.create());
        cancel = new Button("Cancel");


        setStyles();

        add(username, name, email, errorMessageField,changePasswordButton, save, cancel);
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

        setColspan(username, 2);
        setColspan(name, 2);
        setColspan(email, 2);
        setColspan(changePasswordButton, 2);
        setColspan(errorMessageField, 2);
    }


    public H3 getUsername() {
        return username;
    }

    public void setUsername(H3 username) {
        this.username = username;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public EmailField getEmail() {
        return email;
    }

    public void setEmail(EmailField email) {
        this.email = email;
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    public void setErrorMessageField(Span errorMessageField) {
        this.errorMessageField = errorMessageField;
    }

    public Button getChangePasswordButton() {
        return changePasswordButton;
    }

    public void setChangePasswordButton(Button changePasswordButton) {
        this.changePasswordButton = changePasswordButton;
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


}
