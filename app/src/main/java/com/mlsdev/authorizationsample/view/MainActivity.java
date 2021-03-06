package com.mlsdev.authorizationsample.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mlsdev.authorizationsample.R;
import com.mlsdev.authorizationsample.databinding.ActivityMainBinding;
import com.mlsdev.authorizationsample.view.listener.OnTextChangedListener;
import com.mlsdev.authorizationsample.viewmodel.SignInViewModel;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements AuthorizationView {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new SignInViewModel(this, binding));

        binding.etEmail.addTextChangedListener(new OnTextChangedListener(binding.tilEmailWrapper));
        binding.etPassword.addTextChangedListener(new OnTextChangedListener(binding.tilPasswordWrapper));
    }

    @Override
    public void showAuthorizationErrors(Map<String, String> errors) {
        if (errors.containsKey("email"))
            binding.tilEmailWrapper.setError(errors.get("email"));

        if (errors.containsKey("password"))
            binding.tilPasswordWrapper.setError(errors.get("password"));
    }

    @Override
    public void onUserSignedIn() {
        startActivity(new Intent(this, CongratulationActivity.class));
    }

}