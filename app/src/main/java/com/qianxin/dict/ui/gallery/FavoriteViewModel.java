package com.qianxin.dict.ui.gallery;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.qianxin.dict.db.ProverbRepository;
import com.qianxin.dict.db.entity.Proverb;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final LiveData<List<Proverb>> mFavoriteProverbs;
    private final ProverbRepository mRepository;

    public FavoriteViewModel(Application application) {
        super(application);
        mRepository = new ProverbRepository(application);
        mFavoriteProverbs = mRepository.getFavoriteProverbs();
    }

    LiveData<List<Proverb>> getFavoriteProverbs() {
        return mFavoriteProverbs;
    }

    LiveData<List<Proverb>> searchFavoriteProverbs(String string) {
        return mRepository.searchFavoriteProverbs(string);
    }

    public void insert(Proverb proverb) {
        mRepository.insert(proverb);
    }
}