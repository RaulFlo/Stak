package com.example.android.stakdice.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android.stakdice.models.attribute.Attribute;

import java.io.Serializable;

@Entity(tableName = "stak_table")
public class StakCard implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int imageResource;
    private String cardName;
    private Attribute strength;
    private Attribute toughness;
    private Attribute agility;
    private Attribute knowledge;
    private String difficulty;
    private boolean isBeaten;

    public StakCard(int imageResource, String cardName, Attribute strength, Attribute toughness,
                    Attribute agility, Attribute knowledge,String difficulty, boolean isBeaten) {
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

    public Attribute getStrength() {
        return strength;
    }

    public Attribute getToughness() {
        return toughness;
    }

    public Attribute getAgility() {
        return agility;
    }

    public Attribute getKnowledge() {
        return knowledge;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isBeaten() {
        return isBeaten;
    }

    public void setBeaten(boolean beaten) {
        isBeaten = beaten;
    }
}
