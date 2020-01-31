package com.example.android.stakdice.models.attribute;

public class ThreeValueAttribute implements Attribute {

    private int firstValue;
    private int secondValue;
    private int thirdValue;

    public ThreeValueAttribute(int firstValue, int secondValue, int thirdValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
    }

    @Override
    public boolean isValid(int value) {
        return value == firstValue || value == secondValue || value == thirdValue;
    }

    @Override
    public String getDisplayString() {
        return firstValue + "," + secondValue + "," + thirdValue;
    }
}
