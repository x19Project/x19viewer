package com.codepwn.x19viewer.cache

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object APICache: AutoSavePluginData("APICache") {
    @ValueDescription("""
        API get session-id url
    """)
    var sessionIdUrl by value("")

    @ValueDescription ("""
        API encrypt token url
    """)
    var encryptTokenUrl by value("")

    @ValueDescription("""
        session-id cache
    """)
    var session_id by value("")
}