package io.github.lauralves.helios_api.application.services;

import io.github.lauralves.helios_api.application.ports.in.usecase.GetCurrentFlightStateUseCase;
import io.github.lauralves.helios_api.application.ports.out.FlightTrackingOutputPort;
import io.github.lauralves.helios_api.domain.FlightState;
import io.github.lauralves.helios_api.domain.Icao24;

import java.util.Optional;

public class GetCurrentFlightStateService implements GetCurrentFlightStateUseCase {

    private final FlightTrackingOutputPort flightTrackingOutputPort;

    public GetCurrentFlightStateService(FlightTrackingOutputPort flightTrackingOutputPort) {
        this.flightTrackingOutputPort = flightTrackingOutputPort;
    }

    @Override
    public Optional<FlightState> getFlightStateByIcao(Icao24 icao24) {
        return flightTrackingOutputPort.getFlight(icao24);
    }
}
