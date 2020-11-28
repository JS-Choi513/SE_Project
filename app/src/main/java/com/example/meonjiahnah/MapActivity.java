package com.example.meonjiahnah;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.LogManager;

import javax.xml.parsers.ParserConfigurationException;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        TMapData tmapdata = new TMapData();
        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        TMapView tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("l7xx205c92dac3c543e49079f76d026e4598");
        linearLayoutTmap.addView( tMapView );
        Intent intent = getIntent();
        String selectstate =intent.getExtras().getString("key");
        String region[] = selectstate.split(" ");//인텐트로 얻어온 지역정보 분할
        OpenAPI districtdustinfo = null;
        if(selectstate.contains("광역시")||selectstate.contains("특별시")){
            districtdustinfo = new OpenAPI("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                                               "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                                                +selectstate.substring(0,2)+"&searchCondition=DAILY");
           //districtdustinfo.execute();
        }
        else if(region[0].contains("남도")){
            districtdustinfo = new OpenAPI("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +region[0].substring(0,1)+"남"+"&searchCondition=DAILY");
            System.out.println("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +region[0].substring(0,1)+"남"+"&searchCondition=DAILY");
        }
        else if(region[0].contains("북도")){
            districtdustinfo = new OpenAPI("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +region[0].substring(0,1)+"북"+"&searchCondition=DAILY");
            //districtdustinfo.execute();
            System.out.println("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +region[0].substring(0,1)+"북"+"&searchCondition=DAILY");
        }
        else if(region[0].equals("경기도")){
            districtdustinfo = new OpenAPI("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName=경기&searchCondition=DAILY");
           // districtdustinfo.execute();
            System.out.println("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName=경기&searchCondition=DAILY");
        }
        else if(region[0].equals("강원도")){
            districtdustinfo = new OpenAPI("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +"강원"+"&searchCondition=DAILY");
          //  districtdustinfo.execute();
            System.out.println("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +"강원"+"&searchCondition=DAILY");
        }
        else if(region[0].equals("세종특별자치시")){
            districtdustinfo = new OpenAPI("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +"세종"+"&searchCondition=DAILY");
            System.out.println("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +"세종"+"&searchCondition=DAILY");
           // districtdustinfo.execute();
        }
        else if(region[0].equals("제주특별자치도")){
            districtdustinfo = new OpenAPI("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +"제주"+"&searchCondition=DAILY");
            System.out.println("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                    "serviceKey=C7HtSQyQa4rzA8TIPaeo7A3B1sK87SaglueMQdbor%2FFvejPK%2F7AdMco2ZYnwluemTn29Ur4oBSm9OGFp8cO4kw%3D%3D&numOfRows=10&pageNo=1&sidoName="
                    +"제주"+"&searchCondition=DAILY");
            //districtdustinfo.execute();
        }
        //StringTokenizer regionSplit = new StringTokenizer(selectstate," ");
        districtdustinfo.execute();
        String input = "";
        if(region.length == 3){// 일반구가 있는 시를 선택했을 경우
            input = selectstate+"청";

        }
        else {
            char last = region[1].charAt(region[1].length() - 1);//최소행정구역 끝자리 구분
            input = selectstate;
            if(last=='구'||last=='군'||last=='시') {
                input = selectstate + "청";
            }

        }
        System.out.println(input);//이전 인텐트 전송값 지역정보


        tmapdata.findTitlePOI(input, new TMapData.FindTitlePOIListenerCallback() {
            @Override
            public void onFindTitlePOI(ArrayList poiItem) {
                for(int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem  item = (TMapPOIItem) poiItem.get(i);
                    System.out.println(item.getPOIPoint().toString());
                    String location[] = item.getPOIPoint().toString().split(" ");
                    String longitude = location[1];
                    String latitude = location[3];
                    tMapView.setCenterPoint(Double.parseDouble(latitude), Double.parseDouble(longitude));
                }

                tMapView.setZoomLevel(14);
            }
        });
        /*
        //Button buttonMove1 = (Button)findViewById(R.id.buttonMove1);
        //Button buttonMove2 = (Button)findViewById(R.id.buttonMove2);
        //Button buttonZoomIn = (Button)findViewById(R.id.buttonZoomIn);
        //Button buttonZoomOut = (Button)findViewById(R.id.buttonZoomOut);
        //Button buttonZoomLevel10 = (Button)findViewById(R.id.buttonZoomLevel10);

// "N서울타워" 버튼 클릭
        buttonMove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3번째 파라미터 생략 == 지도 이동 Animation 사용안함
                tMapView.setCenterPoint(126.988205, 37.551135);
            }
        });

// "경복궁" 버튼 클릭
        buttonMove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3번째 파라미터 true == 지도 이동 Animation 사용
                tMapView.setCenterPoint(126.976998, 37.579600, true);
            }
        });

// "확대" 버튼 클릭
        buttonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.MapZoomIn();
            }
        });

// "축소" 버튼 클릭
        buttonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.MapZoomOut();
            }
        });

// "ZoomLevel=10" 버튼 클릭
        buttonZoomLevel10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.setZoomLevel(10);
            }
        });
        */

    }
    @Override
    public void onBackPressed() {
        MapActivity.this.finish();

    }



}
