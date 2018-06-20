package ch.canardconfit.skymanager.Listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ch.canardconfit.skymanager.ChatGUI;
import ch.canardconfit.skymanager.SkyManager;

public class MainListener implements Listener {
	
	SkyManager sm = SkyManager.main;
	
	String initial = sm.changePara(sm.message.getString("inventory.initial-name"));
	
	public MainListener(SkyManager skyManager) {
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent event) {
		
		
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getClickedInventory();
		ItemStack item = event.getCurrentItem();
		if (inv == null) {
			return;
		}
		String initialname = initial;
		if (initialname.length() > 32) {
			StringBuilder sb = new StringBuilder();
			char[] charlist = initialname.toCharArray();
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
			initialname = sb.toString();
		}
		if (inv.getName().contains(initialname)) {
			event.setCancelled(true);
			if (item.getItemMeta() != null) {
				if (item.getType() == Material.STORAGE_MINECART && item.getItemMeta().getDisplayName().contains("§a")) {
					
					player.closeInventory();
					player.updateInventory();
					
					
					String dirname = item.getItemMeta().getDisplayName().replace("§a", "");
					
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
					Inventory invn = Bukkit.createInventory(null, 54, abc);
					
					
					String rasinename = inv.getItem(4).getItemMeta().getDisplayName().replace("§e" + SkyManager.main.getServer().getServerName(), "");
					
					File directory;
					
					for (int i = 0 ; i <= 53;) {
				    	
						ItemStack a = SkyManager.main.addItem(Material.STAINED_GLASS_PANE, 1, (byte) 0, " ", Arrays.asList(" "));
						invn.setItem(i, a);
						
						i++;
				    }
					
					if (rasinename.equalsIgnoreCase("")) {
						
						ItemStack a = SkyManager.main.addItem(Material.BARRIER, 1, (byte) 0, sm.changePara(sm.changecfgline(sm.message.getString("items.back-racine.name"), "%racine%", SkyManager.main.getServer().getServerName())), Arrays.asList(sm.changePara(sm.message.getString("items.back-racine.lore"))));
						invn.setItem(0, a);
						 directory = new File(SkyManager.main.getServer().getWorldContainer().getAbsolutePath().replace(".", "") + dirname + "\\" + ".");
					} else {
						ItemStack a = SkyManager.main.addItem(Material.BARRIER, 1, (byte) 0, sm.changePara(sm.changecfgline(sm.message.getString("items.back-racine.name"), "%racine%", SkyManager.main.getServer().getServerName())), Arrays.asList(sm.changePara(sm.message.getString("items.back-racine.lore"))));
						invn.setItem(0, a);
						directory = new File(SkyManager.main.getServer().getWorldContainer().getAbsolutePath().replace(".", "") + rasinename + dirname + "\\" + ".");
					}
					
					
					File[] listfile = directory.listFiles();
					int[] arr = {1,10,19,28,37,46,3,12,21,30,39,48,5,14,23,32,41,50,7,16,25,34,43,52};  
	
					
					int i = 0;
				    int value = listfile.length;
					
				    if (value > 24) {
				    	ItemStack dire = SkyManager.main.addItem(Material.EMERALD, 1, (byte) 0, sm.changecfgline(sm.changePara(sm.message.getString("items.next-page.name")), "%page%", 1), Arrays.asList(sm.changecfgline(sm.changePara(sm.message.getString("items.next-page.lore")), "%page%", 1)));
						invn.setItem(53, dire);
						
						for (File file : listfile) {
							if (i <= value) {
								if (i != 24) {
									ItemStack a;
									
									if (file.isDirectory()) {
										a = SkyManager.main.addItem(Material.STORAGE_MINECART, 1, (byte) 0, "§a" + file.getName(), Arrays.asList(""));
									} else {
										a = SkyManager.main.addItem(Material.POWERED_MINECART, 1, (byte) 0, "§e" + file.getName(), Arrays.asList(""));
									}
									invn.setItem(arr[i], a);
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
								invn.setItem(arr[i], a);
							}
						    i++;
						}
				    }
				    
					
					String temp = directory.getPath().replace(SkyManager.main.getServer().getWorldContainer().getAbsolutePath().replace(".", ""),  SkyManager.main.getServer().getServerName() + "\\").replace(".", "");
					
					ItemStack dire = SkyManager.main.addItem(Material.CHEST, 1, (byte) 0, "§e" + temp, Arrays.asList(""));

					invn.setItem(4, dire);
					
					player.updateInventory();
					
					player.openInventory(invn);
					
					
					
					
					
					
					
					
					
					
					
					
					
				} else if (item.getType() == Material.BARRIER && item.getItemMeta().getDisplayName().equalsIgnoreCase(sm.changePara(sm.changecfgline(sm.message.getString("items.back-racine.name"), "%racine%", SkyManager.main.getServer().getServerName())))) {
					
					event.setCancelled(true);
					
					player.closeInventory();
					
					SkyManager.main.getServer().dispatchCommand(player, "skymanager");
					
					
					
					
					
					
					
					
					
					
					
					
					
					
				} else if (item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().contains(sm.changePara(sm.message.getString("items.next-page.name").replace("%page%", "")))) {
					
					player.closeInventory();
					player.updateInventory();
					
					String co = item.getItemMeta().getDisplayName().replace(sm.changePara(sm.message.getString("items.next-page.name").replace("%page%", "")), "");
					int nbp = Integer.parseInt(co);
					
					int nbv = nbp * 24;
					
					
					String dirname = inv.getName().replace(initialname, "");
					
					String abc = initialname + dirname;
					Inventory invn = Bukkit.createInventory(null, 54, abc);
					
					
					String rasinename = inv.getItem(4).getItemMeta().getDisplayName().replace("§e" + SkyManager.main.getServer().getServerName(), "");
					
					File directory;
					
					for (int i = 0 ; i <= 53;){
				    	
						ItemStack a = SkyManager.main.addItem(Material.STAINED_GLASS_PANE, 1, (byte) 0, " ", Arrays.asList(" "));
						invn.setItem(i, a);
						
						i++;
				    }
					ItemStack ab = SkyManager.main.addItem(Material.BARRIER, 1, (byte) 0, sm.changePara(sm.changecfgline(sm.message.getString("items.back-racine.name"), "%racine%", SkyManager.main.getServer().getServerName())), Arrays.asList(sm.changePara(sm.message.getString("items.back-racine.lore"))));
					invn.setItem(0, ab);
					directory = new File(SkyManager.main.getServer().getWorldContainer().getAbsolutePath().replace(".", "") + rasinename + "\\" + ".");
					
					
					File[] listfile2 = directory.listFiles();
					int[] arr = {1,10,19,28,37,46,3,12,21,30,39,48,5,14,23,32,41,50,7,16,25,34,43,52};
	
					int i = 0;
				    int value = listfile2.length;
				    
				    ArrayList<File> listfile = new ArrayList<File>();
					listfile.addAll(Arrays.asList(listfile2));
					
					for (int i1 = 0 ; i1 < nbv;) {
						listfile.remove(0);
						i1++;
					}
				    if (value > (nbv + 24)) {
				    	ItemStack dire = SkyManager.main.addItem(Material.EMERALD, 1, (byte) 0, sm.changecfgline(sm.changePara(sm.message.getString("items.next-page.name")), "%page%", nbp + 1), Arrays.asList(sm.changecfgline(sm.changePara(sm.message.getString("items.next-page.lore")), "%page%", nbp + 1)));
						invn.setItem(53, dire);
						
						
						
						for (File file : listfile) {
							if (i <= value) {
								if (i != 24) {
									ItemStack a;
									
									if (file.isDirectory()) {
										a = SkyManager.main.addItem(Material.STORAGE_MINECART, 1, (byte) 0, "§a" + file.getName(), Arrays.asList(""));
									} else {
										a = SkyManager.main.addItem(Material.POWERED_MINECART, 1, (byte) 0, "§e" + file.getName(), Arrays.asList(""));
									}
									invn.setItem(arr[i], a);
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
								invn.setItem(arr[i], a);
							}
						    i++;
						}
				    }
				    
					
					String temp = directory.getPath().replace(SkyManager.main.getServer().getWorldContainer().getAbsolutePath().replace(".", ""),  SkyManager.main.getServer().getServerName() + "\\").replace(".", "");
					
					ItemStack dire = SkyManager.main.addItem(Material.CHEST, 1, (byte) 0, "§e" + temp, Arrays.asList(""));

					invn.setItem(4, dire);
					
					player.updateInventory();
					
					player.openInventory(invn);
				
				
				
				
				
				
				
				} else if (SkyManager.main.enabled_file_view == true && item.getType() == Material.POWERED_MINECART) {
					String rasinename = inv.getItem(4).getItemMeta().getDisplayName().replace("§e" + SkyManager.main.getServer().getServerName(), "");
					File directory;
					String dirname = item.getItemMeta().getDisplayName().replace("§e", "");
					if (rasinename.equalsIgnoreCase("")) {
						directory = new File(SkyManager.main.getServer().getWorldContainer().getAbsolutePath().replace(".", ""), dirname);
					} else {
						directory = new File(SkyManager.main.getServer().getWorldContainer().getAbsolutePath().replace(".", "") + rasinename + "\\", dirname);
					}
					
					String extention = getExtention(directory.getName());
					if (isBanned(extention) == false) {
						player.closeInventory();
						if (SkyManager.main.getPlayer(player) != null) {
							SkyManager.main.removeChatGUI(player);
							player.sendMessage(sm.changePara(sm.message.getString("file-view.exit-file.line0")));
							player.sendMessage(sm.changePara(sm.changecfgline(sm.message.getString("file-view.exit-file.line1"), "%file%", directory.getName())));
							player.sendMessage(sm.changePara(sm.message.getString("file-view.exit-file.line2")));
						}
						try {
							File dataFile = directory;
							BufferedReader br = new BufferedReader(new FileReader(dataFile));
							String line;
							int i = 0;
							int nbv = 0 * 10;
							
							ArrayList<String> listline = new ArrayList<String>();
							while ((line = br.readLine()) != null) {
								listline.add(line);
							}
							int value = listline.size();
							player.sendMessage(sm.changePara(sm.message.getString("file-view.info-file.line0")));
							String a = sm.changePara(sm.message.getString("file-view.info-file.line1"));
							a = sm.changecfgline(a, "%file%", directory.getName());
							a = sm.changecfgline(a, "%last-page%", (value / 10));
							player.sendMessage(a);
							player.sendMessage(sm.changePara(sm.message.getString("file-view.info-file.line2")));
							if (nbv <= value) {
								for (int i1 = 0 ; i1 < nbv;) {
									listline.remove(0);
									i1++;
								}
								for (String ab : listline) {
									if (i <= value) {
										if (i != 10) {
											if (extention.equalsIgnoreCase("yml")) {
												String[] lst = ab.split(":");
												if (lst.length >= 2) {
													if (!lst[0].contains("#")) {
														if (isInt(lst[1]) == true) {
															player.sendMessage("§c" + lst[0] + "§7:§6" + lst[1]);
														} else if (lst[1].equalsIgnoreCase("true")) {
															player.sendMessage("§c" + lst[0] + "§7:§a" + lst[1]);
														} else if (lst[1].equalsIgnoreCase("false")) {
															player.sendMessage("§c" + lst[0] + "§7:§4" + lst[1]);
														} else {
															player.sendMessage("§c" + lst[0] + "§7:§e" + lst[1]);
														}
													} else if (lst[0].contains("#")) {
														player.sendMessage("§7" + ab);
													}
												} else {
													if (ab.contains(":")) {
														player.sendMessage("§c" + ab.replace(":", "") + "§7:");
													} else {
														player.sendMessage("§7" + ab);
													}
												}
												
											} else if (extention.equalsIgnoreCase("properties")) {
												String[] lst = ab.split("=");
												if (lst.length >= 2) {
													if (!lst[0].contains("#")) {
														if (isInt(lst[1]) == true) {
															player.sendMessage("§c" + lst[0] + "§7=§6" + lst[1]);
														} else if (lst[1].equalsIgnoreCase("true")) {
															player.sendMessage("§c" + lst[0] + "§7=§a" + lst[1]);
														} else if (lst[1].equalsIgnoreCase("false")) {
															player.sendMessage("§c" + lst[0] + "§7=§4" + lst[1]);
														} else {
															player.sendMessage("§c" + lst[0] + "§7=§e" + lst[1]);
														}
													} else if (lst[0].contains("#")) {
														player.sendMessage("§7" + ab);
													}
												} else {
													if (ab.contains("=")) {
														player.sendMessage("§c" + ab.replace("=", "") + "§7=");
													} else {
														player.sendMessage("§7" + ab);
													}
												}
											} else {
												player.sendMessage(ab);
											}
											i++;
										}
									}
								}
								br.close();
								if (i == 10) {
									SkyManager.main.addChatGUI(player, directory);
									ChatGUI chat = SkyManager.main.getPlayer(player);
									chat.setPage(1);
									player.sendMessage(sm.changePara(sm.message.getString("file-view.enter-page.line0")));
									player.sendMessage(sm.changePara(sm.changecfgline(sm.message.getString("file-view.enter-page.line1"), "%number%", 1)));
									player.sendMessage(sm.changePara(sm.message.getString("file-view.enter-page.line2")));
								} else {
									player.sendMessage(sm.changePara(sm.message.getString("file-view.end-file.line0")));
									player.sendMessage(sm.changePara(sm.message.getString("file-view.end-file.line1")));
									player.sendMessage(sm.changePara(sm.message.getString("file-view.end-file.line2")));
								}
							}
						} catch (IOException e) {
							System.out.println("SkyManager >> An Error on view file");
						}
					} else {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (SkyManager.main.getPlayer(player) != null) {
			event.setCancelled(true);
			SkyManager.main.removeChatGUI(player);
			player.sendMessage(sm.changePara(sm.message.getString("file-view.exit-file-command.line0")));
			player.sendMessage(sm.changePara(sm.message.getString("file-view.exit-file-command.line1")));
			player.sendMessage(sm.changePara(sm.message.getString("file-view.exit-file-command.line2")));
		}
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		
		if (SkyManager.main.getPlayer(player) != null) {
			event.setCancelled(true);
			ChatGUI chat = SkyManager.main.getPlayer(player);
			File file = chat.getFile();
			int page = chat.getPage();
			try {
				int msg = Integer.parseInt(event.getMessage());
				page = msg;
					try {
						File dataFile = file;
						BufferedReader br = new BufferedReader(new FileReader(dataFile));
						String line;
						int i = 0;
						int nbv = page * 10;
						
						ArrayList<String> listline = new ArrayList<String>();
						while ((line = br.readLine()) != null) {
							listline.add(line);
						}
						int value = listline.size();
						player.sendMessage(sm.changePara(sm.message.getString("file-view.info-file.line0")));
						String a = sm.changePara(sm.message.getString("file-view.info-file.line1"));
						a = sm.changecfgline(a, "%file%", file.getName());
						a = sm.changecfgline(a, "%last-page%", (value / 10));
						player.sendMessage(a);
						player.sendMessage(sm.changePara(sm.message.getString("file-view.info-file.line2")));
						if (nbv <= value) {
							for (int i1 = 0 ; i1 < nbv;) {
								listline.remove(0);
								i1++;
							}
							for (String ab : listline) {
								if (i <= value) {
									if (i != 10) {
										String extention = getExtention(file.getName());
										if (extention.equalsIgnoreCase("yml")) {
											String[] lst = ab.split(":");
											if (lst.length >= 2) {
												if (!lst[0].contains("#")) {
													if (isInt(lst[1]) == true) {
														player.sendMessage("§c" + lst[0] + "§7:§6" + lst[1]);
													} else if (lst[1].equalsIgnoreCase("true")) {
														player.sendMessage("§c" + lst[0] + "§7:§a" + lst[1]);
													} else if (lst[1].equalsIgnoreCase("false")) {
														player.sendMessage("§c" + lst[0] + "§7:§4" + lst[1]);
													} else {
														player.sendMessage("§c" + lst[0] + "§7:§e" + lst[1]);
													}
												} else if (lst[0].contains("#")) {
													player.sendMessage("§7" + ab);
												}
											} else {
												if (ab.contains(":")) {
													player.sendMessage("§c" + ab.replace(":", "") + "§7:");
												} else {
													player.sendMessage("§7" + ab);
												}
											}
											
										} else if (extention.equalsIgnoreCase("properties")) {
											String[] lst = ab.split("=");
											if (lst.length >= 2) {
												if (!lst[0].contains("#")) {
													if (isInt(lst[1]) == true) {
														player.sendMessage("§c" + lst[0] + "§7=§6" + lst[1]);
													} else if (lst[1].equalsIgnoreCase("true")) {
														player.sendMessage("§c" + lst[0] + "§7=§a" + lst[1]);
													} else if (lst[1].equalsIgnoreCase("false")) {
														player.sendMessage("§c" + lst[0] + "§7=§4" + lst[1]);
													} else {
														player.sendMessage("§c" + lst[0] + "§7=§e" + lst[1]);
													}
												} else if (lst[0].contains("#")) {
													player.sendMessage("§7" + ab);
												}
											} else {
												if (ab.contains("=")) {
													player.sendMessage("§c" + ab.replace("=", "") + "§7=");
												} else {
													player.sendMessage("§7" + ab);
												}
											}
										} else {
											player.sendMessage(ab);
										}
										i++;
									}
								}
							}
							br.close();
							if (i == 10) {
								chat.setPage(page + 1);
								player.sendMessage(sm.changePara(sm.message.getString("file-view.enter-page.line0")));
								player.sendMessage(sm.changePara(sm.changecfgline(sm.message.getString("file-view.enter-page.line1"), "%number%", (page + 1))));
								player.sendMessage(sm.changePara(sm.message.getString("file-view.enter-page.line2")));
							} else {
								player.sendMessage(sm.changePara(sm.message.getString("file-view.end-file.line0")));
								player.sendMessage(sm.changePara(sm.message.getString("file-view.end-file.line1")));
								player.sendMessage(sm.changePara(sm.message.getString("file-view.end-file.line2")));
							}
						}
					} catch (IOException e) {
						System.out.println("SkyManager >> An Error on view file");
					}
			} catch (NumberFormatException e) {
				if (event.getMessage().equalsIgnoreCase("exit")) {
					SkyManager.main.removeChatGUI(player);
					player.sendMessage(sm.changePara(sm.message.getString("file-view.exit-file.line0")));
					player.sendMessage(sm.changePara(sm.changecfgline(sm.message.getString("file-view.exit-file.line1"), "%file%", file.getName())));
					player.sendMessage(sm.changePara(sm.message.getString("file-view.exit-file.line2")));
					SkyManager.main.getServer().dispatchCommand(player, "skymanager");
				} else {
					player.sendMessage(sm.changePara(sm.message.getString("file-view.command-doesnt-exist.line0")));
					player.sendMessage(sm.changePara(sm.message.getString("file-view.command-doesnt-exist.line1")));
					player.sendMessage(sm.changePara(sm.message.getString("file-view.command-doesnt-exist.line2")));
				}
			}
		}
	}
	private boolean isBanned(String extention) {
		for (String ext : SkyManager.main.config.getStringList("ban-file-view")) {
			if (extention.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}
	private String getExtention(String name) {
		
		name = name.replace("§e", "");
		
		String[] nameex = name.split("\\.");

		return nameex[nameex.length - 1];
	}
	private static boolean isInt(String str) {  
		try {
			  Double.parseDouble(str);
		} catch(NumberFormatException nfe) {return false;}
		return true;
	}
	//private String getName(String name) {
		
	//	name = name.replace("§e", "");
		
	//	String[] nameex = name.split("\\.");
		
	//	String br = "";
		
	//	for (int i = 0 ; i < (nameex.length - 1);) {
	//		if (i == (nameex.length - 2)) {
	//			br = br + nameex[i];
	//		} else {
	//			br = br + nameex[i] + ".";
	//		}
	//		i++;
	//	}
	//	return br;
	//}

}
