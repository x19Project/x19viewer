package com.codepwn.x19viewer.command

import com.codepwn.x19viewer.PluginMain
import com.codepwn.x19viewer.cache.APICache
import com.codepwn.x19viewer.config.MainConfig
import com.codepwn.x19viewer.httpUtils.RequestTokenAPI
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand

object ManageCommand : CompositeCommand(
    owner = PluginMain,
    primaryName = "x19-manage",
    description = "管理员命令"
) {

    @SubCommand
    @Description("API JSON配置拉取")
    suspend fun CommandSender.setConfigUrl(configUrl: String) {
        sendMessage("正在尝试拉取配置")

        try {
            RequestTokenAPI.pullTokenAPIJsonConfig(configUrl)
        } catch (e: Exception) {
            sendMessage("拉取失败, 原因：$e")
            return
        }
        sendMessage(
            """
            拉取成功!
            目标JSON配置地址: ${MainConfig.APIConfigUrl}
            sessionIdUrl: ${APICache.sessionIdUrl}
            encryptTokenUrl: ${APICache.encryptTokenUrl}
            您还需要执行一次”/x19-manage updateToken <token>“ 以更新Token。
        """.trimIndent()
        )
    }

    @SubCommand
    @Description("更新Token")
    suspend fun CommandSender.updateToken(token: String) {
        sendMessage("正在校验Token")
        MainConfig.Token = token
        try {
            RequestTokenAPI.getSessionId()
        } catch (e: Exception) {
            sendMessage("校验失败，原因：$e")
            return
        }
        sendMessage("校验成功，请妥善保管Token，一旦失效请更新")
    }
}