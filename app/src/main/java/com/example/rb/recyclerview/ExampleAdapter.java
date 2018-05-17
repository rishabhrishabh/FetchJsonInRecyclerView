package com.example.rb.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;

    public ExampleAdapter(Context context,ArrayList<ExampleItem> ExampleList)
    {
        super();
        this.mContext = context;
        this.mExampleList = ExampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.singleitem, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, final int position) {
        ExampleItem currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String name= currentItem.getName() ;
        int verify = currentItem.getVerify();

        holder.name.setText(name);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExampleList.remove(position);
                notifyDataSetChanged();
            }
        });
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.personimage);
        Log.d("hello", name + " " + String.valueOf(verify));
        if (verify==1)
        {
            Log.d("hello", "hello");
            holder.verif.setChecked(true);
            holder.verif.setSelected(true);
        }

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{
    public ImageView personimage;
    public TextView name;
        public CheckBox verif;
        LinearLayout linearLayout;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            personimage=itemView.findViewById(R.id.pimage);
            name=itemView.findViewById(R.id.pname);
            verif=itemView.findViewById(R.id.pverify);
            linearLayout=itemView.findViewById(R.id.single_item);
        }
    }




}
