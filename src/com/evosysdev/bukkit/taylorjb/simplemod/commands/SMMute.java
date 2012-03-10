package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

public class SMMute extends SMCommand
{
    protected static final String[] permissions = {"simplemod.mute"};
    
    /**
     * Initialize mute command
     */
    protected SMMute(SimpleMod plugin, SimpleModHandler handler, int argsReq, String... perms)
    {
        super(plugin, handler, argsReq, perms);
    }
    
    /**
     * Initialize mute command with default mute behavior
     */
    public SMMute(SimpleMod plugin, SimpleModHandler handler)
    {
        this(plugin, handler, 1, permissions);
    }
    
    @Override
    /**
     * Execute mute command
     */
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        mute(args[0], 0, sender, stringArrayToString(args, 1));
        return true;
    }
    
    /**
     * Mute player
     * 
     * @param muting
     *            name of player we are muting
     * @param time
     *            time period in hours of mute
     * @param sender
     *            sender of the mute command
     * @param reason
     *            reason for the mute
     */
    protected void mute(String muting, int time, CommandSender sender, String reason)
    {
        handler.mute(muting, time); // mute
        
        StringBuilder message = new StringBuilder(" muted");
        
        // append time info if there is a time limit on it
        if (time > 0)
            message.append(" for ").append(time).append(" hours");
        
        // append reason if it exists
        if (reason.length() > 0)
            message.append(" for: ").append(reason);
        
        message.append('!');
        
        // sender feedback
        sender.sendMessage(ChatColor.GREEN + "Player '" + muting + "'" + message.toString());

        // broadcast message if we need to
        if (handler.broadcastMute())
            plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player '" + muting + "'" + message.toString());
        
        // if player is online, tell him he was muted
        Player p = plugin.getServer().getPlayer(muting);
        if (p != null)
            p.sendMessage(ChatColor.RED + "You have been" + message.toString());
        
        // log mute
        plugin.getLogger().info(sender.getName() + " muted player " + muting + ", reason: " + reason);
    }
}
