package com.songoda.epichoppers.hooks;

import com.songoda.epichoppers.EpicHoppersPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Hook {

    final String pluginName;

    protected Hook(String pluginName) {
        this.pluginName = pluginName;
        if (isEnabled())
            EpicHoppersPlugin.getInstance().hooks.hooksFile.getConfig().addDefault("hooks." + pluginName, true);
    }

    protected boolean isEnabled() {
        return (Bukkit.getPluginManager().isPluginEnabled(pluginName)
                && EpicHoppersPlugin.getInstance().hooks.hooksFile.getConfig().getBoolean("hooks." + pluginName, true));
    }

    boolean hasBypass(Player p) {
        return p.hasPermission(EpicHoppersPlugin.getInstance().getDescription().getName() + ".bypass");
    }

    public abstract boolean canBuild(Player p, Location location);

    public boolean isInClaim(String id, Location location) {
        return false;
    }

    public String getClaimId(String name) {
        return null;
    }


}
