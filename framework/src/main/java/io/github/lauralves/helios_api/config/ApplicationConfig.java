package io.github.lauralves.helios_api.config;

import io.github.lauralves.helios_api.application.ports.in.usecase.GetCurrentFlightStateUseCase;
import io.github.lauralves.helios_api.application.ports.out.FlightTrackingOutputPort;
import io.github.lauralves.helios_api.application.services.GetCurrentFlightStateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wires {@code application} use case implementations to their ports.
 * <p>
 * Kept isolated here so that the {@code application} module does not need to
 * depend on Spring's component-scanning annotations. Adapters that already
 * live in {@code framework} (e.g. {@code FlightTrackingGateway}) self-register
 * via their own stereotype annotations instead.
 * </p>
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public GetCurrentFlightStateUseCase getCurrentFlightStateUseCase(FlightTrackingOutputPort flightTrackingOutputPort) {
        return new GetCurrentFlightStateService(flightTrackingOutputPort);
    }
}
