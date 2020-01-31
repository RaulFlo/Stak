package com.example.android.stakdice.models.attribute;

public class RangeValueAttribute implements Attribute {

    private int startRange;
    private int endRange;

    public RangeValueAttribute(int startRange, int endRange) {
        this.startRange = startRange;
        this.endRange = endRange;
    }

    @Override
    public boolean isValid(int value) {
        return value >= startRange && value <= endRange;
    }

    @Override
    public String getDisplayString() {
        return startRange + " - " + endRange;
    }
}
