package org.hyperion.rs2.clipping;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.hyperion.cache.Cache;
import org.hyperion.cache.stream.InputStream;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.World;

public class WorldMapObjectsLoader {

	public static Logger log = Logger.getAnonymousLogger();

	public static void loadMaps(int region) {
		try {
			loadRegionMap(region);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}

	private static Map<Integer, Boolean> activeMaps = new HashMap<Integer, Boolean>();

	public static void loadRegionMap(int regionId) throws Exception {
		if(activeMaps.get(regionId) != null)
			return;
		activeMaps.put(regionId, Boolean.TRUE);
		int[] mapData = World.getWorld().mapdata.getData(regionId);
		int regionX = (regionId >> 8) * 64;
		int regionY = (regionId & 0xff) * 64;

		byte[] landContainerData = Cache.getCacheFileManagers()[5].getFileData(Cache.getCacheFileManagers()[5].getContainerId("l"+ ((regionX >> 3) / 8) +"_"+ ((regionY >> 3) / 8)), 0, mapData);

		int mapArchiveId = Cache.getCacheFileManagers()[5].getContainerId("m" + ((regionX >> 3) / 8) + "_" + ((regionY >> 3) / 8));


		byte[] mapContainerData = Cache.getCacheFileManagers()[5].getFileData(Cache.getCacheFileManagers()[5].getContainerId("m"+ ((regionX >> 3) / 8) +"_"+ ((regionY >> 3) / 8)), 0);
		byte[][][] mapSettings = new byte[4][64][64];
		System.out.println(mapContainerData);
		if(mapArchiveId != -1) {
			InputStream mapStream = new InputStream(mapContainerData);

			for (int plane = 0; plane < 4; plane++) {
				for (int x = 0; x < 64; x++) {
					for (int y = 0; y < 64; y++) {
						while (true) {
							 int v = mapStream.readByte() & 0xff;
                             if (v == 0) {
                                 break;
                             } else if (v == 1) {
                            	 mapStream.readByte();
                                 break;
                             } else if (v <= 49) {
                            	 mapStream.readByte();
                             } else if (v <= 81) {
                            	 mapSettings[plane][x][y] = (byte) (v - 49);
                             }
						}
					}
				}
			}

		}
		for (int plane = 0; plane < 4; plane++) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++) {
					 if ((mapSettings[plane][x][y] & 1) == 1) {
                         int height = plane;
                         if ((mapSettings[1][x][y] & 2) == 2) {
                             height--;
                         }
                         int absX = x+regionX;
                         int absY = y+regionY;
                         if (height >= 0 && height <= 3) {
                        	 RegionClipping.addClipping(absX, absY, plane, 0x200000);
                         }
                     }
				}
			}
		}

		if (landContainerData != null) {
			InputStream landStream = new InputStream(landContainerData);
			int objectId = -1;
			int incr;

			while ((incr = landStream.readSmart2()) != 0) {
				objectId += incr;
				int location = 0;
				int incr2;
				while ((incr2 = landStream.readUnsignedSmart()) != 0) {
					location += incr2 - 1;
					int localX = (location >> 6 & 0x3f);
					int localY = (location & 0x3f);
					int plane = location >> 12;
					int objectData = landStream.readUnsignedByte();
					int type = objectData >> 2;
					int rotation = objectData & 0x3;
					if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64)
						continue;
					if (mapSettings != null&& (mapSettings[1][localX][localY] & 2) == 2)
						plane--;
					if (plane >= 0 && plane <= 3) {
						Location loc = Location.create(localX + regionX, localY + regionY, plane);
						World.getWorld().register(new GameObject(loc, objectId, type, rotation, true));
					}
				}
			}
		}
	}
}
