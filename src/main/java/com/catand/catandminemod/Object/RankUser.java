package com.catand.catandminemod.Object;

import java.util.ArrayList;

public class RankUser {
	private String rank;
	private String nameColor;
	private String bracketColor;
	private String nick;
	private ArrayList<RankUserPet> pet;

	public RankUser(String rank, String nameColor, String bracketColor, String nick, ArrayList<RankUserPet> pet) {
		this.rank = rank;
		this.nameColor = nameColor;
		this.bracketColor = bracketColor;
		this.nick = nick;
		this.pet = pet;
	}

	public String getRank() {
		return rank;
	}

	public String getNameColor() {
		return nameColor;
	}

	public String getBracketColor() {
		return bracketColor;
	}

	public String getNick() {
		return nick;
	}

	public ArrayList<RankUserPet> getPet() {
		return pet;
	}
}