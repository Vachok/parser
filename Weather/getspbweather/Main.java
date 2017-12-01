/*
 * Vacok 2017.
 */

/*
 * Vacok 2017.
 */

package getspbweather;

import java.io.IOException;

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
public class Main {
    public static void main( String[] args ) throws IOException {
        GetWeather.main();
        System.out.println("Заебцом");
    }
}