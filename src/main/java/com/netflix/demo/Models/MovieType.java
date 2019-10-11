package com.netflix.demo.Models;

public enum MovieType {
    ORIGINAL("original"),
    SUGGESTED("suggested");

    public final String label;

    private MovieType(String label) {
        this.label = label;
    }
}
