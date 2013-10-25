package mc.evan.util;

import java.util.HashMap;

import mc.evan.gui.GUI;
import mc.evan.maintain.ConfigHandler;

//Light Weight Pack Initializer
public class LWPI{

	private String packLink;
	
	public LWPI(String packLink){
		
		this.packLink = packLink;
		
	}
	

	public void run() {
		
		ConfigHandler.packinit(packLink);
		
		
		HashMap<String,String> pack = new HashMap<>();
		pack.put(ConfigHandler.packName, packLink);
		
		GUI.pack.remove("Loading...");
		GUI.pack.putAll(pack);
		GUI.refreshPacks();
		
		JSONHandler.savePack(pack);
		
		
	}

}
