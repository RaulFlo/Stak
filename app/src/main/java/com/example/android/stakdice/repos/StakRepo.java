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
    private LiveData<List<StakCard>> allBeatenStaks;
    private LiveData<List<StakCard>> allNotBeatenStaks;

    public StakRepo(Application application) {
        StakDatabase database = StakDatabase.getInstance(application);
        stakDao = database.stakDao();
        allStaks = stakDao.getAllStaks();
        allBeatenStaks = stakDao.getAllBeatenStaks();
        allNotBeatenStaks = stakDao.getAllNotBeatenStaks();

    }

    public void insert(StakCard... stakCards) {
        new InsertStakAsyncTask(stakDao).execute(stakCards);
    }

    public void update(StakCard... stakCards) {
        new UpdateStakAsyncTask(stakDao).execute(stakCards);
    }


    public void delete(StakCard... stakCards) {
        new DeleteStakAsyncTask(stakDao).execute(stakCards);
    }

    public void deleteAllStaks() {
        new DeleteAllStakAsyncTask(stakDao).execute();
    }

    public LiveData<List<StakCard>> getAllStaks() {
        return allStaks;
    }

    public LiveData<List<StakCard>> getAllBeatenStaks() {
        return allBeatenStaks;
    }

    public LiveData<List<StakCard>> getAllNotBeatenStaks() {
        return allNotBeatenStaks;
    }

    public LiveData<StakCard> getSingleStak(String id) {
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
            for (StakCard stakCard : stakCards) {
                stakDao.insert(stakCard);
            }
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
            for (StakCard stakCard : stakCards) {
                stakDao.update(stakCard);
            }
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
            for (StakCard stakCard : stakCards) {
                stakDao.delete(stakCard);
            }
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
