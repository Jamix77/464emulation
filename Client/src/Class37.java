/* Class37 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class37 {
	public static Class26 aClass26_762;
	public static Class26 aClass26_764;
	public static Class26 aClass26_765;
	public static Class4_Sub20_Sub12_Sub2 aClass4_Sub20_Sub12_Sub2_761;
	public static Class65 aClass65_766;
	public static int anInt758;
	public static int anInt759;
	public static int anInt760 = 0;
	public static int anInt763;
	public static int anInt767;

	static {
		anInt758 = 0;
		aClass26_762 = RS2Font.getRs2PreparedString("hitmarks", false);
		aClass26_764 = (RS2Font.getRs2PreparedString(
				"Please subscribe)1 or use a different world)3", false));
		aClass26_765 = aClass26_764;
		aClass65_766 = new Class65();
		anInt767 = 0;
	}

	public static void method917(byte arg0) {
		try {
			aClass26_765 = null;
			aClass26_764 = null;
			aClass26_762 = null;
			if (arg0 != -53)
				method918(27);
			aClass65_766 = null;
			aClass4_Sub20_Sub12_Sub2_761 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "kb.B("
					+ arg0 + ')');
		}
	}

	public static void method918(int arg0) {
		try {
			anInt763++;
			if (arg0 != -7398)
				aClass26_764 = null;
			Class66.byteBuffer.putOpcode(218, -77);
			Class66.byteBuffer.putLong(189354448, 0L);
			Class43.anInt859++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "kb.A("
					+ arg0 + ')');
		}
	}
}
