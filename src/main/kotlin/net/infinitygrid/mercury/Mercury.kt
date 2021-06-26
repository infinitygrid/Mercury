package net.infinitygrid.mercury

import net.infinitygrid.mercury.chat.AsyncChatEvent
import net.infinitygrid.mercury.chat.ConnectionListener
import net.infinitygrid.mercury.config.GsonConfigManager
import net.infinitygrid.mercury.discord.DiscordLivechat
import net.infinitygrid.mercury.discord.MinecraftSysChatListener
import net.infinitygrid.mercury.pojo.GsonDiscordLivechat

class Mercury : MercuryPluginLoader() {

    companion object {
        lateinit var instance: Mercury
    }

    lateinit var discordConfig: GsonDiscordLivechat
    lateinit var prefixScoreboard: PrefixScoreboard
    var discordLivechat: DiscordLivechat? = null

    override fun onPluginLoad() {
        instance = this
        discordConfig = GsonConfigManager(this, "discordLivechat.json", GsonDiscordLivechat::class.java).read()
        if (discordConfig.enabled) DiscordLivechat(instance)
    }

    override fun onPluginEnable() {
        prefixScoreboard = PrefixScoreboard()
        discordLivechat?.let { MinecraftSysChatListener(it) }
        registerListener(AsyncChatEvent(), ConnectionListener())
    }

    override fun onPluginDisable() {
        discordLivechat?.shutdown()
    }

}
