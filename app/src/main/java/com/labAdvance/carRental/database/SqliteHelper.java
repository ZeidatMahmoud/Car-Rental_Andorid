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

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String CarTable = "CAR";
    private static final String LoginTable = "LOGIN";
    private static final String RegisterTable = "REGISTER";
    private static final String FavTable = "FAVORITE" ;
    private static final String ReservationTable = "RESERVATION" ;
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
            "FirstName TEXT ," +
            "LastName TEXT ," +
            "Gender TEXT ," +
            "Country TEXT , " +
            "City TEXT ," +
            "Phone  TEXT," +
            "Profile BLOB );";

    private static final String dropRegister = "Drop Table If EXISTS " + RegisterTable;

    private static final String createFavoiriteTableQuery = "Create table " + FavTable + "( ID INTEGER primary Key AUTOINCREMENT , " +
            "year INTEGER ," +
            "make TEXT ," +
            "model TEXT ," +
            "distance TEXT ," +
            "price TEXT ," +
            "accidents TEXT , " +
            "offers TEXT );";

    private static final String dropFav = "Drop Table If EXISTS " + FavTable;

    private static final String carateReservationTable = "Create table " + ReservationTable + "( ID INTEGER primary Key AUTOINCREMENT , " +
            "year INTEGER ," +
            "make TEXT ," +
            "model TEXT ," +
            "distance TEXT ," +
            "price TEXT ," +
            "accidents TEXT , " +
            "offers TEXT ," +
            "User TEXT);";

    private static final String dropReservation = "Drop Table If EXISTS " + FavTable;

    public SqliteHelper(Context context) {
        super(context, "BluCar.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(carCreateTableQuery);
        db.execSQL(loginQuery);
        db.execSQL(registerQuery);
        db.execSQL(createFavoiriteTableQuery);
        db.execSQL(carateReservationTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropCar);
        db.execSQL(dropLogin);
        db.execSQL(dropRegister);
        db.execSQL(dropFav);
        db.execSQL(dropReservation);

    }

    /**
     * void
     *
     * @param car insert data to Car Table
     *            take input from server
     */
    public void insertCarData(Car car) {
        SQLiteDatabase db = getWritableDatabase();
        //db.delete(CarTable, null, null);
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
    public void deleteAllCars(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete From "+ CarTable );

    }

    public ArrayList<Car> getAllCars() {
        /**
         *  "year INTEGER ," +
         *             "make TEXT ," +
         *             "model TEXT ," +
         *             "distance TEXT ," +
         *             "price TEXT ," +
         *             "accidents TEXT , " +
         *             "offers TEXT );";
         */
        ArrayList<Car> list = new ArrayList();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From " + CarTable, null);
        while (cursor.moveToNext()) {
            list.add(new Car(cursor.getInt(1),cursor.getString(2) ,cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    Boolean.valueOf(cursor.getString(6)),Boolean.valueOf(cursor.getString(7))));

        }

        return list;
    }

    /**
     * add Register users
     *
     * @param registerModel
     */
    public boolean insertRegsiterData(RegisterModel registerModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", registerModel.getEmail());
        values.put("Password", registerModel.getPassword());
        values.put("FirstName", registerModel.getFirstName());
        values.put("LastName", registerModel.getLastName());
        values.put("Gender", registerModel.getGender());
        values.put("Country", registerModel.getCountry());
        values.put("City", registerModel.getCity());
        values.put("Phone", registerModel.getPhone());
        values.put("Profile", registerModel.getProfile());
        Long result = db.insert(RegisterTable, null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * get the password for the email parameter
     * for Login use
     *
     * @param email
     * @return
     */
    public String getThisEmailPassword(String email) {
        String password = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + RegisterTable + " where " + " Email " + "='" + email + "'", null);
        if (cursor.getCount() == 0) {
            return "nil";
        } else {
            while (cursor.moveToNext()) {
                password = cursor.getString(2);
            }

            return password;
        }

    }

    /**
     * get user data for this email
     *
     * @param email
     * @return
     */
    public RegisterModel getUserData(String email) {
        RegisterModel registerModel = new RegisterModel();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + RegisterTable + " where " + " Email " + "='" + email + "'", null);
        while (cursor.moveToNext()) {
            registerModel = new RegisterModel(cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(2),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getBlob(9));

        }


        return registerModel;
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
     *
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
     *
     * @return
     */
    public Cursor getallData() {
        Cursor cursor = getWritableDatabase().rawQuery("Select * From " + LoginTable, null);
        return cursor;

    }

    /**
     * delete all records in Register Table
     */
    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            //  db.execSQL("Delete From "+ RegisterTable,null);
            db.delete(RegisterTable, null, null);

        } catch (Exception e) {
            Log.d("TA", "deleteAllRecords: " + e.toString());
        }
    }

    /**
     * check if the Email and Password is in the database
     *
     * @param user
     * @return boolean
     */
    public boolean checkEmailAndPassword(LoginUser user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From " + LoginTable + " Where " + " Email " + "='" + user.getEmail() + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String password = cursor.getString(2);
            if (user.getPassword().equals(cursor.getString(2))) {
                return true;
            } else {
                return false;
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
        return false;
    }

    /**
     * when user checked to stay logged in
     *
     * @param user
     */
    public void keepUserLoggedIn(LoginUser user) {
        switchUsers();
        SQLiteDatabase db = getWritableDatabase();
        boolean falsee = false;
        ContentValues values = new ContentValues();
        values.put("Email", user.getEmail());
        values.put("Password", user.getPassword());
        values.put("Stay", "" + user.isStayLogged());
        db.update(LoginTable, values, " Email " + "='" + user.getEmail() + "'", null);
        Log.d("Tag", "keepUserLoggedIn: ");
    }

    /**
     * this method in make the user how just logged in
     * stay the only user who will keep the app logged in
     */
    private void switchUsers() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.rawQuery("update " + LoginTable + " Set Stay " + "='" + false + "'", null);
        } catch (Exception e) {
            Log.d("Tag", "switchUsers: ");
        }
        Cursor cursor = db.rawQuery("Select * From " + LoginTable, null);
        while (cursor.moveToNext()) {
            Log.d("Tag", "switchUsers: ");
            String email = cursor.getString(1);
            String Stay = cursor.getString(3);
            String password = cursor.getString(2);
        }
    }

    /**
     * check if the user want to keep log in
     *
     * @return
     */

    public Boolean isUserLoggedIn() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From " + LoginTable + " Where Stay " + "='" + true + "'", null);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;


    }

    public void insertFavoriteCars(Car car){
        SQLiteDatabase db = getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put("year", car.getYear());
        values.put("make", car.getMake());
        values.put("model", car.getModel());
        values.put("distance", car.getDistance());
        values.put("price", car.getPrice());
        values.put("accidents", "" + car.isAccidents());
        values.put("offers", "" + car.isOffers());
        db.insert(FavTable, null, values);
     }

     public void removeFavoirite(Car car){
        SQLiteDatabase db = getWritableDatabase() ;
        try{
            db.delete(FavTable ,"distance=?",new String[]{car.getDistance()}) ;

        }
        catch(Exception e){
            Log.d("TAG", "removeFavoirite: ");

        }

     }

     public ArrayList<Car> getAllFavorite(){
        ArrayList<Car> list = new ArrayList<>() ;
        SQLiteDatabase db  = getReadableDatabase() ;
        Cursor cursor = db.rawQuery("Select * From " + FavTable,null) ;
         while (cursor.moveToNext()) {
             list.add(new Car(cursor.getInt(1),cursor.getString(2) ,cursor.getString(3),
                     cursor.getString(4),
                     cursor.getString(5),
                     Boolean.valueOf(cursor.getString(6)),Boolean.valueOf(cursor.getString(7))));

         }

         return list ;
     }

    public void insertReserverdCars(Car car ,String name){
        SQLiteDatabase db = getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put("year", car.getYear());
        values.put("make", car.getMake());
        values.put("model", car.getModel());
        values.put("distance", car.getDistance());
        values.put("price", car.getPrice());
        values.put("accidents", "" + car.isAccidents());
        values.put("offers", "" + car.isOffers());
        values.put("User" ,name);
        db.insert(ReservationTable, null, values);
    }

    public void removeReserved(Car car){
        SQLiteDatabase db = getWritableDatabase() ;
        try{
             db.delete(ReservationTable ,"distance=?",new String[] {car.getDistance()}) ;

        }
        catch(Exception e){
            Log.d("TAG", "removeFavoirite: ");

        }

    }
    public ArrayList<Car> getAllReserverd(){
        ArrayList<Car> list = new ArrayList<>() ;
        SQLiteDatabase db  = getReadableDatabase() ;
        Cursor cursor = db.rawQuery("Select * From " + ReservationTable,null) ;
        while (cursor.moveToNext()) {
            list.add(new Car(cursor.getInt(1),cursor.getString(2) ,cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    Boolean.valueOf(cursor.getString(6)),Boolean.valueOf(cursor.getString(7))));

        }

        return list ;
    }

    public ArrayList<Car> getAllOffers(){
        ArrayList<Car> list = new ArrayList<>() ;
        SQLiteDatabase db  = getReadableDatabase() ;
        Cursor cursor = db.rawQuery("Select * From " + CarTable + " Where offers " + "='" + true + "'",null) ;
        while (cursor.moveToNext()) {
            list.add(new Car(cursor.getInt(1),cursor.getString(2) ,cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    Boolean.valueOf(cursor.getString(6)),Boolean.valueOf(cursor.getString(7))));

        }

        return list ;
    }

    public void updateRegisterData(RegisterModel registerModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", registerModel.getEmail());
        values.put("Password", registerModel.getPassword());
        values.put("FirstName", registerModel.getFirstName());
        values.put("LastName", registerModel.getLastName());
        values.put("Gender", registerModel.getGender());
        values.put("Country", registerModel.getCountry());
        values.put("City", registerModel.getCity());
        values.put("Phone", registerModel.getPhone());
        values.put("Profile", registerModel.getProfile());

        try{
            db.update(RegisterTable ,values ,"email=?" ,new String[]{registerModel.getEmail()}) ;

        }
        catch (Exception e){
            Log.d("TAGGGG", "updateRegisterData: ");
        }

    }






}
