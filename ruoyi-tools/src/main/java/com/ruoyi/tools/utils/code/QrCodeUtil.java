package com.ruoyi.tools.utils.code;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.common.exception.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 *
 * @Author wocurr.com
 */
@Slf4j
public class QrCodeUtil {

    /**
     * logo路径
     */
    private static final String LOGO_PATH = "static/logo/logo.png";
    /**
     * 图片前缀
     */
    private static final String IMAGE_PREFIX = "data:image/jpeg;base64,";
    /**
     * 生成图片类型
     */
    private static final String IMAGE_TYPE = "png";
    /**
     * 二维码宽度
     */
    private static final int WIDTH = 200;
    /**
     * 二维码高度
     */
    private static final int HEIGHT = 200;

    /**
     * 生成二维码
     *
     * @param content 内容，json格式
     * @param isLogo  是否带logo
     * @return
     */
    public static String genQrCode(JSONObject content, Boolean isLogo) {
        return qrEncode(JSON.toJSONString(content), isLogo);
    }

    /**
     * 二维码生成
     */
    private static String qrEncode(String content, Boolean isLogo) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);
        ByteArrayOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            BitMatrix bitMatrix = (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            outputStream = new ByteArrayOutputStream();
            Base64.Encoder encoder = Base64.getEncoder();
            if (isLogo) {
                ClassPathResource classPathResource = new ClassPathResource(LOGO_PATH);
                inputStream = classPathResource.getInputStream();
                MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(-16777215, -1);
                BufferedImage bufferedImage = logoMatrix(MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig), inputStream);
                ImageIO.write(bufferedImage, IMAGE_TYPE, outputStream);
                return IMAGE_PREFIX + encoder.encodeToString(outputStream.toByteArray());
            }
            MatrixToImageWriter.writeToStream(bitMatrix, IMAGE_TYPE, outputStream);
            return IMAGE_PREFIX + encoder.encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BaseException("处理二维码失败");
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * logo处理
     */
    private static BufferedImage logoMatrix(BufferedImage matrixImage, InputStream inputStream) throws IOException {
        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();
        BufferedImage logo = ImageIO.read(inputStream);
        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);
        BasicStroke stroke = new BasicStroke(5.0F, 1, 1);
        g2.setStroke(stroke);
        RoundRectangle2D.Float round = new RoundRectangle2D.Float((matrixWidth / 5 * 2), (matrixHeigh / 5 * 2), (matrixWidth / 5), (matrixHeigh / 5), 20.0F, 20.0F);
        g2.setColor(Color.white);
        g2.draw(round);
        BasicStroke stroke2 = new BasicStroke(1.0F, 1, 1);
        g2.setStroke(stroke2);
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float((matrixWidth / 5 * 2 + 2), (matrixHeigh / 5 * 2 + 2), (matrixWidth / 5 - 4), (matrixHeigh / 5 - 4), 20.0F, 20.0F);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);
        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }
}
