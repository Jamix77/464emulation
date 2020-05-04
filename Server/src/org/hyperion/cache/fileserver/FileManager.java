package org.hyperion.cache.fileserver;

import java.io.File;
import java.util.zip.CRC32;

import org.hyperion.rs2.net.PacketBuilder;


public class FileManager {

    private static JagexFS fit_filesystem;
    private static JagexFS[] filesystems;
	
	public static void load(String path) throws Exception {
        int cache_len = -1;
		for (File f: new File(path).listFiles()) {
            String name = f.getName();
            if (name.startsWith("main_file_cache.idx")) {
                if (Integer.parseInt(name.split(".idx")[1]) != 255 && Integer.parseInt(name.split(".idx")[1]) > cache_len) {
                    cache_len = Integer.parseInt(name.split(".idx")[1]);
				}
			}
        }
        fit_filesystem = new JagexFS(255, path);
        filesystems = new JagexFS[cache_len+1];
        for (int fs_id = 0; fs_id < filesystems.length; fs_id++) {
            filesystems[fs_id] = new JagexFS(fs_id, path);
		}
		PacketBuilder crcPacketGen = new PacketBuilder().put((byte)0).putInt(fit_filesystem.length() * 8);
		CRC32 crc32 = new CRC32();
		for (int a = 0; a < fit_filesystem.length(); a++) {
			crc32.update(fit_filesystem.get(a));
			crcPacketGen.putInt((int) crc32.getValue());
			crcPacketGen.putInt(0);
			crc32.reset();
		}
		UpdateServer.setCRC(crcPacketGen.getPayload().array());
	}
	
	public static byte[] getFile(int cache, int id) {
		if (cache == 255) {
			return fit_filesystem.get(id);
		}
		return filesystems[cache].get(id);
	}
}