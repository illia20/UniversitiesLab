package com.example.universitieslab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void showList(View view){
        Intent intent = new Intent(this, UnivListActivity.class);
        startActivity(intent);
    }

    public void showSelection(View view){
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    public void showMinMax(View view){
        Intent intent = new Intent(this, CalcActivity.class);
        startActivity(intent);
    }

    public void showContacts(View view){
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }
}