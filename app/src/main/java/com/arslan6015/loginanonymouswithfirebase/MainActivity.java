package com.arslan6015.loginanonymouswithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView loginMessage;
    private ProgressBar progressBar;
    private Button loginAnonymousbutton, linkAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //initialization
        loginMessage = findViewById(R.id.loginMessage);
        progressBar = findViewById(R.id.progressBar);
        loginAnonymousbutton = findViewById(R.id.loginAnonymousbutton);
        linkAccountButton = findViewById(R.id.linkAccountButton);

loginAnonymousbutton.setOnClickListener(new View.OnClickListener() {        //insert a listener on button that checks whether the button is clicked
        @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);

        if (currentUser == null){                                       //check if the user is new then signIn anonymously
                mAuth.signInAnonymously().                                 //.signInAnonymously is a method provided by Firebase
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {        //insert a Listener that listen
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)          // insert a method that will executes when the process is completed
                        {
                            if (task.isSuccessful())                    // check the required task is completed successfully
                            {
                                loginMessage.setVisibility(View.VISIBLE);       //visible the text
                                progressBar.setVisibility(View.INVISIBLE);       //inVisible the progressBar
                            }
                        }
                    })
                        .addOnFailureListener(new OnFailureListener() {         //if the signin failed
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TAG",e.getMessage());            //return error in logs
                            }
                        });
                }
                else                                            //check if the user is not new
                {
                    progressBar.setVisibility(View.INVISIBLE);          //invisible the progress bar
                    loginMessage.setVisibility(View.VISIBLE);           //visible the Text
                    loginMessage.setText("you are already login anonymously!!!");           //set the text
                }
            }
        });

        linkAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LinkAccount.class); //use intent to send the user to LinkAccount activity
                startActivity(intent);
            }
        });

    }
}
