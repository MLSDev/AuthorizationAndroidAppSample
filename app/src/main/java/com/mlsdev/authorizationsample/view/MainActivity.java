package com.mlsdev.authorizationsample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.mlsdev.authorizationsample.R;
import com.mlsdev.authorizationsample.databinding.ActivityMainBinding;
import com.mlsdev.authorizationsample.viewmodel.SignInViewModel;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements AuthorizationView {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new SignInViewModel(this, binding));
        initTextWatchers();
    }

    @Override
    public void showAuthorizationErrors(Map<String, String> errors) {
        if (errors.containsKey("email"))
            binding.tilEmailWrapper.setError(errors.get("email"));

        if (errors.containsKey("password"))
            binding.tilPasswordWrapper.setError(errors.get("password"));
    }

    private void initTextWatchers() {
        binding.etEmail.addTextChangedListener(new OnTextChangedListener(binding.etEmail.getId()));
        binding.etPassword.addTextChangedListener(new OnTextChangedListener(binding.etPassword.getId()));
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