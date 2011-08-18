package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import util.Connection;

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
	private Action action;
	private JLabel InputLabel;
	private JLabel OutputLabel;
	private JSeparator Separator;
	private Vector<JLabel> inputnames;
	private Vector<JTextField> inputvalues;
	private Vector<JLabel> outputnames;
	private Vector<JTextField> outputvalues;
	private JButton InvokeButton;
	
	private static ActionInfoFrame frame;

	/**
	* Auto-generated main method to display this JFrame
	*/
		
	private ActionInfoFrame(Action action) {
		super();
		this.action = action;
		initGUI(action);
	}
	
	public static ActionInfoFrame getActionInfoFrame(Action action) {
		frame = new ActionInfoFrame(action);
		return frame;
	}
	
	private void initGUI(Action action) {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle(action.getName());
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			{
				InputLabel = new JLabel();
				getContentPane().add(InputLabel);
				InputLabel.setText("Input arguments");
			}
			{
				Separator = new JSeparator();
				getContentPane().add(Separator);
			}
			{
				String[] inputs = action.getInputs();
				inputnames = new Vector<JLabel>();
				inputvalues = new Vector<JTextField>();
				if (inputs != null) {
					for (String s : inputs) {
						JLabel inputi = new JLabel(s);
						JTextField texti = new JTextField();
						getContentPane().add(inputi);
						getContentPane().add(texti);
						inputnames.add(inputi);
						inputvalues.add(texti);
					}
				}
			}
			{
				Separator = new JSeparator();
				getContentPane().add(Separator);
			}
			{
				OutputLabel = new JLabel();
				getContentPane().add(OutputLabel);
				OutputLabel.setText("Output arguments");
			}
			{
				Separator = new JSeparator();
				getContentPane().add(Separator);
			}
			{
				String[] outputs = action.getOutputs();
				outputnames = new Vector<JLabel>();
				outputvalues = new Vector<JTextField>();
				if (outputs != null) {
					for (String s : outputs) {
						JLabel outputi = new JLabel(s);
						JTextField texti = new JTextField();
						texti.setEditable(false);
						getContentPane().add(outputi);
						getContentPane().add(texti);
						outputnames.add(outputi);
						outputvalues.add(texti);
					}
				}
			}
			{
				InvokeButton = new JButton("Invoke");
				InvokeButton.addActionListener(new InvokeListener());
				getContentPane().add(InvokeButton);
			}
			pack();
			setLocationRelativeTo(null);
			setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class InvokeListener implements ActionListener {

		@SuppressWarnings("rawtypes")
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread() {
				
				public void run() {
					Hashtable<String, String> inputs = new Hashtable<String, String>();
					for (int i = 0; i < inputnames.size(); i++) {
						inputs.put(inputnames.get(i).getText(), inputvalues.get(i).getText());
					}
					Dictionary returns = null;
					try {
						InvokeButton.setEnabled(false);
						returns = Connection.invoke(action.getProps(inputs));
						InvokeButton.setEnabled(true);
						if (returns != null) {
							for (int i = 0; i < outputnames.size(); i++) {
								String name = outputnames.get(i).getText();
								String value = returns.get(name).toString();
								outputvalues.get(i).setText(value);
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
			}.start();
			
		}
		
	}

}
