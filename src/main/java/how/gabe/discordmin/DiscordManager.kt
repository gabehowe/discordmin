package how.gabe.discordmin

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration


class DiscordManager(private val config: FileConfiguration, private val plugin: Discordmin) {
    private lateinit var kord: Kord
    private lateinit var channel: MessageChannel
    fun sendDiscordMessage(message: String) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            runBlocking {
                channel.createMessage(message)
            }
        })
    }

    fun quit()  {
        sendDiscordMessage("**Server stopped.**")
    }

    fun start() = runBlocking {
        kord = Kord(config.getString("auth-token")!!) // We shouldn't pretend this will always be available.
        val guild = kord.getGuild(Snowflake(config.get("guild")!! as Long))
        channel = guild.getChannel(Snowflake(plugin.config.get("discord-channel")!! as Long)) as MessageChannel
        sendDiscordMessage("**Server started.**")
        kord.on<MessageCreateEvent> {
            if (message.channelId != channel.id) return@on
            if (message.author?.isBot != false) return@on
            val minecraftMessage = "${message.getAuthorAsMember().effectiveName}: ${message.content}"
            Bukkit.getScheduler().runTask(plugin, Runnable {
                plugin.server.broadcastMessage(minecraftMessage)
            })
        }

        kord.login {
            @OptIn(PrivilegedIntent::class)
            intents += Intent.MessageContent
        }
    }
}