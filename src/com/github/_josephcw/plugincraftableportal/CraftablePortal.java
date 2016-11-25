package com.github._josephcw.plugincraftableportal;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftablePortal extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		addPortalRecipe();
		
	}
	
	public void addPortalRecipe() {
		
		ItemStack portalBlock = new ItemStack(Material.ENDER_PORTAL_FRAME, 3);
		ItemMeta meta = portalBlock.getItemMeta();
		meta = setPortalMeta(meta);
		portalBlock.setItemMeta(meta);
		
		ShapedRecipe bRecipe = new ShapedRecipe(portalBlock);
		bRecipe.shape("ded", 
						"oeo", 
						"bbb");
		bRecipe.setIngredient('d', Material.DIAMOND);
		bRecipe.setIngredient('o', Material.OBSIDIAN);
		bRecipe.setIngredient('b', Material.ENDER_STONE);
		bRecipe.setIngredient('e', Material.EYE_OF_ENDER);
		
		Bukkit.getServer().addRecipe(bRecipe);
	}
	
	public ItemMeta setPortalMeta(ItemMeta currentMeta) {
		currentMeta.setDisplayName(ChatColor.MAGIC + "End Portal Frame");
		currentMeta.setLore(Arrays.asList("This is extreemly precious.", "Remember to use wisely."));
		return currentMeta;
	}
	
	
	@EventHandler
	public void playerBreakingEndPortal(BlockDamageEvent e) {
		// A block is being damaged.
		
		if (e.getBlock().getType() == Material.ENDER_PORTAL_FRAME) {
			
			
			ItemStack portalBlock = new ItemStack(Material.ENDER_PORTAL_FRAME, 1);
			portalBlock.setItemMeta(setPortalMeta(portalBlock.getItemMeta()));
			e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), portalBlock);
			
//			for (MetadataValue mv : e.getBlock().getState().getMetadata("eye")) {
//				Bukkit.broadcastMessage(mv.asString());
//			}
			
			if ((e.getBlock().getData() & 0x4) != 0)
				e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.EYE_OF_ENDER, 1));
			
			
			e.getBlock().setType(Material.AIR);
			
		}
		
		// Sketchy way of removing the portal.
		
		if (e.getBlock().getType() == Material.ENDER_PORTAL) {
			if (e.getItemInHand().getType() == Material.COBBLESTONE) {
				e.getBlock().setType(Material.AIR);
			}
		}
	}
	
}
