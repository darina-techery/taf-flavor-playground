package utils;

import java.io.FileNotFoundException;
import java.net.URL;

public final class FileUtils {
	private FileUtils(){}

	public static String getResourceFilePathString(String relativeFilePath) throws FileNotFoundException {
		ClassLoader classLoader = FileUtils.class.getClassLoader();
		URL resource = classLoader.getResource(relativeFilePath);
		if (resource == null) {
			throw new FileNotFoundException("Resource file was not found by path "+relativeFilePath);
		}
		return resource.getPath();
	}
}
