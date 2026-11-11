package com.passwordgen;

import java.util.ArrayList;
import java.util.List;

public class StrengthEvaluator {
    private static final int LOWERCASE_POOL_SIZE = 26;
    private static final int UPPERCASE_POOL_SIZE = 26;
    private static final int NUMBER_POOL_SIZE = 10;
    private static final int SYMBOL_POOL_SIZE = 27;
    private static final int MINIMUM_LENGTH = 8;
    private static final double WEAK_ENTROPY = 40.0;
    private static final double FAIR_ENTROPY = 60.0;
    private static final double STRONG_ENTROPY = 80.0;

    public PasswordResponse evaluate(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password must not be null");
        }

        List<String> failedRules = new ArrayList<>();
        int characterPoolSize = 0;

        if (containsLowercase(password)) {
            characterPoolSize += LOWERCASE_POOL_SIZE;
        } else {
            failedRules.add("Password must contain a lowercase letter");
        }
        if (containsUppercase(password)) {
            characterPoolSize += UPPERCASE_POOL_SIZE;
        } else {
            failedRules.add("Password must contain an uppercase letter");
        }
        if (containsNumber(password)) {
            characterPoolSize += NUMBER_POOL_SIZE;
        } else {
            failedRules.add("Password must contain a number");
        }
        if (containsSymbol(password)) {
            characterPoolSize += SYMBOL_POOL_SIZE;
        } else {
            failedRules.add("Password must contain a symbol");
        }
        if (password.length() < MINIMUM_LENGTH) {
            failedRules.add("Password must be at least 8 characters long");
        }

        double entropyBits = characterPoolSize == 0
                ? 0.0
                : password.length() * (Math.log(characterPoolSize) / Math.log(2));

        return new PasswordResponse(
                password,
                entropyBits,
                strengthLabel(entropyBits),
                failedRules);
    }

    private String strengthLabel(double entropyBits) {
        if (entropyBits < WEAK_ENTROPY) {
            return "Weak";
        }
        if (entropyBits < FAIR_ENTROPY) {
            return "Fair";
        }
        if (entropyBits < STRONG_ENTROPY) {
            return "Strong";
        }
        return "Very Strong";
    }

    private boolean containsLowercase(String password) {
        return password.chars().anyMatch(Character::isLowerCase);
    }

    private boolean containsUppercase(String password) {
        return password.chars().anyMatch(Character::isUpperCase);
    }

    private boolean containsNumber(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }

    private boolean containsSymbol(String password) {
        return password.chars().anyMatch(character -> !Character.isLetterOrDigit(character));
    }
}
