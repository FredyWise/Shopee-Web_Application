package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.Cart;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.WriteResult;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class FirestoreCartRepository {

    private final CollectionReference cartRef;

    public FirestoreCartRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.cartRef = firestore.collection("cart");
    }

    // Insert a new cart item document
    public void insertCartItem(Cart cart) throws InterruptedException, ExecutionException {
        DocumentReference cartDocRef = this.cartRef.add(cart).get();
        // Handle the write result if needed
    }

    // Update an existing cart item document
    public void updateCartItem(Cart cart) {
        DocumentReference cartDocRef = this.cartRef.document(String.valueOf(cart.getCartId()));
        ApiFuture<WriteResult> writeResult = cartDocRef.set(cart);
        // Handle the write result if needed
    }

    // Delete a cart item document by ID
    public void deleteCartItem(String cartId) {
        DocumentReference cartDocRef = this.cartRef.document(String.valueOf(cartId));
        ApiFuture<WriteResult> writeResult = cartDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve cart items by user ID
    public List<Cart> getCartItemsByUserId(String userId) throws ExecutionException, InterruptedException {
        Query query = this.cartRef
                .whereEqualTo("userId", userId)
                .whereNotEqualTo("quantity", 0);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(Cart.class);
    }

    // Retrieve cart items by item name (case-insensitive search)
    public List<Cart> getUserCartItemsByItemName(String userId, String name) throws ExecutionException, InterruptedException {
        Query query = this.cartRef
                .whereEqualTo("userId", userId)
                .whereEqualTo("quantity", 0)
                .orderBy("name")
                .startAt(name.toLowerCase())
                .endAt(name.toLowerCase() + "\uf8ff");
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(Cart.class);
    }

    // Retrieve a cart item document by cart ID
    public Cart getCartItem(String cartId) throws ExecutionException, InterruptedException {
        DocumentReference cartDocRef = this.cartRef.document(String.valueOf(cartId));
        ApiFuture<DocumentSnapshot> cartFuture = cartDocRef.get();
        return cartFuture.get().toObject(Cart.class);
    }
}
