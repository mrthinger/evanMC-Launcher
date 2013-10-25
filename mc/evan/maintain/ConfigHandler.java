package mc.evan.maintain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import mc.evan.reference.Links;
import mc.evan.util.DirHelper;
import mc.evan.util.FileUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigHandler {

	public static String configDir;
	public static String cache;
	public static String jsonLauncherConfig;
	public static String jsonPackConfig;
	public static JSONObject LauncherVersion;
	public static JSONObject CurrentVersions;
	public static JSONObject LatestVersions;

	public static float launcherLatestVersion;

	public static int packCurrentVersion;
	public static int packLatestVersion;
	public static String packLatestLink;

	public static String forgeCurrentVersion;
	public static String forgeRequiredVersion;
	public static String forgeRequiredLink;

	public static String minecraftRequiredVersion;
	public static boolean isMinecraftRequiredVersion;
	
	public static String creditLink;
	
	public static String packName;
	public static String packDir;

	public static void preInit() {

		configDir = DirHelper.getEvanMCDir();
		cache = DirHelper.getCacheDir();
		jsonLauncherConfig = configDir + "launcher.json";
		jsonPackConfig = cache + "packinfo.json";

		readLauncherJSON();
		
		launcherLatestVersion = getLatestLauncherVersion();
	}
	
	public static void packinit(String packLink){
		
		// This is the config link (for open platforming)
		FileUtil.downloadFile(packLink, jsonPackConfig, true);

		readLatestJSON();
		FileUtil.sexyDelete(jsonPackConfig);

		packName = getPackName();
		packDir = DirHelper.getEvanMCDir() + packName + "/";
		
		minecraftRequiredVersion = getMinecraftRequiredVersion();
		isMinecraftRequiredVersion = isMinecraftRequiredVersion();

		creditLink = getCreditLink();
		

		packLatestVersion = getLatestPackVersion();
		packLatestLink = getLatestPackLink();


		forgeRequiredVersion = getRequiredForgeVersion();
		forgeRequiredLink = getRequiredForgeLink();
		
	}

	public static void postInit(){
		readPackJSON();
		packCurrentVersion = getCurrentPackVersion();
		forgeCurrentVersion = getCurrentForgeVersion();
	}
	
	private static void readPackJSON() {
	File packConfig = new File(ConfigHandler.packDir + "modpack.json");
	JSONParser parser = new JSONParser();

	try {
		FileReader file = new FileReader(packConfig);
		Object obj = parser.parse(file);

		CurrentVersions = (JSONObject) obj;
		
		file.close();

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	}
	private static void readLauncherJSON() {
		File jsonconfigfile = new File(jsonLauncherConfig);
		JSONParser parser = new JSONParser();

		try {
			FileReader file = new FileReader(jsonconfigfile);
			Object obj = parser.parse(file);

			LauncherVersion = (JSONObject) obj;
			
			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void readLatestJSON() {
		File jsonconfigfile = new File(jsonPackConfig);
		JSONParser parser = new JSONParser();

		try {
			FileReader file = new FileReader(jsonconfigfile);
			Object obj = parser.parse(file);

			LatestVersions = (JSONObject) obj;
			file.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static String getPackName() {
		String pName = LatestVersions.get("name").toString();
		return pName;
	}
	private static String getCreditLink() {
		String creditLink = LatestVersions.get("CreditLink").toString();
		return creditLink;
	}

	private static String getRequiredForgeLink() {
		String rversion = LatestVersions.get("RequiredForgeLink").toString();
		return rversion;
	}

	private static String getLatestPackLink() {
		String lversion = LatestVersions.get("LatestPackLink").toString();
		return lversion;
	}

	private static String getMinecraftRequiredVersion() {
		String rversion = LatestVersions.get("MinecraftVersion").toString();
		return rversion;
	}

	private static boolean isMinecraftRequiredVersion() {
		File cversion = new File(DirHelper.getMineDir() + "versions/" + minecraftRequiredVersion);
		if (cversion.exists()) {
			return true;
		} else {
			return false;
		}
	}

	private static float getLatestLauncherVersion() {

		FileUtil.downloadFile(Links.LATESTLAUNCHER_VERSION,
				configDir + "LatestLauncherVersion.txt", true);
		float lversion = Float.parseFloat(FileUtil.readConfig(configDir + "LatestLauncherVersion.txt"));
		FileUtil.sexyDelete(configDir + "LatestLauncherVersion.txt");
		return lversion;

	}

	private static int getCurrentPackVersion() {
		int cversion = Integer.parseInt(CurrentVersions.get("PackVersion").toString());
		return cversion;
	}

	private static int getLatestPackVersion() {

		int lversion = Integer.parseInt(LatestVersions.get("LatestPackVersion").toString());
		return lversion;

	}

	private static String getCurrentForgeVersion() {
		String cversion = CurrentVersions.get("ForgeVersion").toString();
		return cversion;
	}

	private static String getRequiredForgeVersion() {
		String rversion = LatestVersions.get("RequiredForgeVersion").toString();
		return rversion;
	}

}
