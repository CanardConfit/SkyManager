package ch.canardconfit.skymanager.Commands;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ch.canardconfit.skymanager.SkyManager;


public class MainCommand implements CommandExecutor {
	
	SkyManager sm = SkyManager.main;

	String initialname = sm.changePara(sm.message.getString("inventory.initial-name"));
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("skymanager"))
			
			if (sender instanceof Player) {
				
				Player player = (Player) sender;
				
				if (args.length >= 1 && args[0].equalsIgnoreCase("reload") && player.hasPermission("skymanager.reload")) {
					player.sendMessage(sm.changePara(sm.message.getString("plugin.reload-progress")));
					SkyManager.main.loadFile();
					player.sendMessage(sm.changePara(sm.message.getString("plugin.reload-success")));
					return true;
				}
				
				if (player.hasPermission("skymanager.filemanager")) {
					
					File directory = new File(SkyManager.main.getServer().getWorldContainer().getAbsolutePath());
					
					String dirname = SkyManager.main.getServer().getServerName();
					String abc = initialname + dirname;
					
					if (abc.length() > 32) {
						StringBuilder sb = new StringBuilder();
						char[] charlist = abc.toCharArray();
						int ii = 0;
						for(char chara : charlist) {
							if (!(ii == 30)) {
								sb.append(chara);
							} else {
								sb.append("..");
								break;
							}
							ii++;
						}
						abc = sb.toString();
					}
					Inventory inv = Bukkit.createInventory(null, 54, abc);
					
					File[] listfile = directory.listFiles();
					
					int[] arr = {1,10,19,28,37,46,3,12,21,30,39,48,5,14,23,32,41,50,7,16,25,34,43,52};

					for (int i = 0 ; i <= 53;){
				    	
						ItemStack a = SkyManager.main.addItem(Material.STAINED_GLASS_PANE, 1, (byte) 0, " ", Arrays.asList(" "));
						inv.setItem(i, a);
						
						i++;
				    }
					int i = 0;
				    int value = listfile.length;
					
				    if (value > 24) {
				    	ItemStack dire = SkyManager.main.addItem(Material.EMERALD, 1, (byte) 0, sm.changecfgline(sm.changePara(sm.message.getString("items.next-page.name")), "%page%", 1), Arrays.asList(sm.changecfgline(sm.changePara(sm.message.getString("items.next-page.lore")), "%page%", 1)));
						inv.setItem(53, dire);
						
						for (File file : listfile) {
							if (i <= value) {
								if (i != 24) {
									ItemStack a;
									
									if (file.isDirectory()) {
										a = SkyManager.main.addItem(Material.STORAGE_MINECART, 1, (byte) 0, "§a" + file.getName(), Arrays.asList(""));
									} else {
										a = SkyManager.main.addItem(Material.POWERED_MINECART, 1, (byte) 0, "§e" + file.getName(), Arrays.asList(""));
									}
									inv.setItem(arr[i], a);
								    i++;
								}
							}
						}
				    } else {
				    	for (File file : listfile) {
							if (i <= value) {
								ItemStack a;
								
								if (file.isDirectory()) {
									a = SkyManager.main.addItem(Material.STORAGE_MINECART, 1, (byte) 0, "§a" + file.getName(), Arrays.asList(""));
								} else {
									a = SkyManager.main.addItem(Material.POWERED_MINECART, 1, (byte) 0, "§e" + file.getName(), Arrays.asList(""));
								}
								inv.setItem(arr[i], a);
							}
						    i++;
						}
				    }
				    
					
					ItemStack dire = SkyManager.main.addItem(Material.CHEST, 1, (byte) 0, "§e" + SkyManager.main.getServer().getServerName(), Arrays.asList(""));

					inv.setItem(4, dire);
					
					player.openInventory(inv);
					
				} else {
					player.sendMessage(SkyManager.main.changePara(SkyManager.main.message.getString("errors.dont-permission")));
				}
				
			} else {
				sender.sendMessage(SkyManager.main.changePara(SkyManager.main.message.getString("errors.console-execute")));
			}
		return false;
	}

}
