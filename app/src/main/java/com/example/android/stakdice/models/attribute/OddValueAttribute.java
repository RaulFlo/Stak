package com.example.android.stakdice.models.attribute;

public class OddValueAttribute implements Attribute{


    private String odd;

    public OddValueAttribute(String odd) {
        this.odd = odd;
    }

    @Override
    public boolean isValid(int value) {
        { return (value & 0x01) != 0; }
    }

    @Override
    public String getDisplayString() {
        return odd;
    }
}
