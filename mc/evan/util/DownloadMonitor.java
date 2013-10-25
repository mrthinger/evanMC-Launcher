package mc.evan.util;


public class DownloadMonitor implements Runnable {

	public static boolean running = false;

	@Override
	public void run() {
		monitor();

	}

	public static void monitor() {
		while (Thread.currentThread().isAlive()) {
			if (running == false) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			while (running == true) {
				int cpt = FileUtil.cpt;

				
				
				if (!(cpt == 0)) {
					System.out.println("Downloading: " + cpt + " % / 100 %");
				}
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}

}