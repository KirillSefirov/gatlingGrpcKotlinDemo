package gatlingTests

import gatlingTests.scenarios.SampleScenario
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation

class Debug : Simulation() {
    init {
        setUp(
            SampleScenario.sendUnary.injectOpen(
                atOnceUsers(1)
            )
        )
            .protocols(Grpc.protocol)
            .maxDuration(30)
    }
}