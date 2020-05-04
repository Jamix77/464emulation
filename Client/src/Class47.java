/* Class47 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class47 {
	public static Class26 aClass26_947 = RS2Font.getRs2PreparedString("Jul",
			false);
	public static Class26 aClass26_948;
	public static Class26 aClass26_949;
	public static Class26 aClass26_950;
	public static Class26 aClass26_952 = RS2Font.getRs2PreparedString("May",
			false);
	public static Class26 aClass26_956;
	public static Class26 aClass26_961;
	public static Class26 aClass26_962;
	public static Class26 aClass26_963;
	public static Class26 aClass26_965;
	public static Class26 aClass26_966;
	public static Class26 aClass26_967;
	public static Class26 aClass26_970;
	public static Class26 aClass26_972;
	public static Class26[] aClass26Array954;
	public static Class50 aClass50_960;
	public static int anInt951;
	public static int anInt953;
	public static int anInt955;
	public static int anInt958;
	public static int anInt959;
	public static int anInt964;
	public static int anInt969;
	public static int anInt971;
	public static int[] regionIds;
	static {
		aClass26_948 = RS2Font.getRs2PreparedString("Mar", false);
		aClass26_950 = RS2Font.getRs2PreparedString("Nov", false);
		aClass26_949 = RS2Font.getRs2PreparedString("Jan", false);
		aClass26_961 = RS2Font.getRs2PreparedString("Ung-Ultiger Benutzername",
				false);
		aClass26_962 = RS2Font.getRs2PreparedString(":duelreq:", false);
		aClass26_963 = RS2Font.getRs2PreparedString("Apr", false);
		aClass26_970 = RS2Font.getRs2PreparedString("Feb", false);
		aClass26_967 = RS2Font.getRs2PreparedString("Aug", false);
		aClass26_956 = RS2Font.getRs2PreparedString("Sep", false);
		aClass26_972 = RS2Font.getRs2PreparedString("Oct", false);
		aClass26_965 = RS2Font.getRs2PreparedString("Dec", false);
		aClass26_966 = RS2Font.getRs2PreparedString("Jun", false);
		aClass26Array954 = new Class26[] { aClass26_949, aClass26_970,
				aClass26_948, aClass26_963, aClass26_952, aClass26_966,
				aClass26_947, aClass26_967, aClass26_956, aClass26_972,
				aClass26_950, aClass26_965 };
	}

	public static void method976(byte arg0) {
		try {
			if (arg0 <= 46)
				method978(-117, -18, false, 76, -117, 12);
			Class57.aClass66_1131.method1084(0);
			anInt971++;
			Class10.aClass66_299.method1084(0);
			Class80.aClass66_1639.method1084(0);
			Class4_Sub8.aClass66_1972.method1084(0);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "me.B("
					+ arg0 + ')');
		}
	}

	public static void method977(int arg0) {
		try {
			regionIds = null;
			if (arg0 != 0)
				method976((byte) -126);
			aClass26Array954 = null;
			aClass26_966 = null;
			aClass26_948 = null;
			aClass26_962 = null;
			aClass50_960 = null;
			aClass26_950 = null;
			aClass26_947 = null;
			aClass26_956 = null;
			aClass26_972 = null;
			aClass26_970 = null;
			aClass26_952 = null;
			aClass26_963 = null;
			aClass26_961 = null;
			aClass26_967 = null;
			aClass26_949 = null;
			aClass26_965 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "me.D("
					+ arg0 + ')');
		}
	}

	public static void method978(int arg0, int arg1, boolean arg2, int arg3,
			int arg4, int arg5) {
		try {
			anInt955++;
			if (Class53.anInt1079 != arg0
					|| (Class4_Sub17.anInt2324 ^ 0xffffffff) != (arg5 ^ 0xffffffff)
					|| ((arg3 ^ 0xffffffff) != (Class50.anInt1010 ^ 0xffffffff) && Class59.aBoolean1189)) {
				Class4_Sub17.anInt2324 = arg5;
				Class53.anInt1079 = arg0;
				Class50.anInt1010 = arg3;
				if (!Class59.aBoolean1189)
					Class50.anInt1010 = 0;
				Region.method50(25, 1);
				Class19.method735(true, Class7.aClass26_228, (byte) -126);
				int i = Class6.anInt217;
				int i_0_ = Class4_Sub20_Sub8.anInt2987;
				Class4_Sub20_Sub8.anInt2987 = (-6 + arg5) * 8;
				Class6.anInt217 = 8 * (-6 + arg0);
				int i_1_ = Class4_Sub20_Sub8.anInt2987 + -i_0_;
				i_0_ = Class4_Sub20_Sub8.anInt2987;
				int i_2_ = Class6.anInt217 - i;
				i = Class6.anInt217;
				for (int i_3_ = 0; i_3_ < 32768; i_3_++) {
					Class4_Sub20_Sub7_Sub1_Sub2 class4_sub20_sub7_sub1_sub2 = Class57.aClass4_Sub20_Sub7_Sub1_Sub2Array1151[i_3_];
					if (class4_sub20_sub7_sub1_sub2 != null) {
						for (int i_4_ = 0; i_4_ < 10; i_4_++) {
							class4_sub20_sub7_sub1_sub2.anIntArray3248[i_4_] -= i_2_;
							class4_sub20_sub7_sub1_sub2.anIntArray3247[i_4_] -= i_1_;
						}
						class4_sub20_sub7_sub1_sub2.anInt3267 -= i_1_ * 128;
						class4_sub20_sub7_sub1_sub2.anInt3270 -= 128 * i_2_;
					}
				}
				for (int i_5_ = 0; (i_5_ ^ 0xffffffff) > -2049; i_5_++) {
					Class4_Sub20_Sub7_Sub1_Sub1 class4_sub20_sub7_sub1_sub1 = (Class4_Sub20_Sub7_Sub4.aClass4_Sub20_Sub7_Sub1_Sub1Array3358[i_5_]);
					if (class4_sub20_sub7_sub1_sub1 != null) {
						for (int i_6_ = 0; i_6_ < 10; i_6_++) {
							class4_sub20_sub7_sub1_sub1.anIntArray3248[i_6_] -= i_2_;
							class4_sub20_sub7_sub1_sub1.anIntArray3247[i_6_] -= i_1_;
						}
						class4_sub20_sub7_sub1_sub1.anInt3270 -= i_2_ * 128;
						class4_sub20_sub7_sub1_sub1.anInt3267 -= i_1_ * 128;
					}
				}
				Class82.anInt1711 = arg3;
				int i_7_ = 104;
				int i_8_ = 0;
				Class4_Sub15.aClass4_Sub20_Sub7_Sub1_Sub1_2302.method385(arg1,
						arg2, 128, arg4);
				int i_9_ = 0;
				int i_10_ = 104;
				int i_11_ = 1;
				if (i_1_ < 0) {
					i_11_ = -1;
					i_10_ = -1;
					i_9_ = 103;
				}
				int i_12_ = 1;
				if ((i_2_ ^ 0xffffffff) > -1) {
					i_8_ = 103;
					i_12_ = -1;
					i_7_ = -1;
				}
				for (int i_13_ = i_8_; (i_7_ ^ 0xffffffff) != (i_13_ ^ 0xffffffff); i_13_ += i_12_) {
					for (int i_14_ = i_9_; i_14_ != i_10_; i_14_ += i_11_) {
						int i_15_ = i_2_ + i_13_;
						int i_16_ = i_14_ - -i_1_;
						for (int i_17_ = 0; (i_17_ ^ 0xffffffff) > -5; i_17_++) {
							if (i_15_ >= 0 && i_16_ >= 0 && i_15_ < 104
									&& (i_16_ ^ 0xffffffff) > -105)
								Class40.aClass65ArrayArrayArray787[i_17_][i_13_][i_14_] = (Class40.aClass65ArrayArrayArray787[i_17_][i_15_][i_16_]);
							else
								Class40.aClass65ArrayArrayArray787[i_17_][i_13_][i_14_] = null;
						}
					}
				}
				for (Class4_Sub7 class4_sub7 = (Class4_Sub7) Class4_Sub20_Sub7_Sub1_Sub2.aClass65_3618
						.method1071(-120); class4_sub7 != null; class4_sub7 = (Class4_Sub7) Class4_Sub20_Sub7_Sub1_Sub2.aClass65_3618
						.method1075(18485)) {
					class4_sub7.anInt1953 -= i_2_;
					class4_sub7.anInt1950 -= i_1_;
					if (class4_sub7.anInt1953 < 0
							|| (class4_sub7.anInt1950 ^ 0xffffffff) > -1
							|| class4_sub7.anInt1953 >= 104
							|| class4_sub7.anInt1950 >= 104)
						class4_sub7.method70(-315);
				}
				Class5.anInt175 = 0;
				Class18.anInt477 = -1;
				Class25.aBoolean582 = false;
				if ((Class1.anInt74 ^ 0xffffffff) != -1) {
					Class67.anInt1352 -= i_1_;
					Class1.anInt74 -= i_2_;
				}
				Class4_Sub20_Sub13.aClass65_3106.method1070((byte) -56);
				Class4_Sub20_Sub11.aClass65_3075.method1070((byte) -72);
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("me.C("
					+ arg0 + ',' + arg1 + ',' + arg2 + ',' + arg3 + ',' + arg4
					+ ',' + arg5 + ')'));
		}
	}

	public static void method981(int arg0) {
		try {
			anInt951++;
			Class4_Sub3.aClass66_1902.method1084(arg0);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "me.F("
					+ arg0 + ')');
		}
	}

	public static byte[] method982(int arg0, byte[] arg1) {
		try {
			if (arg0 != -24158)
				aClass26_948 = null;
			anInt969++;
			int i = arg1.length;
			byte[] is = new byte[i];
			Class21.method777(arg1, 0, is, 0, i);
			return is;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("me.A("
					+ arg0 + ',' + (arg1 != null ? "{...}" : "null") + ')'));
		}
	}

	public byte[] aByteArray973;

	public int[] anIntArray946;

	public int[] anIntArray957;

	public Class47(byte[] arg0) {
		try {
			int i = arg0.length;
			aByteArray973 = arg0;
			anIntArray957 = new int[8];
			int[] is = new int[33];
			anIntArray946 = new int[i];
			int i_28_ = 0;
			for (int i_29_ = 0; i_29_ < i; i_29_++) {
				int i_30_ = arg0[i_29_];
				if (i_30_ != 0) {
					int i_31_ = 1 << -i_30_ + 32;
					int i_32_ = is[i_30_];
					anIntArray946[i_29_] = i_32_;
					int i_33_;
					if ((i_31_ & i_32_) != 0)
						i_33_ = is[-1 + i_30_];
					else {
						for (int i_34_ = -1 + i_30_; i_34_ >= 1; i_34_--) {
							int i_35_ = is[i_34_];
							if ((i_32_ ^ 0xffffffff) != (i_35_ ^ 0xffffffff))
								break;
							int i_36_ = 1 << -i_34_ + 32;
							if ((i_35_ & i_36_) == 0)
								is[i_34_] = Class4_Sub20_Sub7_Sub1.method389(
										i_35_, i_36_);
							else {
								is[i_34_] = is[i_34_ - 1];
								break;
							}
						}
						i_33_ = i_31_ | i_32_;
					}
					is[i_30_] = i_33_;
					for (int i_37_ = i_30_ + 1; (i_37_ ^ 0xffffffff) >= -33; i_37_++) {
						if (i_32_ == is[i_37_])
							is[i_37_] = i_33_;
					}
					int i_38_ = 0;
					for (int i_39_ = 0; i_30_ > i_39_; i_39_++) {
						int i_40_ = -2147483648 >>> i_39_;
						if ((i_40_ & i_32_) == 0)
							i_38_++;
						else {
							if ((anIntArray957[i_38_] ^ 0xffffffff) == -1)
								anIntArray957[i_38_] = i_28_;
							i_38_ = anIntArray957[i_38_];
						}
						if ((i_38_ ^ 0xffffffff) <= (anIntArray957.length ^ 0xffffffff)) {
							int[] is_41_ = new int[anIntArray957.length * 2];
							for (int i_42_ = 0; ((anIntArray957.length ^ 0xffffffff) < (i_42_ ^ 0xffffffff)); i_42_++)
								is_41_[i_42_] = anIntArray957[i_42_];
							anIntArray957 = is_41_;
						}
						i_40_ >>>= 1;
					}
					anIntArray957[i_38_] = i_29_ ^ 0xffffffff;
					if ((i_38_ ^ 0xffffffff) <= (i_28_ ^ 0xffffffff))
						i_28_ = i_38_ - -1;
				}
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					("me.<init>(" + (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public int method979(byte[] arg0, byte[] arg1, int arg2, int arg3,
			int arg4, int arg5) {
		try {
			arg3 += arg4;
			int i = 0;
			int i_18_ = arg2 << 1528920803;
			anInt953++;
			if (arg5 != 7)
				method981(81);
			for (/**/; arg3 > arg4; arg4++) {
				int i_19_ = arg1[arg4] & 0xff;
				int i_20_ = anIntArray946[i_19_];
				int i_21_ = aByteArray973[i_19_];
				if ((i_21_ ^ 0xffffffff) == -1)
					throw new RuntimeException("No codeword for data value "
							+ i_19_);
				int i_22_ = i_18_ >> 1849550659;
				int i_23_ = 0x7 & i_18_;
				i_18_ += i_21_;
				int i_24_ = (-1 + i_21_ + i_23_ >> 1634935203) + i_22_;
				i &= -i_23_ >> 1200394431;
				i_23_ += 24;
				arg0[i_22_] = (byte) (i = Class4_Sub20_Sub7_Sub1.method389(i,
						i_20_ >>> i_23_));
				if ((i_22_ ^ 0xffffffff) > (i_24_ ^ 0xffffffff)) {
					i_22_++;
					i_23_ -= 8;
					arg0[i_22_] = (byte) (i = i_20_ >>> i_23_);
					if (i_22_ < i_24_) {
						i_22_++;
						i_23_ -= 8;
						arg0[i_22_] = (byte) (i = i_20_ >>> i_23_);
						if ((i_22_ ^ 0xffffffff) > (i_24_ ^ 0xffffffff)) {
							i_22_++;
							i_23_ -= 8;
							arg0[i_22_] = (byte) (i = i_20_ >>> i_23_);
							if (i_24_ > i_22_) {
								i_22_++;
								i_23_ -= 8;
								arg0[i_22_] = (byte) (i = i_20_ << -i_23_);
							}
						}
					}
				}
			}
			return -arg2 + (i_18_ + 7 >> 138127555);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("me.G("
					+ (arg0 != null ? "{...}" : "null") + ','
					+ (arg1 != null ? "{...}" : "null") + ',' + arg2 + ','
					+ arg3 + ',' + arg4 + ',' + arg5 + ')'));
		}
	}

	public int method980(byte[] arg0, int arg1, int arg2, byte[] arg3,
			boolean arg4, int arg5) {
		try {
			anInt959++;
			if ((arg1 ^ 0xffffffff) == -1)
				return 0;
			int i = 0;
			arg1 += arg5;
			if (arg4 != false)
				aClass26_952 = null;
			int i_25_ = arg2;
			for (;;) {
				byte i_26_ = arg0[i_25_];
				if ((i_26_ ^ 0xffffffff) > -1)
					i = anIntArray957[i];
				else
					i++;
				int i_27_;
				if ((i_27_ = anIntArray957[i]) < 0) {
					arg3[arg5++] = (byte) (i_27_ ^ 0xffffffff);
					if ((arg1 ^ 0xffffffff) >= (arg5 ^ 0xffffffff))
						break;
					i = 0;
				}
				if ((i_26_ & 0x40) == 0)
					i++;
				else
					i = anIntArray957[i];
				if ((i_27_ = anIntArray957[i]) < 0) {
					arg3[arg5++] = (byte) (i_27_ ^ 0xffffffff);
					if (arg1 <= arg5)
						break;
					i = 0;
				}
				if ((i_26_ & 0x20) != 0)
					i = anIntArray957[i];
				else
					i++;
				if ((i_27_ = anIntArray957[i]) < 0) {
					arg3[arg5++] = (byte) (i_27_ ^ 0xffffffff);
					if (arg5 >= arg1)
						break;
					i = 0;
				}
				if ((0x10 & i_26_ ^ 0xffffffff) != -1)
					i = anIntArray957[i];
				else
					i++;
				if ((i_27_ = anIntArray957[i]) < 0) {
					arg3[arg5++] = (byte) (i_27_ ^ 0xffffffff);
					if ((arg1 ^ 0xffffffff) >= (arg5 ^ 0xffffffff))
						break;
					i = 0;
				}
				if ((0x8 & i_26_ ^ 0xffffffff) == -1)
					i++;
				else
					i = anIntArray957[i];
				if ((i_27_ = anIntArray957[i]) < 0) {
					arg3[arg5++] = (byte) (i_27_ ^ 0xffffffff);
					if (arg5 >= arg1)
						break;
					i = 0;
				}
				if ((0x4 & i_26_ ^ 0xffffffff) == -1)
					i++;
				else
					i = anIntArray957[i];
				if (((i_27_ = anIntArray957[i]) ^ 0xffffffff) > -1) {
					arg3[arg5++] = (byte) (i_27_ ^ 0xffffffff);
					if (arg5 >= arg1)
						break;
					i = 0;
				}
				if ((0x2 & i_26_ ^ 0xffffffff) != -1)
					i = anIntArray957[i];
				else
					i++;
				if ((i_27_ = anIntArray957[i]) < 0) {
					arg3[arg5++] = (byte) (i_27_ ^ 0xffffffff);
					if ((arg1 ^ 0xffffffff) >= (arg5 ^ 0xffffffff))
						break;
					i = 0;
				}
				if ((i_26_ & 0x1) == 0)
					i++;
				else
					i = anIntArray957[i];
				if (((i_27_ = anIntArray957[i]) ^ 0xffffffff) > -1) {
					arg3[arg5++] = (byte) (i_27_ ^ 0xffffffff);
					if (arg5 >= arg1)
						break;
					i = 0;
				}
				i_25_++;
			}
			return i_25_ + 1 + -arg2;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("me.E("
					+ (arg0 != null ? "{...}" : "null") + ',' + arg1 + ','
					+ arg2 + ',' + (arg3 != null ? "{...}" : "null") + ','
					+ arg4 + ',' + arg5 + ')'));
		}
	}
}
