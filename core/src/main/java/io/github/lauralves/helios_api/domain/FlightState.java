package io.github.lauralves.helios_api.domain;

import java.time.Instant;

/**
 * A snapshot of an aircraft's reported state at a point in time, as observed
 * from live tracking data.
 *
 * @param icao24        unique aircraft transponder address
 * @param callsign      flight callsign as broadcast, may be {@code null} when unavailable
 * @param originCountry country inferred from the ICAO24 address
 * @param position      last known coordinates, {@code null} when the aircraft has no recent position fix
 * @param baroAltitudeMeters barometric altitude in meters, {@code null} when unavailable
 * @param onGround      whether the aircraft is reported to be on the ground
 * @param velocityMps   ground speed in meters/second, {@code null} when unavailable
 * @param trueTrackDegrees track angle clockwise from north, {@code null} when unavailable
 * @param verticalRateMps climb/descent rate in meters/second, {@code null} when unavailable
 * @param lastContact   time of the last update received for this aircraft
 */
public record FlightState(
        Icao24 icao24,
        String callsign,
        String originCountry,
        GeoCoordinates position,
        Double baroAltitudeMeters,
        boolean onGround,
        Double velocityMps,
        Double trueTrackDegrees,
        Double verticalRateMps,
        Instant lastContact
) {

    public FlightState {
        if (icao24 == null) {
            throw new IllegalArgumentException("icao24 must not be null");
        }
        if (lastContact == null) {
            throw new IllegalArgumentException("lastContact must not be null");
        }
        if (callsign != null) {
            callsign = callsign.trim();
            if (callsign.isEmpty()) {
                callsign = null;
            }
        }
    }

    public boolean isAirborne() {
        return !onGround;
    }

    public boolean hasPosition() {
        return position != null;
    }
}
