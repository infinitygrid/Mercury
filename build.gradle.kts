
plugins {
    java
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "net.infinitygrid"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://m2.dv8tion.net/releases")
    maven("https://repo.pl3x.net/")
    maven("https://libraries.minecraft.net")
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    compileOnly("net.pl3x.purpur", "purpur-api", "1.17-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    implementation("net.dv8tion:JDA:4.2.1_276")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("club.minnced:discord-webhooks:0.5.7")
    compileOnly("com.mojang:brigadier:1.0.17")
    implementation("me.lucko:commodore:1.10")
}

tasks.shadowJar {
    dependencies {
        exclude(dependency("com.mojang:brigadier"))
    }
    relocate("me.lucko.commodore", "net.infinitygrid.mercury.commodore")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
