package io.github.lauralves.helios_api.domain;

import java.util.regex.Pattern;

/**
 * ICAO 24-bit aircraft address, uniquely identifying an aircraft transponder.
 */
public record Icao24(String value) {

    private static final Pattern HEX_24_BIT = Pattern.compile("^[0-9a-f]{6}$");

    public Icao24 {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("icao24 must not be blank");
        }
        value = value.trim().toLowerCase();
        if (!HEX_24_BIT.matcher(value).matches()) {
            throw new IllegalArgumentException("icao24 must be a 6-character hex string: " + value);
        }
    }
}
