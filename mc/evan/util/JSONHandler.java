package mc.evan.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import mc.evan.gui.GUI;
import mc.evan.maintain.ConfigHandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unchecked")
public class JSONHandler {

	public static void addProfile(String name) {
		String configfile = DirHelper.getMineDir() + "launcher_profiles.json";

		JSONParser parser = new JSONParser();

		JSONObject inject = new JSONObject();

		String packname = name;
		String forgeparam = ConfigHandler.minecraftRequiredVersion + "-Forge"
				+ ConfigHandler.forgeRequiredVersion;

		inject.put("name", packname);
		inject.put("lastVersionId", forgeparam);
		inject.put("javaArgs", "-Xmx3G -XX:MaxPermSize=128m");
		inject.put("gameDir", DirHelper.getEvanMCDir() + packname);

		try {

			Object obj = parser.parse(new FileReader(configfile));

			JSONObject jsonObject = (JSONObject) obj;

			JSONObject profiles = (JSONObject) jsonObject.get("profiles");

			profiles.put(name, inject);
			jsonObject.put("profiles", profiles);
			jsonObject.put("selectedProfile", packname);

			FileWriter file = new FileWriter(configfile);
			file.write(jsonObject.toJSONString());
			file.flush();
			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void savePack(HashMap<String, String> packEntry) {

		String configfile = DirHelper.getEvanMCDir() + "launcher.json";

		JSONParser parser = new JSONParser();

		HashMap<String, String> pack = packEntry;

		try {

			Object obj = parser.parse(new FileReader(configfile));

			JSONObject jsonObject = (JSONObject) obj;

			jsonObject.putAll(pack);

			FileWriter file = new FileWriter(configfile);
			file.write(jsonObject.toJSONString());
			file.flush();
			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public static void readPacks() {
		HashMap<String, String> packs = new HashMap<>();

		String configfile = DirHelper.getEvanMCDir() + "launcher.json";

		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader(configfile));

			packs = (HashMap<String, String>) obj;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		GUI.pack.putAll(packs);

	}
}
