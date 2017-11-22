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

import static ru.vachok.PrintPartValues.printPartValues;


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

    //Точка вывода
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
