package mc.evan.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;

import mc.evan.LauncherMain;

public class DirHelper {

	public static boolean isMac() {
		if (getOS().equals("Mac OS X")) {
			return true;
		} else {
			return false;
		}
	}

	public static String getOS() {
		return System.getProperty("os.name");
	}

	public static String getUserDir() {
		return System.getProperty("user.home");
	}

	public static String getDesktopDir() {
		return getUserDir() + "/Desktop/";
	}

	public static String getMineDir() {
		if (isMac()) {
			return getUserDir() + "/Library/Application Support/minecraft/";

		} else {
			return getUserDir() + "/AppData/Roaming/.minecraft/";
		}

	}
	public static String getEvanMCDir() {
		if (isMac()) {
			return getUserDir() + "/Library/Application Support/evanMCLauncher/";

		} else {
			return getUserDir() + "/AppData/Roaming/.evanMCLauncher/";
		}

	}

	public static String getCacheDir() {
		String ld = getEvanMCDir() + "cache/";
		return ld;
	}

	public static String getRunningDir() {

		CodeSource codeSource = LauncherMain.class.getProtectionDomain().getCodeSource();
		File jarFile = null;
		try {
			jarFile = new File(codeSource.getLocation().toURI().getPath());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		String jarDir = jarFile.getParentFile().getPath() + "/";
		return jarDir;
	}
	public static File getRunningJar() {
		CodeSource codeSource = LauncherMain.class.getProtectionDomain().getCodeSource();
		File jarFile = null;
		try {
			jarFile = new File(codeSource.getLocation().toURI().getPath());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		File jarDir = new File(jarFile.getPath());
		return jarDir;
	}
	
	
	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}

		} else {
			// if file, then delete it
			file.delete();
		}
	}
}
