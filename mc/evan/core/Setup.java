package mc.evan.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mc.evan.maintain.ConfigHandler;
import mc.evan.util.DirHelper;
import mc.evan.util.FileUtil;

import org.json.simple.JSONObject;

public class Setup {

	public static void init() {
		String mineDir = DirHelper.getMineDir();
		String configDir = DirHelper.getEvanMCDir();
		String cache = DirHelper.getCacheDir();
		String jsonConfig = configDir + "launcher.json";

		File mc = new File(mineDir);
		File cfg = new File(configDir);
		File ce = new File(cache);

		File jc = new File(jsonConfig);

		FileUtil.dlMonitor.start();
		
		if (!mc.exists()) {
			FileUtil.createFolder(mineDir);
		}

		if (!cfg.exists()) {
			FileUtil.createFolder(configDir);
		}
		if (!ce.exists()) {
			FileUtil.createFolder(cache);
		}

		if (!jc.exists()) {
			FileUtil.createFile(jsonConfig);
			JSONObject jsonObject = new JSONObject();
			//jsonObject.put("LauncherVersion", LauncherInfo.Version);

			try {
				FileWriter file = new FileWriter(jsonConfig);
				file.write(jsonObject.toJSONString());
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@SuppressWarnings("unchecked")
	protected static void packSetup() {

		File packFolder = new File(ConfigHandler.packDir);
		File packCacheDir = new File(ConfigHandler.packDir + "cache");
		File packConfig = new File(ConfigHandler.packDir + "modpack.json");
		File oldPackCache = new File(packCacheDir + "/" + ConfigHandler.packName + " V"
				+ ConfigHandler.packCurrentVersion + ".zip");
		if (!packFolder.exists()) {
			FileUtil.createFolder(packFolder.getAbsolutePath());
		}

		if (!packCacheDir.exists()) {
			FileUtil.createFolder(packCacheDir.getAbsolutePath());
		}
		if (!packConfig.exists()) {
			FileUtil.createFile(packConfig.getAbsolutePath());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("PackVersion", 0);
			jsonObject.put("ForgeVersion", 0);

			try {
				FileWriter file = new FileWriter(packConfig);
				file.write(jsonObject.toJSONString());
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (oldPackCache.exists()) {
			FileUtil.sexyDelete(oldPackCache.getAbsolutePath());
		}

	}

}
