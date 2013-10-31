package mvc_average;
/**
*
* @author Henry
*/
public class MVC_Average {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		View theView = new View();
		Model theModel = new Model();
		Controller theController = new Controller(theView, theModel);
		theView.setVisible(true);
	}

}
