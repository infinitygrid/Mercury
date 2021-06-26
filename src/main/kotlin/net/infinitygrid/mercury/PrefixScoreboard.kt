package net.infinitygrid.mercury

import net.infinitygrid.mercury.pojo.GsonPermissionGroupCollection
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PrefixScoreboard(private val permissionGroupCollection: GsonPermissionGroupCollection) {

    private val scoreboard = Bukkit.getScoreboardManager().mainScoreboard

    init {
        for (i in 0 until permissionGroupCollection.groups.size) {
            val permissionGroup = permissionGroupCollection.groups[i]
            val teamName = "$i${permissionGroup.scoreboardTeamName}"
            val team = scoreboard.getTeam(teamName) ?: scoreboard.registerNewTeam(teamName)
            val color: Int = Integer.parseInt(permissionGroup.hexColor, 16)
            if (permissionGroup.shortName != null) team.prefix(
                Component.text("${permissionGroup.shortName} ")
                    .decorate(TextDecoration.BOLD)
                    .color(TextColor.color(color))
            )
            team.color(NamedTextColor.GRAY)
        }
    }

    fun addPlayer(player: Player) {
        for (i in permissionGroupCollection.groups.size - 1 downTo 0) {
            val permissionGroup = permissionGroupCollection.groups[i]
            if (permissionGroup.permissionNode != null) {
                println(permissionGroup.permissionNode)
                if (player.hasPermission(permissionGroup.permissionNode)) {
                    scoreboard.getTeam("$i${permissionGroup.scoreboardTeamName}")!!.addEntry(player.getStringedDisplayName())
                }
            } else {
                scoreboard.getTeam("$i${permissionGroup.scoreboardTeamName}")!!.addEntry(player.getStringedDisplayName())
            }
        }
        player.scoreboard = scoreboard
    }

}