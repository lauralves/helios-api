package io.github.lauralves.helios_api.adapters.out.gateway.dto;

import java.util.List;

public record OpenSkyStatesResponse(long time, List<List<Object>> states) {
}
