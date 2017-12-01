/*
 * Vacok 2017.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface ButtonEventListener extends ActionListener {
    default void actionPerformed( ActionEvent e ) {
        int showSPBvalues = GetSPBWeather.showSPBvalues(GetSPBWeather.getTablefomCSS() , 4);
        System.out.println(showSPBvalues);
    }
}
