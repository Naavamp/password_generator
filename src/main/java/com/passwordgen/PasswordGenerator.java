package com.passwordgen;

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final Logger LOGGER = Logger.getLogger(PasswordGenerator.class.getName());

    private final SecureRandom secureRandom;

    public PasswordGenerator() {
        this.secureRandom = new SecureRandom();
    }

    public String generate(PasswordRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Password request must not be null");
        }

        LOGGER.log(
                Level.INFO,
                "Generating password with parameters: length={0}, tier={1}, includeUppercase={2}, "
                        + "includeNumbers={3}, includeSymbols={4}",
                new Object[] {
                    request.getLength(),
                    request.getTier(),
                    request.isIncludeUppercase(),
                    request.isIncludeNumbers(),
                    request.isIncludeSymbols()
                });

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
