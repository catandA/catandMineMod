package com.catand.catandminemod.Object;

import java.util.ArrayList;

public class RankUser {
	private String name;
	private final String rank;
	private final String nameColor;
	private final String bracketColor;
	private final String nick;
	private final ArrayList<RankUserPet> pet;

	public RankUser(String name, String rank, String nameColor, String bracketColor, String nick, ArrayList<RankUserPet> pet) {
		this.name = name;
		this.rank = rank;
		this.nameColor = nameColor;
		this.bracketColor = bracketColor;
		this.nick = nick;
		this.pet = pet;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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