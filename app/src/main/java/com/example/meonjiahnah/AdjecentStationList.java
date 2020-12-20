package com.example.meonjiahnah;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class AdjecentStationList extends AsyncTask<Void, Void, String> {

    private String url;
    private String region;
    private String stationlist = "";

    public AdjecentStationList(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(Void... params) {
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
                    Log.d("AdjecentStationList", "station name  : " + getTagValue("stationName", eElement));
                    Log.d("AdjecentStationList", "addr  : " + getTagValue("addr", eElement));
                    stationlist+=getTagValue("addr", eElement)+",";
                    }

                }    // for end
            }    // if end
        return stationlist;

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
