package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.Business;
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
public class FirestoreBusinessRepository {

    private final CollectionReference businessRef;

    public FirestoreBusinessRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.businessRef = firestore.collection("business");
    }

    // Insert a new business document
    public void insertBusiness(Business business) throws InterruptedException, ExecutionException {
        DocumentReference businessDocRef = this.businessRef.add(business).get();
        // Handle the write result if needed
    }

    // Update an existing business document
    public void updateBusiness(Business business) {
        DocumentReference businessDocRef = this.businessRef.document(business.getBusinessId());
        ApiFuture<WriteResult> writeResult = businessDocRef.set(business);
        // Handle the write result if needed
    }

    // Delete a business document by ID
    public void deleteBusiness(String businessId) {
        DocumentReference businessDocRef = this.businessRef.document(businessId);
        ApiFuture<WriteResult> writeResult = businessDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve all business documents
    public List<Business> getAllBusinesses() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = this.businessRef.get();
        return querySnapshot.get().toObjects(Business.class);
    }

    // Retrieve a business document by ID
    public Business getBusinessById(String businessId) throws ExecutionException, InterruptedException {
        DocumentReference businessDocRef = this.businessRef.document(businessId);
        ApiFuture<DocumentSnapshot> businessFuture = businessDocRef.get();
        return businessFuture.get().toObject(Business.class);
    }

    // Retrieve businesses by name (case-insensitive search)
    public List<Business> getBusinessByName(String businessName) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = this.businessRef
                .whereGreaterThanOrEqualTo("name", businessName.toLowerCase())
                .whereLessThanOrEqualTo("name", businessName.toLowerCase() + "\uf8ff")
                .orderBy("name")
                .get();
        return query.get().toObjects(Business.class);
    }

    // Retrieve a business document by owner ID
    public Business getBusinessByOwnerId(String ownerId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = this.businessRef.whereEqualTo("ownerId", ownerId).get();
        List<Business> businesses = querySnapshot.get().toObjects(Business.class);
        return businesses.isEmpty() ? null : businesses.get(0);
    }
}
