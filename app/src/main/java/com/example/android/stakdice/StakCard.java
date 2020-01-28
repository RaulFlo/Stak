package com.example.android.stakdice;

import java.io.Serializable;

public class StakCard implements Serializable {

    private int mImageResource;
    private String mCardName;
    private int mStrength;
    private int mToughness;
    private int mAgility;
    private int mKnowledge;

    public StakCard(int mImageResource, String mCardName, int mStrength, int mToughness,int mAgility, int mKnowledge) {
        this.mImageResource = mImageResource;
        this.mCardName = mCardName;
        this.mStrength = mStrength;
        this.mToughness = mToughness;
        this.mAgility = mAgility;
        this.mKnowledge = mKnowledge;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmCardName() {
        return mCardName;
    }

    public void setmCardName(String mCardName) {
        this.mCardName = mCardName;
    }

    public int getmStrength() {
        return mStrength;
    }

    public void setmStrength(int mStrength) {
        this.mStrength = mStrength;
    }

    public int getmAgility() {
        return mAgility;
    }

    public void setmAgility(int mAgility) {
        this.mAgility = mAgility;
    }

    public int getmToughness() {
        return mToughness;
    }

    public void setmToughness(int mToughness) {
        this.mToughness = mToughness;
    }

    public int getmKnowledge() {
        return mKnowledge;
    }

    public void setmKnowledge(int mKnowledge) {
        this.mKnowledge = mKnowledge;
    }
}
