import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class MyFrame extends JFrame implements ActionListener{

	JFrame frame=new JFrame();
	JButton buttonRandom, buttonReset, buttonRun, buttonRequest;
	
	JTextArea resultText=new JTextArea(12,20);
	
	public static int numberOfResource, numberOfProcess, numberOfProcessRequest;
	public static boolean checkRequest = false;
	public static ArrayList<Integer> available, work, orderOfExecution,initial;
	public static ArrayList<ArrayList<Integer>> allocation, max, need, request;
	public static ArrayList<Integer> temp;
	public static ArrayList<Boolean> finish;
	public static Set<Integer> nameOfProcessRequest;
	
	
	MyFrame(){
		frame.setTitle("Banker's Algorithm Simulator");
	    frame.setSize(1200,1200);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(null);
	    
	    resultText.setText("");
	    resultText.setLineWrap(true);
	    JScrollPane textContainer=new JScrollPane(resultText);
	    textContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    textContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    textContainer.setSize(100,100);
	    textContainer.setVisible(true);
	    JPanel panel=new JPanel();
	    panel.setLayout(new BorderLayout());
	    panel.setBounds(10,450,1120,450);
	    panel.add(textContainer,BorderLayout.NORTH);
	    
	    buttonRandom =new JButton("Start Random");
	    buttonRandom.setBounds(980,10,150,50);
	    buttonRandom.addActionListener(this);
	    
	    /*
	    buttonReset = new JButton("Reset");
	    buttonReset.setBounds(980,70,150,50);
	    buttonReset.addActionListener(this);
	    */
	    
	    buttonRun = new JButton("Run");
	    buttonRun.setBounds(800,70,150,50);
	    buttonRun.addActionListener(this);
	    
	    buttonRequest = new JButton("Request Random");
	    buttonRequest.setBounds(800,10,150,50);
	    buttonRequest.addActionListener(this);
	    
	    ImageIcon icon = new ImageIcon("bank.jpg");
	    
	    frame.getContentPane().add(panel);
        frame.add(buttonRandom);
        frame.add(buttonRequest);
        //frame.add(buttonReset);
        frame.add(buttonRun);
        frame.setIconImage(icon.getImage());
        frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Random rand = new Random();
		
		if(e.getSource() == buttonRandom) { 			// click Start button
			//Initial table random 
			
			numberOfResource = rand.nextInt(5)+2 ;
			numberOfProcess = rand.nextInt(10)+2;
			
			initial = new ArrayList<Integer>();
			for ( int i=0 ; i<numberOfResource ; i++ ) {
				initial.add(rand.nextInt(15) +2);
			}
			
			// Initial Table appear 
			InitialTable initialTable = new InitialTable();
			frame.add(initialTable.panel);
			
			//Max table random
			
			max = new ArrayList<ArrayList<Integer>>();
			for( int j=0 ; j<numberOfProcess ; j++) {
				temp = new ArrayList<Integer>();
				for ( int i=0 ; i<numberOfResource ; i++) {
					temp.add(rand.nextInt(5)+2);
				}
				max.add(temp);
			}
			 
			//Max table appear
			
			MaxTable maxTable = new MaxTable();
			frame.add(maxTable.panel);
			
			// Allocation table random
			allocation = new ArrayList<ArrayList<Integer>>();
			for( int j=0 ; j<numberOfProcess ; j++) {
				temp = new ArrayList<Integer>();
				for ( int i=0 ; i<numberOfResource ; i++) {
					temp.add(rand.nextInt(5)+2);
				}
				allocation.add(temp);
			}
			
			//Allocation Table appear
			AllocationTable allocationTable = new AllocationTable();
			frame.add(allocationTable.panel);
			
			//Need table random
			int z;
			need = new ArrayList<ArrayList<Integer>>();
			for ( int j=0 ; j<numberOfProcess ; j++) {
				temp = new ArrayList<Integer>();
				for ( int i=0 ; i<numberOfResource ; i++) {
					z = max.get(j).get(i) - allocation.get(j).get(i);
					if(z>0) temp.add(z);
					else temp.add(0);
				}
				need.add(temp);
			}
			
			//Need table appear
			NeedTable needTable = new NeedTable();
			frame.add(needTable.panel);
			
			//Available table random
			available = new ArrayList<Integer>();
			for ( int i=0 ; i<numberOfResource ; i++) {
				int sum = 0 ;
				for ( int j=0 ; j<numberOfProcess ; j++) {
					sum += allocation.get(j).get(i);
				}
				available.add(initial.get(i) - sum);
			}
			
			//Available table appear
			AvailableTable availableTable = new AvailableTable();
			frame.add(availableTable.panel);
		}
	
		if(e.getSource() == buttonRequest) { 			//click Request button
			//request table random
			
			checkRequest = true;
			request = new ArrayList<ArrayList<Integer>>();
			nameOfProcessRequest = new LinkedHashSet<Integer>();
			numberOfProcessRequest = rand.nextInt(numberOfProcess)+1;
			while(nameOfProcessRequest.size() < numberOfProcessRequest) {
				nameOfProcessRequest.add(rand.nextInt(numberOfProcess)+1);
			}
			for (int j=0 ; j<numberOfProcessRequest ; j++) {
				temp = new ArrayList<Integer>();
				for( int i=0 ; i<numberOfResource ; i++) {
					temp.add(rand.nextInt(5));
				}
				request.add(temp);
			}
			//request table appear
			RequestTable requestTable = new RequestTable();
			frame.add(requestTable.panel);
		}
		
		if(e.getSource() == buttonRun) {				//click Run button
			resultText.setText(resultText.getText()+"\n---------- Kết quả chạy thuật toán ----------\n");
			if(checkRequest == false) {
				if(safe() == true) {
					resultText.setText(resultText.getText()+"\n"+" -> Trạng thái: An toàn \n");
					resultText.setText(resultText.getText()+"\n"+" -> Quá trình thực thi: ");
					for( int i=0 ; i<numberOfProcess ; i++) {
						if(i!=numberOfProcess-1)
							resultText.setText(resultText.getText()+" P"+String.valueOf(orderOfExecution.get(i))+" -> ");
						else
							resultText.setText(resultText.getText()+" P"+String.valueOf(orderOfExecution.get(i))+".\n");
					}
				}else {
					resultText.setText(resultText.getText()+"\n"+" -> Trạng thái: Không an toàn \n");
				}
			}else {
				//đưa từng tiến trình trong bảng request vào
				Integer[] temp = nameOfProcessRequest.toArray(new Integer[nameOfProcessRequest.size()]);
				for ( int i=0 ; i<numberOfProcessRequest ; i++) {
					//xét tiến trình với need[i]
					if(aSmallAndEqualThanB(request.get(i), need.get(temp[i]-1)) == false) {
						resultText.setText(resultText.getText()+"\n"+" -> P"+String.valueOf(temp[i])+
								": Yêu cầu vượt quá khai báo Tài nguyên. Do request(P"
								+String.valueOf(temp[i])+") = (");
						for ( int j=0 ; j<numberOfResource ; j++) {
							if(j!=numberOfResource-1)
								resultText.setText(resultText.getText()+String.valueOf(request.get(i).get(j)+" ; "));
							else
								resultText.setText(resultText.getText()+String.valueOf(request.get(i).get(j)+" ) "));
						}
						resultText.setText(resultText.getText()+"> need(P"+String.valueOf(temp[i]) +")= (");
						for ( int j=0 ; j<numberOfResource ; j++) {
							if(j!=numberOfResource-1)
								resultText.setText(resultText.getText()+String.valueOf(need.get(temp[i]-1).get(j)+" ; "));
							else
								resultText.setText(resultText.getText()+String.valueOf(need.get(temp[i]-1).get(j)+" ) \n"));
						}
					}else {
						//xét tiến trình với available
						if(aSmallAndEqualThanB(request.get(i), available) == false) {
							resultText.setText(resultText.getText()+"\n"+" -> P"+String.valueOf(temp[i])+
									": Không đủ tài nguyên, tiến trình phải đợi. Do request(P"
									+String.valueOf(temp[i])+") = (");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText.setText(resultText.getText()+String.valueOf(request.get(i).get(j)+" ; "));
								else
									resultText.setText(resultText.getText()+String.valueOf(request.get(i).get(j)+" ) "));
							}
							resultText.setText(resultText.getText()+"> available = (");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText.setText(resultText.getText()+String.valueOf(available.get(j)+" ; "));
								else
									resultText.setText(resultText.getText()+String.valueOf(available.get(j)+" ) \n"));
							}
						}else {
							for( int j=0 ; j<numberOfResource ; j++) {
								available.set(j, available.get(j) - request.get(i).get(j));
								allocation.get(temp[i]-1).set(j, allocation.get(temp[i]-1).get(j) + request.get(i).get(j));
								need.get(temp[i]-1).set(j, need.get(temp[i]-1).get(j) - request.get(i).get(j));
							}
							if(safe() == true) {
								resultText.setText(resultText.getText()+"\n"+" -> P"+
										String.valueOf(temp[i])+": Được phân phối theo yêu cầu"+"\n");
							}else {
								resultText.setText(resultText.getText()+"\n"+" -> P"+
										String.valueOf(temp[i])+": Phải đợi do có thể gây deadlock" +"\n");
								
								for( int j=0 ; j<numberOfResource ; j++) {
									available.set(j, available.get(j) + request.get(i).get(j));
									allocation.get(temp[i]-1).set(j, allocation.get(temp[i]-1).get(j) - request.get(i).get(j));
									need.get(temp[i]-1).set(j, need.get(temp[i]-1).get(j) + request.get(i).get(j));
								}
							}
						}
					}
				}
			}
		}
	}
    
	// Kiem tra a < b
	public boolean aSmallAndEqualThanB( ArrayList<Integer> a, ArrayList<Integer> b) {
		for( int i=0 ; i<numberOfResource ; i++) {
			if(a.get(i) > b.get(i)) return false;
		}
		return true;
	}
	
	//chay thuat toan an toan
	public boolean safe() {
		orderOfExecution = new ArrayList<Integer>();
		int countStep = 0;
		work = new ArrayList<Integer>(available);
		finish = new ArrayList<Boolean>(Collections.nCopies(numberOfProcess, false));
		boolean flag = true;
		while(flag == true) {
			flag = false;
			for ( int i=0 ; i<numberOfProcess ; i++) {
				if(finish.get(i) == false && aSmallAndEqualThanB(need.get(i), work) == true) {
					countStep += 1;
					finish.set(i, true);
					orderOfExecution.add(i+1);
					resultText.setText(resultText.getText()+"\n"
							+" -> Bước "+String.valueOf(countStep)+": Need[" +String.valueOf(i+1)+"] = ( ");
					for ( int j=0 ; j<numberOfResource ; j++) {
						if(j!=numberOfResource-1)
							resultText.setText(resultText.getText()+String.valueOf(need.get(i).get(j))+" ; ");
						else 
							resultText.setText(resultText.getText()+String.valueOf(need.get(i).get(j))+" ) ");
					}
					resultText.setText(resultText.getText()+" <= Available = ( ");
					for ( int j=0 ; j<numberOfResource ; j++) {
						if(j!=numberOfResource-1)
							resultText.setText(resultText.getText()+String.valueOf(work.get(j))+" ; ");
						else 
							resultText.setText(resultText.getText()+String.valueOf(work.get(j))+" ) ");
					}
					resultText.setText(resultText.getText()+" -> P" +String.valueOf(i+1)+" thực thi. New Available = (");
					
					for ( int j=0 ; j<numberOfResource ; j++) {
						work.set(j, work.get(j) + allocation.get(i).get(j));
						flag = true;
					}
					
					for ( int j=0 ; j<numberOfResource ; j++) {
						if(j!=numberOfResource-1)
							resultText.setText(resultText.getText()+String.valueOf(work.get(j))+" ; ");
						else 
							resultText.setText(resultText.getText()+String.valueOf(work.get(j))+" ) ");
					}
					resultText.setText(resultText.getText()+"\n");
				}
			}
		}
		for ( int i=0 ; i<numberOfProcess ; i++) {
			if(finish.get(i) == false) return false;
		}
		return true;
	}
	
}
