package com.example.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;


public class AdminEditForm extends FormLayout {

    private TextField username;
    private TextField name;
    private EmailField email;
    private Button edit;
    private Button save;
    private Button cancel;
    private Button delete;
    private Button addAdminButton;
    private Button refreshPassword;
    private Button block;
    private Span errorMessageField;


    public AdminEditForm() {
        addClassName("admin-edit-form");
        edit = new Button("Edit");

        username = new TextField("Username");
        name = new TextField("Name");
        email = new EmailField("Email");

        save = new Button("Save", VaadinIcon.CHECK.create());
        cancel = new Button("Cancel");
        delete = new Button("Delete", VaadinIcon.TRASH.create());
        addAdminButton = new Button("Make admin");
        refreshPassword = new Button("Reset password");
        block = new Button("Block");
        errorMessageField = new Span();

        setStyles();
        add(username, name, email,
                edit, addAdminButton, refreshPassword, block, save, delete, cancel);
    }

    private void setStyles() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        getStyle().setMargin("0 auto");
        getStyle().setMargin("0 auto");
        refreshPassword.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_ERROR);
        addAdminButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);


        setMaxWidth("470px");
        setResponsiveSteps(
                new ResponsiveStep("0", 3),
                new ResponsiveStep("470px", 3, ResponsiveStep.LabelsPosition.TOP));

        setColspan(username, 3);
        setColspan(name, 3);
        setColspan(email, 3);
        setColspan(errorMessageField, 2);

        edit.setVisible(false);
    }


    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public Button getAddAdminButton() {
        return addAdminButton;
    }

    public void setAddAdminButton(Button addAdminButton) {
        this.addAdminButton = addAdminButton;
    }

    public Button getRefreshPassword() {
        return refreshPassword;
    }

    public void setRefreshPassword(Button refreshPassword) {
        this.refreshPassword = refreshPassword;
    }

    public Button getBlock() {
        return block;
    }

    public void setBlock(Button block) {
        this.block = block;
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    public void setErrorMessageField(Span errorMessageField) {
        this.errorMessageField = errorMessageField;
    }

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
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

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
