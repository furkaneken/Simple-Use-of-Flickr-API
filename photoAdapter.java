package com.example.getrecentphotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class photoAdapter extends RecyclerView.Adapter<photoAdapter.NewsViewHolder> {


    List<photoClass> imgs;
    Context context;
    itemClickLister listener;

    public photoAdapter(List<photoClass> imgs, Context context , itemClickLister listener) {
        this.imgs = imgs;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.photos_row_layout , parent , false);









        return new NewsViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {



        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemclicked(imgs.get(position));

            }
        });


        if (imgs.get(position).getBitmap()== null){

            new ImageDownloadTask(holder.img).execute(imgs.get(position));


        }
        else{
            holder.img.setImageBitmap(imgs.get(position).getBitmap());
        }



    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }


    public interface itemClickLister{

        public void itemclicked(photoClass selectednewitem);


    }






    class NewsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        ConstraintLayout root;





        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.listimage);
            root = itemView.findViewById(R.id.container);



        }
    }














}

