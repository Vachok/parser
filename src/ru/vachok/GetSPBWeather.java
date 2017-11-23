package ru.vachok;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSPBWeather {
    private static Document getSPBWeather() throws IOException {
            String url = "http://pogoda.spb.ru/";
            return Jsoup.parse(new URL(url) , 10000);
        }

    Element table = getSPBWeather().select("table[class=wth]").first();
    Elements names = table.select("td[id=wt]");
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
    private static String getDateFrom( String stringDate ) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("no date");
    }
}

