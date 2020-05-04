/* Class74 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class74 {
	public static Class26 aClass26_1517 = RS2Font.getRs2PreparedString(
			"Versteckt", false);
	public static Class26 aClass26_1520;
	public static Class26 aClass26_1521;
	public static Class26 aClass26_1523;
	public static Class26 aClass26_1524;
	public static StreamBuffer aClass4_Sub11_1516;
	public static int anInt1513 = 0;
	public static int anInt1514 = 0;
	public static int anInt1515;
	public static int anInt1522;
	public static int[] anIntArray1518;
	static {
		aClass4_Sub11_1516 = new StreamBuffer(8);
		aClass26_1523 = RS2Font.getRs2PreparedString("Prepared visibility map",
				false);
		aClass26_1521 = aClass26_1523;
		aClass26_1520 = RS2Font.getRs2PreparedString("::rect_debug", false);
		aClass26_1524 = (RS2Font.getRs2PreparedString(
				"Zu viele Verbindungen von Ihrer Adresse)3", false));
	}

	public static void method1167(byte arg0) {
		try {
			if (arg0 >= 15) {
				anIntArray1518 = null;
				aClass26_1517 = null;
				aClass26_1524 = null;
				aClass26_1523 = null;
				aClass26_1520 = null;
				aClass4_Sub11_1516 = null;
				aClass26_1521 = null;
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "va.B("
					+ arg0 + ')');
		}
	}

	public static Class26 method1168(int arg0, int arg1) {
		try {
			if (arg0 > -6)
				method1167((byte) -8);
			anInt1515++;
			return Class76.method1185(10, -9, false, arg1);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("va.A("
					+ arg0 + ',' + arg1 + ')'));
		}
	}

	public static Class4_Sub20_Sub14 method1169(byte arg0, int arg1) {
		try {
			anInt1522++;
			if (arg0 < 40)
				anInt1514 = 87;
			Class4_Sub20_Sub14 class4_sub20_sub14 = ((Class4_Sub20_Sub14) Class34.aClass66_1758
					.method1083(arg1, true));
			if (class4_sub20_sub14 != null)
				return class4_sub20_sub14;
			class4_sub20_sub14 = RS2Font.method337(arg1, false, -119,
					Class4_Sub20_Sub5.aClass19_2892,
					(Class4_Sub20_Sub10.aClass19_3062));
			if (class4_sub20_sub14 != null)
				Class34.aClass66_1758
						.method1082(true, arg1, class4_sub20_sub14);
			return class4_sub20_sub14;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("va.C("
					+ arg0 + ',' + arg1 + ')'));
		}
	}

	public Class4_Sub20_Sub7 aClass4_Sub20_Sub7_1512;
	public int anInt1509;

	public int anInt1510;

	public int anInt1511;

	public int anInt1519;

	public int anInt1525;
}
