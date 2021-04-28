package com.example.universitieslab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class UnivListActivity extends AppCompatActivity {

    //Переменная для работы с БД
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    ArrayList<University> universities = new ArrayList<University>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univ_list);

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

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
        // определяем слушателя нажатия элемента в списке
        UnivAdapter.OnUnivClickListener univClickListener = new UnivAdapter.OnUnivClickListener(){
            @Override
            public  void onUnivClick(University university, int position){
                Intent intent = new Intent(getApplicationContext(), UniversityDetailsActivity.class);
                intent.putExtra(University.class.getSimpleName(), university);
                startActivity(intent);
                // Toast.makeText(getApplicationContext(), "Обрано пункт " + university.getName(), Toast.LENGTH_SHORT).show();
            }
        };
        // создаем адаптер
        UnivAdapter adapter = new UnivAdapter(this, universities, univClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData(){
        Cursor cursor = mDb.rawQuery("SELECT * FROM universities ORDER BY worldrank", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Заполняем клиента
            University university = new University();
            university.setId(cursor.getInt(0));
            university.setName(cursor.getString(1));
            university.setCity(cursor.getString(2));
            university.setNumberOfStudents(cursor.getInt(3));
            university.setWorldrank(cursor.getInt(4));
            university.setImpactrank(cursor.getInt(5));
            university.setOpennessrank(cursor.getInt(6));
            university.setExcellencerank(cursor.getInt(7));
            university.setLng(cursor.getDouble(8));
            university.setLat(cursor.getDouble(9));

            universities.add(university);
            //Переходим к следующему клиенту
            cursor.moveToNext();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        universities.clear();

        setInitialData();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
        // определяем слушателя нажатия элемента в списке
        UnivAdapter.OnUnivClickListener univClickListener = new UnivAdapter.OnUnivClickListener(){
            @Override
            public  void onUnivClick(University university, int position){
                Intent intent = new Intent(getApplicationContext(), UniversityDetailsActivity.class);
                intent.putExtra(University.class.getSimpleName(), university);
                startActivity(intent);
                // Toast.makeText(getApplicationContext(), "Обрано пункт " + university.getName(), Toast.LENGTH_SHORT).show();
            }
        };
        // создаем адаптер
        UnivAdapter adapter = new UnivAdapter(this, universities, univClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    public void showCreate(View view){
        Intent intent = new Intent(this, CreateUniversityActivity.class);
        startActivity(intent);
    }
}