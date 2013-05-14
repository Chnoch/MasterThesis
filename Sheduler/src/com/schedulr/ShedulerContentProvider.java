package com.schedulr;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class ShedulerContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI_FAV = Uri.parse("content://com.schedulr.provider/favitems");
//    public static final Uri CONTENT_URI_SWITCH = Uri.parse("content://com.schedulr.provider/switch");
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
//    public static final String ON_OFF = "on_off";
    private ShedulerDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        // Construct the underlying database.
        // Defer opening the database until you need to perform
        // a query or transaction.
        dbHelper = new ShedulerDBHelper(getContext(), ShedulerDBHelper.DATABASE_NAME, null, ShedulerDBHelper.DATABASE_VERSION);
        return true;
    }

    private static final int ALLROWS_FAV = 1;
    private static final int SINGLE_ROW_FAV = 2;
//    private static final int ALLROWS_SW = 3;
//    private static final int SINGLE_ROW_SW = 4;

    private static final UriMatcher uriMatcher;

    //Populate the UriMatcher object, where a URI ending in 'favitems' will
    //correspond to a request for all items, and 'favitems/[rowID]'
    //represents a single row.
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.schedulr.provider", "favitems", ALLROWS_FAV);
        uriMatcher.addURI("com.schedulr.provider", "favitems/#", SINGLE_ROW_FAV);
//        uriMatcher.addURI("com.schedulr.provider", "switch", ALLROWS_SW);
//        uriMatcher.addURI("com.schedulr.provider", "switch/#", SINGLE_ROW_SW);
    }

    @Override
    public String getType(Uri uri) {
        // Return a string that identifies the MIME type
        // for a Content Provider URI
        switch (uriMatcher.match(uri)) {
            case ALLROWS_FAV: return "vnd.android.cursor.dir/vnd.schedulr.favs";
            case SINGLE_ROW_FAV: return "vnd.android.cursor.item/vnd.schedulr.favs";
//            case ALLROWS_SW: return "vnd.android.cursor.dir/vnd.schedulr.sw";
//            case SINGLE_ROW_SW: return "vnd.android.cursor.item/vnd.schedulr.sw";
            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Open a read-only database.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Replace these with valid SQL statements if necessary.
        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String rowID;
        // If this is a row query, limit the result set to the passed in row.
        switch (uriMatcher.match(uri)) {
            case ALLROWS_FAV:
                queryBuilder.setTables(ShedulerDBHelper.DATABASE_TABLE);
                break;
            case SINGLE_ROW_FAV:
                queryBuilder.setTables(ShedulerDBHelper.DATABASE_TABLE);
                rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + rowID);
                break;
//            case SINGLE_ROW_SW:
//                queryBuilder.setTables(ShedulerDBHelper.DB_TABLE_SWITCH);
//                rowID = uri.getPathSegments().get(1);
//                queryBuilder.appendWhere(KEY_ID + "=" + rowID);
//                break;
//            case ALLROWS_SW:
//                queryBuilder.setTables(ShedulerDBHelper.DB_TABLE_SWITCH);
//                break;
            default: break;
        }

        return queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Open a read / write database to support the transaction.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // To add empty rows to your database by passing in an empty Content Values
        // object, you must use the null column hack parameter to specify the name of
        // the column that can be set to null.
        String nullColumnHack = null;

        String table = "";
        Uri provider = null;
        // Insert the values into the table
        switch (uriMatcher.match(uri)){
            case ALLROWS_FAV:
                provider = CONTENT_URI_FAV;
                table = ShedulerDBHelper.DATABASE_TABLE;
                break;
//            case ALLROWS_SW:
//                provider = CONTENT_URI_SWITCH;
//                table = ShedulerDBHelper.DB_TABLE_SWITCH;
//                break;
            default: break;

        }
        long id = db.insert(table, nullColumnHack, values);

        if (id > -1) {
            // Construct and return the URI of the newly inserted row.
            Uri insertedId = ContentUris.withAppendedId(provider, id);
            // Notify any observers of the change in the data set.
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        }
        else
            return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Open a read / write database to support the transaction.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW_FAV:
                String rowID = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
            default: break;
        }

        // To return the number of deleted items, you must specify a where
        // clause. To delete all rows and return a value, pass in "1".
        if (selection == null)
            selection = "1";

        // Execute the deletion.
        int deleteCount = db.delete(ShedulerDBHelper.DATABASE_TABLE, selection, selectionArgs);

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        // Open a read / write database to support the transaction.
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        // If this is a row URI, limit the deletion to the specified row.
//        switch (uriMatcher.match(uri)) {
//            case SINGLE_ROW_SW :
//                String rowID = uri.getPathSegments().get(1);
//                selection = KEY_ID + "=" + rowID
//                + (!TextUtils.isEmpty(selection) ?
//                " AND (" + selection + ")" : "");
//            default: break;
//        }
//        // Perform the update.
//        int updateCount = db.update(ShedulerDBHelper.DB_TABLE_SWITCH, values, selection, selectionArgs);
//        // Notify any observers of the change in the data set.
//        getContext().getContentResolver().notifyChange(uri, null);
//        return updateCount;
        return 0;
    }

    private static class ShedulerDBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "sheduler.db";
        private static final String DATABASE_TABLE = "FAVOURITES";
//        private static final String DB_TABLE_SWITCH = "SWITCH";
        private static final int DATABASE_VERSION = 4;

        private static final String DATABASE_CREATE_FAV = "create table " +
                DATABASE_TABLE + "(" + KEY_ID +
                " integer primary key, " +
                KEY_NAME + " text not null );";
//        private static final String DATABASE_CREATE_SWITCH = " create table " +
//                DB_TABLE_SWITCH + "(" + KEY_ID +
//                " integer primary key, " +
//                KEY_NAME + " text not null, " +
//                ON_OFF + " text not null );";

        public ShedulerDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_FAV);
//            db.execSQL(DATABASE_CREATE_SWITCH);
//            ContentValues cv = new ContentValues();
//            cv.put(KEY_ID, 1);
//            cv.put(KEY_NAME, "switch");
//            cv.put(ON_OFF, "no");
//            db.insert(DB_TABLE_SWITCH, null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("TaskDBAdapter", "Upgrading from version " +
                    oldVersion + "to" +
                    newVersion + ", which will destroy all old data");
            // Upgrade the existing database to conform to the new
            // version. Multiple previous versions can be handled by
            // comparing oldVersion and newVersion values.
            // The simplest case is to drop the old table and create a new one.
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
//            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_SWITCH);
            // Create a new one.
            onCreate(db);
        }
    }
}
