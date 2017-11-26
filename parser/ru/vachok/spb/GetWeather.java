package ru.vachok.spb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс получения погоды
 */
public class GetWeather {
    private static Element tableWTH;
    private static Document page = getPage();
    /**
     Забираем страницу http://pogoda.spb.ru/
     Отдаём Document с переменной page * @return
     */
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
    /**
     Берёт Document page, выбирает первую таблицу table[class=wt]
     Отдаёт массив, выбранный из table class=wt, с тэгом tr[valign=top] * @return
     */
    private static Elements getVal() {
        tableWTH = GetWeather.page.select("table[class=wt]").first();
        return tableWTH.select("tr[valign=top]") ;
    }
    private static String getDateFrom( String stringDate ) throws Exception {
        Pattern pattern;
        pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        Matcher matcher;
        matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("no date");
    }
    private static String date() throws Exception {
        Document page = getPage();
        tableWTH = page != null ? page.select("table[class=wt]").first() : null;
        Elements names = tableWTH != null ? tableWTH.select("tr[class=wth]") : null;
        String prdate = null;
        assert names != null;
        for (Element name : names) {
            String stdate = getDateFrom(name.select("th[id=dt]").text());
            prdate = getDateFrom(stdate);
        }
        return prdate;


    }



    public static void main() {
        int valSize = getVal().size();
        Elements values = getVal();
        int index = 0;
        Element valueLn = getVal().get(3);
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
                System.out.println();
            }
        } else {
            for (int i = 0; i < iterationCount; i++) {
                Element valueTd = getVal().get(index + i);
                for (Element td : valueTd.select("td")) {
                    System.out.println(td.text());
                }
            }
        }
    }

}

