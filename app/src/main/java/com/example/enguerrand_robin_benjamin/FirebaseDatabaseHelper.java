package com.example.enguerrand_robin_benjamin;


import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


class FirebaseDatabaseHelper {
    final String errorInvalidPassword = "Invalid password";
    final String errorUserNotFound = "User not found";
    final String errorUnknown = "Database unknown error";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
    }

    private void isUserExist(final String userName, Callback found, Callback fail) {
        DatabaseReference ref = mReference.child("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    User user = childSnapshot.getValue(User.class);
                    if (userName.equals(user.name)) {
                        found.run(user);
                        return;
                    }
                }
                fail.run(errorUserNotFound);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    void insertThisQuizz(Quizz quizz, Callback success, Callback error) {
        DatabaseReference ref = mReference.child("quizz");
        ref.push().setValue(quizz, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be saved " + databaseError.getMessage());
                error.run(databaseError.getMessage());
            } else {
                System.out.println("Data saved successfully.");
                success.run(true);
            }
        });
    }

    void getAllQuizz(Callback success, Callback error) {
        DatabaseReference ref = mReference.child("quizz");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Quizz> quizz = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    System.out.println(childSnapshot);
                    Quizz item = childSnapshot.getValue(Quizz.class);
                    quizz.add(item);
                }
                success.run(quizz);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                error.run(errorUnknown);
            }
        });
    }

    private void insertThisUser(User user, Callback success, Callback error) {
        DatabaseReference ref = mReference.child("users");
        ref.push().setValue(user, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be saved " + databaseError.getMessage());
                error.run(databaseError.getMessage());
            } else {
                System.out.println("Data saved successfully.");
                success.run(true);
            }
        });
    }

    void register(User user, Callback success, Callback error) {
        isUserExist(user.name, error, (item) -> insertThisUser(user, success, error));

    }

    void login(User user, Callback success, Callback error) {
        isUserExist(user.name, (item) -> {
            User dbUser = (User) item;
            if (dbUser.password.equals(user.password))
                success.run(item);
            else
                error.run(errorInvalidPassword);
        }, error);
    }
}
