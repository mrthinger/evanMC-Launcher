package mc.evan.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {

	public static int cpt = 0;
	public static Thread dlMonitor = new Thread(new DownloadMonitor());

	public static void createFile(String fileName) {

		try {

			File file = new File(fileName);

			file.createNewFile();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File Creation Unsuccesful");
		}
	}

	public static void renameFile(String orgFile, String newFile) {
		File ofile = new File(orgFile);
		File nfile = new File(newFile);
		ofile.renameTo(nfile);
	}

	public static void writeToFile(String file, String content) {
		FileWriter writertype;
		BufferedWriter bw;
		try {
			writertype = new FileWriter(file);
			bw = new BufferedWriter(writertype);

			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createFolder(String folderName) {

		try {

			File folder = new File(folderName);

			folder.mkdir();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Folder Creation Unsuccesful");
		}

	}

	public static boolean downloadFile(String link, String saveTo, boolean hidden) {
		boolean downloaded = false;
		int pg = 0;
		int tpg = 0;
		try {
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			FileOutputStream out = new FileOutputStream(saveTo);

			tpg = conn.getContentLength();
			tpg = (tpg / 1024);

			byte[] b = new byte[1024];
			int count;

			if (!hidden) {

				cpt = 0;
				DownloadMonitor.running = true;

			}

			while ((count = in.read(b)) >= 0) {
				out.write(b, 0, count);

				if (!hidden) {
					pg++;
					cpt = (int) ((float) pg / (float) tpg * (float) 100);

				}
			}

			if (!hidden) {
				DownloadMonitor.running = false;
				System.out.println("Downloading: 100 % / 100 %");
				cpt = 0;

			}

			out.flush();
			out.close();
			in.close();

			downloaded = true;
			return downloaded;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to Download");
			return downloaded;

		}
	}

	public static final void unzip(String zipLoc, String extractToLoc) throws IOException {

		File zip = new File(zipLoc);
		File extractTo = new File(extractToLoc);
		@SuppressWarnings("resource")
		ZipFile archive = new ZipFile(zip);
		Enumeration<? extends ZipEntry> e = archive.entries();
		while (e.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			File file = new File(extractTo, entry.getName());
			if (entry.isDirectory() && !file.exists()) {
				file.mkdirs();
			} else {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}

				InputStream in = archive.getInputStream(entry);
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));

				byte[] buffer = new byte[8192];
				int read;

				while (-1 != (read = in.read(buffer))) {
					out.write(buffer, 0, read);
				}
				in.close();
				out.close();
				out.flush();
			}
		}
	}

	public static String readConfig(String configlink) {
		BufferedReader read;
		String dlink = null;
		try {
			read = new BufferedReader(new FileReader(configlink));
			dlink = read.readLine();

			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dlink;
	}

	public static void move(String from, String to) {
		Path src = Paths.get(from);
		Path target = Paths.get(to);
		try {
			Files.move(src, target);
			System.out.println("Moved: " + from + " To: " + to);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(from + " Not Moved");
		}
	}

	public static void sexyDelete(String pathToFileDeletion) {
		try {
			DirHelper.delete(new File(pathToFileDeletion));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}