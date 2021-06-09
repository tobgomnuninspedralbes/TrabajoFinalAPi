package api.models;

public class Item {
	public static final int PRODUCTE = 1;
	public static final int MENU = 2;
	private int id;
	private int tipus;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTipus() {
		return tipus;
	}
	public void setTipus(int tipus) {
		this.tipus = tipus;
	}
}
