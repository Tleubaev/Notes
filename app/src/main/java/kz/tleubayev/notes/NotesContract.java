package kz.tleubayev.notes;

import android.provider.BaseColumns;

public class NotesContract {        // тут будут хранится все таблицы
    public static final class NotesEntry implements BaseColumns {   // создаём таблицу для наших заметок, наследуемся от колонок
        public static final String TABLE_NAME = "notes";    // переменная - название таблицы
        public static final String COLUMN_TITLE = "title";  // названия для заголовков таблиц
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
        public static final String COLUMN_PRIORITY = "priority";

        public static final String TYPE_TEXT = "TEXT";          // название для типа данных таблиц
        public static final String TYPE_INTEGER = "INTEGER";

        // переменная для ввода команды для SQL, добавление таблицы
        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE +
                " " + TYPE_TEXT + ", " + COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " + COLUMN_DAY_OF_WEEK +
                " " + TYPE_INTEGER + ", " + COLUMN_PRIORITY + " " + TYPE_INTEGER + ")";
        // удаление таблицы
        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
        
    }
}
