package ch.canardconfit.skymanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ch.canardconfit.skymanager.Commands.MainCommand;
import ch.canardconfit.skymanager.Listeners.MainListener;

public class SkyManager extends JavaPlugin {
	
	public boolean enabled_file_view;
	File messageFile = new File(getDataFolder(), "messages.yml");
	File configFile = new File(getDataFolder(), "config.yml");
	
	
	public ArrayList<ChatGUI> listChatGUI = new ArrayList<ChatGUI>();
	
	public static SkyManager main;
	
	public YamlConfiguration message = new YamlConfiguration();
	public YamlConfiguration config = new YamlConfiguration();
	
	@Override
	public void onEnable() {
		
		main = this;
		
		
		PluginManager pm = getServer().getPluginManager();
		
		loadFile();

		enabled_file_view = config.getBoolean("enable-file-view");
		
		pm.registerEvents(new MainListener(this), this);
		
		System.out.println(message.getString("plugin.start"));
		
		getCommand("skymanager").setExecutor(new MainCommand());
	}
	
	@Override
	public void onDisable() {
		
		main = null;
		
		System.out.println(message.getString("plugin.stop"));
		
	}
	
	public void loadFile() {
        try {
		
			if (!(getDataFolder().exists())) {
				getDataFolder().mkdir();
			}
			
			if (!(messageFile.exists())) {
				InputStream stream = getResource("resource/FRANCAIS/messages.yml");
				Files.copy(stream, messageFile.toPath());
			}
			
			if (!(configFile.exists())) {
				InputStream stream = getResource("resource/FRANCAIS/config.yml");
				Files.copy(stream, configFile.toPath());
			}
			config.load(configFile);
			message.load(messageFile);
			
			if (config.getString("lang").equals("ENG")) {
            	if (config.getBoolean("changeonstart") == true) {
	            	configFile.delete();
		        	File file = new File(getDataFolder(), "config.yml");
					Files.copy(getResource("resource/ANGLAIS/config.yml"), file.toPath());
					
					messageFile.delete();
		        	File file2 = new File(getDataFolder(), "messages.yml");
					Files.copy(getResource("resource/ANGLAIS/messages.yml"), file2.toPath());
	            	System.out.println("SkyManager >> You have selectionned ENGLISH version ! The old config was deleted !");
            	}
            }
            if (config.getString("lang").equals("FR")) {
            	if (config.getBoolean("changeonstart") == true) {
	            	configFile.delete();
		        	File file = new File(getDataFolder(), "config.yml");
					Files.copy(getResource("resource/FRANCAIS/config.yml"), file.toPath());
					
					messageFile.delete();
		        	File file2 = new File(getDataFolder(), "messages.yml");
					Files.copy(getResource("resource/FRANCAIS/messages.yml"), file2.toPath());
	            	System.out.println("SkyManager >> Vous avez selectionné la version francaise ! l'ancienne configuration a ete supprimer !");
           
            	}
            }
			config.load(configFile);
			message.load(messageFile);
        } catch (IOException e) {getLogger().warning("SkyManager >> Error on create file configuration !"); e.printStackTrace();
        } catch (InvalidConfigurationException e) {getLogger().warning("SkyManager >> Error on load file configuration !"); e.printStackTrace();}
	}
	
	
	public ChatGUI getPlayer(Player player) {
		for (ChatGUI pl : listChatGUI) {
			if (pl.getPlayer() == player) {
				return pl;
			}
		}
		return null;
	}
	public void addChatGUI(Player player, File file) {
		ChatGUI chatgui = new ChatGUI(player, file);
		chatgui.setPage(0);
		listChatGUI.add(chatgui);
	}
	public void removeChatGUI(Player player) {
		ChatGUI chat = getPlayer(player);
		listChatGUI.remove(chat);
	}

	public final String changePara(String line) {
		line = line.replaceAll("&", "§");
		return line;
	}
	
	public final String changecfgline(String line, String patern, Object change) {
		if (line.contains(patern)) {
			if (!(change instanceof Integer)) {
				line = line.replace(patern, (CharSequence) change);
			} else {
				line = line.replace(patern, (String) change.toString());
			}
			return line;
		}
		return null;
		
	}
	
	public void setItem(Player player, Material material, String displayname, List<String> lore, int Slot) {
		
		ItemStack customitem = new ItemStack(material);
		ItemMeta customM = customitem.getItemMeta();
		customM.setDisplayName(displayname);
		customM.setLore(lore);
		customitem.setItemMeta(customM);

		player.getInventory().setItem(Slot, customitem);
	}
	
	public ItemStack addItem(Material material, int number, byte damage, String displayname, List<String> lore) {
		ItemStack itm = new ItemStack(material, number, damage);
		ItemMeta itmM = itm.getItemMeta();
		itmM.setDisplayName(displayname);
		itmM.setLore(lore);
		itm.setItemMeta(itmM);
		return itm;
	}
}
