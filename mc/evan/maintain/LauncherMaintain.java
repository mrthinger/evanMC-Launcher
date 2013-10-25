package mc.evan.maintain;

import java.io.IOException;

import mc.evan.reference.LauncherInfo;
import mc.evan.reference.Links;
import mc.evan.util.DirHelper;
import mc.evan.util.FileUtil;

public class LauncherMaintain {

	public static boolean isUpdaterLatestVersion() {

		System.out.println("Checking Launcher Version...");

		if (LauncherInfo.VERSION < ConfigHandler.launcherLatestVersion) {
			return false;
		} else {
			return true;
		}
	}

	public static void updateLauncher() {
		System.out.println("Updating Launcher... DO NOT QUIT");
		String jarPath = DirHelper.getRunningDir() + "evanMC_Launcher.jar";

		FileUtil.sexyDelete(DirHelper.getRunningJar().getAbsolutePath());
		FileUtil.downloadFile(Links.LATESTLAUNCHER_JAR, jarPath, true);

		try {
			Runtime.getRuntime().exec(new String[] { "java", "-jar", jarPath });
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}

}
