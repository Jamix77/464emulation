package org.hyperion.cache.fileserver;

public class JagexFS {

	private int id;
	private RSRandomAccessFile data;
	private RSRandomAccessFile index;

	public JagexFS(int id, String path) {
		this.id = id;
		try {
			this.data = new RSRandomAccessFile(path+"main_file_cache.dat2","rw");
			this.index = new RSRandomAccessFile(path+"main_file_cache.idx"+id,"rw");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized byte[] get(int id) {
		try {
			byte header[] = new byte[6];
			index.seek(id * 6);
			index.readFully(header);
			int length = ((header[0] & 0xff) << 16) + ((header[1] & 0xff) << 8) + (header[2] & 0xff);
			int position = ((header[3] & 0xff) << 16) + ((header[4] & 0xff) << 8) + (header[5] & 0xff);
			if (position > 0) {
				int offset = 0;
				int blockOffset = 0;
				byte file[] = new byte[length];
				while (offset < length) {
					byte header2[] = new byte[8];
					data.seek(position * 520);
					data.readFully(header2);
					position = ((header2[4] & 0xff) << 16) + ((header2[5] & 0xff) << 8) + (header2[6] & 0xff);
					while (offset < length && blockOffset < 512) {
						file[offset] = (byte) data.read();
						offset++;
						blockOffset++;
					}
					blockOffset = 0;
				}
				return file;
			}
		} catch (Exception e) {
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public int length() {
		try {
			return (int) (index.length() / 6);
		} catch (Exception e) {
		}
		return 0;
	}
}