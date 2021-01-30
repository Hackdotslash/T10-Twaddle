package com.highsenbergs.ecofriendly.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.Provider;

import static com.highsenbergs.ecofriendly.utils.Constants.COLUMN_CONTACT_ID;
import static com.highsenbergs.ecofriendly.utils.Constants.COLUMN_MAIL;
import static com.highsenbergs.ecofriendly.utils.Constants.COLUMN_NAME;
import static com.highsenbergs.ecofriendly.utils.Constants.COLUMN_PHONE;
import static com.highsenbergs.ecofriendly.utils.Constants.TABLE_NAME;
import static com.highsenbergs.ecofriendly.utils.Constants._ID;

public class ContactsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    public ContactsDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CONTACT_ID + " INTEGER," + COLUMN_NAME + " TEXT NOT NULL UNIQUE," + COLUMN_MAIL + " TEXT,"
                + COLUMN_PHONE + " TEXT NOT NULL" + ")";

        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

}
