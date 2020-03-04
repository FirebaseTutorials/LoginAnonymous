package com.arslan6015.loginanonymouswithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LinkAccount extends AppCompatActivity {

    private EditText email,password;
    private Button signInButton;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_account);

        //initialization
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();

    if (currentUser!= null)
    {
        if (!mEmail.isEmpty() || !mPassword.isEmpty())  //checking email,password editText are not empty
        {
            AuthCredential credential = EmailAuthProvider.getCredential(mEmail,mPassword);
            currentUser.linkWithCredential(credential)              //linkWithCredential is a method provided by Firebase just to link the credentials
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                                {
                                    Toast.makeText(LinkAccount.this,"account linked",Toast.LENGTH_SHORT).show();
                                }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG2",e.getMessage());
                        }
                    });
        }
    }

    }
    });
    }
}
