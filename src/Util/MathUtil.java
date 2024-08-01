package Util;

import java.util.Random;

public class MathUtil {
    public static int generateAlInt(int min, int max) {
		Random r = new Random();
		int x = r.nextInt((max-min+1));
		return x + min;
	}
	
	public static boolean pourcentage(int pourcentage) {
		int x = generateAlInt(0, 100);
		if (x > pourcentage) {
			return false;
		} else {
			return true;
		}
	}
}
