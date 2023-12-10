package Lab_5;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * builder
 */
public class builder {
    // basic frame
    JTextArea textarea = new JTextArea();
    public void Serverframe(JFrame frame, int width, int height){
        JScrollPane panel = new JScrollPane(textarea);
        frame.setTitle("Server");
        frame.setBounds(0,0,width,height);
        frame.setVisible(true);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        textarea.setSize(width, height);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        Font secondFont = new Font("Arial", Font.PLAIN, 16);
        textarea.setFont(secondFont);
        // panel.add(textarea);
    }

    //client frame
    JTextArea areatxt = new JTextArea(23,28), consoleField = new JTextArea(23,30);
    JTextField toWhofield = new JTextField(), usernamefield = new JTextField(), messageField = new JTextField("",30);
    JLabel toWholabel = new JLabel("Send to: "), username = new JLabel("Username: "), messagelabel = new JLabel("Message: ");
    JButton send = new JButton("Send");
    JPanel fromTo = new JPanel(), messagepane = new JPanel(), messagefieldPanel = new JPanel();
    JCheckBox encrptbox = new JCheckBox("Encrypt"), fancy = new JCheckBox("Fancy");

    /**
     * 
     * @param frame frame or the JFrame
     * @param width with of the frame
     * @param height height of the frame
     */
    public void Clientframe(JFrame frame, int width, int height){
        frame.setTitle("Client | Connection");
        frame.setBounds(600,0, width,height);
        frame.setVisible(true);
        frame.add(fromTo, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // buttons panels
        fromTo.add(username);
        fromTo.add(usernamefield);
        fromTo.add(toWholabel);
        fromTo.add(toWhofield);
        fromTo.add(send);
        fromTo.setLayout(new GridLayout(1,4));

        // message sending panel
        frame.add(messagepane,BorderLayout.CENTER);
        messagepane.add(messagefieldPanel, BorderLayout.NORTH);
        messagefieldPanel.add(messagelabel);
        messagefieldPanel.add(messageField);
        messagefieldPanel.add(encrptbox);  
        messagefieldPanel.add(fancy);  
    
        JScrollPane areatxtpanel = new JScrollPane(areatxt);
        messagepane.add(areatxtpanel);
        // scrolling
        areatxtpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        messagepane.setLayout(new GridLayout(3,1));
        messagepane.add(consoleField);
        consoleField.setBackground(Color.LIGHT_GRAY);
        areatxt.setLineWrap(true);
        areatxt.setWrapStyleWord(true);
    }

    public void console(String str){
         System.out.println(str);
    }

}
