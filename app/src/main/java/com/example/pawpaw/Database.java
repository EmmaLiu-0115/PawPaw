package com.example.pawpaw;

import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    FirebaseFirestore db;

    public Database(){
        db = FirebaseFirestore.getInstance();
    }

    //TODO: Write and read to database
}
