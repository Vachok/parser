import java.io.IOException;

class Main {

    static int i;
    static String date;

    public static void main( String[] args ) throws IOException {
        date = GetSPBWeather.dateGet();
                System.out.println(date);
        i = GetSPBWeather.showSPBvalues(GetSPBWeather.tablefomCSS , i);
                i = 5-i;
        System.out.println(i);
    }
}
