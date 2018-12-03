package engine;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * @author thibdev
 */
public class ImageProcessing {
	
	public ImageProcessing() {
		
	}
	
	/**
	 * https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
	 * @param buffer
	 */
	public void FFT(BufferedImage buffer) {
		System.out.println(buffer.getType());
		//int[] pixels = {1, 2, 3, 0};
		int[] pixels = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
		System.out.println(pixels.length);
        Complex[] cinput = new Complex[512];
        for (int i = 0; i < 512; i++) {
            cinput[i] = new Complex(pixels[i], 0.0);
        }
 
        //FastFourierTransform.fft(cinput);
 
        System.out.println("Results:");
        for (Complex c : cinput) {
            System.out.println(c);
        }
	}

}
