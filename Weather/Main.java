import java.io.IOException;

class Main {


    public static int index;
    static String date;

    public static void main( String[] args ) throws IOException {
        date = GetSPBWeather.dateGet();
                System.out.println(date);
        index = GetSPBWeather.showSPBvalues(GetSPBWeather.getTablefomCSS() , index);
        System.out.println(index + "Main");
    }
}
