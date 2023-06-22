package com.example.components;


import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout implements KeyNotifier {

    private final UserService userService;
    private User user;

    private TextField username = new TextField("Username");
    private TextField name = new TextField("Name");
    private EmailField email = new EmailField("Email");
    private Button addAdminButton = new Button("Make admin");
    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private Button edit = new Button("Edit");
    private Button refreshPassword = new Button("Refresh password");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<User> binder = new Binder<>(User.class);
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    public UserEditor(UserService userService) {
        this.userService = userService;

        refreshPassword.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_SMALL);
        addAdminButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);

        edit.setVisible(false);

        add(username, name, email, refreshPassword, addAdminButton, actions);
        binder.bindInstanceFields(this);

        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> saveOrUpdateUserViaAdmin());

        refreshPassword.addClickListener(e-> refreshPassword(user));
        addAdminButton.addClickListener(e -> makeAdmin(user));
        save.addClickListener(e -> saveOrUpdateUserViaAdmin());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        edit.addClickListener(e -> editUser(user));

        setVisible(false);
    }

    private void refreshPassword(User user){
        userService.refreshPassword(user);
        changeHandler.onChange();
    }

    private void makeAdmin(User user) {
        userService.addAdminRights(user);
        changeHandler.onChange();
    }

    public void delete() {
        userService.delete(user);
        changeHandler.onChange();
    }

    public void saveOrUpdateUserViaAdmin() {
        userService.saveOrUpdateUserViaAdmin(user);
        changeHandler.onChange();
    }

    public void cancel() {
        changeHandler.onChange();
    }

    public void editUser(User newUser) {
        if (newUser == null) {
            setVisible(false);
            return;
        }

        this.user = userService.findByUsername(newUser.getUsername()).orElse(newUser);

        binder.setBean(user);

        setVisible(true);
        name.focus();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}

