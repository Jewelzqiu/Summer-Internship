package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jdesktop.application.SingleFrameApplication;

import device.Device;
import device.Service;

import util.Connection;


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
/**
 * 
 */
public class TerminalApplication extends SingleFrameApplication {
    private JPanel topPanel;
    private ButtonGroup buttonGroup;
    private JRadioButton DeviceRadioButton;
    private JButton ServicesButton;
    private JButton RefreshButton;
    private JButton SettingsButton;
    private JRadioButton ServiceRadioButton;
    private JPanel LeftPanel;
	private String IP;
	private int port;
	private JScrollPane DetailsPane;
	private JComboBox ComboBox;
	private JPanel InfoPanel;
	private Vector<Device> Devices;
	private Hashtable<String, Vector<Service>> Services;

    @Override
    protected void startup() {
    	{
    		topPanel = new JPanel();
    		getMainFrame().getContentPane().add(topPanel, BorderLayout.NORTH);
    		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    		topPanel.setPreferredSize(new java.awt.Dimension(600, 360));
    		topPanel.add(getLeftPanel());
    		topPanel.add(getInfoPanel());
    	}
        show(topPanel);
        checkConfig();
        new Refresh().start();
    }

    public static void main(String[] args) {
        launch(TerminalApplication.class, args);
    }
    
    private ButtonGroup getButtonGroup() {
    	if(buttonGroup == null) {
    		buttonGroup = new ButtonGroup();
    		buttonGroup.add(DeviceRadioButton);
    		buttonGroup.add(ServiceRadioButton);    		
    	}
    	return buttonGroup;
    }
    
    private JPanel getLeftPanel() {
    	if(LeftPanel == null) {
    		LeftPanel = new JPanel();
    		LeftPanel.add(getDeviceRadioButton());
    		LeftPanel.add(getServiceRadioButton());
    		LeftPanel.add(getSettingsButton());
    		LeftPanel.add(getRefreshButton());
    		LeftPanel.add(getServicesButton());
    		getButtonGroup();
    		LeftPanel.setPreferredSize(new java.awt.Dimension(100, 336));
    		LeftPanel.setLayout(null);
    	}
    	return LeftPanel;
    }
    
    private JRadioButton getDeviceRadioButton() {
    	if(DeviceRadioButton == null) {
    		DeviceRadioButton = new JRadioButton();
    		DeviceRadioButton.setName("DeviceRadioButton");
    		DeviceRadioButton.setBounds(12, 26, 77, 24);
    		DeviceRadioButton.addActionListener(new DeviceSelectedListener());
    	}
    	return DeviceRadioButton;
    }
    
    private JRadioButton getServiceRadioButton() {
    	if(ServiceRadioButton == null) {
    		ServiceRadioButton = new JRadioButton();
    		ServiceRadioButton.setName("ServiceRadioButton");
    		ServiceRadioButton.setBounds(12, 50, 80, 24);
    		ServiceRadioButton.addActionListener(new ServiceSelectedListener());
    	}
    	return ServiceRadioButton;
    }
    
    private JButton getSettingsButton() {
    	if(SettingsButton == null) {
    		SettingsButton = new JButton();
    		SettingsButton.setName("SettingsButton");
    		SettingsButton.setBounds(12, 86, 81, 30);
    		SettingsButton.addActionListener(new SettingsListener());
    	}
    	return SettingsButton;
    }
    
    private JButton getRefreshButton() {
    	if(RefreshButton == null) {
    		RefreshButton = new JButton();
    		RefreshButton.setName("RefreshButton");
    		RefreshButton.setBounds(12, 122, 81, 30);
    		RefreshButton.addActionListener(new RefreshListener());
    	}
    	return RefreshButton;
    }
    
    private JButton getServicesButton() {
    	if(ServicesButton == null) {
    		ServicesButton = new JButton();
    		ServicesButton.setName("ServicesButton");
    		ServicesButton.setBounds(12, 158, 81, 30);
    		ServicesButton.addActionListener(new ServicesListener());
    	}
    	return ServicesButton;
    }
    
    private void addDevices() {
    	if (Devices == null || Devices.isEmpty()) {
    		return;
    	}
    	String[] names = new String[Devices.size()];
    	for (int i = 0; i < Devices.size(); i++) {
    		names[i] = Devices.get(i).getName();
    	}
		ComboBoxModel ComboBoxModel = new DefaultComboBoxModel(names);
    	ComboBox.setModel(ComboBoxModel);
    	
    	Device device = Devices.get(0);
    	if (device != null) {
    		Hashtable<String, Object> info = device.getDeviceInfo();
				int n = info.size();
				String[][] data = new String[n][2];
				int flag = 0;
				for (String key : info.keySet()) {
					data[flag][0] = key;
					data[flag++][1] = info.get(key).toString();
				}
				TableModel InfoTableModel = 
						new DefaultTableModel(
								data, new String[] { "Attribute", "Value" }
						);
				JTable infotable = new JTable();
				DetailsPane.setViewportView(infotable);
				infotable.setModel(InfoTableModel);
    	}
    }
    
    private void addServices() {
    	if (Services == null || Services.isEmpty()) {
    		return;
    	}
    	String[] types = new String[Services.size()];
    	int flag = 0;
    	for (String type : Services.keySet()) {
    		types[flag++] = type;
    	}
    	ComboBoxModel ComboBoxModel = new DefaultComboBoxModel(types);
    	ComboBox.setModel(ComboBoxModel);
    	
    	if (types.length > 0) {
    		Vector<Service> services = Services.get(types[0]);
    		DetailsPane.setViewportView(new ServicePanel(services));
    	}
    }
    
    private class DeviceSelectedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				ServicesButton.setEnabled(true);
				new Refresh().start();
				addDevices();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	
    }
    
    private class ServiceSelectedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				ServicesButton.setEnabled(false);
				new Refresh().start();
				addServices();
			} catch (Exception exp) {
				exp.printStackTrace();
			}
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
				Refresh refresh = new Refresh();
				refresh.start();
				if (DeviceRadioButton.isSelected()) {
					addDevices();
				} else if (ServiceRadioButton.isSelected()) {
					addServices();
				}				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private class ServicesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = ComboBox.getSelectedIndex();
			new ServicesFrame(Devices.get(i).getServices()).setVisible(true);
		}
		
	}
	
	private void checkConfig() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("network.cfg"));
			IP = in.readLine();
			port = new Integer(in.readLine());
		} catch (FileNotFoundException e) {
			JFrame Settings = new SettingsFrame("", 0);
			Settings.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JPanel getInfoPanel() {
		if(InfoPanel == null) {
			InfoPanel = new JPanel();
			InfoPanel.setPreferredSize(new java.awt.Dimension(495, 336));
			InfoPanel.setLayout(null);
			InfoPanel.add(getComboBox());
			InfoPanel.add(getDetailsPane());
		}
		return InfoPanel;
	}
	
	private JComboBox getComboBox() {
		if(ComboBox == null) {
			ComboBox = new JComboBox();
			ComboBox.setBounds(10, 10, 476, 21);
			ComboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (DeviceRadioButton.isSelected()) {
						int index = ((JComboBox) arg0.getSource()).getSelectedIndex();
						Hashtable<String, Object> info = 
							Devices.get(index).getDeviceInfo();
						int n = info.size();
						String[][] data = new String[n][2];
						int flag = 0;
						for (String key : info.keySet()) {
							data[flag][0] = key;
							data[flag++][1] = info.get(key).toString();
						}
						TableModel InfoTableModel = 
								new DefaultTableModel(
										data, new String[] { "Attribute", "Value" }
								);
						JTable infotable = new JTable();
						DetailsPane.setViewportView(infotable);
						infotable.setModel(InfoTableModel);
						
					} else if (ServiceRadioButton.isSelected()) {
						String type = ((JComboBox) arg0.getSource())
								.getSelectedItem().toString();
						Vector<Service> services = Services.get(type);
						DetailsPane.setViewportView(new ServicePanel(services));
					}
				}
			});
		}
		return ComboBox;
	}
	
	private JScrollPane getDetailsPane() {
		if(DetailsPane == null) {
			DetailsPane = new JScrollPane();
			DetailsPane.setBounds(10, 41, 476, 310);
		}
		return DetailsPane;
	}

	private class Refresh extends Thread {
		public void run() {
			
			try {
				BufferedReader in = new BufferedReader(new FileReader("network.cfg"));
				IP = in.readLine();
				port = new Integer(in.readLine());
				Vector<Hashtable<String, Object>> devices = Connection.getDevicesInfo(IP, port);
				Devices = new Vector<Device>();
				Services = new Hashtable<String, Vector<Service>>();
				if (devices != null) {
					for (Hashtable<String, Object> device : devices) {
						Device newdevice = new Device(device);
						Devices.add(newdevice);
						for (Service service : newdevice.getServices()) {
							if (Services.containsKey(service.getType())) {
								Vector<Service> services = Services.get(service.getType());
								services.add(service);
								Services.put(service.getType(), services);
							} else {
								Vector<Service> services = new Vector<Service>();
								services.add(service);
								Services.put(service.getType(), services);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	};

}
