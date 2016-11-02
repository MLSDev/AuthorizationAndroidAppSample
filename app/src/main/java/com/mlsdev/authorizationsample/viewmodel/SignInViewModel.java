package com.mlsdev.authorizationsample.viewmodel;

import android.databinding.BaseObservable;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.google.gson.Gson;
import com.mlsdev.authorizationsample.databinding.ActivityMainBinding;
import com.mlsdev.authorizationsample.model.entity.ErrorsHolder;
import com.mlsdev.authorizationsample.model.entity.session.SignInParams;
import com.mlsdev.authorizationsample.model.entity.session.SignInResponseData;
import com.mlsdev.authorizationsample.model.nework.ApiClient;
import com.mlsdev.authorizationsample.util.FieldsValidator;
import com.mlsdev.authorizationsample.view.AuthorizationView;
import com.mlsdev.authorizationsample.view.Constants;

import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

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
            SignInParams signInParams = new SignInParams(email, password);
            ApiClient.getInstance().signUserIn(signInParams, new SignUserInSubscriber());
        }
    }

    public class SignUserInSubscriber extends Subscriber<SignInResponseData> {

        @Override
        public void onCompleted() {
            unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {
                retrofit2.Response response = ((HttpException) e).response();
                ErrorsHolder errorsHolder = new Gson().fromJson(response.errorBody().toString(), ErrorsHolder.class);

                if (!errorsHolder.getErrors().getEmail().isEmpty())
                    binding.tilEmailWrapper.setError(errorsHolder.getErrors().getEmail().get(0));

                if (!errorsHolder.getErrors().getPassword().isEmpty())
                    binding.tilPasswordWrapper.setError(errorsHolder.getErrors().getPassword().get(0));
            }
        }

        @Override
        public void onNext(SignInResponseData signInResponseData) {
            // TODO: 11/2/16 handle when a user has been signed in
        }
    }

}
