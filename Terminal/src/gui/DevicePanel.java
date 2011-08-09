package gui;

import java.util.Hashtable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
public class DevicePanel extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane InfoScrollPane;
	private JTable InfoTable;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	public DevicePanel(Hashtable<String, Object> info) {
		super();
		initGUI(info);
	}
	
	private void initGUI(Hashtable<String, Object> info) {
		try {
			setPreferredSize(new java.awt.Dimension(400, 300));
			{
				InfoScrollPane = new JScrollPane();
				this.add(InfoScrollPane);
				InfoScrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
				int n = info.size();
				String[][] data = new String[n][2];
				int flag = 0;
				for (String key : info.keySet()) {
					data[flag][0] = key;
					data[flag++][1] = info.get(key).toString();
				}
				{
					TableModel InfoTableModel = 
							new DefaultTableModel(
									data,
									new String[] { "Attribute", "Value" });
					InfoTable = new JTable();
					InfoScrollPane.setViewportView(InfoTable);
					InfoTable.setModel(InfoTableModel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
