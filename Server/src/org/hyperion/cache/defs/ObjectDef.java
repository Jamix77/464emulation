package org.hyperion.cache.defs;

import org.hyperion.cache.Cache;
import org.hyperion.cache.stream.InputStream;
import org.hyperion.rs2.model.Location;

public final class ObjectDef {

	public static final ObjectDef[] objectDefinitions = new ObjectDef[Cache.getCacheFileManagers()[2].getFilesSize(6)];

	public int anInt1641 = -1;
    public int anInt1642;
    public int shading;
    public int anInt1644 = 0;
    public int[] childrenIDs;
    public String name;
    public int groundDecorationSprite;
    public boolean aBoolean1648;
    public int id;
    public boolean isSolid;
    public int sizeX = 1;
    public int hasActions;
    public boolean isWalkable = true;
    public boolean aBoolean1654;
    public int scaleX;
    public int[] originalModelColors;
    public int anInt1658;
    public String[] actions;
    public boolean aBoolean1660;
    public int animationId;
    public int[] modifiedModelColors;
    public int mapSceneSprite;
    public int sizeY;
    public boolean aBoolean1665;
    public boolean aBoolean1666;
    public int anInt1668;
    public int anInt1670;
    public int anInt1671;
    public int scaleY;
    public int anInt1673;
    public boolean aBoolean1674;
    public int[] types;
    public int scaleZ;
    public boolean aBoolean1677;
    public int[] models;
    public int lightness;

    public static boolean projectileCliped = true;
	public int clipType = 2;
    
    final void method332() {
    	if (hasActions == -1) {
    		hasActions = 0;
    		if (models != null && (types == null || types[0] == 10))
    			hasActions = 1;
		    for (int i_26_ = 0; i_26_ < 5; i_26_++) {
		    	if (actions[i_26_] != null)
		    		hasActions = 1;
		    }
    	}
    	if (anInt1671 == -1)
    		anInt1671 = isSolid ? 1 : 0;
    }
    
    public int actionCount() {
    	int count = 0;
    	for(int i = 0; i < actions.length; i++) {
    		if(actions[i] == null)
    			continue;
    		if(!actions[i].equalsIgnoreCase("null") || !actions[i].equalsIgnoreCase("hidden"))
    			count++;
    	}
    	return count;
    }

	public final static ObjectDef forId(int objId) {
		if (objId < 0 || objId >= objectDefinitions.length)
			return null;
		ObjectDef def = objectDefinitions[objId];
		if (def == null)
			objectDefinitions[objId] = def = new ObjectDef(objId);
		return def;
	}

	private ObjectDef(int id) {
		this.id = id;
		setDefaultsVariableValues();
		loadObjectDefinitions();
	}

	private final void loadObjectDefinitions() {
		byte[] data = Cache.getCacheFileManagers()[2].getFileData(6, id);
		if (data == null) {
			System.out.println("Failed loading object " + id + ".");
			return;
		}
		readOpcodes(new InputStream(data));
		method332();
	}

	private void setDefaultsVariableValues() {
		groundDecorationSprite = -1;
		scaleX = 128;
		animationId = -1;
		name = "null";
		aBoolean1654 = false;
		mapSceneSprite = -1;
		aBoolean1660 = false;
		isSolid = true;
		aBoolean1665 = false;
		aBoolean1666 = true;
		hasActions = -1;
		anInt1642 = 0;
		scaleY = 128;
		shading = 0;
		projectileCliped = true;
		anInt1658 = -1;
		aBoolean1648 = false;
		sizeY = 1;
		aBoolean1674 = false;
		anInt1671 = -1;
		anInt1668 = 0;
		actions = new String[5];
		anInt1670 = 0;
		scaleZ = 128;
		anInt1673 = 16;
		aBoolean1677 = false;
		lightness = 0;
	}

	private final void readOpcodes(InputStream stream, int opcode) {
    	if (opcode == 1) {
    		int size = stream.readUnsignedByte();
    		if (size > 0) {
    			if (models == null) {
    				models = new int[size];
    				types = new int[size];
    				for (int id = 0; id < size; id++) {
    					models[id] = stream.readUnsignedShort();
    					types[id] = stream.readUnsignedByte();
    				}
    			} else
    				stream.offset += size * 3;
    		}
		}
    	if (opcode == 2) {
    		name = stream.readString();
    	}
    	if (opcode == 5) {
    		int size = stream.readUnsignedByte();
    		if (size > 0) {
    			if (models == null) {
    				types = null;
    				models = new int[size];
    				for (int id = 0; id < size; id++)
    					models[id] = stream.readUnsignedShort();
    			} else
    				stream.offset += size * 2;
    		}
    	}
    	if (opcode == 14) {
    		sizeX = stream.readUnsignedByte();
    	}
    	if (opcode == 15) {
    		sizeY = stream.readUnsignedByte();
    	}
		if (opcode == 17) {
			clipType = 0;
			projectileCliped = false;
		    isSolid = false;
		    isWalkable = false;
		}
		if (opcode == 18) {
		    isWalkable = false;
    	}
    	if (opcode == 19) {
		    hasActions = stream.readUnsignedByte();
    	}
    	if (opcode == 21) {
		    aBoolean1654 = true;
    	}
    	if (opcode == 22) {
		    aBoolean1648 = true;
    	}
    	if (opcode == 23) {
    		aBoolean1677 = true;
    	}
    	if (opcode == 24) {
			animationId = stream.readUnsignedShort();
			if (animationId == 65535) {
			    animationId = -1;
		    }
    	}
    	if (opcode == 27) {
    		clipType = 1;
    		isSolid = true;
    	}
    	if (opcode == 28) {
		    anInt1673 = stream.readUnsignedByte();
    	}
    	if (opcode == 29) {
			lightness = stream.readByte();
    	}
    	if (opcode == 39) {
    		shading = stream.readByte() * 5;
    	}
    	if (opcode >= 30 && opcode < 35) {
    		actions[opcode - 30] = stream.readString();
    		if (actions[opcode - 30].equalsIgnoreCase("hidden"))
    			actions[opcode - 30] = null;
    	}
    	if (opcode == 40) {
    		int i_34_ = stream.readUnsignedByte();
    		modifiedModelColors = new int[i_34_];
    		originalModelColors = new int[i_34_];
    		for (int i_35_ = 0; i_34_ > i_35_; i_35_++) {
    			originalModelColors[i_35_] = stream.readUnsignedShort();
    			modifiedModelColors[i_35_] = stream.readUnsignedShort();
    		}
    	}
    	if (opcode == 60) {
    		groundDecorationSprite = stream.readUnsignedShort();
    	}
    	if (opcode == 62) {
		    aBoolean1660 = true;
    	}
    	if (opcode == 64) {
			aBoolean1666 = false;
    	}
    	if (opcode == 65) {
    		scaleX = stream.readUnsignedShort();
    	}
    	if (opcode == 66) {
		    scaleY = stream.readUnsignedShort();
    	}
    	if (opcode == 67) {
    		scaleZ = stream.readUnsignedShort();
    	}
    	if (opcode == 68) {
			mapSceneSprite = stream.readUnsignedShort();
    	}	
    	if (opcode == 69) {
    		anInt1668 = stream.readUnsignedByte();
    	}
    	if (opcode == 70) {
		    anInt1644 = stream.readShort();
    	}
    	if (opcode == 71) {
			anInt1670 = stream.readShort();
    	}
    	if (opcode == 72) {
		    anInt1642 = stream.readShort();
    	}
    	if (opcode == 73) {
			aBoolean1665 = true;
    	}	
		if (opcode == 74) {
			notCliped = true;
			aBoolean1674 = true;
		}
		if (opcode == 75) {
			anInt1671 = stream.readUnsignedByte();
		}
		if (opcode == 77) {
			anInt1641 = stream.readUnsignedShort();
			if (anInt1641 == 65535)
				anInt1641 = -1;
			anInt1658 = stream.readUnsignedShort();
			if (anInt1658 == 65535)
				anInt1658 = -1;
			int i_36_ = stream.readUnsignedByte();
			childrenIDs = new int[i_36_+ 1];
			for (int i_37_ = 0; (i_37_ <= i_36_); i_37_++) {
				childrenIDs[i_37_] = stream.readUnsignedShort();
				if (childrenIDs[i_37_] == 65535)
					childrenIDs[i_37_] = -1;
			}
		}
		if(opcode == 78) {
			stream.readUnsignedShort();
			stream.readByte();
		}
		if(opcode == 79) {
			stream.readUnsignedShort();
			stream.readUnsignedShort();
			stream.readByte();
			int wut = stream.readByte();
			for(int i = 0; i < wut; i++)
				stream.readUnsignedShort();
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
	
	private Location location;

	public boolean notCliped;
	
	
	public Location getLocation() {
		return location;
	}
	
	public static boolean isProjectileCliped() {
		return projectileCliped;
	}

    public boolean hasActions() {
    	ObjectDef def = ObjectDef.forId(id);
    	for(int i = 0; i < actions.length; i++) {
    		if(actions[i] == null)
    			continue;
    		if ((def.name != null && (def.name.equalsIgnoreCase("bank booth") || def.name.equalsIgnoreCase("counter")))) {
    			System.out.println("Readoing booth");
				def.clipType = 0;
				ObjectDef.projectileCliped = true;
				if (def.clipType == 0)
					def.clipType = 1;
    		}
    		if (def.notCliped) {
				ObjectDef.projectileCliped = false;
				def.clipType = 0;
			}
    		if(!actions[i].equalsIgnoreCase("null") || !actions[i].equalsIgnoreCase("hidden"))
    			return true;
    	}
    	return false;
    }

    public int getBiggestSize() {
    	if(sizeY > sizeX) {
    		return sizeY;
    	}
    	return sizeX;
    }


}
