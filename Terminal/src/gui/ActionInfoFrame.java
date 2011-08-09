package gui;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.ListModel;

import javax.swing.WindowConstants;

import device.Action;


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
		
	public ActionInfoFrame(Action action) {
		super();
		initGUI(action);
	}
	
	private void initGUI(Action action) {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle(action.getName());
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			{
				InputLabel = new JLabel();
				getContentPane().add(InputLabel);
				InputLabel.setText("Input arguments      ");
			}
			{
				String[] inputs = action.getInputs();
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
				String[] outputs = action.getOutputs();
				if (outputs != null) {
					ListModel OutputListModel = 
							new DefaultComboBoxModel(outputs);
					OutputList = new JList();
					getContentPane().add(OutputList);
					OutputList.setModel(OutputListModel);
				}
			}
			pack();
			setLocationRelativeTo(null);
			setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
