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
        lateinit var discordLivechat: DiscordLivechat
    }

    lateinit var discordConfig: GsonDiscordLivechat
    lateinit var prefixScoreboard: PrefixScoreboard

    override fun onPluginLoad() {
        instance = this
        discordConfig = GsonConfigManager(this, "discordLivechat.json", GsonDiscordLivechat::class.java).read()
        discordLivechat = DiscordLivechat(instance)
    }

    override fun onPluginEnable() {
        prefixScoreboard = PrefixScoreboard()
        registerListener(
            AsyncChatEvent(), MinecraftSysChatListener(discordLivechat), ConnectionListener()
        )
    }

    override fun onPluginDisable() {
        discordLivechat.shutdown()
    }

}
