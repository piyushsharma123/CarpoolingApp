package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class driver_lists extends AppCompatActivity {

    DatabaseReference refrence;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_lists);

        listview = findViewById(R.id.list_driver);

        final HashMap<String, String> nameAddresses = new HashMap<>();
        final List<HashMap<String, String>> listItems = new ArrayList<>();
        final SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_specification,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.textView1, R.id.textView2});

        refrence = FirebaseDatabase.getInstance().getReference().child("Users");
        refrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Object> users = (Map<String,Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> entry : users.entrySet()){

                    //Get user map

                    Map singleUser = (Map) entry.getValue();
                    //Get phone field and append to list

                    if(singleUser.get("role").toString().equals("0")||singleUser.get("role").toString().equals("1")){
                        Log.v("inner", "piyush");
                        nameAddresses.put(singleUser.get("name").toString(),singleUser.get("phone").toString());
                    }
                }
                Iterator it = nameAddresses.entrySet().iterator();
                while (it.hasNext())
                {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    Log.v("inner", "poorvi");
                    resultsMap.put("First Line", pair.getKey().toString());
                    resultsMap.put("Second Line", pair.getValue().toString());
                    listItems.add(resultsMap);
                }
                listview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}