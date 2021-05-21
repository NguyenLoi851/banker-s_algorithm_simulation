
import java.util.ArrayList;


import javax.swing.*;
import java.awt.*;

public class AvailableTable extends JFrame{

	JLabel label = new JLabel();
	public static JPanel panel;
	
	AvailableTable(){
			
	        label.setText("AVAILABLE");
	        label.setVerticalTextPosition(JLabel.TOP);
	       
	        String[] nameOfColumn = new String[MyFrame.numberOfResource];
	        for( int i=0 ; i<MyFrame.numberOfResource ; i++) {
	        	nameOfColumn[i] = "R"+String.valueOf(i+1);
	        }
	        String[][] content = new String[1][MyFrame.numberOfResource];
	        for ( int i=0 ; i<1 ; i++) {
	        	for ( int j=0 ; j< MyFrame.numberOfResource ; j++) {
	        		if(i==0) {
	        			content[i][j] = String.valueOf(MyFrame.available.get(j));
	        		}else {
	        			content[i][j] = "";
	        		}
	        	}
	        }
	        JTable table = new JTable(content, nameOfColumn);
	        JScrollPane tableContainer = new JScrollPane(table);
	        panel=new JPanel();
	        panel.setLayout(new BorderLayout());
	        panel.setBounds(358,115,250,75);
	        panel.add(label,BorderLayout.NORTH);
	        panel.add(tableContainer,BorderLayout.CENTER);
	        
	}
}
