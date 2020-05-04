/* Class80 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class80 {
	public static Class26 aClass26_1634;
	public static Class26 aClass26_1635;
	public static Class26 aClass26_1636;
	public static Class26 aClass26_1638 = RS2Font.getRs2PreparedString(
			"http:)4)4www)3runescape)3com", false);
	public static Class26 aClass26_1640;
	public static Class26 aClass26_1641;
	public static Class26 aClass26_1642;
	public static Class26 aClass26_1647;
	public static Class26 aClass26_1648;
	public static Class66 aClass66_1639;
	public static int anInt1637;
	public static int anInt1643;
	public static int anInt1646;
	public static int anInt1649;
	public static int[] anIntArray1644;
	public static int[] anIntArray1645;
	public static int[][][] anIntArrayArrayArray1650;

	static {
		anInt1637 = 0;
		aClass26_1641 = RS2Font.getRs2PreparedString("<col=ffff00>", false);
		anInt1646 = 0;
		aClass26_1642 = RS2Font.getRs2PreparedString("M", false);
		aClass26_1636 = RS2Font.getRs2PreparedString("slide:", false);
		aClass26_1640 = aClass26_1636;
		aClass26_1635 = aClass26_1636;
		aClass26_1634 = RS2Font.getRs2PreparedString(
				"nicht hergestellt werden)3", false);
		anIntArray1645 = new int[128];
		aClass26_1647 = RS2Font.getRs2PreparedString(
				"und loggen sich dann erneut ein)3", false);
		aClass26_1648 = RS2Font.getRs2PreparedString("null", false);
		aClass66_1639 = new Class66(30);
		anIntArrayArrayArray1650 = new int[4][13][13];
	}

	public static void method1202(int arg0) {
		try {
			if (arg0 != 128)
				method1204((byte) -18, -34);
			aClass26_1648 = null;
			aClass26_1634 = null;
			aClass26_1638 = null;
			aClass26_1636 = null;
			anIntArray1644 = null;
			aClass26_1635 = null;
			anIntArrayArrayArray1650 = null;
			aClass26_1641 = null;
			aClass66_1639 = null;
			aClass26_1642 = null;
			anIntArray1645 = null;
			aClass26_1647 = null;
			aClass26_1640 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "wb.B("
					+ arg0 + ')');
		}
	}

	public static Class26 method1203(int arg0, int arg1) {
		try {
			anInt1643++;
			if (arg1 >= -18)
				aClass26_1640 = null;
			return (Class4_Sub24
					.method639(
							(new Class26[] {
									Class74.method1168(-9,
											arg0 >> -1815177096 & 0xff),
									Class4_Sub20_Sub4.aClass26_2864,
									Class74.method1168(-63,
											(arg0 & 0xff9b81) >> -1321169712),
									Class4_Sub20_Sub4.aClass26_2864,
									Class74.method1168(-9,
											(0xff47 & arg0) >> -86774776),
									Class4_Sub20_Sub4.aClass26_2864,
									Class74.method1168(-97, arg0 & 0xff) }),
							-842));
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("wb.C("
					+ arg0 + ',' + arg1 + ')'));
		}
	}

	public static boolean method1204(byte arg0, int arg1) {
		try {
			if (arg0 != -127)
				aClass26_1636 = null;
			anInt1649++;
			if (((0x41617635 & arg1) >> 167845054 ^ 0xffffffff) == -1)
				return false;
			return true;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("wb.A("
					+ arg0 + ',' + arg1 + ')'));
		}
	}
}
