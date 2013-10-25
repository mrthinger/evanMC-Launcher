package mc.evan.maintain;

import java.io.File;

import mc.evan.util.DirHelper;

public class ForgeMaintain {
	public static boolean isForgeRequiredVersion() {
		int forgeCurrentV = Integer.parseInt(ConfigHandler.forgeCurrentVersion.replace(".", ""));
		int forgeRequiredV = Integer.parseInt(ConfigHandler.forgeRequiredVersion.replace(".", ""));

		File installedForge = new File(DirHelper.getMineDir() + "versions/"
				+ ConfigHandler.minecraftRequiredVersion + "-Forge"
				+ ConfigHandler.forgeRequiredVersion);



		if (installedForge.exists() & forgeCurrentV >= forgeRequiredV) {
			return true;
		} else {
			return false;

		}

	}

	public static void updateForgeVersionFile() {
		File packConfig = new File(ConfigHandler.packDir + "modpack.json");
		VersionsMaintain.updateVersion(packConfig.getAbsolutePath(), "ForgeVersion", ConfigHandler.forgeRequiredVersion);

	}
}
