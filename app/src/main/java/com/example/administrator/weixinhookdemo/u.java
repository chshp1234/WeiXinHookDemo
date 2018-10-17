package com.example.administrator.weixinhookdemo;

import java.nio.ByteBuffer;

public final class u {
    private ByteBuffer byteBuffer;
    private boolean sFV;

    public final int by(byte[] bArr) {
        boolean z = (bArr == null || bArr.length == 0) ? true : bArr[0] != (byte) 123 ? true : bArr[bArr.length + -1] != (byte) 125 ? true : false;
        if (z) {
            this.byteBuffer = null;
            return -1;
        }
        this.byteBuffer = ByteBuffer.wrap(bArr);
        this.byteBuffer.position(1);
        this.sFV = false;
        return 0;
    }

    public final int getInt() throws Exception {
        if (!this.sFV) {
            return this.byteBuffer.getInt();
        }
        throw new Exception("Buffer For Build");
    }

    public final long getLong() throws Exception {
        if (!this.sFV) {
            return this.byteBuffer.getLong();
        }
        throw new Exception("Buffer For Build");
    }

    public final byte[] getBuffer() throws Exception {
        if (this.sFV) {
            throw new Exception("Buffer For Build");
        }
        short s = this.byteBuffer.getShort();
        if (s > (short) 2048) {
            this.byteBuffer = null;
            throw new Exception("Buffer String Length Error");
        } else if (s == (short) 0) {
            return new byte[0];
        } else {
            byte[] bArr = new byte[s];
            this.byteBuffer.get(bArr, 0, s);
            return bArr;
        }
    }

    public final String getString() throws Exception {
        if (this.sFV) {
            throw new Exception("Buffer For Build");
        }
        short s = this.byteBuffer.getShort();
        if (s > (short) 2048) {
            this.byteBuffer = null;
            throw new Exception("Buffer String Length Error");
        } else if (s == (short) 0) {
            return "";
        } else {
            byte[] bArr = new byte[s];
            this.byteBuffer.get(bArr, 0, s);
            return new String(bArr, "UTF-8");
        }
    }

    public final void CX(int i) {
        this.byteBuffer.position(this.byteBuffer.position() + i);
    }

    public final void chC() throws Exception {
        if (this.sFV) {
            throw new Exception("Buffer For Build");
        }
        short s = this.byteBuffer.getShort();
        if (s > (short) 2048) {
            this.byteBuffer = null;
            throw new Exception("Buffer String Length Error");
        } else if (s != (short) 0) {
            this.byteBuffer.position(s + this.byteBuffer.position());
        }
    }

    public final boolean chD() {
        return this.byteBuffer.limit() - this.byteBuffer.position() <= 1;
    }

    public final int chE() {
        this.byteBuffer = ByteBuffer.allocate(4096);
        this.byteBuffer.put((byte) 123);
        this.sFV = true;
        return 0;
    }

    private int CY(int i) {
        if (this.byteBuffer.limit() - this.byteBuffer.position() <= i) {
            ByteBuffer allocate = ByteBuffer.allocate(this.byteBuffer.limit() + 4096);
            allocate.put(this.byteBuffer.array(), 0, this.byteBuffer.position());
            this.byteBuffer = allocate;
        }
        return 0;
    }

    public final int CZ(int i) throws Exception {
        if (this.sFV) {
            CY(4);
            this.byteBuffer.putInt(i);
            return 0;
        }
        throw new Exception("Buffer For Parse");
    }

    public final int fX(long j) throws Exception {
        if (this.sFV) {
            CY(8);
            this.byteBuffer.putLong(j);
            return 0;
        }
        throw new Exception("Buffer For Parse");
    }

    public final int bz(byte[] bArr) throws Exception {
        if (this.sFV) {
            byte[] bArr2 = null;
            if (bArr != null) {
                bArr2 = bArr;
            }
            if (bArr2 == null) {
                bArr2 = new byte[0];
            }
            if (bArr2.length > 2048) {
                throw new Exception("Buffer String Length Error");
            }
            CY(bArr2.length + 2);
            this.byteBuffer.putShort((short) bArr2.length);
            if (bArr2.length > 0) {
                this.byteBuffer.put(bArr2);
            }
            return 0;
        }
        throw new Exception("Buffer For Parse");
    }

    public final int Wj(String str) throws Exception {
        if (this.sFV) {
            byte[] bArr = null;
            if (str != null) {
                bArr = str.getBytes();
            }
            if (bArr == null) {
                bArr = new byte[0];
            }
            if (bArr.length > 2048) {
                throw new Exception("Buffer String Length Error");
            }
            CY(bArr.length + 2);
            this.byteBuffer.putShort((short) bArr.length);
            if (bArr.length > 0) {
                this.byteBuffer.put(bArr);
            }
            return 0;
        }
        throw new Exception("Buffer For Parse");
    }

    public final byte[] chF() throws Exception {
        if (this.sFV) {
            CY(1);
            this.byteBuffer.put((byte) 125);
            byte[] obj = new byte[this.byteBuffer.position()];
            System.arraycopy(this.byteBuffer.array(), 0, obj, 0, obj.length);
            return obj;
        }
        throw new Exception("Buffer For Parse");
    }
}
