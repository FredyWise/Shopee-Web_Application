package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class FirestoreUserRepository {

    private final CollectionReference userRef;

    public FirestoreUserRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.userRef = firestore.collection("users");
    }

    // Insert a new user document
    public void insertUser(User user) throws InterruptedException, ExecutionException {
        DocumentReference userRef = this.userRef.add(user).get();
        
    }

    // Update an existing user document
    public void updateUser(User user) {
        DocumentReference userRef = this.userRef.document(user.getUserId());
        ApiFuture<WriteResult> writeResult = userRef.set(user);
        
    }

    // Delete a user document by ID
    public void deleteUser(String userId) {
        DocumentReference userRef = this.userRef.document(userId);
        ApiFuture<WriteResult> writeResult = userRef.delete();
        
    }

    // Retrieve a user document by ID
    public User getUserById(String userId) throws ExecutionException, InterruptedException {
        DocumentReference userRef = this.userRef.document(userId);
        ApiFuture<DocumentSnapshot> userFuture = userRef.get();
        if(userFuture.get().exists()) { 
            return userFuture.get().toObject(User.class);
        } else return null;
    }

    // Retrieve a user document by email
    public User getUserByEmail(String email) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = this.userRef
                .whereEqualTo("email", email)
                .limit(1)
                .get();
        List<User> users = query.get().toObjects(User.class);
        return users.isEmpty() ? null : users.get(0);
    }

    // Retrieve a user document by username
    public User getUserByUsername(String username) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = this.userRef
                .whereEqualTo("username", username)
                .get();
                
        QuerySnapshot querySnapshot = query.get();
        System.out.println("Current user: " + querySnapshot);
        List<User> users = querySnapshot.toObjects(User.class);
        System.out.println("Current user: " + users);
        return users.isEmpty() ? null : users.get(0);
    }
}
