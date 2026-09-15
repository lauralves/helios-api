package io.github.lauralves.helios_api.adapters.out.gateway;

import java.util.List;

record OpenSkyStatesResponse(long time, List<List<Object>> states) {
}
