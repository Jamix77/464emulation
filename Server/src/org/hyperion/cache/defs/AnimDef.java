package org.hyperion.cache.defs;

import org.hyperion.cache.Cache;
import org.hyperion.cache.stream.InputStream;

public final class AnimDef {

	public static final AnimDef[] animDefinitions = new AnimDef[Cache.getCacheFileManagers()[2].getFilesSize(12)];

	public int id;
	public int shieldDisplayed;
	public int speedupType;
	public int[] anIntArray1565;
	public int priority = 5;
	public int walkProperties;
	public int anInt1570;
	public int resetTime = 99;
    public int[] frames;
    public int[] frames2;
    public int resetInPlay = 2;
    public int[] timer;
    public int weaponDisplayed;
    public boolean aBoolean1593;

	public static final AnimDef forId(int animId) {
		if (animId < 0 || animId >= animDefinitions.length)
			return null;
		AnimDef def = animDefinitions[animId];
		if (def == null)
			animDefinitions[animId] = def = new AnimDef(animId);
		return def;
	}

	private AnimDef(int id) {
		this.id = id;
		setDefaultsVariableValues();
		loadAnimDefinitions();
	}

	private final void loadAnimDefinitions() {
		byte[] data = Cache.getCacheFileManagers()[2].getFileData(12, id);
		if (data == null) {
			System.out.println("Failed loading anim " + id + ".");
			return;
		}
		readOpcodes(new InputStream(data));
		applyProperties();
	}
	
    final void applyProperties() {
    	if (walkProperties == -1) {
    		if (anIntArray1565 == null)
    			walkProperties = 0;
    		else
    			walkProperties = 2;
    	}
    	if (speedupType == -1) {
    		if (anIntArray1565 != null)
    			speedupType = 2;
    		else
    			speedupType = 0;
    	}
    }

	private void setDefaultsVariableValues() {
		speedupType = -1;
		shieldDisplayed = -1;
		anInt1570 = -1;
		weaponDisplayed = -1;
		walkProperties = -1;
		aBoolean1593 = false;
	}

	private final void readOpcodes(InputStream stream, int opcode) {
	   	if (opcode == 1) {
    		int len = stream.readUnsignedByte();
    		timer = new int[len];
    		for (int id = 0; len > id; id++)
    			timer[id] = stream.readUnsignedShort();
    		frames = new int[len];
    		for (int id = 0; len > id; id++)
    			frames[id] = stream.readUnsignedShort();
    		for (int id = 0; id < len; id++)
    			frames[id] = ((stream.readUnsignedShort() << 16) + frames[id]);
    	}
    	if (opcode == 2) {
    		anInt1570 = stream.readUnsignedShort();
    	}
    	if (opcode == 3) {
    		int i_9_ = stream.readUnsignedByte();
    		anIntArray1565 = new int[i_9_ + 1];
    		for (int i_10_ = 0; i_10_ < i_9_; i_10_++)
    			anIntArray1565[i_10_] = stream.readUnsignedByte();
    		anIntArray1565[i_9_] = 9999999;
    	}
    	if (opcode == 4) {
    		aBoolean1593 = true;
    	}
    	if (opcode == 5) {
    	    priority = stream.readUnsignedByte();
    	}
	    if (opcode == 6) {
			shieldDisplayed = stream.readUnsignedShort();
	    }
		if (opcode == 7) {
		    weaponDisplayed = stream.readUnsignedShort();
		}
		if (opcode == 8) {
			resetTime = stream.readUnsignedByte();
		}
		if (opcode == 9) {
			speedupType = stream.readUnsignedByte();
		}
		if (opcode == 10) {
		    walkProperties = stream.readUnsignedByte();
		}
		if (opcode == 11) {
			resetInPlay = stream.readUnsignedByte();
		}
     	if (opcode == 12) {
     		int len = stream.readUnsignedByte();
     		frames2 = new int[len];
     		for (int id = 0; id < len; id++)
     			frames2[id] = stream.readUnsignedShort();
     		for (int id = 0; id < len; id++)
     			frames2[id] = ((stream.readUnsignedShort() << 16) + frames2[id]);
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

}
