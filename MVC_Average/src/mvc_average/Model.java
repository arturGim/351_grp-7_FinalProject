package mvc_average;

import java.util.ArrayList;

/**
*
* @author Henry
*/
public class Model {
	
	//value holders
	private ArrayList<Float> numList = null;
	private float average = 0;
	
	public void StoringValue(ArrayList<Float> numbers){
		//store the list of numbers inside model
		numList = numbers;
	}
	
	public void CalculateAverage(){
		float sum = 0;
		
		//add all numbers in the list to sum
		for(int i=0; i<numList.size(); i++){
			sum += (numList.get(i));
		}
		
		//now divide the total sum by the # of numbers in the list
		sum /= numList.size();
		
		//now store that new value in our average variable
		average = sum;		
	}

	public float ReturnAverage(){
		//return the stored average
		return average;
	}
}
