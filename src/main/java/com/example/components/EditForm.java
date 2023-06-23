package com.example.components;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class EditForm extends FormLayout {
    private UserForm userForm;
    private Button save;
    private Button cancel;
    private Button delete;
    private HorizontalLayout  horizonActions;


    public EditForm() {
        addClassName("edit-form");
        userForm = new UserForm();
        save = new Button("Save", VaadinIcon.CHECK.create());
        cancel = new Button("Cancel");
        delete = new Button("Delete", VaadinIcon.TRASH.create());


        horizonActions = createButtonsHorizontalLayout();
        userForm.getVerticalLayout().add(horizonActions);
        add(userForm, userForm.getVerticalLayout());
    }

    private HorizontalLayout createButtonsHorizontalLayout() {
        addClassName("default-edit-button");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, cancel, delete);
    }

    public UserForm getUserForm() {
        return userForm;
    }

    public void setUserForm(UserForm userForm) {
        this.userForm = userForm;
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


    public HorizontalLayout getActions() {
        return horizonActions;
    }

    public void setActions(HorizontalLayout actions) {
        this.horizonActions = actions;
    }
}
