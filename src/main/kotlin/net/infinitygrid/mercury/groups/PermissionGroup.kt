package net.infinitygrid.mercury.groups

import org.bukkit.entity.Player

enum class PermissionGroup(val node: String?, val color: Int) {

    SUPER_ADMIN("rank.superadmin", 0xe35780),
    ADMIN("rank.admin", 0xe35780),
    DEVELOPER("rank.developer", 0x47d48d),
    MODERATOR("rank.moderator", 0xf4c990),
    DEFAULT(null, 0x2c6f98);

    companion object {

        fun getByPlayer(player: Player): PermissionGroup {
            values().forEach { permissionGroup ->
                permissionGroup.node?.let { node ->
                    if (player.hasPermission(node)) {
                        return permissionGroup
                    }
                }
            }
            return DEFAULT
        }

    }

}