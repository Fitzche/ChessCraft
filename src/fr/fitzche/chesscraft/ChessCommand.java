package fr.fitzche.chesscraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChessCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("chess")) {
			switch (args[1]) {
			case "duel":
			case "accept":
			case "deny":
			case "openparty":
			}
		}
		return false;
	}

}
