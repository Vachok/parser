/*
 * Vacok 2017.
 */

package ru.vachok.spb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import statements

/**<b>Получение погоды в Питере</b>
 * @author Vachok
 * Из урока на www.geekbrains.ru
 * @version 0.172711.5
 * @since 27 ноября 2017
 */
public class GetWeather {

    /**<b>Полученная страница http://pogoda.spb.ru</b>*/
    private static Document page = getPage();
    /**<b>Выбранная таблица из {@link GetWeather#page}, с тэгами table[class=wt]</b>*/
    private static Element tableWTH;
    /**<b>Забрать страницу http://pogoda.spb.ru</b>
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
    /** <b>Элементы значений погоды и преобразовывает в Elements</b>
     * @return <p style="font-size:1em; color:blue;">{@code tableWTH.select("tr[valign=top]")}</p>
     */
    private static Elements getVal() {
        tableWTH = GetWeather.page.select("table[class=wt]").first();
        return tableWTH.select("tr[valign=top]");
    }
    /** <b>Выделяет дату из <i>stringDate</i></b>
     * @param stringDate <p style="font-size:1em; color:blue;">(example) "27.11 Понедельник погода сегодня"</p>
     * @return <p style="font-size:1em; color:blue;">java.util.regex.Matcher[pattern=\d{2}\.\d{2} region=0,32 lastmatch=27.11]</p>
     * @throws IOException <p style="font-size:1em; color:blue;">"no date"</p>
     */
    private static String getDateFrom( String stringDate ) throws IOException {
        Pattern pattern;
        pattern = Pattern.compile("\\d{2}\\.\\d{2}");
        Matcher matcher;
        matcher = pattern.matcher(stringDate); //Example ( java.util.regex.Matcher[pattern=\d{2}\.\d{2} region=0,32 lastmatch=27.11] )
        if (matcher.find()) {
            return matcher.group();
        }
        throw new IOException("no date");
    }

    /**<b>Дата из массива </b>
     @return <p style="font-size:1em; color:blue;">date: "01.12"(example)</p>
     @throws IOException <p style="font-size:1em; color:blue;">no date</p>
      */
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
// import statements
    /** <b>Печать значений погоды</b>
     * <p style="font-size:1em; color:red;">в разработке</p>
     */
    private static void printVal() {
        int index = 0; //инициализация переменной индекса массива
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

    /** <p style="font-size:2em; color:red;">Out</p>
     *  <p style="font-size:1em; color:blue;">Вывод даты
     * @see GetWeather#dateGet() <p style="font-size:1em; color:blue;">Вывод значений</p>
     * @see GetWeather#printVal()
     * @throws IOException <p style="font-size:1em; color:blue;">no date</p>
     */
    public static void main() throws IOException {
        String date = dateGet();
        System.out.println(date);
        printVal();
    }
    /**
     * @see ru.vachok.Main
     */
}