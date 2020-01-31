package com.example.android.stakdice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.stakdice.converters.StakConverters;
import com.example.android.stakdice.models.attribute.SimpleValueAttribute;

@Database(entities = {StakCard.class}, version = 1)
@TypeConverters({StakConverters.class})
public abstract class StakDatabase extends RoomDatabase {


    private static StakDatabase instance;

    public abstract StakDao stakDao();

    public static synchronized StakDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StakDatabase.class, "stak_database")
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
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {


        private StakDao stakDao;

        private PopulateDbAsyncTask(StakDatabase db) {
            stakDao = db.stakDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "BlueSlime",
                    new SimpleValueAttribute(12), new SimpleValueAttribute(14), new SimpleValueAttribute(12), new SimpleValueAttribute(10), "Easy", false));
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "RedSkeleton",
                    new SimpleValueAttribute(2), new SimpleValueAttribute(12), new SimpleValueAttribute(13), new SimpleValueAttribute(11), "Medium", false));
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "BlackKnight",
                    new SimpleValueAttribute(12), new SimpleValueAttribute(14), new SimpleValueAttribute(16), new SimpleValueAttribute(14), "Hard", false));
            return null;
        }
    }
}

