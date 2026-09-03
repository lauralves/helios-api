package io.github.lauralves.helios_api.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlightStateTest {

    private final Icao24 icao24 = new Icao24("4ca87f");
    private final Instant now = Instant.now();

    @Test
    void rejectsMissingLastContact() {
        assertThrows(IllegalArgumentException.class, () -> new FlightState(
                icao24, "TAP123", "Portugal", null, null, false, null, null, null, null));
    }

    @Test
    void blankCallsignIsNormalizedToNull() {
        FlightState state = new FlightState(
                icao24, "   ", "Portugal", null, null, false, null, null, null, now);

        assertNull(state.callsign());
    }

    @Test
    void airborneReflectsOnGroundFlag() {
        FlightState airborne = new FlightState(
                icao24, "TAP123", "Portugal", new GeoCoordinates(38.7, -9.1), 10000.0, false, 230.0, 90.0, 0.0, now);
        FlightState grounded = new FlightState(
                icao24, "TAP123", "Portugal", new GeoCoordinates(38.7, -9.1), 0.0, true, 0.0, 0.0, 0.0, now);

        assertTrue(airborne.isAirborne());
        assertFalse(grounded.isAirborne());
    }

    @Test
    void hasPositionReflectsPresenceOfCoordinates() {
        FlightState withPosition = new FlightState(
                icao24, "TAP123", "Portugal", new GeoCoordinates(38.7, -9.1), null, false, null, null, null, now);
        FlightState withoutPosition = new FlightState(
                icao24, "TAP123", "Portugal", null, null, false, null, null, null, now);

        assertTrue(withPosition.hasPosition());
        assertFalse(withoutPosition.hasPosition());
    }
}
