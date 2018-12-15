package com.labAdvance.carRental.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.labAdvance.carRental.models.Car;
import com.labAdvance.carRental.models.LoginUser;
import com.labAdvance.carRental.models.RegisterModel;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String CarTable = "CAR";
    private static final String LoginTable = "LOGIN";
    private static final String RegisterTable = "REGISTER";
    /**
     * Create and drop Car Table
     */
    private static final String carCreateTableQuery = "Create table " + CarTable + "( ID INTEGER primary Key AUTOINCREMENT , " +
            "year INTEGER ," +
            "make TEXT ," +
            "model TEXT ," +
            "distance TEXT ," +
            "price TEXT ," +
            "accidents TEXT , " +
            "offers TEXT );";
    private static final String dropCar = "Drop Table If EXISTS " + CarTable;
    /**
     * create and drop Login table
     */
    private static final String loginQuery = "Create Table " + LoginTable + "(ID INTEGER Primary Key AUTOINCREMENT , " +
            "Email TEXT ," +
            "Password TEXT ," +
            "Stay TEXT );";
    private static final String dropLogin = "Drop Table If EXISTS " + LoginTable;
    /**
     * create and drop Register Table
     */
    private static final String registerQuery = "Create Table " + RegisterTable + "(ID INTEGER Primary Key AUTOINCREMENT , " +
            "Email TEXT ," +
            "Password TEXT ," +
            "FullName TEXT );";
    private static final String dropRegister = "Drop Table If EXISTS " + RegisterTable;


    public SqliteHelper(Context context) {
        super(context, "car.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(carCreateTableQuery);
        db.execSQL(loginQuery);
        db.execSQL(registerQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropCar);
        db.execSQL(dropLogin);
        db.execSQL(dropRegister);

    }

    /**
     * void
     *
     * @param car insert data to Car Table
     *            take input from server
     */
    public void insertCarData(Car car) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CarTable,null,null) ;
        ContentValues values = new ContentValues();
        values.put("year", car.getYear());
        values.put("make", car.getMake());
        values.put("model", car.getModel());
        values.put("distance", car.getDistance());
        values.put("price", car.getPrice());
        values.put("accidents", "" + car.isAccidents());
        values.put("offers", "" + car.isOffers());
        db.insert(CarTable, null, values);

    }

    /**
     * add Register users
     *
     * @param registerModel
     */
    public void insertRegsiterData(RegisterModel registerModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", registerModel.getEmail());
        values.put("Password", registerModel.getPassword());
        values.put("FullName", registerModel.getFullName());
        db.insert(RegisterTable, null, values);
    }

    /**
     * add login users
     *
     * @param loginUser
     */
    public void insertLoginData(LoginUser loginUser) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", loginUser.getEmail());
        values.put("Password", loginUser.getPassword());
        values.put("Stay", "" + loginUser.isStayLogged());
        db.insert(LoginTable, null, values);
    }

    /**
     * check if email is used before
     * @param email
     * @return
     */
    public boolean checkExistingEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("select * from " + RegisterTable + " where " + " Email " + "='" + email + "'", null);
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    /**
     * get all records in Register Table
     * @return
     */
    public Cursor getallData(){
        Cursor cursor = getWritableDatabase().rawQuery("Select * From "+ LoginTable,null);
        return cursor ;

    }

    /**
     * delete all records in Register Table
     */
    public void deleteAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase() ;
        try {
          //  db.execSQL("Delete From "+ RegisterTable,null);
            db.delete(RegisterTable,null,null);

        }catch(Exception e){
            Log.d("TA", "deleteAllRecords: "+e.toString());
        }
    }

    /**
     * check if the Email and Password is in the database
     * @param user
     * @return boolean
     */
    public boolean checkEmailAndPassword(LoginUser user){
        SQLiteDatabase db = this.getReadableDatabase() ;
        Cursor cursor  = db.rawQuery("Select * From "+LoginTable + " Where " + " Email " + "='" + user.getEmail() + "'",null);
        cursor.moveToFirst() ;
        if(cursor.getCount() > 0){
            String password = cursor.getString(2) ;
            if(user.getPassword().equals(cursor.getString(2))){
                return true ;
            }
            else{
                return false ;
            }
        }
//        while(cursor.moveToNext()){
//            String password = cursor.getString(2) ;
//            if(user.getPassword().equals(cursor.getString(2))){
//                return true ;
//            }
//            else{
//                return false ;
//            }
//        }
        return false ;
    }

    /**
     * when user checked to stay logged in
     * @param user
     */
    public void keepUserLoggedIn(LoginUser user){
        switchUsers();
        SQLiteDatabase db =  getWritableDatabase() ;
        boolean falsee = false ;
        ContentValues values = new ContentValues() ;
        values.put("Email" , user.getEmail());
        values.put("Password" ,user.getPassword());
        values.put("Stay" , ""+user.isStayLogged());
        db.update(LoginTable,values ,  " Email " + "='" + user.getEmail() + "'" ,null) ;
        Log.d("Tag", "keepUserLoggedIn: ");
    }

    /**
     * this method in make the user how just logged in
     * stay the only user who will keep the app logged in
     */
    private void switchUsers(){
        SQLiteDatabase db =  getWritableDatabase() ;
        try{
            db.rawQuery("update " +LoginTable +" Set Stay " + "='" + false + "'", null);
        }catch(Exception e){
            Log.d("Tag", "switchUsers: ");
        }
        Cursor cursor = db.rawQuery("Select * From " + LoginTable ,null) ;
        while(cursor.moveToNext()){
            Log.d("Tag", "switchUsers: ");
            String email  = cursor.getString(1) ;
            String Stay = cursor.getString(3);
            String password = cursor.getString(2) ;
        }
    }

    /**
     * check if the user want to keep log in
     * @return
     */

    public Boolean isUserLoggedIn(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From " + LoginTable + " Where Stay " + "='" + true + "'", null);
        if(cursor.getCount() > 0){
            return true ;
        }
        return false;



    }



}
