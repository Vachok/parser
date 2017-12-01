import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Получение погоды в Питере</b>
 *
 * @author Vachok
 * Из урока на www.geekbrains.ru
 * @version 0.171130.6
 * @since 30 ноября 2017
 */
 class GetSPBWeather {
     private static Elements tableCSS = getTablefomCSS();
    /**
     * <b>Для получения кода страницы</b>
     */
    private static Document getPage() {
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
     * <b>Сборщик</b>
     *
     * @return <p style="font-size:1em; color:blue;">{@code tableWTH.select("tr[valign=top]")}</p>
     */
    static Elements getTablefomCSS() {
        Element tablewtFirst = getPage().select("table[class=wt]").first();
        return tablewtFirst.select("tr[valign=top]");
    }
    /**
     * <b>Выделяет дату из <i>stringDate</i></b> <p>Берется строка stringDate, засовывается внутрь, проверяется Matcher"ом и отдаёт строку, в случае совпадения.</p>
     *
     * @param stringDate <i style="font-size:1em; color:blue;">(example) "27.11 Понедельник погода сегодня"</i>
     * @return <i style="font-size:1em; color:blue;">java.util.regex.Matcher[pattern=\d{2}\.\d{2} region=0,32 lastmatch=<b>27.11</b>]</i>
     * @throws IOException <i style="font-size:1em; color:blue;">no date</i>
     * @since 0.171129.3
     */
    private static String getDateFrom( String stringDate ) throws IOException {
        Pattern pattern;
        pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        Matcher matcher;
        matcher = pattern.matcher(stringDate); //Example ( java.util.regex.Matcher[pattern=\d{2}\.\d{2} region=0,32 lastmatch=27.11] )
        if (matcher.find()) {
            return matcher.group();
        }
        final IOException no_date = new IOException("no date");
        return stringDate;
    }
    /**
     * <b>Дата из массива </b>
     *
     * @return <p style="font-size:1em; color:blue;">date: "01.12"(example)</p>
     * @throws IOException <i style="font-size:1em; color:blue;">no date</i>
     */
    static String dateGet() throws IOException {
        Elements names = tableCSS;
        String date = null;
        for (Element name : names) {
            String stdate = getDateFrom(name.select("th[id=dt]").text());
            date = getDateFrom(stdate);
        }
        return date;
    }
    /**
     * @param values кидаем в метод массив отобранный из.
     * @param index ??
     * @return <b style="font-size:2em; color:red;">что отдавать?</b>
     * @since Метод за версией 0.171201.3
     */

    static int showSPBvalues( Elements values , int index  ) throws IOException {
        Element valueLn = values.get(3);
        for (int i = 0; i < values.size(); i++) {
            index = i;
            System.out.println(index + ">  " + "  ");
        }
        int iterationCount = 4;
        boolean isMorning = valueLn.text().contains("Утро");
        boolean isDay = valueLn.text().contains("День");
        boolean isEvening = valueLn.text().contains("Вечер");
        if (isMorning) iterationCount = 3;
        if (isDay) iterationCount = 2;
        if (isEvening) iterationCount = 1;
        if (iterationCount == 4) {
            for (Element elementIndexed : values)
                for (int a = 0; a < iterationCount; a++) {
                    for (Element elementTd : elementIndexed.select("td")) {
                        System.out.print(elementTd.text());
                    }
                }
        }
        else {
            for (Element elementIndexed : values)
                for (int a = 0; a < iterationCount; a++) {
                    for (Element elementTd : elementIndexed.select("td")) {
                        System.out.print(elementTd.text() + " ");
                    }
                }return iterationCount;
        }

        return index - iterationCount;
    }
}