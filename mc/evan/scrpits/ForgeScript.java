package mc.evan.scrpits;

import java.io.File;
import java.io.IOException;

import mc.evan.maintain.ConfigHandler;
import mc.evan.maintain.ForgeMaintain;
import mc.evan.util.DirHelper;
import mc.evan.util.FileUtil;
import mc.evan.util.JSONHandler;

public class ForgeScript {

	@SuppressWarnings("deprecation")
	public static void run() {
		String oldForge = DirHelper.getCacheDir() + "Forge-" + ConfigHandler.forgeCurrentVersion
				+ " Installer.jar";
		String forgeInstaller = DirHelper.getCacheDir() + "Forge-"
				+ ConfigHandler.forgeRequiredVersion + " Installer.jar";
		File installedForge = new File(DirHelper.getMineDir() + "versions/"
				+ ConfigHandler.minecraftRequiredVersion + "-Forge"
				+ ConfigHandler.forgeRequiredVersion);

		// Delete's old forge if it exists
		File oldFile = new File(oldForge);
		if (oldFile.exists())
			FileUtil.sexyDelete(oldForge);

		// Gets and Sets Required Version Download Link
		System.out.println("Getting Required Forge Version Link...");
		String forgeURL = ConfigHandler.forgeRequiredLink;
		System.out.println("Link Found!");

		// Download Forge Installer
		System.out.println("Downloading Forge Installer");
		FileUtil.downloadFile(forgeURL, forgeInstaller, false);
		System.out.println("Downloaded Now Running...");

		// Run Forge Installer
		System.out.println("Choose Install Client then OK");
		try {
			Process ps = Runtime.getRuntime().exec(new String[] { "java", "-jar", forgeInstaller });
			ps.waitFor();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (installedForge.exists()) {

			System.out.println("Forge Installed!" + "\n");

			JSONHandler.addProfile(ConfigHandler.packName);
			
			ForgeMaintain.updateForgeVersionFile();

		} else {
			System.out.println("Forge Install Failed!");
			Thread.currentThread().destroy();
		}

	}
}
