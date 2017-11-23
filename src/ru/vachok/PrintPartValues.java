package ru.vachok;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PrintPartValues {

    private static int printPartValues( Elements values , int index ) {
        int iterationCount = 4;
        if (index == 0) {
            // Проверка третьего элемента на соответствие, для корректного вывода строк
            Element valueLn = values.get(3);
            boolean isMorning = valueLn.text().contains("Утро");
            boolean isDay = valueLn.text().contains("День");
            // Если элемент массива valueLn номер 3
            // содержит Утро - выводим только 3 первые строчки сегодняшнего дня
            if (isMorning) {
                iterationCount = 3;
            }
            if (isDay) {
                iterationCount = 2;
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
        }
        return iterationCount;
    }
}