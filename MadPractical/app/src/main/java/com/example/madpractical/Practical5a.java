package com.example.madpractical;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Practical5a extends AppCompatActivity implements View.OnClickListener {

    ListView listStudent;
    ImageView refresh;
    ArrayList<String> list_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical5a);
        refresh = findViewById(R.id.reload_icon);
        listStudent = findViewById(R.id.list_view);
        refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ReadData readData = new ReadData();
        //readData.execute(new String[]{"https://collegeek.com/"});
        readData.execute(new String[]{"http://192.168.43.217/MAD/readjson.php"});
    }

    private class ReadData extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog = new ProgressDialog(Practical5a.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Reading Data...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... data) {
            InputStream stream;
            for (String data1 : data) {
                try {
                    URL url = new URL(data1);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();

                    stream = con.getInputStream();

                    BufferedReader reader;
                    String text = "";

                    reader = new BufferedReader(new InputStreamReader(stream, "iso-8859-1"), 8);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        text += line + "\n";
                    }
                    //Log.d("Data",text);
                    stream.close();

                    list_student = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(text);
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        list_student.add(jsonObject.getString("en_no"));
                        list_student.add(jsonObject.getString("firstname"));
                        list_student.add(jsonObject.getString("email"));
                        list_student.add(jsonObject.getString("city"));
                        list_student.add(" ");
                    }


                } catch (Exception e) {
                    Log.d("Error", e.toString());
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(Practical5a.this, "Read Data Success", Toast.LENGTH_LONG).show();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Practical5a.this, R.layout.simple_list, list_student);
                listStudent.setAdapter(arrayAdapter);
            } else {
                Log.d("Error", "Error");
            }
            dialog.dismiss();
        }
    }
}