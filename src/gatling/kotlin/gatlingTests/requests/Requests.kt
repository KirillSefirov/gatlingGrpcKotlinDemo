package gatlingTests.requests

import com.github.phisgr.gatling.kt.grpc.*
import com.github.phisgr.gatling.kt.grpc.action.BidiStreamStartActionBuilder
import com.github.phisgr.gatling.kt.grpc.action.ClientStreamStartActionBuilder
import com.github.phisgr.gatling.kt.grpc.action.ServerStreamStartActionBuilder
import com.github.phisgr.gatling.kt.grpc.action.StreamSendBuilder
import org.feuyeux.grpc.proto.LandingServiceGrpc
import org.feuyeux.grpc.proto.TalkRequest
import org.feuyeux.grpc.proto.TalkResponse

object Requests {
    //Sending Unary Request
    fun unaryRequest() = grpc("Unary Request")
        .rpc(LandingServiceGrpc.getTalkMethod())
        .payload(TalkRequest::newBuilder) { session ->
            data = "Hello"
            build()
        }
        .check({ extract { it.allFields}.saveAs("unaryResponse")})


    //Connecting to Server Stream
    val stream: ServerStream<TalkRequest, TalkResponse>  = grpc("Server Stream").serverStream(LandingServiceGrpc.getTalkOneAnswerMoreMethod(), "Server Stream")
    val serverStreamRequest: ServerStreamStartActionBuilder<TalkRequest, TalkResponse> = stream
        .start(TalkRequest::newBuilder){session ->
            data = "4"
            build()
        }
        .check({ extract { it.status}})
        .timestampExtractor { session, message, streamStartTime ->
            println("Message $message")
            streamStartTime
        }
        .endCheck()

    //Starting Stream from Client side and receiving the end response from Server
    val clientStream: ClientStream<TalkRequest, TalkResponse>  = grpc("Client Stream").clientStream(LandingServiceGrpc.getTalkMoreAnswerOneMethod(), "Client Stream")

    val clientStreamConnect: ClientStreamStartActionBuilder<TalkRequest, TalkResponse> = clientStream
        .connect()
        .check({ extract { it.allFields}.saveAs("ClientStreamResponse")})

    val clientStreamSend: StreamSendBuilder<TalkRequest> = clientStream.send(TalkRequest::newBuilder) { session ->
        data = "Hello"
        build()
    }
    val clientStreamEnd = clientStream.completeAndWait()

    //Starting Bi-directional stream, sending requests and waiting for responses
    val bidirectionalStream: BidiStream<TalkRequest, TalkResponse>  =
        grpc("Bidirectional Stream").bidiStream(LandingServiceGrpc.getTalkBidirectionalMethod(), "Bidirectional Stream")
    val bidirectionalStreamConnect:BidiStreamStartActionBuilder<TalkRequest, TalkResponse> = bidirectionalStream
        .connect()
        .timestampExtractor { session, message, streamStartTime ->
            println("Message $message")
            streamStartTime
        }

    val bidirectionalStreamSend: StreamSendBuilder<TalkRequest>  = bidirectionalStream.send(TalkRequest::newBuilder) { session ->
        data = "Hello"
        build()
    }

    val bidirectionalStreamEnd = bidirectionalStream.complete()
}