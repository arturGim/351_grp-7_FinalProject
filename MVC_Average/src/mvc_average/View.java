package mvc_average;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class View extends JFrame {
	// textfields
	private JTextField inputText = new JTextField(10);
	private JTextArea numList = new JTextArea(10, 10);
	private JTextField resultText = new JTextField(10);

	// status label
	private JLabel errorLabel = new JLabel();
	private JLabel inputLabel = new JLabel("INPUT");
	private JLabel listLabel = new JLabel("LIST");
	private JLabel resultLabel = new JLabel("RESULT");

	// number list
	private ArrayList<Float> inputList = new ArrayList<Float>();

	// scrollpane
	private JScrollPane scroll = new JScrollPane(numList);

	public View() {
		// configure GUI
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MVC AVERAGE EXERCISE");

		// Container
		Container c = getContentPane();

		// group layout
		GroupLayout layout = new GroupLayout(c);

		c.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.LEADING)
								.addComponent(inputLabel)
								.addComponent(inputText)
								)
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.LEADING)
								.addComponent(listLabel).addComponent(scroll)
								.addComponent(resultLabel)
								.addComponent(resultText)));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(inputLabel)
								.addComponent(listLabel))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(inputText).addComponent(scroll))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.TRAILING)
								.addComponent(resultLabel))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(resultText)));

		// add listener
		inputText.addActionListener(Input);

		// set non-editable
		numList.setEditable(false);
		resultText.setEditable(false);
		resultText.setBackground(Color.white);
		
		// scroll stuffs
		scroll.setHorizontalScrollBar(null);
	}

	// input action
	Action Input = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String input = ((JTextComponent) e.getSource()).getText();
			((JTextComponent) e.getSource()).setText("");
			addInputs(input);
			setList();
		}
	};

	// add inputs to inputList
	private void addInputs(String input) {
		try {
			inputList.add(Float.parseFloat(input));
			errorLabel.setText("");
		} catch (Exception e) {
			errorLabel.setText("please input a number");
		}
	}

	// display list
	private void setList() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < inputList.size(); i++) {
			b.append(inputList.get(i) + "\n");
		}
		numList.setText(b.toString());
	}

	// display result
	public void setResult(Float result) {
		resultText.setText(result.toString());
	}

	// return inputList
	public ArrayList<Float> returnInput() {
		return inputList;
	}

	// return number list (textArea)
	public JTextArea returnList() {
		return numList;
	}
}