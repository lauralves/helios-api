package io.github.lauralves.helios_api.application.ports.out;

import io.github.lauralves.helios_api.domain.FlightState;
import io.github.lauralves.helios_api.domain.Icao24;

import java.util.List;
import java.util.Optional;

public interface FlightTrackingPort {

    Optional<FlightState> getFlight(Icao24 icao24);

    List<FlightState> getAllFlights(List<Icao24> icao24List);
}
