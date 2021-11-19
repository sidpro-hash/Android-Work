package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Practical2d extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<recyclerModel> studentList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical3);

        initData();
        initRecyclerView();
    }

    private void initData() {
        studentList = new ArrayList<>();
        studentList.add(new recyclerModel(R.drawable.sid1,"Siddharth","Gundiyavada, Sayla, Surendranagar, Gujarat, 363440"));
        studentList.add(new recyclerModel(R.drawable.img3,"Dhruvi","Sun Plaza, Navrangpura, Ahmedabad, Gujarat 380009"));
        studentList.add(new recyclerModel(R.drawable.img2,"Johny","Jodhpur 4 Rasta, Satellite, Ahmedabad, Gujarat 380015"));
        studentList.add(new recyclerModel(R.drawable.img1,"Apexa","Shefali House, Ellisbridge, Ahmedabad, Gujarat 380006"));
        studentList.add(new recyclerModel(R.drawable.co_512,"Collegeek","80 Feet Road, Anand Bagh, Surendranagar, Gujarat 363001"));
        studentList.add(new recyclerModel(R.drawable.img4,"Ajay","31nd Floor, Ghanshyam Chambers, Mithakhali, Ahmedabad, Gujarat 380006"));

        // only for animation
        studentList.add(new recyclerModel(R.drawable.sid1,"Siddharth","Gundiyavada, Sayla, Surendranagar, Gujarat, 363440"));
        studentList.add(new recyclerModel(R.drawable.img3,"Dhruvi","Sun Plaza, Navrangpura, Ahmedabad, Gujarat 380009"));
        studentList.add(new recyclerModel(R.drawable.img2,"Johny","Jodhpur 4 Rasta, Satellite, Ahmedabad, Gujarat 380015"));
        studentList.add(new recyclerModel(R.drawable.img1,"Apexa","Shefali House, Ellisbridge, Ahmedabad, Gujarat 380006"));
        studentList.add(new recyclerModel(R.drawable.co_512,"Collegeek","80 Feet Road, Anand Bagh, Surendranagar, Gujarat 363001"));
        studentList.add(new recyclerModel(R.drawable.img4,"Ajay","31nd Floor, Ghanshyam Chambers, Mithakhali, Ahmedabad, Gujarat 380006"));
        studentList.add(new recyclerModel(R.drawable.sid1,"Siddharth","Gundiyavada, Sayla, Surendranagar, Gujarat, 363440"));
        studentList.add(new recyclerModel(R.drawable.img3,"Dhruvi","Sun Plaza, Navrangpura, Ahmedabad, Gujarat 380009"));
        studentList.add(new recyclerModel(R.drawable.img2,"Johny","Jodhpur 4 Rasta, Satellite, Ahmedabad, Gujarat 380015"));
        studentList.add(new recyclerModel(R.drawable.img1,"Apexa","Shefali House, Ellisbridge, Ahmedabad, Gujarat 380006"));
        studentList.add(new recyclerModel(R.drawable.co_512,"Collegeek","80 Feet Road, Anand Bagh, Surendranagar, Gujarat 363001"));
        studentList.add(new recyclerModel(R.drawable.img4,"Ajay","31nd Floor, Ghanshyam Chambers, Mithakhali, Ahmedabad, Gujarat 380006"));
        studentList.add(new recyclerModel(R.drawable.sid1,"Siddharth","Gundiyavada, Sayla, Surendranagar, Gujarat, 363440"));
        studentList.add(new recyclerModel(R.drawable.img3,"Dhruvi","Sun Plaza, Navrangpura, Ahmedabad, Gujarat 380009"));
        studentList.add(new recyclerModel(R.drawable.img2,"Johny","Jodhpur 4 Rasta, Satellite, Ahmedabad, Gujarat 380015"));
        studentList.add(new recyclerModel(R.drawable.img1,"Apexa","Shefali House, Ellisbridge, Ahmedabad, Gujarat 380006"));
        studentList.add(new recyclerModel(R.drawable.co_512,"Collegeek","80 Feet Road, Anand Bagh, Surendranagar, Gujarat 363001"));
        studentList.add(new recyclerModel(R.drawable.img4,"Ajay","31nd Floor, Ghanshyam Chambers, Mithakhali, Ahmedabad, Gujarat 380006"));
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(studentList,getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return true;
    }
}