package gatlingTests.requests

import com.github.phisgr.gatling.kt.grpc.grpc
import com.github.phisgr.gatling.kt.grpc.payload
import org.feuyeux.grpc.proto.LandingServiceGrpc
import org.feuyeux.grpc.proto.TalkRequest

object SampleRequests {
    fun requestTwo() = grpc("Unary")
        .rpc(LandingServiceGrpc.getTalkMethod())
        .payload(TalkRequest::newBuilder) { session ->
            // dynamic payload!
            data = "Hello"
            build()
        }
        .check({ extract { it.status}})
}