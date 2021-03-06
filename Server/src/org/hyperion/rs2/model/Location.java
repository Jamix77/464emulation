package org.hyperion.rs2.model;

import java.util.ArrayList;
import java.util.List;

import org.hyperion.rs2.clipping.Directions;
import org.hyperion.rs2.clipping.Directions.NormalDirection;

/**
 * Represents a single location in the game world.
 * @author Graham Edgecombe
 *
 */
public class Location {
	
	/**
	 * The x coordinate.
	 */
	private final int x;
	
	/**
	 * The y coordinate.
	 */
	private final int y;
	
	/**
	 * The z coordinate.
	 */
	private final int z;
	
	/**
	 * Creates a location.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 * @return The location.
	 */
	public static Location create(int x, int y, int z) {
		return new Location(x, y, z);
	}
	
	/**
	 * Creates a location.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 */
	public Location(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Gets the absolute x coordinate.
	 * @return The absolute x coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the absolute y coordinate.
	 * @return The absolute y coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gets the z coordinate, or height.
	 * @return The z coordinate.
	 */
	public int getZ() {
		return z;
	}
	
	/**
	 * Gets the local x coordinate relative to this region.
	 * @return The local x coordinate relative to this region.
	 */
	public int getLocalX() {
		return getLocalX(this);
	}
	
	/**
	 * Gets the local y coordinate relative to this region.
	 * @return The local y coordinate relative to this region.
	 */
	public int getLocalY() {
		return getLocalY(this);
	}
	
	public int getLocalX(Location l) {
		return x - 8 * (l.getRegionX() - 6);
	}
	
	/**
	 * Gets the local y coordinate relative to a specific region.
	 * @param l The region the coordinate will be relative to.
	 * @return The local y coordinate.
	 */
	public int getLocalY(Location l) {
		return y - 8 * (l.getRegionY() - 6);
	}
	
	/**
	 * Gets the region x coordinate.
	 * @return The region x coordinate.
	 */
	public int getRegionX() {
		return (x >> 3);
	}
	
	/**
	 * Gets the region y coordinate.
	 * @return The region y coordinate.
	 */
	public int getRegionY() {
		return (y >> 3);
	}
	
	/**
	 * Checks if this location is within range of another.
	 * @param other The other location.
	 * @return <code>true</code> if the location is in range,
	 * <code>false</code> if not.
	 */
	public boolean isWithinDistance(Location other) {
		if(z != other.z) {
			return false;
		}
		int deltaX = other.x - x, deltaY = other.y - y;
		return deltaX <= 14 && deltaX >= -15 && deltaY <= 14 && deltaY >= -15;
	}
	
	/**
	 * Checks if this location is within interaction range of another.
	 * @param other The other location.
	 * @return <code>true</code> if the location is in range,
	 * <code>false</code> if not.
	 */
	public boolean isWithinInteractionDistance(Location other) {
		if(z != other.z) {
			return false;
		}
		int deltaX = other.x - x, deltaY = other.y - y;
		return deltaX <= 2 && deltaX >= -3 && deltaY <= 2 && deltaY >= -3;
	}
	
	/**
	 * Checks if this location is next to another.
	 * @param other The other location.
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean isNextTo(Location other) {
		if(z != other.z) {
			return false;
		}
		/*int deltaX = Math.abs(other.x - x), deltaY = Math.abs(other.y - y);
		return deltaX <= 1 && deltaY <= 1;*/
		return (getX() == other.getX() && getY() != other.getY()
				|| getX() != other.getX() && getY() == other.getY()
				|| getX() == other.getX() && getY() == other.getY());
	}

	/**
	 * Checks if a coordinate is within range of another.
	 * @return <code>true</code> if the location is in range,
	 * <code>false</code> if not.
	 */
	public boolean isWithinDistance(Entity attacker, Entity victim, int distance) {
		if(attacker.getWidth() == 1 && attacker.getHeight() == 1 &&
		   victim.getWidth() == 1 && victim.getHeight() == 1 && distance == 1) {
			return distanceToPoint(victim.getLocation()) <= distance;			
		}
		List<Location> myTiles = entityTiles(attacker);
		List<Location> theirTiles = entityTiles(victim);
		for(Location myTile : myTiles) {
			for(Location theirTile : theirTiles) {
				if(myTile.isWithinDistance(theirTile, distance)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if a coordinate is within range of another.
	 * @return <code>true</code> if the location is in range,
	 * <code>false</code> if not.
	 */
	public int distanceToEntity(Entity attacker, Entity victim) {
		if(attacker.getWidth() == 1 && attacker.getHeight() == 1 &&
		   victim.getWidth() == 1 && victim.getHeight() == 1) {
			return distanceToPoint(victim.getLocation());			
		}
		int lowestDistance = 100;
		List<Location> myTiles = entityTiles(attacker);
		List<Location> theirTiles = entityTiles(victim);
		for(Location myTile : myTiles) {
			for(Location theirTile : theirTiles) {
				int dist = myTile.distanceToPoint(theirTile);
				if(dist <= lowestDistance) {
					lowestDistance = dist;
				}
			}
		}
		return lowestDistance;
	}
	
	/**
	 * The list of tiles this entity occupies.
	 * @param entity The entity.
	 * @return The list of tiles this entity occupies.
	 */
	public List<Location> entityTiles(Entity entity) {
		List<Location> myTiles = new ArrayList<Location>();
		myTiles.add(entity.getLocation());
		if(entity.getWidth() > 1) {
			for(int i = 1; i < entity.getWidth(); i++) {
				myTiles.add(Location.create(entity.getLocation().getX() + i,
						entity.getLocation().getY(), entity.getLocation().getZ()));
			}
		}
		if(entity.getHeight() > 1) {
			for(int i = 1; i < entity.getHeight(); i++) {
				myTiles.add(Location.create(entity.getLocation().getX(),
						entity.getLocation().getY() + i, entity.getLocation().getZ()));
			}
		}
		int myHighestVal = (entity.getWidth() > entity.getHeight() ? entity.getWidth() : entity.getHeight());
		if(myHighestVal > 1) {
			for(int i = 1; i < myHighestVal; i++) {
				myTiles.add(Location.create(entity.getLocation().getX() + i,
						entity.getLocation().getY() + i, entity.getLocation().getZ()));
			}			
		}
		return myTiles;
	}

	/**
	 * Checks if a coordinate is within range of another.
	 * @return <code>true</code> if the location is in range,
	 * <code>false</code> if not.
	 */
	public boolean isWithinDistance(int width, int height, Location otherLocation, int otherWidth, int otherHeight, int distance) {
		Location myClosestTile = this.closestTileOf(otherLocation, width, height);
		Location theirClosestTile = otherLocation.closestTileOf(this, otherWidth, otherHeight);
		
		return myClosestTile.distanceToPoint(theirClosestTile) <= distance;
	}
	
	/**
	 * Checks if a coordinate is within range of another.
	 * @return <code>true</code> if the location is in range,
	 * <code>false</code> if not.
	 */
	public boolean isWithinDistance(Location location, int distance) {
		int objectX = location.getX();
		int objectY = location.getY();
		for (int i = 0; i <= distance; i++) {
		  for (int j = 0; j <= distance; j++) {
			if ((objectX + i) == x && ((objectY + j) == y || (objectY - j) == y || objectY == y)) {
				return true;
			} else if ((objectX - i) == x && ((objectY + j) == y || (objectY - j) == y || objectY == y)) {
				return true;
			} else if (objectX == x && ((objectY + j) == y || (objectY - j) == y || objectY == y)) {
				return true;
			}
		  }
		}
		return false;
	}
	
	/**
	 * Gets the closest tile of this location from a specific point.
	 */
	public Location closestTileOf(Location from, int width, int height) {
		if(width < 2 && height < 2) {
			return this;
		}
		Location location = null;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Location loc = Location.create(this.x + x, this.y + y, this.z);
				if(location == null || loc.distanceToPoint(from) < location.distanceToPoint(from)) {
					location = loc;
				}
			}
		}
		return location;
	}
	
	/**
	 * Gets the tile that is opposite to where the player is standing.
	 */
	public Location oppositeTileOfEntity(Entity entity) {
		if(entity.getWidth() < 2 && entity.getHeight() < 2) {
			return entity.getLocation();
		}
		int newX = x;
		if(x < entity.getLocation().getX()) {
			newX += 1 + entity.getWidth();
		} else if(x > entity.getLocation().getX()) {
			newX -= 1 + entity.getWidth();
		}
		int newY = y;
		if(y < entity.getLocation().getY()) {
			newY += 1 + entity.getHeight();
		} else if(y > entity.getLocation().getY()) {
			newY -= 1 + entity.getHeight();
		}
		return Location.create(newX, newY, z);
	}
	
	/**
	 * Gets the distance to a location.
	 * @param other The location.
	 * @return The distance from the other location.
	 */
   	public int distanceToPoint(Location other) {
   		int absX = x;
   		int absY = y;
   		int pointX = other.getX();
   		int pointY = other.getY();
		return (int) Math.sqrt(Math.pow(absX - pointX, 2) + Math.pow(absY - pointY, 2));
  	}
	
	
	@Override
	public int hashCode() {
		return z << 30 | x << 15 | y;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Location)) {
			return false;
		}
		Location loc = (Location) other;
		return loc.x == x && loc.y == y && loc.z == z;
	}
	
	@Override
	public String toString() {
		return "["+x+","+y+","+z+"]";
	}

	/**
	 * Creates a new location based on this location.
	 * @param diffX X difference.
	 * @param diffY Y difference.
	 * @param diffZ Z difference.
	 * @return The new location.
	 */
	public Location transform(int diffX, int diffY, int diffZ) {
		return Location.create(x + diffX, y + diffY, z + diffZ);
	}

	public NormalDirection direction(Location next) {
		return Directions.directionFor(this, next);
	}

	public Location getActualLocation(Entity entity, GameObject obj) {
		Location location = null;
		if (obj.getDefinition().sizeX > 3)
			location = obj.getLocation().transform(Math.round(obj.getDefinition().sizeX / 2), Math.round(obj.getDefinition().sizeX / 2), 0);
		if (obj.getDefinition().sizeX > 2) { // oak tree, etc
			location = obj.getLocation().transform(1, 1, 0);
		}

		if (obj.getDefinition().sizeX == 2) { // normal trees

			if (obj.getLocation().getY() + 1 < entity.getLocation().getY()) {
				if (obj.getLocation().getX() < entity.getLocation().getX()) {
					location = obj.getLocation().transform(0, -1, 0);
				} else {
					location = obj.getLocation().transform(1, -1, 0);
				}
			}
			if (obj.getLocation().getY() + 1 >= entity.getLocation().getY()) {
				if (obj.getLocation().getX() < entity.getLocation().getX()) {
					if (obj.getLocation().getY() == entity.getLocation().getY()) {
						location = obj.getLocation().transform(-1, 1, 0);
					} else {
						location = obj.getLocation().transform(-1, 0, 0);
					}
				} else {
					if (obj.getLocation().getY() == entity.getLocation().getY()) {
						location = obj.getLocation().transform(2, 1, 0);
					} else {
						location = obj.getLocation().transform(2, 0, 0);
					}
				}
			}
			if (obj.getLocation().getY() > entity.getLocation().getY()) {
				if (obj.getLocation().getX() == entity.getLocation().getX()) {
					location = obj.getLocation().transform(2, 3, 0);
				} else {
					location = obj.getLocation().transform(0, 2, 0);
				}
			}
		} else if (obj.getDefinition().sizeX < 2){
			location = obj.getLocation();
		}
		return location;
	}

	public Location getNorth() {
		return transform(0, 1, 0);
	}
	
	public Location getSouth() {
		return transform(0, -1, 0);
	}
	
	public Location getEast() {
		return transform(1, 0, 0);
	}
	
	public Location getNorthEast() {
		return transform(1, 1, 0);
	}
	
	public Location getSouthEast() {
		return transform(1, -1, 0);
	}
	
	public Location getWest() {
		return transform(-1, 0, 0);
	}

	public Location getUp() {
		return transform(0, 0, 1);
	}
	
	public Location getNorthWest() {
		return transform(-1, 1, 0);
	}
	
	public Location getSouthWest() {
		return transform(-1, -1, 0);
	}

	public boolean inArea(int a, int b, int c, int d) {
		return x >= a && y >= b && x <= c && y <= d;
	}
	public boolean inArea(Location southWest, Location northWest) {
		return x >= southWest.getX() && y >= southWest.getY() && x <= northWest.getX() && y <= northWest.getY();
	}

}
