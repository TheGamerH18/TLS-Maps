package main.tls_maps;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.navigation.NavigationView;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import main.tls_maps.databinding.ActivityMainBinding;
import main.tls_maps.databinding.FragmentAddNoteBinding;
import main.tls_maps.NoteItems.NotesContent;

public class MainActivity extends AppCompatActivity {

    AppBarConfiguration mAppBarConfiguration;
    ActivityMainBinding binding;
    FragmentAddNoteBinding overMenuAddNote;
    InputMethodManager imm;
    NotificationManager NM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotesContent.init(getApplicationContext());
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

/*        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        NM.notify(0, mBuilder.build()); */


        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        overMenuAddNote = binding.appBarMain.addNote;
        imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overMenuAddNote.getRoot().setVisibility(View.VISIBLE);
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
        if(overMenuAddNote.inputFieldNotes.getText().toString() != "")
            NotesContent.addItem(new NotesContent.Note(overMenuAddNote.inputFieldNotes.getText().toString()), getApplicationContext());
        overMenuAddNote.getRoot().setVisibility(View.GONE);
        overMenuAddNote.inputFieldNotes.setText("");
    }

    public void stop(View view) {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        overMenuAddNote.inputFieldNotes.setText("");
        overMenuAddNote.getRoot().setVisibility(View.GONE);
    }
}