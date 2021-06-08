package main.tls_maps.ui.notes;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;

import main.tls_maps.MainActivity;
import main.tls_maps.NoteItems.NotesContent;
import main.tls_maps.NoteItems.NotesContent.Note;
import main.tls_maps.databinding.FragmentNotesBinding;

import java.util.Calendar;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Note}.
 */
public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;
    private NotesFragment fragment;

    public NotesRecyclerViewAdapter(List<Note> items, NotesFragment frag) {
        mValues = items;
        fragment = frag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mId.setText(mValues.get(position).id);
        mValues.get(position).id = ""+ position;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
        public final TextView mContentView;
        public final TextView mId;
        public final ImageButton mBtnView;
        public final ImageButton mBtnViewReminder;
        public final LinearLayout mFrameView;
        public Note mItem;
        private Calendar cl;

        public ViewHolder(FragmentNotesBinding binding) {
            super(binding.getRoot());
            mFrameView = binding.NoteFrame;
            mId = binding.itemNumber;
            mContentView = binding.content;
            mBtnView = binding.imageButton;
            mBtnViewReminder = binding.imageButton2;
            mBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View.OnClickListener delete = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.notes.removeItem(""+mId.getText(), v.getContext());
                            notifyDataSetChanged();
                        }
                    };
                    Snackbar.make(v, "Wollen sie diese Notiz Löschen?", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.rgb(40,40,40))
                            .setAction("Ja", delete).show();
                }
            });
            mBtnViewReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(ViewHolder.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH));
                    dpd.show(fragment.getChildFragmentManager(), "test");
                    dpd.dismissOnPause(true);
                }
            });
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar now = Calendar.getInstance();
            TimePickerDialog tpd = TimePickerDialog.newInstance(ViewHolder.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true);
            tpd.show(fragment.getChildFragmentManager(), "test");
            tpd.dismissOnPause(true);
            cl.set(Calendar.YEAR, year);
            cl.set(Calendar.MONTH, monthOfYear);
            cl.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }

        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            cl.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cl.set(Calendar.MINUTE, minute);
            cl.set(Calendar.SECOND, second);
        }
    }
}