package com.qianxin.dict.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "proverb_table")
public class Proverb {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "proverb")
    private final String mProverb;

    @NonNull
    @ColumnInfo(name = "interpretation")
    private final String mInterpretation;

    @NonNull
    @ColumnInfo(name = "chinaProverb")
    private final String mChinaProverb;

    @ColumnInfo(name = "source")
    private final String mSource;

    @NonNull
    @ColumnInfo(name = "favorite")
    private final Integer mFavorite;


    public Proverb(@NonNull String proverb, @NonNull String interpretation, @NonNull String chinaProverb, String source, @NonNull Integer favorite) {
        this.mProverb = proverb;
        this.mInterpretation = interpretation;
        this.mChinaProverb = chinaProverb;
        this.mSource = source;
        this.mFavorite = favorite;
    }

    public Proverb(@NonNull String[] proverbSet) {
        this.mProverb = proverbSet[0];
        this.mInterpretation = proverbSet[1];
        this.mChinaProverb = proverbSet[2];
        this.mSource = proverbSet[3];
        this.mFavorite = 0;
    }

    @NonNull
    public String getProverb() {
        return this.mProverb;
    }

    public String getInterpretation() {
        return this.mInterpretation;
    }

    public String getChinaProverb() {
        return this.mChinaProverb;
    }

    public String getSource() {
        return this.mSource;
    }

    public Integer getFavorite() {
        return this.mFavorite;
    }
}
