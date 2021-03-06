package net.infinitygrid.mercury

import net.infinitygrid.mercury.pojo.GsonPermissionGroup
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.Player

internal fun Player.getPermissionGroup(): GsonPermissionGroup {
    Mercury.instance.permissionGroupConfig.groups.forEach {
        if (it.permissionNode != null && this.hasPermission(it.permissionNode)) return it
    }
    return GsonPermissionGroup()
}

internal fun Player.getStringedDisplayName(): String {
    return (this.displayName() as TextComponent).content()
}