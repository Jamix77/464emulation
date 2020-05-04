/* Class52 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class52 {
	public static Class26 aClass26_1051;
	public static Class26 aClass26_1054;
	public static Class26 aClass26_1055;
	public static Class26 aClass26_1057 = RS2Font.getRs2PreparedString(
			"headicons_pk", false);
	public static Class26 aClass26_1060 = RS2Font.getRs2PreparedString(
			"Error connecting to server)3", false);
	public static Class26 aClass26_1061;
	public static Class26 aClass26_1063;
	public static Class26 aClass26_1065;
	public static Class26 aClass26_1066;
	public static int anInt1050;
	public static int anInt1052;
	public static volatile int anInt1053;
	public static int anInt1056;
	public static int anInt1058;
	public static int anInt1062;
	public static int anInt1064;
	public static int[] anIntArray1059;

	static {
		anInt1052 = 0;
		aClass26_1051 = aClass26_1060;
		aClass26_1063 = RS2Font.getRs2PreparedString(
				"and choose the (Wcreate account(W", false);
		anInt1053 = 0;
		aClass26_1065 = (RS2Font.getRs2PreparedString(
				"You need a members account to login to this world)3", false));
		aClass26_1054 = aClass26_1063;
		aClass26_1055 = aClass26_1065;
		aClass26_1066 = RS2Font.getRs2PreparedString("Loaded sprites", false);
		aClass26_1061 = aClass26_1066;
	}

	public static void method1000(int arg0, byte arg1, int arg2, int arg3) {
		if (arg1 != 6)
			method1003(-113, 7);
		for (int i = 0; (i ^ 0xffffffff) > -9; i++) {
			for (int i_0_ = 0; (i_0_ ^ 0xffffffff) > -9; i_0_++)
				Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3 - -i][i_0_
						+ arg2] = 0;
		}
		if (arg3 > 0) {
			for (int i = 1; i < 8; i++)
				Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3][i + arg2] = (Class4_Sub23.anIntArrayArrayArray2416[arg0][-1
						+ arg3][arg2 + i]);
		}
		anInt1062++;
		if ((arg2 ^ 0xffffffff) < -1) {
			for (int i = 1; (i ^ 0xffffffff) > -9; i++)
				Class4_Sub23.anIntArrayArrayArray2416[arg0][i + arg3][arg2] = (Class4_Sub23.anIntArrayArrayArray2416[arg0][i
						+ arg3][arg2 - 1]);
		}
		if ((arg3 ^ 0xffffffff) < -1
				&& (Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3 + -1][arg2] != 0))
			Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3][arg2] = Class4_Sub23.anIntArrayArrayArray2416[arg0][-1
					+ arg3][arg2];
		else if ((arg2 ^ 0xffffffff) >= -1
				|| ((Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3][arg2
						+ -1]) ^ 0xffffffff) == -1) {
			if ((arg3 ^ 0xffffffff) < -1
					&& (arg2 ^ 0xffffffff) < -1
					&& ((Class4_Sub23.anIntArrayArrayArray2416[arg0][-1 + arg3][arg2
							+ -1]) ^ 0xffffffff) != -1)
				Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3][arg2] = (Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3
						+ -1][-1 + arg2]);
		} else
			Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3][arg2] = Class4_Sub23.anIntArrayArrayArray2416[arg0][arg3][-1
					+ arg2];
	}

	public static void method1001(byte arg0) {
		try {
			if (arg0 >= -2)
				method1004(true);
			anInt1050++;
			if (Signlink.jVendor.toLowerCase().indexOf("microsoft") == -1) {
				Class14.anIntArray392[46] = 72;
				Class14.anIntArray392[61] = 27;
				Class14.anIntArray392[44] = 71;
				Class14.anIntArray392[47] = 73;
				Class14.anIntArray392[92] = 74;
				Class14.anIntArray392[59] = 57;
				Class14.anIntArray392[45] = 26;
				Class14.anIntArray392[91] = 42;
				Class14.anIntArray392[93] = 43;
				if (Signlink.aMethod1537 == null) {
					Class14.anIntArray392[192] = 58;
					Class14.anIntArray392[222] = 59;
				} else {
					Class14.anIntArray392[222] = 58;
					Class14.anIntArray392[192] = 28;
					Class14.anIntArray392[520] = 59;
				}
			} else {
				Class14.anIntArray392[189] = 26;
				Class14.anIntArray392[220] = 74;
				Class14.anIntArray392[186] = 57;
				Class14.anIntArray392[190] = 72;
				Class14.anIntArray392[222] = 59;
				Class14.anIntArray392[219] = 42;
				Class14.anIntArray392[221] = 43;
				Class14.anIntArray392[188] = 71;
				Class14.anIntArray392[191] = 73;
				Class14.anIntArray392[192] = 58;
				Class14.anIntArray392[223] = 28;
				Class14.anIntArray392[187] = 27;
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "oe.D("
					+ arg0 + ')');
		}
	}

	public static synchronized long method1002(byte arg0) {
		try {
			anInt1064++;
			long l = System.currentTimeMillis();
			if (arg0 != -42)
				method1002((byte) -124);
			if ((l ^ 0xffffffffffffffffL) > (Class4_Sub20_Sub7_Sub3.aLong3335 ^ 0xffffffffffffffffL))
				Class2.aLong82 += Class4_Sub20_Sub7_Sub3.aLong3335 + -l;
			Class4_Sub20_Sub7_Sub3.aLong3335 = l;
			return l + Class2.aLong82;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "oe.B("
					+ arg0 + ')');
		}
	}

	public static void method1003(int arg0, int arg1) {
		try {
			anInt1056++;
			if ((arg1 ^ 0xffffffff) != 0 && Class72.aBooleanArray1487[arg1]) {
				Class9.aClass19_275.method755(-91, arg1);
				if (Class28.aClass4_Sub13ArrayArray632[arg1] != null) {
					boolean bool = true;
					for (int i = 0; Class28.aClass4_Sub13ArrayArray632[arg1].length > i; i++) {
						if (Class28.aClass4_Sub13ArrayArray632[arg1][i] != null) {
							if (((Class28.aClass4_Sub13ArrayArray632[arg1][i].anInt2258) ^ 0xffffffff) != -3)
								Class28.aClass4_Sub13ArrayArray632[arg1][i] = null;
							else
								bool = false;
						}
					}
					if (arg0 >= -113)
						aClass26_1063 = null;
					if (bool)
						Class28.aClass4_Sub13ArrayArray632[arg1] = null;
					Class72.aBooleanArray1487[arg1] = false;
				}
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("oe.C("
					+ arg0 + ',' + arg1 + ')'));
		}
	}

	public static void method1004(boolean arg0) {
		try {
			aClass26_1055 = null;
			aClass26_1054 = null;
			if (arg0 == false) {
				aClass26_1063 = null;
				aClass26_1065 = null;
				aClass26_1051 = null;
				aClass26_1061 = null;
				aClass26_1060 = null;
				anIntArray1059 = null;
				aClass26_1066 = null;
				aClass26_1057 = null;
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "oe.A("
					+ arg0 + ')');
		}
	}
}
