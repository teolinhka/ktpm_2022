package com.frsarker.todotask;

import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;

public class Change_Password extends AppCompatActivity {
    private EditText mPasswordField;
    private Button mChangePasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mPasswordField = (EditText) findViewById(R.id.etConfirmPassword);
        mChangePasswordButton = (Button) findViewById(R.id.btn_change_password);

        mAuth = FirebaseAuth.getInstance();

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPasswordField.getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(Change_Password.this, "Please enter a new password", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth.getInstance().getCurrentUser().updatePassword(password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@Nullable Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Change_Password.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Change_Password.this, "Password update failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
