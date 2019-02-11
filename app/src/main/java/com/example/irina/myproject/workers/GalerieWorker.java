package com.example.irina.myproject.workers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;


import com.example.irina.myproject.GaleriePoze;
import com.example.irina.myproject.models.Galerie;

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
public class GalerieWorker extends AsyncTask<String, Integer, ArrayList<Galerie>> {

    @Override
    protected ArrayList<Galerie> doInBackground(String... strings) {
        if(strings == null || strings.length == 0){
            return new ArrayList<>();
        }
        ArrayList<Galerie> galerieList = new ArrayList<>();
        String categorie = strings[0];
        String address = String.format("https://api.pexels.com/v1/search?query=%s&per_page=15",categorie);
        HttpURLConnection connection = null;
        try{
            URL url = new URL(address);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Authorization","563492ad6f9170000100000167ea014b94854abebf084da2867976d4");
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = reader.readLine())!= null){
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            JSONObject jsonObject= new JSONObject(result);
            JSONArray photos = jsonObject.getJSONArray("photos");
            for(int i = 0; i<photos.length();i++){
                Galerie galerie = new Galerie();
                JSONObject p = photos.getJSONObject(i);
                galerie.numeArtist =p.getString("photographer");
                galerie.siteArtist = p.getString("photographer_url");
                JSONObject src = p.getJSONObject("src");
                // download imagine
                String image = src.getString("small");
                String address2 = String.format(image);
                HttpURLConnection con2 = null;
                try{
                    URL url2 = new URL(address2);
                    con2 = (HttpURLConnection)url2.openConnection();
                    InputStream input = con2.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    galerie.imagine = bitmap;
                    galerieList.add(galerie);
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    if(con2 != null){
                        con2.disconnect();
                    }
                }
            }
        }catch( MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return galerieList;
    }

    @Override
    protected void onPostExecute(ArrayList<Galerie> galeries) {
        GaleriePoze.galerieList.clear();
        GaleriePoze.galerieList.addAll(galeries);
        GaleriePoze.galerieAdapter.notifyDataSetChanged();
    }
}
