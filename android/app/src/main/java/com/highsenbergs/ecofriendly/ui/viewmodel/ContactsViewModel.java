package com.highsenbergs.ecofriendly.ui.viewmodel;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.highsenbergs.ecofriendly.db.ContactsDBHelper;
import com.highsenbergs.ecofriendly.model.Contacts;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.highsenbergs.ecofriendly.utils.Constants.TABLE_NAME;

public class ContactsViewModel extends AndroidViewModel {
    private ContactsDBHelper dbHelper;
    private ArrayList<Contacts> contactsArrayList;


    public ContactsViewModel(Application application){
        super(application);
        dbHelper = new ContactsDBHelper( application );
    }

    public ArrayList<Contacts> getContactsArrayList(){
        if(contactsArrayList == null){
            contactsArrayList = new ArrayList<>();
        }
//        addContacts();
        GetContactsAsyncTask asyncTask = new GetContactsAsyncTask();
        asyncTask.execute();
        loadContacts();
        ArrayList<Contacts> clonedContacts = new ArrayList<>( contactsArrayList);
        return clonedContacts;
    }

    private void loadContacts(){
        contactsArrayList.clear();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
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
        cursor.close();
        database.close();
    }

    private void addContacts(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentResolver resolver = getApplication().getContentResolver();
        Cursor c = resolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.HAS_PHONE_NUMBER + "!=0 AND (" + ContactsContract.Contacts.Data.MIMETYPE + "=? OR " + ContactsContract.Contacts.Data.MIMETYPE + "=?)",
                new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},
                ContactsContract.Data.CONTACT_ID );
        c.moveToFirst();

        while (c.moveToNext()) {
            int id = c.getInt( c.getColumnIndex( ContactsContract.Data.CONTACT_ID ) );
            String name = c.getString( c.getColumnIndex( ContactsContract.Data.DISPLAY_NAME ) );
            String data1 = c.getString( c.getColumnIndex( ContactsContract.Data.DATA1 ) );
            String mail = c.getString( c.getColumnIndexOrThrow( ContactsContract.CommonDataKinds.Email.ADDRESS ) );
            ContentValues contentValues = new ContentValues();
            contentValues.put( "ID", id );
            contentValues.put( "name", name );
            contentValues.put( "phone", data1 );
            contentValues.put( "mail", mail );
            database.insert( TABLE_NAME, null, contentValues );
        }
        c.close();

    }
    private class GetContactsAsyncTask extends AsyncTask<Void, Void , Void>{

        public GetContactsAsyncTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute( aVoid );
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentResolver resolver = getApplication().getContentResolver();
            Cursor c = resolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.HAS_PHONE_NUMBER + "!=0 AND (" + ContactsContract.Contacts.Data.MIMETYPE + "=? OR " + ContactsContract.Contacts.Data.MIMETYPE + "=?)",
                    new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},
                    ContactsContract.Data.CONTACT_ID );
            c.moveToFirst();

            while (c.moveToNext()) {
                int id = c.getInt( c.getColumnIndex( ContactsContract.Data.CONTACT_ID ) );
                String name = c.getString( c.getColumnIndex( ContactsContract.Data.DISPLAY_NAME ) );
                String data1 = c.getString( c.getColumnIndex( ContactsContract.Data.DATA1 ) );
                String mail = c.getString( c.getColumnIndexOrThrow( ContactsContract.CommonDataKinds.Email.ADDRESS ) );
                ContentValues contentValues = new ContentValues();
                contentValues.put( "ID", id );
                contentValues.put( "name", name );
                contentValues.put( "phone", data1 );
                contentValues.put( "mail", mail );
                database.insert( TABLE_NAME, null, contentValues );
            }
            c.close();
            return null;
        }
    }
}
