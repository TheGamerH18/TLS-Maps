package main.tls_maps;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import main.tls_maps.NoteItems.NotesContent;
import main.tls_maps.databinding.ActivityMainBinding;
import main.tls_maps.databinding.FragmentAddNoteBinding;

public class MainActivity extends AppCompatActivity {

    public static NotesContent notes;

    AppBarConfiguration mAppBarConfiguration;
    ActivityMainBinding binding;
    FragmentAddNoteBinding overMenuAddNote;
    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0,0);

        notes = new NotesContent(getApplicationContext());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;

        // Open the add Notes Overlay
        overMenuAddNote = binding.appBarMain.addNote;

        // Input Methode Manager to close the Keyboard
        imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);

        // and add the Click Listener
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overMenuAddNote.getRoot().setVisibility(View.VISIBLE);
                binding.appBarMain.fab.setVisibility(View.GONE);
            }
        });


        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void send(View view) {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if(!overMenuAddNote.inputFieldNotes.getText().toString().isEmpty())
            notes.addItem(overMenuAddNote.inputFieldNotes.getText().toString(), getApplicationContext());
        overMenuAddNote.getRoot().setVisibility(View.GONE);
        binding.appBarMain.fab.setVisibility(View.VISIBLE);
        overMenuAddNote.inputFieldNotes.setText("");
    }

    public void stop(View view) {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        overMenuAddNote.inputFieldNotes.setText("");
        overMenuAddNote.getRoot().setVisibility(View.GONE);
        binding.appBarMain.fab.setVisibility(View.VISIBLE);
    }
}