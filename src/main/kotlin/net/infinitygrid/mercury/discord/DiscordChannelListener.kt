package net.infinitygrid.mercury.discord

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

internal class DiscordChannelListener(private val discordLivechat: DiscordLivechatComponent,
                                      private val channelId: Long) : ListenerAdapter() {

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.channel.idLong != channelId) return
        if (event.isWebhookMessage || event.author.isBot) return
        discordLivechat.receiveMessage(event.message)
    }

}