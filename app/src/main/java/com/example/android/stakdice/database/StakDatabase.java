package com.example.android.stakdice.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.stakdice.R;
import com.example.android.stakdice.converters.StakConverters;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.attribute.EvenValueAttribute;
import com.example.android.stakdice.models.attribute.GreaterThanValueAttribute;
import com.example.android.stakdice.models.attribute.LesserThanValueAttribute;
import com.example.android.stakdice.models.attribute.OddValueAttribute;
import com.example.android.stakdice.models.attribute.RangeValueAttribute;
import com.example.android.stakdice.models.attribute.SimpleValueAttribute;
import com.example.android.stakdice.models.attribute.ThreeValueAttribute;

@Database(entities = {StakCard.class}, version = 1)
@TypeConverters({StakConverters.class})
public abstract class StakDatabase extends RoomDatabase {

    private static String DATABASE_NAME = "stak_database";
    private static StakDatabase instance;

    public abstract StakDao stakDao();

    public static synchronized StakDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StakDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //to override the on create to populate the db when first called
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}

