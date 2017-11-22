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

    // Скачиваем документ из URL
    // Присваиваем переменную page
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

    // Преобразование строки:
    // 21.11 Вторник погода сегодня -> 21.11
    //  Создание шаблона pattern
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    //Проверяем по-шаблону (getDateFrom)
    //Если удалось выделить из строки значения, соотв. шаблону - вернуть значения
    //Если нет - вернуть no date
    private static String getDateFrom( String stringDate ) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("no date");
    }

    // Метод, печатающий 4 значения самой погоды.
    // Индекс - это значение, с которого нужно печатать
    private static int printPartValues( Elements values , int index ) {
        int iterationCount = 4;
        if (index == 0) {
            // Проверка третьего элемента на соответствие, для корректного вывода строк
            Element valueLn = values.get(3);
            boolean isMorning = valueLn.text().contains("Утро");
            // Задаём кол-во строк по-умолчанию
            // Если элемент массива valueLn номер 3
            // содержит Утро - выводим только 3 первые строчки сегодняшнего дня
            if (isMorning) {
                iterationCount = 3;
            }
                for (int i = 0; i < iterationCount; i++) {
                    Element valueLine = values.get(index + i);
                    for (Element td : valueLine.select("td")) {
                        System.out.print(td.text() + "    ");
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i < iterationCount; i++) {
                    // Забрать элемент массива values, по-индексу
                    // Присвоить этому имя valueLine
                    Element valueLine = values.get(index + i);
                    // Выделить из valueLine всё, что td
                    for (Element td : valueLine.select("td")) {
                        System.out.print(td.text() + "    ");
                    }
                    System.out.println();
                }
            }  return iterationCount;
        }
    public static void main( String[] args ) throws Exception {
        Document page = getPage();
        // tableWeather - таблица самой погоды
        // names - названия дней недели и дата
        // values - непосредственно таблица значений погоды
        Element tableWeather = page.select("table[class=wt]").first();
        Elements names = tableWeather.select("tr[class=wth]");
        Elements values = tableWeather.select("tr[valign=top]");

        // Индекс элемента массива
        int index = 0;

        // Берем цифры из шаблона pattern xx.xx
        // date - это конечная переменная для вывода на экран
        for (Element name : names) {
            String dateString = name.select("th[id=dt]").text();
            String date = getDateFrom(dateString);

            // Вывод на экран
            System.out.println();
            System.out.println("Погода в Санкт-Петербурге. По данным сайта pogoda.spb.ru");
            System.out.println(date); //+ "   Явления                      Темп.   Давл.  Влажн.     Ветер"
            int iterationCount = printPartValues(values , index);
            index = index + iterationCount;
        }
    }

}
