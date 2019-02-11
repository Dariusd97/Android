package com.example.irina.myproject.contracts;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String DB_NAME = "myproject.db";
    public static final int DB_VERSION =1;

    //STUDENTI

    public class StudentTable implements BaseColumns{
        public static final String TABLE_NAME = "studenti";

        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PAROLA = "parola";
        public static final String COLUMN_NAME_AN = "an";
        public static final String COLUMN_NAME_GRUPA = "grupa";
        public static final String COLUMN_NAME_SERIE = "serie";
        public static final String COLUMN_NAME_ORIGIN = "origin";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_USERNAME + " TEXT, " +
                        COLUMN_NAME_EMAIL + " TEXT, " +
                        COLUMN_NAME_PAROLA + " TEXT, " +
                        COLUMN_NAME_AN + " TEXT, " +
                        COLUMN_NAME_GRUPA + " INTEGER, " +
                        COLUMN_NAME_SERIE + " TEXT, " +
                        COLUMN_NAME_ORIGIN + " TEXT)";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    //PROFESORI

    public class ProfesorTable implements BaseColumns{
        public static final String TABLE_NAME = "profesori";

      //  public static final String COLUMN_NAME_IDPROF = "id_prof";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PAROLA = "parola";
        public static final String COLUMN_NAME_ORIGIN = "origin";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_EMAIL + " TEXT INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_USERNAME + " TEXT, " +
                        COLUMN_NAME_PAROLA + " TEXT, " +
                        COLUMN_NAME_ORIGIN + " TEXT)";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    //MATERII

    public class MaterieTable implements BaseColumns{
        public static final String TABLE_NAME = "materii";

        public static final String COLUMN_NAME_DESCRIERE = "descriere";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME_DESCRIERE + " TEXT INTEGER PRIMARY KEY)";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    //RAND-MATERIE

    public class RandMaterieTable implements BaseColumns{
        public static final String TABLE_NAME = "rand_materie";

        public static final String COLUMN_NAME_EMAIL = "email_prof";
        public static final String COLUMN_NAME_DESCRIERE = "mat_descriere";
        public static final String COLUMN_NAME_ORIGINE = "origine";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_EMAIL + " TEXT, " +
                        COLUMN_NAME_DESCRIERE + " TEXT, " +
                        COLUMN_NAME_ORIGINE + " TEXT, " +
                        "FOREIGN KEY(email_prof) REFERENCES profesori(email), " +
                        "FOREIGN KEY(mat_descriere) REFERENCES materii(descriere))";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    //TEST

    public class TestTable implements BaseColumns{
        public static final String TABLE_NAME = "teste";

        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_TITLU = "titlu";
        public static final String COLUMN_NAME_CATEGORIE = "categorie";
        public static final String COLUMN_NAME_TIMP = "timp";
        public static final String COLUMN_NAME_ACCES = "acces";
        public static final String COLUMN_NAME_PARCURGERE = "parcurgere";
        public static final String COLUMN_NAME_NRRULARI = "nr_rulari";
        public static final String COLUMN_NAME_AMESTECAREQ = "amestecare_q";
        public static final String COLUMN_NAME_AMESTECARER = "amstecare_r";
        public static final String COLUMN_NAME_EMAILPROF ="email_prof";
        public static final String COLUMN_NAME_ORIGIN = "origin";


        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_TITLU + " TEXT, " +
                        COLUMN_NAME_CATEGORIE + " TEXT, " +
                        COLUMN_NAME_TIMP + " INTEGER, " +
                        COLUMN_NAME_ACCES + " TEXT, " +
                        COLUMN_NAME_PARCURGERE + " TEXT, " +
                        COLUMN_NAME_NRRULARI + " INTEGER, " +
                        COLUMN_NAME_AMESTECAREQ + " INTEGER, " +
                        COLUMN_NAME_AMESTECARER + " INTEGER, " +
                        COLUMN_NAME_EMAILPROF + " TEXT, " +
                        COLUMN_NAME_ORIGIN + " TEXT)";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    //INTREBARE

    public class IntrebareTable implements BaseColumns{
        public static final String TABLE_NAME = "intrebari";

        public static  final String COLUMN_NAME_IDQ = "id_q";
        public static final String COLUMN_NAME_TEXT = "text_intrebare";
        public static final String COLUMN_NAME_TIP = "tip_intrebare";
        public static final String COLUMN_NAME_TIMP = "timp";
        public static final String COLUMN_NAME_IDTEST = "id_test";
        public static final String COLUMN_NAME_PUNCTAJ = "punctaj";
        public static final String COLUMN_NAME_ORIGIN = "origin";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME_IDQ + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_TEXT + " TEXT, " +
                        COLUMN_NAME_TIP + " TEXT, " +
                        COLUMN_NAME_TIMP + " TEXT, " +
                        COLUMN_NAME_IDTEST + " INTEGER, " +
                        COLUMN_NAME_PUNCTAJ + " INTEGER, " +
                        COLUMN_NAME_ORIGIN + " TEXT)";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    //RASPUNS

    public class RaspunsTable implements BaseColumns {
        public static final String TABLE_NAME = "raspunsuri";

        public static final String COLUMN_NAME_TEXT = "text_rasp";
        public static final String COLUMN_NAME_COD = "cod_rasp";
        public static final String COLUMN_NAME_VERIFICARE = "verificare";
        public static final String COLUMN_NAME_IDQ = "id_intrebare";
        public static final String COLUMN_NAME_ORIGIN = "origin";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME_COD + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_TEXT + " TEXT, " +
                        COLUMN_NAME_VERIFICARE + " INTEGER, " +
                        COLUMN_NAME_IDQ + " INTEGER, " +
                        COLUMN_NAME_ORIGIN + " TEXT)";


        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public class TestStudentTable implements BaseColumns{
        public static final String TABLE_NAME = "test_student";

        public static final String COLUMN_NAME_EMAIL_STUDENT="email_student";
        public static final String COLUMN_NAME_ID_TEST = "id_test";
        public static final String COLUMN_NAME_ORIGIN = "origin";
        public static final String COLUMN_NAME_PUNCTAJ = "punctaj";

        public static final String CREATE_TABLE=
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_EMAIL_STUDENT + " TEXT, " +
                        COLUMN_NAME_ID_TEST + " INTEGER, " +
                        COLUMN_NAME_ORIGIN + " TEXT, " +
                        COLUMN_NAME_PUNCTAJ + " INTEGER, " +
                        "FOREIGN KEY(email_student) REFERENCES studenti(email), " +
                        "FOREIGN KEY(id_test) REFERENCES teste(ID))";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}

