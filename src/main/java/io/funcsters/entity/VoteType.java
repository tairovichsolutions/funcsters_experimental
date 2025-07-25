package io.funcsters.entity;

public enum VoteType {
    BEST_PRACTICES("Best Practices"),
    EFFICIENT("Efficient"),
    CLEVER("Clever"),
    CLEAN("Clean"),
    SHORTEST("Shortest");

    private final String label;

    VoteType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}