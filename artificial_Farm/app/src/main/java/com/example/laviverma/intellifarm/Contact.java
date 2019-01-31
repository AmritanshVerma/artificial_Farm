package com.example.laviverma.intellifarm;

import android.provider.BaseColumns;

/**
 * Created by Lavi Verma on 09-Jul-18.
 */

public final class Contact {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private Contact() {}

    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class Entry implements BaseColumns {

        /** Name of database table for pets */
        public final static String TABLE_NAME = "person";

        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_name="name";


        public final static String COLUMN_application_key= "application_key";
        public final static String COLUMN_application_password= "application_password";
        public final static String COLUMN_password= "password";
        public final static String COLUMN_address= "address";
        public final static String COLUMN_farm_types= "farm_types";
        public final static String COLUMN_poly_farms= "poly_farms";
        public final static String COLUMN_email= "email";

        public final static String COLUMN_phone_number = "phone_number";



    }

}

