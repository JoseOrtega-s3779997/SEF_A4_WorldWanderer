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
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "mel", "economy", 0, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // Upper boundary: 10 passengers
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "mel", "economy", 5, 4, 1));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 2: Children cannot be in emergency row or first class
    // -----------------------
    @Test
    public void testCondition2_ChildSeatingRestriction() {
        FlightSearch f = new FlightSearch();

        // Child in emergency row
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("15/12/2025", "mel", true,
                "20/12/2025", "pvg", "economy", 1, 1, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // Child in first class
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("15/12/2025", "mel", false,
                "20/12/2025", "pvg", "first", 1, 1, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 3: Infants cannot be in emergency row or business class
    // -----------------------
    @Test
    public void testCondition3_InfantSeatingRestriction() {
        FlightSearch f = new FlightSearch();

        // Infant in emergency row
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("10/01/2026", "syd", true,
                "15/01/2026", "lax", "economy", 1, 0, 1));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // Infant in business class
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("10/01/2026", "syd", false,
                "15/01/2026", "lax", "business", 1, 0, 1));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 4: Child-to-adult ratio (max 2 children per adult)
    // -----------------------
    @Test
    public void testCondition4_ChildToAdultRatio() {
        FlightSearch f = new FlightSearch();

        // 1 adult, 3 children
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("05/12/2025", "mel", false,
                "12/12/2025", "pvg", "economy", 1, 3, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // 0 adults, 1 child
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("05/12/2025", "mel", false,
                "12/12/2025", "pvg", "economy", 0, 1, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 5: Infant-to-adult ratio (max 1 infant per adult)
    // -----------------------
    @Test
    public void testCondition5_InfantToAdultRatio() {
        FlightSearch f = new FlightSearch();

        // 1 adult, 2 infants
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("15/01/2026", "lax", false,
                "25/01/2026", "cdg", "economy", 1, 0, 2));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // 0 adults, 1 infant
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("15/01/2026", "lax", false,
                "25/01/2026", "cdg", "economy", 0, 0, 1));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 6: Departure date cannot be in the past
    // -----------------------
    @Test
    public void testCondition6_DepartureDatePast() {
        FlightSearch f = new FlightSearch();

        // Clearly past dates
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("10/10/2020", "syd", false,
                "15/10/2020", "mel", "economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("01/01/2023", "mel", false,
                "05/01/2023", "syd", "economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 7: Return date cannot be before departure
    // -----------------------
    @Test
    public void testCondition7_ReturnBeforeDeparture() {
        FlightSearch f = new FlightSearch();

        // Return before departure
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("25/11/2025", "mel", false,
                "20/11/2025", "pvg", "economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // Another variation
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("10/12/2025", "lax", false,
                "05/12/2025", "cdg", "economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 8: Strict date validation (invalid or impossible dates)
    // -----------------------
    @Test
    public void testCondition8_InvalidDates() {
        FlightSearch f = new FlightSearch();

        // Invalid day
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("32/01/2025", "syd", false,
                "02/02/2025", "mel", "economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // Invalid leap day
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("29/02/2026", "syd", false,
                "05/03/2026", "mel", "economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 9: Seating class must be valid
    // -----------------------
    @Test
    public void testCondition9_SeatingClass() {
        FlightSearch f = new FlightSearch();

        // Invalid class names
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "mel", "ultra economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("20/11/2025", "mel", false,
                "25/11/2025", "syd", "super class", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 10: Only economy can have an emergency row
    // -----------------------
    @Test
    public void testCondition10_EmergencyRow() {
        FlightSearch f = new FlightSearch();

        // Business class, emergency row
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("20/11/2025", "syd", true,
                "25/11/2025", "mel", "business", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // First class, emergency row
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("20/11/2025", "mel", true,
                "25/11/2025", "syd", "first", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 11: Airport codes must be valid and not the same
    // -----------------------
    @Test
    public void testCondition11_Airports() {
        FlightSearch f = new FlightSearch();

        // Invalid departure airport
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("20/11/2025", "xyz", false,
                "25/11/2025", "mel", "economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        // Same departure/destination
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertFalse(f.runFlightSearch("20/11/2025", "syd", false,
                "25/11/2025", "syd", "economy", 1, 0, 0));
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

    // -----------------------
    // Condition 12: Valid inputs
    // -----------------------
    @Test
    public void testCondition12_ValidInput() {
        FlightSearch f = new FlightSearch();

        // Case 1: economy, no emergency, 1 adult, 1 child
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertTrue(f.runFlightSearch("20/11/2025", "mel", false,
                "25/11/2025", "pvg", "economy", 1, 1, 0));
        assertEquals(2, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        f = new FlightSearch();

        // Case 2: economy with emergency row allowed, adults only
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertTrue(f.runFlightSearch("01/12/2025", "syd", true,
                "10/12/2025", "lax", "economy", 2, 0, 0));
        assertEquals(2, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        f = new FlightSearch();

        // Case 3: premium economy, no emergency, 2 adults, 4 children, 2 infants
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertTrue(f.runFlightSearch("05/12/2025", "mel", false,
                "12/12/2025", "pvg", "premium economy", 2, 4, 2));
        assertEquals(8, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));

        f = new FlightSearch();

        // Case 4: first class, no emergency, single adult
        assertEquals(0, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
        assertTrue(f.runFlightSearch("10/03/2026", "del", false,
                "20/03/2026", "doh", "first", 1, 0, 0));
        assertEquals(1, f.getTotalPassengers(f.getAdultPassengerCount(), f.getChildPassengerCount(), f.getInfantPassengerCount()));
    }

}
