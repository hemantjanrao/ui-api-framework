package org.hello.core.framework.utils;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


public class FileUtils {

	private static Logger log = Logger.getLogger(FileUtils.class);

	/**
	 * Method to copy file from resources to tmp folder
	 * @param recourseName Resource Name
	 * @return String
	 */
	public static String copyRecourseToTemporaryFolder(String recourseName) {

		File directory = Files.createTempDir();
		File file = new File(directory, recourseName);
		URL url = Resources.getResource(recourseName);
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
			Resources.copy(url, fileOutputStream);
		} catch (IOException e) {
			log.warn("Error copying the file", e);;
			throw new RuntimeException("Was not able to read file " + file, e);
		}

		return file.getPath();
	}

	/**
	 * @param cls Class Name
	 * @param resourceName Resource Name
	 * @return String
	 */
	public static String getResourcePath(Class<?> cls, String resourceName) {
		ClassLoader classLoader = cls.getClassLoader();

		URL resource = classLoader.getResource(resourceName);
		if (resource == null) {
			throw new IllegalArgumentException("file is not found! - ");
		} else {
			return new File(resource.getFile()).getAbsolutePath();
		}
	}
}
