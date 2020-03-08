package com.eightkdevelopers.dodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eightkdevelopers.dodo.utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
/**
 * Created by nidalouf on 3/8/2020.
 */
public class MainActivity extends AppCompatActivity {

    private DodoNoteViewModel dodoNoteViewModel;
    private CoordinatorLayout layout;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.coordinator_layout);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, Constants.getAddNoteRequest());
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        dodoNoteViewModel = ViewModelProviders.of(this).get(DodoNoteViewModel.class);
        dodoNoteViewModel.getAllNotes().observe(this, new Observer<List<DodoNote>>() {
            @Override
            public void onChanged(List<DodoNote> dodoNotes) {
                //Update recyclerView
                adapter.setNotes(dodoNotes);
            }
        });

        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                dodoNoteViewModel.delete(adapter.getDodoNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);*/

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DodoNote note) {

                Intent intent = new Intent(MainActivity.this , AddEditNoteActivity.class);
                intent.putExtra(Constants.getExtraId(),note.getId());
                intent.putExtra(Constants.getExtraTitle() , note.getTitle());
                intent.putExtra(Constants.getExtraDescription() , note.getDescription());
                intent.putExtra(Constants.getExtraPriority(), note.getPriority());
                startActivityForResult(intent,Constants.getEditNoteRequest());

            }
        });

        enableSwipeToDeleteAndUndo();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.getAddNoteRequest() && resultCode == RESULT_OK) {

            String title = data.getStringExtra(Constants.getExtraTitle());
            String description = data.getStringExtra(Constants.getExtraDescription());
            int priority = data.getIntExtra(Constants.getExtraPriority(), 1);
            DodoNote note = new DodoNote(title, description, priority);
            dodoNoteViewModel.insert(note);

            Toast.makeText(this, "Dodo Note saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == Constants.getEditNoteRequest()&& resultCode == RESULT_OK){

            int id = data.getIntExtra(Constants.getExtraId(),-1);
            if(id == -1){
                Toast.makeText(this, "Note Can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(Constants.getExtraTitle());
            String description = data.getStringExtra(Constants.getExtraDescription());
            int priority = data.getIntExtra(Constants.getExtraPriority(), 1);
            DodoNote note  =new DodoNote(title,description,priority);
            note.setId(id);
            dodoNoteViewModel.update(note);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();


        } else  {
            Toast.makeText(this, "Dodo Note not saved", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_note:
                dodoNoteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final DodoNote current = adapter.getNoteAt(position);
                final DodoNote item = current;

                //adapter.removeItem(position);
                //dodoNoteViewModel.delete(item);

                dodoNoteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Snackbar snackbar = Snackbar
                        .make(layout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dodoNoteViewModel.insert(item);
                        //adapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
