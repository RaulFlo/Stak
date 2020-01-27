package com.example.android.stakdice;

public class StakCard {

    private String mImage;
    private String mCardName;
    private int mStrength;
    private int mAgility;
    private int mToughness;
    private int mKnowledge;

    public StakCard(String mImage, String mCardName, int mStrength, int mAgility, int mToughness, int mKnowledge) {
        this.mImage = mImage;
        this.mCardName = mCardName;
        this.mStrength = mStrength;
        this.mAgility = mAgility;
        this.mToughness = mToughness;
        this.mKnowledge = mKnowledge;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
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
