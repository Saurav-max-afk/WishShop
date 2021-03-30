package com.saurav.wishshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SinupForm extends AppCompatActivity {
     EditText text_FullName,text_UserName,text_Email,text_Password,text_ConfirmPassword;
     Button btn_Register;
    RadioButton radioGenderMale,radioGenderFemale;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String gender="";
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinup_form);
        getSupportActionBar().setTitle("Signup Form");

        text_FullName=findViewById(R.id.text_FullName);
        text_UserName=findViewById(R.id.text_UserName);
        text_Email=findViewById(R.id.text_Email);
        text_Password=findViewById(R.id.text_Password);
        text_ConfirmPassword=findViewById(R.id.text_confirmPassword);
        btn_Register=findViewById(R.id.btn_Register);
        radioGenderMale=findViewById(R.id.radioGenderMale);
        radioGenderFemale=findViewById(R.id.radioGenderFemale);
        progressBar=findViewById(R.id.progressBar);

        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        firebaseAuth=FirebaseAuth.getInstance();

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName=text_FullName.getText().toString();
                String userName=text_UserName.getText().toString();
                String email=text_Email.getText().toString();
                String password=text_Password.getText().toString();
                String confirmPassword=text_ConfirmPassword.getText().toString();

                if (radioGenderMale.isChecked()){
                    gender="Male";
                }
                if (radioGenderFemale.isChecked())
                {
                    gender="Female";
                }
                if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)||TextUtils.isEmpty(confirmPassword))
                {
                    Toast.makeText(SinupForm.this, "Please Enter All the Fields", Toast.LENGTH_SHORT).show();
                }
                 else {
                    progressBar.setVisibility(View.VISIBLE);

                    if (password.equals(confirmPassword)) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SinupForm.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            SignUpModel information = new SignUpModel(fullName, userName, email, gender);
                                            FirebaseDatabase.getInstance().getReference("Student")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(SinupForm.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                }
                                            });
                                        } else {
                                            Toast.makeText(SinupForm.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });



    }
}