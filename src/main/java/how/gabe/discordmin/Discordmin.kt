package how.gabe.discordmin

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Discordmin : JavaPlugin() {
    private lateinit var discabs: DiscordAbstraction
    override fun onEnable() {
        this.saveDefaultConfig()
        discabs = DiscordAbstraction(this.config, this)
        Bukkit.getScheduler().runTaskAsynchronously(this, discabs::start)
        val listener = Listeners(discabs)
        this.server.pluginManager.registerEvents(listener, this)
    }


    override fun onDisable() {
        discabs.quit()
        Bukkit.getScheduler().cancelTasks(this)
    }
}
