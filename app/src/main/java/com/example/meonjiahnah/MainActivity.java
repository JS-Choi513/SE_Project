package com.example.meonjiahnah;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity /*implements AbsListView.OnScrollListener*/ {
    private ListView listView;                      // 리스트뷰
    private ArrayList<String> list;                 // String 데이터를 담고있는 리스트
    private ListViewAdapter adapter;                // 리스트뷰의 아답터

    private String[] county = {"서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시",
                               "울산광역시","세종특별자치시"};
    private String[] state = {"경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주특별자치도"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        list = new ArrayList<String>();//행정구역 이름을 저장할 객체 생성
        adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);
        getItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        adapter.getItem(position),
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, CountySelect.class);
                intent.putExtra("key",adapter.getItem(position));
                startActivity(intent);
                //finish();
            }
        });

    }
    private void getItem(){
        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.
        // 다음 20개의 데이터를 불러와서 리스트에 저장한다.
        for(int i = 0; i < 17; i++){
            String label = "";
            if(i<county.length){
                label = county[i];
            }
            else if(i>=county.length){
                label = state[i-county.length];
            }
            list.add(label);
        }

    }
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로 가기 버튼을 누를 때 표시
    private Toast toast;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
            toast.cancel();
            toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
