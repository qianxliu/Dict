package com.qianxin.dict.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.qianxin.dict.db.dao.ProverbDao;
import com.qianxin.dict.db.entity.Proverb;

import java.util.List;

public class ProverbRepository {


    private ProverbDao mProverbDao;
    private LiveData<List<Proverb>> mAllProverbs;
    private LiveData<List<Proverb>> mSearchProverbs;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ProverbRepository(Application application) {
        ProverbRoomDatabase db = ProverbRoomDatabase.getDatabase(application);
        mProverbDao = db.proverbDao();
        mAllProverbs = mProverbDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Proverb>> getAllProverbs() {
        return mAllProverbs;
    }

    // All proverbs
    public LiveData<List<Proverb>> searchAllProverbs(String string) {
        return mProverbDao.searchAllProverbs(string);
    }

    // Dict proverbs
    public LiveData<List<Proverb>> searchDictProverbs(String string) {
        return mProverbDao.searchDictProverbs(string);
    }

    // Get Favorite proverbs
    public LiveData<List<Proverb>> getFavoriteProverbs() {
        return mProverbDao.getFavoriteProverbs();
    }

    // Search Favorite proverbs
    public LiveData<List<Proverb>> searchFavoriteProverbs(String string) {
        return mProverbDao.searchFavoriteProverbs(string);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Proverb word) {
        ProverbRoomDatabase.databaseWriteExecutor.execute(() -> {
            mProverbDao.insert(word);
        });
    }
}
