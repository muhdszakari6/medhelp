package com.salim.medhelp.crud;


import android.app.AlarmManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.salim.medhelp.pojo.Alarm_pojo;
import com.salim.medhelp.pojo.Ap_pojo;
import com.salim.medhelp.pojo.Pres_pojo;
import com.salim.medhelp.pojo.Rem_pojo;
import com.salim.medhelp.pojo.Signup_pojo;
import com.salim.medhelp.pojo.Trigger_pojo;

import java.util.ArrayList;
import java.util.List;

public class Signup_crud extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";

    private static final String TABLE_NAME = "sign_up";
    private static final String TABLE2 = "pres";
    private static final String TABLE3 = "appointment";
    private static final String TABLE4 = "reminder";
    private static final String TABLE5 = "alarm";
    private static final String TABLE6 = "alarm_trigger";


    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_IMAGE = "image";

    private static final String pres_id = "_id";
    private static final String pres_name = "name";
    private static final String pres_treat = "treatment";
    private static final String pres_no = "number";
    private static final String pres_dose = "dose";
    private static final String pres_dosedate = "dosedate";
    private static final String pres_dosetime = "dosetime";
    private static final String pres_pic = "presimg";
    private static final String pres_status = "status";
    private static final String pres_user = "user_id";
    private static final String pres_trig = "pres_trig";

    private static final String ap_id = "_id";
    private static final String ap_loc = "location";
    private static final String ap_comp = "complain";
    private static final String ap_date = "date";
    private static final String ap_time = "time";
    private static final String ap_lat = "lat";
    private static final String ap_lon = "lon";
    private static final String ap_trig = "trig";
    private static final String ap_user = "user_id";

    private static final String rem_id = "_id";
    private static final String rem_name = "name";
    private static final String rem_treat = "treatment";
    private static final String rem_user = "user_id";

    private static final String alarm_id = "_id";
    private static final String alarm_time = "time";
    private static final String alarm_date = "date";
    private static final String alarm_trig = "trig";
    private static final String alarm_user = "user_id";
    private static final String alarm_rem = "rem_id";
    private static final String alarm_dose = "dose";
    private static final String alarm_freq = "freq";

    private static final String trig_id = "_id";
    private static final String initial_trig = "inittrig";
    private static final String trig = "trig";
    private static final String trig_alarm_id = "alarm_id";
    private static final String trig_rem_id = "rem_id";



    private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME +
            " ( " + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT,  "
            + COLUMN_FIRST_NAME + " TEXT NOT NULL, "
            + COLUMN_LAST_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE , "
            + COLUMN_PASSWORD + " TEXT NOT NULL , "
            + COLUMN_DOB + " TEXT NOT NULL , "
            + COLUMN_IMAGE + " BLOB )";

    private static final String CREATE_TABLE_TRIG = "CREATE TABLE IF NOT EXISTS "+TABLE6+
            " ( "+trig_id+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +initial_trig+ " INTEGER NOT NULL, "
            +trig+ " INTEGER NOT NULL, "
            +trig_alarm_id+ " INTEGER NOT NULL, "
            +trig_rem_id+ " INTEGER NOT NULL, "+
            " FOREIGN Key ( " + trig_rem_id+ " ) REFERENCES " + TABLE5 + " ( " + alarm_rem + " ) ON DELETE CASCADE , " +
            " FOREIGN Key ( " + trig_alarm_id + " ) REFERENCES " + TABLE5 + " ( " + COLUMN_ID + " ) ON DELETE CASCADE ); ";


    ;


    private static final String CREATE_TABLE_PRES = "CREATE TABLE IF NOT EXISTS " + TABLE2 +
            " ( " + pres_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + pres_name + " TEXT NOT NULL, "
            + pres_treat + " TEXT NOT NULL, "
            + pres_no + " INTEGER NOT NULL, "
            + pres_dose + " TEXT NOT NULL, "
            + pres_dosedate + " TEXT NOT NULL, "
            + pres_dosetime + " TEXT NOT NULL, "
            + pres_pic + " BLOB , "
            + pres_status + " INTEGER NOT NULL, "
            + pres_user + " INTEGER NOT NULL, " +
            pres_trig + " INTEGER NOT NULL, " +
            " FOREIGN Key ( " + pres_user + " ) REFERENCES " + TABLE_NAME + " ( " + COLUMN_ID + " ) ON DELETE CASCADE ); ";


    private static final String CREATE_TABLE_AP = " CREATE TABLE IF NOT EXISTS " + TABLE3 +
            " ( " + ap_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ap_loc + " TEXT NOT NULL, "
            + ap_comp + " TEXT NOT NULL, "
            + ap_date + " TEXT NOT NULL, "
            + ap_time + " TEXT NOT NULL, "
            + ap_lat + " INTEGER, "
            + ap_lon + " INTEGER, "
            + ap_trig + " INTEGER NOT NULL, "
            + ap_user + " INTEGER NOT NULL, " +
            " FOREIGN Key ( " + ap_user + " ) REFERENCES " + TABLE_NAME + " ( " + COLUMN_ID + " ) ON DELETE CASCADE ); ";

    private static final String CREATE_TABLE_REM = "CREATE TABLE IF NOT EXISTS " + TABLE4 +
            " ( " + rem_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + rem_name + " TEXT NOT NULL, "
            + rem_treat + " TEXT NOT NULL, "
            + rem_user + " INTEGER NOT NULL, " +
            " FOREIGN Key ( " + rem_user + " ) REFERENCES " + TABLE_NAME + " ( " + COLUMN_ID + " ) ON DELETE CASCADE ); ";


    private static final String CREATE_ALARM_TABLE = " CREATE TABLE IF NOT EXISTS " + TABLE5 +
            " ( " + alarm_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + alarm_time + " TEXT NOT NULL, "
            + alarm_date + " TEXT NOT NULL, "
            + alarm_trig + " INTEGER , "
            + alarm_user + " INTEGER NOT NULL, "
            + alarm_rem + " INTEGER NOT NULL, "
            + alarm_dose + " INTEGER NOT NULL, "
            +alarm_freq+ " TEXT , "+
            " FOREIGN Key ( " + alarm_user + " ) REFERENCES " + TABLE_NAME + " ( " + COLUMN_ID + " ) ON DELETE CASCADE , " +
            " FOREIGN Key ( " + alarm_rem + " ) REFERENCES " + TABLE4 + " ( " + rem_id + " ) ON DELETE CASCADE ); ";

    private static final String[] alarm_columns = {
            alarm_id,
            alarm_time,
            alarm_date,
            alarm_trig,
            alarm_user,
            alarm_rem,
            alarm_dose,
            alarm_freq
    };

    private static final String[] rem_columns = {
            rem_id,
            rem_name,
            rem_treat,
            rem_user

    };

    private static final String[] ap_columns = {
            ap_id,
            ap_loc,
            ap_comp,
            ap_date,
            ap_time,
            ap_lat,
            ap_lon,
            ap_trig,
            ap_user
    };
    private static final String[] allColumns = {

            COLUMN_ID,
            COLUMN_FIRST_NAME,
            COLUMN_LAST_NAME,
            COLUMN_EMAIL,
            COLUMN_PASSWORD,
            COLUMN_DOB,
            COLUMN_IMAGE
    };

    private static final String[] pres_columns = {
            pres_id,
            pres_name,
            pres_treat,
            pres_no,
            pres_dose,
            pres_dosedate,
            pres_dosetime,
            pres_pic,
            pres_status,
            pres_user,
            pres_trig
    };

    private static final String[] trig_columns = {
            trig_id,
            initial_trig,
            trig,
            trig_alarm_id,
            trig_rem_id
    };



    private SQLiteDatabase database;

    public Signup_crud(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Signup_crud(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(CREATE_TABLE_PRES);
        db.execSQL(CREATE_TABLE_AP);
        db.execSQL(CREATE_TABLE_REM);
        db.execSQL(CREATE_ALARM_TABLE);
        db.execSQL(CREATE_TABLE_TRIG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE6);

        onCreate(db);
    }

    public Rem_pojo addRem(Rem_pojo pojo, Signup_pojo spojo) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(rem_name, pojo.getTabletname());
        values.put(rem_treat, pojo.getTablettreatment());
        values.put(rem_user, spojo.getId());
        long inserted = database.insert(TABLE4, null, values);
        database.close();
        if (inserted != -1) {
            pojo.setId(inserted);
            pojo.setMessage("success");
            Log.i("Element", "Inserted new row :" + inserted);

        } else {
            pojo.setId(inserted);
            pojo.setMessage("An Error Occurred");
            Log.i("Element", "no row was inserted :" + inserted);
        }

        return pojo;

    }

    public Trigger_pojo addTrig(Trigger_pojo pojo, Alarm_pojo apojo) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(initial_trig, pojo.getInitialtrig());
        values.put(trig, pojo.getTrig());
        values.put(trig_alarm_id, apojo.getId());
        values.put(trig_rem_id, apojo.getRem_id());
        long inserted = database.insert(TABLE6, null, values);
        database.close();
        if (inserted != 1) {
            pojo.setId(inserted);
            Log.i("Element", "Trigger pojo inserted new row :" + inserted);

        } else {
            pojo.setId(inserted);
            Log.i("Element", "no row was inserted :" + inserted);
        }

        return pojo;
    }

    public Alarm_pojo addAlarm(Alarm_pojo pojo, Signup_pojo spojo, Rem_pojo rpojo) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(alarm_time, pojo.getTime());
        values.put(alarm_date, pojo.getDate());
        values.put(alarm_trig, pojo.getTrig());
        values.put(alarm_user, spojo.getId());
        values.put(alarm_rem, rpojo.getId());
        values.put(alarm_dose, pojo.getDose());
        values.put(alarm_freq,pojo.getFreq());

        long inserted = database.insert(TABLE5, null, values);
        database.close();
        if (inserted != -1) {
            pojo.setId(inserted);
            pojo.setMessage("success");
            Log.i("Element", "Inserted new row :" + inserted);

        } else {
            pojo.setId(inserted);
            pojo.setMessage("An Error Occurred");
            Log.i("Element", "no row was inserted :" + inserted);
        }

        return pojo;

    }


    public Signup_pojo add_new_user(Signup_pojo signUp_POJO) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, signUp_POJO.getFirstname());
        values.put(COLUMN_LAST_NAME, signUp_POJO.getLastname());
        values.put(COLUMN_EMAIL, signUp_POJO.getEmail());
        values.put(COLUMN_PASSWORD, signUp_POJO.getPassword());
        values.put(COLUMN_DOB, signUp_POJO.getDob());
        values.put(COLUMN_IMAGE, signUp_POJO.getImage());

        long inserted = database.insert(TABLE_NAME, null, values);
        database.close();
        if (inserted != -1) {
            signUp_POJO.setId(inserted);
            signUp_POJO.setMessage("success");
            Log.i("Element", "Inserted new row :" + inserted);

        } else {
            signUp_POJO.setId(inserted);
            signUp_POJO.setMessage("An Error Occurred");
            Log.i("Element", "no row was inserted :" + inserted);
        }

        return signUp_POJO;
    }

    public Ap_pojo addAp(Ap_pojo apPojo, Signup_pojo pojo) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ap_loc, apPojo.getLocation());
        values.put(ap_comp, apPojo.getComplain());
        values.put(ap_date, apPojo.getDate());
        values.put(ap_time, apPojo.getTime());
        values.put(ap_lat, apPojo.getLat());
        values.put(ap_lon, apPojo.getLon());
        values.put(ap_trig, apPojo.getTrig());
        values.put(ap_user, pojo.getId());

        long inserted = database.insert(TABLE3, null, values);
        database.close();
        if (inserted != -1) {
            apPojo.setId(inserted);
            apPojo.setMessage("success");
        } else {
            apPojo.setMessage("Error");
            apPojo.setId(inserted);

        }
        return apPojo;

    }

    public Pres_pojo addPres(Pres_pojo ppojo, Signup_pojo pojo) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(pres_name, ppojo.getName());
        values.put(pres_treat, ppojo.getTreatment());
        values.put(pres_no, ppojo.getNumber());
        values.put(pres_dose, ppojo.getDose());
        values.put(pres_dosedate, ppojo.getFirstdosedate());
        values.put(pres_dosetime, ppojo.getFirstdosetime());
        values.put(pres_pic, ppojo.getImage());
        values.put(pres_status, ppojo.getStatus());
        values.put(pres_user, pojo.getId());
        values.put(pres_trig, ppojo.getTrig());

        long inserted = database.insert(TABLE2, null, values);
        database.close();
        if (inserted != -1) {
            ppojo.setId(inserted);
            ppojo.setMessage("success");

        } else {
            ppojo.setId(inserted);
            ppojo.setMessage("An Error Occurred");

        }
        return ppojo;
    }

    ;

    public Cursor getPress(Signup_pojo pojo) {
        database = getReadableDatabase();
        return database.query(TABLE2, pres_columns, " user_id = ? AND status = ? ", new String[]{String.valueOf(pojo.getId()), String.valueOf(0)}, null, null, null);
    }

    public Cursor getRemAlarms(Rem_pojo rem_pojo) {
        database = getReadableDatabase();
        return database.query(TABLE6, trig_columns, " rem_id  = ? ", new String[]{String.valueOf(rem_pojo.getId())}, null, null, null);
    }

    public Cursor getAlarmTrigs(Alarm_pojo a_pojo) {
        database = getReadableDatabase();
        return database.query(TABLE6, trig_columns, " alarm_id  = ? ", new String[]{String.valueOf(a_pojo.getId())}, null, null, null);
    }

    public Cursor getFinPress(Signup_pojo pojo) {
        database = getReadableDatabase();
        return database.query(TABLE2, pres_columns, " user_id = ? AND status = ? ", new String[]{String.valueOf(pojo.getId()), String.valueOf(1)}, null, null, null);
    }

    public Cursor getAps(Signup_pojo pojo) {
        database = getReadableDatabase();
        return database.query(TABLE3, ap_columns, " user_id = ? ", new String[]{String.valueOf(pojo.getId())}, null, null, null);
    }

    public Cursor getRems(Signup_pojo pojo) {
        database = getReadableDatabase();
        return database.query(TABLE4, rem_columns, " user_id = ? ", new String[]{String.valueOf(pojo.getId())}, null, null, null);
    }

    public Cursor getAlarms(Signup_pojo pojo, Rem_pojo rpojo) {
        database = getReadableDatabase();
        return database.query(TABLE5, alarm_columns, " user_id = ? AND rem_id = ? ", new String[]{String.valueOf(pojo.getId()), String.valueOf(rpojo.getId())}, null, null, null);
    }

    public void delete_ap(Ap_pojo ap_pojo) {
        database = getWritableDatabase();
        long delete = database.delete(TABLE3, " _id = ? ", new String[]{String.valueOf(ap_pojo.getId())});
    }

    public void delete_Alarm(Alarm_pojo pojo) {
        database = getWritableDatabase();
        long delete = database.delete(TABLE5, " _id = ? ", new String[]{String.valueOf(pojo.getId())});
    }


    public void delete_rem(Rem_pojo rem_pojo) {
        database = getWritableDatabase();
        long delete = database.delete(TABLE4, " _id = ? ", new String[]{String.valueOf(rem_pojo.getId())});
    }


    public void delete_pres(Pres_pojo pres_pojo) {
        database = getWritableDatabase();
        long delete = database.delete(TABLE2, " _id = ? ", new String[]{String.valueOf(pres_pojo.getId())});
    }

    public void delete_user(Signup_pojo pojo) {
        database = getWritableDatabase();
        long delete = database.delete(TABLE_NAME, " _id = ? ", new String[]{String.valueOf(pojo.getId())});
        long deletee = database.delete(TABLE2, " user_id = ? ", new String[]{String.valueOf(pojo.getId())});
        long delete1 = database.delete(TABLE3, " user_id = ? ", new String[]{String.valueOf(pojo.getId())});
        long delete2 = database.delete(TABLE4, " user_id = ? ", new String[]{String.valueOf(pojo.getId())});
        long delete3 = database.delete(TABLE5, " user_id = ? ", new String[]{String.valueOf(pojo.getId())});

    }

    public Signup_pojo getUser(Signup_pojo signUp_POJO) {
        //Open database on read mode..
        database = getReadableDatabase();
        Cursor cursor;
        //Log.i("Element", "database queried 2");
        try {
            cursor = database.query(TABLE_NAME, allColumns, "email=?", new String[]{signUp_POJO.getEmail()}, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String paswd = cursor.getString(4);
                Long id = null;
                String firstname = "";
                String lastname = "";
                String email = "";
                String password = "";
                String dob = "";
                byte[] image = null;
                String message = "";


                if (signUp_POJO.getPassword().equals(paswd)) {

                    Log.i("Element", "database queried : " + cursor.getString(0));
                    id = Long.parseLong(cursor.getLong(0) + "");
                    firstname = cursor.getString(1);
                    lastname = cursor.getString(2);
                    email = cursor.getString(3);
                    password = cursor.getString(4);
                    dob = cursor.getString(5);

                    image = cursor.getBlob(6);
                    message = "Success";
                } else {
                    message = "Failed";
                }

                //close database connection
                cursor.close();
                database.close();
                return new Signup_pojo(id, firstname, lastname, email, password, dob, image, message);
            }
        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            signUp_POJO.setMessage(ex.getMessage());
            //
            database.close();
            return signUp_POJO;
        }

        // return User
        return signUp_POJO;
    }

    public Ap_pojo getCurrentAP(Ap_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor;
        try {
            cursor = database.query(TABLE3, ap_columns, " trig = ( SELECT MAX( " + ap_trig + " ) FROM appointment) ", null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                pojo.setId(cursor.getLong(cursor.getColumnIndex(ap_id)));
                pojo.setLocation(cursor.getString(cursor.getColumnIndex(ap_loc)));
                pojo.setComplain(cursor.getString(cursor.getColumnIndex(ap_comp)));
                pojo.setDate(cursor.getString(cursor.getColumnIndex(ap_date)));
                pojo.setTime(cursor.getString(cursor.getColumnIndex(ap_time)));
                pojo.setLon(cursor.getDouble(cursor.getColumnIndex(ap_lon)));
                pojo.setLat(cursor.getDouble(cursor.getColumnIndex(ap_lat)));
                pojo.setUser(cursor.getLong(cursor.getColumnIndex(ap_user)));
                pojo.setTrig(cursor.getLong(cursor.getColumnIndex(ap_trig)));
                cursor.close();
                database.close();
                return pojo;

            } else {

                pojo.setMessage("Error");

                database.close();

                return pojo;

            }


        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            pojo.setMessage(ex.getMessage());
            //
            database.close();
            return pojo;
        }
    }

    public Trigger_pojo getCurrentTrigger(){
        database = getReadableDatabase();
        Trigger_pojo pojo = new Trigger_pojo();
        Cursor cursor;
        try{
            cursor = database.query(TABLE6, trig_columns, " trig = ( SELECT MIN( " + trig + " ) FROM alarm_trigger) ", null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                pojo.setId(cursor.getLong(cursor.getColumnIndex(trig_id)));
                pojo.setInitialtrig(cursor.getLong(cursor.getColumnIndex(initial_trig)));
                pojo.setTrig(cursor.getLong(cursor.getColumnIndex(trig)));
                pojo.setAlarm_id(cursor.getLong(cursor.getColumnIndex(trig_alarm_id)));
                pojo.setRem_id(cursor.getLong(cursor.getColumnIndex(trig_rem_id)));
                cursor.close();
                database.close();
                Log.i("Current trig","not empty" );

                return pojo;
            }
            else {

                Log.e("Current trig","empty" );

                database.close();

                return pojo;

            }


        } catch (Exception ex) {
            Log.e("Current trig", "error");
            //
            database.close();
            return pojo;



    }
    }
    public Alarm_pojo getCurrentAlarm(Alarm_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor;
        try {
            cursor = database.query(TABLE5, alarm_columns, " trig = ( SELECT MIN( " + alarm_trig + " ) FROM alarm ) ", null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                pojo.setId(cursor.getLong(cursor.getColumnIndex(alarm_id)));
                pojo.setTime(cursor.getString(cursor.getColumnIndex(alarm_time)));
                pojo.setDate(cursor.getString(cursor.getColumnIndex(alarm_date)));
                pojo.setTrig(cursor.getLong(cursor.getColumnIndex(alarm_trig)));
                pojo.setUser_id(cursor.getLong(cursor.getColumnIndex(alarm_user)));
                pojo.setRem_id(cursor.getLong(cursor.getColumnIndex(alarm_rem)));
                pojo.setFreq(cursor.getString(cursor.getColumnIndex(alarm_freq)));
                pojo.setDose(cursor.getInt(cursor.getColumnIndex(alarm_dose)));

                cursor.close();
                database.close();
                return pojo;

            } else {

                pojo.setMessage("Error");

                database.close();

                return pojo;

            }


        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            pojo.setMessage(ex.getMessage());
            //
            database.close();
            return pojo;
        }


    }

    public Alarm_pojo getAlarm(Alarm_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor;
        try {
            cursor = database.query(TABLE5, alarm_columns, " _id = ? ", new String[]{String.valueOf(pojo.getId())}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                pojo.setId(cursor.getLong(cursor.getColumnIndex(alarm_id)));
                pojo.setTime(cursor.getString(cursor.getColumnIndex(alarm_time)));
                pojo.setDate(cursor.getString(cursor.getColumnIndex(alarm_date)));
                pojo.setTrig(cursor.getLong(cursor.getColumnIndex(alarm_trig)));
                pojo.setUser_id(cursor.getLong(cursor.getColumnIndex(alarm_user)));
                pojo.setRem_id(cursor.getLong(cursor.getColumnIndex(alarm_rem)));
                pojo.setDose(cursor.getInt(cursor.getColumnIndex(alarm_dose)));
                pojo.setFreq(cursor.getString(cursor.getColumnIndex(alarm_freq)));


                cursor.close();
                database.close();
                return pojo;

            } else {

                pojo.setMessage("Error");

                database.close();

                return pojo;

            }


        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            pojo.setMessage(ex.getMessage());
            //
            database.close();
            return pojo;
        }

    }

    public Ap_pojo getAP(Ap_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor;
        try {
            cursor = database.query(TABLE3, ap_columns, " _id = ? ", new String[]{String.valueOf(pojo.getId())}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                pojo.setId(cursor.getLong(cursor.getColumnIndex(ap_id)));
                pojo.setLocation(cursor.getString(cursor.getColumnIndex(ap_loc)));
                pojo.setComplain(cursor.getString(cursor.getColumnIndex(ap_comp)));
                pojo.setDate(cursor.getString(cursor.getColumnIndex(ap_date)));
                pojo.setTime(cursor.getString(cursor.getColumnIndex(ap_time)));
                pojo.setLon(cursor.getDouble(cursor.getColumnIndex(ap_lon)));
                pojo.setLat(cursor.getDouble(cursor.getColumnIndex(ap_lat)));
                pojo.setUser(cursor.getLong(cursor.getColumnIndex(ap_user)));
                pojo.setTrig(cursor.getLong(cursor.getColumnIndex(ap_trig)));
                cursor.close();
                database.close();
                return pojo;

            } else {

                pojo.setMessage("Error");

                database.close();

                return pojo;

            }


        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            pojo.setMessage(ex.getMessage());
            //
            database.close();
            return pojo;
        }
    }


    public Pres_pojo getFinishPres(Pres_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor;
        try {
            cursor = database.query(TABLE2, pres_columns, " pres_trig = ( SELECT MIN( " + pres_trig + " ) FROM pres WHERE status = '0' )", null, null, null, null);
            //cursor = database.query(TABLE2,pres_columns," pres_trig = ? ",new String[]{String.valueOf(pojo.getTrig())},null,null,null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                /*while (cursor.getInt(cursor.getColumnIndex(pres_status))==1){
                    cursor.moveToNext();
                }*/
                pojo.setId(cursor.getLong(cursor.getColumnIndex(pres_id)));

                pojo.setName(cursor.getString(cursor.getColumnIndex(pres_name)));
                pojo.setTreatment(cursor.getString(cursor.getColumnIndex(pres_treat)));
                pojo.setNumber(cursor.getInt(cursor.getColumnIndex(pres_no)));
                pojo.setDose(cursor.getString(cursor.getColumnIndex(pres_dose)));
                pojo.setFirstdosedate(cursor.getString(cursor.getColumnIndex(pres_dosedate)));
                pojo.setFirstdosetime(cursor.getString(cursor.getColumnIndex(pres_dosetime)));
                pojo.setImage(cursor.getBlob(cursor.getColumnIndex(pres_pic)));
                pojo.setStatus(cursor.getInt(cursor.getColumnIndex(pres_status)));
                pojo.setUserid(cursor.getLong(cursor.getColumnIndex(pres_user)));
                pojo.setTrig(cursor.getLong(cursor.getColumnIndex(pres_trig)));
                pojo.setMessage("successful");

                cursor.close();
                database.close();
                return pojo;


            } else {

                pojo.setMessage("Error");

                database.close();

                return pojo;

            }


        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            pojo.setMessage(ex.getMessage());
            //
            database.close();
            return pojo;
        }
    }

    public Rem_pojo getAlarmFromRem(Alarm_pojo pojo) {
        database = getReadableDatabase();
        Rem_pojo rpojo = new Rem_pojo();
        Cursor cursor;
        try {
            cursor = database.query(TABLE4, rem_columns, " _id = ? ", new String[]{String.valueOf(pojo.getRem_id())}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                rpojo.setId(cursor.getLong(cursor.getColumnIndex(rem_id)));
                rpojo.setTabletname(cursor.getString(cursor.getColumnIndex(rem_name)));
                rpojo.setTablettreatment(cursor.getString(cursor.getColumnIndex(rem_treat)));
                rpojo.setUserid(cursor.getLong(cursor.getColumnIndex(rem_user)));
                cursor.close();
                database.close();
                return rpojo;

            } else {

                pojo.setMessage("Error");

                database.close();

                return rpojo;

            }


        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            pojo.setMessage(ex.getMessage());
            //
            database.close();
            return rpojo;
        }

    }


    public Rem_pojo getRem(Rem_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor;
        try {
            cursor = database.query(TABLE4, rem_columns, " _id = ? ", new String[]{String.valueOf(pojo.getId())}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                pojo.setId(cursor.getLong(cursor.getColumnIndex(rem_id)));
                pojo.setTabletname(cursor.getString(cursor.getColumnIndex(rem_name)));
                pojo.setTablettreatment(cursor.getString(cursor.getColumnIndex(rem_treat)));
                pojo.setUserid(cursor.getLong(cursor.getColumnIndex(rem_user)));
                cursor.close();
                database.close();
                return pojo;

            } else {

                pojo.setMessage("Error");

                database.close();

                return pojo;

            }


        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            pojo.setMessage(ex.getMessage());
            //
            database.close();
            return pojo;
        }
    }


    public Pres_pojo getPres(Pres_pojo pres_pojo) {
        database = getReadableDatabase();
        Cursor cursor;
        try {
            cursor = database.query(TABLE2, pres_columns, " _id = ? ", new String[]{String.valueOf(pres_pojo.getId())}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                pres_pojo.setName(cursor.getString(cursor.getColumnIndex(pres_name)));
                pres_pojo.setTreatment(cursor.getString(cursor.getColumnIndex(pres_treat)));
                pres_pojo.setNumber(cursor.getInt(cursor.getColumnIndex(pres_no)));
                pres_pojo.setDose(cursor.getString(cursor.getColumnIndex(pres_dose)));
                pres_pojo.setFirstdosedate(cursor.getString(cursor.getColumnIndex(pres_dosedate)));
                pres_pojo.setFirstdosetime(cursor.getString(cursor.getColumnIndex(pres_dosetime)));
                pres_pojo.setImage(cursor.getBlob(cursor.getColumnIndex(pres_pic)));
                pres_pojo.setStatus(cursor.getInt(cursor.getColumnIndex(pres_status)));
                pres_pojo.setUserid(cursor.getLong(cursor.getColumnIndex(pres_user)));
                pres_pojo.setTrig(cursor.getLong(cursor.getColumnIndex(pres_trig)));
                pres_pojo.setMessage("successful");
                cursor.close();
                database.close();
                return pres_pojo;

            } else {

                pres_pojo.setMessage("Error");

                database.close();

                return pres_pojo;

            }


        } catch (Exception ex) {
            Log.e("Element", ex.getMessage());
            pres_pojo.setMessage(ex.getMessage());
            //
            database.close();
            return pres_pojo;
        }
    }


    public int update(Signup_pojo signUp_pojo) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(COLUMN_ID, signUp_pojo.getId());
            values.put(COLUMN_FIRST_NAME, signUp_pojo.getFirstname());
            values.put(COLUMN_LAST_NAME, signUp_pojo.getLastname());
            values.put(COLUMN_EMAIL, signUp_pojo.getEmail());
            values.put(COLUMN_PASSWORD, signUp_pojo.getPassword());
            values.put(COLUMN_DOB, signUp_pojo.getDob());
            values.put(COLUMN_IMAGE, signUp_pojo.getImage());

            int i = database.update(TABLE_NAME, values, " _id = ? ", new String[]{String.valueOf(signUp_pojo.getId())});
            database.close();

            return i;
        } catch (Exception e) {
            signUp_pojo.setMessage(e.getMessage());
            database.close();
        }
        database.close();
        return 0;
    }


    public int updateAlarm(Alarm_pojo pojo, Signup_pojo spojo, Rem_pojo rpojo) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(alarm_time, pojo.getTime());
            values.put(alarm_date, pojo.getDate());
            values.put(alarm_trig, pojo.getTrig());
            values.put(alarm_user, spojo.getId());
            values.put(alarm_rem, rpojo.getId());
            values.put(alarm_dose, pojo.getDose());
            values.put(alarm_freq,pojo.getFreq());
            int i = database.update(TABLE5, values, " _id = ? ", new String[]{String.valueOf(pojo.getId())});

            database.close();
            Log.i("freq","Success");

            return i;

        } catch (Exception e) {
            pojo.setMessage(e.getMessage());
            database.close();
            Log.i("freq","failed");
        }
        database.close();
        return 0;
    }

    public int updateTrig(Trigger_pojo pojo,Alarm_pojo apojo){
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(initial_trig, pojo.getInitialtrig());
            values.put(trig, pojo.getTrig());
            values.put(trig_alarm_id, apojo.getId());
            values.put(trig_rem_id, apojo.getRem_id());

            int i = database.update(TABLE6, values, " _id = ? ", new String[]{String.valueOf(pojo.getId())});

            database.close();
            Log.i("Update Trig","Success");

            return i;

        } catch (Exception e) {
            database.close();
            Log.i("Update Trig","failed");
        }
        database.close();
        return 0;

    }

    public int updatepres(Pres_pojo ppojo,Signup_pojo pojo){
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        try{
            values.put(pres_id,ppojo.getId());
            values.put(pres_name,ppojo.getName());
            values.put(pres_treat,ppojo.getTreatment());
            values.put(pres_no,ppojo.getNumber());
            values.put(pres_dose,ppojo.getDose());
            values.put(pres_dosedate,ppojo.getFirstdosedate());
            values.put(pres_dosetime,ppojo.getFirstdosetime());
            values.put(pres_pic,ppojo.getImage());
            values.put(pres_status,ppojo.getStatus());
            values.put(pres_user,pojo.getId());
            values.put(pres_trig,ppojo.getTrig());

            int i = database.update(TABLE2,values," _id = ? ",new String[]{String.valueOf(ppojo.getId())});

            database.close();
            return i;
        }catch (Exception e ){
            ppojo.setMessage(e.getMessage());
            database.close();
        }
        database.close();
        return 0;
    }


    public byte[] getImage(Signup_pojo pojo){
        database = getReadableDatabase();
        Cursor cursor;
        byte[] img;
        try{


            cursor = database.query(TABLE_NAME, allColumns, " _id = ? ", new String[]{String.valueOf(pojo.getId())},null,null,null);
            if (cursor != null && cursor.getCount() > 0 ) {
                cursor.moveToFirst();
                img = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
                cursor.close();
                database.close();
                return img;

            }
            else {
                Log.i("image","No cursor");

            }
        }
        catch (Exception ex){
            Log.e("Element", ex.getMessage());
            pojo.setMessage(ex.getMessage());
            //
            database.close();
        }

        // return User
        return null;
    }

    public int getPressCount(Signup_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor =  database.query(TABLE2,pres_columns," user_id = ? AND status = ? ",new String[]{String.valueOf(pojo.getId()), String.valueOf(0)},null,null,null);

        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public int getFinPressCount(Signup_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor =  database.query(TABLE2,pres_columns," user_id = ? AND status = ? ",new String[]{String.valueOf(pojo.getId()), String.valueOf(1)},null,null,null);

        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public int getApCount(Signup_pojo pojo) {
        database = getReadableDatabase();
        Cursor cursor =  database.query(TABLE3,ap_columns," user_id = ? ",new String[]{String.valueOf(pojo.getId())},null,null,null);

        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }



}
