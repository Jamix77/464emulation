package org.hyperion.cache.fileserver;

import org.hyperion.rs2.net.Packet;
import org.hyperion.rs2.net.PacketBuilder;

public class UpdateServer {

	private static byte[] crcfile;
	
    public static Packet getPacketRequest(int cacheId, int id) {
		try {
			PacketBuilder packet = new PacketBuilder().put((byte) cacheId).putShort(id);
			byte cache[] = getFile(cacheId, id);
			int len = (((cache[1] & 0xff) << 24) + ((cache[2] & 0xff) << 16) + ((cache[3] & 0xff) << 8) + (cache[4] & 0xff)) + 9; //ERROR?
			if (cache[0] == 0) {
				len -= 4;
			}
			int c = 3;
			for (int i = 0; i < len; i++) {
				if (c == 512) {
					packet.put((byte) 0xFF);
					c = 1;
				}
				packet.put(cache[i]);
				c++;
			}
			return packet.toPacket();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static synchronized byte[] getFile(int cache, int id) {
		try {
			//System.out.println("Cache: "+cache+", Id: "+id); We don't really have a need for this to be printing... xD
			if (cache == 255 && id == 255) {
				return crcfile;
			}
			return FileManager.getFile(cache, id);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void setCRC(byte[] bytes) {
		crcfile = bytes;
	}
}