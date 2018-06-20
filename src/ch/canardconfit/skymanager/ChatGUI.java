package ch.canardconfit.skymanager;

import java.io.File;

import org.bukkit.entity.Player;

public class ChatGUI {

	private Player pl;
	private File fileopen;
	private int page;
	
	
	public ChatGUI(Player player, File file) {
		
		pl = player;
		fileopen = file;
	}
	
	public File getFile() {
		return fileopen;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int p) {
		page = p;
	}
	public Player getPlayer() {
		return pl;
	}
}
