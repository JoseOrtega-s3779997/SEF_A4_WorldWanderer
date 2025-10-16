package flight;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlightSearchTest {

    // -----------------------
    // Condition 1: Passenger count (1–9)
    // -----------------------
    @Test
    public void testCondition1_PassengerCount() {
        FlightSearch f = new FlightSearch();

        // Lower boundary: 0 passengers
        assertFalse(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "mel", "economy", 0, 0, 0));

        // Upper boundary: 10 passengers
        assertFalse(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "mel", "economy", 5, 4, 1));
    }

    // -----------------------
    // Condition 2: Children cannot be in emergency row or first class
    // -----------------------
    @Test
    public void testCondition2_ChildSeatingRestriction() {
        FlightSearch f = new FlightSearch();

        // Child in emergency row
        assertFalse(f.runFlightSearch("15/12/2025", "mel", true,
                "20/12/2025", "pvg", "economy", 1, 1, 0));

        // Child in first class
        assertFalse(f.runFlightSearch("15/12/2025", "mel", false,
                "20/12/2025", "pvg", "first", 1, 1, 0));
    }

    // -----------------------
    // Condition 3: Infants cannot be in emergency row or business class
    // -----------------------
    @Test
    public void testCondition3_InfantSeatingRestriction() {
        FlightSearch f = new FlightSearch();

        // Infant in emergency row
        assertFalse(f.runFlightSearch("10/01/2026", "syd", true,
                "15/01/2026", "lax", "economy", 1, 0, 1));

        // Infant in business class
        assertFalse(f.runFlightSearch("10/01/2026", "syd", false,
                "15/01/2026", "lax", "business", 1, 0, 1));
    }

    // -----------------------
    // Condition 4: Child-to-adult ratio (max 2 children per adult)
    // -----------------------
    @Test
    public void testCondition4_ChildToAdultRatio() {
        FlightSearch f = new FlightSearch();

        // 1 adult, 3 children (boundary)
        assertFalse(f.runFlightSearch("05/12/2025", "mel", false,
                "12/12/2025", "pvg", "economy", 1, 3, 0));

        // 0 adults, 1 child
        assertFalse(f.runFlightSearch("05/12/2025", "mel", false,
                "12/12/2025", "pvg", "economy", 0, 1, 0));
    }

    // -----------------------
    // Condition 5: Infant-to-adult ratio (max 1 infant per adult)
    // -----------------------
    @Test
    public void testCondition5_InfantToAdultRatio() {
        FlightSearch f = new FlightSearch();

        // 1 adult, 2 infants (boundary)
        assertFalse(f.runFlightSearch("15/01/2026", "lax", false,
                "25/01/2026", "cdg", "economy", 1, 0, 2));

        // 0 adults, 1 infant
        assertFalse(f.runFlightSearch("15/01/2026", "lax", false,
                "25/01/2026", "cdg", "economy", 0, 0, 1));
    }

    // -----------------------
    // Condition 6: Departure date cannot be in the past
    // -----------------------
    @Test
    public void testCondition6_DepartureDatePast() {
        FlightSearch f = new FlightSearch();

        assertFalse(f.runFlightSearch("10/10/2020", "syd", false,
                "15/10/2020", "mel", "economy", 1, 0, 0));

        assertFalse(f.runFlightSearch("01/01/2023", "mel", false,
                "05/01/2023", "syd", "economy", 1, 0, 0));
    }

    // -----------------------
    // Condition 7: Return date cannot be before departure
    // -----------------------
    @Test
    public void testCondition7_ReturnBeforeDeparture() {
        FlightSearch f = new FlightSearch();

        assertFalse(f.runFlightSearch("25/11/2025", "mel", false,
                "20/11/2025", "pvg", "economy", 1, 0, 0));

        assertFalse(f.runFlightSearch("10/12/2025", "lax", false,
                "05/12/2025", "cdg", "economy", 1, 0, 0));
    }

    // -----------------------
    // Condition 8: Strict date validation (format and valid calendar dates)
    // -----------------------
    @Test
    public void testCondition8_InvalidDates() {
        FlightSearch f = new FlightSearch();

        // Invalid day
        assertFalse(f.runFlightSearch("32/01/2025", "syd", false,
                "02/02/2025", "mel", "economy", 1, 0, 0));

        // Invalid leap day
        assertFalse(f.runFlightSearch("29/02/2026", "syd", false,
                "05/03/2026", "mel", "economy", 1, 0, 0));
    }

    // -----------------------
    // Condition 9: Seating class must be valid
    // -----------------------
    @Test
    public void testCondition9_SeatingClass() {
        FlightSearch f = new FlightSearch();

        assertFalse(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "mel", "ultra economy", 1, 0, 0));

        assertFalse(f.runFlightSearch("20/11/2025", "mel", false,
                "25/11/2025", "syd", "super class", 1, 0, 0));
    }

    // -----------------------
    // Condition 10: Emergency row only in economy
    // -----------------------
    @Test
    public void testCondition10_EmergencyRow() {
        FlightSearch f = new FlightSearch();

        assertFalse(f.runFlightSearch("20/11/2025", "syd", true,
                "25/11/2025", "mel", "business", 1, 0, 0));

        assertFalse(f.runFlightSearch("20/11/2025", "mel", true,
                "25/11/2025", "syd", "first", 1, 0, 0));
    }

    // -----------------------
    // Condition 11: Airport codes must be valid and not the same
    // -----------------------
    @Test
    public void testCondition11_Airports() {
        FlightSearch f = new FlightSearch();

        // Invalid departure airport
        assertFalse(f.runFlightSearch("20/11/2025", "xyz", false,
                "25/11/2025", "mel", "economy", 1, 0, 0));

        // Departure and destination same
        assertFalse(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "syd", "economy", 1, 0, 0));
    }

    // -----------------------
    // Condition 12: Valid inputs (boundary values included)
    // -----------------------
    @Test
    public void testCondition12_ValidInput() {
        FlightSearch f = new FlightSearch();

        assertTrue(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "mel", "economy", 2, 2, 1));

        assertTrue(f.runFlightSearch("05/12/2025", "mel", false,
                "12/12/2025", "pvg", "premium economy", 2, 4, 2));

        assertTrue(f.runFlightSearch("10/01/2026", "lax", false,
                "15/01/2026", "cdg", "business", 1, 0, 0));

        assertTrue(f.runFlightSearch("10/03/2026", "del", false,
                "20/03/2026", "doh", "first", 1, 0, 0));
    }
}

