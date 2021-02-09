package com.example.android.stakdice.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.stakdice.models.StakCard;

import java.util.List;

@Dao
public interface StakDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StakCard stakCard);

    @Update
    void update(StakCard stakCard);

    @Delete
    void delete(StakCard stakCard);

    @Query("DELETE FROM stak_table")
    void deleteAllStaks();

    @Query("SELECT * FROM stak_table ORDER BY cardName DESC")
    LiveData<List<StakCard>> getAllStaks();

    @Query("SELECT * FROM stak_table  where id = :stakId LIMIT 1")
    LiveData<StakCard> getSingleStak(String stakId);

    @Query("Select * From stak_table where isBeaten = 1")
    LiveData<List<StakCard>> getAllBeatenStaks();

    @Query("Select * From stak_table where isBeaten = 0")
    LiveData<List<StakCard>> getAllNotBeatenStaks();


}

