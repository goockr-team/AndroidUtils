/**
 * 
 */
package cxx.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @comment �ļ��������߰�
 * @author ning
 * 
 *         2014-4-14
 */
public class FileUtils {
	/**
	 * д�ı��ļ� ��Androidϵͳ�У��ļ������� /data/data/PACKAGE_NAME/files Ŀ¼��
	 * 
	 * @param context
	 * @param fileName
	 */
	public static void write(Context context, String fileName, String content) {
		if (content == null)
			content = "";

		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ�ı��ļ�
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ��ȡ�ı��ļ�
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String readFromAsset(Context context, String fileName) {
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String readInStream(InputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return null;
	}

	public static String readInputStream(InputStreamReader read)
	{     
	    String fileContent = "";
		try {
			BufferedReader reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent += line;
			}
			read.close();        
	    } catch (Exception e)
	    {         
	        e.printStackTrace();     
	    }     
	    return fileContent;   
	}
	public static File createFile(String folderPath, String fileName) {
		File destDir = new File(folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return new File(folderPath, fileName + fileName);
	}

	/**
	 * ���ֻ�дͼƬ
	 * 
	 * @param buffer
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile(byte[] buffer, String folder,
			String fileName) {
		boolean writeSucc = false;

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);

		String folderPath = "";
		if (sdCardExist) {
			folderPath = Environment.getExternalStorageDirectory()
					+ File.separator + folder + File.separator;
		} else {
			writeSucc = false;
		}

		File fileDir = new File(folderPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(folderPath + fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(buffer);
			writeSucc = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writeSucc;
	}

	/**
	 * �����ļ�����·����ȡ�ļ���
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * �����ļ��ľ���·����ȡ�ļ�������������չ��
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
				point);
	}

	/**
	 * ��ȡ�ļ���չ��
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * ��ȡ�ļ���С
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath) {
		long size = 0;

		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * ��ȡ�ļ���С
	 * 
	 * @param size
	 *            �ֽ�
	 * @return
	 */
	public static String getFileSize(long size) {
		if (size <= 0)
			return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float) size / 1024;
		if (temp >= 1024) {
			return df.format(temp / 1024) + "M";
		} else {
			return df.format(temp) + "K";
		}
	}

	/**
	 * ת���ļ���С
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * ��ȡĿ¼�ļ���С
	 * 
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // �ݹ���ü���ͳ��
			}
		}
		return dirSize;
	}

	/**
	 * ��ȡĿ¼�ļ�����
	 * 
	 * @param dir
	 * @return
	 */
	public long getFileList(File dir) {
		long count = 0;
		File[] files = dir.listFiles();
		count = files.length;
		for (File file : files) {
			if (file.isDirectory()) {
				count = count + getFileList(file);// �ݹ�
				count--;
			}
		}
		return count;
	}

	public static byte[] toBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			out.write(ch);
		}
		byte buffer[] = out.toByteArray();
		out.close();
		return buffer;
	}

	/**
	 * ����ļ��Ƿ����
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkFileExists(String name) {
		boolean status;
		if (!name.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + name);
			status = newPath.exists();
		} else {
			status = false;
		}
		return status;
	}

	/**
	 * ���·���Ƿ����
	 * 
	 * @param path
	 * @return
	 */
	public static boolean checkFilePathExists(String path) {
		return new File(path).exists();
	}

	/**
	 * ����SD����ʣ��ռ�
	 * 
	 * @return ����-1��˵��û�а�װsd��
	 */
	public static long getFreeDiskSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return (freeSpace);
	}

	/**
	 * �½�Ŀ¼
	 * 
	 * @param directoryName
	 * @return
	 */
	public static boolean createDirectory(String directoryName) {
		boolean status;
		if (!directoryName.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + directoryName);
			status = newPath.mkdir();
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * ����Ƿ�װSD��
	 * 
	 * @return
	 */
	public static boolean checkSaveLocationExists() {
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * ɾ��Ŀ¼(������Ŀ¼��������ļ�)
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteDirectory(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isDirectory()) {
				String[] listfile = newPath.list();
				// delete all files within the specified directory and then
				// delete the directory
				try {
					for (int i = 0; i < listfile.length; i++) {
						File deletedFile = new File(newPath.toString() + "/"
								+ listfile[i].toString());
						deletedFile.delete();
					}
					newPath.delete();
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
					status = false;
				}

			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * ɾ����Ŀ¼
	 * 
	 * ���� 0����ɹ� ,1 ����û��ɾ��Ȩ��, 2�����ǿ�Ŀ¼,3 ����δ֪����
	 * 
	 * @return
	 */
	public static int deleteBlankPath(String path) {
		File f = new File(path);
		if (!f.canWrite()) {
			return 1;
		}
		if (f.list() != null && f.list().length > 0) {
			return 2;
		}
		if (f.delete()) {
			return 0;
		}
		return 3;
	}

	/**
	 * ������
	 * 
	 * @param oldName
	 * @param newName
	 * @return
	 */
	public static boolean reNamePath(String oldName, String newName) {
		File f = new File(oldName);
		return f.renameTo(new File(newName));
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param filePath
	 */
	public static boolean deleteFileWithPath(String filePath) {
		SecurityManager checker = new SecurityManager();
		File f = new File(filePath);
		checker.checkDelete(filePath);
		if (f.isFile()) {
			f.delete();
			return true;
		}
		return false;
	}

	/**
	 * ��ȡSD���ĸ�Ŀ¼��ĩβ��\
	 * 
	 * @return
	 */
	public static String getSDRoot() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * �г�rootĿ¼��������Ŀ¼
	 * 
	 * @param root
	 * @return ����·��
	 */
	public static List<String> listPath(String root) {
		List<String> allDir = new ArrayList<String>();
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		checker.checkRead(root);
		if (path.isDirectory()) {
			for (File f : path.listFiles()) {
				if (f.isDirectory()) {
					allDir.add(f.getAbsolutePath());
				}
			}
		}
		return allDir;
	}

	/***
	 * 
	 * @param fileName
	 * @param pathName
	 * @param content
	 */
	public  static  void writeFileToSD(String fileName, String pathName, String content) {
	    String sdStatus = Environment.getExternalStorageState();
	    if(!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
	        Log.d("FileUtils", "SD card is not avaiable/writeable right now.");
	        return;  
	    }  
	    try {  
	        
	        File path = new File(pathName);
	        File file = new File(pathName + fileName);
	        if( !path.exists()) {  
	            Log.d("FileUtils", "Create the path:" + pathName);
	            path.mkdir();  
	        }  
	        if( !file.exists()) {  
	            Log.d("FileUtils", "Create the file:" + fileName);
	            file.createNewFile();  
	        }  
	        FileOutputStream stream = new FileOutputStream(file);
//	        String s = "this is a test string writing to file.";  
	        byte[] buf = content.getBytes();  
	        stream.write(buf);            
	        stream.close();  
	          
	    } catch(Exception e) {
	        Log.e("FileUtils", "Error on writeFilToSD.");
	        e.printStackTrace();  
	    }  
	} 
	
	
	/***
	 * 
	 * @param fileName
	 * @param content
	 */
	public  static  void writeFileToSD(String fileName, String content) {
	    String sdStatus = Environment.getExternalStorageState();
	    if(!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
	        Log.d("FileUtils", "SD card is not avaiable/writeable right now.");
	        return;  
	    }  
	    try {  
	        String pathName = fileName.substring(0,fileName.lastIndexOf("/"));
	        File path = new File(pathName);
	        File file = new File(fileName);
	        if( !path.exists()) {  
	            Log.d("FileUtils", "Create the path:" + pathName);
	            path.mkdir();  
	        }  
	        if( !file.exists()) {  
	            Log.d("FileUtils", "Create the file:" + fileName);
	            file.createNewFile();  
	        }  
	        FileOutputStream stream = new FileOutputStream(file);
	        byte[] buf = content.getBytes();  
	        stream.write(buf);            
	        stream.close();  
	          
	    } catch(Exception e) {
	        Log.e("FileUtils", "Error on writeFilToSD.");
	        e.printStackTrace();  
	    }  
	} 
	
	/**
	* ��һ��inputstream���������д��SD���� ��һ������ΪĿ¼�� �ڶ�������Ϊ�ļ���
	*/
	public static File write2SDFromInput(String path, InputStream inputstream) {
				File file = null;
				OutputStream output = null;
				try {
				file = createFile(path);
				output = new FileOutputStream(file);
				// 4kΪ��λ��ÿ4Kдһ��
				byte buffer[] = new byte[4 * 1024];
				int temp = 0;
				while ((temp = inputstream.read(buffer)) != -1) {
				// ��ȡָ����,��ֹд��û�õ���Ϣ
				output.write(buffer, 0, temp);
				}
				output.flush();
				} catch (IOException e) {
				e.printStackTrace();
				} finally {
				try {
				output.close();
				} catch (IOException e) {
				e.printStackTrace();
				}
				}
				return file;
	}
	
	public static File createFile(String fileName){
		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
		
	}
	
	public enum PathStatus {
		SUCCESS, EXITS, ERROR
	}

	/**
	 * ����Ŀ¼
	 * 
	 * @param newPath
	 */
	public static PathStatus createPath(String newPath) {
		File path = new File(newPath);
		if (path.exists()) {
			return PathStatus.EXITS;
		}
		if (path.mkdir()) {
			return PathStatus.SUCCESS;
		} else {
			return PathStatus.ERROR;
		}
	}

	/**
	 * ��ȡ·����
	 * 
	 * @return
	 */
	public static String getPathName(String absolutePath) {
		int start = absolutePath.lastIndexOf(File.separator) + 1;
		int end = absolutePath.length();
		return absolutePath.substring(start, end);
	}
	
	
	// �ж��Ƿ���sdcard
	public static boolean isHaveSdcard() {
		String sdcardState = Environment.getExternalStorageState();
		if (sdcardState.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
		
	public static String getPhotoPath() {
		if (!isHaveSdcard()) {
			return "";
		}
		String imageFilePath = Environment.getExternalStorageDirectory()
				+ "/yqy/photo/";

		String imageName = TimeUtils.getYyyymmddHHmmssFormat() + ".jpg";
		File out = new File(imageFilePath);
		if (!out.exists()) {
			out.mkdirs();
		}
		imageFilePath = imageFilePath + imageName;
		return imageFilePath;
	}
	
	public static String getEmojiFile() {
		if (!isHaveSdcard()) {
			return "";
		}
		String filePath = Environment.getExternalStorageDirectory()
				+ "/yqy/file/";

		File out = new File(filePath);
		if (!out.exists()) {
			out.mkdirs();
		}
		filePath = filePath + "emoji.txt";
		return filePath;
	}
	
	
	public static String getCompressImagePath() {
		if (!isHaveSdcard()) {
			return "";
		}
		String imageFilePath = Environment.getExternalStorageDirectory()
				+ "/yqy/compress/";

		String imageName = "compress" + ".jpg";
		File out = new File(imageFilePath);
		if (!out.exists()) {
			out.mkdirs();
		}
		imageFilePath = imageFilePath + imageName;
		return imageFilePath;
	}
	
	
	public static String getDownloadApkPath(String fileName) {
		if (!isHaveSdcard()) {
			return "";
		}
		String imageFilePath = "/sdcard/Download/";

		File out = new File(imageFilePath);
		if (!out.exists()) {
			out.mkdirs();
		}
		imageFilePath = imageFilePath + fileName;
		return imageFilePath;
	}
	 
}
