package com.color;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public class Fontes {
	
	public static Font getFontRed(){
		return FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.NORMAL, new BaseColor(BaseColor.RED.getRGB()));
	}
	
	public static Font getFontBlue(){
		return FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.NORMAL, new BaseColor(BaseColor.BLUE.getRGB()));
	}
	
	public static Font getFontBlack(){
		return FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.NORMAL, new BaseColor(BaseColor.BLACK.getRGB()));
	}
	
	public static Font getFontOrange(){
		return FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.NORMAL, new BaseColor(BaseColor.ORANGE.getRGB()));
	}
	
	public static Font getFontGreen(){
		return FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.NORMAL, new BaseColor(BaseColor.GREEN.getRGB()));
	}

}
