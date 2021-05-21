package com.qianxin.dict.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.qianxin.dict.db.dao.ProverbDao;
import com.qianxin.dict.db.entity.Proverb;

import java.util.List;

public class ProverbRepository {


    private ProverbDao mProverbDao;
    private LiveData<List<Proverb>> mAllProverbs;

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

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Proverb word) {
        ProverbRoomDatabase.databaseWriteExecutor.execute(() -> {
            mProverbDao.insert(word);
        });
    }
}
