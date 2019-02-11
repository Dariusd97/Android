package com.example.irina.myproject.workers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.example.irina.myproject.Profesor;
import com.example.irina.myproject.RandMaterie;
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

public class RandMaterieWorker extends AsyncTask<String,Integer,ArrayList<RandMaterie>> {

    private Context _context;

    public RandMaterieWorker(Context context) {
        _context = context;
    }
    @Override
    protected ArrayList<RandMaterie> doInBackground(String... strings) {
        if(strings == null && strings.length ==0){
            return new ArrayList<>();
        }

        ArrayList<RandMaterie> listaRandMaterie = new ArrayList<>();
        String address = String.format("https://api.myjson.com/bins/ty1oo");
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
            JSONObject jsonObjectRandMaterie = new JSONObject(result);
            JSONArray randMaterieJSONArray = jsonObjectRandMaterie.getJSONArray("rand_materie");
            for(int i=0;i<randMaterieJSONArray.length();i++){
                RandMaterie materie = new RandMaterie();
                JSONObject p = randMaterieJSONArray.getJSONObject(i);
                materie.setEmailProfesor(p.getString("email_profesor"));
                materie.setDescriere(p.getString("descriere"));
                materie.setOrigine(p.getString("origine"));
                listaRandMaterie.add(materie);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(RandMaterie in : listaRandMaterie){
            System.out.println("prof"+in.toString());
        }
        return listaRandMaterie;
    }
    @Override
    protected void onPostExecute(ArrayList<RandMaterie> randMaterieArrayList) {
        DatabaseHelper helper = new DatabaseHelper(_context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DatabaseContract.RandMaterieTable.TABLE_NAME +
                " WHERE " + DatabaseContract.RandMaterieTable.COLUMN_NAME_ORIGINE +
                " like 'json'");

        for(RandMaterie r:randMaterieArrayList ) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.RandMaterieTable.COLUMN_NAME_EMAIL,
                    r.getEmailProfesor());
            cv.put(DatabaseContract.RandMaterieTable.COLUMN_NAME_DESCRIERE,
                    r.getDescriere());
            cv.put(DatabaseContract.RandMaterieTable.COLUMN_NAME_ORIGINE,
                    r.getOrigine());

            db.insert(DatabaseContract.RandMaterieTable.TABLE_NAME,
                    null, cv);
        }
    }
}
