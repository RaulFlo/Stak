package com.example.android.stakdice;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stak_table")
public class StakCard implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int imageResource;
    private String cardName;
    private int strength;
    private int toughness;
    private int agility;
    private int knowledge;
    private String difficulty;
    private boolean isBeaten;

    public StakCard(int imageResource, String cardName, int strength, int toughness,
                    int agility, int knowledge,String difficulty, boolean isBeaten) {
        this.imageResource = imageResource;
        this.cardName = cardName;
        this.strength = strength;
        this.toughness = toughness;
        this.agility = agility;
        this.knowledge = knowledge;
        this.difficulty = difficulty;
        this.isBeaten = isBeaten;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public int getImageResource() {
        return imageResource;
    }

    public String getCardName() {
        return cardName;
    }

    public int getStrength() {
        return strength;
    }

    public int getToughness() {
        return toughness;
    }

    public int getAgility() {
        return agility;
    }

    public int getKnowledge() {
        return knowledge;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isBeaten() {
        return isBeaten;
    }
}
