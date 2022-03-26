package ru.cft.cfttesttask.network;

import ru.cft.cfttesttask.model.CurrencyModel.CurrencyItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class GetCurrencyData {

    private static URL url;

    static {
        try {
            url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, CurrencyItem> getData(){

        HttpURLConnection connection = null;
        String result = null;

        JSONObject jsonObject = null;
        Map<String, CurrencyItem>  dataMap = new LinkedHashMap<>();

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            jsonObject = new JSONObject(result);

            jsonObject = jsonObject.getJSONObject("Valute");

            Iterator<String> iterator = jsonObject.keys();

            while (iterator.hasNext()) {
                String key = iterator.next();
                CurrencyItem currencyItem = new CurrencyItem(jsonObject.getJSONObject(key));
                dataMap.put(key, currencyItem);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataMap;


    }


}
