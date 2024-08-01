package fr;



import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.fitzche.chesscraft.Game;
import fr.fitzche.chesscraft.Main;

public class ClickListener implements Listener {
    public Game game;
    
    public ClickListener(Game game) {
        this.game = game;
        Bukkit.getPluginManager().registerEvents(this, Main.plug);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String name = event.getWhoClicked().getName();
        if (event.getInventory().equals(game.Blackinv) ||event.getInventory().equals(game.Whiteinv) ) {
            
        	if ((event.getAction().compareTo(InventoryAction.PLACE_ALL) == 1 || event.getAction().compareTo(InventoryAction.PICKUP_ALL) == 1)) {
                event.setCancelled(true);
                System.out.println("No, Action is "+ event.getAction().name());
                return;
            }
            if (event.getInventory().equals(game.Whiteinv) && name.equals(game.white.getName())) {
                if (game.moveFirstPosWhite != null && !game.moveFirstPosWhite.equals(event.getCurrentItem()) && event.getAction().compareTo(InventoryAction.PLACE_ALL) == 1) {
                    if (game.turnWhite) {
                        game.play(game.moveFirstPosWhite.letter, game.moveFirstPosWhite.number, Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(0)), Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(1)));
                    } else {
                    	event.getWhoClicked().sendMessage("ce n'est pas votre tour");
                    	event.setCancelled(true);
                    }
                } else if (event.getAction().compareTo(InventoryAction.PICKUP_ALL) == 1){
                    game.moveFirstPosWhite = game.board.getPosition(Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(0)), Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(1)));
                    System.out.println("first pos set");
                }

            }
            if (event.getInventory().equals(game.Blackinv) && name.equals(game.black.getName())) {
                if (game.moveFirstPosBlack != null && !game.moveFirstPosBlack.equals(event.getCurrentItem())) {
                    if (!game.turnWhite) {
                        game.play(game.moveFirstPosBlack.letter, game.moveFirstPosBlack.number, Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(0)), Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(1)));
                    } else {
                    	event.getWhoClicked().sendMessage("Ce n'est pas votre tour");
                    }
                } else  if (event.getAction().compareTo(InventoryAction.PICKUP_ALL) == 1){
                    game.moveFirstPosBlack = game.board.getPosition(Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(0)), Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(1)));

                }
            }
        }

        
        
        
        
    }


}
