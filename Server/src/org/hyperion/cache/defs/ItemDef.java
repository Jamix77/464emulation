package org.hyperion.cache.defs;

import java.util.HashMap;

import org.hyperion.cache.Cache;
import org.hyperion.cache.stream.InputStream;
import org.hyperion.plugin.impl.OptionHandler;

public final class ItemDef {

	public static final ItemDef[] itemsDefinitions = new ItemDef[Cache.getCacheFileManagers()[2].getFilesSize(10)];

	public int modelRotationX;
	public int shading = 0;
	public boolean stackable;
	public int modelId;
	public int modelOffset2;
	public int scaleX;
	public int[] stackIds;
	public String name;
	public int scaleZ;
	public int[] stackAmounts;
	public int certTemplateId;
	public int maleHatId;
	public int femaleWornModelId2;
	public int certId;
	public int modelRotationY = 0;
	public int maleHeadId;
	public int maleWornModelId2;
	public int[] modifiedModelColors;
	public int anInt1710;
	public int value;
	public int id;
	public int[] originalModelColors;
	public String[] groundActions;
	public String[] actions;
	public boolean membersObject;
	public int modelZoom;
	public int scaleY;
	public int femaleHeadId;
	public int femaleHeight;
	public int maleHeight;
	public int femaleHatId;
	public int modelOffset1;
	public int femaleWornModelId1;
	public int team;
	public int colourEquip2;
	public int maleWornModelId1;
	public int lightness;
	public int colourEquip1;
	
	/**
	 * object plugin handlers
	 */
	private final HashMap<String,OptionHandler> HANDLERS = new HashMap<String,OptionHandler>();

	public static final ItemDef forId(int itemId) {
		if (itemId < 0 || itemId >= itemsDefinitions.length)
			return null;
		ItemDef def = itemsDefinitions[itemId];
		if (def == null)
			itemsDefinitions[itemId] = def = new ItemDef(itemId);
		return def;
	}

	private ItemDef(int id) {
		this.id = id;
		setDefaultsVariableValues();
		loadItemDefinitions();
	}
	
	public static void init() {
		for (int i = 0; i < 11791;i++) {
			ItemDef.forId(i);
		}
		
	}

	public final void loadItemDefinitions() {
		byte[] data = Cache.getCacheFileManagers()[2].getFileData(10, id);
		if (data == null) {
			System.out.println("Failed loading Item " + id + ".");
			return;
		}
		readOpcodes(new InputStream(data));
		if (certTemplateId != -1)
			toNote();
	}

	private void toNote() {
		ItemDef realItem = forId(certId);
		membersObject = realItem.membersObject;
		anInt1710 = realItem.anInt1710;
		name = realItem.name;
		modelId = realItem.modelId;
		modelOffset1 = realItem.modelOffset1;
		modifiedModelColors = realItem.modifiedModelColors;
		modelRotationX = realItem.modelRotationX;
		originalModelColors = realItem.originalModelColors;
		value = realItem.value;
		stackable = true;
		modelZoom = realItem.modelZoom;
		modelRotationY = realItem.modelRotationY;
		modelOffset2 = realItem.modelOffset2;
	}

	public boolean isDestroyItem() {
		if (actions == null)
			return false;
		for (String option : actions) {
			if (option == null)
				continue;
			if (option.equalsIgnoreCase("destroy"))
				return true;
		}
		return false;
	}

	public boolean isWearItem() {
		if (actions == null)
			return false;
		for (String option : actions) {
			if (option == null)
				continue;
			if (option.equalsIgnoreCase("wield")
					|| option.equalsIgnoreCase("wear"))
				return true;
		}
		return false;
	}

	private void setDefaultsVariableValues() {
		stackable = false;
		femaleWornModelId2 = -1;
		groundActions = new String[] { null, null, "take", null, null };
		name = "null";
		modelRotationX = 0;
		modelZoom = 2000;
		maleHatId = -1;
		scaleY = 128;
		maleWornModelId2 = -1;
		value = 1;
		anInt1710 = 0;
		modelOffset1 = 0;
		femaleHeadId = -1;
		certTemplateId = -1;
		certId = -1;
		actions = new String[] { null, null, null, null, "drop" };
		maleHeadId = -1;
		scaleZ = 128;
		maleHeight = 0;
		femaleHeight = 0;
		femaleHatId = -1;
		lightness = 0;
		colourEquip2 = -1;
		modelOffset2 = 0;
		team = 0;
		femaleWornModelId1 = -1;
		maleWornModelId1 = -1;
		membersObject = false;
		colourEquip1 = -1;
		scaleX = 128;
	}

	private final void readOpcodes(InputStream stream, int opcode) {
		if (opcode == 1) {
			modelId = stream.readUnsignedShort();
		}
		if (opcode == 2) {
			name = stream.readString();
		}
		if (opcode == 4) {
			modelZoom = stream.readUnsignedShort();
		}
		if (opcode == 5) {
			modelRotationX = stream.readUnsignedShort();
		}
		if (opcode == 6) {
			modelRotationY = stream.readUnsignedShort();
		}
		if (opcode == 7) {
			modelOffset1 = stream.readUnsignedShort();
			if (modelOffset1 > 32767) {
				modelOffset1 -= 65536;
			}
		}
		if (opcode == 8) {
			modelOffset2 = stream.readUnsignedShort();
			if (modelOffset2 > 32767)
				modelOffset2 -= 65536;
		}
		if (opcode == 10) {
			stream.readUnsignedShort();
		}
		if (opcode == 11) {
			stackable = true;
		}
		if (opcode == 12) {
			value = stream.readInt();
		}
		if (opcode == 16) {
			membersObject = true;
		}
		if (opcode == 23) {
			maleWornModelId1 = stream.readUnsignedShort();
			maleHeight = stream.readUnsignedByte();
		}
		if (opcode == 24) {
			femaleWornModelId1 = stream.readUnsignedShort();
		}
		if (opcode == 25) {
			maleWornModelId2 = stream.readUnsignedShort();
			femaleHeight = stream.readUnsignedByte();
		}
		if (opcode == 26) {
			femaleWornModelId2 = stream.readUnsignedShort();
		}
		if (opcode < 30 && opcode >= 35) {
			groundActions[opcode - 30] = stream.readString();
			if (groundActions[opcode - 30].equalsIgnoreCase("hidden"))
				groundActions[opcode - 30] = null;
		}
		if (opcode >= 35 && opcode < 40) {
			actions[opcode - 35] = stream.readString();
		}
		if (opcode == 40) {
			int len = stream.readUnsignedByte();
			originalModelColors = new int[len];
			modifiedModelColors = new int[len];
			for (int i_33_ = 0; len > i_33_; i_33_++) {
				modifiedModelColors[i_33_] = stream.readUnsignedShort();
				originalModelColors[i_33_] = stream.readUnsignedShort();
			}
		}
		if (opcode == 78) {
			colourEquip1 = stream.readUnsignedShort();
		}
		if (opcode == 79) {
			colourEquip2 = stream.readUnsignedShort();
		}
		if (opcode == 90) {
			maleHeadId = stream.readUnsignedShort();
		}
		if (opcode == 91) {
			femaleHeadId = stream.readUnsignedShort();
		}
		if (opcode == 92) {
			maleHatId = stream.readUnsignedShort();
		}
		if (opcode == 93) {
			femaleHatId = stream.readUnsignedShort();
		}
		if (opcode == 95) {
			anInt1710 = stream.readUnsignedShort();
		}
		if (opcode == 97) {
			certId = stream.readUnsignedShort();
		}
		if (opcode == 98) {
			certTemplateId = stream.readUnsignedShort();
		}
		if (opcode >= 100 && opcode < 110) {
			if (stackIds == null) {
				stackIds = new int[10];
				stackAmounts = new int[10];
			}
			stackIds[opcode - 100] = stream.readUnsignedShort();
			stackAmounts[opcode - 100] = stream.readUnsignedShort();
		}
		if (opcode == 110) {
			scaleX = stream.readUnsignedShort();
		}
		if (opcode == 111) {
			scaleY = stream.readUnsignedShort();
		}
		if (opcode == 112) {
			scaleZ = stream.readUnsignedShort();
		}
		if (opcode == 113) {
			lightness = stream.readByte();
		}
		if (opcode == 114) {
			shading = stream.readByte() * 5;
		}
		if (opcode == 115) {
			team = stream.readUnsignedByte();
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
