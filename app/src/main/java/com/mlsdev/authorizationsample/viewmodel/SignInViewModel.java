package com.mlsdev.authorizationsample.viewmodel;

import android.databinding.BaseObservable;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.mlsdev.authorizationsample.databinding.ActivityMainBinding;
import com.mlsdev.authorizationsample.util.FieldsValidator;
import com.mlsdev.authorizationsample.view.AuthorizationView;
import com.mlsdev.authorizationsample.view.Constants;

import java.util.Map;

public class SignInViewModel extends BaseObservable {
    private AuthorizationView view;
    private ActivityMainBinding binding;

    public SignInViewModel(AuthorizationView view, ActivityMainBinding binding) {
        this.view = view;
        this.binding = binding;
    }

    public void onSignInClick(View view) {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        Map<String, String> fieldsForValidation = new ArrayMap<>(2);
        fieldsForValidation.put(Constants.EMAIL, email);
        fieldsForValidation.put(Constants.PASSWORD, password);

        Map<String, String> incorrectFields = FieldsValidator.validateFields(view.getContext(), fieldsForValidation);

        if (!incorrectFields.isEmpty()) {
            this.view.showAuthorizationErrors(incorrectFields);
        } else {
            // TODO: 11/1/16 make a sign in request
        }
    }

}
