package net.infinitygrid.mercury.crafting

import net.infinitygrid.mercury.Mercury
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPlaceEvent
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType

class InvisibleItemFrame {

    companion object {
        private val resultingRecipe: ItemStack = ItemStack(Material.ITEM_FRAME, 8)
    }


    init {

        // MESSY CODE BUT IT WORKS

        val newMeta = resultingRecipe.itemMeta
        val starComponent = Component.text("★").color(TextColor.color(0xf0f55d))
        val mainNameComponent = MiniMessage.get().parse(" <bold><gradient:#71d6f0:#30aac9>Invisible Item Frame ")
        val displayName = Component.empty().decoration(TextDecoration.ITALIC, false)
            .append(starComponent).append(mainNameComponent).append(starComponent)
        newMeta.displayName(displayName)
        resultingRecipe.itemMeta = newMeta
        val recipe = ShapedRecipe(
            NamespacedKey.fromString("invisible_item_frame", Mercury.instance)!!, resultingRecipe
        )
        val invisibilityPotion = ItemStack(Material.POTION)
        val potionMeta = invisibilityPotion.itemMeta as PotionMeta
        potionMeta.basePotionData = PotionData(PotionType.INVISIBILITY)
        invisibilityPotion.itemMeta = potionMeta
        recipe.shape(
            "ooo",
            "oio",
            "ooo"
        )
        recipe.setIngredient('o', Material.ITEM_FRAME)
        recipe.setIngredient('i', invisibilityPotion)
        Bukkit.addRecipe(recipe)
        Bukkit.getPluginManager().registerEvents(InvisibleItemFramePlacementListener(), Mercury.instance)
    }

    class InvisibleItemFramePlacementListener : Listener {

        @EventHandler
        private fun on(e: HangingPlaceEvent) {
            val player = e.player!!
            val item = player.inventory.itemInMainHand
            if (item.type != Material.ITEM_FRAME) return
            if (item.isSimilar(resultingRecipe)) {
                val itemFrame = e.entity as ItemFrame
                itemFrame.isVisible = false
                itemFrame.world.spawnParticle(Particle.CRIT_MAGIC, itemFrame.location, 10, .2, .2, .2, .1)
                itemFrame.world.spawnParticle(Particle.END_ROD, itemFrame.location, 10, .2, .2, .2, .001)
            }
        }

    }

}