package com.example.irina.myproject.workers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.irina.myproject.ClasaTest;
import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TestWorker extends AsyncTask<String,Integer,ArrayList<ClasaTest>>
{
    private Context _context;

    public TestWorker(Context context) {
        _context = context;
    }
    @Override
    protected ArrayList<ClasaTest> doInBackground(String... strings) {
        if(strings == null && strings.length==0){
            return new ArrayList<>();
        }
        ArrayList<ClasaTest> testeList = new ArrayList<>();
        String address = String.format("https://api.myjson.com/bins/18pdrs");
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection=(HttpURLConnection)url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray testsArray = jsonObject.getJSONArray("tests");
            for(int i=0;i<testsArray.length();i++){
                ClasaTest test = new ClasaTest();
                JSONObject t = testsArray.getJSONObject(i);
                test.id=t.getInt("id");
                test.titlu=t.getString("titlu");
                test.categorie=t.getString("categorie");
                test.timp=t.getInt("timp");
                test.acces=t.getString("acces");
                test.parcurgere=t.getString("parcurgere");
                test.nrRulari=t.getInt("nr_rulari");
                test.amestecareIntrebari=t.getInt("amestecare_q");
                test.amestecareRaspunri=t.getInt("amestecare_r");
                test.emailProfeor=t.getString("email_prof");
                test.origine=t.getString("origin");
                testeList.add(test);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(ClasaTest in : testeList){
            System.out.println("test"+in);
        }
        return testeList;
    }

    @Override
    protected void onPostExecute(ArrayList<ClasaTest> clasaTests) {
        DatabaseHelper helper = new DatabaseHelper(_context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DatabaseContract.TestTable.TABLE_NAME +
                " WHERE " + DatabaseContract.TestTable.COLUMN_NAME_ORIGIN +
                " like 'json'");

        for(ClasaTest test:clasaTests ) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_ID,
                    test.id);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_TITLU,
                    test.titlu);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_CATEGORIE,
                    test.categorie);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_TIMP,
                    test.timp);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_ACCES,
                    test.acces);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_PARCURGERE,
                    test.parcurgere);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_AMESTECAREQ,
                    test.amestecareIntrebari);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_AMESTECARER,
                    test.amestecareRaspunri);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_NRRULARI,
                    test.nrRulari);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_EMAILPROF,
                    test.emailProfeor);
            cv.put(DatabaseContract.TestTable.COLUMN_NAME_ORIGIN,
                    test.origine);

            db.insert(DatabaseContract.TestTable.TABLE_NAME,
                    null, cv);
        }
    }
}
