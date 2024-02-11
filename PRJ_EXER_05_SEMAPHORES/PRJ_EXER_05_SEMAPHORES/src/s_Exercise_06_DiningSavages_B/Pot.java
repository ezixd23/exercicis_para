package s_Exercise_06_DiningSavages_B;

public class Pot {
	private final int CAPACITY;
	private volatile int servings;
	
	public Pot (int capacity) {
		this.CAPACITY = capacity;
		this.servings = 0;
	}
	
	public void helpYourself () {
		if (servings > 0) servings--;
		else throw new IllegalStateException("can't help yourself from an empty pot!");
	}
	
	public void refill () {
		if (servings>0) throw new IllegalStateException("can't refill a non-empty pot!");
		else servings = CAPACITY;
	}
	
	public int getServings () {
		return this.servings;
	}
	
}