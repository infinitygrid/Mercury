package net.infinitygrid.mercury.chat

import io.papermc.paper.event.player.AsyncChatEvent
import net.infinitygrid.mercury.Mercury
import net.infinitygrid.mercury.getPermissionGroup
import net.infinitygrid.mercury.getStringedDisplayName
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.markdown.DiscordFlavor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AsyncChatEvent : Listener {

    companion object {
        val miniMessage = MiniMessage.withMarkdownFlavor(DiscordFlavor.get())
        const val DEFAULT_COLOR = 0xF2F2F2
    }

    @EventHandler
    private fun on(event: AsyncChatEvent) {
        val player = event.player
        val permGroup = player.getPermissionGroup()
        val nameComponent = miniMessage
            .parse("<bold>${player.getStringedDisplayName()} ")
            .color(TextColor.color(permGroup.hexAsInteger))
        val rawMessage = event.message() as TextComponent
        val component = Component.text()
            .color(TextColor.color(DEFAULT_COLOR))
            .append(nameComponent)
            .append(Component.text("\n "))
            .append(miniMessage.parse((rawMessage).content()))
        Mercury.instance.discordLivechat?.sendMessage(player, rawMessage.content())
        event.renderer { _, _, _, _ ->
            component.build()
        }
    }

}
