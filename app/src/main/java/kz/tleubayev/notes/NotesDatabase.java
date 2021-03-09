package kz.tleubayev.notes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// создаём БД
@Database(entities = {Note.class}, version = 1, exportSchema = false)   // помечаем базу данных как БД
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase database;              // создаём объект класса
    private static final String DB_NAME = "notes2.db";  // имя новой базы данных
    private static final Object LOOK = new Object();    // создаём объект

    // SINGLETON - шаблон проектирования

    public static NotesDatabase getInstance(Context context) {      // создаём метод создания новой БД
        // для того что бы разные запросы в один момент не могли, взять базу данных, делается синхронизация по объекту
        synchronized (LOOK) {
            if (database == null) { // если БД не существует
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME)
//                        .allowMainThreadQueries()
                        // даём возможность обращатся к БД в главном потоке(ТАК НЕЛЬЗЯ ДЕЛАТЬ В РЕАЛЬНОМ ПРИЛОЖЕНИИ!), только для тестов
                        .build();     // создаём БД
            }
            return database;  // возвращаём созданную БД
        }
    }

    public abstract NotesDao notesDao();    // получаем объект интерфейса NotesDao
}
