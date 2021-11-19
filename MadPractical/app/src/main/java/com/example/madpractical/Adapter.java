package com.example.madpractical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<recyclerModel> studentList;
    private Context context;
    public Adapter(List<recyclerModel> studentList,Context context){
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        int imageview = studentList.get(position).getImageView();
        String name = studentList.get(position).getName();
        String address = studentList.get(position).getAddress();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentList.remove(position);
                notifyDataSetChanged();
            }
        });
        // for animation
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_row);
        Animation animation_fade = AnimationUtils.loadAnimation(context,R.anim.fade_scale);
        holder.imageView.startAnimation(animation);
        holder.itemView.startAnimation(animation_fade);
        // animation over

        holder.setData(imageview,name,address);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView name;
        private TextView address;
        private Button delete;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            delete = (Button) itemView.findViewById(R.id.delete);
        }

        public void setData(int imageview, String name, String address) {
            imageView.setImageResource(imageview);
            this.name.setText(name);
            this.address.setText(address);
        }
    }
}
