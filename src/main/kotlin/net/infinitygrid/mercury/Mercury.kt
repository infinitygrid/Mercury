package net.infinitygrid.mercury

import net.infinitygrid.mercury.chat.AsyncChatEvent
import net.infinitygrid.mercury.chat.ConnectionListener
import net.infinitygrid.mercury.config.GsonConfigManager
import net.infinitygrid.mercury.discord.DiscordLivechat
import net.infinitygrid.mercury.discord.MinecraftSysChatListener
import net.infinitygrid.mercury.pojo.GsonDiscordLivechat
import net.infinitygrid.mercury.pojo.GsonPermissionGroupCollection

internal class Mercury : MercuryPluginLoader() {

    companion object {
        lateinit var instance: Mercury
    }

    lateinit var discordConfig: GsonDiscordLivechat
    lateinit var permissionGroupConfig: GsonPermissionGroupCollection
    lateinit var prefixScoreboard: PrefixScoreboard
    var discordLivechat: DiscordLivechat? = null

    override fun onPluginLoad() {
        instance = this
        discordConfig = GsonConfigManager(this, "discordLivechat.json", GsonDiscordLivechat::class.java).read()
        permissionGroupConfig = GsonConfigManager(this, "permissionGroups.json", GsonPermissionGroupCollection::class.java).read()
        if (discordConfig.enabled) DiscordLivechat(instance)
    }

    override fun onPluginEnable() {
        prefixScoreboard = PrefixScoreboard(permissionGroupConfig)
        discordLivechat?.let { MinecraftSysChatListener(it) }
        registerListener(AsyncChatEvent(), ConnectionListener())
    }

    override fun onPluginDisable() {
        discordLivechat?.shutdown()
    }

}
