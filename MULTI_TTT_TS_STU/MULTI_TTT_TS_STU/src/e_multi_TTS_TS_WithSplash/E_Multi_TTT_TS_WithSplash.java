package e_multi_TTS_TS_WithSplash;

import java.util.concurrent.atomic.AtomicInteger;

/* Copy, from your solution of the previous exercise:
   - The launcher class containing main
   - Tic, Tac and Toe classes
   - TsBasedSynchronizer class
   Then modifies what needs to be modified in order to accomodate
   the splash
 */
public class E_Multi_TTT_TS_WithSplash {

    static int INSTANCES = 10;

    public static void main (String [] args) throws InterruptedException {
        TsBasedSynchronizer syncTool = new TsBasedSynchronizer();
        Tic[] theTics = new Tic[INSTANCES];
        Tac[] theTacs = new Tac[INSTANCES];
        Toe[] theToes = new Toe[INSTANCES];
        Splash theSplash = new Splash(syncTool);

        for (int i=0; i<INSTANCES; i++) {
            theTics[i] = new Tic(i, syncTool);
            theTacs[i] = new Tac(i, syncTool);
            theToes[i] = new Toe(i, syncTool);
            theToes[i].start();
            theTacs[i].start();
            theTics[i].start();
        }
        theSplash.start();
        Thread.sleep(5000);

        System.exit(0);
    }
}

class TsBasedSynchronizer  {
    private static final  int TIC_POSSIBLE = 1;
    private static final int TAC_POSSIBLE = 2;
    private static final int TOE_POSSIBLE = 3;
    private static final int WRITING_IN_COURSE = 4;
    private static final int SPLASH_POSSIBLE = 5;

    private int nextId;

    AtomicInteger state = new AtomicInteger(TIC_POSSIBLE);

    public void letMeTic() {
        while (!state.compareAndSet(TIC_POSSIBLE,WRITING_IN_COURSE)){
            Thread.yield();
        }
    }
    public void letMeTac(int id) {
        if (id % 2 == 0) state.set(SPLASH_POSSIBLE);
        while (!state.compareAndSet(TAC_POSSIBLE,WRITING_IN_COURSE)){
            Thread.yield();
        }
    }
    public void letMeToe(int id) {
        while (!(id == nextId && state.compareAndSet(TOE_POSSIBLE,WRITING_IN_COURSE))){
            Thread.yield();
        }
    }

    public void letMeSplash() {
        while (!state.compareAndSet(SPLASH_POSSIBLE,WRITING_IN_COURSE)){
            Thread.yield();
        }
    }

    public void ticDone() {
        state.set(TAC_POSSIBLE);
    }

    public void tacDone() {
        state.set(TOE_POSSIBLE);
    }

    public void toeDone() {
        nextId = (nextId+1)%10;
        state.set(TIC_POSSIBLE);
    }

    public void splashDone() {
        state.set(TIC_POSSIBLE);
    }

}

class Tic extends Thread {

    private TsBasedSynchronizer syncTool;
    private int id;

    public Tic (int id, TsBasedSynchronizer st) {
        this.syncTool = st;
        this.id = id;
    }

    public  void run () {
        while (true) {
            /* COMPLETE */
            syncTool.letMeTic();
            System.out.print("TIC("+id+")-");
            syncTool.ticDone();
        }
    }
}

class Tac extends Thread {

    private TsBasedSynchronizer syncTool;
    private int id;

    private static volatile boolean upperCase = true;  // static. All instance share
    // the same variable

    public Tac (int id, TsBasedSynchronizer st) {
        this.syncTool = st;
        this.id = id;
    }

    public void run () {
        while (true) {
            /* COMPLETE */
            syncTool.letMeTac(id);
            if (upperCase) System.out.print("TAC("+id+")-");
            else System.out.print("tac("+id+")-");
            upperCase = !upperCase;
            syncTool.tacDone();
        }
    }
}

class Toe extends Thread {

    private TsBasedSynchronizer syncTool;
    private int id;

    public Toe (int id,TsBasedSynchronizer st) {
        this.syncTool = st;
        this.id = id;
    }

    public  void run () {
        while (true) {
            /* COMPLETE */
            syncTool.letMeToe(id);
            System.out.println("TOE("+id+")");
            syncTool.toeDone();
        }
    }
}

//------------------------------------------------------------

class Splash extends Thread {
	private TsBasedSynchronizer syncTool;
	
	public Splash (TsBasedSynchronizer st) {
		this.syncTool = st;
	}
	
	public void run () {
		while(true) {
			try {Thread.sleep(500);} catch(InterruptedException ie) {}
			syncTool.letMeSplash();
			System.out.println("\n\tSPLASH!");
			syncTool.splashDone();
		}
	}
}

