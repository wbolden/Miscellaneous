


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;
import javax.imageio.*;



//http://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html
public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static double modifier = 50;  //2000
	public static double modifiery = 50;  //10
	

	
	public static final int WIDTH = (int) (100*modifier), HEIGHT = (int) (100*modifiery);
	public static final String TITLE = "Mandelbrot Set";
	static int piccount = 0;
	public static ExecutorService pool;
	public static List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();

	private Thread thread;
	private boolean running = false;
	
	static int threads = Runtime.getRuntime().availableProcessors();
	public BufferedImage image;

	private int[] pixels;
	
	
	private InputHandler input;
	Mandelbrot m = new Mandelbrot();
	public static boolean hang = false;
	int ystep = 0;
	
	int totcount = -1;
	
	public Display(int step) {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		ystep = step;

	}

	
	public void run(){
		requestFocus();
		System.out.println("Running");
		running = true;

		render(ystep);

		
	}
	
//	private void tick(){
//		Time.tick();
//	}
	
	private void render(int ystep){

		m.render(pixels, ystep);

		
		try{
			ImageIO.write(image, "png", new File("mandelbrot"+ystep+".png"));
			} catch (IOException e){
			System.out.println("nope");
			}
		System.out.println("done");

	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
	
		//pool = Executors.newFixedThreadPool(threads);
		System.out.println(threads+" threads");
		
		for(int x = 0; x <= (modifier/modifiery); x+=4)
		{
			pool = Executors.newFixedThreadPool(threads);
			for(int i=0; i< threads; i++){
				Future f = pool.submit(new Display(x+i));
				futures.add(f);
			}
			
			for (Future<Runnable> f : futures)
			{
				f.get();
			}
			futures.clear();
			pool.shutdown();
			pool = null;
		
		}
		
		//display.start();
	
	}

//for(Future f: futures) f.get();
}
