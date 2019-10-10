package com.example.enguerrand_robin_benjamin;


import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirebaseDatabaseHelper {
    final String errorInvalidPassword = "Invalid password";
    final String errorUserNotFound = "User not found";
    final String errorUnknown = "Database unknown error";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public FirebaseDatabaseHelper() {
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
                    user.dataBaseId = childSnapshot.getKey();
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

    public void deleteThisQuizz(Quizz quizz, Callback success, Callback error) {
        DatabaseReference ref = mReference.child("quizz");
        Map<String, Object> map = new HashMap<>();
        String quizzKey = quizz.dataBaseId;
        if (quizzKey == null) {
            error.run("Quizz id is null");
            return;
        }
        String key = quizzKey;
        map.put(key, null);
        ref.updateChildren(map, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be deleted " + databaseError.getMessage());
                error.run(databaseError.getMessage());
            } else {
                System.out.println("Data saved successfully.");
                success.run(true);
            }
        });
    }

    public void deleteThisUser(User user, Callback success, Callback error) {
        DatabaseReference ref = mReference.child("quizz");
        Map<String, Object> map = new HashMap<>();
        String userKey = user.dataBaseId;
        if (userKey == null) {
            error.run("Quizz id is null");
            return;
        }
        String key = userKey;
        map.put(key, null);
        ref.updateChildren(map, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be deleted " + databaseError.getMessage());
                error.run(databaseError.getMessage());
            } else {
                System.out.println("Data saved successfully.");
                success.run(true);
            }
        });
    }

    public void insertThisQuizz(Quizz quizz, Callback success, Callback error) {
        DatabaseReference ref = mReference.child("quizz");
        Map<String, Object> map = new HashMap<>();
        String quizzKey = quizz.dataBaseId;
        String key = quizzKey != null ? quizzKey : ref.push().getKey();
        quizz.dataBaseId = null;
        map.put(key, quizz);
        ref.updateChildren(map, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be saved " + databaseError.getMessage());
                error.run(databaseError.getMessage());
            } else {
                System.out.println("Data saved successfully.");
                success.run(true);
            }
        });
    }

    public void getAllQuizz(Callback success, Callback error) {
        DatabaseReference ref = mReference.child("quizz");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Quizz> quizz = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Quizz item = childSnapshot.getValue(Quizz.class);
                    item.dataBaseId = childSnapshot.getKey();
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

    public void getAllUser(Callback success, Callback error) {
        DatabaseReference ref = mReference.child("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> quizz = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    User item = childSnapshot.getValue(User.class);
                    item.dataBaseId = childSnapshot.getKey();
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


        Map<String, Object> map = new HashMap<>();
        String userKey = user.dataBaseId;
        String key = userKey != null ? userKey : ref.push().getKey();
        user.dataBaseId = null;
        map.put(key, user);
        ref.updateChildren(map, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be saved " + databaseError.getMessage());
                error.run(databaseError.getMessage());
            } else {
                System.out.println("Data saved successfully.");
                success.run(true);
            }
        });
    }

    public void register(User user, Callback success, Callback error) {
        isUserExist(user.name, error, (item) -> insertThisUser(user, success, error));

    }

    public void login(User user, Callback success, Callback error) {
        isUserExist(user.name, (item) -> {
            User dbUser = (User) item;
            if (dbUser.password.equals(user.password))
                success.run(item);
            else
                error.run(errorInvalidPassword);
        }, error);
    }
}
