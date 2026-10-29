package com.passwordgen;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private final SecureRandom secureRandom;

    public PasswordGenerator() {
        this.secureRandom = new SecureRandom();
    }

    public String generate(PasswordRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Password request must not be null");
        }

        int length = Math.max(request.getLength(), minimumLengthFor(request.getTier()));
        String characterPool = buildCharacterPool(request);
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        return password.toString();
    }

    private String buildCharacterPool(PasswordRequest request) {
        StringBuilder characterPool = new StringBuilder(LOWERCASE);

        if (request.isIncludeUppercase()) {
            characterPool.append(UPPERCASE);
        }
        if (request.isIncludeNumbers()) {
            characterPool.append(NUMBERS);
        }
        if (request.isIncludeSymbols()) {
            characterPool.append(SYMBOLS);
        }

        return characterPool.toString();
    }

    private int minimumLengthFor(String tier) {
        if (tier == null || tier.isBlank() || "basic".equalsIgnoreCase(tier)) {
            return 8;
        }
        if ("standard".equalsIgnoreCase(tier)) {
            return 12;
        }
        if ("paranoid".equalsIgnoreCase(tier)) {
            return 16;
        }

        throw new IllegalArgumentException("Unsupported password tier: " + tier);
    }
}
