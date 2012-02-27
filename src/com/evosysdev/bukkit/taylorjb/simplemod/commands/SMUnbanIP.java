package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

public class SMUnbanIP extends SMCommand
{
    protected static final String[] permissions = {"simplemod.unbanip"};
    
    /**
     * Initialize unban IP command
     */
    public SMUnbanIP(SimpleMod plugin, SimpleModHandler handler)
    {
        super(plugin, handler, 1, permissions);
    }

    @Override
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
}
