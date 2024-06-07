package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.OrderItem;
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
public class FirestoreOrderItemRepository {

    private final CollectionReference orderItemRef;

    public FirestoreOrderItemRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.orderItemRef = firestore.collection("order_items");
    }

    // Insert a new order item document
    public void insertOrderItem(OrderItem orderItem) throws InterruptedException, ExecutionException {
        DocumentReference orderItemDocRef = this.orderItemRef.add(orderItem).get();
        // Handle the write result if needed
    }

    // Update an existing order item document
    public void updateOrderItem(OrderItem orderItem) {
        DocumentReference orderItemDocRef = this.orderItemRef.document(String.valueOf(orderItem.getOrderItemId()));
        ApiFuture<WriteResult> writeResult = orderItemDocRef.set(orderItem);
        // Handle the write result if needed
    }

    // Delete an order item document by ID
    public void deleteOrderItem(String orderItemId) {
        DocumentReference orderItemDocRef = this.orderItemRef.document(String.valueOf(orderItemId));
        ApiFuture<WriteResult> writeResult = orderItemDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve an order item document by ID
    public OrderItem getOrderItemById(String orderItemId) throws ExecutionException, InterruptedException {
        DocumentReference orderItemDocRef = this.orderItemRef.document(String.valueOf(orderItemId));
        ApiFuture<DocumentSnapshot> orderItemFuture = orderItemDocRef.get();
        return orderItemFuture.get().toObject(OrderItem.class);
    }

    // Retrieve order items by order ID
    public List<OrderItem> getOrderItemsByOrderId(String orderId) throws ExecutionException, InterruptedException {
        Query query = this.orderItemRef.whereEqualTo("orderId", orderId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(OrderItem.class);
    }
}
