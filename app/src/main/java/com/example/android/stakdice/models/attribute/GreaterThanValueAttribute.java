package com.example.android.stakdice.models.attribute;

public class GreaterThanValueAttribute implements Attribute {

    private int greaterThan;

    public GreaterThanValueAttribute(int greaterThan) {
        this.greaterThan = greaterThan;
    }

    @Override
    public boolean isValid(int value) {
        return value >= greaterThan;
    }

    @Override
    public String getDisplayString() {
        return greaterThan + "+";
    }
}
