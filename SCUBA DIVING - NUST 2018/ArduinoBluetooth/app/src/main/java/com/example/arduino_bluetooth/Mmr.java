package com.example.arduino_bluetooth;

public class Mmr {
    public static String str[] = new String[500];
    public static String str5[] = new String[500];
    public static String str6[] = new String[500];

    public static int i = 0;

    public void setValue(String value) {
        this.str[i++] = value;
    }

    public String getValue() {
        return str[i++];
    }

    public  String getStr5() {
        return str5[i++];
    }

    public  void setStr5(String str5) {
        Mmr.str5[i++] = str5;
    }

    public  String getStr6() {
        return str6[i++];
    }

    public  void setStr6(String str6) {
        Mmr.str6[i++] = str6;
    }

    public Mmr() {

    }
}



