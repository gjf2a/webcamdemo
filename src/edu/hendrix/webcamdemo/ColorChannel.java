package edu.hendrix.webcamdemo;


import java.util.EnumMap;

import javafx.scene.paint.Color;

public enum ColorChannel {
	ALPHA {
		@Override
		public int shift() {
			return 24;
		}
	}, RED {
		@Override
		public int shift() {
			return 16;
		}
	}, GREEN {
		@Override
		public int shift() {
			return 8;
		}
	}, BLUE {
		@Override
		public int shift() {
			return 0;
		}
	};
	
	abstract public int shift();
	
	public int extractFrom(int pixel) {
		return (pixel >> shift()) & 0xFF;
	}
	
	public static int buildPixelFrom(EnumMap<ColorChannel,Integer> colors) {
		int pixel = 0;
		for (ColorChannel c: values()) {
			pixel += (colors.containsKey(c) ? colors.get(c) : 0) << c.shift();
		}
		return pixel;
	}
	
	public static javafx.scene.paint.Color buildColorFrom(int argb) {
		return Color.rgb(ColorChannel.RED.extractFrom(argb), 
				ColorChannel.GREEN.extractFrom(argb), 
				ColorChannel.BLUE.extractFrom(argb), 
				ColorChannel.ALPHA.extractFrom(argb) / 255.0);
	}
	
	public static EnumMap<ColorChannel,Integer> buildChannelsFrom(int argb) {
		EnumMap<ColorChannel,Integer> channels = new EnumMap<>(ColorChannel.class);
		for (ColorChannel c: values()) {
			channels.put(c, c.extractFrom(argb));
		}
		return channels;
	}
}
