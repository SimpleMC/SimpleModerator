package com.evosysdev.bukkit.taylorjb.simplemod;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

public class SimpleModPlayerListener extends PlayerListener
{
    private SimpleMod plugin; // instance of our plugin
    
    /**
     * Initialize our listener
     * 
     * @param plugin
     *            instance of our plugin
     */
    public SimpleModPlayerListener(SimpleMod plugin)
    {
        this.plugin = plugin;
    }
    
    /**
     * Listen to player logins/login event
     * Check if user is banned
     * 
     * @param login
     *            login event
     */
    @Override
    public void onPlayerLogin(PlayerLoginEvent login)
    {
        if (this.plugin.getHandler().isBanned(login.getPlayer().getName()))
        {
            login.disallow(PlayerLoginEvent.Result.KICK_BANNED, "You are banned!");
            Logger.getLogger("minecraft").info("Player " + login.getPlayer().getName() + " denied entry: banned");
        }
    }
    
    /**
     * Listen to player join event
     * See if user should be IP banned
     * 
     * @param join
     *            join event
     */
    @Override
    public void onPlayerJoin(PlayerJoinEvent join)
    {
        if (this.plugin.getHandler().isBanned(join.getPlayer().getName(), join.getPlayer().getAddress().getAddress().getHostAddress()))
        {
            join.getPlayer().kickPlayer("You are banned!");
            Logger.getLogger("minecraft").info("Player " + join.getPlayer().getName() + " denied entry: banned");
        }
    }
    
    /**
     * Listen to chat/chat event
     * 
     * @param chat
     *            chat event
     */
    @Override
    public void onPlayerChat(PlayerChatEvent chat)
    {
        if (plugin.getHandler().isMuted(chat.getPlayer().getName()))
        {
            chat.getPlayer().sendMessage(ChatColor.RED + "You are muted!");
            chat.setCancelled(true);
        }
    }
}