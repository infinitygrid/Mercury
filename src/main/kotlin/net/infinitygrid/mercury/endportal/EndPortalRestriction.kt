package net.infinitygrid.mercury.endportal

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class EndPortalRestriction : Listener {

    @EventHandler
    private fun on(e: PlayerInteractEvent) {
        val player = e.player
        val item = player.inventory.itemInMainHand
        val block = e.clickedBlock
        if (item.type != Material.ENDER_EYE) return
        if (block!!.type == Material.END_PORTAL_FRAME) {
            e.isCancelled = true
            block.world.spawnParticle(Particle.SMOKE_LARGE, block.location.add(.5, .5, .5), 50, .25, .1, .25, 0.01)
            block.world.playSound(block.location, Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2F, 1F)
            block.world.playSound(block.location, Sound.ENTITY_GENERIC_EXPLODE, 2F, .5F)
            block.world.playSound(block.location, Sound.ENTITY_ENDER_EYE_DEATH, 2F, .5F)
            player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 1))
            val blockLocVec = block.location.toVector()
            player.velocity = player.location.toVector().subtract(blockLocVec)
            player.damage((player.health / 3.0) * 2.0)
        }
    }

}
