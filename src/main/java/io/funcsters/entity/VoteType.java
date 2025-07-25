package io.funcsters.entity;

public enum VoteType {
    BEST_PRACTICE("Best Practice"),
    EFFICIENT("Efficient"),
    CLEVER("Clever"),
    CLEAN("Clean"),
    SHORTEST("Short");

    private final String label;

    VoteType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}