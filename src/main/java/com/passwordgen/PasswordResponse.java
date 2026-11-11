package com.passwordgen;

import java.util.List;

public class PasswordResponse {
    private String password;
    private double entropyBits;
    private String strengthLabel;
    private List<String> failedRules;

    public PasswordResponse() {
    }

    public PasswordResponse(
            String password,
            double entropyBits,
            String strengthLabel,
            List<String> failedRules) {
        this.password = password;
        this.entropyBits = entropyBits;
        this.strengthLabel = strengthLabel;
        this.failedRules = failedRules;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getEntropyBits() {
        return entropyBits;
    }

    public void setEntropyBits(double entropyBits) {
        this.entropyBits = entropyBits;
    }

    public String getStrengthLabel() {
        return strengthLabel;
    }

    public void setStrengthLabel(String strengthLabel) {
        this.strengthLabel = strengthLabel;
    }

    public List<String> getFailedRules() {
        return failedRules;
    }

    public void setFailedRules(List<String> failedRules) {
        this.failedRules = failedRules;
    }
}
