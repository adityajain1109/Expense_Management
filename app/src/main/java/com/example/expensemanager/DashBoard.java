package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.expensemanager.databinding.ActivityDashBoardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity
{
    ActivityDashBoardBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    ArrayList<TransactionalModel> transactionalModelArrayList;
    TransAdapt transAdapt;
    int sumExpenses=0;
    int sumIncome=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        transactionalModelArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        binding.recyclerView.setHasFixedSize(true);

        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoard.this, AddTransaction.class));
            }
        });


        loadData();

    }

    private void loadData()
    {
        firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot ds:task.getResult()){
                            TransactionalModel model = new TransactionalModel(
                                    ds.getString("id"),
                                    ds.getString("note"),
                                    ds.getString("amount"),
                                    ds.getString("type"),
                                    ds.getString("date"));
                            int amount = Integer.parseInt(ds.getString("amount"));
                            if(ds.getString("type").equals("Expense"))
                            {
                                    sumExpenses=sumExpenses+amount;

                            }
                            else{
                                sumIncome=sumIncome+amount;
                            }
                            transactionalModelArrayList.add(model);
                        }
                        binding.totalIncome.setText(String.valueOf(sumIncome));
                        binding.totalExpense.setText(String.valueOf(sumExpenses));
                        binding.balance.setText(String.valueOf(sumIncome-sumExpenses));
                        transAdapt = new TransAdapt(DashBoard.this, transactionalModelArrayList);
                        binding.recyclerView.setAdapter(transAdapt);
                    }
                });
    }

}