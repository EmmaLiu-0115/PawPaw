package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListMainActivity extends AppCompatActivity {

    ListView listView;
    String[] fruitNames = {"Location1","Location2","Location3","Location4","Location5s"};
    int[] fruitImages = {R.drawable.list,R.drawable.logo,R.drawable.contact,R.drawable.mess,R.drawable.ic_launcher_background};
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        listView = findViewById(R.id.listview);
        back= findViewById(R.id.button4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMainActivity.this, MapHomePage.class));
            }
        });
        CursorAdapter cursorAdapter = new CursorAdapter();

        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),ListdataActivity.class);
                intent.putExtra("name",fruitNames[i]);
                intent.putExtra("image", fruitImages[i]);
                startActivity(intent);

            }
        });
    }
    private class CursorAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return fruitNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            TextView name = view1.findViewById(R.id.fruits);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(fruitNames[i]);
            image.setImageResource(fruitImages[i]);
            return view1;
        }
    }

}
