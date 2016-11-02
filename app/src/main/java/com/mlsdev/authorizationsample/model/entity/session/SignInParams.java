package com.mlsdev.authorizationsample.model.entity.session;


import com.google.gson.annotations.SerializedName;

public class SignInParams {
    @SerializedName("user")
    private User user;

    public SignInParams() {
    }

    public SignInParams(String email, String password) {
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User {
        @SerializedName("email")
        private String email;
        @SerializedName("password")
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
