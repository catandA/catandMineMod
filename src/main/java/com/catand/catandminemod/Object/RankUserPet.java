package com.catand.catandminemod.Object;

public class RankUserPet {
	private final String name;
	private final String displayName;
	private final String nameColor;
	private final String bracketColor;

	public RankUserPet(String name, String nameColor, String bracketColor, String displayName) {
		this.name = name;
		this.displayName = displayName;
		this.nameColor = nameColor;
		this.bracketColor = bracketColor;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getNameColor() {
		return nameColor;
	}

	public String getBracketColor() {
		return bracketColor;
	}
}
