package com.example.views.admin;


import com.example.components.AdminEditForm;
import com.example.data.entity.Role;
import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.vaadin.flow.component.Key;
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

    public AdminFormEditor setAdminUserEditForm(AdminEditForm adminEditForm){
        this.adminEditForm = adminEditForm;
//        adminEditForm.getStyle().setMargin("0 auto");

        add(adminEditForm);

        binder.bindInstanceFields(adminEditForm);
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

        return this;
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

    private void delete() {
        userService.delete(user);
        changeHandler.onChange();
    }

    private void saveOrUpdateUserViaAdmin() {
        userService.saveOrUpdateUserViaAdmin(user);
        changeHandler.onChange();
    }

    private void cancel() {
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


    public void addBindingAndValidation() {
        binder.bindInstanceFields(adminEditForm);

        binder.setStatusLabel(adminEditForm.getErrorMessageField());

        adminEditForm.getSave().addClickListener(event -> {
            try {
                User userBean = new User();
                binder.writeBean(userBean);

                showSuccess();
            } catch (ValidationException exception) {
                Notification.show(exception.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }
    private void showSuccess() {
        Notification notification =
                Notification.show("Data saved");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

    }
}

