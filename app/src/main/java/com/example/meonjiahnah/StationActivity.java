package com.example.meonjiahnah;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class StationActivity extends AppCompatActivity {
    //private double getLatitude;
    //private double getLogitude;
    private StationLog stationforecatst;
    private String getJSON;
    private TextView forecastlog;
    private String printlog ="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationinfo);
        forecastlog = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        String getcode = intent.getExtras().getString("code");//ㅇㅇ도 ㅇㅇ시
        String inputurl[] = getcode.split(" ");
        System.out.println(getcode);
        stationforecatst = new StationLog("https://api.waqi.info/feed/geo:"+inputurl[0]+";"+inputurl[1]+"/?token=2cb3f47c5dc26210f66b28597dd5e845338c6396");
        try {
            getJSON = stationforecatst.execute().get();//현재위치 주변 측정소 정보
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" 미세먼지 예보 API스레드 리턴값: "+getJSON);

        Vector receivedvalue = stationLogParser(getJSON);
        for(int i=0;i<receivedvalue.size();i++){
            printlog+=receivedvalue.get(i);
        }
        forecastlog.setText(printlog);
        System.out.println(printlog);
        forecastlog.setPadding(5,0,1,5);

    }
    //받아온 JSON 파싱
    public Vector stationLogParser(String jsonString) {

        String avg = null;
        String address = null;
        String day = null;
        Vector<String> forecastdata = new Vector();
        try {
            //가장 바깥태그
            JSONObject data = new JSONObject(jsonString).optJSONObject("data");
            //관측소 주소
            JSONObject city = new JSONObject(data.toString()).optJSONObject("city");
            String[] loc = city.optString("name").split(",");
            String inputloc="위치: ";
            for(int i=0;i<loc.length;i++){
                if(i==loc.length-2){
                    inputloc+=loc[i]+"\n";
                }
                else inputloc+=loc[i];
            }
            forecastdata.add(inputloc+"\n");
            forecastdata.add("\n");
            //예보데이터
            JSONObject forecast = new JSONObject(data.toString()).optJSONObject("forecast");
            JSONObject daily = new JSONObject(forecast.toString()).optJSONObject("daily");
            JSONArray jarray = new JSONObject(daily.toString()).getJSONArray("pm10");
            for (int i = 0; i < jarray.length(); i++) {
                System.out.println(jarray.get(i));
                JSONObject jObject = jarray.getJSONObject(i);
                day = jObject.optString("day");
                avg = jObject.optString("avg");
                forecastdata.add("날짜: " +day +"미세먼지 예측 평균값 : " +avg+"㎍/m³"+"\n");
                forecastdata.add("\n");
                //arraysum[0] = "날짜:" +day;
                // arraysum[1] = "미세먼지 예측값" +avg;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return forecastdata;
    }

    /*뒤로가기 버튼을 클릭했을 때*/
    @Override
    public void onBackPressed() {
        StationActivity.this.finish();

    }
}
