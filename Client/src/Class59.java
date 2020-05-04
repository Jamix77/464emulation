/* Class59 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class59 {
	public static boolean aBoolean1188;
	public static boolean aBoolean1189;
	public static byte[][][] aByteArrayArrayArray1181;
	public static Class22 aClass22_1185;
	public static Class26 aClass26_1176;
	public static Class26 aClass26_1180 = RS2Font.getRs2PreparedString("Type",
			false);
	public static Class26 aClass26_1183 = aClass26_1180;
	public static Class26 aClass26_1187;
	public static Class4_Sub20_Sub12_Sub1[] aClass4_Sub20_Sub12_Sub1Array1174;
	public static int anInt1178;
	public static int anInt1184 = 0;
	public static int anInt1186;
	public static int anInt1190;
	static {
		aClass26_1176 = RS2Font.getRs2PreparedString(
				" zuerst von Ihrer Ignorieren)2Liste(Q", false);
		anInt1186 = 0;
		aClass26_1187 = RS2Font.getRs2PreparedString("<col=ffff00>*V", false);
		aBoolean1188 = true;
		aBoolean1189 = false;
	}

	public static byte[] method1034(boolean arg0, byte arg1, Object arg2) {
		try {
			anInt1190++;
			if (arg2 == null)
				return null;
			if (arg2 instanceof byte[]) {
				byte[] is = (byte[]) arg2;
				if (arg0)
					return Class47.method982(-24158, is);
				return is;
			}
			if (arg2 instanceof Class45) {
				Class45 class45 = (Class45) arg2;
				return class45.method971(false);
			}
			throw new IllegalArgumentException();
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("r.C("
					+ arg0 + ',' + arg1 + ','
					+ (arg2 != null ? "{...}" : "null") + ')'));
		}
	}

	public static int method1035(int arg0, int arg1) {
		try {
			return arg0 & arg1;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("r.A("
					+ arg0 + ',' + arg1 + ')'));
		}
	}

	public static void method1036(byte arg0) {
		try {
			aClass4_Sub20_Sub12_Sub1Array1174 = null;
			aClass26_1176 = null;
			aClass26_1183 = null;
			aByteArrayArrayArray1181 = null;
			aClass26_1187 = null;
			aClass22_1185 = null;
			aClass26_1180 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "r.B("
					+ arg0 + ')');
		}
	}

	public static void method1037(byte arg0) {
		anInt1178++;
		boolean bool = false;
		while (!bool) {
			bool = true;
			for (int i = 0; i < Class4_Sub20_Sub8.anInt2980 - 1; i++) {
				if (Class82.anIntArray1712[i] < 1000
						&& Class82.anIntArray1712[1 + i] > 1000) {
					bool = false;
					Class26 class26 = Class18.aClass26Array462[i];
					Class18.aClass26Array462[i] = Class18.aClass26Array462[1 + i];
					Class18.aClass26Array462[i + 1] = class26;
					Class26 class26_0_ = Class4_Sub20_Sub8.aClass26Array2957[i];
					Class4_Sub20_Sub8.aClass26Array2957[i] = Class4_Sub20_Sub8.aClass26Array2957[i
							- -1];
					Class4_Sub20_Sub8.aClass26Array2957[1 + i] = class26_0_;
					int i_1_ = Class82.anIntArray1712[i];
					Class82.anIntArray1712[i] = Class82.anIntArray1712[i + 1];
					Class82.anIntArray1712[i - -1] = i_1_;
					i_1_ = Class43.anIntArray885[i];
					Class43.anIntArray885[i] = Class43.anIntArray885[1 + i];
					Class43.anIntArray885[1 + i] = i_1_;
					i_1_ = Class40.anIntArray789[i];
					Class40.anIntArray789[i] = Class40.anIntArray789[i - -1];
					Class40.anIntArray789[i - -1] = i_1_;
					i_1_ = Class4_Sub1.anIntArray1862[i];
					Class4_Sub1.anIntArray1862[i] = Class4_Sub1.anIntArray1862[i + 1];
					Class4_Sub1.anIntArray1862[i + 1] = i_1_;
				}
			}
		}
		if (arg0 != -43)
			aClass26_1180 = null;
	}

	public Class26[] aClass26Array1177;

	public Class4_Sub20_Sub16 aClass4_Sub20_Sub16_1182;

	public int anInt1175 = -1;

	public int[] anIntArray1179;
}
