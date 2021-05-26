package com.qianxin.dict.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.qianxin.dict.db.ProverbRepository;
import com.qianxin.dict.db.entity.Proverb;

import java.util.List;

public class ProverbViewModel extends AndroidViewModel {

    private final LiveData<List<Proverb>> mAllProverbs;
    private final ProverbRepository mRepository;

    public ProverbViewModel(Application application) {
        super(application);
        mRepository = new ProverbRepository(application);
        mAllProverbs = mRepository.getAllProverbs();
    }

    LiveData<List<Proverb>> getAllProverbs() {
        return mAllProverbs;
    }

    LiveData<List<Proverb>> searchAllProverbs(String string) {
        return mRepository.searchAllProverbs(string);
    }

    public void insert(Proverb proverb) {
        mRepository.insert(proverb);
    }
}