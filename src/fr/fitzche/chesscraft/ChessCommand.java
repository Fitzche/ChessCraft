package fr.fitzche.chesscraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Util.MathUtil;
import fr.ChessObject.Color;
import fr.ChessObject.Piece;
import fr.ChessObject.PieceType;
import fr.ChessObject.GameInterface;

public class ChessCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		Player player = (Player) sender;
		player.sendMessage("commande faite");
		
			if (args[0].equals("test")) {
				player.sendMessage("reussi");
			}else if (args[0].equals("duel")) {
				if (args[1] == null) {
					player.sendMessage("Veuillez entrer le nom du joueur à qui vous envoyez une requete");
					return true;
				}
				Main.requests.put(sender.getName(), args[1]);
				for (Player p:Main.plug.getServer().getOnlinePlayers()) {
					if (p.getName().equals(args[1])) {
						p.sendMessage("Faites /chess accept "+ player.getName() + " pour accepter la demande de duel de "+ player.getName());
					}
				}
			}else if (args[0].equals("accept")){
				if (args[1] == null) {
					player.sendMessage("Veuillez entrer le nom du joueur dont vous acceptez la requete");
				}
				if (Main.isInGame.get(player.getName() ) == null ) {
					Main.isInGame.put(player.getName(), false);
				}
				if (Main.isInGame.get(args[1] ) == null ) {
					Main.isInGame.put(args[1], false);
				}
				if (Main.isInGame.get(player.getName()) || Main.isInGame.get(args[1])) {
					player.sendMessage("Un des deux joueur est déjà en partie");
				} else {
					Player player2 = null;
					for (Player p:Main.plug.getServer().getOnlinePlayers()) {
						if (p.getName().equals(args[1])) {
							player2 = p;
						}
					}
					Game game;
					
					if (MathUtil.generateAlInt(0, 1) == 1) {
						
						game = new Game(player2, player);
						
						player2.openInventory(game.Whiteinv);
						player.openInventory(game.Blackinv);

					} else {
						game = new Game(player, player2);
						player2.openInventory(game.Blackinv);
						player.openInventory(game.Whiteinv);
					}
					Main.isInGame.put(player.getName(), true);
					Main.isInGame.put(player2.getName(), true);
					Main.gameOf.put(player.getName(), game);
					Main.gameOf.put(player2.getName(), game);
					Main.requests.put(player.getName(), null);
					Main.requests.put(player2.getName(), null);

					player.sendMessage("Vous jouez contre "+ player2.getName());
					player2.sendMessage("Vous jouez contre "+ player.getName());
					
				}
			}else if (args[0].equals("deny")){
				for (Player p:Main.plug.getServer().getOnlinePlayers()) {
					if (p.getName().equals(args[1])) {
						p.sendMessage("Votre demande de duel contre "+ player.getName() + " est refusée");
						Main.requests.put(p.getName(), null);
					}
				}
			
				
			}else if (args[0].equals("open")){
				if (Main.gameOf.get(player.getName()).equals(null)) {
					player.sendMessage("Vous n'avez pas de partie en cours");
				} else {
					if (Main.gameOf.get(player.getName()).black.getName().equals(player.getName())) {
						player.openInventory(Main.gameOf.get(player.getName()).Blackinv);
					} else {
						player.openInventory(Main.gameOf.get(player.getName()).Whiteinv);
					}
				}
			}else if (args[0].equals("giveup")){
				if (Main.gameOf.get(player.getName()).equals(null)) {
					player.sendMessage("Vous n'avez pas de partie en cours");
				} else {
					if (player.getName().equals(Main.gameOf.get(player.getName()).white.getName())) {
						Main.gameOf.get(player.getName()).winListener(Color.BLACK);
					} else {
						Main.gameOf.get(player.getName()).winListener(Color.WHITE);
					}
				}
			} else {
				return false;
			}
			return false;
		

			

	}

}
