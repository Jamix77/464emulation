package org.hyperion.rs2.clipping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hyperion.cache.defs.ObjectDef;
import org.hyperion.rs2.model.GameObject;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Mob;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;

@SuppressWarnings({"unchecked"})
public class RegionClipping {

	public static final int PLAYERS = 0, NPCS = 1;

	public static final int REGION_SIZE = 128;

	public static final int MAX_MAP_X = 16383, MAX_MAP_Y = 16383;

	private static RegionClipping[][] regions = new RegionClipping[(MAX_MAP_X + 1) / REGION_SIZE][(MAX_MAP_Y + 1) / REGION_SIZE];
	private static List<RegionClipping> loadedRegions = new ArrayList<RegionClipping>();

	public static int hash(int x, int y) {
		return x >> 7 << 8 | y >> 7;
	}

	public static void addClipping(int x, int y, int z, int shift) {
		RegionClipping region = forCoords(x, y);
		int localX = x - ((x >> 7) << 7);
		int localY = y - ((y >> 7) << 7);
		if (region.clippingMasks[z] == null) {
			region.clippingMasks[z] = new int[region.size][region.size];
		}
		region.clipped = true;
		region.clippingMasks[z][localX][localY] |= shift;
	}

	public static void setClippingMask(int x, int y, int z, int shift) {
		RegionClipping region = forCoords(x, y);
		int localX = x - ((x >> 7) << 7);
		int localY = y - ((y >> 7) << 7);
		if (region.clippingMasks[z] == null) {
			region.clippingMasks[z] = new int[region.size][region.size];
		}
		region.clipped = true;
		region.clippingMasks[z][localX][localY] = shift;
	}

	public static void removeClipping(int x, int y, int z, int shift) {
		RegionClipping region = forCoords(x, y);
		int localX = x - ((x >> 7) << 7);
		int localY = y - ((y >> 7) << 7);
		if (region.clippingMasks[z] == null) {
			region.clippingMasks[z] = new int[region.size][region.size];
		}
		region.clippingMasks[z][localX][localY] &= ~shift;
	}

	public static RegionClipping forCoords(int x, int y) {
		int regionX = x >> 7, regionY = y >> 7;
		RegionClipping r = regions[regionX][regionY];
		if (r == null) {
			r = regions[regionX][regionY] = new RegionClipping(regionX, regionY, REGION_SIZE);
		}
		return r;
	}

	public static RegionClipping forLocation(Location other) {
		return forCoords(other.getX(), other.getY());
	}

	public static int getClippingMask(int x, int y, int z) {
		RegionClipping region = forCoords(x, y);
		if (region.clippingMasks[z] == null || !region.clipped) {
			return -1;
		}
		int localX = x - ((x >> 7) << 7);
		int localY = y - ((y >> 7) << 7);
		return region.clippingMasks[z][localX][localY];
	}

	public static RegionClipping getRegion(int x, int y, int z) {
		return forCoords(x, y);
	}

	private int[][][] clippingMasks = new int[4][][];
	private int size;
	private int x;
	private int y;

	public Set<Player>[] players = new HashSet[4];
	public Set<NPC>[] npcs = new HashSet[4];

	private boolean clipped;

	public RegionClipping(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		for (int i = 0; i < 4; i++) {
			players[i] = new HashSet<Player>();
			npcs[i] = new HashSet<NPC>();
		}
		loadedRegions.add(this);
	}

	public int getSize() {
		return size;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return x << 8 | y;
	}

	public void setClipped(boolean clipped) {
		this.clipped = clipped;
	}

	public boolean isClipped() {
		return clipped;
	}

	public static void addClipping(GameObject obj) {
		ObjectDef def = obj.getDefinition();
		int sizeX = 1;
		int sizeY = 1;
		int clipType = 2;
		boolean aBoolean1674 = false;
		int x = obj.getLocation().getX();
		int y = obj.getLocation().getY();
		int height = obj.getLocation().getZ();
		int xLength;
		int yLength;
		if (obj.getDirection() != 1 && obj.getDirection()  != 3) {
			xLength = def == null ? sizeX : def.sizeX;
			yLength = def == null ? sizeY : def.sizeY;
		} else {
			xLength = def == null ? sizeY : def.sizeY;
			yLength = def == null ? sizeX : def.sizeX;
		}
		int type = obj.getType();
		if (type == 22) {
			if (def == null ? clipType == 1 : def.clipType == 1) {
				addClipping(x, y, height, 0x200000);
			}
		} else if (type >= 9 && type <= 11) {
			if (def == null ? clipType != 0 : def.clipType != 0) {
				addClippingForSolidObject(x, y, height, xLength, yLength, def == null ? aBoolean1674 : def.aBoolean1674);
			}
		} else if (type >= 0 && type <= 3) {
			if (def == null ? clipType != 0 : def.clipType != 0) {
				addClippingForVariableObject(x, y, height, type, obj.getDirection(),  def == null ? aBoolean1674 : def.aBoolean1674);
			}
		}
	}

	public static void removeClipping(GameObject obj) {
		ObjectDef def = obj.getDefinition();
		int xLength;
		int yLength;
		int x = obj.getLocation().getX();
		int y = obj.getLocation().getY();
		int height = obj.getLocation().getZ();
		if (obj.getDirection() != 1 && obj.getDirection() != 3) {
			xLength = def.sizeX;
			yLength = def.sizeY;
		} else {
			xLength = def.sizeY;
			yLength = def.sizeX;
		}
		if (obj.getType() == 22) {
			if (def.clipType == 1) {
				removeClipping(x, y, height, 0x200000);
			}
		} else if (obj.getType() >= 9 && obj.getType() <= 11) {
			if (def.clipType != 0) {
				removeClippingForSolidObject(x, y, height, xLength, yLength, def.aBoolean1674);
			}
		} else if (obj.getType() >= 0 && obj.getType() <= 3) {
			if (def.clipType != 0) {
				removeClippingForVariableObject(x, y, height, obj.getType(), obj.getDirection(), def.aBoolean1674);
			}
		}
	}

	private static void addClippingForSolidObject(int x, int y, int height, int xLength, int yLength, boolean flag) {
		int clipping = 256;
		if (flag) {
			clipping |= 0x20000;
		}
		for (int i = x; i < x + xLength; i++) {
			for (int i2 = y; i2 < y + yLength; i2++) {
				addClipping(i, i2, height, clipping);
			}
		}
	}

	private static void removeClippingForSolidObject(int x, int y, int height, int xLength, int yLength, boolean flag) {
		int clipping = 256;
		if (flag) {
			clipping |= 0x20000;
		}
		for (int i = x; i < x + xLength; i++) {
			for (int i2 = y; i2 < y + yLength; i2++) {
				removeClipping(i, i2, height, clipping);
			}
		}
	}

	private static void addClippingForVariableObject(int x, int y, int z, int type, int direction, boolean flag) {
		if (type == 0) {
			if (direction == 0) {
				addClipping(x, y, z, 128);
				addClipping(x - 1, y, z, 8);
			}
			if (direction == 1) {
				addClipping(x, y, z, 2);
				addClipping(x, y + 1, z, 32);
			}
			if (direction == 2) {
				addClipping(x, y, z, 8);
				addClipping(x + 1, y, z, 128);
			}
			if (direction == 3) {
				addClipping(x, y, z, 32);
				addClipping(x, y - 1, z, 2);
			}
		}
		if (type == 1 || type == 3) {
			if (direction == 0) {
				addClipping(x, y, z, 1);
				addClipping(x - 1, y + 1, z, 16);
			}
			if (direction == 1) {
				addClipping(x, y, z, 4);
				addClipping(x + 1, y + 1, z, 64);
			}
			if (direction == 2) {
				addClipping(x, y, z, 16);
				addClipping(x + 1, y - 1, z, 1);
			}
			if (direction == 3) {
				addClipping(x, y, z, 64);
				addClipping(x - 1, y - 1, z, 4);
			}
		}
		if (type == 2) {
			if (direction == 0) {
				addClipping(x, y, z, 130);
				addClipping(x - 1, y, z, 8);
				addClipping(x, y + 1, z, 32);
			}
			if (direction == 1) {
				addClipping(x, y, z, 10);
				addClipping(x, y + 1, z, 32);
				addClipping(x + 1, y, z, 128);
			}
			if (direction == 2) {
				addClipping(x, y, z, 40);
				addClipping(x + 1, y, z, 128);
				addClipping(x, y - 1, z, 2);
			}
			if (direction == 3) {
				addClipping(x, y, z, 160);
				addClipping(x, y - 1, z, 2);
				addClipping(x - 1, y, z, 8);
			}
		}
		if (flag) {
			if (type == 0) {
				if (direction == 0) {
					addClipping(x, y, z, 0x10000);
					addClipping(x - 1, y, z, 4096);
				}
				if (direction == 1) {
					addClipping(x, y, z, 1024);
					addClipping(x, y + 1, z, 16384);
				}
				if (direction == 2) {
					addClipping(x, y, z, 4096);
					addClipping(x + 1, y, z, 0x10000);
				}
				if (direction == 3) {
					addClipping(x, y, z, 16384);
					addClipping(x, y - 1, z, 1024);
				}
			}
			if (type == 1 || type == 3) {
				if (direction == 0) {
					addClipping(x, y, z, 512);
					addClipping(x - 1, y + 1, z, 8192);
				}
				if (direction == 1) {
					addClipping(x, y, z, 2048);
					addClipping(x + 1, y + 1, z, 32768);
				}
				if (direction == 2) {
					addClipping(x, y, z, 8192);
					addClipping(x + 1, y - 1, z, 512);
				}
				if (direction == 3) {
					addClipping(x, y, z, 32768);
					addClipping(x - 1, y - 1, z, 2048);
				}
			}
			if (type == 2) {
				if (direction == 0) {
					addClipping(x, y, z, 0x10400);
					addClipping(x - 1, y, z, 4096);
					addClipping(x, y + 1, z, 16384);
				}
				if (direction == 1) {
					addClipping(x, y, z, 5120);
					addClipping(x, y + 1, z, 16384);
					addClipping(x + 1, y, z, 0x10000);
				}
				if (direction == 2) {
					addClipping(x, y, z, 20480);
					addClipping(x + 1, y, z, 0x10000);
					addClipping(x, y - 1, z, 1024);
				}
				if (direction == 3) {
					addClipping(x, y, z, 0x14000);
					addClipping(x, y - 1, z, 1024);
					addClipping(x - 1, y, z, 4096);
				}
			}
		}
	}
	public static boolean blockedNorth(Location loc) {
		return (getClippingMask(loc.getX(), loc.getY() + 1, loc.getZ()) & 0x1280120) != 0;
	}
	
	public static boolean blockedEast(Location loc) {
		return (getClippingMask(loc.getX() + 1, loc.getY(), loc.getZ()) & 0x1280180) != 0;
	}
	
	public static boolean blockedSouth(Location loc) {
		return (getClippingMask(loc.getX(), loc.getY() - 1, loc.getZ()) & 0x1280102) != 0;
	}
	
	public static boolean blockedWest(Location loc) {
		return (getClippingMask(loc.getX() - 1, loc.getY(), loc.getZ()) & 0x1280108) != 0;
	}
	
	public static boolean blockedNorthEast(Location loc) {
		return (getClippingMask(loc.getX() + 1, loc.getY() + 1, loc.getZ()) & 0x12801E0) != 0;
	}
	
	public static boolean blockedNorthWest(Location loc) {
		return (getClippingMask(loc.getX() - 1, loc.getY() + 1, loc.getZ()) & 0x1280138) != 0;
	}
	
	public static boolean blockedSouthEast(Location loc) {
		return (getClippingMask(loc.getX() + 1, loc.getY() - 1, loc.getZ()) & 0x1280183) != 0;
	}
	
	public static boolean blockedSouthWest(Location loc) {
		return (getClippingMask(loc.getX() - 1, loc.getY() - 1, loc.getZ()) & 0x128010E) != 0;
	}
	public static boolean projectileBlocked(Mob mob) {
		Mob target = mob.getInteractingEntity();
		if (target == null) {
			return true;
		}
		Location pointA = mob.getCentreLocation();
		Location pointB = target.getCentreLocation();

		double offsetX = Math.abs(pointA.getX() - pointB.getX());
		double offsetY = Math.abs(pointA.getY() - pointB.getY());
		
		
		int xDis = Math.abs(pointA.getX() - pointB.getX());
		int yDis = Math.abs(pointA.getY() - pointB.getY());
		int distance =  xDis > yDis ? xDis : yDis;
		distance = distance > 1 ? distance - 1 : distance;
	
		
		if (distance == 0) {
			return true;
		}
		offsetX = offsetX > 0 ? offsetX / distance : 0;
		offsetY = offsetY > 0 ? offsetY / distance : 0;

		int[][] path = new int[distance][5];
		
		int curX = pointA.getX();
		int curY = pointA.getY();
		int next = 0;
		int nextMoveX = 0;
		int nextMoveY = 0;
		
		double currentTileXCount = 0.0;
		double currentTileYCount = 0.0;

		while(distance > 0) {
			distance--;
			nextMoveX = 0;
			nextMoveY = 0;
			if (curX > pointB.getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX--;
					curX--;	
					currentTileXCount -= offsetX;
				}		
			} else if (curX < pointB.getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX++;
					curX++;
					currentTileXCount -= offsetX;
				}
			}
			if (curY > pointB.getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY--;
					curY--;	
					currentTileYCount -= offsetY;
				}	
			} else if (curY < pointB.getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY++;
					curY++;
					currentTileYCount -= offsetY;
				}
			}
			path[next][0] = curX;
			path[next][1] = curY;
			path[next][2] = mob.getLocation().getZ();
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;	
		}
		for (int i = 0; i < path.length; i++) {
			if (!RegionClipping.getClipping(path[i][0], path[i][1], path[i][2], path[i][3], path[i][4])) {// && RegionClipping.getSingleton().getClipping(path[i][0], path[i][1], path[i][2] & 0x20000) != 0) {
				return true;	
			}
		}
		return false;
	}
	 public static boolean getClipping(int x, int y, int height, int moveTypeX, int moveTypeY) {
	        try {
	            if (height > 3) {
	                height = 0;
	            }
	            int checkX = x + moveTypeX;
	            int checkY = y + moveTypeY;
	            if ((moveTypeX == -1) && (moveTypeY == 0))
	                return (getClippingMask(x, y, height) & 0x1280108) == 0;
	            if ((moveTypeX == 1) && (moveTypeY == 0))
	                return (getClippingMask(x, y, height) & 0x1280180) == 0;
	            if ((moveTypeX == 0) && (moveTypeY == -1))
	                return (getClippingMask(x, y, height) & 0x1280102) == 0;
	            if ((moveTypeX == 0) && (moveTypeY == 1))
	                return (getClippingMask(x, y, height) & 0x1280120) == 0;
	            if ((moveTypeX == -1) && (moveTypeY == -1))
	                return ((getClippingMask(x, y, height) & 0x128010E) == 0) && ((getClippingMask(checkX - 1, checkY, height) & 0x1280108) == 0) && ((getClippingMask(checkX, checkY - 1, height) & 0x1280102) == 0);
	            if ((moveTypeX == 1) && (moveTypeY == -1))
	                return ((getClippingMask(x, y, height) & 0x1280183) == 0) && ((getClippingMask(checkX + 1, checkY, height) & 0x1280180) == 0) && ((getClippingMask(checkX, checkY - 1, height) & 0x1280102) == 0);
	            if ((moveTypeX == -1) && (moveTypeY == 1))
	                return ((getClippingMask(x, y, height) & 0x1280138) == 0) && ((getClippingMask(checkX - 1, checkY, height) & 0x1280108) == 0) && ((getClippingMask(checkX, checkY + 1, height) & 0x1280120) == 0);
	            if ((moveTypeX == 1) && (moveTypeY == 1)) {
	                return ((getClippingMask(x, y, height) & 0x12801E0) == 0) && ((getClippingMask(checkX + 1, checkY, height) & 0x1280180) == 0) && ((getClippingMask(checkX, checkY + 1, height) & 0x1280120) == 0);
	            }
	            return false;
	        } catch (Exception e) {
	        }
	        return true;
	    }
	 public static boolean canRange(Location start, Location end) {
	    	int x = start.getX();
	    	int y = start.getY();
	    	int x2 = end.getX();
	    	int y2 = end.getY();
	    	@SuppressWarnings("unused")
			int z = start.getZ();
	    	int w = x2 - x;
	    	int h = y2 - y;
	    	int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
	    	if (w < 0)
	    		dx1 = -1;
	    	else if (w > 0)
	    		dx1 = 1;
	    	if (h < 0)
	    		dy1 = -1;
	    	else if (h > 0)
	    		dy1 = 1;
	    	if (w < 0)
	    		dx2 = -1;
	    	else if (w > 0)
	    		dx2 = 1;
	    	int longest = Math.abs(w);
	    	int shortest = Math.abs(h);
	    	if (!(longest > shortest)) {
	    		longest = Math.abs(h);
	    		shortest = Math.abs(w);
	    		if (h < 0)
	    		    dy2 = -1;
	    		else if (h > 0)
	    			dy2 = 1;
	    		dx2 = 0;
	    	}
	    	int numerator = longest >> 1;
	        for (int i = 0; i <= longest; i++) {
	        	numerator += shortest;
	        	if (!(numerator < longest)) {
	        		numerator -= longest;
	        		x += dx1;
	        		y += dy1;
	        	} else {
	        		x += dx2;
	        		y += dy2;
	        	}
	        	// Check the rotation using the last x and y
	        	/*LinkedList<GameObject> objects = null;
	        	for (GameObject object : objects) {
	        		int type = object.getType();
	        		if (type >= 0 && type <= 2) {
//	        			/return false;
	        		} 
	        	}
	        	//System.out.println(getClipping(x, y, z));
	        	if(getClipping(x, y, z) >= 1 << 16)
	        		return false;
	        	if (x == x2 && y == y2) {
	        		return true;
	        	}*/
	        }
	        return true;
	    }
	public static void removeClippingForVariableObject(int x, int y, int z, int type, int direction, boolean flag) {
		if (type == 0) {
			if (direction == 0) {
				removeClipping(x, y, z, 128);
				removeClipping(x - 1, y, z, 8);
			}
			if (direction == 1) {
				removeClipping(x, y, z, 2);
				removeClipping(x, 1 + y, z, 32);
			}
			if (direction == 2) {
				removeClipping(x, y, z, 8);
				removeClipping(1 + x, y, z, 128);
			}
			if (direction == 3) {
				removeClipping(x, y, z, 32);
				removeClipping(x, y - 1, z, 2);
			}
		}
		if (type == 1 || type == 3) {
			if (direction == 0) {
				removeClipping(x, y, z, 1);
				removeClipping(x - 1, 1 + y, z, 16);
			}
			if (direction == 1) {
				removeClipping(x, y, z, 4);
				removeClipping(1 + x, y + 1, z, 64);
			}
			if (direction == 2) {
				removeClipping(x, y, z, 16);
				removeClipping(x + 1, -1 + y, z, 1);
			}
			if (direction == 3) {
				removeClipping(x, y, z, 64);
				removeClipping(-1 + x, -1 + y, z, 4);
			}
		}
		if (type == 2) {
			if (direction == 0) {
				removeClipping(x, y, z, 130);
				removeClipping(x - 1, y, z, 8);
				removeClipping(x, 1 + y, z, 32);
			}
			if (direction == 1) {
				removeClipping(x, y, z, 10);
				removeClipping(x, 1 + y, z, 32);
				removeClipping(1 + x, y, z, 128);
			}
			if (direction == 2) {
				removeClipping(x, y, z, 40);
				removeClipping(x + 1, y, z, 128);
				removeClipping(x, -1 + y, z, 2);
			}
			if (direction == 3) {
				removeClipping(x, y, z, 160);
				removeClipping(x, y - 1, z, 2);
				removeClipping(-1 + x, y, z, 8);
			}
		}
		if (flag) {
			if (type == 0) {
				if (direction == 0) {
					removeClipping(x, y, z, 0x10000);
					removeClipping(-1 + x, y, z, 4096);
				}
				if (direction == 1) {
					removeClipping(x, y, z, 1024);
					removeClipping(x, 1 + y, z, 16384);
				}
				if (direction == 2) {
					removeClipping(x, y, z, 4096);
					removeClipping(x + 1, y, z, 0x10000);
				}
				if (direction == 3) {
					removeClipping(x, y, z, 16384);
					removeClipping(x, y - 1, z, 1024);
				}
			}
			if (type == 1 || type == 3) {
				if (direction == 0) {
					removeClipping(x, y, z, 512);
					removeClipping(-1 + x, 1 + y, z, 8192);
				}
				if (direction == 1) {
					removeClipping(x, y, z, 2048);
					removeClipping(1 + x, 1 + y, z, 32768);
				}
				if (direction == 2) {
					removeClipping(x, y, z, 8192);
					removeClipping(x + 1, -1 + y, z, 512);
				}
				if (direction == 3) {
					removeClipping(x, y, z, 32768);
					removeClipping(x - 1, -1 + y, z, 2048);
				}
			}
			if (type == 2) {
				if (direction == 0) {
					removeClipping(x, y, z, 0x10400);
					removeClipping(-1 + x, y, z, 4096);
					removeClipping(x, y + 1, z, 16384);
				}
				if (direction == 1) {
					removeClipping(x, y, z, 5120);
					removeClipping(x, 1 + y, z, 16384);
					removeClipping(x + 1, y, z, 0x10000);
				}
				if (direction == 2) {
					removeClipping(x, y, z, 20480);
					removeClipping(1 + x, y, z, 0x10000);
					removeClipping(x, -1 + y, z, 1024);
				}
				if (direction == 3) {
					removeClipping(x, y, z, 0x14000);
					removeClipping(x, -1 + y, z, 1024);
					removeClipping(-1 + x, y, z, 4096);
				}
			}
		}
	}

	public int getClippingFlag(int localX, int localY, int height) {
		return clippingMasks[localX][localY][height];
	}

	public static RegionClipping getRegion(int realRegionId) {

		return null;
	}

	public static boolean isPassable(Location l) {
		int clippingMask = getClippingMask(l.getX(), l.getY(), l.getZ());
		if (clippingMask == -1) {
			return true; //?
		}
		return clippingMask < 1; /*(clippingMask & 0x1280180) == 0 && (clippingMask & 0x1280108) == 0
		&& (clippingMask & 0x1280120) == 0 && (clippingMask & 0x1280102) == 0;*/
	}

	public static List<RegionClipping> getRegions() {
		return loadedRegions;
	}

}