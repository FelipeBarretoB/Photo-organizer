package thread;

import model.LoadBall;
import ui.PhotoOrganizerGUI;

public class BallThread extends Thread{
	private LoadBall ball;
	private int sleep;
	private PhotoOrganizerGUI photo;
	
	public BallThread(int s, PhotoOrganizerGUI p) {
		this.ball = new LoadBall();
		this.sleep = s;
		this.photo = p;
	}
	
	@Override
	public void run() {
		boolean exit = false;
		try {
			while(!exit) {
				photo.uploadCircle(ball.moveBallX(), ball.moveBallY());
				Thread.sleep(sleep);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
}
