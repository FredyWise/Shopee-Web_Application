package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.Order;
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
public class FirestoreOrderRepository {

    private final CollectionReference orderRef;

    public FirestoreOrderRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.orderRef = firestore.collection("orders");
    }

    // Insert a new order document
    public void insertOrder(Order order) throws InterruptedException, ExecutionException {
        DocumentReference orderDocRef = this.orderRef.add(order).get();
        // Handle the write result if needed
    }

    // Update an existing order document
    public void updateOrder(Order order) {
        DocumentReference orderDocRef = this.orderRef.document(order.getOrderId());
        ApiFuture<WriteResult> writeResult = orderDocRef.set(order);
        // Handle the write result if needed
    }

    // Delete an order document by ID
    public void deleteOrder(String orderId) {
        DocumentReference orderDocRef = this.orderRef.document(String.valueOf(orderId));
        ApiFuture<WriteResult> writeResult = orderDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve all order documents
    public List<Order> getAllOrders() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = this.orderRef.get();
        return querySnapshot.get().toObjects(Order.class);
    }

    // Retrieve an order document by ID
    public Order getOrderById(String orderId) throws ExecutionException, InterruptedException {
        DocumentReference orderDocRef = this.orderRef.document(String.valueOf(orderId));
        ApiFuture<DocumentSnapshot> orderFuture = orderDocRef.get();
        return orderFuture.get().toObject(Order.class);
    }

    // Retrieve orders by user ID
    public List<Order> getOrdersByUserId(String userId) throws ExecutionException, InterruptedException {
        Query query = this.orderRef.whereEqualTo("userId", userId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot.get().toObjects(Order.class);
    }

    // Retrieve order ID by all order details (user ID, order date, total price, status)
    public String getOrderByAll(Order order) throws ExecutionException, InterruptedException {
        Query query = this.orderRef
                .whereEqualTo("userId", order.getUserId())
                .whereEqualTo("orderDate", order.getOrderDate())
                .whereEqualTo("totalPrice", order.getTotalPrice())
                .whereEqualTo("status", order.getStatus())
                .limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<Order> orders = querySnapshot.get().toObjects(Order.class);
        return orders.isEmpty() ? null : orders.get(0).getOrderId();
    }
}
