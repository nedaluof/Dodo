package com.eightkdevelopers.dodo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by nidalouf on 3/8/2020.
 */
/*
 * Dodo Note Repository Class
 * For @Dodo Note App@
 * 8K-Developers {3/8/2020 at 8:30 PM}
 * */


public class DodoNoteRepository {

    private DodoNoteDAO dodoNoteDAO;
    private LiveData<List<DodoNote>> allDodoNote;

    public DodoNoteRepository(Application application) {
        DodoNoteDatabase database = DodoNoteDatabase.getInstance(application);
        dodoNoteDAO = database.dodoNoteDAO();
        allDodoNote = dodoNoteDAO.getAllNote();
    }

    public void insert(DodoNote note) {
        new InsertDodoNoteAsyncTask(dodoNoteDAO).execute(note);
    }

    public void update(DodoNote note) {
        new UpdateDodoNoteAsyncTask(dodoNoteDAO).execute(note);
    }


    public void delete(DodoNote note) {
        new DeleteDodoNoteAsyncTask(dodoNoteDAO).execute(note);
    }

    public void deleteAllDodo() {
        new DeleteAllDodoNoteAsyncTask(dodoNoteDAO).execute();
    }

    public LiveData<List<DodoNote>> getAllNotes() {
        return allDodoNote;
    }


    private static class InsertDodoNoteAsyncTask extends AsyncTask<DodoNote, Void, Void>{
        private DodoNoteDAO dodoNoteDAO;

        private InsertDodoNoteAsyncTask(DodoNoteDAO dodoNoteDAO) {
            this.dodoNoteDAO = dodoNoteDAO;
        }

        @Override
        protected Void doInBackground(DodoNote... dodoNotes) {
            dodoNoteDAO.insert(dodoNotes[0]);
            return null;
        }
    }


    private static class UpdateDodoNoteAsyncTask extends AsyncTask<DodoNote, Void, Void >{
        private DodoNoteDAO dodoNoteDAO;

        private UpdateDodoNoteAsyncTask(DodoNoteDAO dodoNoteDAO) {
            this.dodoNoteDAO = dodoNoteDAO;
        }

        @Override
        protected Void doInBackground(DodoNote... dodoNotes) {
            dodoNoteDAO.update(dodoNotes[0]);
            return null;
        }
    }

    private static class DeleteDodoNoteAsyncTask extends AsyncTask<DodoNote, Void, Void> {
        private DodoNoteDAO dodoNoteDAO;

        private DeleteDodoNoteAsyncTask(DodoNoteDAO dodoNoteDAO) {
            this.dodoNoteDAO = dodoNoteDAO;
        }

        @Override
        protected Void doInBackground(DodoNote... dodoNotes) {
            dodoNoteDAO.delete(dodoNotes[0]);
            return null;
        }
    }

    private static class DeleteAllDodoNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private DodoNoteDAO dodoNoteDAO;

        private DeleteAllDodoNoteAsyncTask(DodoNoteDAO dodoNoteDAO) {
            this.dodoNoteDAO = dodoNoteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dodoNoteDAO.deleteAllDodo();
            return null;
        }
    }

}
