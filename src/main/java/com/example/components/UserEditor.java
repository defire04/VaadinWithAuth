package com.example.components;


import com.example.data.entity.Role;
import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout implements KeyNotifier {

    private final UserService userService;
    private User user;
    private final Binder<User> binder = new Binder<>(User.class);
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    public UserEditor(UserService userService) {
        this.userService = userService;

        AdminEditForm adminEditForm = new AdminEditForm();
        adminEditForm.getStyle().setMargin("0 auto");

        add(adminEditForm);

        binder.bindInstanceFields(adminEditForm.getUserForm());
        setSpacing(true);

        addKeyPressListener(Key.ENTER, e -> saveOrUpdateUserViaAdmin());
        adminEditForm.getRefreshPassword().addClickListener(e-> refreshPassword(user));
        adminEditForm.getAddAdminButton().addClickListener(e -> makeAdmin(user));
        adminEditForm.getSave().addClickListener(e -> saveOrUpdateUserViaAdmin());
        adminEditForm.getDelete().addClickListener(e -> delete());
        adminEditForm.getCancel().addClickListener(e -> cancel());
        adminEditForm.getEdit().addClickListener(e -> editUser(user));
        adminEditForm.getBlock().addClickListener(e -> blockUser(user));
        setVisible(false);

    }
    private void blockUser(User user){
        userService.updateUserRole(user, Role.BLOCKED);
        changeHandler.onChange();
    }

    private void refreshPassword(User user){
        userService.resetPassword(user);
        changeHandler.onChange();
    }

    private void makeAdmin(User user) {
        userService.updateUserRole(user, Role.ADMIN);
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
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}

