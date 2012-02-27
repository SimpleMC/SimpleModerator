package com.evosysdev.bukkit.taylorjb.simplemod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Handles all of the lists required for SimpleMod's moderator actions
 * 
 * @author taylorjb
 * 
 */
public class SimpleModHandler
{
    private Map<String, Long> bans, // currently banned players
            mutes; // currently muted players
    private List<String> ipBans; // currently banned IP addresses
    private SimpleMod instance; // instance of SimpleMod
    private boolean persistMutes; // should mutes persist through logout
    
    /**
     * Initialize the handler
     * 
     * @param instance
     *            instance of plugin
     */
    public SimpleModHandler(SimpleMod instance)
    {
        this.instance = instance;
        load();
    }
    
    /**
     * Perm ban player
     * 
     * @param player
     *            player to be banned
     */
    public void ban(String player)
    {
        ban(player, -1);
    }
    
    /**
     * Ban player for hours
     * 
     * @param player
     *            player to be banned
     * @param hours
     *            hours to ban player for
     */
    public void ban(String player, int hours)
    {
        if (hours > 0)
            bans.put(player.toLowerCase(), System.currentTimeMillis() + (hours * 1000 * 60 * 60));
        else
            bans.put(player.toLowerCase(), new Long(-1));
        
        save("bans", bansString());
    }
    
    /**
     * Unban player
     * 
     * @param player
     *            player to unban
     * @return whether player was unbanned or not
     */
    public boolean unBan(String player)
    {
        player = player.toLowerCase();
        
        // no ban to remove
        if (!isBanned(player))
            return false;
        
        // remove and success
        bans.remove(player);
        save("bans", bansString());
        return true;
    }
    
    /**
     * @return string representation of bans in format for file
     */
    public String bansString()
    {
        String banString = "";
        for (String player : this.bans.keySet())
        {
            banString = banString + player + ':' + this.bans.get(player) + ',';
        }
        
        return banString;
    }
    
    /**
     * Ban host
     * 
     * @param host
     *            host to be banned
     */
    public void banIP(String host)
    {
        ipBans.add(host);
        save("ipbans", ipbansString());
    }
    
    /**
     * Unban host/IP
     * 
     * @param host
     *            host to be unbanned
     */
    public boolean unBanIP(String host)
    {
        boolean success = ipBans.remove(host);
        
        // no need to save file if we didn't remove a host
        if (success)
            save("ipbans", ipbansString());
        
        return success;
    }
    
    /**
     * @return String representation of IP bans
     */
    public String ipbansString()
    {
        String ipbanString = "";
        for (String player : this.ipBans)
        {
            ipbanString = ipbanString + player + ',';
        }
        
        return ipbanString;
    }
    
    /**
     * Check if a player is banned
     * 
     * @param player
     *            player to check ban status of
     * @return if player is banned or not
     */
    public boolean isBanned(String player)
    {
        player = player.toLowerCase();
        
        // if player is in ban list, ensure we shouldn't be unbanning them
        if (this.bans.containsKey(player))
        {
            // ban expired
            if ((((Long) this.bans.get(player)).longValue() != -1L) && (((Long) this.bans.get(player)).longValue() < System.currentTimeMillis()))
            {
                this.bans.remove(player);
                save("bans", bansString());
            }
            else
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Check if a player is banned
     * 
     * @param player
     *            player to check ban status of
     * @param hostname
     *            host to check
     * @return if player is banned or not
     */
    public boolean isBanned(String player, String hostString)
    {
        player = player.toLowerCase();
        
        if ((bans.containsKey(player)) || (this.ipBans.contains(hostString)))
        {
            if ((bans.get(player) != null) && (((Long) bans.get(player)).longValue() != -1L) && (bans.containsKey(player))
                    && (((Long) bans.get(player)).longValue() < System.currentTimeMillis()))
            {
                bans.remove(player);
                save("bans", bansString());
            }
            else
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Perm mute player
     * 
     * @param player
     *            to be muted
     */
    public void mute(String player)
    {
        mute(player, -1);
    }
    
    /**
     * Mute player for time
     * 
     * @param player
     *            player to be muted
     * @param hours
     *            hours to mute player for
     */
    public void mute(String player, int hours)
    {
        if (hours > 0)
            mutes.put(player.toLowerCase(), System.currentTimeMillis() + (hours * 1000 * 60 * 60));
        else
            mutes.put(player.toLowerCase(), new Long(-1));
        
        if (this.persistMutes)
            save("mutes", mutesString());
    }
    
    /**
     * Unmute player
     * 
     * @param player
     *            player to unmute
     * @return whether player was unmuted or not
     */
    public boolean unMute(String player)
    {
        player = player.toLowerCase();
        
        // no mute to remove
        if (!isMuted(player))
            return false;
        
        // remove and success
        mutes.remove(player);
        save("mutes", mutesString());
        return true;
    }
    
    /**
     * @return String representation of mutes
     */
    public String mutesString()
    {
        String muteString = "";
        for (String player : this.mutes.keySet())
        {
            muteString = muteString + player + ':' + this.mutes.get(player) + ',';
        }
        
        return muteString;
    }
    
    /**
     * Check if a player is muted
     * 
     * @param player
     *            player to check mute status of
     * @return if player is muted or not
     */
    public boolean isMuted(String player)
    {
        player = player.toLowerCase();
        
        if (this.mutes.containsKey(player))
        {
            if ((((Long) this.mutes.get(player)).longValue() != -1L) && (((Long) this.mutes.get(player)).longValue() < System.currentTimeMillis()))
            {
                this.mutes.remove(player);
                save("mutes", mutesString());
            }
            else
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Load data from the plugin's config file
     * 
     * If it doesn't exist, generate defaults
     */
    public void load()
    {
        bans = new HashMap<String, Long>();
        ipBans = new LinkedList<String>();
        mutes = new HashMap<String, Long>();
        persistMutes = false;
        
        try
        {
            // load bans
            String banString = instance.getConfig().getString("bans");
            if (banString.length() > 0)
                decodeList(bans, banString.split(","));
            
            // load IP bans
            String ipBanString = instance.getConfig().getString("ipbans");
            if (ipBanString.length() > 0)
                ipBans.addAll(Arrays.asList(ipBanString.split(",")));
            
            // load setting to persist mutes through sessions or not
            persistMutes = instance.getConfig().getBoolean("persist-mutes", false);
            
            // if we are persisting mutes, load pre-existing ones
            if (persistMutes)
            {
                String muteString = instance.getConfig().getString("mutes");
                if (muteString.length() > 0)
                    decodeList(this.mutes, muteString.split(","));
            }
        }
        catch (NullPointerException npe)
        { // config file couldn't be read
            instance.getConfig().set("bans", "");
            instance.getConfig().set("mutes", "");
            instance.getConfig().set("persist-mutes", "false");
            instance.getConfig().set("ipbans", "");
            instance.saveConfig();
        }
    }
    
    /**
     * Decode list of string:long format into a map of the same
     * Is there an easier way to do this?
     * 
     * @param map
     * @param list
     */
    public void decodeList(Map<String, Long> map, String[] list)
    {
        if (list.length > 0)
        {
            for (String s : list)
            {
                String[] split = s.split(":");
                map.put(split[0], Long.valueOf(split[1]));
            }
        }
    }
    
    /**
     * Save data to config file
     * 
     * @param node
     *            node to change/set/save
     * @param data
     *            data we are writing to the node
     */
    private void save(String node, String data)
    {
        instance.getConfig().set(node, data);
        instance.saveConfig();
    }
}
