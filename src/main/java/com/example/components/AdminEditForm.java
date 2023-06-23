package com.example.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AdminEditForm extends EditForm {
    private Button edit;
    private Button addAdminButton;
    private Button refreshPassword;

    public AdminEditForm() {
        addClassName("admin-edit-form");
        edit = new Button("Edit");


        addAdminButton =  new Button("Make admin");
        refreshPassword = new Button("Reset password");

        setStyles();


        getUserForm().getVerticalLayout().add(addAdminButton,refreshPassword);
        add(getUserForm());
    }

    private void setStyles() {
        refreshPassword.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_SMALL);
        addAdminButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);


        getUserForm().getUsernameTextField().getStyle().setWidth("100%");
        getUserForm().getEmailTextField().getStyle().setWidth("100%");
        getUserForm().getNameTextField().getStyle().setWidth("100%");
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

}
