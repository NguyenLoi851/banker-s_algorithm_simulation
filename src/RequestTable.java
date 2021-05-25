import java.awt.BorderLayout;
import java.util.*;

import javax.swing.*;

public class RequestTable {
	
	public static JPanel panel;
	
	RequestTable(){
		JLabel label = new JLabel();
        label.setText("Request");
        label.setVerticalTextPosition(JLabel.TOP);
        
        String[] nameOfColumn = new String[MyFrame.numberOfResource+1];
        nameOfColumn[0] = "";
        for( int i=1 ; i<=MyFrame.numberOfResource ; i++) {
        	nameOfColumn[i] = "R"+String.valueOf(i);
        }
        int process = MyFrame.numberOfProcessRequest;
        int resource = MyFrame.numberOfResource;
        String[][] content = new String[process][resource+1];
        
        Set<Integer> temp = new LinkedHashSet<Integer>(MyFrame.nameOfProcessRequest);
        //ArrayList<Integer> temp = new ArrayList<Integer>(MyFrame.nameOfProcessRequest);
        Iterator<Integer> it = temp.iterator();
        for ( int i=0 ; i<process ; i++) {
        	content[i][0] = "P"+String.valueOf(it.next());
        	for( int j=1 ; j<=resource ; j++) {
        		content[i][j] = String.valueOf(MyFrame.request.get(i).get(j-1));
        	}
        }
        
        JTable table = new JTable(content, nameOfColumn);
        JScrollPane tableContainer = new JScrollPane(table);
        panel=new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(1005,200,250,200);
        panel.add(label,BorderLayout.NORTH);
        panel.add(tableContainer,BorderLayout.CENTER);
	}
}
