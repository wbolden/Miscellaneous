import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;






public class Mandelbrot implements Runnable {
	Thread it;
	static int iterations = 150;
	static double magnification = 1.65; //1.65
	static int offset = 0;
	//static double modifier = 20;
	//static double modifiery = 10;
//	static int ystep = 0;
	
	public Mandelbrot(){
		
	}
	
	public void render(int[] pixels, int ystep) {
		
		System.out.println(ystep);

		for(int x = 0; x < 100*Display.modifier; x++){
			for(int y = (int) (100*Display.modifiery* (ystep-1)); y < 100*Display.modifiery*ystep; y++){
				
				double it = iterate(x-80*Display.modifier + offset, y-50*Display.modifier + offset);
				
				
				if(it == -1){
					pixels[x + (int)(y-100*Display.modifiery*(ystep-1)) * Display.WIDTH] = 0x00000;
				}
				else
				{
					int colors3 = 255;
					
					for(double tempIt = it; tempIt > 0; tempIt--)
					{
						if(colors3 > 10)
						{
							if(colors3 < 95)
							{
								colors3-= .9;
							}
							else
							{
								colors3-= .3;
							}
						}
					}
					
					pixels[x + (int)(y-100*Display.modifiery*(ystep-1)) * Display.WIDTH] = CustomColor.rgbToHex(colors3, colors3, colors3);
				}
			}
		}
		
		
	}
	
	

	public  double iterate(double x, double y)
	{
												//Mandelbrot
		double z1Re=0, z1Im=0, z2Re=0, z2Im=0;
		
		for(int i = iterations; i > 0; i--)
		{
			z2Re = z1Re;
			z2Im = z1Im;
			
			z1Re = ((z2Re * z2Re) + (z2Im * z2Im* -1)) + x/(25*magnification*Display.modifier);
			z1Im = (2*(z2Re * z2Im)) + y/(25*magnification*Display.modifier);

			if(90000< Math.sqrt(Math.abs((z1Re*z1Re) + (z1Im*z1Im*-1))))
			{
//				return (Math.sqrt(Math.abs((z1Re*z1Re) + (z1Im*z1Im*-1)))); //cool bands
				return (iterations - i);
//				return i;
				
			}
			
		}
		return -1;
	}
	
	
	
	public static void reset(int[] pixels) {
		//for(int x = 0; x < Display.WIDTH * Display.HEIGHT; x++){
		//	pixels[x] = 0;//0x1569C7;
		//}
	}

	
	public void run() {
		
		
	}

	
	
	/*
	 public static void render(int[] pixels) {
		
		
		//[x + y * Display.width
		for(int x = 0; x < 100*modifier; x++){
			for(int y = 100* (ystep-1); y < 100*modifiery*ystep; y++){
				double it = iterate(x-80*modifier + offset, y-50*modifier + offset);
				if(it == -1){
					pixels[x + y * Display.WIDTH] = 0x00000;
				}else{
					
					int colors3 = 150;
					for(double tempIt = it; tempIt > 0; tempIt--)
					{
						if(colors3 > 10)
						{
							if(colors3 < 95)
							{
								colors3-= .01;

							}
							else
							{
								colors3-= .3;
							}
						}
					}
					
						
					pixels[x + y * Display.WIDTH] = CustomColor.rgbToHex(colors3, colors3, colors3);
				}
			}
		}
		
		if(Display.HEIGHT*ystep >= Display.WIDTH){
			ystep = 1;
			Display.hang = true;
		}else{
			ystep++;
		}
		//offset++;
	}

	public static double iterate(double x, double y)
	{
												//Mandelbrot
		double z1Re=0, z1Im=0, z2Re=0, z2Im=0;
		
		for(int i = iterations; i > 0; i--)
		{
			z2Re = z1Re;
			z2Im = z1Im;
			
			z1Re = ((z2Re * z2Re) + (z2Im * z2Im* -1)) + x/(25*magnification*modifier);
			z1Im = (2*(z2Re * z2Im)) + y/(25*magnification*modifier);

			if(2 < Math.sqrt(Math.abs((z1Re*z1Re) + (z1Im*z1Im*-1))))
			{
//				return (Math.sqrt(Math.abs((z1Re*z1Re) + (z1Im*z1Im*-1)))); //cool bands
				return (iterations - i);
//				return i;
				
			}
			
		}
		return -1;
	}
	
	 */
	
	
}
