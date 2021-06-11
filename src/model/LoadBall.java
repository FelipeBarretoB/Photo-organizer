package model;

public class LoadBall {
	private int posX;
	private int posY;
	private int prevPosX;
	private int prevPosY;
	
	public LoadBall() {
		this.posX = 300;
		this.posY = 250;
		this.prevPosX = 275;
		this.prevPosY = 0;
	}
	
	public int moveBallX() {
		switch(posX) {
			case 250:
				posX += 25;
				prevPosX = 250;
				break;
			case 275:
				if(prevPosX == 250) {
					posX += 25;
				}else {
					posX -= 25;
				}
				prevPosX = 275;
				break;
			case 300:
				if(prevPosX == 275) {
					posX += 25;
				}else {
					posX -= 25;
				}
				prevPosX = 300;
				break;
			case 325:
				if(prevPosX == 300) {
					posX += 25;
				}else {
					posX -= 25;
				}
				prevPosX = 325;
				break;
				
			case 350:
				posX -= 25;
				prevPosX = 350;
				break;
		}
		return posX;
	}
	public int moveBallY() {
		switch(posY) {
			case 150:
				posY += 25;
				prevPosY = 150;
				break;
			case 175:
				if(prevPosY == 150) {
					posY += 25;
				}else {
					posY -= 25;
				}
				prevPosY = 175;
				break;
			case 200:
				if(prevPosY == 175) {
					posY += 25;
				}else {
					posY -= 25;
				}
				prevPosY = 200;
				break;
			case 225:
				if(prevPosY == 200) {
					posY += 25;
				}else {
					posY -= 25;
				}
				prevPosY = 225;
				break;
				
			case 250:
				posY -= 25;
				prevPosY = 250;
				break;
		}
		return posY;
	}
	
}
