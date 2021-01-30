package com.highsenbergs.ecofriendly.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.highsenbergs.ecofriendly.model.Contacts;

import java.security.Provider;
import java.util.ArrayList;

import static com.highsenbergs.ecofriendly.utils.Constants.COLUMN_CONTACT_ID;
import static com.highsenbergs.ecofriendly.utils.Constants.COLUMN_MAIL;
import static com.highsenbergs.ecofriendly.utils.Constants.COLUMN_NAME;
import static com.highsenbergs.ecofriendly.utils.Constants.COLUMN_PHONE;
import static com.highsenbergs.ecofriendly.utils.Constants.TABLE_NAME;
import static com.highsenbergs.ecofriendly.utils.Constants._ID;

public class ContactsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 3;

    public ContactsDBHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CONTACT_ID + " INTEGER," + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_MAIL + " TEXT,"
                + COLUMN_PHONE + " TEXT NOT NULL" + ")";

        sqLiteDatabase.execSQL( CREATE_CONTACTS_TABLE );
    }

    public ArrayList<Contacts> getAllContacts() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Contacts> contactsArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery( "SELECT * FROM contacts", null );
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int id = cursor.getInt( cursor.getColumnIndexOrThrow( "ID" ) );
            String name = cursor.getString( cursor.getColumnIndexOrThrow( "name" ) );
            double mobile = cursor.getDouble( cursor.getColumnIndexOrThrow( "phone" ) );
            String email = cursor.getString( cursor.getColumnIndexOrThrow( "mail" ) );
            Contacts contacts = new Contacts( id, email, mobile, name );
            contactsArrayList.add( contacts );
        }
        return  contactsArrayList;
    }
}
