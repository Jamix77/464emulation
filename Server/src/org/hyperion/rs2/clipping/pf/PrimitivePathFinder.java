package org.hyperion.rs2.clipping.pf;

import java.awt.Point;

import org.hyperion.rs2.clipping.BasicPoint;
import org.hyperion.rs2.clipping.Directions;
import org.hyperion.rs2.clipping.RegionClipping;
import org.hyperion.rs2.clipping.Directions.NormalDirection;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Mob;

public class PrimitivePathFinder implements PathFinder {

	public static PrimitivePathFinder INSTANCE = new PrimitivePathFinder();
	
	public static Point getNextStep(Location source, int toX, int toY, int height, int xLength, int yLength) {
    	int baseX = source.getLocalX(), baseY = source.getLocalY();
        int moveX = 0;
        int moveY = 0;
        if (baseX - toX > 0) {
            moveX--;
        } else if (baseX - toX < 0) {
            moveX++;
        }
        if (baseY - toY > 0) {
            moveY--;
        } else if (baseY - toY < 0) {
            moveY++;
        }
        if (canMove(source, baseX, baseY, baseX + moveX, baseY + moveY, height, xLength, yLength)) {
            return new Point(baseX + moveX, baseY + moveY);
        } else if (moveX != 0 && canMove(source, baseX, baseY, baseX + moveX, baseY, height, xLength, yLength)) {
            return new Point(baseX + moveX, baseY);
        } else if (moveY != 0 && canMove(source, baseX, baseY, baseX, baseY + moveY, height, xLength, yLength)) {
            return new Point(baseX, baseY + moveY);
        }
        return null;
    }
    
    public static boolean canNextStep(Location source, int toX, int toY, int height, int xLength, int yLength) {
    	int baseX = source.getLocalX(), baseY = source.getLocalY();
        int moveX = 0;
        int moveY = 0;
        if (baseX - toX > 0) {
            moveX--;
        } else if (baseX - toX < 0) {
            moveX++;
        }
        if (baseY - toY > 0) {
            moveY--;
        } else if (baseY - toY < 0) {
            moveY++;
        }
        if (canMove(source, baseX, baseY, baseX + moveX, baseY + moveY, height, xLength, yLength)) {
            return true;
        } else if (moveX != 0 && canMove(source, baseX, baseY, baseX + moveX, baseY, height, xLength, yLength)) {
            return true;
        } else if (moveY != 0 && canMove(source, baseX, baseY, baseX, baseY + moveY, height, xLength, yLength)) {
            return true;
        }
        return false;
    }
    
    
    public static boolean canMove(Location base, int startX, int startY, int endX, int endY, int height, int xLength, int yLength) {
    	Location RSTile = Location.create((base.getRegionX() - 6) << 3, (base.getRegionY() - 6) << 3, base.getZ());
        int diffX = endX - startX;
        int diffY = endY - startY;
        int max = Math.max(Math.abs(diffX), Math.abs(diffY));
        for (int ii = 0; ii < max; ii++) {
            int currentX = RSTile.getX() + (endX - diffX);
            int currentY = RSTile.getY() + (endY - diffY);
            for (int i = 0; i < xLength; i++) {
                for (int i2 = 0; i2 < yLength; i2++) {
                    if (diffX < 0 && diffY < 0) {
                        if ((RegionClipping.getClippingMask(currentX + i - 1, currentY + i2 - 1, height) & 0x128010e) != 0 || (RegionClipping.getClippingMask(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0 || (RegionClipping.getClippingMask(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY > 0) {
                        if ((RegionClipping.getClippingMask(currentX + i + 1, currentY + i2 + 1, height) & 0x12801e0) != 0 || (RegionClipping.getClippingMask(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0 || (RegionClipping.getClippingMask(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX < 0 && diffY > 0) {
                        if ((RegionClipping.getClippingMask(currentX + i - 1, currentY + i2 + 1, height) & 0x1280138) != 0 || (RegionClipping.getClippingMask(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0 || (RegionClipping.getClippingMask(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY < 0) {
                        if ((RegionClipping.getClippingMask(currentX + i + 1, currentY + i2 - 1, height) & 0x1280183) != 0 || (RegionClipping.getClippingMask(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0 || (RegionClipping.getClippingMask(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY == 0) {
                        if ((RegionClipping.getClippingMask(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0) {
                            return false;
                        }
                    } else if (diffX < 0 && diffY == 0) {
                        if ((RegionClipping.getClippingMask(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0) {
                            return false;
                        }
                    } else if (diffX == 0 && diffY > 0) {
                        if ((RegionClipping.getClippingMask(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX == 0 && diffY < 0) {
                        if ((RegionClipping.getClippingMask(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    }
                }
            }
            if (diffX < 0) {
                diffX++;
            } else if (diffX > 0) {
                diffX--;
            }
            if (diffY < 0) {
                diffY++;
            } else if (diffY > 0) {
                diffY--;
            }
        }
        return true;
    }
	
	public static boolean canMove(Location source, Directions.NormalDirection dir) {
		return canMove(source, dir, 1, false);
	}

	public static boolean canMove(Location source, Directions.NormalDirection dir, boolean npcCheck) {
		return canMove(source, dir, 1, npcCheck);
	}

	public static boolean canMove(Location source, Directions.NormalDirection dir, int size, boolean npcCheck) {
		return canMove(source.getX(), source.getY(), source.getZ(), dir, size, npcCheck ? 0x1 : 0);
	}

	public static boolean canMove(int x, int y, int z, NormalDirection dir, int size, int checkType) {
		if (dir == null) {
			return true;
		}
		switch (dir) {
		case WEST:
			for (int k = y; k < y + size; k++) {
				if ((RegionClipping.getClippingMask(x - 1, k, z) & 0x1280108) != 0)
					return false;
			}
			break;
		case EAST:
			for (int k = y; k < y + size; k++) {
				if ((RegionClipping.getClippingMask(x + size, k, z) & 0x1280180) != 0)
					return false;
			}
			break;
		case SOUTH:
			for (int i = x; i < x + size; i++) {
				if ((RegionClipping.getClippingMask(i, y - 1, z) & 0x1280102) != 0)
					return false;
			}
			break;
		case NORTH:
			for (int i = x; i < x + size; i++) {
				if ((RegionClipping.getClippingMask(i, y + size, z) & 0x1280120) != 0)
					return false;
			}
			break;
		case SOUTH_WEST:
			for (int i = x; i < x + size; i++) {
				int s = RegionClipping.getClippingMask(i, y - 1, z);
				int w = RegionClipping.getClippingMask(i - 1, y, z);
				int sw = RegionClipping.getClippingMask(i - 1, y - 1, z);
				if ((sw & 0x128010e) != 0 || (s & 0x1280102) != 0 || (w & 0x1280108) != 0)
					return false;
			}
			for (int k = y; k < y + size; k++) {
				int s = RegionClipping.getClippingMask(x, k - 1, z);
				int w = RegionClipping.getClippingMask(x - 1, k, z);
				int sw = RegionClipping.getClippingMask(x - 1, k - 1, z);
				if ((sw & 0x128010e) != 0 || (s & 0x1280102) != 0 || (w & 0x1280108) != 0)
					return false;
			}
			break;
		case SOUTH_EAST:
			for (int i = x; i < x + size; i++) {
				int s = RegionClipping.getClippingMask(i, y - 1, z);
				int e = RegionClipping.getClippingMask(i + 1, y, z);
				int se = RegionClipping.getClippingMask(i + 1, y - 1, z);
				if ((se & 0x1280183) != 0 || (s & 0x1280102) != 0 || (e & 0x1280180) != 0)
					return false;
			}
			for (int k = y; k < y + size; k++) {
				int s = RegionClipping.getClippingMask(x + size - 1, k - 1, z);
				int e = RegionClipping.getClippingMask(x + size, k, z);
				int se = RegionClipping.getClippingMask(x + size, k - 1, z);
				if ((se & 0x1280183) != 0 || (s & 0x1280102) != 0 || (e & 0x1280180) != 0)
					return false;
			}
			break;
		case NORTH_WEST:
			for (int i = x; i < x + size; i++) {
				int n = RegionClipping.getClippingMask(i, y + size, z);
				int w = RegionClipping.getClippingMask(i - 1, y + size - 1, z);
				int nw = RegionClipping.getClippingMask(i - 1, y + size, z);
				if ((nw & 0x1280138) != 0 || (n & 0x1280102) != 0 || (w & 0x1280108) != 0)
					return false;
			}
			for (int k = y; k < y + size; k++) {
				int n = RegionClipping.getClippingMask(x, y, z);
				int w = RegionClipping.getClippingMask(x - 1, y, z);
				int nw = RegionClipping.getClippingMask(x - 1, y + 1, z);
				if ((nw & 0x1280138) != 0 || (n & 0x1280102) != 0 || (w & 0x1280108) != 0)
					return false;
			}
			break;
		case NORTH_EAST:
			for (int i = x; i < x + size; i++) {
				int n = RegionClipping.getClippingMask(i, y + size, z);
				int e = RegionClipping.getClippingMask(i + 1, y + size - 1, z);
				int ne = RegionClipping.getClippingMask(i + 1, y + size, z);
				if ((ne & 0x12801e0) != 0 || (n & 0x1280120) != 0 || (e & 0x1280180) != 0)
					return false;
			}
			for (int k = y; k < y + size; k++) {
				int n = RegionClipping.getClippingMask(x + size - 1, k + 1, z);
				int e = RegionClipping.getClippingMask(x + size, k, z);
				int ne = RegionClipping.getClippingMask(x + size, k + 1, z);
				if ((ne & 0x12801e0) != 0 || (n & 0x1280120) != 0 || (e & 0x1280180) != 0)
					return false;
			}
			break;
		}
		return true;
	}

	@Override
	public PathState findPath(Mob mob, Location base, int srcX, int srcY, int dstX, int dstY, int z, int radius, boolean running, boolean ignoreLastStep, boolean moveNear) {
		return findPath(mob, base, srcX, srcY, dstX, dstY, z, radius, running, ignoreLastStep, moveNear, false);
	}

	public PathState findPath(Mob mob, Location base, int srcX, int srcY, int dstX, int dstY, int z, int radius, boolean running, boolean ignoreLastStep, boolean moveNear, boolean nullOnFail) {
		if (srcX < 0 || srcY < 0 || srcX >= 104 || srcY >= 104 || dstX < 0 || dstY < 0 || srcX >= 104 || srcY >= 104) {
			return null;
		}
		if (srcX == dstX && srcY == dstY) {
			return null;
		}
		Location location = Location.create((base.getRegionX() - 6) << 3, (base.getRegionY() - 6) << 3, base.getZ());
		Location current = Location.create(location.getX() + srcX, location.getY() + srcY, location.getZ());
		Location end = Location.create(location.getX() + dstX, location.getY() + dstY, location.getZ());
		PathState state = new PathState();
		while (current != end) {
			NormalDirection nextDirection = current.direction(end);
			if (nextDirection != null && canMove(current, nextDirection, mob.getWidth(), false)) {
				current = current.transform(Directions.DIRECTION_DELTA_X[nextDirection.intValue()], Directions.DIRECTION_DELTA_Y[nextDirection.intValue()], 0);
				state.getPoints().add(BasicPoint.create(current.getX(), current.getY(), current.getZ()));
			} else {
				break;
			}
		}
		return state;
	}
}
