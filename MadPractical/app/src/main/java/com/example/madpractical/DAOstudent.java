package com.example.madpractical;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DAOstudent {
    private DatabaseReference databaseReference;

    public DAOstudent() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Student.class.getSimpleName());
    }

    public Task<Void> add(Student student) {
        return databaseReference.child(student.getId()).setValue(student);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return databaseReference.child(key).updateChildren(hashMap);
    }
    // we can use read operation from here also but somehow it's not working
    // due to main thread, we have run it using AsyncTask in separate thread


    /*public void read(List<Student> studentList){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    Student student = snap.getValue(Student.class);
                    studentList.add(new Student(student.getId(),student.getName(),student.getEmail()));
                    Log.d("FireBase",student.getName());
                    Log.d("FireBase",Integer.toString(studentList.size()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FireBase",error.toString());
            }
        });
        //Log.d("FireBase",studentList.get(0).getName());
    }*/
    public Task<Void> delete(String key) {
        return databaseReference.child(key).removeValue();
    }

}
