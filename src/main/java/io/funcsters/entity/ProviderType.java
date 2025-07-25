package io.funcsters.entity;

public enum ProviderType {
    GOOGLE("Google"),
    GITHUB("GitHub"),
    LINKEDIN("LinkedIn");

    private final String displayName;

    ProviderType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}