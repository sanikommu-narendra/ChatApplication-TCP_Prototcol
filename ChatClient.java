import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
public class ChatClient extends JFrame implements ActionListener{
	TextArea ta,ta2;
	JButton b;
	JLabel l;
	JPanel p1,p2;

	String str;
	ServerSocket sc;
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	ChatClient() throws Exception{
		super("MyChat");
		setLayout(null);
		ta=new TextArea("",1,30,TextArea.SCROLLBARS_VERTICAL_ONLY);
		ta2=new TextArea("",1,30,TextArea.SCROLLBARS_VERTICAL_ONLY);

		b=new JButton("send");
		l=new JLabel("");
		ta.setBounds(10,10,400,300);ta.setEditable(false);
		ta2.setBounds(10,320,300,30);
		b.setBounds(320,320,90,30);
		add(ta);
		add(ta2);
		add(b);
		b.addActionListener(this);
		setBackground(new Color(255, 255, 255));
		setVisible(true);
		setSize(430,390);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s=new Socket("127.0.0.1",2026);
		System.out.println("connected successfully");
		din=new DataInputStream(s.getInputStream());
		dout=new DataOutputStream(s.getOutputStream());

		new Thread(){
			public void run() {
				try { while(true) {
						String st=din.readUTF();
						ta.append("\n"+"Friend:"+st);
						}
				}
				catch(Exception e){}
			}
		}.start();
		
		
	}


	public void actionPerformed(ActionEvent ae) {
		try {
			 
			String st=ta2.getText();
			dout.writeUTF(st);
			ta.append("\n"+"You:"+st);
			ta2.setText("");
			
			
			
		}
		catch(Exception e){System.out.println("Your Friend Might Have Disconnected");}
	}
	
	public static void main(String[] args) throws Exception {
		new ChatClient();
	}

}
