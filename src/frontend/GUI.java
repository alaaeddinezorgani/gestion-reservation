package frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI  extends JFrame{
	SideMenuPanel sp;
	
	public GUI() {
		initComponents();
		sp = new SideMenuPanel(this);
		sp.setMain(mainPanel);
		sp.setSide(sidebar);
		sp.setMinWidth(55);
		sp.setMaxWidth(150);
		sp.setMainAnimation(true);
		sp.setSpeed(4);
		sp.setResponsiveMinWidth(600);
		
	}
	private void initComponents() {
		panel1 = new JPanel();
		sidebar = new JPanel();
		menu = new JButton();
		home = new JButton();
		reservations = new JButton();
		clients = new JButton();
		vehicules = new JButton();
		comptes = new JButton();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		sidebar.setBackground(new Color(16,84,129) );
		sidebar.setPreferredSize(new Dimension(60,32));
		//reservations button
		reservations.setFont(new Font("Microsoft PhagsPa", 0, 14));
		reservations.setForeground(new Color(195,217,233));
		reservations.setIcon(new ImageIcon(getClass().getResource("reservations.png")));
		reservations.setText("Reservations");
		reservations.setBorderPainted(false);
		reservations.setContentAreaFilled(false);
		reservations.setHideActionText(true);
		reservations.setHorizontalAlignment(SwingConstants.LEADING);
		reservations.setHorizontalTextPosition(SwingConstants.RIGHT);
		reservations.setIconTextGap(25);
		reservations.setMargin(new Insets(2,0,2,14));
		reservations.setMinimumSize(new Dimension(0,35));
		reservations.setPreferredSize(new Dimension(50,574));
		//clients button
		clients.setFont(new Font("Microsoft PhagsPa", 0, 14));
		clients.setForeground(new Color(195,217,233));
		clients.setIcon(new ImageIcon(getClass().getResource("clients.png")));
		clients.setText("Clients");
		clients.setBorderPainted(false);
		clients.setContentAreaFilled(false);
		clients.setHideActionText(true);
		clients.setHorizontalAlignment(SwingConstants.LEADING);
		clients.setHorizontalTextPosition(SwingConstants.RIGHT);
		clients.setIconTextGap(25);
		clients.setMargin(new Insets(2,0,2,14));
		clients.setMinimumSize(new Dimension(0,35));
		clients.setPreferredSize(new Dimension(50,574));
		//vehicules button
		vehicules.setFont(new Font("Microsoft PhagsPa", 0, 14));
		vehicules.setForeground(new Color(195,217,233));
		vehicules.setIcon(new ImageIcon(getClass().getResource("vehicules.png")));
		vehicules.setText("Vehicules");
		vehicules.setBorderPainted(false);
		vehicules.setContentAreaFilled(false);
		vehicules.setHideActionText(true);
		vehicules.setHorizontalAlignment(SwingConstants.LEADING);
		vehicules.setHorizontalTextPosition(SwingConstants.RIGHT);
		vehicules.setIconTextGap(25);
		vehicules.setMargin(new Insets(2,0,2,14));
		vehicules.setMinimumSize(new Dimension(0,35));
		vehicules.setPreferredSize(new Dimension(50,574));
		//comptes button
		comptes.setFont(new Font("Microsoft PhagsPa", 0, 14));
		comptes.setForeground(new Color(195,217,233));
		comptes.setIcon(new ImageIcon(getClass().getResource("comptes.png")));
		comptes.setText("Comptes");
		comptes.setBorderPainted(false);
		comptes.setContentAreaFilled(false);
		comptes.setHideActionText(true);
		comptes.setHorizontalAlignment(SwingConstants.LEADING);
		comptes.setHorizontalTextPosition(SwingConstants.RIGHT);
		comptes.setIconTextGap(25);
		comptes.setMargin(new Insets(2,0,2,14));
		comptes.setMinimumSize(new Dimension(0,35));
		comptes.setPreferredSize(new Dimension(50,574));
		//home button
		home.setFont(new Font("Microsoft PhagsPa", 0, 14));
		home.setForeground(new Color(195,217,233));
		home.setIcon(new ImageIcon(getClass().getResource("home.png")));
		home.setText("Home");
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.setHideActionText(true);
		home.setHorizontalAlignment(SwingConstants.LEADING);
		home.setHorizontalTextPosition(SwingConstants.RIGHT);
		home.setIconTextGap(25);
		home.setMargin(new Insets(2,0,2,14));
		home.setMinimumSize(new Dimension(0,35));
		home.setPreferredSize(new Dimension(50,574));
		//menu button
		menu.setBackground(new Color(34,40,47));
		menu.setFont(new Font("Microsoft PhagsPa", 0, 14));
		menu.setIcon(new ImageIcon(getClass().getResource("menu.png")));
		menu.setBorderPainted(false);
		menu.setContentAreaFilled(false);
		menu.setFocusPainted(false);
		menu.setHideActionText(true);
		menu.setHorizontalAlignment(SwingConstants.LEADING);
		menu.setHorizontalTextPosition(SwingConstants.RIGHT);
		menu.setIconTextGap(20);
		menu.setMargin(new Insets(2,0,2,14));
		menu.setMinimumSize(new Dimension(0,35));
		menu.setPreferredSize(new Dimension(50,574));
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menActionPerformed(evt);
			}
		});
		
		GroupLayout sidebarLayout = new GroupLayout(sidebar);
		sidebar.setLayout(sidebarLayout);
		sidebarLayout.setHorizontalGroup(sidebarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(home,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(sidebarLayout.createSequentialGroup())
				.addGroup(sidebarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(menu, GroupLayout.PREFERRED_SIZE,55,GroupLayout.PREFERRED_SIZE)
				.addComponent(reservations, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
				.addComponent(clients, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE)
				.addComponent(vehicules, GroupLayout.PREFERRED_SIZE, 520, GroupLayout.PREFERRED_SIZE)
				.addComponent(comptes, GroupLayout.PREFERRED_SIZE, 675, GroupLayout.PREFERRED_SIZE))
				.addGap(55,55,55)
				);
		
		sidebarLayout.setVerticalGroup(
				sidebarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(sidebarLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(menu, GroupLayout.PREFERRED_SIZE,35,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(home,GroupLayout.PREFERRED_SIZE,35,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(reservations,GroupLayout.PREFERRED_SIZE,35,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(clients,GroupLayout.PREFERRED_SIZE,35,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(vehicules,GroupLayout.PREFERRED_SIZE,35,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(comptes,GroupLayout.PREFERRED_SIZE,35,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
						.addGap(18, 18, 18)
						
			
						
						)
				
				);
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(210,231,255));
		mainPanel.setLayout(new BorderLayout());
		topBar = new NewJPanel2();
		//topBar.setPreferredSize(new Dimension(40,40));
		//topBar.setBackground(Color.blue);
		mainPanel.add(topBar, BorderLayout.NORTH);
		cardLayout = new CardLayout();
		content = new JPanel();
		content.setLayout(cardLayout);
		homePan = new HomePane();
		//homePan.setBackground(Color.gray);
		reservationsPan = new Reservations();
		reservationsPan.setBackground(Color.magenta);
		clientsPan = new Clients();
		//clientsPan.setBackground(Color.green);
		vehiculesPan = new Vehicules();
		//vehiculesPan.setBackground(Color.cyan);
		comptesPan = new Comptes();
		comptesPan.setBackground(Color.red);
		content.add(homePan, "1");
		content.add(reservationsPan, "2");
		content.add(clientsPan, "3");
		content.add(vehiculesPan, "4");
		content.add(comptesPan, "5");
		mainPanel.add(content, BorderLayout.CENTER);
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(content, "1");
			}
		});
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(content, "1");
			}
		});
		reservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(content, "2");
			}
		});
		clients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(content, "3");
			}
		});
		vehicules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(content, "4");
			}
		});
		comptes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(content, "5");
			}
		});
		
		
		
		GroupLayout panel1Layout = new GroupLayout(panel1);
		panel1.setLayout(panel1Layout);
		panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panel1Layout.createSequentialGroup()
				.addComponent(sidebar, GroupLayout.PREFERRED_SIZE,55,GroupLayout.PREFERRED_SIZE)
				.addGap(0,0,0)
				.addComponent(mainPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
						)
				
				);
		panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(sidebar,GroupLayout.DEFAULT_SIZE,419,Short.MAX_VALUE)
				.addComponent(mainPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
				);
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(panel1,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
				);
		pack();
		setLocationRelativeTo(null);

	}
	
	private void menActionPerformed(ActionEvent evt) {
		sp.onSideMenu();
	}
	
	public static void main(String[] args) {
		//look and feel?
		try {
			for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());;
					break;
				}
			}
		} catch(ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
		} catch(InstantiationException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
		} catch(IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null,ex);
		} catch(UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//create and display the form
		/*
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUI().setVisible(true);
			}
		});
		*/
		new GUI().setVisible(true);
	}
	private JButton menu, home, vehicules, clients, reservations, comptes;
	private JPanel mainPanel, sidebar, panel1, topBar, content, homePan, vehiculesPan, reservationsPan, clientsPan, comptesPan;
	private CardLayout cardLayout;
}
