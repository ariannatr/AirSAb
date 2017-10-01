package airbnb.knn;

/**
 * Created by Σταυρίνα on 1/10/2017.
 */
import java.util.ArrayList;

public class Instance {
	private ArrayList<Feature> attributes;
	private String uuid;

	public void setAttributes(ArrayList<Feature> attributes) {
		this.attributes = attributes;
	}

	public ArrayList<Feature> getAttributes() {
		return attributes;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public int getMax(){
		int max = Integer.MIN_VALUE;
		for(int i=0; i<attributes.size(); i++){
			if(attributes.get(i).getRate() > max){
				max = attributes.get(i).getRate();
			}
		}
		return max;
	}

	public int getMaxIndex(){
		int max = Integer.MIN_VALUE;
		int index=-1;
		for(int i=0; i<attributes.size(); i++){
			if(attributes.get(i).getRate() > max){
				max = attributes.get(i).getRate();
				index=i;
			}
		}
		return index;
	}

	public  int getSecondMaxIndex()
	{
		int secondLargest =  attributes.get(0).getRate();
		int secondIndex=-1;
		int largestIndex=-1;
		int largest = attributes.get(0).getRate();
		for (int i = 1; i < attributes.size(); i++) {
			if(attributes.get(i).getRate() > largest) {
				secondLargest = largest;
				secondIndex=largestIndex;
				largest = attributes.get(i).getRate();
				largestIndex=i;
			}
			if(attributes.get(i).getRate() > secondLargest && attributes.get(i).getRate() != largest) {
				secondLargest = attributes.get(i).getRate();
				secondIndex=i;
			}
		}
		System.out.print("Second biggest number ");
		return secondIndex;
	}
}
