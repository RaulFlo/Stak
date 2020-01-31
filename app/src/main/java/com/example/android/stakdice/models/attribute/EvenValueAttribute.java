package com.example.android.stakdice.models.attribute;

public class EvenValueAttribute implements Attribute {

    private String even;

    public EvenValueAttribute(String even) {
        this.even = even;
    }


    @Override
    public boolean isValid(int value) {
        return (value % 2) == 0;
    }

    @Override
    public String getDisplayString() {
        return even;
    }
}
