package com.eightkdevelopers.dodo;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
/**
 * Created by nidalouf on 3/8/2020.
 */
/*
 * Dodo Note ViewModel Class
 * For @Dodo Note App@
 * 8K-Developers {3/8/2020 at 7:00 PM}
 * */
public class DodoNoteViewModel extends AndroidViewModel {

    private DodoNoteRepository repository;
    private LiveData<List<DodoNote>> allNotes;

    public DodoNoteViewModel(@NonNull Application application) {
        super(application);
        repository = new DodoNoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(DodoNote note){
        repository.insert(note);
    }

    public void update(DodoNote note){
        repository.update(note);
    }

    public void delete(DodoNote note){
        repository.delete(note);
    }

    public void deleteAllNotes(){
        repository.deleteAllDodo();
    }

    public LiveData<List<DodoNote>> getAllNotes() {
        return allNotes;
    }
}
