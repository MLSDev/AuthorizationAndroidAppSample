package com.mlsdev.authorizationsample.viewmodel;

import android.databinding.BaseObservable;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
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
        initTextWatchers();
    }

    private void initTextWatchers() {
        binding.etEmail.addTextChangedListener(new OnTextChangedListener(binding.etEmail.getId()));
        binding.etPassword.addTextChangedListener(new OnTextChangedListener(binding.etPassword.getId()));
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

    public class OnTextChangedListener implements TextWatcher {
        private int viewId;

        public OnTextChangedListener(int viewId) {
            this.viewId = viewId;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                if (viewId == binding.etEmail.getId()) {
                    binding.tilEmailWrapper.setError(null);
                    binding.tilEmailWrapper.setErrorEnabled(false);
                }

                if (viewId == binding.etPassword.getId()) {
                    binding.tilPasswordWrapper.setError(null);
                    binding.tilPasswordWrapper.setErrorEnabled(false);
                }
            }
        }
    }

}
