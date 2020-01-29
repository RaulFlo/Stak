package com.example.android.stakdice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {StakCard.class}, version = 1)
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
                    12,14,12,10,"Easy",false));
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "RedSkeleton",
                    2,12,13,11,"Medium",false));
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "BlackKnight",
                    12,14,16,14,"Hard",false));
            return null;
        }
    }
}

