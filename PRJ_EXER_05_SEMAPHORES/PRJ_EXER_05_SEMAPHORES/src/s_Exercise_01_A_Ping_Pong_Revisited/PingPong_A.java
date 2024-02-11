package s_Exercise_01_A_Ping_Pong_Revisited;

import java.util.concurrent.Semaphore;

public class PingPong_A {

	public static void main(String[] args) {
		Semaphore canPing = new Semaphore(0);/* COMPLETE */
		Semaphore canPong = new Semaphore(1);/* COMPLETE */

		Ping[] pings = new Ping[10];
		Pong[] pongs = new Pong[10];

		for (int i = 0; i < pings.length; i++) {
			pings[i] = new Ping(i, canPing, canPong);
			pongs[i] = new Pong(i, canPing, canPong);
			pings[i].start();
			pongs[i].start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
		}

		for (int i = 0; i < pings.length; i++) {
			pings[i].stop();
			pongs[i].stop();
		}
	}

}

class Ping extends Thread {

	private Semaphore canPing;
	private Semaphore canPong;
	private int id;

	public Ping(int id, Semaphore canPing, Semaphore canPong) {
		this.id = id;
		this.canPing = canPing;
		this.canPong = canPong;
	}

	public void run() {
		while (true) {

			/* COMPLETE */
			canPing.acquireUninterruptibly(); // Adquiere el semáforo canPong
			System.out.println("Ping " + id);
			canPong.release(); // Libera el semáforo canPing para que Ping pueda ejecutarse
		}
	}
}

class Pong extends Thread {

	private Semaphore canPing;
	private Semaphore canPong;
	private int id;

	public Pong(int id, Semaphore canPing, Semaphore canPong) {
		this.canPing = canPing;
		this.canPong = canPong;
		this.id = id;
	}

	public void run() {
		/* COMPLETE */
		while (true) {

			canPong.acquireUninterruptibly(); // Adquiere el semáforo canPong
			System.out.println("Pong " + id);
			canPing.release(); // Libera el semáforo canPing para que Ping pueda ejecutarse

		}
	}
}