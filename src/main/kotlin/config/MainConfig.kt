package com.codepwn.x19viewer.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object MainConfig: AutoSavePluginConfig("MainConfig") {
    @ValueDescription("""
        前排提示：不要修改Config，要修改请使用命令修改
        ===== 分割线 =====
        API配置的地址（需要带http/https）
        例如：https://example.com/api.json
        其返回的应该是一个JSON，分别记录sessionId和encryptToken两个API的url
    """)
    var APIConfigUrl by value("NOT_SET_API_URL")

    @ValueDescription("""
        你的访问凭据（Token）
    """)
    var Token by value("NOT_SET_TOKEN")

    @ValueDescription("""
        默认访问的服务器地址（网易）
        一般情况下无需修改
        不要在末尾加斜杠
    """)
    var defaultServerUrl by value("https://g79mclobt.nie.netease.com")
}