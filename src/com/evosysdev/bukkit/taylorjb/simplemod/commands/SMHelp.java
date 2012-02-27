package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;

public class SMHelp extends SMCommand
{
    private Map<Command, String[]> commands;
    
    /**
     * Initialize help(/simplemoderator) command
     */
    public SMHelp(SimpleMod plugin)
    {
        super(plugin, null, 0, "simplemod");
        
        commands = new HashMap<Command, String[]>();
        commands.put(plugin.getCommand("ban"), SMBan.permissions);
        commands.put(plugin.getCommand("tempban"), SMTempBan.permissions);
        commands.put(plugin.getCommand("unban"), SMUnban.permissions);
        commands.put(plugin.getCommand("banip"), SMBanIP.permissions);
        commands.put(plugin.getCommand("unbanip"), SMUnbanIP.permissions);
        commands.put(plugin.getCommand("mute"), SMMute.permissions);
        commands.put(plugin.getCommand("tempmute"), SMTempMute.permissions);
        commands.put(plugin.getCommand("unmute"), SMUnmute.permissions);
        commands.put(plugin.getCommand("kick"), SMKick.permissions);
    }
    
    @Override
    /**
     * Execute help command
     */
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        boolean canCommand = true; // can sender use command?
        
        // go through all commands
        for (Command c : commands.keySet())
        {
            // ensure sender has all perms required for command
            for (String perm : commands.get(c))
            {
                if (!sender.hasPermission(perm))
                    canCommand = false;
            }
            
            // sender can use command c
            if (canCommand)
                sender.sendMessage(ChatColor.AQUA + "/" + c.getName() + ChatColor.WHITE + " | " + ChatColor.BLUE + c.getDescription());
        }
        return true;
    }
}
