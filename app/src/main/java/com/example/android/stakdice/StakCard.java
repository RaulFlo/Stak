package com.example.android.stakdice;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stak_table")
public class StakCard implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int mImageResource;
    private String mCardName;
    private int mStrength;
    private int mToughness;
    private int mAgility;
    private int mKnowledge;
    private String mDifficulty;
    private boolean isBeaten;

    public StakCard(int mImageResource, String mCardName,
                    int mStrength, int mToughness, int mAgility,
                    int mKnowledge, String mDifficulty,boolean isBeaten) {
        this.mImageResource = mImageResource;
        this.mCardName = mCardName;
        this.mStrength = mStrength;
        this.mToughness = mToughness;
        this.mAgility = mAgility;
        this.mKnowledge = mKnowledge;
        this.mDifficulty = mDifficulty;
        this.isBeaten = isBeaten;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public String getmDifficulty() {
        return mDifficulty;
    }

    public void setmDifficulty(String mDifficulty) {
        this.mDifficulty = mDifficulty;
    }

    public boolean isBeaten() {
        return isBeaten;
    }

    public void setBeaten(boolean beaten) {
        isBeaten = beaten;
    }
}
