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
 * <b>Получение погоды в Питере</b>
 *
 * @author Vachok
 * Из урока на www.geekbrains.ru
 * @version 0.171130.5
 * @since 30 ноября 2017
 */
class GetWeather {
    private static Elements tableTrVtop;
    private static Document page = getPage();

    /**
     * <b>Для получения кода страницы</b>
     */
    private static Document getPage() {
        String typedUrl = "http://pogoda.spb.ru";
        Document page = null;
        try {
            page = Jsoup.parse(new URL(typedUrl) , 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
    /**<b>Сборщик</b>
     * @return <p style="font-size:1em; color:blue;">{@code tableWTH.select("tr[valign=top]")}</p>
     */
    private static Elements getVal()  {
         Element tablewtFirst = getPage().select("table[class=wt]").first();
         Elements tableTrVtop = tablewtFirst.select("tr[valign=top]");
        return tableTrVtop;
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
        throw new IOException("no date");
    }

    /**
     * <b>Дата из массива </b>
     *
     * @return <p style="font-size:1em; color:blue;">date: "01.12"(example)</p>
     * @throws IOException <i style="font-size:1em; color:blue;">no date</i>
     */
    private static String dateGet() throws IOException {
        Elements names = getVal() != null ? tableTrVtop.select("tr[class=wth]") : null;
        String date = null;
        assert names != null;
        for (Element name : names) {
            String stdate = getDateFrom(name.select("th[id=dt]").text());
            date = getDateFrom(stdate);
        }
        return date;
    }

    /**
     * <p style="font-size:2em; color:red;">Out</p>
     *
     * @throws IOException <i style="font-size:1em; color:blue;">no date</i>
     * @see GetWeather#dateGet() <p style="font-size:1em; color:blue;">Вывод даты</p>
     * @see GetWeather#getVal() <p style="font-size:1em; color:blue;">Элементы</p>
     * @see GetWeather#showSPBvalues(Elements , int) <p style="font-size:1em; color:blue;">Вывод значений</p>
     * @since Метод за версией 0.171129.3
     */
    static void main(){ //throws IOException {
        int index = 1 + 9;
        Elements values = getVal();
        showSPBvalues(values, index);
    }
//        throw new IOException("Sorry, ERRRRRRRRRRR");

    /**
     * <p style="font-size:2em; color:red;"><b>Метод вывода значений</b></p>
     * <p><b>index</b> инициализация переменной индекса массива;
     * <p><b>valueLn</b> - элемент, который мы передаём на проверку (Утро / День / Вечер);
     * <p><b>iterationCount</b> - это то, сколько раз пройтись по;
     * <p><b>index</b> = 0;
     * <p><b>i</b> переменная для подсчёта шагов;
     * <p><b>valueTd</b> - элемент массива <b>values</b> , с индексом <b>index</b> +  <b>i</b>;
     * <p><b>td</b> - это таблицы с тэгом <b>td</b> из <b>valueTd</b>;
     * <b>td.text</b> - текст из <b>td</b> , который содержит нужные нам значения!</p>
     * <p><i>values 19 iter 4</i> (число из вызывающего метода. Его можно засунуть в метод, как переменную)</p>
     *
     * @param values кидаем в метод массив отобранный из.
     * @param index  индекс элемента из массива values
     * @return <b style="font-size:2em; color:red;">что отдавать?</b>
     * @since Метод за версией 0.171130.2
     */
    private static void showSPBvalues( Elements values , int index ) {
        Element valueLn = values.get(3); // берем элемент 3 из полученного массива
        int chandedIndex = values.size();
        for (Element elementIndexed : values) {
            int iterationCount = 4;
            boolean isMorning = valueLn.text().contains("Утро");
            boolean isDay = valueLn.text().contains("День");
            boolean isEvening = valueLn.text().contains("Вечер");
            if (isMorning) iterationCount = 3;
            if (isDay) iterationCount = 2;
            if (isEvening) iterationCount = 1;
            if (iterationCount == 4) {
                for (int a = 0; a < iterationCount; a++) {
                    for (Element elementTd : elementIndexed.select("td")) {
                        System.out.println(elementTd.text());
                    }
                }
            } else {
                for (int a = 0; a < iterationCount; a++) {
                    for (Element elementTd : elementIndexed.select("td")) {
                        System.out.println(elementTd.text());
                    }
                }
            }
        }
    }
}