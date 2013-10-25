package mc.evan.maintain;

import java.io.File;


public class PackMaintain {
	
	public static boolean isPackLatestVersion() {


		if (ConfigHandler.packCurrentVersion < ConfigHandler.packLatestVersion) {
			return false;
		} else {
			return true;
		}

	}

	public static void updatePackVersionFile() {
		File packConfig = new File(ConfigHandler.packDir + "modpack.json");
		VersionsMaintain.updateVersion(packConfig.getAbsolutePath() ,"PackVersion",  new Integer (ConfigHandler.packLatestVersion).toString());
		
	}
}
