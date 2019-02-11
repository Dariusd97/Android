package com.example.irina.myproject;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.irina.myproject.contracts.DatabaseContract;
import com.example.irina.myproject.helpers.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        barChart = (BarChart)findViewById(R.id.chartView);

        SharedPreferences sp2 = getApplication().getSharedPreferences("emaillogare", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp2.edit();
        String email_logare = sp2.getString("pref_email","");
        DatabaseHelper helper1 = new DatabaseHelper(ChartActivity.this);
        SQLiteDatabase db1 = helper1.getReadableDatabase();
        Cursor cursor2 = db1.rawQuery("SELECT " + DatabaseContract.TestStudentTable.COLUMN_NAME_PUNCTAJ + ", " + DatabaseContract.TestTable.COLUMN_NAME_TITLU + " FROM "
                + DatabaseContract.TestStudentTable.TABLE_NAME + ", " + DatabaseContract.TestTable.TABLE_NAME + " WHERE " + DatabaseContract.TestStudentTable.COLUMN_NAME_ID_TEST +
                " = " + DatabaseContract.TestTable.COLUMN_NAME_ID + " AND " + DatabaseContract.TestStudentTable.COLUMN_NAME_EMAIL_STUDENT + " like '" + email_logare + "'",null);

        final ArrayList<ClasaTest> listaTeteEfectuate = new ArrayList<>();

        ClasaTest test;
        while(cursor2.moveToNext()){
            int index = cursor2.getColumnIndex(DatabaseContract.TestStudentTable.COLUMN_NAME_PUNCTAJ);
            int index2 = cursor2.getColumnIndex(DatabaseContract.TestTable.COLUMN_NAME_TITLU);
            test = new ClasaTest(cursor2.getString(index2),cursor2.getInt(index));
            listaTeteEfectuate.add(test);
        }









        ArrayList<BarEntry> barEntries=new ArrayList<>();
        for(int i=0;i<listaTeteEfectuate.size();i++){
            barEntries.add(new BarEntry(i,listaTeteEfectuate.get(i).getPunctaj()));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,"Punctaje");

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.notifyDataSetChanged();




        //ChartView chartView = findViewById(R.id.chartView);
        // chartView.setData(listaTeteEfectuate);
        // chartView.invalidate();
    }

}
