package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.Cart;
import com.fooddelivery.finalprojectfredy.Data.Entity.Item;
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
public class FirestoreItemRepository {

    private final CollectionReference itemRef;

    public FirestoreItemRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.itemRef = firestore.collection("items");
    }

    // Insert a new item document
    public void insertItem(Item item) throws InterruptedException, ExecutionException {
        DocumentReference itemDocRef = this.itemRef.add(item).get();
        // Handle the write result if needed
    }

    // Update an existing item document
    public void updateItem(Item item) {
        DocumentReference itemDocRef = this.itemRef.document(String.valueOf(item.getItemId()));
        ApiFuture<WriteResult> writeResult = itemDocRef.set(item);
        // Handle the write result if needed
    }

    // Delete an item document by ID
    public void deleteItem(String itemId) {
        DocumentReference itemDocRef = this.itemRef.document(String.valueOf(itemId));
        ApiFuture<WriteResult> writeResult = itemDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve all item documents
    public List<Item> getAllItems() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = this.itemRef.get();
        return querySnapshot.get().toObjects(Item.class);
    }

    // Retrieve an item document by ID
    public Item getItemById(String itemId) throws ExecutionException, InterruptedException {
        DocumentReference itemDocRef = this.itemRef.document(String.valueOf(itemId));
        ApiFuture<DocumentSnapshot> itemFuture = itemDocRef.get();
        return itemFuture.get().toObject(Item.class);
    }

    // Retrieve items by name (case-insensitive search)
    public List<Item> getItemsByName(String name) throws ExecutionException, InterruptedException {
        Query query = this.itemRef
                .whereGreaterThanOrEqualTo("name", name.toLowerCase())
                .whereLessThanOrEqualTo("name", name.toLowerCase() + "\uf8ff")
                .orderBy("name");
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(Item.class);
    }

    // Retrieve items by category ID
    public List<Item> getItemsByCategory(String categoryId) throws ExecutionException, InterruptedException {
        Query query = this.itemRef.whereEqualTo("categoryId", categoryId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(Item.class);
    }

    // Retrieve items by type (e.g., vegetarian, vegan, etc.)
    public List<Item> getItemsByType(String type) throws ExecutionException, InterruptedException {
        Query query = this.itemRef.whereEqualTo("type", type.toLowerCase());
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(Item.class);
    }

    // Retrieve items by business ID
    public List<Item> getItemsByBusinessId(String businessId) throws ExecutionException, InterruptedException {
        Query query = this.itemRef.whereEqualTo("businessId", businessId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(Item.class);
    }

    // Retrieve business items by name (case-insensitive search)
    public List<Item> getBusinessItemsByName(String businessId, String name) throws ExecutionException, InterruptedException {
        Query query = this.itemRef
                .whereEqualTo("businessId", businessId)
                .whereGreaterThanOrEqualTo("name", name.toLowerCase())
                .whereLessThanOrEqualTo("name", name.toLowerCase() + "\uf8ff")
                .orderBy("name");
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(Item.class);
    }

    public List<Cart> getUserCartItemsByItemName(String userId, String itemName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserCartItemsByItemName'");
    }

    public List<Cart> getCartItemsByUserId(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCartItemsByUserId'");
    }
}
