

public class CustomColor
{
	static int rgbToHex(int r, int g, int b){
		
		return (r << 16) | (g << 8) | b;
	}
	
	static void hexToRGB(int h){
		System.out.println("red: "+ ((h & 0xFFFFFF) >> 16));
		System.out.println("green: "+ ((h & 0xFFFF) >> 8));
		System.out.println("blue: "+ (h & 0xFF));
	}
}
