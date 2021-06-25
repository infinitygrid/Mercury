package net.infinitygrid.mercury.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ConnectionListener : Listener {

    @EventHandler
    private fun onJoin(e: PlayerJoinEvent) {
        val player = e.player
        val textComponent = Component.text("→ ")
            .append(player.displayName())
                .color(TextColor.color(0x21cf9a))
                .decorate(TextDecoration.BOLD)
            .append(
                Component.text(" joined").decoration(TextDecoration.BOLD, false)
            )
        e.joinMessage(textComponent)
    }

    @EventHandler
    private fun onQuit(e: PlayerQuitEvent) {
        val player = e.player
        val textComponent = Component.text("← ")
            .append(player.displayName())
                .color(TextColor.color(0xe0266b))
                .decorate(TextDecoration.BOLD)
            .append(
                Component.text(" left").decoration(TextDecoration.BOLD, false)
            )
        e.quitMessage(textComponent)
    }

}