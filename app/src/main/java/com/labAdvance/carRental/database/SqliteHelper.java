package com.labAdvance.carRental.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.labAdvance.carRental.models.Car;
import com.labAdvance.carRental.models.LoginUser;
import com.labAdvance.carRental.models.RegisterModel;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String CarTable = "CAR";
    private static final String LoginTable = "LOGIN";
    private static final String RegisterTable  = "REGISTER" ;
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
    private static final String loginQuery = "Create Table "+ LoginTable + "(ID INTEGER Primary Key AUTOINCREMENT , " +
            "Email TEXT ," +
            "Password TEXT ," +
            "Stay TEXT );";
    private static final String dropLogin = "Drop Table If EXISTS " + LoginTable;
    /**
     * create and drop Register Table
     */
    private static final String registerQuery  ="Create Table "+ RegisterTable + "(ID INTEGER Primary Key AUTOINCREMENT , " +
            "Email TEXT ," +
            "Password TEXT ," +
            "FullName TEXT );";
    private static final String dropRegister = "Drop Table If EXISTS " + RegisterTable ;



    public SqliteHelper(Context context) {
        super(context, "car-rental.db", null, 1);
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
     * @param car
     * insert data to Car Table
     * take input from server
     */
    public void insertCarData(Car car) {
        SQLiteDatabase db = getWritableDatabase();
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
     * @param registerModel
     */
    public void insertRegsiterData(RegisterModel registerModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email",  registerModel.getEmail());
        values.put("Password", registerModel.getPassword());
        values.put("FullName" ,registerModel.getFullName());
        db.insert(RegisterTable , null, values) ;
    }

    /**
     * add login users
     * @param loginUser
     */
    public void insertLoginData(LoginUser loginUser){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email",  loginUser.getEmail());
        values.put("Password", loginUser.getPassword());
        values.put("Stay" ,""+loginUser.isStayLogged());
        db.insert(LoginTable, null, values) ;
    }


}
