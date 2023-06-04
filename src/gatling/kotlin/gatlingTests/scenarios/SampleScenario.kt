package gatlingTests.scenarios

import gatlingTests.requests.SampleRequests
import io.gatling.javaapi.core.CoreDsl.*

object SampleScenario {

    val sendUnary = scenario("Unary")
        .exec(SampleRequests.unaryRequest())

    val serverStream = scenario("Server Stream")
        .exec(SampleRequests.serverStreamRequest).during(10).on(pause(1))

    val clientStream =  scenario("Client Stream")
        .exec(SampleRequests.clientStreamConnect)
        .repeat(3).on(
            pause(3)
                .exec(SampleRequests.clientStreamSend))
        .exec(SampleRequests.clientStreamEnd)
        .exec{session ->
            println(session.getString("ClientStreamResponse"))
            session
        }

    val bidirectionalStream = scenario("Bidirectional Stream")
        .exec(SampleRequests.bidirectionalStreamConnect)
        .repeat(5).on(
            pause(3)
                .exec(SampleRequests.bidirectionalStreamSend)
                .pause(1)
        )
        .exec(SampleRequests.bidirectionalStreamEnd)


}

