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

public class CountySelect extends AppCompatActivity {
    private ListView listView;                      // 리스트뷰
    private ArrayList<String> list;                      // String 데이터를 담고있는 리스트
    private ListViewAdapter adapter;                // 리스트뷰의 아답터
    private ProgressBar progressBar;                // 데이터 로딩중을 표시할 프로그레스바
    private String[] seoul = {"종로구","중구","용산구","성동구","광진구","동대문구","중랑구","성북구","강북구","도봉구",
                              "노원구","은평구","서대문구","마포구","양천구","강서구","구로구","금천구","영등포구","동작구",
                              "관악구","서초구","강남구","송파구","강동구"};
    private String[] busan = {"중구","서구","동구","영도구","부산진구","동래구","남구","북구","강서구","해운대구","사하구",
                              "금정구","연제구","수영구","사상구"};
    private String[] deagu = {"중구","동구","서구","남구","북구","수성구","달서구"};
    private String[] incheon = {"중구","동구","미추홀구","연수구","계양구","남동구","부평구","서구","강화군","옹진군"};
    private String[] gwangju = {"동구","서구","남구","북구","광산구"};
    private String[] deajeon = {"동구","중구","서구","유성구","대덕구"};
    private String[] ulsan = {"중구","남구","동구","북구","울주군"};
    private String[] seajong = {"소정면","전의면","전동면","조치원읍","인서면","연동면","인기면","장군면","부강면","금남면"};
    private String[] gyeonggido = {"수원시","성남시","안양시","안산시","용인시","부천시","광명시","과천시","오산시","시흥시","군포시",
                                   "의왕시","하남시","이천시","안성시","김포시","화성시","광주시","여주시","양평군","고양시","덕양구","일산동구","일산서구",
                                   "의정부시","동두천시","구리시","남양주시","파주시","양주시","포천시","연천군","가평군"};
    private String[] gangwondo = {"춘천시","원주시","강릉시","동해시","태백시","속초시","삼척시","홍천군","횡성군","영월군","평창군",
                                  "정선군","철원군","화천군","양구군","인제군","고성군","양양군"};

    private String[] north_chungcheong = {"청주시","충주시","제천시","보은군","옥천군","영동군","증평군","진천군","괴산군","음성군","단양군"};
    private String[] south_chungcheong = {"천안시","공주시","보령시","아산시","서산시","논산시","계룡시","당진시","금산군","부여군","서천군","청양군",
                                          "홍성군","예산군","태안군"};
    private String[] north_jeolla = {"전주시","군산시","익산시","정읍시","남원시","김제시","완주군","진안군","무주군","장수군","임실군","순창군","고창군","부안군"};
    private String[] south_jeolla = {"목포시","여수시","순천시","나주시","광양시","담양군","곡성군","구례군","고흥군","보성군","화순군","장흥군","강진군","해남군",
                                     "영암군","무안군","함평군","영광군","장성군","완도군","진도군","신안군"};
    private String[] north_gyeongsang = {"포항시","경주시","김천시","안동시","구미시","영주시","영천시","상주시","문경시","경산시","군위군","의성군","청송군",
                                         "영양군","영덕군","청도군","고령군","성주군","칠곡군","예천군","봉화군","울진군","울릉군"};
    private String[] south_gyeongsang = {"창원시","진주시","통영시","사천시","김해시","밀양시","거제시","양산시","의령군","함안군","창녕군","고성군","남해군","하동군",
                                         "산청군","햠양군","거창군","합천군"};
    private String[] jeju = {"제주시","서귀포시"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);
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
                Intent intent;
                String regioninfo;
                if(adapter.getItem(position).equals("수원시") ||adapter.getItem(position).equals("성남시")||adapter.getItem(position).equals("청주시")||adapter.getItem(position).equals("천안시")||adapter.getItem(position).equals("전주시")||
                   adapter.getItem(position).equals("포항시") ||adapter.getItem(position).equals("창원시")||adapter.getItem(position).equals("안양시")||adapter.getItem(position).equals("안산시")||adapter.getItem(position).equals("용인시")||
                   adapter.getItem(position).equals("고양시")){//일반구가 있는 시를 선택할 경우
                    intent = new Intent(CountySelect.this, ProvinceSelect.class);//각 시의 구 단위 행정구역을 선택
                    regioninfo = selectstate + " " + adapter.getItem(position);
                    System.out.println(regioninfo);
                    finish();
                }
                else{
                    intent = new Intent(CountySelect.this, MapActivity.class);
                    regioninfo = selectstate + " " + adapter.getItem(position);
                }
                intent.putExtra("key",regioninfo);
                startActivity(intent);

            }
        });


    }
    private void getItem(String text){

        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.

        String[] region = {};

        switch(text){
            case "서울특별시":
                region = seoul;
                break;
            case "부산광역시":
                region = busan;
                break;
            case "대구광역시":
                region = deagu;
                break;
            case "인천광역시":
                region = incheon;
                break;
            case "광주광역시":
                region = gwangju;
                break;
            case "대전광역시":
                region = deajeon;
                break;
            case "울산광역시":
                region = ulsan;
                break;
            case "세종특별자치시":
                region = seajong;
                break;
            case "경기도":
                region = gyeonggido;
                break;
            case "강원도":
                region = gangwondo;
                break;
            case "충청북도":
                region =north_chungcheong;
                break;
            case "충청남도":
                region = south_chungcheong;
                break;
            case "전라북도":
                region = north_jeolla;
                break;
            case "전라남도":
                region = south_jeolla;
                break;
            case "경상북도":
                region = north_gyeongsang;
                break;
            case "경상남도":
                region = south_gyeongsang;
                break;
            case "제주특별자치도":
                region = jeju;
                break;

        }
        // 다음 20개의 데이터를 불러와서 리스트에 저장한다.
        for(int i = 0; i < region.length; i++){
            String label = "";
            label = region[i];
            list.add(label);
        }


    }
    @Override
    public void onBackPressed() {
        CountySelect.this.finish();

    }
}
