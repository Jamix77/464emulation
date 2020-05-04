/* Class4_Sub20_Sub5 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class4_Sub20_Sub5 extends Class4_Sub20 {
	public static byte[][] aByteArrayArray2887;
	public static Class19 aClass19_2892;
	public static Class26 aClass26_2868;
	public static Class26 aClass26_2871;
	public static Class26 aClass26_2873 = RS2Font.getRs2PreparedString("gr-Un:",
			false);
	public static Class26 aClass26_2884;
	public static Class26 aClass26_2885;
	public static Class26 aClass26_2886;
	public static Class26 aClass26_2890;
	public static Class26 aClass26_2891;
	public static Class26 aClass26_2894;
	public static Class26[] aClass26Array2893;
	public static Class4_Sub11_Sub1 aClass4_Sub11_Sub1_2883;
	public static RSInterface aClass4_Sub13_2882;
	public static Class81 aClass81_2880;
	public static int anInt2867;
	public static int anInt2869;
	public static int anInt2879;
	public static int anInt2888;
	public static int anInt2889;
	static {
		aClass26_2871 = RS2Font.getRs2PreparedString("Loading fonts )2 ", false);
		aClass26_2868 = RS2Font.getRs2PreparedString("headicons_prayer", false);
		anInt2879 = -2;
		aClass26_2884 = aClass26_2871;
		aClass26_2885 = (RS2Font.getRs2PreparedString(
				"Ung-Ultige Verbindung mit einem Anmelde)2Server)3", false));
		aClass4_Sub11_Sub1_2883 = new Class4_Sub11_Sub1(5000);
		aClass26_2886 = RS2Font.getRs2PreparedString("::fpson", false);
		anInt2889 = 0;
		aClass26_2891 = RS2Font.getRs2PreparedString(" )2> <col=00ffff>", false);
		aClass26_2890 = RS2Font.getRs2PreparedString(
				"Anmelde)2Limit -Uberschritten)3", false);
		aClass26_2894 = RS2Font.getRs2PreparedString("Ung-Ultige Session)2ID)3",
				false);
		aClass26Array2893 = new Class26[200];
	}

	public static void method362(int arg0) {
		try {
			aClass26_2894 = null;
			aClass26_2884 = null;
			aClass26_2890 = null;
			if (arg0 <= 122)
				method362(27);
			aClass26Array2893 = null;
			aClass4_Sub13_2882 = null;
			aClass26_2868 = null;
			aClass4_Sub11_Sub1_2883 = null;
			aByteArrayArray2887 = null;
			aClass26_2871 = null;
			aClass26_2891 = null;
			aClass26_2886 = null;
			aClass19_2892 = null;
			aClass26_2873 = null;
			aClass26_2885 = null;
			aClass81_2880 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "fd.B("
					+ arg0 + ')');
		}
	}

	public Class26 aClass26_2878 = Class41.aClass26_804;
	public Class26[] aClass26Array2877;
	public int anInt2874;
	public int anInt2875 = 0;
	public int anInt2876;
	public int anInt2881;

	public int[] anIntArray2870;

	public int[] anIntArray2872;

	public void method361(int arg0, int arg1, StreamBuffer arg2) {
		anInt2869++;
		if (arg0 != 1) {
			if (arg0 != 2) {
				if (arg0 != 3) {
					if (arg0 == 4)
						anInt2881 = arg2.method219((byte) 73);
					else if (arg0 == 5) {
						anInt2875 = arg2.method209((byte) -123);
						anIntArray2870 = new int[anInt2875];
						aClass26Array2877 = new Class26[anInt2875];
						for (int i = 0; (anInt2875 ^ 0xffffffff) < (i ^ 0xffffffff); i++) {
							anIntArray2870[i] = arg2.method219((byte) 73);
							aClass26Array2877[i] = arg2.method212(123);
						}
					} else if ((arg0 ^ 0xffffffff) == -7) {
						anInt2875 = arg2.method209((byte) -110);
						anIntArray2870 = new int[anInt2875];
						anIntArray2872 = new int[anInt2875];
						for (int i = 0; (i ^ 0xffffffff) > (anInt2875 ^ 0xffffffff); i++) {
							anIntArray2870[i] = arg2.method219((byte) 73);
							anIntArray2872[i] = arg2.method219((byte) 73);
						}
					}
				} else
					aClass26_2878 = arg2.method212(88);
			} else
				anInt2876 = arg2.get();
		} else
			anInt2874 = arg2.get();
		if (arg1 != -2)
			anInt2879 = 99;
	}

	public void method363(StreamBuffer arg0, boolean arg1) {
		anInt2867++;
		for (;;) {
			int i = arg0.get();
			if (i == 0)
				break;
			method361(i, -2, arg0);
		}
		if (arg1 != false)
			method363(null, false);
	}
}
