package com.example.irina.myproject.workers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.irina.myproject.TestStudent;
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

public class TestStudentWorker extends AsyncTask<String,Integer,ArrayList<TestStudent>> {

    private Context _context;
    public TestStudentWorker(Context context){
        _context=context;
    }
    @Override
    protected ArrayList<TestStudent> doInBackground(String... strings) {
        if(strings == null && strings.length ==0){
            return new ArrayList<>();
        }
        ArrayList<TestStudent> listaTestStudent = new ArrayList<>();
        String address = String.format("https://api.myjson.com/bins/x9uyg");
        HttpURLConnection connection=null;

        try {
            URL url = new URL(address);
            connection= (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader= new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray array = jsonObject.getJSONArray("test_student");
            for(int i=0;i<array.length();i++){
                TestStudent ts = new TestStudent();
                JSONObject ob = array.getJSONObject(i);
                ts.setEmail_student(ob.getString("email_student"));
                ts.setId_test(ob.getInt("id_test"));
                ts.setOrigine(ob.getString("origin"));
                ts.setPunctaj(ob.getInt("punctaj"));
                listaTestStudent.add(ts);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listaTestStudent;
    }
    @Override
    protected void onPostExecute(ArrayList<TestStudent> testStudentArrayList) {
        DatabaseHelper helper = new DatabaseHelper(_context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DatabaseContract.TestStudentTable.TABLE_NAME +
                " WHERE " + DatabaseContract.TestStudentTable.COLUMN_NAME_ORIGIN +
                " like 'json'");

        for(TestStudent testStudent :testStudentArrayList ) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.TestStudentTable.COLUMN_NAME_EMAIL_STUDENT,
                    testStudent.getEmail_student());
            cv.put(DatabaseContract.TestStudentTable.COLUMN_NAME_ID_TEST,
                    testStudent.getId_test());
            cv.put(DatabaseContract.TestStudentTable.COLUMN_NAME_ORIGIN,
                    testStudent.getOrigine());
            cv.put(DatabaseContract.TestStudentTable.COLUMN_NAME_PUNCTAJ,
                    testStudent.getPunctaj());

            db.insert(DatabaseContract.TestStudentTable.TABLE_NAME,
                    null, cv);
        }
    }
}
