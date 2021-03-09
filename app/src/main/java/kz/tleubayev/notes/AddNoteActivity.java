package kz.tleubayev.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Spinner daysOfWeekSpinner;
    private RadioGroup radioGroupPriority;

    private MainViewModal viewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModal = ViewModelProviders.of(this).get(MainViewModal.class);
        ActionBar actionBar = getSupportActionBar();        // что бы убрать actionBar в коде
        if (actionBar != null) {
            actionBar.hide();
        }

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.editTextDescription);
        daysOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

    }

    public void onClickSaveNote(View view) {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        int dayOfWeek = daysOfWeekSpinner.getSelectedItemPosition();    // позиция начинается с нуля
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();   // определяем id выбранной кнопки
        RadioButton radioButton = findViewById(radioButtonId);      // объявляем radioButton с выбранной id
        int priority = Integer.parseInt(radioButton.getText().toString());  // принимаем текст в виде int из radioButton
//        MainActivity.notes.add(note);   // добавили в массив - Удалили
        if (isFill(title, description)) {
            Note note = new Note(title, description, dayOfWeek, priority);
            viewModal.insertNote(note);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning_add_note, Toast.LENGTH_SHORT).show();
        }
    }

    // Проверка на заполненность строк Title и Description пользователем
    private boolean isFill(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
}