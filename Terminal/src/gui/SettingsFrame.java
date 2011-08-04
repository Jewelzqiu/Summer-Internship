package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
public class SettingsFrame extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel IPPanel;
	private JButton CancelButton;
	private JButton OKButton;
	private JPanel PortPanel;
	private JPanel ButtonsPanel;
	private JLabel IPLabel;
	private JLabel PortLabel;
	private JTextField IPText;
	private JTextField PortText;

	/**
	* Auto-generated main method to display this JFrame
	*/
		
	public SettingsFrame(String IP, int port) {
		super();
		initGUI(IP, port);
	}
	
	private void initGUI(String IP, int port) {
		try {
			BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS);
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("Settings");
			{
				IPPanel = new JPanel();
				IPPanel.setLayout(new BoxLayout(IPPanel, BoxLayout.X_AXIS));
				getContentPane().add(IPPanel);
				{
					IPLabel = new JLabel();
					IPPanel.add(IPLabel);
					IPLabel.setText(" IP address: ");
					IPText = new JTextField();
					if (port != 0) {
						IPText.setText(IP);
					}
					IPText.setEditable(true);
					//IPText.setPreferredSize(new java.awt.Dimension(250, 100));
					IPPanel.add(IPText);
				}
			}
			{
				PortPanel = new JPanel();
				PortPanel.setLayout(new BoxLayout(PortPanel, BoxLayout.X_AXIS));
				PortLabel = new JLabel("            Port: ");
				PortPanel.add(PortLabel);
				PortText = new JTextField();
				if (port != 0) {
					PortText.setText(port + "");
				}
				PortText.setEditable(true);
				PortPanel.add(PortText);
				//PortText.setPreferredSize(new java.awt.Dimension(250, 100));
				getContentPane().add(PortPanel);
			}
			{
				ButtonsPanel = new JPanel();
				getContentPane().add(ButtonsPanel);
				{
					OKButton = new JButton();
					OKButton.addActionListener(new OKListener());
					ButtonsPanel.add(OKButton);
					OKButton.setText("   OK   ");
				}
				{
					CancelButton = new JButton();
					CancelButton.addActionListener(new CancelListener());
					ButtonsPanel.add(CancelButton);
					CancelButton.setText("Cancel");
				}
			}
			pack();
			setSize(220, 120);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private class OKListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				PrintWriter out = new PrintWriter(
						new BufferedWriter(new FileWriter("network.cfg")));
				out.println(IPText.getText());
				out.println(PortText.getText());
				out.close();
				SettingsFrame.this.dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SettingsFrame.this.dispose();
		}
		
	}	

}
