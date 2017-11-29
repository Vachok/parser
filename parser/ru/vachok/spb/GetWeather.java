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
 * @version 0.171129.3
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
     * @see GetWeather#tableWTH
     */
    private static Elements getVal() {
        tableWTH = GetWeather.page.select("table[class=wt]").first();
        return tableWTH.select("tr[valign=top]");
    }
    /** <b>Выделяет дату из <i>stringDate</i></b> <p>Берется строка stringDate, засовывается внутрь, проверяется Matcher"ом и отдаёт строку, в случае совпадения.</p>
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
        throw new IOException("no date");
    }
    /**<b>Дата из массива </b>
     @return <p style="font-size:1em; color:blue;">date: "01.12"(example)</p>
     @throws IOException <i style="font-size:1em; color:blue;">no date</i>
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
    /** <p style="font-size:2em; color:red;">Out</p>
     * @see GetWeather#dateGet() <p style="font-size:1em; color:blue;">Вывод даты</p>
     * @see GetWeather#getVal() <p style="font-size:1em; color:blue;">Элементы</p>
     * @see GetWeather#printVal(Elements , int) <p style="font-size:1em; color:blue;">Вывод значений</p>
     * @see GetWeather#tableWTH
     * @throws IOException <i style="font-size:1em; color:blue;">no date</i>
     * @since 0.171129.3
     */
    public static void main() throws IOException {
        int counter =  2;
        printVal(getVal(), counter); // вызов метода печати, и передача ему массива элементов
        String date = dateGet();
        System.out.println(date);
    }
    /** <p style="font-size:2em; color:red;"><b>Метод вывода значений</b></p>
     * <p><b>index</b> инициализация переменной индекса массива;
     * <p><b>valueLn</b> - элемент, который мы передаём на проверку (Утро / День / Вечер);
     * <p><b>iterationCount</b> - это то, сколько раз пройтись по;
     * <p><b>index</b> = 0;
     * <p><b>i</b> переменная для подсчёта шагов;
     * <p><b>valueTd</b> - элемент массива <b>values</b> , с индексом <b>index</b> +  <b>i</b>;
     * <p><b>td</b> - это таблицы с тэгом <b>td</b> из <b>valueTd</b>;
     * <b>td.text</b> - текст из <b>td</b> , который содержит нужные нам значения!</p>
     * <p><i>values 19 iter 4</i> (число из вызывающего метода. Его можно засунуть в метод, как переменную)</p>
     * @param values кидаем в метод массив отобранный из {@link GetWeather#tableWTH}.
     * @param index индекс элемента из массива values <p>В данном случае его назначает {@link GetWeather#main}.</p>
     * @return <b style="font-size:2em; color:red;">что отдавать?</b>
     * @since 0.171129.3*/
    private static int printVal( Elements values , int index) {
        Element valueLn = values.get(3); // берем элемент 3 из полученного массива
        int iterationCount = 4;
        boolean isMorning = valueLn.text().contains("Утро");
        boolean isDay = valueLn.text().contains("День");
        boolean isEvening = valueLn.text().contains("Вечер");
        if (isMorning) iterationCount = 3;
        if (isDay) iterationCount = 2;
        if (isEvening) iterationCount = 1;
        if (index == 0) {
            for (int i = 0; i < iterationCount; i++) {
                Element elementTds = values.get(index + i);
                for (Element elementTd : elementTds.select("td")) {
                    System.out.print("td из элемента. Индекс " + elementTd.elementSiblingIndex() + "::::::::" + elementTd.text()); // в виде обычного текста
                    System.out.println();
                }System.out.println("Мы попали в if iterationCount = " + iterationCount);
            }
        } else
            for (int i = 0; i < iterationCount; i++) {
                Element elementTds = getVal().get(index + i);
                for (Element elementTd : elementTds.select("td")) { // вывести содержимое всех <elementTd> из элемента elementTds
                    System.out.print("td из элемента. Индекс " + elementTd.elementSiblingIndex() + "::::::::" + elementTd.text()); // в виде обычного текста
                    System.out.println();
                }
                System.out.println("Мы попали в else. iterationCount = " + iterationCount);
                System.out.println(" = > valueLn содержит Утро");
                System.out.println();
        }
        return iterationCount;
    }
}