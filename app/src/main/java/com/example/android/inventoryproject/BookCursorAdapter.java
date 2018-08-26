package com.example.android.inventoryproject;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryproject.data.BookContract.BookEntry;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BookCursorAdapter extends CursorAdapter {

    private Uri mCurrentBookUri;
    private Context mContext;

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        mContext = context;
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        final TextView nameTextView = (TextView) view.findViewById(R.id.title);
        TextView summaryTextView = (TextView) view.findViewById(R.id.supplier);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView phoneTextView = (TextView) view.findViewById(R.id.phone);
        Button mSellButton = (Button) view.findViewById(R.id.sell_book);

        // Find the columns of book attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int supplierColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
        int phoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE);

        // Read the book attributes from the Cursor for the current book
        final long id = cursor.getLong(idColumnIndex);
        String bookName = cursor.getString(nameColumnIndex);
        String bookSupplier = cursor.getString(supplierColumnIndex);
        String bookPrice = cursor.getString(priceColumnIndex);
        String bookQuantity = cursor.getString(quantityColumnIndex);
        String supplierPhone = cursor.getString(phoneColumnIndex);

        // If the book supplier is empty string or null, then use some default text
        // that says "Unknown supplier", so the TextView isn't blank.
        if (TextUtils.isEmpty(bookSupplier)) {
            bookSupplier = context.getString(R.string.unknown_supplier);
        }

        mSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent openEditor = new Intent(context, EditorActivity.class);
                    openEditor.putExtra("id", id);
                    Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                    openEditor.setData(currentBookUri);
                    view.getContext().startActivity(openEditor);
                } catch (Exception e) {
                    String s = e.getMessage();
                }

                ContentValues values = new ContentValues();
                try {
                    String bookQuantity = quantityTextView.getText().toString().trim();

                    if (Integer.valueOf(bookQuantity) == 0) {
                        throw new IllegalArgumentException("No more copies left");
                    }
                    mCurrentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                    values.put(BookEntry.COLUMN_BOOK_QUANTITY, String.valueOf(Integer.valueOf(bookQuantity) - 1));
                    int rowsAffected = mContext.getContentResolver().update(mCurrentBookUri, values, null,
                            null);
                } catch (Exception ex) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String sStackTrace = sw.toString();
                    System.out.println(sStackTrace);
                }
            }
        });

        // Update the TextViews with the attributes for the current book
        nameTextView.setText(bookName);
        summaryTextView.setText(bookSupplier);
        priceTextView.setText(bookPrice);
        quantityTextView.setText(bookQuantity);
        phoneTextView.setText(supplierPhone);
    }
}
