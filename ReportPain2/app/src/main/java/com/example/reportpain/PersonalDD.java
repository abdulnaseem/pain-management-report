package com.example.reportpain;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PersonalDD extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add;

    DiaryDatabase db;
    ArrayList<String> id, date, time, painLoc, painFeel, painLev, comm;
    CustomAdapter customAdapter;

    protected void onCreate(Bundle bundle){
        setTheme(R.style.PersonDD);
        super.onCreate(bundle);
        setContentView(R.layout.p_daily_diary);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        add = (FloatingActionButton) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PersonalDD.this, Entry.class);
                startActivity(i);
            }
        });

        db = new DiaryDatabase(PersonalDD.this);
        id = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();
        painLoc = new ArrayList<>();
        painFeel = new ArrayList<>();
        painLev = new ArrayList<>();
        comm = new ArrayList<>();

        arrData();

        customAdapter = new CustomAdapter(PersonalDD.this, this, id, date, time, painLoc,
                painFeel, painLev, comm);
        recyclerView.setAdapter(customAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonalDD.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        //displays the entry and organises it as newer entry first followed by old
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
        /*
         * This method simple refreshes the PersonalDD activity or
         * personal daily diary page.
         */
    }

    public void arrData(){
        Cursor cursor = db.readData();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "No Data.", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                //iterate through the table and add the relevant coloumns row within the arraylist
                id.add(cursor.getString(0));
                date.add(cursor.getString(1));
                time.add(cursor.getString(2));
                painLoc.add(cursor.getString(3));
                painFeel.add(cursor.getString(4));
                painLev.add(cursor.getString(5));
                comm.add(cursor.getString(6));
            }
        }
    }
}
