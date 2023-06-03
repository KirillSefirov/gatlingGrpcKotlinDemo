package gatlingTests

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

fun main() {
    val props = GatlingPropertiesBuilder()
        .simulationClass(Debug::class.qualifiedName)

    Gatling.fromMap(props.build())
}
