package com.mlsdev.authorizationsample.model.nework;

import com.mlsdev.authorizationsample.model.entity.session.SignInParams;
import com.mlsdev.authorizationsample.model.entity.session.SignInResponseData;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiClient {
    private static ApiClient instance;
    private static String sBaseUrl = "/";
    private AuthorizationService authorizationService;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        authorizationService = retrofit.create(AuthorizationService.class);
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null)
            instance = new ApiClient();

        return instance;
    }

    public Subscription signUserIn(SignInParams params, Subscriber<SignInResponseData> subscriber) {
        return authorizationService.signUserIn(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
