package com.codepwn.x19viewer.httpUtils.netease

import com.codepwn.x19viewer.config.MainConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*

object X19Client {
    suspend fun request(url:String, jsonBody:String, userId:String, userToken:String): String {
        val getSessionId = HttpClient(OkHttp)

        val postData = getSessionId.post<String>(MainConfig.defaultServerUrl+url) {
            header("User-Agent", "okhttp/3.12.12")
            header("Content-Type", "application/json;charset=utf-8")
            header("user-id", userId)
            header("user-token", userToken)
            body = jsonBody
        }

        return postData
    }
}