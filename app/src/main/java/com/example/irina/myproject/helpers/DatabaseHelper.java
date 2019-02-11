package com.example.irina.myproject.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.irina.myproject.contracts.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseContract.DB_NAME,
                null, DatabaseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseContract.StudentTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.ProfesorTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.MaterieTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.RandMaterieTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.TestTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.IntrebareTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.RaspunsTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.TestStudentTable.CREATE_TABLE);

        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Algebra')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Analiza datelor')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Analiza statistica multidimensionala')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Baze de date')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Bazele cercetarilor operationale')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Bazele programarii calculatoarelor')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Bazele statisticii')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Bazele tehnologiei informatiei')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Cercetari operationale')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Cibernetica sistemelor economice')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Demografie')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Dinamica sistemelor economice')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Dispozitive si aplicatii mobile')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Econometrie')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Economia si gestiunea riscului')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Economie')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Engleza')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Finante')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Inteligenta computationala in economie')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Management')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Metode statistice in managementul calitatii')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Microeconomie cantitativa')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Macroeconomie cantitativa')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Modelarea si vizualizarea geospatiala a datelor statistice')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Multimedia')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Probabilitati si statistica matematica')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Programare orientata obiect')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Retele de calculatoare')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Sondaje si anchete statistice')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Statistica macroeconomica')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Marketing')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Tehnologii Web')");
        sqLiteDatabase.execSQL("INSERT INTO materii(descriere) VALUES('Teoria jocurilor')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DatabaseContract.StudentTable.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.StudentTable.CREATE_TABLE);

        sqLiteDatabase.execSQL(DatabaseContract.ProfesorTable.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.ProfesorTable.CREATE_TABLE);

        sqLiteDatabase.execSQL(DatabaseContract.MaterieTable.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.MaterieTable.CREATE_TABLE);

        sqLiteDatabase.execSQL(DatabaseContract.RandMaterieTable.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.RandMaterieTable.CREATE_TABLE);

        sqLiteDatabase.execSQL(DatabaseContract.TestTable.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.TestTable.CREATE_TABLE);

        sqLiteDatabase.execSQL(DatabaseContract.IntrebareTable.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.IntrebareTable.CREATE_TABLE);

        sqLiteDatabase.execSQL(DatabaseContract.RaspunsTable.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.RaspunsTable.CREATE_TABLE);

        sqLiteDatabase.execSQL(DatabaseContract.TestStudentTable.DROP_TABLE);
        sqLiteDatabase.execSQL(DatabaseContract.TestStudentTable.CREATE_TABLE);

    }
}
