package mc.evan.scrpits;

import mc.evan.maintain.ConfigHandler;

public class PackScriptUpdate extends PackScript {
	/*
	 * For those of you wondering I originally had two "PackScripts", one for
	 * updating and one for installing. But in the end I thought it'd be easier
	 * to have one script and I didn't feel like refactoring that much code.
	 */
	public static void run() {
		String[] files = { ConfigHandler.packDir + "GunCus",
				ConfigHandler.packDir + "servers.dat" };

		downloadCredits();
		getLatestPack();
		downloadLatestPack();
		deleteOldFiles(files);
		unzipPack();
		updatePackVersionFile();
		
	}

}
