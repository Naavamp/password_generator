package com.passwordgen;

public class PasswordRequest {
    private int length;
    private String tier;
    private boolean includeUppercase;
    private boolean includeNumbers;
    private boolean includeSymbols;

    public PasswordRequest() {
    }

    public PasswordRequest(
            int length,
            String tier,
            boolean includeUppercase,
            boolean includeNumbers,
            boolean includeSymbols) {
        this.length = length;
        this.tier = tier;
        this.includeUppercase = includeUppercase;
        this.includeNumbers = includeNumbers;
        this.includeSymbols = includeSymbols;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public boolean isIncludeUppercase() {
        return includeUppercase;
    }

    public void setIncludeUppercase(boolean includeUppercase) {
        this.includeUppercase = includeUppercase;
    }

    public boolean isIncludeNumbers() {
        return includeNumbers;
    }

    public void setIncludeNumbers(boolean includeNumbers) {
        this.includeNumbers = includeNumbers;
    }

    public boolean isIncludeSymbols() {
        return includeSymbols;
    }

    public void setIncludeSymbols(boolean includeSymbols) {
        this.includeSymbols = includeSymbols;
    }
}
