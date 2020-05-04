/* Applet_Sub1 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Method;
import java.net.URL;

public abstract class RSApplet extends Applet implements Runnable,
		FocusListener, WindowListener {
	/**
	 * 
	 */
	public static boolean mouseWheelDown;
	private static final long serialVersionUID = -6646571432138758395L;
	public static boolean aBoolean44;
	public static byte[][] regionsData;
	public static Class26 aClass26_11;
	public static Class26 aClass26_15 = RS2Font.getRs2PreparedString(
			"headicons_hint", false);
	public static Class26 aClass26_2 = RS2Font.getRs2PreparedString(
			"Willkommen auf RuneScape", false);
	public static Class26 aClass26_20 = (RS2Font.getRs2PreparedString(
			"und die Schaltfl-=che (WSpielkonto erstellen(W am", false));
	public static Class26 aClass26_25;
	public static Class26 aClass26_28 = RS2Font.getRs2PreparedString(
			"wishes to trade with you)3", false);
	public static Class4_Sub20_Sub12_Sub1[] aClass4_Sub20_Sub12_Sub1Array43;
	public static int anInt1;
	public static int anInt10;
	public static int anInt12;
	public static int anInt13;
	public static int anInt14;
	public static int anInt16;
	public static int anInt17;
	public static int anInt18;
	public static int anInt21;
	public static int anInt22;
	public static int anInt23;
	public static int anInt24;
	public static int anInt27;
	public static int anInt29;
	public static int anInt3;
	public static int anInt30;
	public static int anInt31;
	public static int anInt32;
	public static int anInt33;
	public static int anInt34;
	public static int anInt35;
	public static int anInt36;
	public static int anInt37;
	public static int anInt38;
	public static int anInt39;
	public static int anInt4;
	public static int anInt40;
	public static int anInt41;
	public static int anInt42;
	public static int anInt5;
	public static int anInt6 = 0;
	public static int anInt7;
	public static int anInt8;
	public static int anInt9;
	static {
		anInt23 = 0;
		anInt12 = 0;
		aClass26_25 = RS2Font.getRs2PreparedString("logo", false);
		aClass26_11 = aClass26_28;
	}

	public static void method15(int arg0) {
		try {
			aClass26_20 = null;
			aClass26_15 = null;
			aClass26_11 = null;
			regionsData = null;
			aClass4_Sub20_Sub12_Sub1Array43 = null;
			if (arg0 >= -62)
				regionsData = null;
			aClass26_25 = null;
			aClass26_28 = null;
			aClass26_2 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "we.K("
					+ arg0 + ')');
		}
	}

	public static void method20(int arg0, Class26 arg1, boolean arg2, int arg3,
			Class26 arg4, Class19 arg5) {
		try {
			anInt30++;
			if (arg3 == 21332) {
				int i = arg5.method754(arg1, arg3 + -21331);
				int i_1_ = arg5.method747(false, arg4, i);
				AppletListener.method1151(arg0, -2, arg5, i_1_, i, arg2);
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("we.N("
					+ arg0 + ',' + (arg1 != null ? "{...}" : "null") + ','
					+ arg2 + ',' + arg3 + ','
					+ (arg4 != null ? "{...}" : "null") + ','
					+ (arg5 != null ? "{...}" : "null") + ')'));
		}
	}

	public static void providesignlink(Signlink arg0) {
		try {
			anInt5++;
			Class77.aClass75_1597 = Class43.aClass75_872 = arg0;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.providesignlink("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public boolean aBoolean19 = false;

	@Override
	public void destroy() {
		try {
			anInt36++;
			if (this == Class4_Sub22.anApplet_Sub1_2401 && !Class27.aBoolean616) {
				Class50.aLong1027 = Class52.method1002((byte) -42);
				Class25.method799(5000L, 10);
				Class77.aClass75_1597 = null;
				method18(1000);
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					"we.destroy(" + ')');
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		try {
			Class32.aBoolean702 = true;
			anInt27++;
			Class4_Sub20_Sub4.aBoolean2849 = true;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.focusGained("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		try {
			Class32.aBoolean702 = false;
			anInt1++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.focusLost("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public AppletContext getAppletContext() {
		try {
			anInt42++;
			if (Class26.aFrame1786 != null)
				return null;
			if (Class43.aClass75_872 != null
					&& Class43.aClass75_872.applet != this)
				return Class43.aClass75_872.applet.getAppletContext();
			return super.getAppletContext();
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					("we.getAppletContext(" + ')'));
		}
	}

	public URL getCodeBase() {
		try {
			anInt38++;
			if (Class26.aFrame1786 != null)
				return null;
			if (Class43.aClass75_872 != null
					&& Class43.aClass75_872.applet != this)
				return Class43.aClass75_872.applet.getCodeBase();
			return super.getCodeBase();
		} catch (RuntimeException runtimeexception) {
			throw runtimeexception;
		}
	}

	public URL getDocumentBase() {
		try {
			anInt10++;
			if (Class26.aFrame1786 != null)
				return null;
			if (Class43.aClass75_872 != null
					&& this != Class43.aClass75_872.applet)
				return Class43.aClass75_872.applet.getDocumentBase();
			return super.getDocumentBase();
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					("we.getDocumentBase(" + ')'));
		}
	}

	public String getParameter(String arg0) {
		try {
			anInt16++;
			if (Class26.aFrame1786 != null)
				return null;
			if (Class43.aClass75_872 != null
					&& Class43.aClass75_872.applet != this)
				return Class43.aClass75_872.applet.getParameter(arg0);
			return super.getParameter(arg0);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.getParameter("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public abstract void init();

	public synchronized void method11(int arg0) {
		try {
			Container container;
			if (Class26.aFrame1786 == null)
				container = Class43.aClass75_872.applet;
			else
				container = Class26.aFrame1786;
			anInt34++;
			if (Class4_Sub20_Sub7_Sub5.runeCanvas != null) {
				Class4_Sub20_Sub7_Sub5.runeCanvas.removeFocusListener(this);
				container.remove(Class4_Sub20_Sub7_Sub5.runeCanvas);
			}
			Class4_Sub20_Sub7_Sub5.runeCanvas = new RSCanvas(this);
			container.add(Class4_Sub20_Sub7_Sub5.runeCanvas);
			Class4_Sub20_Sub7_Sub5.runeCanvas.setSize(Class58.anInt1160,
					Class57.anInt1138);
			Class4_Sub20_Sub7_Sub5.runeCanvas.setVisible(true);
			if (Class26.aFrame1786 == null)
				Class4_Sub20_Sub7_Sub5.runeCanvas.setLocation(0, 0);
			else {
				Insets insets = Class26.aFrame1786.getInsets();
				Class4_Sub20_Sub7_Sub5.runeCanvas.setLocation(insets.left,
						insets.top);
			}
			Class4_Sub20_Sub7_Sub5.runeCanvas.addFocusListener(this);
			Class4_Sub20_Sub7_Sub5.runeCanvas.requestFocus();
			Class4_Sub20_Sub4.aBoolean2849 = true;
			Class73.aBoolean1495 = false;
			Class9.aLong281 = Class52.method1002((byte) -42);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "we.H("
					+ arg0 + ')');
		}
	}

	public void method12(byte arg0) {
		try {
			long l = Class52.method1002((byte) -42);
			if (arg0 < 6)
				anInt23 = -110;
			anInt7++;
			long l_0_ = Class32.aLongArray699[Class41.anInt837];
			Class32.aLongArray699[Class41.anInt837] = l;
			if (l_0_ != 0L && l > l_0_) {
				int i = (int) (-l_0_ + l);
				Class73.anInt1497 = ((i >> -131121087) + 32000) / i;
			}
			Class41.anInt837 = 0x1f & 1 + Class41.anInt837;
			if (Class27.anInt625++ > 50) {
				Class4_Sub20_Sub4.aBoolean2849 = true;
				Class27.anInt625 -= 50;
				Class4_Sub20_Sub7_Sub5.runeCanvas.setSize(Class58.anInt1160,
						Class57.anInt1138);
				Class4_Sub20_Sub7_Sub5.runeCanvas.setVisible(true);
				if (Class26.aFrame1786 != null) {
					Insets insets = Class26.aFrame1786.getInsets();
					Class4_Sub20_Sub7_Sub5.runeCanvas.setLocation(insets.left,
							insets.top);
				} else
					Class4_Sub20_Sub7_Sub5.runeCanvas.setLocation(0, 0);
			}
			method13((byte) -103);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "we.O("
					+ arg0 + ')');
		}
	}

	public abstract void method13(byte i);

	public void method14(int arg0, int arg1, int arg2, int arg3, String arg4,
			boolean arg5, int arg6) {
		try {
			try {
				Class4_Sub20_Sub7_Sub3.anInt3347 = arg3;
				Class57.anInt1138 = arg2;
				Class4_Sub22.anApplet_Sub1_2401 = this;
				Class58.anInt1160 = arg1;
				Class26.aFrame1786 = new Frame();
				Class26.aFrame1786.setTitle("Jagex");
				Class26.aFrame1786.setResizable(arg5);
				Class26.aFrame1786.addWindowListener(this);
				Class26.aFrame1786.setVisible(true);
				Class26.aFrame1786.toFront();
				Insets insets = Class26.aFrame1786.getInsets();
				Class26.aFrame1786.setSize(insets.left + (arg1 + insets.right),
						insets.bottom + (arg2 - -insets.top));
				Class77.aClass75_1597 = Class43.aClass75_872 = new Signlink(
						true, null, arg6, arg4, arg0);
				Class43.aClass75_872.method1172(this, 1, 66);
			} catch (Exception exception) {
				Class4_Sub20_Sub7_Sub4.method422(exception, -77, null);
			}
			anInt35++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("we.I("
					+ arg0 + ',' + arg1 + ',' + arg2 + ',' + arg3 + ','
					+ (arg4 != null ? "{...}" : "null") + ',' + arg5 + ','
					+ arg6 + ')'));
		}
	}

	public abstract void method16(int i);

	public abstract void method17(int i);

	public synchronized void method18(int arg0) {
		try {
			anInt14++;
			if (!Class27.aBoolean616) {
				Class27.aBoolean616 = true;
				try {
					if (arg0 != 1000)
						anInt21 = -113;
					Class4_Sub20_Sub7_Sub5.runeCanvas
							.removeFocusListener(this);
				} catch (Exception exception) {
					/* empty */
				}
				try {
					method21(arg0 + -19075);
				} catch (Exception exception) {
					/* empty */
				}
				if (Class26.aFrame1786 != null) {
					try {
						System.exit(0);
					} catch (Throwable throwable) {
						/* empty */
					}
				}
				if (Class43.aClass75_872 != null) {
					try {
						Class43.aClass75_872.method1174(0);
					} catch (Exception exception) {
						/* empty */
					}
				}
				method16(-107);
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "we.C("
					+ arg0 + ')');
		}
	}

	public void method19(byte arg0, String arg1) {
		try {
			anInt24++;
			if (!aBoolean19) {
				aBoolean19 = true;
				System.out.println("error_game_" + arg1);
				try {
					getAppletContext().showDocument(
							new URL(getCodeBase(),
									("error_game_" + arg1 + ".ws")), "_self");
				} catch (Exception exception) {
					/* empty */
				}
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, ("we.F("
					+ arg0 + ',' + (arg1 != null ? "{...}" : "null") + ')'));
		}
	}

	public abstract void method21(int i);

	public void method22(byte arg0) {
		try {
			anInt9++;
			long l = Class52.method1002((byte) -42);
			if (arg0 < 25)
				anInt6 = 88;
			long l_2_ = Class43.aLongArray881[Class4_Sub1.anInt1865];
			if (l_2_ != 0L && l_2_ < l) {
				/* empty */
			}
			Class43.aLongArray881[Class4_Sub1.anInt1865] = l;
			Class4_Sub1.anInt1865 = 0x1f & 1 + Class4_Sub1.anInt1865;
			synchronized (this) {
				Region.aBoolean126 = Class32.aBoolean702;
			}
			method25(-19134);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "we.E("
					+ arg0 + ')');
		}
	}

	public void method23(int arg0, int arg1, byte arg2, int arg3, int arg4) {
		try {
			anInt33++;
			try {
				if (Class4_Sub22.anApplet_Sub1_2401 != null) {
					Class4_Sub20_Sub10.anInt3066++;
					if (Class4_Sub20_Sub10.anInt3066 >= 3)
						method19((byte) 100, "alreadyloaded");
					else
						getAppletContext().showDocument(getDocumentBase(),
								"_self");
				} else {
					Class4_Sub22.anApplet_Sub1_2401 = this;
					Class57.anInt1138 = arg1;
					Class4_Sub20_Sub7_Sub3.anInt3347 = arg0;
					Class58.anInt1160 = arg3;
					if (Class43.aClass75_872 == null)
						Class77.aClass75_1597 = Class43.aClass75_872 = new Signlink(
								false, this, arg4, null, 0);
					Class43.aClass75_872.method1172(this, 1, 14);
				}
			} catch (Exception exception) {
				Class4_Sub20_Sub7_Sub4.method422(exception, -53, null);
				method19((byte) 40, "crash");
			}
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					("we.B(" + arg0 + ',' + arg1 + ',' + arg2 + ',' + arg3
							+ ',' + arg4 + ')'));
		}
	}

	public boolean method24(int arg0) {
		try {
			String string = getDocumentBase().getHost().toLowerCase();
			anInt3++;
			if (string.equals("jagex.com") || string.endsWith(".jagex.com"))
				return true;
			if (string.equals("runescape.com")
					|| string.endsWith(".runescape.com"))
				return true;
			if (string.endsWith("127.0.0.1"))
				return true;
			for (/**/; (string.length() > 0
					&& string.charAt(-1 + string.length()) >= '0' && string
					.charAt(string.length() + -1) <= '9'); string = string
					.substring(0, string.length() + -1)) {
				/* empty */
			}
			if (string.endsWith("192.168.1."))
				return true;
			if (arg0 != 0)
				windowDeiconified(null);
			method19((byte) -87, "invalidhost");
			return false;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception, "we.L("
					+ arg0 + ')');
		}
	}

	public abstract void method25(int i);

	public synchronized void paint(Graphics arg0) {
		do {
			try {
				anInt18++;
				if (Class4_Sub22.anApplet_Sub1_2401 == this
						&& !Class27.aBoolean616) {
					Class4_Sub20_Sub4.aBoolean2849 = true;
					if (Signlink.jVersion == null
							|| !Signlink.jVersion.startsWith("1.5")
							|| (-Class9.aLong281
									+ Class52.method1002((byte) -42) <= 1000L))
						break;
					Rectangle rectangle = arg0.getClipBounds();
					if (rectangle == null
							|| (((rectangle.width ^ 0xffffffff) <= (Class58.anInt1160 ^ 0xffffffff)) && ((Class57.anInt1138 ^ 0xffffffff) >= (rectangle.height ^ 0xffffffff))))
						Class73.aBoolean1495 = true;
				}
			} catch (RuntimeException runtimeexception) {
				throw Class4_Sub20_Sub7_Sub4
						.method423(runtimeexception, ("we.paint("
								+ (arg0 != null ? "{...}" : "null") + ')'));
			}
			break;
		} while (false);
	}

	public void run() {
		try {
			anInt17++;
			try {
				if (Signlink.jVendor != null) {
					String string = Signlink.jVendor.toLowerCase();
					if ((string.indexOf("sun") ^ 0xffffffff) == 0
							&& string.indexOf("apple") == -1) {
						if ((string.indexOf("ibm") ^ 0xffffffff) != 0
								&& (Signlink.jVersion == null || Signlink.jVersion
										.equals("1.4.2"))) {
							method19((byte) 89, "wrongjava");
							return;
						}
					} else {
						String string_3_ = Signlink.jVersion;
						if (string_3_.equals("1.1")
								|| string_3_.startsWith("1.1.")
								|| string_3_.equals("1.2")
								|| string_3_.startsWith("1.2.")) {
							method19((byte) 22, "wrongjava");
							return;
						}
						Class4_Sub20.anInt2358 = 5;
					}
				}
				if (Class43.aClass75_872.applet != null) {
					Method method = Signlink.setFocusCycleRoot;
					if (method != null) {
						try {
							method.invoke(Class43.aClass75_872.applet,
									new Object[] { Boolean.TRUE });
						} catch (Throwable throwable) {
							/* empty */
						}
					}
				}
				method11(-3);
				Class62.aClass13_1225 = Class63.method1058(-113,
						Class58.anInt1160, Class4_Sub20_Sub7_Sub5.runeCanvas,
						Class57.anInt1138);
				method17(0);
				Class4_Sub20_Sub13.aClass14_3126 = Class4_Sub12.method276(-98);
				while (Class50.aLong1027 == 0L
						|| ((Class50.aLong1027 ^ 0xffffffffffffffffL) < (Class52
								.method1002((byte) -42) ^ 0xffffffffffffffffL))) {
					Class4_Sub10.anInt2024 = (Class4_Sub20_Sub13.aClass14_3126
							.method705(true, Class4_Sub20.anInt2358,
									Class31.anInt691));
					for (int i = 0; ((Class4_Sub10.anInt2024 ^ 0xffffffff) < (i ^ 0xffffffff)); i++)
						method22((byte) 65);
					method12((byte) 77);
					Class4_Sub20_Sub7_Sub4.method424((byte) -48,
							(Class4_Sub20_Sub7_Sub5.runeCanvas),
							Class43.aClass75_872);
				}
			} catch (Exception exception) {
				Class4_Sub20_Sub7_Sub4.method422(exception, -52, null);
				method19((byte) -127, "crash");
			}
			method18(1000);
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					"we.run(" + ')');
		}
	}

	public void start() {
		try {
			anInt31++;
			if (Class4_Sub22.anApplet_Sub1_2401 == this && !Class27.aBoolean616)
				Class50.aLong1027 = 0L;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					"we.start(" + ')');
		}
	}

	public void stop() {
		try {
			anInt8++;
			if (Class4_Sub22.anApplet_Sub1_2401 == this && !Class27.aBoolean616)
				Class50.aLong1027 = Class52.method1002((byte) -42) + 4000L;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					"we.stop(" + ')');
		}
	}

	public void update(Graphics arg0) {
		try {
			paint(arg0);
			anInt40++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					("we.update(" + (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public void windowActivated(WindowEvent arg0) {
		try {
			anInt13++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.windowActivated("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public void windowClosed(WindowEvent arg0) {
		try {
			anInt39++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.windowClosed("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public void windowClosing(WindowEvent arg0) {
		try {
			anInt41++;
			destroy();
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.windowClosing("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public void windowDeactivated(WindowEvent arg0) {
		try {
			anInt4++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					("we.windowDeactivated("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public void windowDeiconified(WindowEvent arg0) {
		try {
			anInt22++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4.method423(runtimeexception,
					("we.windowDeiconified("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public void windowIconified(WindowEvent arg0) {
		try {
			anInt32++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.windowIconified("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}

	public void windowOpened(WindowEvent arg0) {
		try {
			anInt37++;
		} catch (RuntimeException runtimeexception) {
			throw Class4_Sub20_Sub7_Sub4
					.method423(runtimeexception, ("we.windowOpened("
							+ (arg0 != null ? "{...}" : "null") + ')'));
		}
	}
}
