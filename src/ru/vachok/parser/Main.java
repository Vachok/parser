package ru.vachok.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Main {


    public static void main( String[] args ) throws MalformedURLException {
        Document page = getPage();
        Element tableWeather = page.select("table[class=wt]").first();
        Elements names = tableWeather.select("tr[class=wth]");
        Elements value = tableWeather.select("tr[valign=top]");
        String date = "";
        System.out.println(date + "   Явления     Температура     Влажность       Ветер");
    }
    private static Document getPage() throws MalformedURLException {
        String url = "http://pogoda.spb.ru/";
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url) , 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
}
