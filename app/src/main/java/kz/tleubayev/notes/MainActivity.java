package kz.tleubayev.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notesRecycleView;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private MainViewModal viewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModal = ViewModelProviders.of(this).get(MainViewModal.class);
        notesRecycleView = findViewById(R.id.notesRecycleView);
        
        // Что бы убрать actionBar через коде
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        adapter = new NotesAdapter(notes); // объявляем адаптер и передаём ему массив
        notesRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getData();
        notesRecycleView.setAdapter(adapter);       // устанавливаем адаптер в RecycleView

        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() { // при нажатии вызывать анонимный класс
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "Клик", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClock(int position) {
                remove(position);
            }
        });

        // объект класса ItemTouch нужен для свайпа, SimpleCallback - простые свайпы
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {  // direction - направление свайпа
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(notesRecycleView); // объект для свайпа прикрепляем к RecycleView
    }

    private void remove(int position) {
        Note note = adapter.getNotes().get(position);        // определяем позицию элемента
        viewModal.deleteNote(note);
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        LiveData<List<Note>> notesFromDB = viewModal.getNotes();    // получаем заметки из БД
        notesFromDB.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesFromLiveData) {
//                notes.clear();                      // чистим массив
//                notes.addAll(notesFromLiveData);    // добавляем заметки в массив в массив
//                adapter.notifyDataSetChanged();     //- метод обновляет адаптер
                adapter.setNotes(notesFromLiveData);
            }
        });
    }

}