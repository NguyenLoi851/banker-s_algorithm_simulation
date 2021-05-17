import java.awt.BorderLayout;

import javax.swing.*;

public class AllocationTable {
	
	public static JPanel panel;
	
	AllocationTable(){
		JLabel label = new JLabel();
        label.setText("Allocation");
        label.setVerticalTextPosition(JLabel.TOP);
        
        String[] nameOfColumn = new String[MyFrame.numberOfResource+1];
        nameOfColumn[0] = "";
        for( int i=1 ; i<=MyFrame.numberOfResource ; i++) {
        	nameOfColumn[i] = "R"+String.valueOf(i);
        }
        int process = MyFrame.numberOfProcess;
        int resource = MyFrame.numberOfResource;
        String[][] content = new String[process][resource+1];
        for (int i=0 ; i<process ; i++) {
        	content[i][0] = "P"+String.valueOf(i+1);
        	for( int j=1 ; j<=resource ; j++ ) {
        		content[i][j] = String.valueOf(MyFrame.allocation.get(i).get(j-1));
        	}
        }
        
        JTable table = new JTable(content, nameOfColumn);
        JScrollPane tableContainer = new JScrollPane(table);
        panel=new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(300,150,250,250);
        panel.add(label,BorderLayout.NORTH);
        panel.add(tableContainer,BorderLayout.CENTER);
	}
}
