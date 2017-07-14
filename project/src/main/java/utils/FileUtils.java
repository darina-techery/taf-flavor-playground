package utils;

import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.api.clients.SharedServicesClient;
import rest.api.services.RemoteDownloadAPI;
import retrofit2.Response;

import java.io.*;
import java.net.URL;

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

	public static File downloadRemoteFile(String url, String filename) throws IOException {
		final RemoteDownloadAPI remoteDownloadAPI = new SharedServicesClient().create(RemoteDownloadAPI.class);
		Response<ResponseBody> response = remoteDownloadAPI.downloadFileWithDynamicUrl(url).execute();
		if (response.isSuccessful()) {
			ResponseBody body = response.body();
			try {
				log.info("Writing requested resource to file started.");
				File imageFile = new File("target/downloads/"+filename);
				imageFile.mkdirs();
				InputStream inputStream = null;
				OutputStream outputStream = null;
				try {
					byte[] fileReader = new byte[4096];
//					long fileSize = body.contentLength();
//					long fileSizeDownloaded = 0;

					inputStream = body.byteStream();
					outputStream = new FileOutputStream(imageFile);

					while (true) {
						int read = inputStream.read(fileReader);
						if (read == -1) {
							break;
						}
						outputStream.write(fileReader, 0, read);
//						fileSizeDownloaded += read;
						log.info("File downloaded to: " + imageFile.getPath());
					}

					outputStream.flush();
					return imageFile;
				} catch (IOException e) {
					log.error("Failed to save downloaded file from URL " + url);
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					if (outputStream != null) {
						outputStream.close();
					}
				}
			} catch (IOException e) {
				log.error("Failed to download file by URL " + url);
			}
		}
		return null;
	}
}
