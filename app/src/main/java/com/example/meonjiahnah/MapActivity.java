package com.example.meonjiahnah;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kr.hyosang.coordinate.*;

import androidx.appcompat.app.AppCompatActivity;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapActivity extends AppCompatActivity {
    private CountyDustInfo districtdustinfo = null;
    private AdjecentStationList stationlist = null;
    private TextView dustinfo;
    private Button markerbtn;
    private String received_dustinfo;
    private double longitude;//경도
    private double latitude;//위도
    private double markerSpotLatitude;
    private double markerSpotLongitude;
    private String received_stationinfo;
    String station_address[] = {};
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point);
        setContentView(R.layout.activity_map);
        dustinfo = (TextView) findViewById(R.id.textView7);
        markerbtn = (Button) findViewById(R.id.button3);
        TMapData tmapdata = new TMapData();
        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        TMapView tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("l7xx205c92dac3c543e49079f76d026e4598");
        linearLayoutTmap.addView( tMapView );
        Intent intent = getIntent();
        String selectstate =intent.getExtras().getString("key");//ㅇㅇ특별시(광역시) ㅇㅇ구 or ㅇㅇ도 ㅇㅇ시 ㅇㅇ구
        System.out.println("selectstate: "+selectstate);
        String region[] = selectstate.split(" ");//인텐트로 얻어온 지역정보 분할         특별시 oo구
        StringBuilder selected_region;//최종선택지역
        String call_region = "";
        String thread_call_value = "";
        ArrayList arTMapPOIItem_2;//주변측정소 주소

        if(region[0].contains("광역시")||region[0].contains("특별시")){// 가장큰 행정구역단위(region[0]-> 예: ㅇㅇ도 ㅇㅇ광역시, ㅇㅇ특별시)
            call_region = selectstate.substring(0,2);
        }
        else if(region[0].contains("남도")){
            call_region = region[0].substring(0,1)+"남";
        }
        else if(region[0].contains("북도")){
            call_region = region[0].substring(0,1)+"북";
        }
        else if(region[0].equals("경기도")){
            call_region = "경기";
        }
        else if(region[0].equals("강원도")){
            call_region = "강원";
        }
        else if(region[0].equals("세종특별자치시")){
            call_region = "세종";
        }
        else if(region[0].equals("제주특별자치도")){
            call_region = "제주";
        }
        districtdustinfo = new CountyDustInfo("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=30&pageNo=1&sidoName="
                +call_region+"&searchCondition=HOUR",region[1]);
        try {
            received_dustinfo = districtdustinfo.execute().get();//이전 인텐트 전송값 지역정보
            System.out.println("스레드 리턴값: "+received_dustinfo);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String input = "";
        if(region.length == 3){// 일반구가 있는 시를 선택했을 경우
            input = selectstate+"청";
            System.out.println("input"+input);//테스트 로그
        }
        else {
            char last = region[1].charAt(region[1].length() - 1);//최소행정구역 끝자리 구분
            input = selectstate;
            System.out.println("input"+input);//테스트 로그
            if(last=='구'||last=='군'||last=='시') {
                input = selectstate + "청";
                System.out.println("input"+input);//테스트 로그
            }
        }
            /*지역명 검색으로 좌표값을 반환하는 메소드*/
            tmapdata.findTitlePOI(input, new TMapData.FindTitlePOIListenerCallback() {
                @Override
                public void onFindTitlePOI(ArrayList poiItem) {
                    TMapPOIItem  item = (TMapPOIItem) poiItem.get(0);
                    String location[] = item.getPOIPoint().toString().split(" ");//WGS84
                    String longitude = location[1];//경도
                    String latitude = location[3];//위도
                    System.out.println("메소드 "+longitude);
                    System.out.println("메소드 "+latitude);
                    setCoordinate(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    tMapView.setCenterPoint(Double.parseDouble(latitude),Double.parseDouble(longitude));
                    tMapView.setZoomLevel(12);
                    CoordPoint pt = new CoordPoint(Double.parseDouble(latitude), Double.parseDouble(longitude)); //관공서 좌표
                    CoordPoint ktmPt = TransCoord.getTransCoord(pt, TransCoord.COORD_TYPE_WGS84, TransCoord.COORD_TYPE_TM);////WGS84 -> TM좌표계 변환
                    setCoordinate(ktmPt.y, ktmPt.x);//TM 좌표계로 변환하여 저장
                    System.out.println("X"+ktmPt.x);
                    System.out.println("y"+ktmPt.x);
                    stationlist = new AdjecentStationList("http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?" +
                            "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&tmX="+Double.toString(ktmPt.x)+"&"+"tmY="+Double.toString(ktmPt.y));
                    try {
                        received_stationinfo = stationlist.execute().get();//현재위치 주변 측정소 정보
                        station_address = received_stationinfo.split(","); //주변 측정소 주소분할

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("스레드2 리턴값: "+received_stationinfo);
                }
            });
        System.out.println("insdfghhsdfhsdfhgsdfhsdfhsdfhsdfhput: "+input);//이전 인텐트 전송값 지역정보
        
        //등급 indicator
        String split_dustinfo[] = received_dustinfo.split(" ");
        String dust_level = "";
        if(Integer.parseInt(split_dustinfo[3])<=30) {
            dust_level = "좋음";
            dustinfo.setBackgroundColor(Color.parseColor("#1E90FF"));
        }
        else if(Integer.parseInt(split_dustinfo[3])>30 && Integer.parseInt(split_dustinfo[3])<=80){
            dust_level = "보통";
            dustinfo.setBackgroundColor(Color.parseColor("#00FF00"));
        }
        else if(Integer.parseInt(split_dustinfo[3])>80 && Integer.parseInt(split_dustinfo[3])<=150){
            dust_level = "나쁨";
            dustinfo.setBackgroundColor(Color.parseColor("#FF4500"));
        }
        else if(Integer.parseInt(split_dustinfo[3])>150){
            dust_level = "매우나쁨";
            dustinfo.setBackgroundColor(Color.parseColor("#DC143C"));
        }
        dustinfo.setText("업데이트 시간: "+split_dustinfo[0]+" "+split_dustinfo[1]+"\n"+
                         "지역구: "+split_dustinfo[2]+"\n"+
                         "미세먼지 수치: "+split_dustinfo[3]+" ㎍/m³"+"\n"+
                         "미세먼지 등급: "+dust_level+"\n"+
                         "등급기준: 한국환경공단 통합대기환경지수"+"\n"+
                         "설정한 행정구역의 평균 측정 값으로 실제와 다를 수 있음");
        dustinfo.setPadding(5,0,1,5);

        //markerbtn.setEnabled(true);
        ArrayList alTMapPoint = new ArrayList();// 검색한 주소들에 대한 좌표를 저장할 리스트
        markerbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<station_address.length-1;i++){//마지막 인덱스에는 ","만 들어있으므로 읽지않음
                    tmapdata.findAddressPOI(station_address[i], new TMapData.FindAddressPOIListenerCallback() {
                        @Override
                        public void onFindAddressPOI(ArrayList poiItem) {
                            for(int j = 0; j < poiItem.size(); j++) {
                                TMapPOIItem  item = (TMapPOIItem) poiItem.get(j);
                                System.out.println("POI Name: " + item.getPOIName().toString() + ", " +
                                        "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                                        "Point: " + item.getPOIPoint().toString());
                                String location[] = item.getPOIPoint().toString().split(" ");//WGS84
                                String longitude = location[3];//경도
                                String latitude = location[1];//위도
                                //tMapView.setCenterPoint(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                alTMapPoint.add(new TMapPoint(Double.parseDouble(latitude),Double.parseDouble(longitude)));
                                System.out.println("맵포인트 사이즈 "+alTMapPoint.size());
                                for(int k=0; k<alTMapPoint.size(); k++){
                                    TMapMarkerItem markerItem1 = new TMapMarkerItem();
                                    // 마커 아이콘 지정
                                    markerItem1.setIcon(bitmap);
                                    // 마커의 좌표 지정
                                    markerItem1.setPosition(0.5f, 1.0f);
                                    String mapoint = alTMapPoint.get(k).toString();
                                    String[] coordinate = mapoint.split(" ");

                                    TMapPoint tMapPoint1 = new TMapPoint(Double.parseDouble(coordinate[1]), Double.parseDouble(coordinate[3]));
                                    markerItem1.setTMapPoint(tMapPoint1);
                                    System.out.println("안녕하세요 좌표입니다. "+alTMapPoint.get(k).toString());
                                    //지도에 마커 추가
                                    markerItem1.setVisible(markerItem1.VISIBLE);
                                    System.out.println("마커추가");
                                    tMapView.addMarkerItem("markerItem"+k, markerItem1);
                                }
                            }
                        }
                    });
                }
            }
        });

        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem>arrayList, ArrayList<TMapPOIItem> poilist, TMapPoint point, PointF pointf) {
                    for (TMapMarkerItem item : arrayList) {
                        TMapPoint markerSpot = item.getTMapPoint();
                        setMarkerPoint(markerSpot.getLongitude(),markerSpot.getLatitude());
                        if(getApplicationContext() == null) return false;
                        Toast.makeText(getApplicationContext(), "위도: " + markerSpot.getLongitude() + " 경도: " + markerSpot.getLatitude(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), StationActivity.class);
                        System.out.println(getMarkerPoint());
                        intent.putExtra("code",getMarkerPoint());
                        startActivity(intent);
                    }
                return false;
            }

            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem>arrayList, ArrayList<TMapPOIItem> poilist, TMapPoint point, PointF pointf) {
                return false;
            }
        });
}
/*

    /*뒤로가기 버튼을 클릭했을 때*/
    @Override
    public void onBackPressed() {
        try
        {
            if (districtdustinfo.getStatus() == AsyncTask.Status.RUNNING||stationlist.getStatus() == AsyncTask.Status.RUNNING)
            {
                districtdustinfo.cancel(true);
                stationlist.cancel(true);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.latitude = 0;
        this.longitude = 0;
        MapActivity.this.finish();

    }
    public void setDustinfo(String str) {
        this.received_dustinfo = str;
    }
    public void setCoordinate(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public void setMarkerPoint(double longitude, double latitude){
        this.markerSpotLongitude = longitude;
        this.markerSpotLatitude = latitude;
    }
    public String getMarkerPoint(){
        String markercode = Double.toString(this.markerSpotLatitude)+" "+Double.toString(this.markerSpotLongitude);
        return markercode;
    }


}