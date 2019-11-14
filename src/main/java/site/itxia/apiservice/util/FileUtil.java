package site.itxia.apiservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author zhenxi
 */
@Component
public class FileUtil {

    /**
     * 存储文件的根目录.
     */
    private static String rootPath = null;

    /**
     * 从配置文件中读取.
     */
    @Value("${itxia.upload.path}")
    private void setRootPath(String path) {
        rootPath = path;
        //创建文件夹
        var dir = new File(rootPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String toAbsolutePath(String fileName) {
        return rootPath + "/" + fileName;
    }

    /**
     * 写入文件.
     *
     * @param fileName
     * @param bytes
     * @return
     */
    public static boolean writeFile(String fileName, byte[] bytes) {
        var file = new File(toAbsolutePath(fileName));
        try (var stream = new FileOutputStream(file)) {
            stream.write(bytes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取文件.
     *
     * @param fileName
     * @return
     */
    public static byte[] readFile(String fileName) {
        try (var stream = getFileInputStream(fileName)) {
            return stream.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream getFileInputStream(String fileName) throws IOException {
        var file = new File(toAbsolutePath(fileName));
        return new FileInputStream(file);
    }

}
