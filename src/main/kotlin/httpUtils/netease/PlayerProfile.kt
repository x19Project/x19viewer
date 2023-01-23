package com.codepwn.x19viewer.httpUtils.netease

import com.codepwn.x19viewer.httpUtils.RequestTokenAPI
import org.json.JSONObject

object PlayerProfile {

    suspend fun getUserState(user_id: String): String {
        val sessionId = RequestTokenAPI.getSessionId()
        val url = "/user-stat/get-user-state"
        val body = JSONObject()
        body.put("search_id", user_id)
        val bodyString = body.toString()

        val tokenData = RequestTokenAPI.getEncryptedTokenData(sessionId, url, bodyString)
        val clientUserId = tokenData.split(":")[0]
        val clientUserToken = tokenData.split(":")[1]

        val result = X19Client.request(url, bodyString, clientUserId, clientUserToken)
        val resultObj = JSONObject(result)
        if(resultObj.getInt("code") != 0) {
            throw Exception("Get failed.${resultObj.getString("message")}")
        }
        return result
    }

    suspend fun getUserInfo(user_name: String): String {
        val sessionId = RequestTokenAPI.getSessionId()
        val url = "/user/query/search-by-name"
        val body = JSONObject()
        body.put("name", user_name)
        val bodyString = body.toString()

        val tokenData = RequestTokenAPI.getEncryptedTokenData(sessionId, url, bodyString)
        val clientUserId = tokenData.split(":")[0]
        val clientUserToken = tokenData.split(":")[1]

        val result = X19Client.request(url, bodyString, clientUserId, clientUserToken)
        val resultObj = JSONObject(result)
        if(resultObj.getInt("code") != 0) {
            throw Exception("Get failed.${resultObj.getString("message")}")
        }
        return result
    }

}