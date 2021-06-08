package api.models;

public abstract class Item {
	public static final int PRODUCTE = 1;
	public static final int MENU = 2;
	private int id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
