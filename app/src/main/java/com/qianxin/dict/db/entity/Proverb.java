package com.qianxin.dict.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "proverb_table")
public class Proverb {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "proverb")
    private String mProverb;

    @NonNull
    @ColumnInfo(name = "interpretation")
    private String mInterpretation;

    @NonNull
    @ColumnInfo(name = "chinaProverb")
    private String mChinaProverb;

    @ColumnInfo(name = "source")
    private String mSource;

    public Proverb(@NonNull String proverb, String interpretation, String chinaProverb, String source) {
        this.mProverb = proverb;
        this.mInterpretation = interpretation;
        this.mChinaProverb = chinaProverb;
        this.mSource = source;
    }

    public Proverb(@NonNull String[] proverbSet) {
        this.mProverb = proverbSet[0];
        this.mInterpretation = proverbSet[1];
        this.mChinaProverb = proverbSet[2];
        this.mSource = proverbSet[3];
    }

    @NonNull
    public String getProverb() {
        return this.mProverb;
    }


    public String[] getDetail() {
        return new String[]{
                this.mInterpretation,
                this.mChinaProverb,
                this.mSource
        };
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

}
