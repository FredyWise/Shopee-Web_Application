package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.BusinessAddress;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Repository
public class FirestoreBusinessAddressRepository {

    private final CollectionReference businessAddressRef;

    public FirestoreBusinessAddressRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.businessAddressRef = firestore.collection("business_addresses");
    }

    // Insert a new business address document
    public void insertBusinessAddress(BusinessAddress businessAddress) throws InterruptedException, ExecutionException {
        DocumentReference addressDocRef = this.businessAddressRef.add(businessAddress).get();
        // Handle the write result if needed
    }

    // Update an existing business address document
    public void updateBusinessAddress(BusinessAddress businessAddress) {
        DocumentReference addressDocRef = this.businessAddressRef.document(businessAddress.getBusinessAddressId());
        ApiFuture<WriteResult> writeResult = addressDocRef.set(businessAddress);
        // Handle the write result if needed
    }

    // Delete a business address document by ID
    public void deleteBusinessAddress(String businessAddressId) {
        DocumentReference addressDocRef = this.businessAddressRef.document(businessAddressId);
        ApiFuture<WriteResult> writeResult = addressDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve a business address document by business ID
    public BusinessAddress getBusinessAddressByBusinessId(String businessId) throws ExecutionException, InterruptedException {
        DocumentReference addressDocRef = this.businessAddressRef.document(businessId);
        ApiFuture<DocumentSnapshot> addressFuture = addressDocRef.get();
        return addressFuture.get().toObject(BusinessAddress.class);
    }

    // Retrieve a business address document by address line and business ID
    public BusinessAddress getBusinessAddressByAddressLine(String businessId, String geographicId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = this.businessAddressRef
                .whereEqualTo("businessId", businessId)
                .whereEqualTo("geographicId", geographicId)
                .limit(1)
                .get();
        List<BusinessAddress> addresses = query.get().toObjects(BusinessAddress.class);
        return addresses.isEmpty() ? null : addresses.get(0);
    }
}
