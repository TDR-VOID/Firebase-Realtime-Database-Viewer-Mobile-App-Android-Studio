package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView countTextView, ldrTextView, pirTextView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countTextView = findViewById(R.id.countTextView);
        ldrTextView = findViewById(R.id.ldrTextView);
        pirTextView = findViewById(R.id.pirTextView);

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("test");

        // Read data from Firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get values from dataSnapshot
                    Long countValue = dataSnapshot.child("Count").getValue(Long.class);
                    Long ldrValue = dataSnapshot.child("LDR Output").getValue(Long.class);
                    String pirValue = dataSnapshot.child("PIR Output").getValue(String.class);

                    // Check for null values before using them
                    if (countValue != null) {
                        long count = countValue;
                        countTextView.setText("Count: " + count);
                    } else {
                        countTextView.setText("Count: N/A");
                    }

                    if (ldrValue != null) {
                        long ldr = ldrValue;
                        ldrTextView.setText("LDR Output: " + ldr);
                    } else {
                        ldrTextView.setText("LDR Output: N/A");
                    }

                    if (pirValue != null) {
                        pirTextView.setText("PIR Output: " + pirValue);
                    } else {
                        pirTextView.setText("PIR Output: N/A");
                    }
                } else {
                    // Handle case where data doesn't exist
                    countTextView.setText("Count: N/A");
                    ldrTextView.setText("LDR Output: N/A");
                    pirTextView.setText("PIR Output: N/A");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }
        });
    }
}