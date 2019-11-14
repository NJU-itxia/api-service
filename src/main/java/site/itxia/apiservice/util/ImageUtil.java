package site.itxia.apiservice.util;

import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author zhenxi
 */
public class ImageUtil {

    /**
     * 缩略图最大尺寸.
     */
    private static int maxImageSize = 480;

    /**
     * 从配置文件中读取.
     */
    @Value("${itxia.upload.max-image-size}")
    private void setMaxImageSize(int size) {
        maxImageSize = size;
    }

    /**
     * 将图片文件压缩为缩略图, 并保存为文件.
     *
     * @param inputStream 需要压缩的图片输入流
     * @return 缩略图byte[]
     */
    public static byte[] resize(InputStream inputStream) {
        try {
            BufferedImage img = ImageIO.read(inputStream);

            //计算缩略图尺寸(保持长宽比)
            int width = img.getWidth();
            int height = img.getHeight();
            if (width > maxImageSize) {
                double rate = (double) maxImageSize / width;
                width = (int) (width * rate);
                height = (int) (height * rate);
            }
            if (height > maxImageSize) {
                double rate = (double) maxImageSize / height;
                width = (int) (width * rate);
                height = (int) (height * rate);
            }

            //生成缩略图
            BufferedImage compressImg = new BufferedImage(width, height, img.getType());
            compressImg.getGraphics().drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            var result = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                return inputStream.readAllBytes();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return new byte[0];
    }
}
