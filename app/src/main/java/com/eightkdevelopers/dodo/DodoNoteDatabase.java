package com.eightkdevelopers.dodo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
/**
 * Created by nidalouf on 3/8/2020.
 */
/*
 * Dodo Note Database Singleton Class
 * For @Dodo Note App@
 * 8K-Developers {3/8/2020 at 10:06 PM}
 * */

@Database(entities = {DodoNote.class}, version = 1 , exportSchema = false)
public abstract class DodoNoteDatabase extends RoomDatabase {

    private static DodoNoteDatabase instance;

    public abstract DodoNoteDAO dodoNoteDAO();

    public static synchronized DodoNoteDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DodoNoteDatabase.class, "dodo_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private DodoNoteDAO dodoNoteDAO;

        private PopulateDbAsyncTask(DodoNoteDatabase database){
            dodoNoteDAO = database.dodoNoteDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
