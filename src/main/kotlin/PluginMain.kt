package com.codepwn.x19viewer

import com.codepwn.x19viewer.cache.APICache
import com.codepwn.x19viewer.command.ManageCommand
import com.codepwn.x19viewer.command.TestCommand
import com.codepwn.x19viewer.config.MainConfig
import com.codepwn.x19viewer.utils.WebData
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info
import net.mamoe.mirai.utils.warning
import xyz.cssxsh.mirai.plugin.MiraiSeleniumPlugin
import kotlin.concurrent.thread

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "com.codepwn.x19viewer",
        name = "NeteaseMinecraftViewer",
        version = "1.0-SNAPSHOT",
    ) {
        author("CodePwn2021")
        info("""x19 viewer.""")

        dependsOn("xyz.cssxsh.mirai.plugin.mirai-selenium-plugin",false)
    }
) {
    override fun onEnable() {
        // logger.info { "Plugin loaded" }
        reloadData()
        if ((MainConfig.APIConfigUrl == "NOT_SET_API_URL") || (APICache.sessionIdUrl == "") || (APICache.encryptTokenUrl == "")) logger.warning("未设置API配置JSON的URL，请使用”/x19-manage setConfigUrl <配置地址>“来进行设置")

        registerCommands()
        setupSelenium()

        WebData.unzipData()
    }

    override fun onDisable() {
        unregisterCommands()
    }

    private fun reloadData() {
        MainConfig.reload()

        APICache.reload()
    }

    private fun setupSelenium() {
        if(MiraiSeleniumPlugin.setup(true)) {
            logger.info { "Selenium初始化完成" }
        } else {
            logger.warning { "Selenium初始化失败，准备下载依赖库。" }

            thread(start = true) {
                logger.info { "开始下载依赖库" }
                MiraiSeleniumPlugin.chromium("99")
                logger.info { "下载完成！" }
            }
        }
    }

    private fun registerCommands() {
        //TODO
        ManageCommand.register()
        TestCommand.register()
    }

    private fun unregisterCommands() {
        ManageCommand.unregister()
        TestCommand.unregister()
    }
}