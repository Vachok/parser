package ru.vachok;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
// Скачиваем документ из URL (page)
    private static Document getPage() throws MalformedURLException {
        String url = "http://pogoda.spb.ru/";
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url) , 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

//  21.11 Вторник погода сегодня -> 21.11
    // Создание шаблона pattern
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
    //Проверяем по-шаблону (getDateFrom)
    private static String getDateFrom( String stringDate ) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("no date");
    }

// Метод, печатающий 4 значения
    private static void printFourValues(Elements values, int index) {
        // Тут нужно проверить значение массива номер 3, на соответствие слову "Утро"
        // Массив values. Таблица, которая содержит погодую информацию
        Element valueLine;
        if (index == 0) {
            Element valueLin = values.get(3);
            // Если значение "Утро", то вывести 4 строки (Утро, День, Вечер, Ночь)
            // в другом случае только 3 (День, Вечер, Ночь
            boolean isMorning = valueLin.text().contains("Утро");
            int iterationCount = 4;
            if (isMorning) {
                iterationCount = 3;
            }
            for (int i = 0; i < iterationCount; i++) {
                valueLine = values.get(index + i);
                for (Element td : valueLine.select("td")) {
                    System.out.print(td.text() + " ");
                }
                System.out.println();
            }

        } else {
            int iterationCount = 4;
            for (int i = 0; i < iterationCount; i++) {
                valueLine = values.get(index);
                for (Element td : valueLine.select("td")) {
                    System.out.print(td.text() + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main( String[] args ) throws Exception {
        Document page;
        page = getPage();
        Element tableWeather;
        tableWeather = page.select("table[class=wt]").first();
        int index = 0;
        Elements names = tableWeather.select("tr[class=wth]");
        Elements values = tableWeather.select("tr[valign=top]");

        // Берем цифры из шаблона pattern xx.xx (date)
        for (Element name : names) {
            String dateString = name.select("th[id=dt]").text();
            String date = getDateFrom(dateString);

            // Выводим на экран
            System.out.println(date);
            System.out.println("   Явления     Температура     Влажность       Ветер");
            printFourValues(values, index);
        }
    }
}
