package org.hyperion.cache.fileserver;

import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;

public class RSRandomAccessFile extends RandomAccessFile {

    public RSRandomAccessFile(String name, String mode) throws FileNotFoundException {
        super(name, mode);
    }

    public RSRandomAccessFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
    }

    public int read24BitInt() throws IOException {
        int i = readByte() << 16;
        i += readByte() << 8;
        return i + readByte();
    }

    public void write24BitInt(int i) throws IOException {
        writeByte(i >> 16);
        writeByte(i >> 8);
        writeByte(i);
    }
}