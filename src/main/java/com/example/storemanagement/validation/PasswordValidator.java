package com.example.storemanagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final int MIN_LENGTH = 14;
    private static final String UPPERCASE_PATTERN = ".*[A-Z].*";
    private static final String LOWERCASE_PATTERN = ".*[a-z].*";
    private static final String DIGIT_PATTERN = ".*\\d.*";
    private static final String SPECIAL_CHAR_PATTERN = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        return hasMinimumLength(password)
                && hasUppercase(password)
                && hasLowercase(password)
                && hasDigit(password)
                && hasSpecialCharacter(password);
    }

    private boolean hasMinimumLength(String password) {
        return password.length() >= MIN_LENGTH;
    }

    private boolean hasUppercase(String password) {
        return password.matches(UPPERCASE_PATTERN);
    }

    private boolean hasLowercase(String password) {
        return password.matches(LOWERCASE_PATTERN);
    }

    private boolean hasDigit(String password) {
        return password.matches(DIGIT_PATTERN);
    }

    private boolean hasSpecialCharacter(String password) {
        return password.matches(SPECIAL_CHAR_PATTERN);
    }
}
