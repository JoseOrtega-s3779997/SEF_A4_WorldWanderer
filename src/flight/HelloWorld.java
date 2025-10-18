package flight;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");

        FlightSearch f = new FlightSearch();

        f.runFlightSearch("20/11/2025", "mel", false,
                "25/11/2025", "pvg", "economy", 1, 1, 0);
        System.out.println(f);

        f.runFlightSearch("01/12/2025", "syd", true,
                "10/12/2025", "lax", "economy", 2, 0, 0);
        System.out.println(f);

        f.runFlightSearch("05/12/2025", "mel", false,
                "12/12/2025", "pvg", "premium economy", 2, 4, 2);
        System.out.println(f);

        f.runFlightSearch("10/03/2026", "del", false,
                "20/03/2026", "doh", "first", 1, 0, 0);
        System.out.println(f);
    }
}
