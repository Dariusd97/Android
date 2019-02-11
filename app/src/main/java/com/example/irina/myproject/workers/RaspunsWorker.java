package com.example.irina.myproject.workers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.irina.myproject.Raspuns;
import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;

public class RaspunsWorker extends AsyncTask<String,Integer,ArrayList<Raspuns>> {

    private Context _context;

    public RaspunsWorker(Context context) {
        _context = context;
    }
    @Override
    protected ArrayList<Raspuns> doInBackground(String... strings) {
        if(strings == null && strings.length ==0){
            return new ArrayList<>();
        }

        ArrayList<Raspuns> listaRaspunsuri = new ArrayList<>();
        String address =String.format("https://api.myjson.com/bins/19qod4");
        HttpURLConnection connection=null;
        try {
            URL url = new URL(address);
            connection= (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            JSONObject jsonObjectTest = new JSONObject(result);
            JSONArray testeArray = jsonObjectTest.getJSONArray("tests");
            for(int i=0;i<testeArray.length();i++){

                JSONObject jsonObjectIntrebari = testeArray.getJSONObject(i);
                JSONArray intrebareArray = jsonObjectIntrebari.getJSONArray("questions");

                for(int j=0;j<intrebareArray.length();j++){
                    JSONObject jsonObjectRaspunsuri = intrebareArray.getJSONObject(j);
                    JSONArray raspunsArray = jsonObjectRaspunsuri.getJSONArray("answers");
                    for(int k=0;k<raspunsArray.length();k++){
                        Raspuns raspuns = new Raspuns();
                        JSONObject a = raspunsArray.getJSONObject(k);
                        raspuns.setCodRasp(a.getInt("id"));
                        raspuns.setTextRasp(a.getString("text_rasp"));
                        raspuns.setCorect(a.getInt("verificare"));
                        raspuns.setIdIntrebare(a.getInt("id_question"));
                        raspuns.setOrigine(a.getString("origin"));
                        listaRaspunsuri.add(raspuns);
                    }

                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(Raspuns in : listaRaspunsuri){
            System.out.println("rasouns"+in);
        }
        return listaRaspunsuri;
    }
    @Override
    protected void onPostExecute(ArrayList<Raspuns> raspunsArrayList) {
        DatabaseHelper helper = new DatabaseHelper(_context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DatabaseContract.RaspunsTable.TABLE_NAME +
                " WHERE " + DatabaseContract.RaspunsTable.COLUMN_NAME_ORIGIN +
                " like 'json'");

        for(Raspuns intr:raspunsArrayList ) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.RaspunsTable.COLUMN_NAME_COD,
                    intr.getCodRasp());
            cv.put(DatabaseContract.RaspunsTable.COLUMN_NAME_TEXT,
                    intr.getTextRasp());
            cv.put(DatabaseContract.RaspunsTable.COLUMN_NAME_VERIFICARE,
                    intr.getCorect());
            cv.put(DatabaseContract.RaspunsTable.COLUMN_NAME_IDQ,
                    intr.getIdIntrebare());
            cv.put(DatabaseContract.RaspunsTable.COLUMN_NAME_ORIGIN,
                    intr.getOrigine());

            db.insert(DatabaseContract.RaspunsTable.TABLE_NAME,
                    null, cv);
        }
    }
}
