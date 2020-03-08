package com.eightkdevelopers.dodo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Created by nidalouf on 3/8/2020.
 */
/*
* Entity Class
* For @Dodo Note App@
* 8K-Developers {3/8/2020 at 8:00 PM}
* */

@Entity(tableName = "dodo_table")
public class DodoNote {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int priority;

    public DodoNote(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DodoNote)) return false;
        DodoNote note = (DodoNote) o;
        return getId() == note.getId() &&
                getPriority() == note.getPriority() &&
                getTitle().equals(note.getTitle()) &&
                getDescription().equals(note.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getPriority());
    }
}
