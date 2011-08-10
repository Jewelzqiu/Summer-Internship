package gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.BoxLayout;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import device.Action;
import device.Service;


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
public class ServicePanel extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	public ServicePanel(Vector<Service> services) {
		super();
		initGUI(services);
	}
	
	private void initGUI(Vector<Service> services) {
		try {
			setPreferredSize(new Dimension(400, 300));
			BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS);
			this.setLayout(thisLayout);
			{
				for (Service service : services) {
					DefaultMutableTreeNode servicenode = 
							new DefaultMutableTreeNode(service.getID(), true);
					final Vector<Action> actions = service.getActions();
					for (Action action : actions) {
						DefaultMutableTreeNode actionnode = 
								new DefaultMutableTreeNode(action.getName());
						servicenode.add(actionnode);
					}
					final JTree servicetree = new JTree(servicenode);
					MouseListener ml = new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
					         int selRow = servicetree.getRowForLocation(e.getX(), e.getY());
					         if(selRow > 0) {
					        	 if(e.getClickCount() == 2) {
					        		 ActionInfoFrame.getActionInfoFrame(
					        				 (Action) actions.get(selRow - 1))
					        		 .setVisible(true);
					             }
					         }
					     }
					};
					servicetree.addMouseListener(ml);
					JScrollPane pane = new JScrollPane(servicetree);
					this.add(pane);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
