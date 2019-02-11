package com.example.irina.myproject.workers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.example.irina.myproject.Profesor;
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

public class ProfesorWorker extends AsyncTask<String,Integer,ArrayList<Profesor>> {

    private Context _context;

    public ProfesorWorker(Context context) {
        _context = context;
    }
    @Override
    protected ArrayList<Profesor> doInBackground(String... strings) {
        if(strings == null && strings.length ==0){
            return new ArrayList<>();
        }

        ArrayList<Profesor> listaProfesori = new ArrayList<>();
        String address =String.format("https://api.myjson.com/bins/fj54o");
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
            JSONObject jsonObjectProfesor = new JSONObject(result);
            JSONArray profesoriArray = jsonObjectProfesor.getJSONArray("profesori");
            for(int i=0;i<profesoriArray.length();i++){
                Profesor prof = new Profesor();
                JSONObject p = profesoriArray.getJSONObject(i);
                prof.setEmail(p.getString("email"));
                prof.setUsername(p.getString("username"));
                prof.setParola(p.getString("parola"));
                prof.setOrigine(p.getString("origine"));
                listaProfesori.add(prof);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(Profesor in : listaProfesori){
            System.out.println("prof"+in.toString());
        }
        return listaProfesori;
    }
    @Override
    protected void onPostExecute(ArrayList<Profesor> profesorArrayList) {
        DatabaseHelper helper = new DatabaseHelper(_context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DatabaseContract.ProfesorTable.TABLE_NAME +
                " WHERE " + DatabaseContract.ProfesorTable.COLUMN_NAME_ORIGIN +
                " like 'json'");

        for(Profesor p:profesorArrayList ) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.ProfesorTable.COLUMN_NAME_EMAIL,
                    p.getEmail());
            cv.put(DatabaseContract.ProfesorTable.COLUMN_NAME_USERNAME,
                    p.getUsername());
            cv.put(DatabaseContract.ProfesorTable.COLUMN_NAME_PAROLA,
                    p.getParola());
            cv.put(DatabaseContract.ProfesorTable.COLUMN_NAME_ORIGIN,
                    p.getOrigine());

            db.insert(DatabaseContract.ProfesorTable.TABLE_NAME,
                    null, cv);
        }
    }
}
