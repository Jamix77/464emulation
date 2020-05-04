/* Class32 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class32 {
	public static volatile boolean aBoolean702;
	public static boolean aBoolean703;
	public static Class26 aClass26_695 = RS2Font.getRs2PreparedString(
			"blaugr-Un:", false);
	public static Class26 aClass26_700;
	public static Class26 aClass26_701 = RS2Font.getRs2PreparedString(
			"Passwort: ", false);
	public static RSInterface aClass4_Sub13_698;
	public static long[] aLongArray699;
	public static int anInt694;
	public static int anInt696;
	public static int[][] anIntArrayArray697;

	static {
		aClass26_700 = RS2Font.getRs2PreparedString(
				"Lade Benutzeroberfl-=che )2 ", false);
		anIntArrayArray697 = new int[5][5000];
		aBoolean702 = true;
		aLongArray699 = new long[32];
		aBoolean703 = false;
	}

	public static void method882(int arg0, int arg1) {
		try {
			Class4_Sub20_Sub6.aBoolean2914 = false;
			Class4_Sub21.anInt2374 = -1;
			Class4_Sub20_Sub9.anInt3050 = 0;
			Class25.anInt577 = -1;
			Class67.aClass19_1363 = null;
			StreamBuffer.anInt2085 = 1;
			Class4_Sub20_Sub5.anInt2888 = arg0;
			if (arg1 <= 63)
				method883(115);
			anInt694++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("jb.A("
					+ arg0 + ',' + arg1 + ')'));
		}
	}

	public static void method883(int arg0) {
		try {
			aClass4_Sub13_698 = null;
			anIntArrayArray697 = null;
			aClass26_695 = null;
			aClass26_701 = null;
			aClass26_700 = null;
			if (arg0 == 1)
				aLongArray699 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "jb.B("
					+ arg0 + ')');
		}
	}
}
