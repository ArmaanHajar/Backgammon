
public class Selector {

	int x;
	int y;
	boolean selectable;
	int name;
	
	
	
	public Selector (int newx, int newy, boolean newselectable, int newname)
	{
		x = newx;
		y = newy;
		selectable = newselectable;
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

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selected) {
		this.selectable = selected;
	}
	
}
