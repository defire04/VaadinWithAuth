package com.example.views.admin;


import com.example.components.AdminEditForm;
import com.example.data.entity.Role;
import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class AdminFormEditor extends VerticalLayout implements KeyNotifier {
    private final UserService userService;
    private User user;
    private final BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
    private ChangeHandler changeHandler;

    private AdminEditForm adminEditForm;

    public interface ChangeHandler {
        void onChange();
    }

    public AdminFormEditor(UserService userService) {
        this.userService = userService;
    }

    public AdminFormEditor setAdminUserEditForm(AdminEditForm adminEditForm) {
        this.adminEditForm = adminEditForm;

        add(adminEditForm);

        binder.bindInstanceFields(adminEditForm);
        setSpacing(true);

        adminEditForm.getRefreshPassword().addClickListener(e -> resetPassword(user));
        adminEditForm.getAddAdminButton().addClickListener(e -> changeRole(user, Role.ADMIN));
        adminEditForm.getSave().addClickListener(e -> saveOrUpdateUserViaAdmin(user));
        adminEditForm.getDelete().addClickListener(e -> delete(user));
        adminEditForm.getCancel().addClickListener(e -> cancel());
        adminEditForm.getEdit().addClickListener(e -> editUser(user));
        adminEditForm.getBlock().addClickListener(e -> changeRole(user, Role.BLOCKED));
        setVisible(false);

        return this;
    }

    private void changeRole(User user, Role role) {
        handleExceptionWithChange(user, () -> userService.updateUserRole(user, role));
    }

    private void resetPassword(User user) {
        handleExceptionWithChange(user, () -> userService.resetPassword(user));
    }

    private void delete(User user) {
        handleExceptionWithChange(user, () -> userService.delete(user));
    }

    private void saveOrUpdateUserViaAdmin(User user) {
        handleExceptionWithChange(user, () -> userService.saveOrUpdateUserViaAdmin(user));
    }

    private void cancel() {
        changeHandler.onChange();
    }

    private void handleExceptionWithChange(User user, Runnable action) {
        try {
            binder.writeBean(user);
            action.run();
            changeHandler.onChange();
        } catch (ValidationException getMessage) {
            Notification.show("All fields must be filled").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
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


    public AdminFormEditor addBindingAndValidation() {
        binder.bindInstanceFields(adminEditForm);

        binder.setStatusLabel(adminEditForm.getErrorMessageField());

        adminEditForm.getSave().addClickListener(event -> {
            handleExceptionWithChange(user, this::showSuccess);
        });
        return this;
    }

    private void showSuccess() {
        Notification notification =
                Notification.show("Data saved");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}

