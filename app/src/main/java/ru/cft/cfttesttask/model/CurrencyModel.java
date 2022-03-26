package ru.cft.cfttesttask.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CurrencyModel {

    private Map<String, CurrencyItem> dataMap = new LinkedHashMap<>();
    private List<CurrencyItem> dataList = new ArrayList<>(100);
    private List<String> stringList = new ArrayList<>(100);

    public void setData (Map<String, CurrencyItem> dataMap) {
        this.dataMap.clear();
        this.dataMap.putAll(dataMap);

        dataList.clear();
        dataList.addAll(this.dataMap.values());

        stringList.clear();
        stringList.add("RUB");
        for (CurrencyItem item : getDataList()) {
            stringList.add(item.charCode);
        }

    }

    public List<CurrencyItem> getDataList() {
        return dataList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void changeOutputCurrency(String code) {
        if(dataMap.containsKey(code)) {
            CurrencyItem.coeff = dataMap.get(code).rawValue;
        } else if(code.equals("RUB")) {
            CurrencyItem.coeff = 1.0;
        }
    }

    public static class CurrencyItem {

        private static double coeff = 1.0;
        private String charCode;
        private String name;
        private double rawValue;

        public CurrencyItem(JSONObject jobj){
            try {
                charCode = jobj.getString("CharCode");
                name = jobj.getString("Name");
                rawValue = jobj.getDouble("Value") / jobj.getInt("Nominal");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private String getActualValue(){

            double actualValue = rawValue/coeff;

            return String.format("%.4f", actualValue);
        }

        public String recyclerText() {
            return name + '\n' +
                    "Курс: " + getActualValue();
        }

        @NonNull
        @Override
        public String toString() {
            return charCode;
        }
    }

}
