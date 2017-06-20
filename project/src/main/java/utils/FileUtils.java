package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public final class FileUtils {
	private FileUtils(){}

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
}
