package com.example.pawpaw;

import android.util.Log;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;


import java.util.ArrayList;
import java.util.List;


public class Database {
    FirebaseFirestore db;
    private static final String TAG = "Database";

    private AddALocation aal;
    private AccountPage ap;
    private AddFriend af;
    private GetUserProfileLoc loc;
    private Location ll;
    private ListMainActivity lma;


    /*public Database(AddALocation aal){
        db = FirebaseFirestore.getInstance();
        this.aal = aal;
    }*/

    public Database(AccountPage ap){
        this.ap = ap;
        db = FirebaseFirestore.getInstance();
    }

    public Database(GetUserProfileLoc loc){
        db = FirebaseFirestore.getInstance();
        this.loc = loc;
    }

    public Database(AddFriend af){
        db = FirebaseFirestore.getInstance();
        this.af = af;
    }

    public Database(AddALocation aal){
        this.aal = aal;
        db = FirebaseFirestore.getInstance();
    }


    //TODO: Write and read to database

    //LOCATION
    /*
    Add location to database
    */
    public void addLocationToDB (Location location) {
        Log.i("Database", "add location to DB");
        db.collection("locations");
        db.collection("locations").document(location.getLocationID()).set(location);
    }


    /*
    Get location from database
    */
    public void getLocationFromDB (String locationID){
        Log.d(TAG, "in getLocationFromDB method");
        DocumentReference docRef = db.collection("locations").document(locationID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Location location = documentSnapshot.toObject(Location.class);
                //ll.setLocationType(location.getLocationType());

                aal.getLocationData(location);
                //TODO: Call the function which uses location info from the other class

                String id = location.getLocationID();
                Log.d(TAG, id);
            }
        });

    }

    /*
    Update String type field in location from database
    */
    public void updateLocationInDB (String document, String field, String value){
        db.collection("locations").document(document).update(
            field, value
        );
    }

    /*
    Update int type field in location from database
    */
    public void updateLocationInDB (String document, String field, double value){
        db.collection("locations").document(document).update(
                field, value
        );
    }

    /*
    Update reviewedUsers field in location from database
    */
    public void updatereviewedUsersInDB (String document, String value){
        DocumentReference Ref = db.collection("locations").document(document);

        // Automatically add a new photo address to the "photos" array field.
        Ref.update("reviewedUsers", FieldValue.arrayUnion(value));
    }

    /*
    Update reviewedUsers field in location from database
    */
    public void updatereviewedUsersContentInDB (String document, String value){
        DocumentReference Ref = db.collection("locations").document(document);

        // Automatically add a new photo address to the "photos" array field.
        Ref.update("reviewedUsersContent", FieldValue.arrayUnion(value));
    }


    /*
    Update photo address field in location from database
    */
    public void updateLocationPhotosInDB (String document, String value){
        DocumentReference Ref = db.collection("locations").document(document);

        // Automatically add a new photo address to the "photos" array field.
        Ref.update("photos", FieldValue.arrayUnion(value));
    }

    /*
    Delete photo address field in location from database
    */
    public void deleteLocationPhotosInDB (String document, String value){
        DocumentReference Ref = db.collection("locations").document(document);

        // Atomically remove a photo address from the "photos" array field.
        Ref.update("photos", FieldValue.arrayRemove(value));
    }

    /*
    Delete location from database
    */
    public void deleteLocationFromDB(String document){
        db.collection("locations").document(document)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Location successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting location", e);
                    }
                });
    }


    //USER
    /*
    Add user to database
     */
    public void addUserToDB (User user) {
        db.collection("users").document(user.getUserID()).set(user);
        db.collection("friends").document(user.getUserID()).set(new Friend(user.getUserID(),new ArrayList<String>()));
    }

    /*
    Get user from database
    */
    public void getUserFromDB (String userID){
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);


                Log.d(TAG, "Successfully get user from database");
            }
        });
    }

    /*
   Update String type field in user from database
   */
    public void updateUserInDB (String document, String field, String value){
        db.collection("users").document(document).update(
                field, value
        );
    }

    /*
    Update int type field in user from database
    */
    public void updateUserInDB (String document, String field, int value){
        db.collection("users").document(document).update(
                field, value
        );
    }

    /*
    Delete user from database
    */
    public void deleteUserFromDB(String document){
        db.collection("users").document(document)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting user", e);
                    }
                });
    }

    //FRIEND
    /*
    Add a new friend relationship to friend database
    */
    public void addFriendToDB (String user1ID, String user2ID){
        DocumentReference ref = db.collection("friends").document(user1ID);

        // Atomically add a new region to the "user2IDs" array field.
        ref.update("user2IDs", FieldValue.arrayUnion(user2ID));
    }


    /*
    Get friend relationships from friend database
    */
    public void getFriendsFromDB (String user1ID){
        DocumentReference docRef = db.collection("friends").document(user1ID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Friend friend = documentSnapshot.toObject(Friend.class);

                //TODO: Call the function which uses friend info from the other class


                Log.d(TAG, "Successfully get friends from database");
            }
        });
    }

    /*
    Delete a friendship
    */
    public void deleteAFriendInDB (String user1ID, String user2ID){
        DocumentReference Ref = db.collection("friends").document(user1ID);

        // Atomically remove a friend from the "user2IDs" array field.
        Ref.update("user2IDs", FieldValue.arrayRemove(user2ID));
    }

    //REVIEWS
    public void addReviewsToDB (Reviews reviews) {
        db.collection("reviews").document().set(reviews);
    }

    /*
    Get personal location reviews for my account page
    */
    public void getReviewsForAccountPage (String userID){
        final List<Reviews> result = new ArrayList<>();
        db.collection("reviews")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.add(document.toObject(Reviews.class));


                                //TODO: Call the function which uses reviews info from the other class

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /*
    Get all location reviews for my location page
    */
    public void getReviewsForLocationPage (String locationID){
        final List<Reviews> result = new ArrayList<>();
        db.collection("reviews")
                .whereEqualTo("locationID", locationID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.add(document.toObject(Reviews.class));
                                //TODO: Call the function which uses reviews info from the other class

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
