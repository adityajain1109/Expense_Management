package com.example.expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransAdapt extends RecyclerView.Adapter<TransAdapt.MyViewHolder> {
    Context context;
    ArrayList<TransactionalModel> transactionalModelsArrayList;
    public TransAdapt(Context context, ArrayList<TransactionalModel> transactionalModelsArrayList)
    {
        this.context=context;
        this.transactionalModelsArrayList=transactionalModelsArrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransAdapt.MyViewHolder holder, int position) {
            TransactionalModel model = transactionalModelsArrayList.get(position);
            holder.amount.setText(model.getAmount());
            holder.date.setText(model.getDate());
            holder.note.setText(model.getNote());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView note,amount,date;
        View priority;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note=itemView.findViewById(R.id.note_one);
            amount=itemView.findViewById(R.id.amount_one);
            date=itemView.findViewById(R.id.date1);
            priority=itemView.findViewById(R.id.priority_one);

        }
    }
}



