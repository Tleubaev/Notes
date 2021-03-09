package kz.tleubayev.notes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModal extends AndroidViewModel {   // создаём класс ViewModal

    private static NotesDatabase database;

    private LiveData<List<Note>> notes;

    public MainViewModal(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(getApplication());     // вызываем БД
        notes = database.notesDao().getAllNotes();                  // для получения из базы данных
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insertNote(Note note) {
        new InsertTask().execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteTask().execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllTask().execute();
    }

    private static class InsertTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                database.notesDao().insertNote(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                database.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... notes) {
            database.notesDao().deleteAllNotes();
            return null;
        }
    }

}
