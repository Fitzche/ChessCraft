package fr.fitzche.chesscraft;

import org.bukkit.plugin.java.JavaPlugin;

import fr.fitzche.chessplugin.Chess;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		getCommand("chess").setExecutor(new ChessCommand());
	}
}
