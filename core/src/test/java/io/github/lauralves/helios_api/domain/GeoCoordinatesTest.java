package io.github.lauralves.helios_api.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GeoCoordinatesTest {

    @Test
    void acceptsBoundaryValues() {
        assertDoesNotThrow(() -> new GeoCoordinates(90.0, 180.0));
        assertDoesNotThrow(() -> new GeoCoordinates(-90.0, -180.0));
    }

    @Test
    void rejectsLatitudeOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> new GeoCoordinates(90.1, 0.0));
    }

    @Test
    void rejectsLongitudeOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> new GeoCoordinates(0.0, -180.1));
    }
}
