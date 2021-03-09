package kz.tleubayev.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {
    // DAO - data access object (объект доступа к данным)
    @Query("SELECT * FROM notes ORDER BY dayOfWeek ASC")    // что бы метод вызывался при запроса к базе, ASC - по возрастанию, DESC - по убыванию
    LiveData<List<Note>> getAllNotes();                     // LiveData - для того что бы прослеживать изменения

    @Insert
    void insertNote(Note note); // для добавления

    @Delete
    void deleteNote(Note note); // для удаления

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
