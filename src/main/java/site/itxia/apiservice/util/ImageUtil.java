package site.itxia.apiservice.util;

import org.springframework.beans.factory.annotation.Value;
import site.itxia.apiservice.exception.UploadFileNotFoundException;

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
     * 将图片文件压缩为缩略图.
     *
     * @param inputStream 需要压缩的图片输入流
     * @return 缩略图byte[]
     */
    public static byte[] resize(InputStream inputStream) {
        try {
            //憨批api，又得把byte拿出来复制一遍再做成流
            var bytes = inputStream.readAllBytes();
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));

            //判断尺寸
            int width = img.getWidth();
            int height = img.getHeight();
            if (width < maxImageSize && height < maxImageSize) {
                //图片很小, 不用压缩
                return bytes;
            } else {
                //计算缩略图尺寸(保持长宽比)
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

            }

            //生成缩略图
            BufferedImage compressImg = new BufferedImage(width, height, img.getType());
            compressImg.getGraphics().drawImage(img, 0, 0, width, height, null);

            //写入流，转换成byte[]返回
            var outputStream = new ByteArrayOutputStream();
            ImageIO.write(compressImg, "png", outputStream);
            var result = outputStream.toByteArray();
            outputStream.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                return inputStream.readAllBytes();
            } catch (IOException exception) {
                exception.printStackTrace();
                throw new UploadFileNotFoundException();
            }
        }
    }
}
