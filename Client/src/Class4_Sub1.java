/* Class4_Sub1 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class4_Sub1 extends RSFont {
	public static Class19_Sub1 aClass19_Sub1_1861;
	public static Class19_Sub1 aClass19_Sub1_1863;
	public static Class26 aClass26_1856;
	public static Class26 aClass26_1868;
	public static Class26 aClass26_1869;
	public static Class26 aClass26_1870;
	public static ByteBuffer aClass4_Sub11_Sub1_1854;
	public static int anInt1855;
	public static int anInt1857;
	public static int anInt1859;
	public static int anInt1860;
	public static int anInt1864;
	public static int anInt1865;
	public static int anInt1867;
	public static int[] anIntArray1862 = new int[500];
	static {
		anInt1859 = 0;
		aClass26_1856 = RS2Font.getRs2PreparedString("<col=ff3000>", false);
		aClass26_1868 = RS2Font.getRs2PreparedString(" )2> ", false);
		aClass4_Sub11_Sub1_1854 = new ByteBuffer(5000);
		aClass26_1870 = RS2Font.getRs2PreparedString(
				"go back to the main RuneScape webpage", false);
		aClass26_1869 = aClass26_1870;
	}

	public static void method75(int arg0, Class4_Sub2 arg1) {
		anInt1855++;
		arg1.aBoolean1874 = false;
		if (arg1.aClass4_Sub4_1873 != null)
			arg1.aClass4_Sub4_1873.anInt1913 = 0;
		for (Class4_Sub2 class4_sub2 = arg1.method79(); class4_sub2 != null; class4_sub2 = arg1
				.method81())
			method75(-19798, class4_sub2);
		if (arg0 != -19798)
			aClass19_Sub1_1861 = null;
	}

	public static void method76(byte arg0) {
		try {
			aClass19_Sub1_1863 = null;
			aClass26_1869 = null;
			aClass4_Sub11_Sub1_1854 = null;
			if (arg0 != 95)
				method76((byte) -93);
			aClass26_1868 = null;
			aClass26_1870 = null;
			aClass26_1856 = null;
			aClass19_Sub1_1861 = null;
			anIntArray1862 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "af.A("
					+ arg0 + ')');
		}
	}

	public static boolean method77(int arg0) {
		try {
			anInt1867++;
			if ((StreamBuffer.anInt2085 ^ 0xffffffff) != -1)
				return true;
			return Class1.aClass4_Sub2_Sub1_64.method119((byte) -25);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "af.B("
					+ arg0 + ')');
		}
	}

	public int[] anIntArray1858 = new int[1];

	public int[] anIntArray1866 = { -1 };
}
