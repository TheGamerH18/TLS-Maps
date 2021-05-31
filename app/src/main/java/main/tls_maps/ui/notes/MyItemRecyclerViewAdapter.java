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

import main.tls_maps.placeholder.NotesContent.Note;
import main.tls_maps.databinding.FragmentNotesBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Note}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;

    public MyItemRecyclerViewAdapter(List<Note> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageButton mBtnView;
        public final LinearLayout mFrameView;
        public Note mItem;

        public ViewHolder(FragmentNotesBinding binding) {
            super(binding.getRoot());
            mFrameView = binding.NoteFrame;
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mBtnView = binding.imageButton;
            mBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View.OnClickListener delete = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mFrameView.removeAllViews();
                        }
                    };
                    Snackbar.make(v, "Wollen sie diese Notiz Löschen?", Snackbar.LENGTH_LONG).setBackgroundTint(Color.rgb(40,40,40)).setAction("Ok", delete).show();
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}