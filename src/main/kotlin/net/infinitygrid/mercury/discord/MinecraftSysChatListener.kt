package net.infinitygrid.mercury.discord

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class MinecraftSysChatListener(private val discordLivechat: DiscordLivechat) : Listener {

    @EventHandler
    private fun on(e: PlayerDeathEvent) {
        discordLivechat.sendDeathMessage(e)
    }

    @EventHandler
    private fun on(e: PlayerAdvancementDoneEvent) {
        discordLivechat.sendAchievementMessage(e)
    }

}