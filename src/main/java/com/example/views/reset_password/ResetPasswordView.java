package com.example.views.reset_password;

import com.example.components.ResetPasswordForm;
import com.example.data.service.UserService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ResetPasswordView extends VerticalLayout {
    private final UserService authService;

    public ResetPasswordView(UserService authService) {
        this.authService = authService;
        addClassName("reset-password-view");
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        add(resetPasswordForm);

        ResetPasswordBinder resetPasswordBinder = new ResetPasswordBinder(resetPasswordForm, authService);
        resetPasswordBinder.addBindingAndValidation();
    }
}
