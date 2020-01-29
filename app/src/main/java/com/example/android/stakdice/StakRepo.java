package com.example.android.stakdice;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StakRepo {
    private StakDao stakDao;
    private LiveData<List<StakCard>> allStaks;

    public StakRepo(Application application) {
        StakDatabase database = StakDatabase.getInstance(application);
        stakDao = database.stakDao();
        allStaks = stakDao.getAllStaks();

    }

    public void insert(StakCard note) {
        new InsertStakAsyncTask(stakDao).execute(note);
    }

    public void update(StakCard note) {
        new UpdateStakAsyncTask(stakDao).execute(note);
    }


    public void delete(StakCard note) {
        new DeleteStakAsyncTask(stakDao).execute(note);
    }

    public void deleteAllStaks() {
        new DeleteAllStakAsyncTask(stakDao).execute();
    }

    public LiveData<List<StakCard>> getAllStaks() {
        return allStaks;
    }


    //AsyncTask for each method

    private static class InsertStakAsyncTask extends AsyncTask<StakCard, Void, Void> {
        private StakDao stakDao;

        private InsertStakAsyncTask(StakDao stakDao) {
            this.stakDao = stakDao;
        }

        @Override
        protected Void doInBackground(StakCard... stakCards) {
            stakDao.insert(stakCards[0]);
            return null;
        }
    }

    private static class UpdateStakAsyncTask extends AsyncTask<StakCard, Void, Void> {
        private StakDao stakDao;

        private UpdateStakAsyncTask(StakDao stakDao) {
            this.stakDao = stakDao;
        }

        @Override
        protected Void doInBackground(StakCard... stakCards) {
            stakDao.update(stakCards[0]);
            return null;
        }
    }

    private static class DeleteStakAsyncTask extends AsyncTask<StakCard, Void, Void> {
        private StakDao stakDao;

        private DeleteStakAsyncTask(StakDao stakDao) {
            this.stakDao = stakDao;
        }

        @Override
        protected Void doInBackground(StakCard... stakCards) {
            stakDao.delete(stakCards[0]);
            return null;
        }
    }

    private static class DeleteAllStakAsyncTask extends AsyncTask<Void, Void, Void> {
        private StakDao stakDao;

        private DeleteAllStakAsyncTask(StakDao stakDao) {
            this.stakDao = stakDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stakDao.deleteAllStaks();
            return null;
        }
    }
}
