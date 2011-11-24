package com.evosysdev.bukkit.taylorjb.simplemod;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

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
     * 
     * @param login
     *            login event
     */
    @Override
    public void onPlayerLogin(PlayerLoginEvent login)
    {
        if (plugin.getHandler().isBanned(login.getPlayer().getName(), login.getPlayer().getAddress()))
        {
            login.disallow(Result.KICK_BANNED, "You are banned!");
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