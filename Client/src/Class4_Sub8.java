/* Class4_Sub8 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class4_Sub8 extends RSFont {
	public static Class26 aClass26_1966;
	public static Class26 aClass26_1968 = RS2Font.getRs2PreparedString(
			"Select a world", false);
	public static Class26 aClass26_1970;
	public static Class26 aClass26_1971;
	public static Class26 aClass26_1974;
	public static Class26 aClass26_1976;
	public static Class26 aClass26_1977;
	public static Class66 aClass66_1972;
	public static int anInt1967;
	public static int anInt1978;
	public static int[] anIntArray1979;
	public static int[] anIntArray1980;
	static {
		aClass26_1966 = RS2Font.getRs2PreparedString("Freie Welt", false);
		aClass26_1970 = RS2Font.getRs2PreparedString("Username: ", false);
		aClass26_1971 = RS2Font.getRs2PreparedString("<col=ffffff>", false);
		anIntArray1979 = new int[1000];
		aClass26_1976 = RS2Font.getRs2PreparedString("Hierhin gehen", false);
		aClass26_1974 = aClass26_1968;
		aClass26_1977 = aClass26_1970;
		aClass66_1972 = new Class66(30);
		anIntArray1980 = new int[128];
	}

	public static void method189(int arg0, int arg1) {
		try {
			anInt1978++;
			if (arg0 > -117)
				method190(53);
			if (Class13_Sub2.aClass4_Sub8Array2482 != null
					&& (arg1 ^ 0xffffffff) <= -1
					&& Class13_Sub2.aClass4_Sub8Array2482.length > arg1
					&& Class13_Sub2.aClass4_Sub8Array2482[arg1] != null) {
				Class66.aClass4_Sub11_Sub1_1328.method264(161, -30);
				Class9.anInt276++;
				Class66.aClass4_Sub11_Sub1_1328.method255(189354448,
						Class13_Sub2.aClass4_Sub8Array2482[arg1].aLong150);
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("hb.A("
					+ arg0 + ',' + arg1 + ')'));
		}
	}

	public static void method190(int arg0) {
		try {
			aClass26_1966 = null;
			anIntArray1980 = null;
			aClass26_1974 = null;
			aClass66_1972 = null;
			aClass26_1976 = null;
			aClass26_1977 = null;
			aClass26_1968 = null;
			aClass26_1971 = null;
			anIntArray1979 = null;
			if (arg0 != 30)
				method190(92);
			aClass26_1970 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "hb.B("
					+ arg0 + ')');
		}
	}

	public byte aByte1973;

	public Class26 aClass26_1975;

	public int anInt1969;
}
