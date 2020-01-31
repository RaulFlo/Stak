package com.example.android.stakdice.models.attribute;

import java.io.Serializable;

public interface Attribute extends Serializable {
    boolean isValid(int value);

    String getDisplayString();
}
