
public class Token {

	int x;
	int y;
	int affiliation;
	boolean won;

	
	public Token(int newx, int newy, int newaffiliation, boolean newwon)
	{
		x = newx;
		y = newy;
		affiliation = newaffiliation;
		won = newwon;
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

	public int getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(int affiliation) {
		this.affiliation = affiliation;
	}


	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}
	
}
