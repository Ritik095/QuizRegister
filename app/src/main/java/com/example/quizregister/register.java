package com.example.quizregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    // private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$");
    EditText fname, femail, fcontact, fpassword, fre_pass;
    RadioButton male,female,trans;
    Button register;
    TextView click;
    ImageView gmail, google, fb;
    FirebaseAuth fAuth;
    ProgressBar progress;
    FirebaseFirestore fStore;
    String userID;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        femail = (EditText) findViewById(R.id.email);
        fpassword = (EditText) findViewById(R.id.password);
        fname = (EditText) findViewById(R.id.name);
        fcontact = (EditText) findViewById(R.id.contact);
        fre_pass = (EditText) findViewById(R.id.repass);
        google = (ImageView) findViewById(R.id.google);
        gmail = (ImageView) findViewById(R.id.gmail);
        fb = (ImageView) findViewById(R.id.fb);
        click = (TextView) findViewById(R.id.click);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        trans = (RadioButton) findViewById(R.id.trans);
        register = (Button) findViewById(R.id.register);

        fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),login.class));
            finish();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gender = "";
                final String email = femail.getText().toString().trim();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String password = fpassword.getText().toString().trim();
                final String name = fname.getText().toString().trim();
                final String phone = fcontact.getText().toString().trim();


//name
                if (TextUtils.isEmpty(name)){
                    fname.setError("Please Enter Full Name....");
                }
//for email matching
                if (email.matches(emailPattern)){
                    femail.setError("Please Enter Valid Email..");
                //}

                //else {
                  //  if (TextUtils.isEmpty(email)) {
                     //   femail.setError("Please Enter email..");
                    //}
                }
//for password
                if (TextUtils.isEmpty(password)) {
                    fpassword.setError("Please enter password....");

                }
                if (password.length() <= 5) {
                    Toast.makeText(getApplicationContext(), "Password must >5 digite", Toast.LENGTH_SHORT).show();
                }
//phone
                if (TextUtils.isEmpty(phone)){
                    fcontact.setError("Enter phone number..");
                }
                if (phone.length()<10 && phone.length()>12){
                    fcontact.setError("Invalid Mobile Number...");
                }
//radiobutton
                if (male.isChecked()){
                    gender="Male";
                }
                if (female.isChecked()){
                    gender="Female";
                }
                if (trans.isChecked()){
                    gender="Transgender";
                }
                progress.setVisibility(View.VISIBLE);

//firebaseAuth
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registerd Successfully", Toast.LENGTH_SHORT).show();
                            userID= fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fname",name);
                            user.put("femail",email);
                            user.put("fcontact",phone);
                            documentReference.set("user").addOnSuccessListener((new OnSuccessListener<Void>() {

                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "User profile created.. " + userID);

                                }
                            })).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailoure"+ e.toString());
                                }
                            });

                            Intent in = new Intent(register.this,login.class);
                            startActivity(in);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Registerd failed..", Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });
            }

        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(register.this,login.class);
                startActivity(in);
            }
        });
    }
}
