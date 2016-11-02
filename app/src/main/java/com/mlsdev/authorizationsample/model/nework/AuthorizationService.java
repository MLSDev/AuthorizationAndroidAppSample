package com.mlsdev.authorizationsample.model.nework;

import com.mlsdev.authorizationsample.model.entity.session.SignInParams;
import com.mlsdev.authorizationsample.model.entity.session.SignInResponseData;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthorizationService {
    @POST("/")
    Observable<SignInResponseData> signUserIn(@Body SignInParams params);
}
