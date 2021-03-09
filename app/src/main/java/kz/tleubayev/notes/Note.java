package kz.tleubayev.notes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// POJO - Plain Old Java Object - это объект который содержит поля и констуктор с гетерами и сетерами

@Entity(tableName = "notes")     // помечаем класс, связываем его с БД, в скобках - название таблицы
public class Note {             // создали класс для одной записи
    @PrimaryKey(autoGenerate = true)    // говорим что id - автогенерируется
    private int id;
    private String title;           // он будет принимать эти данные
    private String description;
    private int dayOfWeek;
    private int priority;

    // конструктор
    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    // что бы использовать этот класс в БД, в нем должен быть только один конструктор, остальные игнорируем
    @Ignore
    public Note(String title, String description, int dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }
    // этот констуктор нужен для того, что бы мы могли сами вручную добавить заметку, он без id

    //гетеры
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    // сетеры
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static String getDayAsString(int position) {
        switch (position) {
            case 1:
                return "Понедельник";
            case 2:
                return "Вторник";
            case 3:
                return "Среда";
            case 4:
                return "Четверг";
            case 5:
                return "Пятница";
            case 6:
                return "Суббота";
            default:
                return "Воскресенье";
        }
    }


}
