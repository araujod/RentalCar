package ca.douglas.rentalcar.DB;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyDBConnection {
    private FirebaseFirestore mydb;

    public  MyDBConnection(){mydb= FirebaseFirestore.getInstance();}
    public CollectionReference getCollectionReference(String collectionName){
        return  mydb.collection(collectionName);
    }
}
