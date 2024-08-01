package fr.fitzche.chesscraft;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin{
	
	public static JavaPlugin plug;
	public static HashMap<String, String> requests = new HashMap<String, String>();
	public static HashMap<String, Boolean> isInGame = new HashMap<String, Boolean>();
	public static HashMap<String, Game> gameOf = new HashMap<String, Game>();


	public JavaPlugin getPlugin() {
		return plug;
	}
	@Override
	public void onEnable() {
		getCommand("chess").setExecutor(new ChessCommand());
		Main.plug = this;
	}
}
