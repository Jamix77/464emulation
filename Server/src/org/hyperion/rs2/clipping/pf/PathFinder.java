package org.hyperion.rs2.clipping.pf;

import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Mob;

public interface PathFinder {

    public static final int SOUTH_FLAG = 0x1, WEST_FLAG = 0x2, NORTH_FLAG = 0x4, EAST_FLAG = 0x8;

    public static final int SOUTH_WEST_FLAG = SOUTH_FLAG | WEST_FLAG;
    public static final int NORTH_WEST_FLAG = NORTH_FLAG | WEST_FLAG;
    public static final int SOUTH_EAST_FLAG = SOUTH_FLAG | EAST_FLAG;
    public static final int NORTH_EAST_FLAG = NORTH_FLAG | EAST_FLAG;

    public static final int SOLID_FLAG = 0x20000;
    public static final int UNKNOWN_FLAG = 0x40000000;

    public PathState findPath(Mob mob, Location base, int srcX, int srcY, int dstX, int dstY, int z, int radius, boolean running, boolean ignoreLastStep, boolean moveNear);
}
