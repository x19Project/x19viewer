package com.codepwn.x19viewer.command

import com.codepwn.x19viewer.PluginMain
import com.codepwn.x19viewer.httpUtils.netease.PlayerProfile
import com.codepwn.x19viewer.imageCut.PlayerProfileCut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.UserCommandSender

object TestCommand : CompositeCommand(
    owner = PluginMain,
    primaryName = "x19-test",
    description = "测试命令"
) {
    @SubCommand
    @Description("测试获取机器人自己的状态")
    suspend fun CommandSender.getMyState() {
        sendMessage("请稍后")
        try {
            //val myUserId = RequestTokenAPI.getMyUserId()
            val results = PlayerProfile.getUserState("2673373749")
            sendMessage(results)
            return
        } catch (e:Exception) {
            sendMessage("发生错误，$e")
            return
        }
    }

    @SubCommand
    @Description("测试发送截屏")
    suspend fun UserCommandSender.screen(userName: String) {
        try {
            val externalRes = PlayerProfileCut.cutPicture(userName)
            val img = user.uploadImage(externalRes)
            sendMessage(img)
            withContext(Dispatchers.IO) {
                externalRes.close()
            }

        } catch (e:Exception) {
            sendMessage("发生错误：$e")
        }
    }
}