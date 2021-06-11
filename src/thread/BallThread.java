package thread;

import model.LoadBall;
import ui.PhotoOrganizerGUI;

public class BallThread extends Thread{
	private LoadBall ball1;
	private LoadBall ball2;
	private LoadBall ball3;
	private LoadBall ball4;
	private int sleep;
	private PhotoOrganizerGUI photo;
	
	public BallThread(int s, PhotoOrganizerGUI p, LoadBall b1, LoadBall b2, LoadBall b3, LoadBall b4) {
		this.ball1 = b1;
		this.ball2 = b2;
		this.ball3 = b3;
		this.ball4 = b4;
		this.sleep = s;
		this.photo = p;
	}
	
	@Override
	public void run() {
		boolean exit = false;
		try {
			while(!exit) {
				photo.uploadCircle1(ball1.moveBallX(), ball1.moveBallY());
				photo.uploadCircle2(ball2.moveBallX(), ball2.moveBallY());
				photo.uploadCircle3(ball3.moveBallX(), ball3.moveBallY());
				photo.uploadCircle4(ball4.moveBallX(), ball4.moveBallY());
				Thread.sleep(sleep);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
}
