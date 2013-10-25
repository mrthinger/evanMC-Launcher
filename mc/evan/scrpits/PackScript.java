package mc.evan.scrpits;

import java.io.File;
import java.io.IOException;

import mc.evan.maintain.ConfigHandler;
import mc.evan.maintain.PackMaintain;
import mc.evan.util.FileUtil;

abstract public class PackScript {

	private static boolean downloaded = false;

	protected static String configtarget = ConfigHandler.packDir + "config";
	protected static String modstarget = ConfigHandler.packDir + "mods";

	protected static String tempPackURL;

	protected static File packCacheDir = new File(ConfigHandler.packDir
			+ "cache");
	protected static String tempPack = packCacheDir + "/"
			+ ConfigHandler.packName + " V" + ConfigHandler.packLatestVersion
			+ ".zip";

	protected static void getLatestPack() {
		// Gets and Sets Latest Version Download Link
		System.out.println("Getting Latest Pack Version Link...");
		tempPackURL = ConfigHandler.packLatestLink;
		System.out.println("Link Found!");
	}

	protected static void downloadLatestPack() {
		// Downloads Archive
		System.out.println("Downloading Pack... This may take a few minutes");
		if (FileUtil.downloadFile(tempPackURL, tempPack, false)) {
			System.out.println("Downloaded");
			downloaded = true;
		}
	}

	protected static void downloadCredits() {
		// Credits ;D
	
			System.out.println("Downloading " + ConfigHandler.packName
					+ " credits to the modpack's folder...");
			FileUtil.sexyDelete(ConfigHandler.packDir + ConfigHandler.packName
					+ " Credits.txt");
			FileUtil.downloadFile(ConfigHandler.creditLink,
					ConfigHandler.packDir + ConfigHandler.packName
							+ " Credits.txt", true);
			System.out.println("Downloaded Credits, Take a look at them!");
	}

	/*
	 * By default includes config and mod delete
	 */
	protected static void deleteOldFiles(String[] deleteFiles) {
		// Deletes Old Modpack Files

		System.out.println("Deleting old modpack files...");

		if (downloaded) {
			if (deleteFiles != null) {
				for (int i = 0; i < deleteFiles.length; i++) {
					File file = new File(deleteFiles[i]);
					if (file.exists()) {
						FileUtil.sexyDelete(deleteFiles[i]);
					}
				}
			}

			File cfgs = new File(configtarget);
			if (cfgs.exists()) {
				FileUtil.sexyDelete(configtarget);
			}

			File mods = new File(modstarget);
			if (mods.exists()) {
				FileUtil.sexyDelete(modstarget);
			}
		}
	}

	protected static void unzipPack() {
		// Unzips Files
		if (downloaded) {
			System.out.println("Unzipping...");
			try {
				FileUtil.unzip(tempPack, ConfigHandler.packDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Unzipped");
		}
	}

	/*
	 * By default includes config and mod move
	 */
	@Deprecated
	protected static void moveNewFiles(String[] fileArrayToMove,
			String[] fileArrayMoveTo) {
		// Moves New Minecraft Files/Folders
		if (downloaded) {
			if (fileArrayToMove != null && fileArrayMoveTo != null) {
				for (int i = 0; i < fileArrayToMove.length; i++) {
					FileUtil.move(fileArrayToMove[i], fileArrayMoveTo[i]);
				}
			}
		}
	}

	protected static void updatePackVersionFile() {
		if (downloaded) {
			// Updates Stored Version
			PackMaintain.updatePackVersionFile();
		}
	}

}
