package com.dave.frame;

import com.dave.main.CanvasControl;

public class CountFrame {
	
	public void logi(long ft){
		if(ft!=0)CanvasControl.frameNow = (1000/ft);
		
	}

}
