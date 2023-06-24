package com.example.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;


public class AdminEditForm extends EditForm {
    private Button edit;
    private Button addAdminButton;
    private Button refreshPassword;
    private Button block;
    private final VerticalLayout verticalLayout;

    private final HorizontalLayout adminButtons;
    private final HorizontalLayout editButtons;


    public AdminEditForm() {
        addClassName("admin-edit-form");
        edit = new Button("Edit");

        addAdminButton = new Button("Make admin");
        refreshPassword = new Button("Reset password");
        block = new Button("Block");


        adminButtons = new HorizontalLayout(addAdminButton, refreshPassword, block);
        adminButtons.addClassName("admin-buttons");
        editButtons = new HorizontalLayout(getSave(), getDelete(), getCancel());
        editButtons.addClassName("edit-buttons");

        verticalLayout = new VerticalLayout(getUserForm(),
                adminButtons,
                editButtons);


        setStyles();
        add(verticalLayout);
    }

    private void setStyles() {
        refreshPassword.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_ERROR);
        addAdminButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);

        getStyle().setDisplay(Style.Display.FLEX)
                .set("justify-content", "center");

        getUserForm().getUsernameTextField().getStyle().setWidth("100%");
        getUserForm().getEmailTextField().getStyle().setWidth("100%");
        getUserForm().getNameTextField().getStyle().setWidth("100%");

        adminButtons.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setWidth("100%")
                .set("align-items", "baseline")
                .set("justify-content", "space-around")
        ;

        addAdminButton.getStyle().set("flex", "1 0 30%");
        refreshPassword.getStyle().set("flex", "1 0 30%");
        block.getStyle().set("flex", "1 0 30%");

        editButtons.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setWidth("100%")
                .set("align-items", "baseline")
                .set("justify-content", "space-around")
        ;

        getSave().getStyle().set("flex", "1 0 30%");
        getDelete().getStyle().set("flex", "1 0 30%");
        getCancel().getStyle().set("flex", "1 0 30%");





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
}
