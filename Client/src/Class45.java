/* Class45 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.util.Calendar;

public abstract class Class45 {
	public static Calendar aCalendar919;
	public static Class26 aClass26_918 = RS2Font.getRs2PreparedString("Mem:",
			false);
	public static Class26 aClass26_921 = RS2Font.getRs2PreparedString(
			"Schlie-8en", false);
	public static Class26 aClass26_922;
	public static Class26 aClass26_924 = RS2Font.getRs2PreparedString(
			"Lade Sprites )2 ", false);
	public static Class26 aClass26_925;
	public static int anInt920 = 2301979;
	public static int anInt923 = 0;
	public static int anInt926;

	static {
		aClass26_922 = RS2Font.getRs2PreparedString(" <col=ffff00>", false);
		aClass26_925 = RS2Font.getRs2PreparedString("<col=ff7000>", false);
		aCalendar919 = Calendar.getInstance();
	}

	public static void method972(int arg0) {
		do {
			try {
				aCalendar919 = null;
				aClass26_921 = null;
				aClass26_918 = null;
				aClass26_922 = null;
				aClass26_925 = null;
				aClass26_924 = null;
				if (arg0 <= -109)
					break;
				method972(102);
			} catch (RuntimeException runtimeexception) {
				throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
						"lf.C(" + arg0 + ')');
			}
			break;
		} while (false);
	}

	public abstract void method970(int i, byte[] is);

	public abstract byte[] method971(boolean bool);
}
