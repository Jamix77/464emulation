/* Class4_Sub20_Sub10 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class4_Sub20_Sub10 extends Class4_Sub20 {
	public static byte[][][] aByteArrayArrayArray3054;
	public static byte[][][] aByteArrayArrayArray3065;
	public static Class19 aClass19_3062;
	public static Class26 aClass26_3056;
	public static Class26 aClass26_3058;
	public static Class26 aClass26_3059;
	public static Class26 aClass26_3067;
	public static Class26 aClass26_3069;
	public static Class26 aClass26_3071;
	public static Class26 aClass26_3072;
	public static RSInterface aClass4_Sub13_3064;
	public static long aLong3063;
	public static int anInt3053;
	public static int anInt3055;
	public static int anInt3057 = 0;
	public static int anInt3060;
	public static int anInt3066;
	public static int anInt3068;
	static {
		aClass26_3056 = RS2Font.getRs2PreparedString("mapedge", false);
		anInt3055 = 0;
		anInt3060 = 0;
		aClass26_3058 = RS2Font.getRs2PreparedString("Welcome to RuneScape",
				false);
		aClass26_3059 = RS2Font.getRs2PreparedString(
				" is already on your ignore list", false);
		aLong3063 = 0L;
		anInt3066 = 0;
		aClass26_3067 = aClass26_3059;
		aClass26_3069 = aClass26_3058;
		aClass26_3071 = RS2Font.getRs2PreparedString("event_opbase", false);
		anInt3053 = 1;
		aClass26_3072 = RS2Font.getRs2PreparedString("<)4col>", false);
	}

	public static void method482(boolean arg0, long arg1) {
		do {
			try {
				anInt3068++;
				if (arg1 != 0L) {
					Class66.byteBuffer.putOpcode(218, 125);
					Class66.byteBuffer.putLong(189354448, arg1);
					Class43.anInt859++;
					if (arg0 == true)
						break;
					anInt3053 = -61;
				}
			} catch (RuntimeException runtimeexception) {
				throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
						("ma.B(" + arg0 + ',' + arg1 + ')'));
			}
			break;
		} while (false);
	}

	public static void method483(byte arg0) {
		try {
			aClass19_3062 = null;
			aClass26_3058 = null;
			aClass26_3071 = null;
			aByteArrayArrayArray3065 = null;
			aClass26_3069 = null;
			aClass26_3067 = null;
			aClass4_Sub13_3064 = null;
			if (arg0 >= -40)
				method482(true, -36L);
			aClass26_3072 = null;
			aClass26_3056 = null;
			aByteArrayArrayArray3054 = null;
			aClass26_3059 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "ma.A("
					+ arg0 + ')');
		}
	}

	public byte aByte3052;

	public Class19_Sub1 aClass19_Sub1_3061;

	public int anInt3070;
}
