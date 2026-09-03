package io.github.lauralves.helios_api.domain;

/**
 * WGS84 geographic coordinates of an aircraft.
 */
public record GeoCoordinates(double latitude, double longitude) {

    public GeoCoordinates {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("latitude must be between -90 and 90: " + latitude);
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("longitude must be between -180 and 180: " + longitude);
        }
    }
}
