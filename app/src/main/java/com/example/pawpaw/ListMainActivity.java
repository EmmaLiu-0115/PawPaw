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
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListMainActivity extends AppCompatActivity {

    ListView listView;
    String[] LocationName = {"Memory Union","Bascon","School of Bussiness","Van Valk","Gym"};
    int[] LocationImage = {R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        listView = findViewById(R.id.listview);
        CursorAdapter cursorAdapter = new CursorAdapter();

        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),ListdataActivity.class);
                intent.putExtra("name",LocationName[i]);
                intent.putExtra("image", LocationImage[i]);
                startActivity(intent);

            }
        });
    }
    private class CursorAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return LocationName.length;
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
            TextView name = view1.findViewById(R.id.Locations);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(LocationName[i]);
            image.setImageResource(LocationImage[i]);
            return view1;
        }
    }

}
