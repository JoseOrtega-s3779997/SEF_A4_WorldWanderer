package flight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;

public class FlightSearch {
   private String  departureDate;
   private String  departureAirportCode;
   private boolean emergencyRowSeating;
   private String  returnDate;
   private String  destinationAirportCode;
   private String  seatingClass;
   private int     adultPassengerCount;
   private int     childPassengerCount;
   private int     infantPassengerCount;

   public FlightSearch() {}

   public String getDepartureDate() {
      return departureDate;
   }

   public String getDepartureAirportCode() {
      return departureAirportCode;
   }

   public boolean isEmergencyRowSeating() {
      return emergencyRowSeating;
   }

   public String getReturnDate() {
      return returnDate;
   }

   public String getDestinationAirportCode() {
      return destinationAirportCode;
   }

   public String getSeatingClass() {
      return seatingClass;
   }

   public int getAdultPassengerCount() {
      return adultPassengerCount;
   }

   public int getChildPassengerCount() {
      return childPassengerCount;
   }

   public int getInfantPassengerCount() {
      return infantPassengerCount;
   }

   public int getTotalPassengers(int adultPassengerCount, int childPassengerCount, int infantPassengerCount) {
      return adultPassengerCount +  childPassengerCount + infantPassengerCount;
   }

   public boolean runFlightSearch(String departureDate, String departureAirportCode, boolean emergencyRowSeating,
                                  String returnDate, String destinationAirportCode, String seatingClass,
                                  int adultPassengerCount, int childPassengerCount, int infantPassengerCount) {

      boolean valid = true;

      //TODO: Validate all the provided parameters.
      //if the search parameters meets the given conditions,
      //   the function should initialise all the class attributes and return true.
      //else
      //   the function should return false

      List<String> validAirports = Arrays.asList("syd", "mel", "lax", "cdg", "del", "pvg", "doh");
      List<String> validClasses = Arrays.asList("economy", "premium economy", "business", "first");

      // 1. Passenger total
      int totalPassengers = adultPassengerCount + childPassengerCount + infantPassengerCount;

      if (totalPassengers < 1 || totalPassengers > 9)
         valid = false;

      // 2. Children seating restriction
      if (childPassengerCount > 0 && (emergencyRowSeating || seatingClass.equals("first")))
         valid = false;

      // 3. Infant seating restriction
      if (infantPassengerCount > 0 && (emergencyRowSeating || seatingClass.equals("business")))
         valid = false;

      // 4. Child to adult ratio (max 2 children per adult)
      if (adultPassengerCount == 0 && childPassengerCount > 0)
         valid = false;

      if (childPassengerCount > adultPassengerCount * 2)
         valid = false;

      // 5. Infant per adult (1 infant per adult)
      if (infantPassengerCount > adultPassengerCount)
         valid = false;

      // 6–8. Date validation
      if (valid) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                 .withResolverStyle(ResolverStyle.STRICT);
         LocalDate depDate;
         LocalDate retDate;

         try {
            depDate = LocalDate.parse(departureDate, formatter);
            retDate = LocalDate.parse(returnDate, formatter);

            if (depDate.isBefore(LocalDate.now())) valid = false;
            if (retDate.isBefore(depDate)) valid = false;

         } catch (Exception e) {
            valid = false;
         }
      }

      // 9. Valid class
      if (!validClasses.contains(seatingClass))
         valid = false;

      // 10. Only economy class seating can have an emergency row (all classes of seating can be non-emergency).
      if (emergencyRowSeating && !seatingClass.equals("economy"))
         valid = false;

      // 11. Valid airport codes + not same
      if (!validAirports.contains(departureAirportCode) || !validAirports.contains(destinationAirportCode))
         valid = false;

      if (departureAirportCode.equals(destinationAirportCode))
         valid = false;

      if (valid) {
         this.departureDate = departureDate;
         this.departureAirportCode = departureAirportCode;
         this.emergencyRowSeating = emergencyRowSeating;
         this.returnDate = returnDate;
         this.destinationAirportCode = destinationAirportCode;
         this.seatingClass = seatingClass;
         this.adultPassengerCount = adultPassengerCount;
         this.childPassengerCount = childPassengerCount;
         this.infantPassengerCount = infantPassengerCount;
      }
      return valid;
   }

   @Override
   public String toString() {
      return departureDate + ", " + departureAirportCode + ", " + emergencyRowSeating
              + ", " + returnDate + ", " + destinationAirportCode + ", " + seatingClass
              + ", " + adultPassengerCount + ", " + childPassengerCount + ", " + infantPassengerCount + "\n";
   }
}
