package ru.vachok;

import org.jsoup.nodes.Element;

public class Main {

    public static void main( String[] args ) throws Exception {
        Element values = PrintPartValues.printValues();
        String date = GetWeather.date();
//        System.out.println("Погода в Санкт-Петербурге. По данным сайта pogoda.spb.ru");
        System.out.println(values);
        System.out.println(date);
        System.out.println("Заебцом");
    }
}