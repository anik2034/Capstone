package com.anik.capstone.db;

import android.util.Log;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.UserModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreDB {

    private final String TAG = FirestoreDB.class.getSimpleName();

    private final FirebaseFirestore db;

    public FirestoreDB() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(UserModel userModel) {
        db.collection("users")
                .document(userModel.getId())
                .set(userModel)
                .addOnFailureListener(e -> Log.e(TAG, "addUser(): Error adding document", e));
    }

    public void deleteUser(UserModel userModel) {
        db.collection("users")
                .document(userModel.getId())
                .delete()
                .addOnFailureListener(e -> Log.e(TAG, "deleteUser(): Error deleting document", e));
    }

    public void addBook(BookModel bookModel) {
        db.collection("books")
                .add(bookModel)
                .addOnFailureListener(e -> Log.e(TAG, "addBook(): Error adding document", e));
    }

    public void updateBook(BookModel bookModel) {
            db.collection("books")
                    .whereEqualTo("isbn", bookModel.getISBN())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            String documentId = task.getResult().getDocuments().get(0).getId();
                            db.collection("books").document(documentId)
                                    .set(bookModel)
                                    .addOnFailureListener(e -> Log.e(TAG, "updateBook(): Error updating document", e));
                        } else {
                            Log.e(TAG, "updateBook(): Error finding book with ISBN: " + bookModel.getISBN() + " error: " + task.getException());
                        }
                    });
    }

    public void fetchBooks(String currentUserID, DbOperationCompleteListener dbOperationCompleteListener) {
        List<BookModel> bookModels = new ArrayList<>();
        db.collection("books")
                .whereEqualTo("ownerId", currentUserID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                BookModel bookModel = documentSnapshot.toObject(BookModel.class);
                                bookModels.add(bookModel);
                            }
                        }
                        dbOperationCompleteListener.fetchBooks(bookModels);
                    } else {
                        Log.e(TAG, "fetchBooks(): Error getting documents: ", task.getException());
                    }
                });
    }

    public void deleteBook(BookModel bookModel) {
        db.collection("books")
                .whereEqualTo("ownerId", bookModel.getOwnerId())
                .whereEqualTo("ISBN", bookModel.getISBN())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty() && querySnapshot.getDocuments().size() == 1) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            db.collection("books")
                                    .document(documentSnapshot.getId())
                                    .delete()
                                    .addOnFailureListener(e -> Log.e(TAG, "deleteBook(): Error deleting document", e));
                        }
                    } else {
                        Log.e(TAG, "deleteBook(): Error finding book with ISBN: " + bookModel.getISBN() + " error: " + task.getException());
                    }
                });

    }

    public void deleteAllBooks(String currentUserID) {
        db.collection("books")
                .whereEqualTo("ownerId", currentUserID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                db.collection("books")
                                        .document(documentSnapshot.getId())
                                        .delete()
                                        .addOnFailureListener(e -> Log.e(TAG, "deleteAllBooks(): Error deleting document", e));
                            }
                        }
                    } else {
                        Log.e(TAG, "deleteAllBooks(): Error getting documents: ", task.getException());
                    }
                });

    }
}
