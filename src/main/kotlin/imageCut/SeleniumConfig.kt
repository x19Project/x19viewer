package com.codepwn.x19viewer.imageCut

import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.value
import xyz.cssxsh.selenium.RemoteWebDriverConfig
import xyz.cssxsh.selenium.UserAgents

object SeleniumConfig: ReadOnlyPluginConfig("SeleniumConfig"), RemoteWebDriverConfig {
    // 宽
    override val width: Int by value(540)
    // 后台模式
    override val headless: Boolean by value(true)
    // 不要使用ktor，会报EOF（虽然不影响使用，但是红屏日志真的太难看辣）
    override val factory: String by value("netty")
}