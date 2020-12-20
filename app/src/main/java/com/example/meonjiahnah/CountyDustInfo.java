package com.example.meonjiahnah;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*미세먼지 정보 Get thread*/
public class CountyDustInfo extends AsyncTask<Void, Void, String> {

    private String url;
    private String region;
    //private String state;

    public CountyDustInfo(String url, String region) {
        this.url = url;
        this.region = region;
    }

    @Override
    protected String doInBackground(Void... params) {

        // parsing할 url 지정(API 키 포함해서)
        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactoty.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(url);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }

        // root tag
        doc.getDocumentElement().normalize();
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result

        // 파싱할 tag
        NodeList itemsList = doc.getElementsByTagName("items");
        NodeList itemList = doc.getElementsByTagName("item");
        for(int temp = 0; temp < itemsList.getLength(); temp++){

            for (int temp2 = 0; temp2 < itemList.getLength(); temp2++) {
                Node nNode = itemList.item((itemList.getLength()*temp)+temp2);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    Log.d("OPEN_API", "data Time  : " + getTagValue("dataTime", eElement));
                    Log.d("OPEN_API", "지역구  : " + getTagValue("cityName", eElement));
                    Log.d("OPEN_API", "미세먼지  : " + getTagValue("pm10Value", eElement));
                    Log.d("OPEN_API", "초미세먼지 : " + getTagValue("pm25Value", eElement));
                    if(getTagValue("cityName", eElement).equals(this.region)||getTagValue("cityName", eElement).equals("세종시")){
                        region= getTagValue("dataTime", eElement) +" "+getTagValue("cityName", eElement)+" "+getTagValue("pm10Value", eElement);
                        System.out.println("스레드 내에서 호출됨:" + region);
                        return region;
                    }

                }    // for end
            }    // if end
        }

        return null;

    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
    }


    private String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}


