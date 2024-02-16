plugins {
    val kotlinVersion = "2.0.0-Beta4"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("io.ktor.plugin") version "3.0.0-beta-1"
    application
}

group = "xihan.cn"
version = "0.0.1"

application {
    mainClass.set("cn.xihan.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/releases")
    mavenCentral()
}

dependencies {
    implementation(fileTree("dir" to file("libs"), "include" to listOf("*.jar")))
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-conditional-headers-jvm")
    implementation("io.ktor:ktor-server-compression-jvm")
    implementation("io.ktor:ktor-server-auto-head-response-jvm")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation(kotlin("test-junit"))

}

kotlin {
    jvmToolchain(17)
}

ktor {
    fatJar {
        archiveFileName.set("unidbg-qd-sign-fat.jar")
    }
    docker {
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName.set("unidbg-qd-sign-image")
        imageTag.set("0.0.1-preview")
        externalRegistry.set(
            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
                appName = provider { "unidbg-qd-sign" },
                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
            )
        )
    }
}