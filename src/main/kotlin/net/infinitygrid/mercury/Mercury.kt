package net.infinitygrid.mercury

import com.google.gson.Gson
import net.infinitygrid.mercury.chat.AsyncChatEvent
import net.infinitygrid.mercury.crafting.InvisibleItemFrame
import net.infinitygrid.mercury.discord.DiscordLivechat
import net.infinitygrid.mercury.discord.MinecraftSysChatListener
import net.infinitygrid.mercury.endportal.EndPortalRestriction
import net.infinitygrid.mercury.pojo.MercuryConfig
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Mercury : JavaPlugin() {

    companion object {
        lateinit var instance: Mercury
        lateinit var discordLivechat: DiscordLivechat
    }

    private val pluginManager = Bukkit.getPluginManager()
    lateinit var config: MercuryConfig

    override fun onLoad() {
        config = Gson().fromJson(File("${dataFolder.absolutePath}/config.json").readText(), MercuryConfig::class.java)
        instance = this
        discordLivechat = DiscordLivechat(instance)
    }

    override fun onEnable() {
        registerListener(AsyncChatEvent(), EndPortalRestriction(), MinecraftSysChatListener(discordLivechat))
        InvisibleItemFrame()
    }

    override fun onDisable() {
        discordLivechat.shutdown()
    }

    private fun registerListener(vararg listeners: Listener) {
        listeners.forEach {
            pluginManager.registerEvents(it, this)
        }
    }

}
