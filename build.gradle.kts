import com.google.protobuf.gradle.*
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.9.2"
    id("io.gatling.gradle") version "3.9.3.1"
}

dependencies {
    implementation("io.grpc:grpc-protobuf:1.53.0")
    implementation("io.grpc:grpc-stub:1.53.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    gatling("io.grpc:grpc-protobuf:1.53.0")
    gatling("com.github.phisgr:gatling-grpc:0.16.0")
    gatling("com.github.phisgr:gatling-grpc-kt:0.15.1")
    gatling("ch.qos.logback.contrib:logback-janino-fragment:0.1.5")
    gatling("org.codehaus.janino:janino:3.1.9")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.22.2"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.53.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

gatling {
    // WARNING: options below only work when logback config file isn't provided
//    logLevel = "WARN" // logback root level
    logHttp = io.gatling.gradle.LogHttp.NONE // set to 'ALL' for all HTTP traffic in TRACE, 'FAILURES' for failed HTTP traffic in DEBUG
}

repositories {
    mavenCentral()
}