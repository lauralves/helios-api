package io.github.lauralves.helios_api.adapters.in.web;

import io.github.lauralves.helios_api.application.ports.in.usecase.GetCurrentFlightStateUseCase;
import io.github.lauralves.helios_api.domain.FlightState;
import io.github.lauralves.helios_api.domain.Icao24;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final GetCurrentFlightStateUseCase getCurrentFlightStateUseCase;

    public FlightController(GetCurrentFlightStateUseCase getCurrentFlightStateUseCase) {
        this.getCurrentFlightStateUseCase = getCurrentFlightStateUseCase;
    }

    @GetMapping("/{icao24}")
    public ResponseEntity<FlightState> getFlight(@PathVariable String icao24) {
        return getCurrentFlightStateUseCase.getFlightStateByIcao(new Icao24(icao24))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleInvalidIcao24() {
        return ResponseEntity.badRequest().build();
    }
}
