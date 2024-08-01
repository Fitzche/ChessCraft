package fr.fitzche.chesscraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Util.ItemUtil;
import fr.ClickListener;
import fr.ChessObject.Board;
import fr.ChessObject.Color;
import fr.ChessObject.GameInterface;
import fr.ChessObject.Piece;
import fr.ChessObject.PieceType;
import fr.ChessObject.Position;
import fr.ChessObject.Rapport;
import net.md_5.bungee.api.ChatColor;

public class Game implements GameInterface {
	public Player white;
	public Player black;
	public Inventory Blackinv;
	public Inventory Whiteinv;
	public Board board;
	public Position moveFirstPosWhite;
	public Position moveFirstPosBlack;
	public ClickListener listener;
	public boolean turnWhite = true;
	
	
	public Game(Player white, Player black) {
		this.white = white;
		this.black = black;
		this.board = new Board(this);
		this.listener = new ClickListener(this);
		
		List<Position> posPionsB = board.getPositions().subList(9, 16);
		for (Position pos: posPionsB) {
			pos.piece = new Piece(board, PieceType.PION, pos, Color.WHITE);
			
		}
		
		List<Position> posPionsN = board.getPositions().subList(49, 56);
		for (Position pos: posPionsN) {
			pos.piece = new Piece(board, PieceType.PION, pos, Color.BLACK);
			
		}
		PieceType[] piecesT = new PieceType[] {
				PieceType.TOWER_NOMOVE,
				PieceType.HORSE,
				PieceType.FOU, 
				PieceType.QUEEN,
				PieceType.KING_NOMOVE,
				PieceType.FOU,
				PieceType.HORSE,
				PieceType.TOWER_NOMOVE
				};
		for (int i = 1; i<9; i++) {
			board.getPosition(i, 1).piece = new Piece(board, piecesT[i-1], board.getPosition(i, 1), Color.WHITE);
			board.getPosition(i, 8).piece = new Piece(board, piecesT[i-1], board.getPosition(i, 1), Color.BLACK);

		}
		this.Blackinv = this.getBlackInv();
		this.Whiteinv = this.getWhiteInv();
		
	}

	@Override
	public Rapport play(int arg0, int arg1, int arg2, int arg3) {
		Rapport rapp = board.playMove(board.getPosition(arg0, arg1), board.getPosition(arg2, arg3));
		if (rapp.possible) {
			turnWhite = (!turnWhite);
		} else {
			if (board.getPosition(arg0, arg1).piece.color.equals(Color.WHITE)) {
				white.sendMessage("mouvement impossible");
			} else {
				black.sendMessage("mouvement impossible");
			}
		}
		
		return rapp;
		
	}

	@Override
	public void winListener(Color arg0) {
		String name = "";
		String otherName = "";
		switch (arg0) {
			case BLACK:
				name = black.getName();
				otherName = white.getName();
			case WHITE:
				name = white.getName();
				otherName = black.getName();
			default:
				break;
			
		}
		Bukkit.broadcastMessage(ChatColor.ITALIC+"Le joueur "+ name + " a battu "+ otherName);

	}
	
	public Inventory getWhiteInv() {
		this.Whiteinv = Bukkit.createInventory(null, 72);
		for (Position pos: board.getPositions()) {
			if (pos.piece != null) {
				ItemStack item = new ItemStack(Material.NAME_TAG);
				String cStr = "";
				switch (pos.piece.color) {
				case BLACK:
					cStr = "Noir";
				case WHITE:
					cStr = "Blanc";
				default:
					break;
				
				}
				
				String typeStr = "";
				switch (pos.piece.type) {
				case FOU:
					typeStr = "Fou ";
				case HORSE:
					typeStr = "Cavalier ";
				case KING:
					typeStr = "Roi ";
				case KING_NOMOVE:
					typeStr = "Roi ";
				case PION:
					typeStr = "Pion ";
				case QUEEN:
					typeStr = "Dame ";
				case TOWER:
					typeStr = "Tour ";
				case TOWER_NOMOVE:
					typeStr = "Tour ";
				default:
					break;
				
				}
				ItemUtil.setName(item, typeStr + cStr);
				List<String> lores = new ArrayList<String>(); 
				lores.add(String.valueOf(pos.letter));
				lores.add(String.valueOf(pos.number));
				ItemUtil.setLore(item, lores);
				this.Whiteinv.setItem(getInvIndexOfPosition(pos), item);
				
			}
			
		}
		return this.Whiteinv;
		
	}
	
	public Inventory getBlackInv() {
		Inventory inv = Bukkit.createInventory(null, 72);
		Inventory whiteInv = getWhiteInv();
		for (int i = 0; i < 72; i ++) {
			inv.setItem(i, whiteInv.getItem(71 - i));
		}
		this.Blackinv = inv;
		return this.Blackinv;
	}
	
	public Integer getInvIndexOfPosition(Position p) {
		String str = String.valueOf(((p.number - 1) * 9) + p.letter);
		System.out.println("slot of" +String.valueOf(p.letter) + " & "+String.valueOf(p.number) + " is "+ str);
		return (((p.number - 1) * 9) + p.letter);
	}

	@Override
	public Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}

	@Override
	public void nulListener() {
		Bukkit.broadcastMessage(ChatColor.ITALIC+"Les joueurs "+ white.getName() + " et "+ black.getName() + " ont fait partie nulle");
		
	}

}
