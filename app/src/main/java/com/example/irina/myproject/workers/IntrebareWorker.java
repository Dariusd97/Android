package com.example.irina.myproject.workers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.irina.myproject.ClasaTest;
import com.example.irina.myproject.Intrebare;
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

public class IntrebareWorker extends AsyncTask<String,Integer,ArrayList<Intrebare>>{

    private Context _context;

    public IntrebareWorker(Context context) {
        _context = context;
    }
    @Override
    protected ArrayList<Intrebare> doInBackground(String... strings) {
        if(strings == null && strings.length ==0){
            return new ArrayList<>();
        }
        ArrayList<Intrebare>listaIntrebari = new ArrayList<>();
        String address = String.format("https://api.myjson.com/bins/19qod4");
        HttpURLConnection connection=null;
        try {
            URL url = new URL(address);
            connection=(HttpURLConnection)url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line=null;

            while((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(result);


            JSONArray testsArray = jsonObject.getJSONArray("tests");

            for(int i=0;i<testsArray.length();i++){

                JSONObject t = testsArray.getJSONObject(i);

                JSONArray intrebareArray =t.getJSONArray("questions");

                for(int j=0;j<intrebareArray.length();j++){
                    Intrebare intr = new Intrebare();
                    JSONObject q = intrebareArray.getJSONObject(j);
                    intr.setId(q.getInt("id"));
                    intr.setIntrebare(q.getString("text_intrebare"));
                    intr.setTipIntrebare(q.getString("tip_intrebare"));
                    intr.setTimp(q.getString("timp"));
                    intr.setPunctaj(q.getInt("punctaj"));
                    intr.setIdTest(q.getInt("id_test"));
                    intr.setOrigine(q.getString("origin"));
                    listaIntrebari.add(intr);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(Intrebare in : listaIntrebari){
            System.out.println("intrebare"+in);
        }
        return listaIntrebari;
    }
    @Override
    protected void onPostExecute(ArrayList<Intrebare> intrebareArrayList) {
        DatabaseHelper helper = new DatabaseHelper(_context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DatabaseContract.IntrebareTable.TABLE_NAME +
                " WHERE " + DatabaseContract.IntrebareTable.COLUMN_NAME_ORIGIN +
                " like 'json'");

        for(Intrebare intr:intrebareArrayList ) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.IntrebareTable.COLUMN_NAME_IDQ,
                    intr.getId());
            cv.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TEXT,
                    intr.getIntrebare());
            cv.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TIP,
                    intr.getTipIntrebare());
            cv.put(DatabaseContract.IntrebareTable.COLUMN_NAME_TIMP,
                    intr.getTimp());
            cv.put(DatabaseContract.IntrebareTable.COLUMN_NAME_IDTEST,
                    intr.getIdTest());
            cv.put(DatabaseContract.IntrebareTable.COLUMN_NAME_PUNCTAJ,
                    intr.getPunctaj());
            cv.put(DatabaseContract.IntrebareTable.COLUMN_NAME_ORIGIN,
                    intr.getOrigine());


            db.insert(DatabaseContract.IntrebareTable.TABLE_NAME,
                    null, cv);
        }
    }
}
