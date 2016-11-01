package com.mlsdev.authorizationsample.util;

import android.content.Context;
import android.util.Patterns;

import com.mlsdev.authorizationsample.R;

import java.util.HashMap;
import java.util.Map;

import static com.mlsdev.authorizationsample.view.Constants.EMAIL;
import static com.mlsdev.authorizationsample.view.Constants.PASSWORD;

public class FieldsValidator {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static Map<String, String> sInvalidFields;

    private static boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean validatePasswordLength(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    public static Map<String, String> validateFields(Context context, Map<String, String> fields) {
        sInvalidFields = new HashMap<>();
        boolean oneIsEmpty = false;

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getValue().isEmpty()) {
                oneIsEmpty = true;
                sInvalidFields.put(entry.getKey(), context.getString(R.string.error_message_empty));
            }
        }

        if (oneIsEmpty)
            return sInvalidFields;


        if (fields.containsKey(EMAIL)) {
            if (!validateEmail(fields.get(EMAIL))) {
                sInvalidFields.put(EMAIL, context.getString(R.string.error_message_incorrect));
            }
        }

        if (fields.containsKey(PASSWORD)) {
            if (!validatePasswordLength(fields.get(PASSWORD))) {
                sInvalidFields.put(PASSWORD, context.getString(R.string.error_message_too_short_password));
            }
        }

        return sInvalidFields;
    }

}
