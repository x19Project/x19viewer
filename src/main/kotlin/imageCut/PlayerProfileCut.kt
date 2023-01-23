package com.codepwn.x19viewer.imageCut

import com.codepwn.x19viewer.PluginMain.dataFolder
import com.codepwn.x19viewer.httpUtils.netease.PlayerProfile
import com.codepwn.x19viewer.utils.PluginUtils
import net.mamoe.mirai.utils.ExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import org.json.JSONObject
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import xyz.cssxsh.mirai.plugin.MiraiSeleniumPlugin
import java.io.File

object PlayerProfileCut {
    suspend fun cutPicture(user_name: String): ExternalResource {

        val driver = MiraiSeleniumPlugin.driver(SeleniumConfig)

        val html = File("${dataFolder}/webData/pages/playerProfile.html").readText()
        val cacheFile = File("${dataFolder}/webData/Cache_${PluginUtils.getRandomString(10)}.html")

        // get data
        val userInfo = JSONObject(PlayerProfile.getUserInfo(user_name))
        val userInfoEntity = userInfo.getJSONObject("entity")

        val userId = userInfoEntity.getString("entity_id")
        val userName = userInfoEntity.getString("name")
        val userAvatarUrl = userInfoEntity.getString("avatar_image_url")
        val userRegisterTime = userInfoEntity.getLong("register_time")
        val userLoginTime = userInfoEntity.getLong("login_time")
        val userSignature = signatureCheck(userInfoEntity.getString("signature"))

        val userState = JSONObject(PlayerProfile.getUserState(userId))
        val userStateEntity = userState.getJSONObject("entity")
        val userView = userStateEntity.getInt("personal_page_view_count")
        val userLike = userStateEntity.getInt("personal_page_like_count")
        val userFriend = userStateEntity.getInt("friend_cnt")
        val userFans = userStateEntity.getInt("public_user_fans_cnt")
        val generateTime = System.currentTimeMillis() / 1000

        // replace
        val newHtml = html
            .replace("%PLAYER_AVATAR%",userAvatarUrl)
            .replace("%PLAYER_NAME%", userName)
            .replace("%PLAYER_UID%", userId)
            .replace("%PLAYER_VIEW%", userView.toString())
            .replace("%PLAYER_LIKE%", userLike.toString())
            .replace("%PLAYER_FRIEND%", userFriend.toString())
            .replace("%PLAYER_FANS%", userFans.toString())
            .replace("%PLAYER_REGISTER_TIME%", PluginUtils.ts2d(userRegisterTime.toString()))
            .replace("%PLAYER_LOGIN%", PluginUtils.ts2d(userLoginTime.toString()))
            .replace("%PLAYER_SIGNATURE%", userSignature)
            .replace("%GENERATE_TIME%", PluginUtils.ts2d(generateTime.toString()))

        cacheFile.writeText(newHtml)

        driver.get(cacheFile.toString())
        val info = driver.findElement(By.xpath("/html/body/div"))
        val pic = info.getScreenshotAs(OutputType.FILE)
        driver.close()
        cacheFile.delete()
        return pic.toExternalResource()
    }
    private fun signatureCheck(signature: String): String {
        if(signature == "") {
            return "这位冒险家还没有签名~"
        }
        return signature.replace("\n","<br>")
    }
}