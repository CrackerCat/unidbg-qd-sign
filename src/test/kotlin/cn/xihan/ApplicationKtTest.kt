package cn.xihan


import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*


import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationKtTest {

    @Test
    fun testGetV1Sign() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
        client.get("/v1/sign") {
            parameter(
                "param",
                "clienttype=1&qimei=cc51cce4aafd4d0e0fd61031100014816b02&tstamp=1707126825671&versioncode=1186"
            )
        }.apply {
            assertEquals("2f2831a2b75e8a4b9c08ee9a38cadb8c", body<Response.Success<String>>().data)
        }
    }
}