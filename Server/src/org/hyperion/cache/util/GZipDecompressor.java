package org.hyperion.cache.util;

import java.util.zip.Inflater;

import org.hyperion.cache.stream.Stream;

public class GZipDecompressor {

	private static final Inflater inflaterInstance = new Inflater(true);
	
	public static final void decompress(Stream stream, byte data[]) {
		if(data == null)
			return;
		synchronized(inflaterInstance) {
			if (~stream.getBuffer()[stream.getOffset()] != -32 || stream.getBuffer()[stream.getOffset() + 1] != -117) {
				data = null;
				throw new RuntimeException("Invalid GZIP header!");
			}
			try {
				inflaterInstance.setInput(stream.getBuffer(), stream.getOffset() + 10, -stream.getOffset() - 18 + stream.getBuffer().length);
				inflaterInstance.inflate(data);
			} catch (Exception e) {
				//inflaterInstance.reset();
				data = null;
			//	throw new RuntimeException("Invalid GZIP compressed data!");
			}
			inflaterInstance.reset();
		}
	}
	
}
