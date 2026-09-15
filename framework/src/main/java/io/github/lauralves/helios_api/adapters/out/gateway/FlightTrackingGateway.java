package io.github.lauralves.helios_api.adapters.out.gateway;

import io.github.lauralves.helios_api.application.ports.out.FlightTrackingOutputPort;
import io.github.lauralves.helios_api.domain.FlightState;
import io.github.lauralves.helios_api.domain.GeoCoordinates;
import io.github.lauralves.helios_api.domain.Icao24;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Fetches live aircraft state vectors from the OpenSky Network REST API.
 *
 * @see <a href="https://openskynetwork.github.io/opensky-api/rest.html">OpenSky REST API</a>
 */
@Component
public class FlightTrackingGateway implements FlightTrackingOutputPort {

    private static final String OPENSKY_BASE_URL = "https://opensky-network.org/api";

    private final RestClient restClient;

    public FlightTrackingGateway() {
        this.restClient = RestClient.builder().baseUrl(OPENSKY_BASE_URL).build();
    }

    @Override
    public Optional<FlightState> getFlight(Icao24 icao24) {
        return getAllFlights(List.of(icao24)).stream().findFirst();
    }

    @Override
    public List<FlightState> getAllFlights(List<Icao24> icao24List) {
        OpenSkyStatesResponse response = restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/states/all");
                    icao24List.forEach(icao24 -> uriBuilder.queryParam("icao24", icao24.value()));
                    return uriBuilder.build();
                })
                .retrieve()
                .body(OpenSkyStatesResponse.class);

        if (response == null || response.states() == null) {
            return List.of();
        }

        List<FlightState> flightStates = new ArrayList<>(response.states().size());
        for (List<Object> state : response.states()) {
            toFlightState(state).ifPresent(flightStates::add);
        }
        return flightStates;
    }

    private Optional<FlightState> toFlightState(List<Object> state) {
        if (!(state.get(0) instanceof String icao24Value) || !(state.get(4) instanceof Number lastContact)) {
            return Optional.empty();
        }

        Double latitude = toDouble(state.get(6));
        Double longitude = toDouble(state.get(5));
        GeoCoordinates position = (latitude != null && longitude != null)
                ? new GeoCoordinates(latitude, longitude)
                : null;

        return Optional.of(new FlightState(
                new Icao24(icao24Value),
                (String) state.get(1),
                (String) state.get(2),
                position,
                toDouble(state.get(7)),
                Boolean.TRUE.equals(state.get(8)),
                toDouble(state.get(9)),
                toDouble(state.get(10)),
                toDouble(state.get(11)),
                Instant.ofEpochSecond(lastContact.longValue())
        ));
    }

    private static Double toDouble(Object value) {
        return value instanceof Number number ? number.doubleValue() : null;
    }
}
