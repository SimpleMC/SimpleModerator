package com.evosysdev.bukkit.taylorjb.simplemod;

import org.bukkit.plugin.java.JavaPlugin;

import com.evosysdev.bukkit.taylorjb.simplemod.commands.*;
/**
 * SimpleMod plugin container
 * 
 * @author taylorjb
 *
 */
public class SimpleMod extends JavaPlugin
{
    private SimpleModHandler smHandler; // our moderation action handler
    
    /**
     * Set up Permissions and set up listeners
     */
    public void onEnable()
    {
        smHandler = new SimpleModHandler(this);
        
        // set command executors
        getCommand("simplemoderator").setExecutor(new SMHelp(this));
        getCommand("ban").setExecutor(new SMBan(this, smHandler));
        getCommand("tempban").setExecutor(new SMTempBan(this, smHandler));
        getCommand("banip").setExecutor(new SMBanIP(this, smHandler));
        getCommand("unban").setExecutor(new SMUnban(this, smHandler));
        getCommand("mute").setExecutor(new SMMute(this, smHandler));
        getCommand("tempmute").setExecutor(new SMTempMute(this, smHandler));
        getCommand("kick").setExecutor(new SMKick(this));
        getCommand("unmute").setExecutor(new SMUnmute(this, smHandler));
        
        // create listener
        new SimpleModListener(this);
        
        getLogger().info(getDescription().getName() + " version " + getDescription().getVersion() + " enabled!");
    }
    
    /**
     * plugin disabled
     */
    public void onDisable()
    {
        getLogger().info("SimpleModerator disabled.");
    }
    
    /**
     * Get our moderation handler
     * 
     * @return our moderation handler
     */
    public SimpleModHandler getHandler()
    {
        return smHandler;
    }
}
