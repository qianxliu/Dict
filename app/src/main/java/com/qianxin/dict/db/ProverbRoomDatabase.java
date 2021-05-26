package com.qianxin.dict.db;


/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.qianxin.dict.db.dao.ProverbDao;
import com.qianxin.dict.db.entity.Proverb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.  In a real
 * app, consider exporting the schema to help you with migrations.
 */

@Database(entities = {Proverb.class}, version = 2, exportSchema = false)
abstract class ProverbRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile ProverbRoomDatabase INSTANCE;
    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                //ProverbDao dao = INSTANCE.proverbDao();
                //dao.deleteAll();

                /*
                Proverb word = new Proverb("ああ言えばこう言う",
                        "相手の言うことに素直に従わず、一つ一つ理屈をつけて逆らうこと",
                        "倒着干；针锋相对；唇枪舌战",
                        "");

                dao.insert(word);
                */
            });
        }
    };

    static ProverbRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProverbRoomDatabase.class) {
                if (INSTANCE == null) {
                    /*
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProverbRoomDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                    */
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProverbRoomDatabase.class, "app_database")
                            .createFromAsset("proverb.db")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    abstract ProverbDao proverbDao();
}