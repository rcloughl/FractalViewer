
// Java Project Fractal Graphing

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.Color;

public class FractalProject extends JFrame{
	
	static final int WIDTH = 500;
	static final int HEIGHT = 500;

	Canvas canvas;
	BufferedImage fractalImage;
	
	static final double DEFAULT_ZOOM = 100.0;
	static final double DEFAULT_TOP_LEFT_X = -2.5;
	static final double DEFAULT_TOP_LEFT_Y= +2.5;
	
	double zoomFactor = DEFAULT_ZOOM;
	double topLeftX = DEFAULT_TOP_LEFT_X;
	double topLeftY = DEFAULT_TOP_LEFT_Y;

	
	
	static final int MAX_ITER = 200;
	
	public FractalProject() {
		setInitialGUIProperties();
		addCanvas();
		updateFractal();
	}
	
	private double getXPos(double x) {
		return x/zoomFactor + topLeftX;
	}
	
	private double getYPos(double y) {
		return y/zoomFactor - topLeftY;
	}
	
	
	public void updateFractal() {
		for( int x=0; x<WIDTH; x++) {
			for( int y=0; y<HEIGHT; y++) {
				double c_re= getXPos(x);
				double c_im= getYPos(y);
				
				int iterCount = computeIterations(c_re, c_im);
				int pixelColor = makeColor(iterCount);
				fractalImage.setRGB(x, y, pixelColor);
			}
		}
		canvas.repaint();
	}
	
	private int makeColor(int iterCount) {
		if (iterCount == MAX_ITER) {
			return Color.black.getRGB();
		}
		return Color.orange.getRGB();
	}
	//learned how to implement on https://jonisalonen.com/2013/lets-draw-the-mandelbrot-set/
	private int computeIterations(double c_re, double c_im) {
		double x = 0.0;
		double y = 0.0;
		int iterCount = 0;
		while( x*x + y*y <= 4.0) {
			double temp = x*x-y*y+c_re;
			y=2*y*x+c_im;
			x = temp;
			if (iterCount>=MAX_ITER) {
				return MAX_ITER;
			}
			iterCount++;
		}
		return iterCount;
	}
	
	private void addCanvas() {
		canvas= new Canvas();
		fractalImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		canvas.setVisible(true);
		this.add(canvas, BorderLayout.CENTER);
	}
	
	public void setInitialGUIProperties() {
		this.setTitle("Fractal Project");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
			new FractalProject();
	}
	
	private class Canvas extends JPanel {
		@Override public Dimension getPreferredSize() {
			return new Dimension(WIDTH, HEIGHT);
		}
		@Override public void paintComponent(Graphics drawingObj) {
			drawingObj.drawImage(fractalImage, 0, 0, null);
		}
	}
	
}
