package com.example.android.stakdice.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.database.StakDao;
import com.example.android.stakdice.database.StakDatabase;

import java.util.List;

public class StakRepo {
    private StakDao stakDao;
    private LiveData<List<StakCard>> allStaks;

    public StakRepo(Application application) {
        StakDatabase database = StakDatabase.getInstance(application);
        stakDao = database.stakDao();
        allStaks = stakDao.getAllStaks();

    }

    public void insert(StakCard stakCard) {
        new InsertStakAsyncTask(stakDao).execute(stakCard);
    }

    public void update(StakCard stakCard) {
        new UpdateStakAsyncTask(stakDao).execute(stakCard);
    }


    public void delete(StakCard stakCard) {
        new DeleteStakAsyncTask(stakDao).execute(stakCard);
    }

    public void deleteAllStaks() {
        new DeleteAllStakAsyncTask(stakDao).execute();
    }

    public LiveData<List<StakCard>> getAllStaks() {
        return allStaks;
    }

    public LiveData<StakCard> getSingleStak(int id) {
        return stakDao.getSingleStak(id);
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
