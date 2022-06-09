
public class Selector {

	int x;
	int y;
	boolean selected;
	int name;
	
	
	
	public Selector (int newx, int newy, boolean newselected, int newname)
	{
		x = newx;
		y = newy;
		selected = newselected;
		name = newname;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
