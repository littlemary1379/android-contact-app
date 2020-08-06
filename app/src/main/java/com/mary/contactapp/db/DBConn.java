package com.mary.contactapp.db;

import android.content.Context;

import androidx.room.Room;

import com.mary.contactapp.db.model.Contact;

public class DBConn {

    private static ContactAppDatabase contactAppDatabase;

    public static ContactAppDatabase getConnection(Context context){
        if(contactAppDatabase==null) {
            contactAppDatabase = Room.databaseBuilder(context, ContactAppDatabase.class, "ContactDB")
                    .allowMainThreadQueries()
                    .build();
        }
        return contactAppDatabase;
    }
}
