package ru.vachok.spb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**<b>Получение погоды в Питере</b>
 * @author Vachok
 * Из урока на www.geekbrains.ru
 * @version 0.171129.2
 * @since 29 ноября 2017
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
    /** <p style="font-size:2em; color:red;"><b>Метод вывода значений</b></p>
     * <p><b>index</b> инициализация переменной индекса массива;
     * <b>valueLn</b> - элемент, который мы передаём на проверку (Утро / День / Вечер);
     * <b>iterationCount</b> - это то, сколько раз пройтись по;
     * <b>index</b> = 0;
     * <b>i</b> переменная для подсчёта шагов;
     * <b>valueTd</b> - элемент массива <b>values</b> , с индексом <b>index</b> +  <b>i</b>;
     * <b>td</b> - это таблицы с тэгом <b>td</b> из <b>valueTd</b>;
     * <b>td.text</b> - текст из <b>td</b> , который содержит нужные нам значения!</p>
     * <p>values 19 iter 4 (число из вызывающего метода. Его можно засунуть в метод, как переменную)</p>
     * @param values Колличество элементов в массиве {@link GetWeather#getVal()}
     * @param iter число из вызывающего метода. Его можно засунуть в метод, как переменную.
     *             В данном случае его назначает {@link GetWeather#main}.
     *             @since 0.171129.2
     * @return <b style="font-size:1em; color:red;">что отдавать?</b>*/
    private static int printVal( Elements values , int iter) {
        int index = 0;
        Element valueLn = values.get(3); // Табл. погоды, элемент индекс 3 = Утро
        int iterationCount = 4;
        boolean isMorning = valueLn.text().contains("Утро"); //=
        boolean isDay = valueLn.text().contains("День");
        boolean isEvening = valueLn.text().contains("Вечер");
        if (isMorning) iterationCount = 3;
        if (isDay) iterationCount = 2;
        if (isEvening) iterationCount = 1;
        if (index == 0) { //Если индекс 0, что верно ВСЕГДА(!), то
            for (int i = 0; i < iterationCount; i++) {
                Element valueTd = values.get(index + i);
                for (Element td : valueTd.select("td")) {
                     System.out.println(td.text());
                }
            }
        } else
            for (int i = 0; i < iterationCount; i++) {
                Element valueTd = getVal().get(index + i);
                for (Element td : valueTd.select("td")) {
                    System.out.println(td.text());
                } return iterationCount;
            }
        return 0;
    }
    /** <p style="font-size:2em; color:red;">Out</p>
     * @see GetWeather#dateGet() <p style="font-size:1em; color:blue;">Вывод даты</p>
     * @see GetWeather#getVal() <p style="font-size:1em; color:blue;">Элементы</p>
     * @see GetWeather#printVal(Elements , int) <p style="font-size:1em; color:blue;">Вывод значений</p>
     * @throws IOException <i style="font-size:1em; color:blue;">no date</i>
     * @since 0.171129.2
     */
    public static void main() throws IOException {
        printVal(getVal(),4);
        String date = dateGet();
        System.out.println(date);

    }
}