package gatlingTests

import gatlingTests.scenarios.Scenarios
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation

class Simulation : Simulation() {
    init {
        setUp(
            Scenarios.bidirectionalStream.injectOpen(
                atOnceUsers(1)
            )
        )
            .protocols(Grpc.protocol)
            .maxDuration(30)
    }
}
