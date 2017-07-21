package com.techery.dtat.utils;

import okhttp3.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public final class FileUtils {
	private static final Logger log = LogManager.getLogger();
	private FileUtils(){

	}

	public static String getResourceFilePath(String relativeFilePath) throws FileNotFoundException {
		ClassLoader classLoader = FileUtils.class.getClassLoader();
		URL resource = classLoader.getResource(relativeFilePath);
		if (resource == null) {
			throw new FileNotFoundException("Resource file was not found by path "+relativeFilePath);
		}
		return resource.getPath();
	}

	public static File getResourceFile(String relativeFilePath) {
		String path = null;
		try {
			path = getResourceFilePath(relativeFilePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not locate resource file at "+relativeFilePath);
		}
		return new File(path);
	}

	public static MediaType getMediaType(File f) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(f));
		String contentType = URLConnection.guessContentTypeFromStream(is);
		is.close();
		return MediaType.parse(contentType);
	}
}
