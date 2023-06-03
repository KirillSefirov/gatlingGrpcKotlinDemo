package gatlingTests

import com.github.phisgr.gatling.kt.grpc.grpc
import io.grpc.ManagedChannelBuilder

object Grpc {
    val host = "127.0.0.1"
    val port = 9996
    val protocol = grpc(ManagedChannelBuilder.forAddress(host, port).usePlaintext())
}
