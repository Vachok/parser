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
    private static String getDateFrom( String stringDate ) throws Exception {
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("no date");
    }

    static String date() throws Exception {
        Document page = getPage();
        Element tableWTH = page.select("table[class=wt]").first();
        Elements names = tableWTH.select("tr[class=wth]");
        String prdate = null;
        for (Element name : names) {
            String stdate = getDateFrom(name.select("th[id=dt]").text());
            prdate = getDateFrom(stdate);
        }
        return prdate;


    }
}

