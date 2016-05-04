package edu.hendrix.webcamdemo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;

import java.awt.image.BufferedImage;

public class WebcamDemoController implements Quittable {
	@FXML Canvas image;
	
	@FXML TextField frameRate;
	
	ImageThread renderer;
	
	int frames;
	
	public static void render(BufferedImage img, Canvas canv) {
		double cellWidth = canv.getWidth() / img.getWidth();
		double cellHeight = canv.getHeight() / img.getHeight();
		GraphicsContext g = canv.getGraphicsContext2D();
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				g.setFill(ColorChannel.buildColorFrom(img.getRGB(x, y)));
				g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
			}
		}
	}
	
	@FXML
	void initialize() {
		renderer = new ImageThread((img, rate) -> Platform.runLater(() -> {
			render(img, image);
			frameRate.setText(String.format("%4.2f", rate));
		}));
		renderer.start();
		frameRate.setEditable(false);
	}

	@Override
	public void quit() {
		renderer.quit();
		System.out.println("Quit starts...");
	}
}
