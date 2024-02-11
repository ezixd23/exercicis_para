package s_Exercise_06_DiningSavages_C;

public class Pot {
	private final int CAPACITY;
	private volatile int servings;
	private GuiObjects gui;
	
	public Pot (int capacity, GuiObjects gui) {
		this.CAPACITY = capacity;
		this.servings = 0;
		this.gui = gui;
	}
	
	public void helpYourself () {
		if (servings > 0) {
			servings--;
			gui.availableServings.setText(servings+"");
		}
		else throw new IllegalStateException("can't help yourself from an empty pot!");
	}
	
	public void refill () {
		if (servings>0) throw new IllegalStateException("can't refill a non-empty pot!");
		else {
			servings = CAPACITY;
			gui.availableServings.setText(servings+"");
		}
	}
	
	public int getServings () {
		return this.servings;
	}
	
}