package gatlingTests.scenarios

import gatlingTests.requests.SampleRequests
import io.gatling.javaapi.core.CoreDsl.*

object SampleScenario {

    val sendUnary = scenario("Unary")
        .exec(SampleRequests.requestTwo())
//

}

