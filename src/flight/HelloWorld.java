package flight;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
        FlightSearch f = new FlightSearch();

        f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "mel", "economy", 2, 2, 1);

        System.out.println(f);
    }
}
