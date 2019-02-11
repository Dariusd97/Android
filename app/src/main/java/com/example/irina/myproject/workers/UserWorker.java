package com.example.irina.myproject.workers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import com.example.irina.myproject.StartIndividual;
import com.example.irina.myproject.User;
import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserWorker extends AsyncTask<String,Integer,ArrayList<User>> {
    private Context _context;

    public UserWorker(Context context) {
        _context = context;
    }

    @Override
    protected ArrayList<User> doInBackground(String... strings) {
        if(strings == null && strings.length == 0){
            return new ArrayList<>();
        }
        ArrayList<User> userList = new ArrayList<>();
        String address = String.format("https://api.myjson.com/bins/1b2vpc");
        HttpURLConnection connection = null;
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
            JSONArray users = jsonObject.getJSONArray("users");
            for(int i=0;i<users.length();i++){
                User user = new User();
                JSONObject u = users.getJSONObject(i);

                user.setUsername(u.getString("username"));
                user.setEmail(u.getString("email"));
                user.setAnStudiu(u.getString("an"));
                user.setGrupa(u.getInt("grupa"));
                user.setSerie(u.getString("serie"));
                user.setParola(u.getString("parola"));
                user.setOrigin(u.getString("origine"));
                userList.add(user);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(User u:userList){
            System.out.println(u.toString());
        }
        return userList;
    }
    @Override
    protected void onPostExecute(ArrayList<User> users) {
        DatabaseHelper helper = new DatabaseHelper(_context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DatabaseContract.StudentTable.TABLE_NAME +
                " WHERE " + DatabaseContract.StudentTable.COLUMN_NAME_ORIGIN +
                " like 'json'");

        for(User user :users ) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.StudentTable.COLUMN_NAME_USERNAME,
                    user.getUsername());
            cv.put(DatabaseContract.StudentTable.COLUMN_NAME_EMAIL,
                    user.getEmail());
            cv.put(DatabaseContract.StudentTable.COLUMN_NAME_PAROLA,
                    user.getParola());
            cv.put(DatabaseContract.StudentTable.COLUMN_NAME_AN,
                    user.getAnStudiu());
            cv.put(DatabaseContract.StudentTable.COLUMN_NAME_GRUPA,
                    user.getGrupa());
            cv.put(DatabaseContract.StudentTable.COLUMN_NAME_SERIE,
                    user.getSerie());
            cv.put(DatabaseContract.StudentTable.COLUMN_NAME_ORIGIN,
                    user.getOrigin());

            db.insert(DatabaseContract.StudentTable.TABLE_NAME,
                    null, cv);
        }
    }
}
