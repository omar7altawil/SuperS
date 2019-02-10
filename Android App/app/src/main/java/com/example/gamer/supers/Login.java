package com.example.gamer.supers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    // public FirebaseDatabase myFirebaseRef;
    private FirebaseAuth mAuth;
    com.google.android.gms.common.SignInButton signInButton;
    public GoogleApiClient googleApiClient;
    //LoginButton loginButton;
    public static final int RequestSignInCode = 7;
    // private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Userroot = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //https://oo10-507e2.firebaseapp.com/__/auth/handler
        mAuth = FirebaseAuth.getInstance();
        signInButton = (com.google.android.gms.common.SignInButton) findViewById(R.id.signin);
        //google
        // Creating and Configuring Google Sign In object.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(Login.this)
                .enableAutoManage(Login.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        // Adding Click listener to User Sign in Google button.
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignInMethod();

                Toast.makeText(Login.this,"Wait while we get your profile information",Toast.LENGTH_LONG).show();

            }
        });


    }

    // Sign In function Starts From Here.
    public void UserSignInMethod(){

        // Passing Google Api Client into Intent.
        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(AuthIntent, RequestSignInCode);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode){

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()){

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);
            }

        }
    }

    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(Login.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {

                        if (AuthResultTask.isSuccessful()){

                            // Getting Current Login user details.
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            // Setting up Email into TextView.
                            String email =  firebaseUser.getEmail().toString();
                            String name =firebaseUser.getDisplayName().toString();
                            String image = firebaseUser.getPhotoUrl().toString();
                            String id=firebaseUser.getUid();
                            User user=new User(email,name,image,id);
                            Moreinfo.setRequestedObj(user);
                            startActivity(new Intent(Login.this, Moreinfo.class));

                        }else {
                            Toast.makeText(Login.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
