package com.eightkdevelopers.dodo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by nidalouf on 3/8/2020.
 */
/*
 * DAO interface
 * For @Dodo Note App@
 * 8K-Developers {3/8/2020 at 8:00 PM}
 * */
@Dao
public interface DodoNoteDAO {

    @Insert
    void insert(DodoNote note);

    @Update
    void update(DodoNote note);

    @Delete
    void delete(DodoNote note);

    @Query("DELETE FROM dodo_table")
    void deleteAllDodo();

    @Query("SELECT * FROM dodo_table ORDER BY priority DESC")
    LiveData<List<DodoNote>> getAllNote();
}
