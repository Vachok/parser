package ru.vachok;

/**Вывод:
 * <p>Утро
 <p>Пасмурно. Небольшой снег. Метель.
 <p>0..-2
 <p>757
 <p>89%
 <p>[Ю] 3-5 м/с
 <p>03.12
 <p>Заебцом</p>
 @since 0.171129.3
 */
class Main {
    public static void main( String[] args ) throws Exception {
        ru.vachok.spb.GetWeather.main();
        System.out.println("Заебцом");
    }
}