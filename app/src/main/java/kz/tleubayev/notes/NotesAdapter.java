package kz.tleubayev.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// класс адаптера который наследуем от Adapter в который передаем созданный внутри ViewHolder(class NotesViewHolder)
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notes;  // массив заметок
    private OnNoteClickListener onNoteClickListener;    // создаём объект слушателя

    interface OnNoteClickListener { // слушатель шелчков на элементы
        void onNoteClick(int position); // принимает позицию элемента
        void onLongClock(int position);
    }

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {   // добавили сетер слушателя
        this.onNoteClickListener = onNoteClickListener;
    }

    public NotesAdapter(ArrayList<Note> notes) {    // конструктор с массивом
        this.notes = notes;
    }

    @NonNull
    @Override
    // В методе onCreateViewHolder нужно указать макет для отдельного элемента списка
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater?
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    // В методе onBindViewHolder связываем используемые текстовые вьюшки с данными
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);        // получаем заметку из массива, в качестве параметра передаем позицию элемента
        holder.titleTextView.setText(note.getTitle());  // у NotesViewHolder получаем элемент, и ставим текст из заметки
        holder.descriptionTextView.setText(note.getDescription());
        holder.dayOfWeekTextView.setText(Note.getDayAsString(note.getDayOfWeek() + 1));
        int colorId;
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorId = holder.itemView.getResources().getColor(R.color.red);
                break;
            case 2:
                colorId = holder.itemView.getResources().getColor(R.color.yellow);
                break;
            default:
                colorId = holder.itemView.getResources().getColor(R.color.green);
                break;
        }
        holder.titleTextView.setBackgroundColor(colorId);
    }

    @Override
    // При работе с массивом мы можем просто вычислить его длину и передать это значение методу адаптера
    public int getItemCount() {     // этот метод возвращает количество элементов массиве
        return notes.size();
    }

    // Класс служит для оптимизации ресурсов. Нужно просто перечислить используемые компоненты из макета для отдельного элемента списка
    class NotesViewHolder extends RecyclerView.ViewHolder {     // создаём класс который наследуется от ViewHolder

        // объявляем нужные нам ссылки
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView dayOfWeekTextView;

        // конструктор родительского класса который будет содержать все видимые части ViewHolder
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            // т.к. мы не находимся в активности, мы не можем на прямую объявить объект, поэтому объявляем его у параметра itemView
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dayOfWeekTextView = itemView.findViewById(R.id.dayOfWeekTextView);

            // у ItemView можно вызват слушатель событий
            itemView.setOnClickListener(new View.OnClickListener() {    // слушатель событий нами созданного интерфейса
                @Override
                public void onClick(View v) {   // у него есть только один метод onClick, вызывается при нажатии
                    if (onNoteClickListener != null) {  // проверка существует ли объект нашего слушателя
                        onNoteClickListener.onNoteClick(getAdapterPosition());  // getAdapterPosition определяет позицию элемента
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onNoteClickListener != null) {
                        onNoteClickListener.onLongClock(getAdapterPosition());
                    }
                    return true;    // если сделать false, то сработают оба метода, быстрого и медленного нажатия
                }
            });
        }
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return notes;
    }
}
