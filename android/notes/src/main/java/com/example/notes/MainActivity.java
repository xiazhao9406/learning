package com.example.notes;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.models.Note;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "NotesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Note note = new Note("some title", "some content", "timestamp");
        Log.d(TAG, "onCreate : my note : " + note.toString());

    }
}