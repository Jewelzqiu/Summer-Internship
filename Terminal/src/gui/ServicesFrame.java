package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
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
public class ServicesFrame extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size = 0;
	
	private static ServicesFrame ActionList = null;
	private static ActionInfoFrame ActionInfo = null;

	/**
	* Auto-generated main method to display this JFrame
	*/
		
	public ServicesFrame(Hashtable<String, ArrayList<ArrayList<Object>>> Services) {
		super();
		size = Services.size();
		setTitle("Services");
		initGUI();
		addServicesButtons(Services);
	}
	
	public ServicesFrame(ArrayList<ArrayList<Object>> Actions) {
		super();
		size = Actions.size();
		setTitle("Actions");
		initGUI();
		addActionsButtons(Actions);
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(400, 80 * size);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void addServicesButtons(
			Hashtable<String, ArrayList<ArrayList<Object>>> Services) {
		
		for (String service : Services.keySet()) {
			//final ServicesFrame actions = new ServicesFrame(Services.get(service));
			ActionList = new ServicesFrame(Services.get(service));
			JButton button = new JButton(service);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					final ServicesFrame actions = ActionList;
					actions.setVisible(true);
				}
				
			});
			getContentPane().add(button);
		}
	}
	
	private void addActionsButtons(ArrayList<ArrayList<Object>> Actions) {
		for (ArrayList<Object> action : Actions) {
			//final ActionInfoFrame acframe = new ActionInfoFrame(action);
			ActionInfo = new ActionInfoFrame(action);
			JButton button = new JButton(action.get(0).toString());
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					final ActionInfoFrame acframe = ActionInfo;
					acframe.setVisible(true);
				}
				
			});
			getContentPane().add(button);
		}
	}

}
