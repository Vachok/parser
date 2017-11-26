package ru.vachok.spb;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    static String date;

    static {
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
                for (@NotNull Element td : valueTd.select("td")) {
                    System.out.println(td.text());
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < iterationCount; i++) {
                Element valueTd = valueLines.get(index + i);
                for (@NotNull Element td : valueTd.select("td")) {
                    System.out.println(td.text());
                }
            }
        }
        System.out.println(date);

        try {
            date = date();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    private static Document getPage() {
        @NotNull String url = "http://pogoda.spb.ru/";
        @Nullable Document page = null;
        try {
            page = Jsoup.parse(new URL(url) , 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    private static String getDateFrom( @NotNull String stringDate ) throws Exception {
        @NotNull Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        @NotNull Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("no date");
    }

    @Nullable
    static String date() throws Exception {
        @Nullable Document page = getPage();
        tableWTH = page.select("table[class=wt]").first();
        Elements names = tableWTH.select("tr[class=wth]");
        @Nullable String prdate = null;
        for (@NotNull Element name : names) {
            String stdate = getDateFrom(name.select("th[id=dt]").text());
            prdate = getDateFrom(stdate);
        }
        return prdate;


    }

    private static Elements getVal() {
        tableWTH = GetWeather.getPage().select("table[class=wt]").first();

        return tableWTH.select("tr[valign=top]");
    }
}

