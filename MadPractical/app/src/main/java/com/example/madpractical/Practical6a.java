package com.example.madpractical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Practical6a extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Student> studentList = new ArrayList<>();
    Adapter_fire adapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical6a);
        initRecyclerView();
        dialog = new ProgressDialog(Practical6a.this);
        ReadData readData = new ReadData();
        readData.execute(new String[]{"http://192.168.43.217/MAD/readjson.php"});
    }

    private class ReadData extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Reading Data...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... data) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = db.getReference(Student.class.getSimpleName());
            try {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Student student = snap.getValue(Student.class);
                            studentList.add(student);
                            //Log.d("FireBase",student.getName());
                            Log.d("FireBase",Integer.toString(studentList.size()));
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Practical6a.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                });
            }catch (Exception e){
                Log.d("Error", e.toString());
                return false;
            }
            Log.d("Post FireBase", Integer.toString(studentList.size()));
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(Practical6a.this, "Read Data Success", Toast.LENGTH_LONG).show();
                Log.d("Post FireBase", Integer.toString(studentList.size()));
            } else {
                Toast.makeText(Practical6a.this, "Error in Fetching Data", Toast.LENGTH_LONG).show();
                Log.d("Error", "Error");
            }
            dialog.dismiss();
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview_fire);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter_fire(studentList, Practical6a.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

}