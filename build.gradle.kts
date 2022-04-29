plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "+"
}

group = "com.codepwn"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

//fun skikoAwt(ver: String) = "org.jetbrains.skiko:skiko-awt-runtime-$ver"

dependencies {
    /*
    val skikoVer = "0.7.18"
    implementation(skikoAwt("windows-x64:$skikoVer"))
    implementation(skikoAwt("linux-x64:$skikoVer"))
    implementation(skikoAwt("linux-arm64:$skikoVer"))
    implementation("com.github.LaoLittle:SkikoMirai:1.0.5")
    */
    val seleniumVersion = "2.0.8"
    compileOnly("xyz.cssxsh.mirai:mirai-selenium-plugin:${seleniumVersion}")
    implementation("org.json:json:20220320")
}