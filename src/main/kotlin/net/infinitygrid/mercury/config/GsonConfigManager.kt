package net.infinitygrid.mercury.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

public class GsonConfigManager<T>(plugin: JavaPlugin, fileName: String, private var classOfT: Class<T>) {

    private companion object {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
    }

    private val configFile = File("./${plugin.dataFolder}/$fileName")

    public fun read(): T {
        if (!configFile.isFile) {
            configFile.parentFile.parentFile.mkdirs()
            configFile.createNewFile()
        }
        val fileContent = configFile.readText()
        return gson.fromJson(fileContent, classOfT)
    }

    public fun write(clazz: T) {
        val newFileContent = gson.toJson(clazz)
        configFile.writeText(newFileContent)
    }

}