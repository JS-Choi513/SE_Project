package com.example.meonjiahnah;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class ContentActivity2 extends AppCompatActivity {
    private ListView listView;                      // 리스트뷰
    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private ArrayList<String> list;                      // String 데이터를 담고있는 리스트
    private ListViewAdapter adapter;                // 리스트뷰의 아답터
    private int page = 0;                           // 페이징변수. 초기 값은 0 이다.
    private final int OFFSET = 20;                  // 한 페이지마다 로드할 데이터 갯수.
    private ProgressBar progressBar;                // 데이터 로딩중을 표시할 프로그레스바
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    private String[] suwon = {"장안구","권선구","팔달구","영통구"};
    private String[] sungnam = {"수정구","중원구","분당구"};
    private String[] anyang = {"만안구","동안구"};
    private String[] ansan = {"상록구","단원구"};
    private String[] yongin = {"처인구","기흥구","수지구"};
    private String[] goyang = {"덕양구","일산동구","일산서구"};
    private String[] cheonan = {"동남구","서북구"};
    private String[] junju = {"완산구","덕진구"};
    private String[] pohang = {"남구","북구"};
    private String[] changwon = {"의창구","성산구","마산합포구","마산회원구","진해구"};
    private String[] chungju = {"상강구","흥덕구","서원구","청원구"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        Intent intent = getIntent();
        String selectstate =intent.getExtras().getString("key");
        listView = (ListView) findViewById(R.id.listview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        list = new ArrayList<String>();

        adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);


        //listView.setOnScrollListener(this);
        getItem(selectstate);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        adapter.getItem(position),
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ContentActivity2.this, MapActivity.class);
                String regioninfo = selectstate + " " + adapter.getItem(position);
                intent.putExtra("key",regioninfo);
                startActivity(intent);
                finish();
            }
        });


    }
    private void getItem(String text){

        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.

        mLockListView = true;
        String[] region = {};

        switch(text){
            case "수원시":
                region = suwon;
                break;
            case "성남시":
                region = sungnam;
                break;
            case "안양시":
                region = anyang;
                break;
            case "안산시":
                region = ansan;
                break;
            case "용인시":
                region = yongin;
                break;
            case "고양시":
                region = goyang;
                break;
            case "청주시":
                region = chungju;
                break;
            case "천안시":
                region = cheonan;
                break;
            case "전주시":
                region = junju;
                break;
            case "포항시":
                region = pohang;
                break;
            case "창원시":
                region = changwon;
                break;
        }
        // 다음 20개의 데이터를 불러와서 리스트에 저장한다.
        for(int i = 0; i < region.length; i++){
            String label = "";
            label = region[i];
            list.add(label);
        }




        // 1초 뒤 프로그레스바를 감추고 데이터를 갱신하고, 중복 로딩 체크하는 Lock을 했던 mLockListView변수를 풀어준다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                mLockListView = false;
            }
        },1000);
    }
    @Override
    public void onBackPressed() {
        ContentActivity2.this.finish();

    }
}
