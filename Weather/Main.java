import java.io.IOException;

class Main {


    public static int i;
    static String date;

    public static void setI( int i ) {
        Main.i = i;
    }

    public static void main( String[] args ) throws IOException {
        date = GetSPBWeather.dateGet();
                System.out.println(date);
        i = GetSPBWeather.showSPBvalues(GetSPBWeather.getTablefomCSS() , i);
                i = i + 5;
        System.out.println(i);
    }
}
