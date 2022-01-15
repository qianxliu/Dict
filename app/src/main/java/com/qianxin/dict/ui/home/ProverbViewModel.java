package com.qianxin.dict.ui.home;

import android.app.Application;
import android.os.Environment;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.qianxin.dict.db.ProverbRepository;
import com.qianxin.dict.db.entity.Proverb;
import com.qianxin.dict.util.Similar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ProverbViewModel extends AndroidViewModel {
    private final LiveData<List<Proverb>> mAllProverbs;
    private final ProverbRepository mRepository;
    private final List<String> chsDocuments = new ArrayList<>();
    private final String modelName = "45000-small.txt";
    private final File chsVec = new File(Environment.getExternalStorageDirectory(), modelName);
    private static DocVectorModel docVectorModel;

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

    LiveData<List<Proverb>> marchProverbs(List<String> chinaProverbs) {
        return mRepository.marchProverbs(chinaProverbs);
    }

    LiveData<List<Proverb>> searchAiAllProverbs(String string) throws IOException {
        if (chsDocuments.isEmpty()) {
            for (Proverb p : Objects.requireNonNull(mAllProverbs.getValue())) {
                chsDocuments.add(p.getChinaProverb());
            }
        }

        if (!chsVec.exists()) {
            InputStream inputStream = getApplication().getAssets().open(modelName);
            OutputStream outputStream = new FileOutputStream(chsVec);
            byte[] buf = new byte[1024];
            while (inputStream.read(buf) > 0)
                outputStream.write(buf, 0, inputStream.read(buf));
            inputStream.close();
            outputStream.close();
            docVectorModel = new DocVectorModel(new WordVectorModel(chsVec.getAbsolutePath()));
        }
        if (docVectorModel == null)
            docVectorModel = new DocVectorModel(new WordVectorModel(chsVec.getAbsolutePath()));

        Similar similar = new Similar(docVectorModel, chsDocuments);
        List<String> chinaProverbs = new ArrayList<>();
        for (Map.Entry<Integer, Float> entry : similar.docNearest(string, 10))
            chinaProverbs.add(chsDocuments.get(entry.getKey()));
        return mRepository.marchProverbs(chinaProverbs);
    }

    public void insert(Proverb proverb) {
        mRepository.insert(proverb);
    }
}