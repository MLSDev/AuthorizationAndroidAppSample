package com.mlsdev.authorizationsample;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.mlsdev.authorizationsample.util.FieldsValidator;
import com.mlsdev.authorizationsample.view.Constants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.Map;

@RunWith(RobolectricTestRunner.class)
public class FieldsValidatorTest {
    private String email;
    private String password;
    private boolean actualResult;
    private String emptyErrorMessage = "Empty";
    private String emailErrorMessage = "Incorrect";
    private String passwordErrorMessage = "To short";
    private String correctEmail = "test@test.com";
    private String correctPassword = "12345678";
    private String incorrectEmail = "test@...";
    private String incorrectPassword = "1";

    @Mock
    Context context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(context.getString(R.string.error_message_empty)).thenReturn(emptyErrorMessage);
        Mockito.when(context.getString(R.string.error_message_incorrect)).thenReturn(emailErrorMessage);
        Mockito.when(context.getString(R.string.error_message_too_short_password)).thenReturn(passwordErrorMessage);
    }

    @Test
    public void testValidEmailAndPassword() {
        actualResult = FieldsValidator.validateFields(context, getValues(correctEmail, correctPassword)).isEmpty();
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testEmptyValues() {
        email = "";
        password = "";
        Map<String, String> errors = FieldsValidator
                .validateFields(context, getValues(email, password));
        actualResult = errors.isEmpty();
        Assert.assertFalse(actualResult);
        Assert.assertTrue(errors.containsKey(Constants.EMAIL));
        Assert.assertTrue(errors.containsKey(Constants.PASSWORD));
        Assert.assertEquals(emptyErrorMessage, errors.get(Constants.EMAIL));
        Assert.assertEquals(emptyErrorMessage, errors.get(Constants.PASSWORD));
    }

    @Test
    public void testIncorrectEmailAndCorrectPassword() {
        Map<String, String> errors = FieldsValidator
                .validateFields(context, getValues(incorrectEmail, correctPassword));
        actualResult = errors.isEmpty();
        Assert.assertFalse(actualResult);
        Assert.assertTrue(errors.containsKey(Constants.EMAIL));
        Assert.assertFalse(errors.containsKey(Constants.PASSWORD));
        Assert.assertEquals(emailErrorMessage, errors.get(Constants.EMAIL));
    }

    @Test
    public void testCorrectEmailAndIncorrectPassword() {
        Map<String, String> errors = FieldsValidator
                .validateFields(context, getValues(correctEmail, incorrectPassword));
        actualResult = errors.isEmpty();
        Assert.assertFalse(actualResult);
        Assert.assertTrue(errors.containsKey(Constants.PASSWORD));
        Assert.assertFalse(errors.containsKey(Constants.EMAIL));
        Assert.assertEquals(passwordErrorMessage, errors.get(Constants.PASSWORD));
    }

    @Test
    public void testEmptyEmailAndIncorrectPassword() {
        Map<String, String> errors = FieldsValidator
                .validateFields(context, getValues("", incorrectPassword));
        actualResult = errors.isEmpty();
        Assert.assertFalse(actualResult);
        Assert.assertTrue(errors.containsKey(Constants.EMAIL));
        Assert.assertTrue(errors.containsKey(Constants.PASSWORD));
        Assert.assertEquals(emptyErrorMessage, errors.get(Constants.EMAIL));
        Assert.assertEquals(passwordErrorMessage, errors.get(Constants.PASSWORD));
    }

    @Test(expected = NullPointerException.class)
    public void testEmailAndPasswordAreNull() {
        FieldsValidator.validateFields(context, getValues(null, null));
    }

    private Map<String, String> getValues(String email, String password) {
        Map<String, String> values = new ArrayMap<>();
        values.put(Constants.EMAIL, email);
        values.put(Constants.PASSWORD, password);
        return values;
    }

}
