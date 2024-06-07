package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.UserAddress;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class FirestoreUserAddressRepository {

    private final CollectionReference userAddressRef;

    public FirestoreUserAddressRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.userAddressRef = firestore.collection("user_addresses");
    }

    // Insert a new user address document
    public void insertUserAddress(UserAddress userAddress) throws InterruptedException, ExecutionException {
        DocumentReference userAddressDocRef = this.userAddressRef.add(userAddress).get();
        // Handle the write result if needed
    }

    // Update an existing user address document
    public void updateUserAddress(UserAddress userAddress) {
        DocumentReference userAddressDocRef = this.userAddressRef.document(String.valueOf(userAddress.getUserAddressId()));
        ApiFuture<WriteResult> writeResult = userAddressDocRef.set(userAddress);
        // Handle the write result if needed
    }

    // Delete a user address document by ID
    public void deleteUserAddress(String userAddressId) {
        DocumentReference userAddressDocRef = this.userAddressRef.document(String.valueOf(userAddressId));
        ApiFuture<WriteResult> writeResult = userAddressDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve a user address document by ID
    public UserAddress getUserAddressById(String userAddressId) throws ExecutionException, InterruptedException {
        DocumentReference userAddressDocRef = this.userAddressRef.document(String.valueOf(userAddressId));
        ApiFuture<DocumentSnapshot> userAddressFuture = userAddressDocRef.get();
        return userAddressFuture.get().toObject(UserAddress.class);
    }

    // Retrieve user addresses by user ID
    public List<UserAddress> getUserAddressesByUserId(String userId) throws ExecutionException, InterruptedException {
        Query query = this.userAddressRef.whereEqualTo("userId", userId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(UserAddress.class);
    }
}
