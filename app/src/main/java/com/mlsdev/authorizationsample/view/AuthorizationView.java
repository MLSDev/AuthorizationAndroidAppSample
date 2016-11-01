package com.mlsdev.authorizationsample.view;

import java.util.Map;

public interface AuthorizationView {
    void showAuthorizationErrors(Map<String, String> errors);
}
