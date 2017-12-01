class Main {

    static int i;

    public static void main( String[] args ) {
        i = GetSPBWeather.showSPBvalues(GetSPBWeather.values , i);
        i = 3-i;
        System.out.println(i);
    }

}
