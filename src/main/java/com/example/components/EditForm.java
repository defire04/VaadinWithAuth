package com.example.components;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;


public class EditForm extends FormLayout {
    private UserForm userForm;
    private Button save;
    private Button cancel;
    private Button delete;



    public EditForm() {
        addClassName("edit-form");
        userForm = new UserForm();
        save = new Button("Save", VaadinIcon.CHECK.create());
        cancel = new Button("Cancel");
        delete = new Button("Delete", VaadinIcon.TRASH.create());

        setStyles();

        add(userForm, save, cancel, delete);
    }

    private void setStyles(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
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

}
