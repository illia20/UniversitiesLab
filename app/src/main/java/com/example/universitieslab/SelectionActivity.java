package com.example.universitieslab;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    //Переменная для работы с БД
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    ArrayList<String> universitiesNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        setInitialData();

        makeList();
    }

    private void setInitialData(){
        Cursor cursor = mDb.rawQuery("SELECT name FROM universities WHERE excellencerank < 5000 AND city != 'Kyiv'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String university = cursor.getString(0);
            universitiesNames.add(university);
            //Переходим к следующему
            cursor.moveToNext();
        }
    }

    private void makeList(){
        TableLayout tableLayout = findViewById(R.id.selectionTable);
        for(int i = 0; i < universitiesNames.size(); i++) {
            TableRow row = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setPadding(10, 10, 10, 10);
            textView.setText(universitiesNames.get(i));
            row.addView(textView);
            tableLayout.addView(row);
        }
    }
}