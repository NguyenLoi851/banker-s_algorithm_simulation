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
	JTextArea resultText2=new JTextArea(12,20);
	
	public static int numberOfResource, numberOfProcess, numberOfProcessRequest;
	public static boolean checkButtonRequest = false, checkButtonRandom = false;
	public static ArrayList<Integer> available, work, orderOfExecution,initial;
	public static ArrayList<ArrayList<Integer>> allocation, max, need, request;
	public static ArrayList<Integer> temp;
	public static ArrayList<Boolean> finish;
	public static Set<Integer> nameOfProcessRequest;
	
	
	MyFrame(){
		frame.setTitle("Banker's Algorithm Simulation");
	    frame.setSize(1200,1200);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(null);
	    frame.setBackground(Color.white);
	    
	    resultText.setText("");
	    JScrollPane textContainer=new JScrollPane(resultText);
	    textContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    textContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    textContainer.setSize(100,100);
	    textContainer.setVisible(true);
	    JPanel panel=new JPanel();
	    panel.setLayout(new BorderLayout());
	    panel.setBounds(35,425,610,500);
	    panel.add(textContainer,BorderLayout.NORTH);
	    
	    resultText2.setText("");
	    JScrollPane textContainer2=new JScrollPane(resultText2);
	    textContainer2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    textContainer2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    textContainer2.setSize(100,100);
	    textContainer2.setVisible(true);
	    JPanel panel2=new JPanel();
	    panel2.setLayout(new BorderLayout());
	    panel2.setBounds(645,425,610,500);
	    panel2.add(textContainer2,BorderLayout.NORTH);
	    
	    JTextField text=new JTextField();
        text.setText("Banker's Algorithm Simulation");
        text.setBounds(500,10,300,75);
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setFont(new Font("Comic Sans", Font.BOLD + Font.ITALIC, 20));
        text.setBackground(Color.cyan);
        text.setEditable(false);
        text.setAutoscrolls(false);
	    
	    buttonRandom =new JButton("Start Random");
	    buttonRandom.setBounds(682,130,135,50);
	    buttonRandom.setBackground(Color.green);
	    buttonRandom.addActionListener(this);
	    

	    buttonReset = new JButton("Reset");
	    buttonReset.setBounds(1120,130,135,50);
	    buttonReset.setBackground(Color.red);
	    buttonReset.addActionListener(this);
	    
	    buttonRun = new JButton("Run");
	    buttonRun.setBounds(828,130,135,50);
	    buttonRun.setBackground(Color.yellow);
	    buttonRun.addActionListener(this);
	    
	    buttonRequest = new JButton("Request Random");
	    buttonRequest.setBounds(974,130,135,50);
	    buttonRequest.setBackground(Color.pink);
	    buttonRequest.addActionListener(this);
	    
	    ImageIcon icon = new ImageIcon("bank.jpg");
	    
	    frame.getContentPane().add(panel);
	    frame.getContentPane().add(panel2);
	    frame.add(text);
        frame.add(buttonRandom);
        frame.add(buttonRequest);
        frame.add(buttonReset);
        frame.add(buttonRun);
        frame.setIconImage(icon.getImage());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(false);
        frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Random rand = new Random();
		
		if(e.getSource() == buttonRandom) { 			// click Start button
			//Initial table random 
			if(checkButtonRandom == true) {
				AllocationTable.panel.setVisible(false);
				frame.remove(AllocationTable.panel);
				
				AvailableTable.panel.setVisible(false);
				frame.remove(AvailableTable.panel);
				
				InitialTable.panel.setVisible(false);
				frame.remove(InitialTable.panel);
				
				MaxTable.panel.setVisible(false);
				frame.remove(MaxTable.panel);
				
				NeedTable.panel.setVisible(false);
				frame.remove(NeedTable.panel);
				
				resultText.setText("");
				resultText2.setText("");
			}
			checkButtonRandom = true;
			numberOfResource = rand.nextInt(6)+3 ;	// 3-8
			numberOfProcess = rand.nextInt(6)+5;	// 5-10
			
			initial = new ArrayList<Integer>();
			for ( int i=0 ; i<numberOfResource ; i++ ) {
				initial.add(rand.nextInt(21)+80);	// 80-100
			}
			
			// Initial Table appear 
			InitialTable initialTable = new InitialTable();
			frame.add(initialTable.panel);
			
			//Max table random
			
			max = new ArrayList<ArrayList<Integer>>();
			for( int j=0 ; j<numberOfProcess ; j++) {
				temp = new ArrayList<Integer>();
				for ( int i=0 ; i<numberOfResource ; i++) {
					temp.add(rand.nextInt(55)+rand.nextInt(25));
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
					temp.add(rand.nextInt((initial.get(i))/numberOfProcess));
					
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
			frame.setVisible(true);
		}
	
		if(e.getSource() == buttonRequest) { 			//click Request button
			//request table random
			if(checkButtonRequest == true) {
				RequestTable.panel.setVisible(false);
				frame.remove(RequestTable.panel);
				resultText2.setText("");
			}
			
			checkButtonRequest = true;
			request = new ArrayList<ArrayList<Integer>>();
			nameOfProcessRequest = new LinkedHashSet<Integer>();
			numberOfProcessRequest = rand.nextInt(numberOfProcess/2)+1;
			while(nameOfProcessRequest.size() < numberOfProcessRequest) {
				nameOfProcessRequest.add(rand.nextInt(numberOfProcess)+1);
			}
			Integer[] tem = nameOfProcessRequest.toArray(new Integer[nameOfProcessRequest.size()]);
			for (int j=0 ; j<numberOfProcessRequest ; j++) {
				temp = new ArrayList<Integer>();
				for( int i=0 ; i<numberOfResource ; i++) {
					float a = need.get(tem[j]-1).get(i);
					temp.add(rand.nextInt((int)(a/100*85+a/100*30)+1));
				}
				request.add(temp);
			}
			//request table appear
			RequestTable requestTable = new RequestTable();
			frame.add(requestTable.panel);
			frame.setVisible(true);
		}
		
		if(e.getSource() == buttonReset) {				//click Reset button
			
			AllocationTable.panel.setVisible(false);
			frame.remove(AllocationTable.panel);
			
			AvailableTable.panel.setVisible(false);
			frame.remove(AvailableTable.panel);
			
			InitialTable.panel.setVisible(false);
			frame.remove(InitialTable.panel);
			
			MaxTable.panel.setVisible(false);
			frame.remove(MaxTable.panel);
			
			NeedTable.panel.setVisible(false);
			frame.remove(NeedTable.panel);
			
			if(checkButtonRequest == true) {
				RequestTable.panel.setVisible(false);
				frame.remove(RequestTable.panel);
				checkButtonRequest = false;
			}
			resultText.setText("");
			resultText2.setText("");
		}
		
		
		
		
		if(e.getSource() == buttonRun) {				//click Run button
			
			if(checkButtonRequest == false) {
				resultText.setText(resultText.getText()+"\n\n\n                       "
						+ "---------- Kết quả chạy thuật toán kiểm tra tính an toàn----------\n");
				if(safe(resultText) == true) {
					resultText.setText(resultText.getText()+"\n"+" -> Trạng thái hiện tại của hệ thống: An toàn \n");
					resultText.setText(resultText.getText()+"   -> Trình tự cấp phát tài nguyên: ");
					for( int i=0 ; i<numberOfProcess ; i++) {
						if(i!=numberOfProcess-1)
							resultText.setText(resultText.getText()+" P"+String.valueOf(orderOfExecution.get(i))+" -> ");
						else
							resultText.setText(resultText.getText()+" P"+String.valueOf(orderOfExecution.get(i))+".\n");
					}
				}else {
					resultText.setText(resultText.getText()+"\n"+" -> Trạng thái hiện tại của hệ thống: Không an toàn \n");
				}
			}else {
				resultText2.setText(resultText2.getText()+"\n\n\n                       "
						+ "---------- Kết quả chạy thuật toán yêu cầu tài nguyên----------\n");
				//đưa từng tiến trình trong bảng request vào
				Integer[] temp = nameOfProcessRequest.toArray(new Integer[nameOfProcessRequest.size()]);
				for ( int i=0 ; i<numberOfProcessRequest ; i++) {
					//xét tiến trình với need[i]
					
					resultText2.setText(resultText2.getText()+"\n------------------------------------------------------------"
							+"-----------------------------------------------------------------------------------------------\n"
							+"\n"+" -> Xét P"+String.valueOf(temp[i])+":");
					
					if(aSmallAndEqualThanB(request.get(i), need.get(temp[i]-1)) == false) {
						
						resultText2.setText(resultText2.getText()+"\n"+" -> P"+String.valueOf(temp[i])+
								": Yêu cầu vượt quá khai báo Tài nguyên. Do request["
								+String.valueOf(temp[i])+"] = (");
						for ( int j=0 ; j<numberOfResource ; j++) {
							if(j!=numberOfResource-1)
								resultText2.setText(resultText2.getText()+String.valueOf(request.get(i).get(j)+" ; "));
							else
								resultText2.setText(resultText2.getText()+String.valueOf(request.get(i).get(j)+" ) "));
						}
						resultText2.setText(resultText2.getText()+"> need["+String.valueOf(temp[i]) +"]= (");
						for ( int j=0 ; j<numberOfResource ; j++) {
							if(j!=numberOfResource-1)
								resultText2.setText(resultText2.getText()+String.valueOf(need.get(temp[i]-1).get(j)+" ; "));
							else
								resultText2.setText(resultText2.getText()+String.valueOf(need.get(temp[i]-1).get(j)+" ) \n"));
						}
						
					}else {
						//xét tiến trình với available
						if(aSmallAndEqualThanB(request.get(i), available) == false) {
							
							resultText2.setText(resultText2.getText()+"\n"+" -> P"+String.valueOf(temp[i])+
									": Không đủ tài nguyên, tiến trình phải đợi. Do request(P"
									+String.valueOf(temp[i])+") = (");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText2.setText(resultText2.getText()+String.valueOf(request.get(i).get(j)+" ; "));
								else
									resultText2.setText(resultText2.getText()+String.valueOf(request.get(i).get(j)+" ) "));
							}
							resultText2.setText(resultText2.getText()+"> available = (");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText2.setText(resultText2.getText()+String.valueOf(available.get(j)+" ; "));
								else
									resultText2.setText(resultText2.getText()+String.valueOf(available.get(j)+" ) \n"));
							}
							
						}else {
							for( int j=0 ; j<numberOfResource ; j++) {
								available.set(j, available.get(j) - request.get(i).get(j));
								allocation.get(temp[i]-1).set(j, allocation.get(temp[i]-1).get(j) + request.get(i).get(j));
								need.get(temp[i]-1).set(j, need.get(temp[i]-1).get(j) - request.get(i).get(j));
							}
							//in thông tin mới cho available
							resultText2.setText(resultText2.getText()+"\n -> Cập nhật thông tin, nếu cấp phát tài nguyên thì: "+"\n"
										+" -> Available = (");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText2.setText(resultText2.getText()+String.valueOf(available.get(j)+" ; "));
								else
									resultText2.setText(resultText2.getText()+String.valueOf(available.get(j)+" ) \n"));
							}
							//int thông tin mới cho allocation
							resultText2.setText(resultText2.getText()+" -> Allocation["+String.valueOf(temp[i])
									+"] = (");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText2.setText(resultText2.getText()+String.valueOf(allocation.get(temp[i]-1).get(j)+" ; "));
								else
									resultText2.setText(resultText2.getText()+String.valueOf(allocation.get(temp[i]-1).get(j)+" ) \n"));
							}
							//in thông tin mới cho need
							resultText2.setText(resultText2.getText()+" -> Need["+String.valueOf(temp[i])
									+"] = (");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText2.setText(resultText2.getText()+String.valueOf(need.get(temp[i]-1).get(j)+" ; "));
								else
									resultText2.setText(resultText2.getText()+String.valueOf(need.get(temp[i]-1).get(j)+" ) \n"));
							}
							
							if(safe(resultText2) == true) {
								resultText2.setText(resultText2.getText()+"\n"+" -> P"+
										String.valueOf(temp[i])+": Được phân phối tài nguyên theo yêu cầu"+"\n");
								resultText2.setText(resultText2.getText()+"   -> Trình tự cấp phát tài nguyên: ");
								for( int k=0 ; k<numberOfProcess ; k++) {
									if(k!=numberOfProcess-1)
										resultText2.setText(resultText2.getText()+" P"+String.valueOf(orderOfExecution.get(k))+" -> ");
									else
										resultText2.setText(resultText2.getText()+" P"+String.valueOf(orderOfExecution.get(k))+".\n");
								}
							}else {
								resultText2.setText(resultText2.getText()+"\n"+" -> P"+
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
	public boolean safe(JTextArea resultText) {
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
					resultText.setText(resultText.getText()+"\n    -> P" +String.valueOf(i+1)+" được cấp phát thêm tài nguyên."
							+ "\n        -> Chương trình thu hồi tài nguyên đã cấp phát cho tiến trình P"
							+ String.valueOf(i+1)
							+ "\n            -> New Available = (");
					
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
				else {
					if(countStep<numberOfProcess) {
						if(finish.get(i) == true) {
							resultText.setText(resultText.getText() + "\n-> P"
									+String.valueOf(i+1)+" đã được cấp phát tài nguyên -> Bỏ qua\n");
						}
						else {
							resultText.setText(resultText.getText() + "\n-> P"
									+String.valueOf(i+1)+ " có Need["+String.valueOf(i+1)+"] = (");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText.setText(resultText.getText()+String.valueOf(need.get(i).get(j))+" ; ");
								else 
									resultText.setText(resultText.getText()+String.valueOf(need.get(i).get(j))+" ) ");
							}
							resultText.setText(resultText.getText()+" > Available = ( ");
							for ( int j=0 ; j<numberOfResource ; j++) {
								if(j!=numberOfResource-1)
									resultText.setText(resultText.getText()+String.valueOf(work.get(j))+" ; ");
								else 
									resultText.setText(resultText.getText()+String.valueOf(work.get(j))+" ) -> Bỏ qua \n");
							}
						}
					}
				}
			}
		}
		
		for ( int i=0 ; i<numberOfProcess ; i++) {
			if(finish.get(i) == false) return false;
		}
		return true;
	}
}

