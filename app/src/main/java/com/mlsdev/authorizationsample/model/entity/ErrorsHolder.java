package com.mlsdev.authorizationsample.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ErrorsHolder {
    @SerializedName("errors")
    private Errors errors;

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public class Errors {
        @SerializedName("email")
        private List<String> email = new ArrayList<String>();
        @SerializedName("password")
        private List<String> password = new ArrayList<String>();

        public List<String> getEmail() {
            return email;
        }

        public void setEmail(List<String> email) {
            this.email = email;
        }

        public List<String> getPassword() {
            return password;
        }

        public void setPassword(List<String> password) {
            this.password = password;
        }
    }
}
