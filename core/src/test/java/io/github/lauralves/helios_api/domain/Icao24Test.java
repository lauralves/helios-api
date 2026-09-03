package io.github.lauralves.helios_api.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Icao24Test {

    @Test
    void normalizesToLowercase() {
        Icao24 icao24 = new Icao24("4CA87F");
        assertEquals("4ca87f", icao24.value());
    }

    @Test
    void rejectsBlankValue() {
        assertThrows(IllegalArgumentException.class, () -> new Icao24(" "));
    }

    @Test
    void rejectsInvalidHexLength() {
        assertThrows(IllegalArgumentException.class, () -> new Icao24("4ca87"));
    }

    @Test
    void rejectsNonHexCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new Icao24("4ca87z"));
    }
}
