package com.project.intellifit_trainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoggedInActivity extends AppCompatActivity {

    TextView welcomeText;
    Button startWorkout, createWorkout, myWorkouts, workoutHistory, learnExercises, myProfile, support, logOut;
    Intent intent;
    FirebaseAuth mAuth;

    @Override
    protected void onResume() {
        super.onResume();
        // Check selected workout when returning to the activity
        checkSelectedWorkout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        mAuth = FirebaseAuth.getInstance();
        welcomeText = findViewById(R.id.loggedin_tv_welcomeText);
        startWorkout = findViewById(R.id.loggedin_bt_startworkout);
        createWorkout = findViewById(R.id.loggedin_bt_createworkout);
        myWorkouts = findViewById(R.id.loggedin_bt_myworkouts);
        workoutHistory = findViewById(R.id.loggedin_bt_workoutHistory);
        learnExercises = findViewById(R.id.loggedin_bt_learnExercises);
        myProfile = findViewById(R.id.loggedin_bt_myProfile);
        support = findViewById(R.id.loggedin_bt_support);
        logOut = findViewById(R.id.loggedin_bt_logOut);

        fetchUsername();
        checkSelectedWorkout();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoggedInActivity.this, SupportActivity.class);
                startActivity(intent);
            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoggedInActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });

        learnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoggedInActivity.this, LearnExercisesActivity.class);
                startActivity(intent);
            }
        });

        createWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoggedInActivity.this, CreateWorkoutActivity.class);
                startActivity(intent);
            }
        });

        myWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoggedInActivity.this, MyWorkoutsActivity.class);
                startActivity(intent);
            }
        });

        startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://server-mad-cb99.vercel.app/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    } // Add this closing brace to complete the onCreate() method

    private void fetchUsername() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.child("username").getValue(String.class);
                        welcomeText.setText("Welcome back, " + username + "!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("LoggedInActivity", "Error fetching username", databaseError.toException());
                }
            });
        }
    }

    private void checkSelectedWorkout() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userID = user.getUid();
            DatabaseReference selectedWorkoutRef = FirebaseDatabase.getInstance().getReference("users").child(userID).child("selected_workout");

            selectedWorkoutRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Enable the button and update its background if a selected workout exists
                        startWorkout.setEnabled(true);
                        startWorkout.setBackgroundResource(R.drawable.button_background);
                    } else {
                        // Disable the button and update its background if no selected workout exists
                        startWorkout.setEnabled(false);
                        startWorkout.setBackgroundResource(R.drawable.button_disabled);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("LoggedInActivity", "Error checking selected workout", databaseError.toException());
                }
            });
        }
    }
}
