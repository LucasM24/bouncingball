package com.example.bouncingball.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DbUser extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2 ;
    private static final String DATABASE_NOMBRE = "players.db";
    public static final String TABLE_USER = "t_users";
    public static final String Campo_ID = "usuario";
    public static final String Campo_CLAVE ="clave";
    public static final String Campo_EMAIL ="email";
    public static final String Campo_PUNTAJE ="puntaje";


    public DbUser(Context context){
        super(context,DATABASE_NOMBRE,null,DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase sqLiteDatabase) {

     sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_USER+ "("+
                     "usuario varchar(20) PRIMARY KEY ,"+
                      "clave varchar(12) NOT NULL," +
                      "email varchar(20) ,"+
                      "puntaje int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USER);
        onCreate(sqLiteDatabase);
    }
}
