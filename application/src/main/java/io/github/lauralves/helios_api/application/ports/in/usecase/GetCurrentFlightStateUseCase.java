package io.github.lauralves.helios_api.application.ports.in.usecase;

import io.github.lauralves.helios_api.domain.FlightState;
import io.github.lauralves.helios_api.domain.Icao24;

import java.util.Optional;

public interface GetCurrentFlightStateUseCase {

    Optional<FlightState> getFlightStateByIcao(Icao24 icao24);
}
