package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;
import terminal.Terminal;


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
public class DevicesWindow extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane InfoTab;
	private JButton SettingsButton;
	private JButton ServicesButton;
	private JButton RefreshButton;
	private JPanel Options;
	
	private String IP;
	private int port;
	private Vector<Hashtable<String, ArrayList<ArrayList<Object>>>> Services =
			new Vector<Hashtable<String, ArrayList<ArrayList<Object>>>>();

	/**
	* Auto-generated main method to display this JFrame
	 * @throws Exception 
	*/
		
	public DevicesWindow() throws Exception {
		super();
		checkIP();
		initGUI();
		Refresh refresh = new Refresh();
		refresh.start();
	}
	
	private void initGUI() {
		try {
			FlowLayout thisLayout = new FlowLayout();
			getContentPane().setLayout(thisLayout);
			{
				InfoTab = new JTabbedPane();
				getContentPane().add(InfoTab);
				InfoTab.setPreferredSize(new java.awt.Dimension(500, 360));
			}
			{
				Options = new JPanel();
				getContentPane().add(Options);
				Options.setPreferredSize(new java.awt.Dimension(120, 320));
				{
					SettingsButton = new JButton();
					SettingsButton.addActionListener(new SettingsListener());
					Options.add(SettingsButton);
					SettingsButton.setText("Settings");
					SettingsButton.setPreferredSize(new java.awt.Dimension(100, 25));
				}
				{
					RefreshButton = new JButton();
					RefreshButton.addActionListener(new RefreshListener());
					Options.add(RefreshButton);
					RefreshButton.setText("Refresh");
					RefreshButton.setPreferredSize(new java.awt.Dimension(100, 25));
				}
				{
					ServicesButton = new JButton();
					ServicesButton.addActionListener(new ServicesListener());
					Options.add(ServicesButton);
					ServicesButton.setText("Services");
					ServicesButton.setPreferredSize(new java.awt.Dimension(100, 25));
				}
			}
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(650, 400);
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void addTabs(Vector<Hashtable<String, Object>> devices) {
		for (Hashtable<String, Object> info : devices) {
			int n = info.size();
			String[][] data = new String[n - 1][2];
			int flag = 0;
			for (String key : info.keySet()) {
				if (!key.equals("Device Services")) {
					data[flag][0] = key;
					data[flag++][1] = info.get(key).toString();
				}
			}
			InfoTab.addTab(
					info.get("UPnP.device.modelName").toString(),
					new TabPanel(info));
			Services.add(
					(Hashtable<String, ArrayList<ArrayList<Object>>>)
					info.get("Device Services"));
		}
	}
	
	private void checkIP() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("network.cfg"));
			IP = in.readLine();
			port = new Integer(in.readLine());
		} catch (FileNotFoundException e) {
			JFrame Settings = new SettingsFrame("", 0);
			Settings.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class SettingsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SettingsFrame settings = new SettingsFrame(IP, port);
			settings.setVisible(true);
		}
		
	}
	
	private class RefreshListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {				
				InfoTab.removeAll();
				//addTabs(Terminal.getDevicesInfo(IP, port));
				Refresh refresh = new Refresh();
				refresh.start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	private class ServicesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = InfoTab.getSelectedIndex();
			Hashtable<String, ArrayList<ArrayList<Object>>> services = Services.get(i);
			new ServicesFrame(services).setVisible(true);
		}
		
	}
	
	private class Refresh extends Thread {
		public void run() {
			Vector<Hashtable<String, Object>> devices;
			try {
				devices = Terminal.getDevicesInfo(IP, port);
				if (devices != null) {
					Services = new Vector<Hashtable<String, ArrayList<ArrayList<Object>>>>();
					addTabs(devices);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	};
}
