package com.example.android.stakdice.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android.stakdice.models.attribute.Attribute;

import java.io.Serializable;

@Entity(tableName = "stak_table")
public class StakCard implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;

    private String imageResourceUrl;
    private String cardName;
    private Attribute strength;
    private Attribute toughness;
    private Attribute agility;
    private Attribute knowledge;
    private String difficulty;
    private boolean isBeaten;
    private String description;

    public StakCard(@NonNull String id, String imageResourceUrl, String cardName, Attribute strength, Attribute toughness,
                    Attribute agility, Attribute knowledge, String difficulty, boolean isBeaten, String description) {
        this.id = id;
        this.imageResourceUrl = imageResourceUrl;
        this.cardName = cardName;
        this.strength = strength;
        this.toughness = toughness;
        this.agility = agility;
        this.knowledge = knowledge;
        this.difficulty = difficulty;
        this.isBeaten = isBeaten;
        this.description = description;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public String getImageResourceUrl() {
        return imageResourceUrl;
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

    public String getDescription() {
        return description;
    }

    public void setBeaten(boolean beaten) {
        isBeaten = beaten;
    }

    public boolean isValid(Integer sValue, Integer tValue, Integer aValue, Integer kValue) {
        return strength.isValid(sValue) && toughness.isValid(tValue)
                && agility.isValid(aValue) && knowledge.isValid(kValue);
    }
}
