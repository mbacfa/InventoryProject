package com.example.android.inventoryproject;


import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryproject.data.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
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
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.title);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        // Find the columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int supplierColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER);

        // Read the book attributes from the Cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        String bookSupplier = cursor.getString(supplierColumnIndex);

        // If the book supplier is empty string or null, then use some default text
        // that says "Unknown supplier", so the TextView isn't blank.
        if (TextUtils.isEmpty(bookSupplier)) {
            bookSupplier = context.getString(R.string.unknown_supplier);
        }

        // Update the TextViews with the attributes for the current book
        nameTextView.setText(bookName);
        summaryTextView.setText(bookSupplier);
    }
}
