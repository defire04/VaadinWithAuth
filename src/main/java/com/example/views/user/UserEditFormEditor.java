package com.example.views.user;


import com.example.components.UserEditForm;
import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.example.views.reset_password.ResetPasswordView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;


public class UserEditFormEditor {
    private final UserEditForm userEditForm;
    private final UserService authService;
    public UserEditFormEditor(UserEditForm userEditForm, UserService authService) {
        this.userEditForm = userEditForm;
        this.authService = authService;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

        User currentUser = authService.findByUsernameOrElseThrowUserNotFoundException(
                UI.getCurrent().getSession().getAttribute(User.class).getUsername());

        userEditForm.getUsername().setText("Hello user " + currentUser.getUsername());
        userEditForm.getName().setValue(currentUser.getName());
        userEditForm.getEmail().setValue(currentUser.getEmail());

        binder.bindInstanceFields(userEditForm);

        binder.setStatusLabel(userEditForm.getErrorMessageField());

        userEditForm.getSave().addClickListener(event -> {
            try {
                User userBean = new User();
                binder.writeBean(userBean);

                authService.update(currentUser.getUsername(), userBean);

                showSuccess();

            } catch (ValidationException exception) {
                Notification.show(exception.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        userEditForm.getChangePasswordButton().addClickListener(event -> {
            UI.getCurrent().navigate(ResetPasswordView.class);
        });

        userEditForm.getCancel().addClickListener(event -> {
            UI.getCurrent().navigate(UserView.class);
        });
    }

    private void showSuccess() {
        Notification notification =
                Notification.show("Data updated successfully ");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().navigate("home");
    }
}

