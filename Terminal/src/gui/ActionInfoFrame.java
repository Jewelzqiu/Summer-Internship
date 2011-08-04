package gui;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.ListModel;

import javax.swing.WindowConstants;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ActionInfoFrame extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList InputList;
	private JLabel InputLabel;
	private JList OutputList;
	private JLabel OutputLabel;
	private JSeparator Separator;

	/**
	* Auto-generated main method to display this JFrame
	*/
		
	public ActionInfoFrame(ArrayList<Object> Action) {
		super();
		initGUI(Action);
	}
	
	private void initGUI(ArrayList<Object> Action) {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle(Action.get(0).toString());
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			{
				InputLabel = new JLabel();
				getContentPane().add(InputLabel);
				InputLabel.setText("Input arguments      ");
			}
			{
				String[] inputs = (String[]) Action.get(1);
				if (inputs != null) {
					ListModel InputListModel = 
							new DefaultComboBoxModel(inputs);
					InputList = new JList();
					getContentPane().add(InputList);
					InputList.setModel(InputListModel);
				}
			}
			{
				Separator = new JSeparator();
				getContentPane().add(Separator);
			}
			{
				OutputLabel = new JLabel();
				getContentPane().add(OutputLabel);
				OutputLabel.setText("Output arguments      ");
			}
			{
				String[] outputs = (String[]) Action.get(2);
				if (outputs != null) {
					ListModel OutputListModel = 
							new DefaultComboBoxModel(outputs);
					OutputList = new JList();
					getContentPane().add(OutputList);
					OutputList.setModel(OutputListModel);
				}
			}
			pack();
			//setSize(400, 300);
			setVisible(false);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
