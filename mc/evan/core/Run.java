package mc.evan.core;

import java.util.concurrent.Callable;

import mc.evan.gui.GUI;
import mc.evan.maintain.ConfigHandler;
import mc.evan.maintain.ForgeMaintain;
import mc.evan.maintain.LauncherMaintain;
import mc.evan.maintain.PackMaintain;
import mc.evan.reference.LauncherInfo;
import mc.evan.scrpits.ForgeScript;
import mc.evan.scrpits.PackScriptUpdate;
import mc.evan.util.JSONHandler;

@SuppressWarnings("rawtypes")
public class Run implements Callable {

	@Override
	public Object call() throws Exception {

		// Made by Evan P.
		System.out.println("evanMC Modpack Launcher V" + LauncherInfo.VERSION
				+ " by Evan P.");

		System.out.println("Launcher Update Notes: "
				+ LauncherInfo.UPDATE_NOTES);

		if (LauncherMaintain.isUpdaterLatestVersion()) {

			System.out.println("Launcher is up to date!");

			ConfigHandler.packinit(GUI.choice);

			// Message containing pack name
			System.out.println();
			for (int i = 0; i < ConfigHandler.packName.length() * 1.4; i++) {
				System.out.print("-");
			}
			System.out.println("\n" + ConfigHandler.packName);
			for (int i = 0; i < ConfigHandler.packName.length() * 1.4; i++) {
				System.out.print("-");
			}
			System.out.println("\n");

			System.out.println("Minecraft:" + "\n"
					+ "Checking Minecraft Version...");
			if (ConfigHandler.isMinecraftRequiredVersion) {
				System.out.println("Minecraft is Correct Version: "
						+ ConfigHandler.minecraftRequiredVersion + "\n");

				Setup.packSetup();
				ConfigHandler.postInit();

				System.out.println("Forge:" + "\n"
						+ "Checking Forge Version...");
				if (ForgeMaintain.isForgeRequiredVersion()) {

					System.out.println("Forge is up to date!" + "\n");

					System.out.println(ConfigHandler.packName + ":" + "\n"
							+ "Checking Pack Version...");
					if (PackMaintain.isPackLatestVersion()) {

						System.out.println("Pack is up to date!");
						System.out.println("\n" + "Done!");

						// If pack needs to be updated
					} else {
						updatePack();
					}
					// If forge needs to be updated
				} else {
					updateForge();

					// Re-check for Pack Update

					System.out.println(ConfigHandler.packName + ":" + "\n"
							+ "Checking Pack Version...");
					if (PackMaintain.isPackLatestVersion()) {
						System.out.println("Pack is up to date!");
						System.out.println("\n" + "Done!");
					} else {
						updatePack();
					}
				}
				// If Minecraft needs to be updated
			} else {
				promptInstallMinecraftVersion();
			}

			// If launcher needs to be updated
		} else {
			return true;

		}
		return false;
	}

	private static void promptInstallMinecraftVersion() {
		System.out.println("Please install Minecraft version "
				+ ConfigHandler.minecraftRequiredVersion
				+ " by running it, then run the launcher again");
	}

	private static void updateForge() {
		System.out.println("Forge Update Found!");

		System.out.println("Current: " + ConfigHandler.forgeCurrentVersion);
		System.out.println("Required: " + ConfigHandler.forgeRequiredVersion);

		// updates profile for pack in this
		ForgeScript.run();
	}

	public static void updateLauncher() {
		System.out.println("Current: " + LauncherInfo.VERSION);
		System.out.println("Latest: " + ConfigHandler.launcherLatestVersion);

		System.out.println("Downloading Latest Version of Launcher");
		LauncherMaintain.updateLauncher();

	}

	private static void updatePack() {
		// Update Found
		System.out.println(ConfigHandler.packName + " Modpack Update Found!");

		System.out.println("Current: " + ConfigHandler.packCurrentVersion);
		System.out.println("Latest: " + ConfigHandler.packLatestVersion);

		// Run Script
		PackScriptUpdate.run();

		// Add a profile for new pack
		System.out.println("Adding/Updating Profile for "
				+ ConfigHandler.packName);
		JSONHandler.addProfile(ConfigHandler.packName);

		// Says Done :)
		System.out.println("\n" + "Done!");
	}

}
