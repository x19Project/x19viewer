package com.codepwn.x19viewer.httpUtils

// import com.codepwn.x19viewer.PluginMain.logger
import com.codepwn.x19viewer.cache.APICache
import com.codepwn.x19viewer.config.MainConfig
import io.ktor.client.*
import io.ktor.client.request.*
// import net.mamoe.mirai.utils.info
import org.json.JSONObject

object RequestTokenAPI {

    suspend fun pullTokenAPIJsonConfig(tokenAPIJSONUrl: String) {
        val getSessionId = HttpClient(io.ktor.client.engine.okhttp.OkHttp)

        val getData = getSessionId.get<String>(tokenAPIJSONUrl) {
            header("User-Agent", "okhttp/3.12.12 (Mirai x19viewer)")
        }

        val obj: JSONObject
        try {
            obj = JSONObject(getData)
        } catch (e: Exception) {
            throw Exception("无法正确解析配置JSON")
        }

        MainConfig.APIConfigUrl = tokenAPIJSONUrl
        APICache.sessionIdUrl = obj.getString("sessionIdUrl")
        APICache.encryptTokenUrl = obj.getString("encryptTokenUrl")
        // logger.info{ "pull config success" }
    }

    suspend fun getSessionId(): String {
        if(testSessionId()) {
            return APICache.session_id
        }

        val sessionIdUrl = APICache.sessionIdUrl
        val getSessionId = HttpClient(io.ktor.client.engine.okhttp.OkHttp)

        val idClientBody = JSONObject()
        idClientBody.put("token", MainConfig.Token)

        val postData = getSessionId.post<String>(sessionIdUrl) {
            header("Content-Type", "application/json;charset=utf-8")
            header("User-Agent", "okhttp/3.12.12 (Mirai x19viewer)")
            body = idClientBody.toString()
        }

        val obj = JSONObject(postData)
        if (!obj.getBoolean("success")) throw Exception(obj.getString("message"))

        APICache.session_id = obj.getString("session_id")
        return APICache.session_id
    }

    suspend fun getMyUserId(): String {
        val posted:String
        try {
            posted = getEncryptedTokenData(getSessionId(), "/a", "a")
        } catch (e:Exception) {
            throw Exception("getMyUserId Failed.")
        }

        return posted.split(":")[0]
    }

    private suspend fun testSessionId(): Boolean {
        try {
            getEncryptedTokenData(APICache.session_id, "/a", "a")
        } catch (e:Exception) {
            return false
        }
        return true
    }

    suspend fun getEncryptedTokenData(sessionId: String, requestUrl: String, requestValue: String): String {
        val encryptTokenUrl = APICache.encryptTokenUrl
        val getEncryptedTokenUrl = HttpClient(io.ktor.client.engine.okhttp.OkHttp)

        val tokenClientBody = JSONObject()
        tokenClientBody.put("path", requestUrl)
        tokenClientBody.put("body", requestValue)

        val postData = getEncryptedTokenUrl.post<String>(encryptTokenUrl) {
            header("Content-Type", "application/json;charset=utf-8")
            header("User-Agent", "okhttp/3.12.12 (Mirai x19viewer)")
            header("session-id", sessionId)
            body = tokenClientBody.toString()
        }

        val obj = JSONObject(postData)
        if (!obj.getBoolean("success")) throw Exception(obj.getString("message"))

        return obj.getString("user-id") + ":" + obj.getString("user-token")
    }
}