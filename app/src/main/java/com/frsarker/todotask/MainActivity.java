package com.frsarker.todotask;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;
    private DatabaseReference mDatabase;
    FirebaseFirestore fstore;
    TextView txt_hello,txt_name;
    ImageView img1,img2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        txt_hello = findViewById(R.id.hellotxt);
        txt_name = findViewById(R.id.nametxt);
        img1 = findViewById(R.id.img_user);
        img2 = findViewById(R.id.notification_img);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser user = mAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        mDatabase.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        txt_name.setText(String.valueOf(dataSnapshot.child("Name").getValue()));
                    }

                }
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.miHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.miCalendar:
                        return true;

                    case R.id.Task:
                        Intent intent = new Intent(getApplicationContext(),TaskList.class);
                        startActivity(intent);
                        intent.putExtra("userId",user.getUid());
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miHome:
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent1);
                        intent1.putExtra("userId",user.getUid());
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.miSettings:
                        Intent intent2 = new Intent(getApplicationContext(),AccountSettingsActivity.class);
                        startActivity(intent2);
                        intent2.putExtra("userId",user.getUid());
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.AddTask:
                        Intent intent3 = new Intent(getApplicationContext(),AddModifyTask_Company.class);
                        startActivity(intent3);
                        intent3.putExtra("userId",user.getUid());
                        overridePendingTransition(0,0);

                        return true;
                }
                return false;
            }
        });
    }

//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user == null){
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        }
//    }
    public void AddTask(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fstore.collection("User").document(user.getUid());
        documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if( value.getLong("Role") == 1){
                    startActivity(new Intent(getApplicationContext(),AddModifyTask_Company.class));
                }
                else{

                }
//                Intent intent = new Intent(LoginActivity.this,Welcome.class);
//                startActivity(intent);
//                intent.putExtra("userId",user.getUid());
            }
        });
//        startActivity(new Intent(getApplicationContext(),AddModifyTask_Company.class));
//        finish();
    }






}
