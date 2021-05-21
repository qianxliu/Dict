package com.qianxin.dict.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qianxin.dict.db.ProverbRepository;
import com.qianxin.dict.db.entity.Proverb;

import java.util.List;

public class ProverbViewModel extends AndroidViewModel {

    private ProverbRepository mRepository;

    private final LiveData<List<Proverb>> mAllProverbs;

    public ProverbViewModel (Application application) {
        super(application);
        mRepository = new ProverbRepository(application);
        mAllProverbs = mRepository.getAllProverbs();
    }

    LiveData<List<Proverb>> getAllProverbs() { return mAllProverbs; }

    public void insert(Proverb proverb) { mRepository.insert(proverb); }
}