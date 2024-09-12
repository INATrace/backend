package com.abelium.inatrace.tools;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageTools {
	
	public static final class ImageSizeData {
		public int width;
		public int height;
		public String name;
		
		public ImageSizeData(int width, int height, String name) {
			this.width = width;
			this.height = height;
			this.name = name;
		}
	}
	
    public static final ImageSizeData[] STANDARD_IMAGE_SIZES = new ImageSizeData[] {
		new ImageSizeData(640, 320, "SMALL"),
		new ImageSizeData(1024, 512, "MEDIUM"),
		new ImageSizeData(1200, 600, "LARGE"),
		new ImageSizeData(1440, 720, "XLARGE"),
		new ImageSizeData(2000, 1000, "XXLARGE"),
    };  

	public static BufferedImage resize(BufferedImage inputImage, int scaledWidth, int scaledHeight) {
		BufferedImage outputImage = Scalr.resize(inputImage, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_WIDTH, scaledWidth, scaledHeight);
		int x1 = 0;
		int y1 = (outputImage.getHeight() - scaledHeight) / 2;

		if (y1 >= 0) {
			outputImage = Scalr.crop(outputImage, x1, y1, scaledWidth, scaledHeight);
			return outputImage;
		}
		return outputImage;
	}
	
	public static byte[] resize(byte[] file, int scaledWidth, int scaledHeight)  {
		try {
	        BufferedImage image = ImageIO.read(new ByteArrayInputStream(file));
			BufferedImage resizedImage = resize(image, scaledWidth, scaledHeight);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(resizedImage, "jpg", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Error resizing image", e);
		}
	}	
	
	public static byte[] resize(byte[] file, ImageSizeData isd) {
		return resize(file, isd.width, isd.height);
	}

	public static byte[] convertToJpg(byte[] file) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(file));
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(newBufferedImage, "jpg", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Error converting image", e);
		}
	}
	
}
