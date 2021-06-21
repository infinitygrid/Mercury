package net.infinitygrid.mercury.discord

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.send.AllowedMentions
import club.minnced.discord.webhook.send.WebhookMessageBuilder
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.requests.GatewayIntent
import net.infinitygrid.mercury.chat.AsyncChatEvent
import net.infinitygrid.mercury.Mercury
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TranslatableComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class DiscordLivechat(private val mercury: Mercury) {

    private val webhook = WebhookClient.withUrl(mercury.config.webhookUrl)
    private var channelListener = DiscordChannelListener(this, mercury.config.channelId)
    private var jda = connect()

    private fun connect(): JDA {
        return JDABuilder.createLight(mercury.config.botToken,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_TYPING,
            GatewayIntent.GUILD_MESSAGE_REACTIONS
        ).addEventListeners(channelListener).build().awaitReady()
    }

    fun sendMessage(player: Player, message: String) {
        webhook.send(WebhookMessageBuilder()
            .setUsername((player.displayName() as TextComponent).content())
            .setAvatarUrl(getAvatarURL(player))
            .setAllowedMentions(AllowedMentions.none())
            .setContent(message)
            .build())
    }

    private fun getAvatarURL(player: Player): String {
        return "https://crafatar.com/renders/head/${player.uniqueId}?overlay&scale=5"
    }

    private fun getBodyURL(player: Player): String {
        return "https://crafatar.com/renders/body/${player.uniqueId}?overlay&scale=5"
    }

    fun sendDeathMessage(event: PlayerDeathEvent) {
        val player = event.entity
        val channel = jda.getTextChannelById(mercury.config.channelId)!!
        val embed = EmbedBuilder()
            .setColor(0xFF0000)
            .setAuthor(Bukkit.getUnsafe().legacyComponentSerializer().serialize(event.deathMessage()!!), null, getAvatarURL(player))
        channel.sendMessage(embed.build()).queue()
    }

    fun sendAchievementMessage(event: PlayerAdvancementDoneEvent) {
        println((event.message() as TranslatableComponent).args()[0])
        val player = event.player
        val channel = jda.getTextChannelById(mercury.config.channelId)!!
        val rawMessage = Bukkit.getUnsafe().legacyComponentSerializer().serialize(event.message()!!)
        val achName = rawMessage.substring(rawMessage.indexOf('[') + 1, rawMessage.length - 1)
        val challenge = rawMessage.contains("challenge")
        val embed = if (!challenge) {
            EmbedBuilder()
                .setColor(0xFFFF00)
                .setAuthor("${player.displayName} has made the advancement $achName!", null, getAvatarURL(player))
        } else {
            EmbedBuilder()
                .setColor(0xFFFF00)
                .setAuthor("${player.displayName} has completed the challenge $achName!", null, getAvatarURL(player))
        }
        channel.sendMessage(embed.build()).queue {
            it.addReaction("U+1F389").queue()
        }
    }

    fun receiveMessage(message: Message) {
        val member = message.member!!
        val displayName = member.nickname ?: member.effectiveName
        val effectiveName = member.effectiveName
        val roleColor = member.color!!
        Bukkit.broadcast(Component.text()
            .color(TextColor.color(AsyncChatEvent.DEFAULT_COLOR))
            .append(Component.text("$displayName ").decorate(TextDecoration.BOLD).color(TextColor.color(roleColor.rgb)))
            .append(Component.text("⇄ Discord").color(TextColor.color(0x454545)))
            .append(Component.text("\n "))
            .append(AsyncChatEvent.miniMessage.parse(message.contentDisplay)).build())
    }

    fun shutdown() {
        jda.removeEventListener(channelListener)
        jda.shutdownNow()
        webhook.close()
    }

}