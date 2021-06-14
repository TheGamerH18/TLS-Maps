package main.tls_maps.ui.notes;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import main.tls_maps.MainActivity;
import main.tls_maps.R;
import main.tls_maps.NoteItems.NotesContent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {



    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    static RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotesFragment() {
        // Todo
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static NotesFragment newInstance(int columnCount) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new NotesRecyclerViewAdapter(MainActivity.notes.getITEMS(context), this));
            MainActivity.notes.recyclerview(recyclerView);
        }
        return view;
    }
}