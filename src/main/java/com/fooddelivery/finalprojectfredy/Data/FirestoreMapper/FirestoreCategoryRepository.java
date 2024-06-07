package com.fooddelivery.finalprojectfredy.Data.FirestoreMapper;

import com.fooddelivery.finalprojectfredy.Data.Entity.Category;
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
public class FirestoreCategoryRepository {

    private final CollectionReference categoryRef;

    public FirestoreCategoryRepository() {
        Firestore firestore = FirestoreClient.getFirestore();
        this.categoryRef = firestore.collection("categories");
    }

    // Insert a new category document
    public void insertCategory(Category category) throws InterruptedException, ExecutionException {
        DocumentReference categoryDocRef = this.categoryRef.add(category).get();
        // Handle the write result if needed
    }

    // Update an existing category document
    public void updateCategory(Category category) {
        DocumentReference categoryDocRef = this.categoryRef.document(String.valueOf(category.getCategoryId()));
        ApiFuture<WriteResult> writeResult = categoryDocRef.set(category);
        // Handle the write result if needed
    }

    // Delete a category document by ID
    public void deleteCategory(String categoryId) {
        DocumentReference categoryDocRef = this.categoryRef.document(String.valueOf(categoryId));
        ApiFuture<WriteResult> writeResult = categoryDocRef.delete();
        // Handle the write result if needed
    }

    // Retrieve all category documents
    public List<Category> getAllCategories() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = this.categoryRef.get();
        return querySnapshot.get().toObjects(Category.class);
    }

    // Retrieve a category document by ID
    public Category getCategoryById(String categoryId) throws ExecutionException, InterruptedException {
        DocumentReference categoryDocRef = this.categoryRef.document(String.valueOf(categoryId));
        ApiFuture<DocumentSnapshot> categoryFuture = categoryDocRef.get();
        return categoryFuture.get().toObject(Category.class);
    }

    // Retrieve a category document by name (case-insensitive search)
    public Category getCategoryByName(String name) throws ExecutionException, InterruptedException {
        Query query = this.categoryRef
                .whereEqualTo("name", name.toLowerCase())
                .limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<Category> categories = querySnapshot.get().toObjects(Category.class);
        return categories.isEmpty() ? null : categories.get(0);
    }
}
