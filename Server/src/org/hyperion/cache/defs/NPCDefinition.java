package org.hyperion.cache.defs;

import java.util.HashMap;

import org.hyperion.cache.Cache;
import org.hyperion.cache.stream.InputStream;
import org.hyperion.plugin.impl.OptionHandler;

public final class NPCDefinition {

	public static final NPCDefinition[] npcDefinitions = new NPCDefinition[Cache
			.getCacheFileManagers()[2].getFilesSize(9)];

	/**
	 * object plugin handlers
	 */
	private final HashMap<String,OptionHandler> HANDLERS = new HashMap<String,OptionHandler>();
	
	public int walkLeftAnimation;
	public int anInt1531;
	public int anInt1532;
	public int[] childrenIDs;
	public int width = 128;
	public int defaultTurnValue;
	public int headIcon;
	public String[] actions;
	public boolean canRightClick;
	public int[] model;
	public int idleAnimation;
	public boolean isInvisible;
	public int walkBackwardsAnimation;
	public boolean displayOnMinimap;
	public int[] originalModelColors;
	public int shading;
	public int id;
	public int combatLevel;
	public String name;
	public int walkRightAnimation;
	public int lightness;
	public int height;
	public int size;
	public int walkAnimation;
	public int[] headModels;
	public int[] modifiedModelColor;
	public String desc;

	public static final NPCDefinition forId(int npcId) {
		if (npcId < 0 || npcId >= npcDefinitions.length)
			return null;
		NPCDefinition def = npcDefinitions[npcId];
		if (def == null)
			npcDefinitions[npcId] = def = new NPCDefinition(npcId);
		return def;
	}

	private NPCDefinition(int id) {
		this.id = id;
		setDefaultsVariableValues();
		loadNpcDefinitions();
	}
	
	public static void init() {
		for (int i = 0; i < Cache
				.getCacheFileManagers()[2].getFilesSize(9);i++) {
			NPCDefinition.forId(i);
		}
		
	}

	private final void loadNpcDefinitions() {
		byte[] data = Cache.getCacheFileManagers()[2].getFileData(9, id);
		if (data == null) {
			System.out.println("Failed loading Item " + id + ".");
			return;
		}
		readOpcodes(new InputStream(data));
	}

	private void setDefaultsVariableValues() {
		anInt1532 = -1;
		canRightClick = true;
		idleAnimation = -1;
		actions = new String[5];
		isInvisible = false;
		walkLeftAnimation = -1;
		anInt1531 = -1;
		shading = 0;
		lightness = 0;
		combatLevel = -1;
		headIcon = -1;
		height = 128;
		defaultTurnValue = 32;
		walkAnimation = -1;
		displayOnMinimap = true;
		size = 1;
		walkBackwardsAnimation = -1;
		name = "null";
		walkRightAnimation = -1;
		desc = "null";
	}

	private final void readOpcodes(InputStream stream, int opcode) {
		if (opcode == 1) {
			int count = stream.readUnsignedByte();
			model = new int[count];
			for (int id = 0; id < count; id++)
				model[id] = stream.readUnsignedShort();
		}
		if (opcode == 2) {
			name = stream.readString();
		}
		if (opcode == 3) {
			desc = stream.readString();
		}
		if (opcode == 12) {
			size = stream.readUnsignedByte();
		}
		if (opcode == 13) {
			idleAnimation = stream.readUnsignedShort();
		}
		if (opcode == 14) {
			walkAnimation = stream.readUnsignedShort();
		}
		if (opcode == 17) {
			walkAnimation = stream.readUnsignedShort();
			walkBackwardsAnimation = stream.readUnsignedShort();
			walkLeftAnimation = stream.readUnsignedShort();
			walkRightAnimation = stream.readUnsignedShort();
		}
		if (opcode >= 30 && opcode < 35) {
			actions[opcode - 30] = stream.readString();
			if (actions[opcode - 30].equalsIgnoreCase("hidden"))
				actions[opcode - 30] = null;
		}
		if (opcode == 40) {
			int count = stream.readUnsignedByte();
			modifiedModelColor = new int[count];
			originalModelColors = new int[count];
			for (int id = 0; id < count; id++) {
				originalModelColors[id] = stream.readUnsignedShort();
				modifiedModelColor[id] = stream.readUnsignedShort();
			}
		}
		if (opcode == 60) {
			int count = stream.readUnsignedByte();
			headModels = new int[count];
			for (int id = 0; count > id; id++)
				headModels[id] = stream.readUnsignedShort();
		}
		if (opcode == 93) {
			displayOnMinimap = false;
		}
		if (opcode == 95) {
			combatLevel = stream.readUnsignedShort();
		}
		if (opcode == 97) {
			width = stream.readUnsignedShort();
		}
		if (opcode == 98) {
			height = stream.readUnsignedShort();
		}
		if (opcode == 99) {
			isInvisible = true;
		}
		if (opcode == 100) {
			lightness = stream.readByte();
		}
		if (opcode == 101) {
			shading = stream.readByte() * 5;
		}
		if (opcode == 102) {
			headIcon = stream.readUnsignedShort();
		}
		if (opcode == 103) {
			defaultTurnValue = stream.readUnsignedShort();
		}
		if (opcode == 106) {
			anInt1531 = stream.readUnsignedShort();
			if (anInt1531 == 65535)
				anInt1531 = -1;
			anInt1532 = stream.readUnsignedShort();
			if (anInt1532 == 65535)
				anInt1532 = -1;
			int count = stream.readUnsignedByte();
			childrenIDs = new int[count + 1];
			for (int i_34_ = 0; count >= i_34_; i_34_++) {
				childrenIDs[i_34_] = stream.readUnsignedShort();
				if (childrenIDs[i_34_] == 65535)
					childrenIDs[i_34_] = -1;
			}
		}
		if (opcode == 107) {
			canRightClick = false;
		}
	}

	private final void readOpcodes(InputStream stream) {
		while (true) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			readOpcodes(stream, opcode);
		}
	}

	public HashMap<String,OptionHandler> getHandlers() {
		return HANDLERS;
	}

}