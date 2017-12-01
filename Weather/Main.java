class Main {


    public static int index;
    static String date;

    public static void main( String[] args )  {
        date = GetSPBWeather.dateGet();
        System.out.println(date);
        index = GetSPBWeather.showSPBvalues(GetSPBWeather.getTablefomCSS() , index);
        System.out.println(index + "Main");
    }
}
