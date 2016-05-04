package edu.hendrix.webcamdemo;

import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

import com.github.sarxos.webcam.Webcam;

public class ImageThread extends Thread {
	private boolean quit = false;
	private int frames;
	private BiConsumer<BufferedImage,Double> renderer;
	
	public ImageThread(BiConsumer<BufferedImage,Double> renderer) {
		this.renderer = renderer;
	}
	
	public void quit() {quit = true;}
	
	@Override
	public void run() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		frames = 0;
		long start = System.currentTimeMillis();
		while (!quit) {
			if (webcam.isImageNew()) {
				BufferedImage img = webcam.getImage();
				frames += 1;
				renderer.accept(img, 1000.0 * frames / (System.currentTimeMillis() - start));
			}
		}
		webcam.close();
	}
}