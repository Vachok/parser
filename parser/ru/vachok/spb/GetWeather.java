package ru.vachok.spb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Получение погоды в Питере*/
public class GetWeather {

    /** Полученная страница http://pogoda.spb.ru*/
    private static Document page = getPage();
    /**Выбранная таблица из {@link GetWeather#page}, с тэгами table[class=wt]*/
    private static Element tableWTH;
    /** Забрать страницу http://pogoda.spb.ru
     * @return {@link GetWeather#page}*/
    private static Document getPage() {
        String url = "http://pogoda.spb.ru/";
        page = null;
        try {
            page = Jsoup.parse(new URL(url) , 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    private static Elements getVal() {
        tableWTH = GetWeather.page.select("table[class=wt]").first();
        return tableWTH.select("tr[valign=top]");
    }

    private static String getDateFrom( String stringDate ) throws IOException {
        Pattern pattern;
        pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        Matcher matcher;
        matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new IOException("no date");
    }

    private static String dateGet() throws IOException {
        Document page = getPage();
        tableWTH = page != null ? page.select("table[class=wt]").first() : null;
        Elements names = tableWTH != null ? tableWTH.select("tr[class=wth]") : null;
        String date = null;
        assert names != null;
        for (Element name : names) {
            String stdate = getDateFrom(name.select("th[id=dt]").text());
            date = getDateFrom(stdate);
        }
        return date;
    }

    private static void printVal() {
        int index = 0;
        Elements values = getVal();
        int valSize = values.size();
        Element valueLn = values.get(3);
        int iterationCount = 4;
        boolean isMorning = valueLn.text().contains("Утро");
        boolean isDay = valueLn.text().contains("День");
        boolean isEvening = valueLn.text().contains("Вечер");
        if (isMorning) iterationCount = 3;
        if (isDay) iterationCount = 2;
        if (isEvening) iterationCount = 1;
        if (valSize == 0) {
            for (int i = 0; i < iterationCount; i++) {
                Element valueTd = getVal().get(index + i);
                for (Element td : valueTd.select("td")) {
                    System.out.println(td.text());
                }
            }
        } else
            for (int i = 0; i < iterationCount; i++) {
                Element valueTd = getVal().get(index + i);
                for (Element td : valueTd.select("td")) {
                    System.out.println(td.text());
                }
            }
    }

    public static void main() throws IOException {
        String date = dateGet();
        System.out.println(date);
        printVal();
    }
}