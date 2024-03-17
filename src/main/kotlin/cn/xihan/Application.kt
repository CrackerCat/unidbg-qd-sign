package cn.xihan

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import java.io.File

val qdReaderJni by lazy {
    QDReaderJni(File("libs/com.qidian.QDReader_7.9.336_1206.apk"))
}

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }
    install(ConditionalHeaders)
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
    install(AutoHeadResponse)
    install(ShutDownUrl.ApplicationCallPlugin) {
        // The URL that will be intercepted (you can also use the application.conf's ktor.deployment.shutdown.url key)
        shutDownUrl = "/v1/shutdown"
        // A function that will be executed to get the exit code of the process
        exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
    }

    routing {
        get("/v1/sign") {
            val param = call.parameters["param"].orEmpty()
            if (param.isBlank()) {
                respondBadRequest()
                return@get
            }
            // url 解码
            val borgus = qdReaderJni.getSign(java.net.URLDecoder.decode(param, "UTF-8"))
            respondSuccess(borgus)
        }
    }
}

@kotlinx.serialization.Serializable
sealed class Response {

    @kotlinx.serialization.Serializable
    @SerialName("success")
    data class Success<T>(val data: T, val message: String = "", val result: Int = 0) : Response()

    @kotlinx.serialization.Serializable
    @SerialName("bad_request")
    data class BadRequest(val message: String = "请求参数错误", val result: Int = 400) : Response()

}

suspend inline fun PipelineContext<*, ApplicationCall>.respondBadRequest() = call.respond(
    Response.BadRequest("参数错误~~~")
)

suspend inline fun <reified T> PipelineContext<*, ApplicationCall>.respondSuccess(data: T) =
    call.respond(Response.Success(data))
