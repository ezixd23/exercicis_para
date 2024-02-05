package C_exer_tic_tac_toe_With_Splash;

public class C_TicTacTacToe {

    public static void main (String [] args) throws InterruptedException {

        /* COMPLETE */
        Shared sharedobject = new Shared();
        Tic tic = new Tic(sharedobject);
        Tac tac = new Tac(sharedobject);
        Toe toe = new Toe(sharedobject);
        Splash splash = new Splash(sharedobject);

        tic.start();
        tac.start();
        toe.start();
        splash.start();

        try {Thread.sleep(5000);}  catch(InterruptedException ie) {};

        tic.stopNow();
        tac.stopNow();
        toe.stopNow();
        splash.stopNow();

        try {Thread.sleep(100);}  catch(InterruptedException ie) {};

        System.out.println("Shutting down");
        System.exit(0);
    }
}

class Shared {

    /* COMPLETE -private attributes- private? yes, private*/
    private volatile boolean tic = true;
    private volatile boolean tac = false;
    private volatile boolean toe = false;
    private volatile boolean splash = false;
    private volatile boolean wanna = true;
    public boolean upperCase = true;
    public void letMeTic () {
        // invoked by Tic before writing TIC-.
        // if this method returns then it is safe to write TIC-

        /* COMPLETE */
        while (!tic){}

    }

    public void ticWritten () {
        // invoked by Tic just after printing TIC-

        /* COMPLETE */
        tic=false;
        tac=true;
    }


    public void letMeTac () {
        // invoked by Tac before writing TAC.
        // if this method returns then it is safe to write TAC

        /* COMPLETE */
        while(!tac){}

    }

    public void tacWritten () {
        // invoked by Tac just after printing TAC (or tac)

        /* COMPLETE */
        tac=false;
        if(wanna){
            if (upperCase==false) splash = true;
            else toe = true;
        }
    }

    public void letMeToe () {
        // invoked by Toe before writing -TOE.
        // if this method returns then it is safe to write -TOE

        /* COMPLETE */
        while(!toe){}
    }

    public void toeWritten () {
        // invoked by Toe just after printing -TOE

        /* COMPLETE */
        toe=false;
        tic=true;
    }

    public void letMeSplash(){
        while(!splash){}
        wanna = false;
    }
    public void splashDone(){
        splash = false;
        tic = true;
        wanna = true;
    }
}

class Tic extends Thread {

    private volatile boolean stop = false;
    private Shared shared;

    public Tic (Shared sh) {
        this.shared = sh;
    }

    public  void run () {
        while (!stop) {
            shared.letMeTic();

            /* COMPLETE */
            System.out.print("TIC-");
            shared.ticWritten();
        }
    }

    public void stopNow() {stop=true;}
}

class Tac extends Thread {

    private volatile boolean stop = false;
    private Shared shared;
    // anything else? /* COMPLETE if needed*/

    public Tac (Shared sh) {
        this.shared = sh;
    }

    public void run () {
        while (!stop) {
            /* COMPLETE */
            shared.letMeTac();
            if (shared.upperCase) System.out.print("TAC-");
            else System.out.print("tac-");
            shared.tacWritten();
            shared.upperCase = !shared.upperCase;
        }
    }

    public void stopNow() {stop=true;}
}

class Toe extends Thread {

    private volatile boolean stop = false;
    private Shared shared;

    public Toe (Shared sh) {
        this.shared = sh;
    }

    public  void run () {
        while (!stop) {
            /* COMPLETE */
            shared.letMeToe();
            System.out.println("TOE");
            shared.toeWritten();
        }
    }

    public void stopNow() {stop=true;}
}

class Splash extends Thread {

    private volatile boolean stop;
    private Shared shared;

    public Splash (Shared sh) {
        this.shared = sh;
    }

    public  void run () {
        while (!stop) {
            try {Thread.sleep(500);} catch(InterruptedException ie) {}
            shared.letMeSplash();
            System.out.println("\n\tSPLASH!");
            shared.splashDone();
        }
    }

    public void stopNow () {stop=true;}
}