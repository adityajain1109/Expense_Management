package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.expensemanager.databinding.ActivityAddTransactionBinding;
import com.example.expensemanager.databinding.ActivityDashBoardBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddTransaction extends AppCompatActivity {

    ActivityAddTransactionBinding binding;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String type="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        binding.usrExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Expense";
                binding.usrExp.setChecked(true);
                binding.usrIncome.setChecked(false);
            }
        });

        binding.usrIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Income";
                binding.usrExp.setChecked(false);
                binding.usrIncome.setChecked(true);
            }
        });

        binding.usrAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = binding.usrAmount.getText().toString().trim();
                String note = binding.usrNote.getText().toString().trim();
                if(amount.length()<=0)
                {
                    return;
                }
                String id = UUID.randomUUID().toString();
                Map<String,Object> transaction=new HashMap<>();
                transaction.put("amount", amount);
                transaction.put("note", note);
                transaction.put("type", type);
                transaction.put("id", id);


                fstore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes").document(id).set(transaction)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddTransaction.this, "Added", Toast.LENGTH_SHORT).show();
                                binding.usrNote.setText("");
                                binding.usrAmount.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddTransaction.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }
}