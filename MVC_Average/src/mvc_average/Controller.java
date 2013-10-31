package mvc_average;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author Henry
 */
public class Controller {

	// reference holders to other two classes
	private View theView;
	private Model theModel;

	public Controller(View theView, Model theModel) {
		// receive references to the other two classes
		this.theView = theView;
		this.theModel = theModel;
		
		// add document listener to the list in view
		theView.returnList().getDocument().addDocumentListener(detectChanges);
	}

	// input action
	DocumentListener detectChanges = new DocumentListener() {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			// grab value from View, and send to Model
			ArrayList<Float> input = theView.returnInput();
			theModel.StoringValue(input);
			// call to calculate Average
			AverageList();
			// call to display result
			DisplayResult();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

	};

	// calculate method
	private void AverageList() {
		// tell Model to calculate the average
		theModel.CalculateAverage();
	}

	// display result method
	private void DisplayResult() {
		// grab the result from Model and send to View
		float average = theModel.ReturnAverage();
		theView.setResult(average);
	}
}
