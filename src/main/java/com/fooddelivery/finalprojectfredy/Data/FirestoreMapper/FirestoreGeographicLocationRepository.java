package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.GeographicLocation;
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
public class FirestoreGeographicLocationRepository {

    private final CollectionReference locationRef;

    public FirestoreGeographicLocationRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.locationRef = firestore.collection("geographic_locations");
    }

    // Insert a new geographic location document
    public void insertGeographicLocation(GeographicLocation location) throws InterruptedException, ExecutionException {
        DocumentReference locationDocRef = this.locationRef.add(location).get();
        // Handle the write result if needed
    }

    // Update an existing geographic location document
    public void updateGeographicLocation(GeographicLocation location) {
        DocumentReference locationDocRef = this.locationRef.document(String.valueOf(location.getGeographicId()));
        ApiFuture<WriteResult> writeResult = locationDocRef.set(location);
        // Handle the write result if needed
    }

    // Delete a geographic location document by ID
    public void deleteGeographicLocation(String geographicId) {
        DocumentReference locationDocRef = this.locationRef.document(String.valueOf(geographicId));
        ApiFuture<WriteResult> writeResult = locationDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve a geographic location document by ID
    public GeographicLocation getGeographicLocationById(String geographicId) throws ExecutionException, InterruptedException {
        DocumentReference locationDocRef = this.locationRef.document(String.valueOf(geographicId));
        ApiFuture<DocumentSnapshot> locationFuture = locationDocRef.get();
        return locationFuture.get().toObject(GeographicLocation.class);
    }

    // Retrieve a geographic location ID by city, state, and country
    public String getGeographicLocationIdByCityStateCountry(String city, String state, String country) throws ExecutionException, InterruptedException {
        Query query = this.locationRef
                .whereEqualTo("city", city)
                .whereEqualTo("state", state)
                .whereEqualTo("country", country)
                .limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<GeographicLocation> locations = querySnapshot.get().toObjects(GeographicLocation.class);
        return locations.isEmpty() ? null : String.valueOf(locations.get(0).getGeographicId());
    }

    // Retrieve all geographic location documents
    public List<GeographicLocation> getAllGeographicLocations() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = this.locationRef.get();
        return querySnapshot.get().toObjects(GeographicLocation.class);
    }
}
