package com.example.android.stakdice.models.attribute;

public class SimpleValueAttribute implements Attribute {
    private int attributeValue;

    public SimpleValueAttribute(int attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public boolean isValid(int value) {
        return value == attributeValue;
    }

    @Override
    public String getDisplayString() {
        return String.valueOf(attributeValue);
    }
}
