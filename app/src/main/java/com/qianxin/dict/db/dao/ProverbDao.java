package com.qianxin.dict.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.qianxin.dict.db.entity.Proverb;

import java.util.List;

@Dao
public interface ProverbDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Proverb word);

    @Query("DELETE FROM proverb_table")
    void deleteAll();

    @Query("SELECT * FROM proverb_table ORDER BY proverb ASC")
    LiveData<List<Proverb>> getAlphabetizedWords();

    // || == +
    @Query("SELECT * FROM proverb_table WHERE proverb LIKE '%' || :proverb  || '%' ORDER BY proverb ASC")
    LiveData<List<Proverb>> searchAllProverbs(String proverb);

    @Query("SELECT * FROM proverb_table WHERE proverb LIKE :proverb  || '%' ORDER BY proverb ASC")
    LiveData<List<Proverb>> searchDictProverbs(String proverb);

    @Query("SELECT * FROM proverb_table WHERE favorite = 1 ORDER BY proverb ASC")
    LiveData<List<Proverb>> getFavoriteProverbs();

    @Query("SELECT * FROM proverb_table WHERE favorite = 1 AND proverb LIKE '%' || :proverb  || '%' ORDER BY proverb ASC")
    LiveData<List<Proverb>> searchFavoriteProverbs(String proverb);
}
