plugins {
    java
    kotlin("jvm") version "1.5.10"
}

group = "net.infinitygrid"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://m2.dv8tion.net/releases")
    maven("https://repo.pl3x.net/")
    jcenter()
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
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
