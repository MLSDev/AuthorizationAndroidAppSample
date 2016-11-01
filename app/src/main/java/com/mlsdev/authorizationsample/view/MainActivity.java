package com.mlsdev.authorizationsample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    }

    @Override
    public void showAuthorizationErrors(Map<String, String> errors) {
        if (errors.containsKey("email"))
            binding.tilEmailWrapper.setError(errors.get("email"));

        if (errors.containsKey("password"))
            binding.tilPasswordWrapper.setError(errors.get("password"));
    }

}