package com.example.android.stakdice.models.attribute;

public class LesserThanValueAttribute implements Attribute {


    private int lesserThan;

    public LesserThanValueAttribute(int lesserThan) {
        this.lesserThan = lesserThan;
    }


    @Override
    public boolean isValid(int value) {
        return value <= lesserThan;
    }

    @Override
    public String getDisplayString() {
        return lesserThan + "-";
    }
}
