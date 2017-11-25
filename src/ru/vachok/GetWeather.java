package ru.vachok;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetWeather {
    static Element tableWTH;


    /**
     * <p>Забираем документ из сети</p>
     *
     * @return Целевую страницу as is (page)
     * @throws MalformedURLException http://pogoda.spb.ru/
     */
    static Document getPage() throws MalformedURLException {
        String url = "http://pogoda.spb.ru/";
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url) , 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    /**
     * Преобразование и выборка дат
     *
     * @param stringDate это
     * @return
     * @throws Exception
     */
    static String getDateFrom( String stringDate ) throws Exception {
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("no date");
    }

    static String date() throws Exception {
        Document page = getPage();
        tableWTH = page.select("table[class=wt]").first();
        Elements names = tableWTH.select("tr[class=wth]");
        String prdate = null;
        for (Element name : names) {
            String stdate = getDateFrom(name.select("th[id=dt]").text());
            prdate = getDateFrom(stdate);
        }
        return prdate;


    }


    static Elements getVal() {
        try {
            tableWTH = GetWeather.getPage().select("table[class=wt]").first();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Elements values = tableWTH.select("tr[valign=top]");

        return values;
    }

    public static void main( String[] args ) throws Exception {
        int valSize = getVal().size();
        int index = 0;
        Element valueLn = getVal().get(3);
        Elements valueLines = getVal();
        int iterationCount = 4;
        boolean isMorning = valueLn.text().contains("Утро");
        boolean isDay = valueLn.text().contains("День");
        boolean isEvening = valueLn.text().contains("Вечер");
        if (isMorning) iterationCount = 3;
        if (isDay) iterationCount = 2;
        if (isEvening) iterationCount = 1;
        if (valSize == 0) {
            for (int i = 0; i < iterationCount; i++) {
                Element valueTd = valueLines.get(index + i);
                for (Element td : valueTd.select("td")) {
                    System.out.println(td.text());
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < iterationCount; i++) {
                Element valueTd = valueLines.get(index + i);
                for (Element td : valueTd.select("td")) {
                    System.out.println(td.text());
                }
            }
        }                System.out.println();

    }
}

