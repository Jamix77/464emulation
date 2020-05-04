/* Class4_Sub20_Sub11 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.awt.Font;

public class Class4_Sub20_Sub11 extends Class4_Sub20 {
	public static boolean[] aBooleanArray3083;
	public static Class26 aClass26_3074;
	public static Class26 aClass26_3078;
	public static Class26 aClass26_3080 = RS2Font.getRs2PreparedString(
			"Loading)3)3)3", false);
	public static Class26 aClass26_3081;
	public static Class26 aClass26_3085;
	public static Class26 aClass26_3086;
	public static Class26 aClass26_3089;
	public static Class26 aClass26_3090;
	public static Class26 aClass26_3091;
	public static Class26 aClass26_3094;
	public static Class26 aClass26_3095;
	public static Class26 aClass26_3096;
	public static Class65 aClass65_3075;
	public static Font aFont3087;
	public static int anInt3073;
	public static int anInt3077;
	public static int anInt3079;
	public static int anInt3084;
	public static int anInt3088;
	public static int anInt3092;
	public static int anInt3093;
	public static int[] anIntArray3076 = { 16, 32, 64, 128 };
	static {
		aClass26_3078 = RS2Font.getRs2PreparedString(
				"Bitte starten Sie eine Mitgliedschaft", false);
		aClass26_3074 = aClass26_3080;
		aClass26_3081 = RS2Font.getRs2PreparedString("<br>(X", false);
		aBooleanArray3083 = new boolean[100];
		aClass65_3075 = new Class65();
		aClass26_3085 = RS2Font.getRs2PreparedString("Members object", false);
		anInt3088 = -1;
		aClass26_3086 = RS2Font.getRs2PreparedString("glow1:", false);
		anInt3093 = 0;
		aClass26_3095 = aClass26_3086;
		anInt3092 = 0;
		aClass26_3094 = aClass26_3086;
		aClass26_3091 = RS2Font.getRs2PreparedString(
				"Unexpected loginserver response)3", false);
		aClass26_3090 = RS2Font.getRs2PreparedString("titlebutton", false);
		aClass26_3089 = aClass26_3091;
		aClass26_3096 = aClass26_3085;
	}

	public static void method484(byte arg0, Class26 arg1, int arg2, int arg3,
			int arg4) {
		anInt3084++;
		RSInterface class4_sub13 = Class54.method1012(arg3, -1, arg4);
		if (class4_sub13 != null) {
			if (class4_sub13.anObjectArray2145 != null) {
				Class4_Sub14 class4_sub14 = new Class4_Sub14();
				class4_sub14.aClass4_Sub13_2282 = class4_sub13;
				class4_sub14.anObjectArray2267 = class4_sub13.anObjectArray2145;
				class4_sub14.aClass26_2276 = arg1;
				class4_sub14.anInt2269 = arg2;
				Class16.method721(1, class4_sub14);
			}
			boolean bool = true;
			if (class4_sub13.anInt2227 > 0)
				bool = RSFont.method71(-119, class4_sub13);
			if (bool
					&& Class4_Sub12.method278(75, arg2 - 1,
							Class54.method1011(class4_sub13, -22389))) {
				if (arg2 == 1) {
					Class76.anInt1559++;
					Class66.aClass4_Sub11_Sub1_1328.method264(113, -126);
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
				}
				if ((arg2 ^ 0xffffffff) == -3) {
					Class4_Sub15.anInt2293++;
					Class66.aClass4_Sub11_Sub1_1328.method264(37, -53);
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
				}
				if (arg2 == 3) {
					Class66.aClass4_Sub11_Sub1_1328.method264(134, -18);
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
					Class4_Sub24.anInt2440++;
				}
				if (arg2 == 4) {
					Class66.aClass4_Sub11_Sub1_1328.method264(137, 111);
					Class33.anInt705++;
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
				}
				if ((arg2 ^ 0xffffffff) == -6) {
					Class35.anInt727++;
					Class66.aClass4_Sub11_Sub1_1328.method264(140, -12);
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
				}
				if (arg2 == 6) {
					Class66.aClass4_Sub11_Sub1_1328.method264(210, -91);
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class4_Sub21.anInt2369++;
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
				}
				if (arg2 == 7) {
					Class66.aClass4_Sub11_Sub1_1328.method264(148, -82);
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
					Class4_Sub24.anInt2437++;
				}
				if (arg2 == 8) {
					Class66.aClass4_Sub11_Sub1_1328.method264(104, -100);
					Class4_Sub20_Sub7_Sub5.anInt3401++;
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
				}
				if ((arg2 ^ 0xffffffff) == -10) {
					anInt3073++;
					Class66.aClass4_Sub11_Sub1_1328.method264(9, -61);
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
				}
				if (arg2 == 10) {
					Class46.anInt933++;
					Class66.aClass4_Sub11_Sub1_1328.method264(28, 110);
					Class66.aClass4_Sub11_Sub1_1328.method236(181, arg3);
					Class66.aClass4_Sub11_Sub1_1328.method207(arg4, 2018779304);
				}
			}
		}
	}

	public static void method487(boolean arg0) {
		try {
			aClass26_3085 = null;
			aClass26_3074 = null;
			aClass26_3094 = null;
			aClass26_3091 = null;
			aClass26_3096 = null;
			aFont3087 = null;
			aClass26_3090 = null;
			aClass26_3078 = null;
			aClass26_3081 = null;
			anIntArray3076 = null;
			if (arg0 == true) {
				aBooleanArray3083 = null;
				aClass26_3086 = null;
				aClass26_3089 = null;
				aClass26_3095 = null;
				aClass26_3080 = null;
				aClass65_3075 = null;
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "mb.C("
					+ arg0 + ')');
		}
	}

	public int anInt3082 = 0;

	public void method485(StreamBuffer arg0, int arg1) {
		try {
			anInt3077++;
			if (arg1 < 106)
				aClass26_3091 = null;
			for (;;) {
				int i = arg0.get();
				if (i == 0)
					break;
				method486(-126, arg0, i);
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("mb.A("
					+ (arg0 != null ? "{...}" : "null") + ',' + arg1 + ')'));
		}
	}

	public void method486(int arg0, StreamBuffer arg1, int arg2) {
		anInt3079++;
		if (arg0 <= -40) {
			if ((arg2 ^ 0xffffffff) == -3)
				anInt3082 = arg1.method209((byte) -123);
		}
	}
}
