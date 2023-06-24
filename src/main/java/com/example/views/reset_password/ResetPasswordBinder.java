package com.example.views.reset_password;

import com.example.components.ResetPasswordForm;
import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.example.views.user.UserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

public class ResetPasswordBinder {
    private ResetPasswordForm resetPasswordForm;

    private final UserService authService;

    private boolean enablePasswordValidation;

    public ResetPasswordBinder(ResetPasswordForm resetPasswordForm, UserService authService) {
        this.authService = authService;
        this.resetPasswordForm = resetPasswordForm;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

        User currentUser = authService.findByUsernameOrElseThrowUserNotFoundException(
                UI.getCurrent().getSession().getAttribute(User.class).getUsername());

        binder.bindInstanceFields(resetPasswordForm);

        binder.forField(resetPasswordForm.getPassword())
                .withValidator(this::passwordValidator).bind("password");

        resetPasswordForm.getPasswordConfirm().addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(resetPasswordForm.getErrorMessageField());

        resetPasswordForm.getSave().addClickListener(event -> {
            try {
                User userBean = new User();
                binder.writeBean(userBean);

                authService.updatePassword(currentUser.getUsername(), userBean);

                showSuccess();

            } catch (ValidationException exception) {
                Notification.show(exception.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });



        resetPasswordForm.getCancel().addClickListener(event -> {
            UI.getCurrent().navigate(UserView.class);
        });
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        if (!enablePasswordValidation) {

            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = resetPasswordForm.getPasswordConfirm().getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private void showSuccess() {
        Notification notification =
                Notification.show("Your password updated successfully" );
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().navigate("home");
    }
}
