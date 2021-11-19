package com.example.madpractical;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.List;

public class Adapter_fire extends RecyclerView.Adapter<Adapter_fire.ViewHolder> {

    private List<Student> studentList;
    private Context context;

    public Adapter_fire(List<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_fire.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_firebase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_fire.ViewHolder holder, int position) {
        String id = studentList.get(position).getId();
        String name = studentList.get(position).getName();
        String email = studentList.get(position).getEmail();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = studentList.get(position).getId();
                DAOstudent dao = new DAOstudent();
                dao.delete(id).addOnSuccessListener(suc -> {
                    Toast.makeText(context, "Record is Deleted", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(context, er.getMessage(), Toast.LENGTH_LONG).show();
                });
                // due to addValueEventListener it will add all value again in list
                // to remove duplicate we clear list first than notify change
                studentList.clear();
                notifyDataSetChanged();
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);

                // get context : pain in ass
                LayoutInflater inflater = LayoutInflater.from(context);
                // get layout from inflater
                View dialog_layout_view = inflater.inflate(R.layout.dialog_layout_fire, null);
                // to recognize student by click on recycler view
                String id = studentList.get(position).getId();

                EditText name = (EditText) dialog_layout_view.findViewById(R.id.name);
                EditText email = (EditText) dialog_layout_view.findViewById(R.id.email);

                Button btn_cancel = (Button) dialog_layout_view.findViewById(R.id.btn_cancel);
                Button btn_okay = (Button) dialog_layout_view.findViewById(R.id.btn_okay);

                // insert layout into dialog box
                alert.setView(dialog_layout_view);
                // create dialog box
                final AlertDialog alertDialog = alert.create();
                // around area : false
                alertDialog.setCanceledOnTouchOutside(false);

                // on cancel
                btn_cancel.setOnClickListener(v1 -> alertDialog.dismiss());
                // on okay
                btn_okay.setOnClickListener(v1 -> {
                    String Name = name.getText().toString();
                    String Email = email.getText().toString();

                    if (Name.isEmpty() || Email.isEmpty()) {
                        Toast.makeText(context, "Student ALL info is required!", Toast.LENGTH_LONG).show();
                    } else {
                        DAOstudent daOstudent = new DAOstudent();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("name", Name);
                        hashMap.put("email", Email);
                        daOstudent.update(id, hashMap).addOnSuccessListener(suc -> {
                            Toast.makeText(context, "Student Info Updated Successfully", Toast.LENGTH_LONG).show();
                        }).addOnFailureListener(er -> {
                            Toast.makeText(context, er.getMessage(), Toast.LENGTH_LONG).show();
                        });
                        // due to addValueEventListener it will add all value again in list
                        // to remove duplicate we clear list first than notify change
                        studentList.clear();
                        notifyDataSetChanged();
                    }
                    alertDialog.dismiss();
                });

                alertDialog.show();

                //studentList.remove(position);
                //notifyDataSetChanged();
            }
        });
        holder.setData(id, name, email);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView name;
        private TextView email;
        private Button delete;
        private Button update;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.contact_id);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            delete = (Button) itemView.findViewById(R.id.delete);
            update = (Button) itemView.findViewById(R.id.update);
        }

        public void setData(String id, String name, String email) {
            this.id.setText(id);
            this.name.setText(name);
            this.email.setText(email);
        }
    }
}
