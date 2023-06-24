package com.example.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;



public class AdminEditForm extends EditForm {
    private Button edit;
    private Button addAdminButton;
    private Button refreshPassword;
    private Button block;
    private Span errorMessageField;


    public AdminEditForm() {
        addClassName("admin-edit-form");
        edit = new Button("Edit");

        addAdminButton = new Button("Make admin");
        refreshPassword = new Button("Reset password");
        block = new Button("Block");
        errorMessageField = new Span();

        setStyles();
        add(getUserForm().getUsernameTextField(),  getUserForm().getNameTextField(), getUserForm().getEmailTextField(),
                edit, addAdminButton, refreshPassword, block, getSave(), getDelete(), getCancel());
    }

    private void setStyles() {
        getStyle().setMargin("0 auto");
        refreshPassword.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_ERROR);
        addAdminButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);


        setMaxWidth("470px");
        setResponsiveSteps(
                new ResponsiveStep("0", 3),
                new ResponsiveStep("470px", 3, ResponsiveStep.LabelsPosition.TOP));

        setColspan(getUserForm().getUsernameTextField(), 3);
        setColspan(getUserForm().getNameTextField(), 3);
        setColspan(getUserForm().getEmailTextField(), 3);
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
}
