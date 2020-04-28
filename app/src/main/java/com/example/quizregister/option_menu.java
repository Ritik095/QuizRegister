package com.example.quizregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class option_menu extends AppCompatActivity {
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu);
        fAuth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about:
                Toast.makeText(getApplicationContext(), "Work is in progress", Toast.LENGTH_LONG).show();
                return true;
            case R.id.contact:
                Toast.makeText(getApplicationContext(), "Contact Us Selected", Toast.LENGTH_LONG).show();
                startActivity(new Intent(option_menu.this,contact.class));
                return true;
            case R.id.gallery:
                Toast.makeText(getApplicationContext(), "Work is in progress", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                fAuth.signOut();
                finish();
                startActivity(new Intent(option_menu.this,login.class));
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}