package airbnb.knn;
/**
 * Created by Σταυρίνα on 1/10/2017.
 */

public class Feature {
	private int rate;
	
	public Feature(int rate) {
		this.rate = rate;
	}
	
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getRate() {
		return rate;	
	}
}
