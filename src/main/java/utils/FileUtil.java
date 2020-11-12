package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * 文件工具类
 *
 * @author dujianxiao
 */

public class FileUtil {
	private static String filePath = FileUtil.getValue("global.properties", "filePath1");// 默认文件地址
	private static String filePath2 = FileUtil.getValue("global.properties", "filePath2");// 默认文件地址
	private static String filePath3 = FileUtil.getValue("global.properties", "filePath3");// 默认上传文件存放目录

	/**
	 * 等待文件下载完成
	 *
	 *            等待时间，单位秒
	 */
	public static void waitForFile(String fileName) {
		File file = new File(filePath2 + fileName);
		for (int i = 0; i < 200; i++) {
			if (file.exists() == false) {
				try {
					Thread.sleep(100);
					continue;
				} catch (InterruptedException e) {
				}
			} else {
				break;
			}
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		Boolean flag = false;
		try {
			File file = new File(filePath + fileName);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除文件 模糊匹配
	 *
	 * @param fileName
	 */
	public static void deleteFileLike(String fileName) {
		try {
			String[] fileNameArray = getFileNameArray();
			File[] files = getFileArray();
			for (int i = 0; i < fileNameArray.length; i++) {
				// 如果文件名中包含fileName，则删除此文件
				if (fileNameArray[i].indexOf(fileName) == 0) {
					files[i].delete();
				}
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 读取配置文件
	 *
	 * @param fileName
	 *            配置文件路径名
	 * @param key
	 *            配置文件中的key
	 * @return
	 */
	public static String getValue(String fileName, String key) {
		String file = fileName;
		Properties pro = new Properties();
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			try {
				pro.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return pro.getProperty(key);
	}

	/**
	 * 读取excel中单元格的值
	 *
	 * @param fileName
	 *            文件名
	 * @param rowIndex
	 *            行号
	 * @param cellnum
	 *            列号
	 * @return 单元格值
	 */
	public static String getCellValue(String fileName, int rowIndex, int cellnum) {
		File file = new File(filePath + fileName);
		POIFSFileSystem poifsFileSystem;
		HSSFRow row = null;
		String res = null;
		try {
			poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			row = hssfSheet.getRow(rowIndex);
			res = row.getCell(cellnum).getStringCellValue();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res.trim().replace(" ", "");
	}

	/**
	 * 获取excel中的行数
	 *
	 * @param fileName
	 *            文件名
	 * @return excel中的非数据行数－－如标题等(计算数据量时需要减去这些行数)
	 */
	public static int getExcelRow(String fileName, int num) {
		File file = new File(filePath + fileName);
		POIFSFileSystem poifsFileSystem;
		int begin = 0;
		int row = 0;
		try {
			poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			row = hssfSheet.getLastRowNum();
			begin = hssfSheet.getFirstRowNum();
			for (int i = begin; i <= row; i++) {
				if (hssfSheet.getRow(i).getCell(0) == null
						|| hssfSheet.getRow(i).getCell(0).getCellType() == HSSFCell.CELL_TYPE_BLANK) {
					row--;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return row + 1 - num;

	}

	/**
	 * word中是否包含某段文字
	 *
	 * @param fileName
	 * @param str
	 * @return true/false
	 */
	public static boolean readWord(String fileName, String str) {
		String text = "";
		text = getWordContent(fileName);
		if (text.indexOf(str) != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回word中的内容
	 *
	 * @param fileName
	 * @return
	 */
	public static String getWordContent(String fileName) {
		String text = "";
		File file = new File(filePath + fileName);
		// 2003
		try {
			FileInputStream stream = new FileInputStream(file);
			WordExtractor word = new WordExtractor(stream);
			text = word.getText();
			// 去掉word文档中的多个换行
			text = text.replaceAll("(\\r\\n){2,}", "\r\n");
			text = text.replaceAll("(\\n){2,}", "\n");
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;

	}

	/**
	 * 返回pdf中的内容
	 *
	 * @param fileName
	 * @return
	 */
	public static String getPDFContent(String fileName) {
		String content = null;
		File pdfFile = new File(filePath + fileName);
		PDDocument document = null;
		try {
			document = PDDocument.load(pdfFile);
			// 获取页码
			int pages = document.getNumberOfPages();
			// 读文本内容
			PDFTextStripper stripper = new PDFTextStripper();
			// 设置按顺序输出
			stripper.setSortByPosition(true);
			stripper.setStartPage(1);
			stripper.setEndPage(pages);
			content = stripper.getText(document);
			return content;
		} catch (Exception e) {
		}
		return content;
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param fileName
	 * @return true false
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(filePath2 + fileName);
		return file.exists();
	}

	/**
	 * 获取根目录下所有文件的数组
	 *
	 * @return
	 */
	public static File[] getFileArray() {
		File dir = new File(filePath2);
		File[] files = dir.listFiles();
		String[] fileList = new String[files.length];

		for (int i = 0; i < files.length; i++) {
			fileList[i] = files[i].getName();
		}
		return files;
	}

	/**
	 * 获取文件夹下所有文件名的数组
	 *
	 * @return
	 */
	public static String[] getFileNameArray(String fileName) {
		// 获取pathName的File对象
		File dir = new File(filePath2 + fileName);
		File[] files = dir.listFiles();
		String[] fileNameArray = new String[files.length];

		for (int i = 0; i < files.length; i++) {
			fileNameArray[i] = files[i].getName();
		}
		return fileNameArray;
	}

	/**
	 * 获取根目录下所有文件名的数组 根目录为：filePath2
	 *
	 * @return
	 */
	public static String[] getFileNameArray() {
		// 获取pathName的File对象
		File dir = new File(filePath2);
		File[] files = dir.listFiles();
		String[] fileNameArray = new String[files.length];

		for (int i = 0; i < files.length; i++) {
			fileNameArray[i] = files[i].getName();
		}
		return fileNameArray;
	}

	/**
	 * 修改文件名
	 *
	 * @param fileName
	 *            模糊匹配
	 * @param newFileName
	 */
	public static void renameFile(String fileName, String newFileName) {
		String[] fileNameArray = getFileNameArray();
		File[] files = getFileArray();
		for (int i = 0; i < fileNameArray.length; i++) {
			// 如果文件名中包含fileName，则删除此文件
			if (fileNameArray[i].indexOf(fileName) == 0) {
				files[i].renameTo(new File(filePath2 + newFileName));
			}
		}
	}

	/**
	 * 删除文件夹
	 *
	 * @param file
	 */
	public static void deleteFolder(File file) {
		if (file.exists()) {// 判断文件是否存在
			if (file.isFile()) {// 判断是否是文件
				file.delete();// 删除文件
			} else if (file.isDirectory()) {// 否则如果它是一个目录
				File[] files = file.listFiles();// 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
					deleteFolder(files[i]);// 把每个文件用这个方法进行迭代
				}
				file.delete();// 删除文件夹
			}
		} else {

		}
	}

	/**
	 * 文件上传
	 *
	 * @param fileName
	 */
	public static void upLoad(String fileName) {
		Runtime exe = Runtime.getRuntime();
		try {
			String str = filePath3 + fileName;
			exe.exec(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压zip格式压缩包

	public static void unzip(String fileName, String format) {
		try {
			Project p = new Project();
			Expand e = new Expand();
			e.setProject(p);
			e.setSrc(new File(filePath + fileName + "." + format));
			e.setOverwrite(false);
			e.setDest(new File(filePath + fileName));
			/*
			 * ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
			 * 所以解压缩时要制定编码格式

			e.setEncoding("gbk");
			e.execute();
		} catch (Exception e) {
			throw e;
		}
	}
		*/

	/**
	 * 解压rar格式压缩包。
	 */
	public static void unrar(String fileName) throws Exception {
		Archive a = null;
		FileOutputStream fos = null;
		try {
			a = new Archive(new File(filePath + fileName));
			FileHeader fh = a.nextFileHeader();
			while (fh != null) {
				if (!fh.isDirectory()) {
					// 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
					String compressFileName = fh.getFileNameString().trim();
					String destFileName = "";
					String destDirName = "";
					// 非windows系统
					if (File.separator.equals("/")) {
						destFileName = filePath + compressFileName.replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
						// windows系统
					} else {
						destFileName = filePath + compressFileName.replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));
					}
					// 2创建文件夹
					File dir = new File(destDirName);
					if (!dir.exists() || !dir.isDirectory()) {
						dir.mkdirs();
					}
					// 3解压缩文件
					fos = new FileOutputStream(new File(destFileName));
					a.extractFile(fh, fos);
					fos.close();
					fos = null;
				}
				fh = a.nextFileHeader();
			}
			a.close();
			a = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (a != null) {
				try {
					a.close();
					a = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解压缩

	public static void deCompress(String fileName, String format) throws Exception {
		// 保证文件夹路径最后是"/"或者"\"
		char lastChar = filePath.charAt(filePath.length() - 1);
		if (lastChar != '/' && lastChar != '\\') {
			filePath += File.separator;
		}
		// 根据类型，进行相应的解压缩
		String type = (filePath + fileName + "." + format)
				.substring((filePath + fileName + "." + format).lastIndexOf(".") + 1);
		if (type.equals("zip")) {
			unzip(fileName, format);
		} else if (type.equals("rar")) {
			unrar(fileName + "." + format);
		} else {
			throw new Exception("只支持zip和rar格式的压缩包！");
		}
	}
	 */

}