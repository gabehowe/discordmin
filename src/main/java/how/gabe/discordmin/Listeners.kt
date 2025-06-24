package how.gabe.discordmin

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class Listeners(private val discAbstraction: DiscordAbstraction) : Listener {
    @EventHandler (priority= EventPriority.MONITOR)
    fun onPlayerChat(event: AsyncPlayerChatEvent){
        val message = "**${event.player.name}**: ${event.message}"
        discAbstraction.sendDiscordMessage(message)
    }
    @EventHandler(priority= EventPriority.MONITOR)
    fun onPlayerDeath(event: PlayerDeathEvent){
        val message = "**${event.deathMessage}** *@ ${event.entity.location.blockX} ${event.entity.location.blockY} ${event.entity.location.blockZ}.*"
        discAbstraction.sendDiscordMessage(message)
    }
    @EventHandler(priority= EventPriority.MONITOR)
    fun onPlayerJoin(event: PlayerJoinEvent){
        val message = "**${event.player.name}** joined."
        discAbstraction.sendDiscordMessage(message)
    }
    @EventHandler(priority= EventPriority.MONITOR)
    fun onPlayerLeave(event: PlayerQuitEvent){
        val message = "**${event.player.name}** left."
        discAbstraction.sendDiscordMessage(message)
    }
}