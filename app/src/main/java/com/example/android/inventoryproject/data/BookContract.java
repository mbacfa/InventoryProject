package com.example.android.inventoryproject.data;

import android.provider.BaseColumns;

public final class BookContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {}

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

        /** Name of database table for books */
        public final static String TABLE_NAME = "books";

        /**
         * Name of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_NAME ="name";

        /**
         * Supplier of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_SUPPLIER = "supplier";

        /**
         * price of the book.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_PRICE = "price";

        /**
         * Quantity of the book.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_QUANTITY = "quantity";

        /**
         * Phone number of the supplier.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_SUPPLIER_PHONE = "phone";
    }
}
