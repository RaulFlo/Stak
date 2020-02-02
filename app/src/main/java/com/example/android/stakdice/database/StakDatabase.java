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
                    new SimpleValueAttribute(12), new SimpleValueAttribute(14), new SimpleValueAttribute(12), new SimpleValueAttribute(10), "Easy", true));


            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "RedSlime",
                    new SimpleValueAttribute(12), new SimpleValueAttribute(14), new SimpleValueAttribute(12), new SimpleValueAttribute(10), "Easy", true));
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "YellowSlime",
                    new SimpleValueAttribute(12), new SimpleValueAttribute(14), new SimpleValueAttribute(12), new SimpleValueAttribute(10), "Easy", true));
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "GreenSlime",
                    new SimpleValueAttribute(12), new SimpleValueAttribute(14), new SimpleValueAttribute(12), new SimpleValueAttribute(10), "Easy", true));

            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "RedSkeleton",
                    new SimpleValueAttribute(2), new SimpleValueAttribute(12), new SimpleValueAttribute(13), new SimpleValueAttribute(11), "Medium", false));
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "BlackKnight",
                    new LesserThanValueAttribute(12), new ThreeValueAttribute(14,15,16), new SimpleValueAttribute(16), new SimpleValueAttribute(14), "Hard", false));
            stakDao.insert(new StakCard(R.drawable.ic_face_black_24dp, "Ranger",
                    new RangeValueAttribute(12,13),new OddValueAttribute("Odd"),new EvenValueAttribute("Even"), new GreaterThanValueAttribute(15),"Hard",false));
            return null;
        }
    }
}

