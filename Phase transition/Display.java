
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable
{

	private static final long	serialVersionUID	= 1L;

	public static final int		WIDTH				= 1000, HEIGHT = 1000;

	public int					low, high;

	private boolean				running				= false;
	private Thread				thread;

	static Toolkit				toolkit				= Toolkit.getDefaultToolkit();
	private BufferedImage		image;
	public int[]				pixels;
	int							backColor			= 0x326496;
	private boolean				scale				= true;

	public static void main(String[] args)
	{
		Display display = new Display();
		JFrame frame = new JFrame();
		frame.add(display);
		frame.setTitle("Random Network Phase Transition");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = toolkit.getScreenSize();

		frame.setLocation((d.width - WIDTH) / 2, (d.height - HEIGHT) / 2);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		display.start();

	}

	public Display()
	{
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	private void start()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
		requestFocus();
		Network.test();
	}

	public void run()
	{
		while (running)
		{
			draw();
			for (int x = 0; x < WIDTH * HEIGHT; x++)
			{
				pixels[x] = backColor;
			}

			if (scale)
			{
				
				
				
				double ratio = Network.average.length / (double) WIDTH;
				for (int x = 0; x < WIDTH; x++)
				{
					// = (int) ((Network.average.length) / (double)WIDTH)
					// System.out.println(Network.average.length / (double)WIDTH * x);
					int t = (int) (Network.average[(int) (x * ratio)]);
					try
					{
						t = (int) ((double) t / Network.average[Network.average.length - 1] * 800.0);
						// System.out.println(t);
						for (int y = HEIGHT - t; y < HEIGHT; y++)
						{
							pixels[y * WIDTH + x] = 0x00B803;

						}
					}
					catch (Exception e)
					{

					}

				}
				
			}
			else
			{
				
				for (int x = 0; x < Network.average.length; x++)
				{
					int t = (int) Network.average[x];
					for (int y = HEIGHT - t; y < HEIGHT; y++)
					{
						//System.out.println(y);
					
						int pixel = y * WIDTH + x;
						//System.out.println(pixel);
					
						
						if(pixel >= 0 && pixel < WIDTH*HEIGHT)
							pixels[pixel] = 0x00B803;

					}
				}
				
			}
			
		}
	}

	public void draw()
	{
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}

		// 0x00B803
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);

		g.dispose();
		bs.show();
	}

	public void fillArea(int x1, int x2, int y1, int y2, int color)
	{
		for (int x = x1; x < x2; x++)
		{
			for (int y = y1; y < y2; y++)
			{
				pixels[y * WIDTH + x] = color;
			}
		}
	}

}
