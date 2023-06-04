package gatlingTests.scenarios

import gatlingTests.requests.Requests
import io.gatling.javaapi.core.CoreDsl.*

object Scenarios {

    val sendUnary = scenario("Unary")
        .exec(Requests.unaryRequest())

    val serverStream = scenario("Server Stream")
        .exec(Requests.serverStreamRequest).during(10).on(pause(1))

    val clientStream =  scenario("Client Stream")
        .exec(Requests.clientStreamConnect)
        .repeat(3).on(
            pause(3)
                .exec(Requests.clientStreamSend))
        .exec(Requests.clientStreamEnd)
        .exec{session ->
            println(session.getString("ClientStreamResponse"))
            session
        }

    val bidirectionalStream = scenario("Bidirectional Stream")
        .exec(Requests.bidirectionalStreamConnect)
        .repeat(5).on(
            pause(3)
                .exec(Requests.bidirectionalStreamSend)
                .pause(1)
        )
        .exec(Requests.bidirectionalStreamEnd)
}

