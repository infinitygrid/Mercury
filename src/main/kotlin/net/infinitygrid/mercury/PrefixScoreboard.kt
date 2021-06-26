package net.infinitygrid.mercury

import net.infinitygrid.mercury.groups.PermissionGroup
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PrefixScoreboard {

    private val scoreboard = Bukkit.getScoreboardManager().mainScoreboard

    init {
        val teamSU = scoreboard.getTeam("a-superadmin") ?: scoreboard.registerNewTeam("a-superadmin")
        val teamAdmin = scoreboard.getTeam("b-admin") ?: scoreboard.registerNewTeam("b-admin")
        val teamDev = scoreboard.getTeam("c-developer") ?: scoreboard.registerNewTeam("c-developer")
        val teamMod = scoreboard.getTeam("d-moderator") ?: scoreboard.registerNewTeam("d-moderator")
        val teamDefault = scoreboard.getTeam("e-default") ?: scoreboard.registerNewTeam("e-default")
        teamSU.prefix(Component.text("Admin ").decorate(TextDecoration.BOLD).color(TextColor.color(0xe35780)))
        teamAdmin.prefix(Component.text("Admin ").decorate(TextDecoration.BOLD).color(TextColor.color(0xe35780)))
        teamDev.prefix(Component.text("Dev ").decorate(TextDecoration.BOLD).color(TextColor.color(0x47d48d)))
        teamMod.prefix(Component.text("Mod ").decorate(TextDecoration.BOLD).color(TextColor.color(0xf4c990)))
        teamSU.color(NamedTextColor.GRAY)
        teamAdmin.color(NamedTextColor.GRAY)
        teamDev.color(NamedTextColor.GRAY)
        teamMod.color(NamedTextColor.GRAY)
        teamDefault.color(NamedTextColor.GRAY)
    }

    fun addPlayer(player: Player) {
        when (PermissionGroup.getByPlayer(player)) {
            PermissionGroup.SUPER_ADMIN -> scoreboard.getTeam("a-superadmin")!!.addEntry(player.displayName)
            PermissionGroup.ADMIN -> scoreboard.getTeam("b-admin")!!.addEntry(player.displayName)
            PermissionGroup.DEVELOPER -> scoreboard.getTeam("c-developer")!!.addEntry(player.displayName)
            PermissionGroup.MODERATOR -> scoreboard.getTeam("d-moderator")!!.addEntry(player.displayName)
            PermissionGroup.DEFAULT -> scoreboard.getTeam("e-default")!!.addEntry(player.displayName)
        }
        player.scoreboard = scoreboard
    }

}