package com.example.administrator.weixinhookdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public final class a {
    private InputStream AvC;
    private int AvD = 0;
    private int bfI = 0;
    public int bfJ;
    public int bfK = 0;
    private int bfL = Integer.MAX_VALUE;
    private int bfO = 67108864;
    public byte[] buffer;
    public int bufferSize;

    public final double readDouble() throws IOException {
        byte rC = rC();
        byte rC2 = rC();
        return Double.longBitsToDouble(
                ((((((((((long) rC2) & 255) << 8) | (((long) rC) & 255))
                                                                | ((((long) rC()) & 255) << 16))
                                                        | ((((long) rC()) & 255) << 24))
                                                | ((((long) rC()) & 255) << 32))
                                        | ((((long) rC()) & 255) << 40))
                                | ((((long) rC()) & 255) << 48))
                        | ((((long) rC()) & 255) << 56));
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(
                (((rC() & 255) | ((rC() & 255) << 8)) | ((rC() & 255) << 16))
                        | ((rC() & 255) << 24));
    }

    public final int ry() throws IOException {
        byte rC = rC();
        if (rC >= (byte) 0) {
            return rC;
        }
        int i = rC & 127;
        byte rC2 = rC();
        if (rC2 >= (byte) 0) {
            return i | (rC2 << 7);
        }
        i |= (rC2 & 127) << 7;
        rC2 = rC();
        if (rC2 >= (byte) 0) {
            return i | (rC2 << 14);
        }
        i |= (rC2 & 127) << 14;
        rC2 = rC();
        if (rC2 >= (byte) 0) {
            return i | (rC2 << 21);
        }
        i |= (rC2 & 127) << 21;
        rC2 = rC();
        i |= rC2 << 28;
        if (rC2 >= (byte) 0) {
            return i;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (rC() >= (byte) 0) {
                return i;
            }
        }
        throw b.cJH();
    }

    public final long rz() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte rC = rC();
            j |= ((long) (rC & 127)) << i;
            if ((rC & FileUtils.S_IWUSR) == 0) {
                return j;
            }
        }
        throw b.cJH();
    }

    public a(byte[] bArr, int i) {
        this.buffer = bArr;
        this.bufferSize = i + 0;
        this.bfJ = 0;
        this.AvC = null;
    }

    public final boolean of(boolean z) throws IOException {
        if (this.bfJ < this.bufferSize) {
            throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
        } else if (this.AvD + this.bufferSize != this.bfL) {
            this.AvD += this.bufferSize;
            this.bfJ = 0;
            this.bufferSize = this.AvC == null ? -1 : this.AvC.read(this.buffer);
            if (this.bufferSize == -1) {
                this.bufferSize = 0;
                if (!z) {
                    return false;
                }
                throw b.cJF();
            }
            this.bufferSize += this.bfI;
            int i = this.AvD + this.bufferSize;
            if (i > this.bfL) {
                this.bfI = i - this.bfL;
                this.bufferSize -= this.bfI;
            } else {
                this.bfI = 0;
            }
            i = (this.AvD + this.bufferSize) + this.bfI;
            if (i <= this.bfO && i >= 0) {
                return true;
            }
            throw b.cJJ();
        } else if (!z) {
            return false;
        } else {
            throw b.cJF();
        }
    }

    private byte rC() throws IOException {
        if (this.bfJ == this.bufferSize) {
            of(true);
        }
        byte[] bArr = this.buffer;
        int i = this.bfJ;
        this.bfJ = i + 1;
        return bArr[i];
    }
}
