/* Class30 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class30 {
	public static Class26 aClass26_651;
	public static Class26 aClass26_652 = RS2Font.getRs2PreparedString("(Y",
			false);
	public static Class26 aClass26_653;
	public static Class26 aClass26_654 = RS2Font.getRs2PreparedString(
			"Loading title screen )2 ", false);
	public static Class26 aClass26_659;
	public static Class26 aClass26_660;
	public static Class26 aClass26_662;
	public static int anInt655;
	public static int anInt656;
	public static int anInt657;
	public static int anInt658;
	public static int anInt663;
	public static int[] anIntArray650;
	public static int[] anIntArray661 = new int[4000];

	static {
		aClass26_659 = RS2Font.getRs2PreparedString("::fpsoff", false);
		aClass26_660 = RS2Font.getRs2PreparedString(
				"Invalid loginserver requested)3", false);
		aClass26_651 = aClass26_660;
		aClass26_653 = RS2Font.getRs2PreparedString(
				" zuerst von Ihrer Freunde)2Liste(Q", false);
		aClass26_662 = aClass26_654;
	}

	public static Class26 method872(int arg0, int arg1) {
		try {
			anInt663++;
			if (arg1 != -1)
				return null;
			if ((arg0 ^ 0xffffffff) > -100001)
				return Class4_Sub24.method639(
						(new Class26[] { Class82.aClass26_1724,
								Class74.method1168(arg1 + -11, arg0),
								(Class4_Sub20_Sub10.aClass26_3072) }), -842);
			if (arg0 < 10000000)
				return (Class4_Sub24.method639(
						new Class26[] { Class4_Sub20_Sub16.aClass26_3181,
								Class74.method1168(-16, arg0 / 1000),
								Class73.aClass26_1501,
								Class4_Sub20_Sub10.aClass26_3072 }, -842));
			return (Class4_Sub24.method639(
					new Class26[] { Class16.aClass26_445,
							Class74.method1168(-76, arg0 / 1000000),
							Class31.aClass26_681,
							Class4_Sub20_Sub10.aClass26_3072 }, -842));
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("j.B("
					+ arg0 + ',' + arg1 + ')'));
		}
	}

	public static void method873(int arg0) {
		anInt658++;
		Class9.aClass66_277.method1084(0);
		if (arg0 != 4000)
			method874(-89);
	}

	public static void method874(int arg0) {
		try {
			aClass26_659 = null;
			anIntArray650 = null;
			aClass26_654 = null;
			aClass26_651 = null;
			anIntArray661 = null;
			aClass26_652 = null;
			aClass26_660 = null;
			if (arg0 != -1)
				method874(24);
			aClass26_662 = null;
			aClass26_653 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "j.C("
					+ arg0 + ')');
		}
	}
}
