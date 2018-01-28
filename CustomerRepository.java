import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;

public class YadavSiddhantDatabaseGUI extends JFrame implements ActionListener {

	public String readings = new String(); 
	//string reading
	public Container contp; 
	//object for container
	public JButton loading, saving;
	// object for buttons
	public JTextArea fullarea = new JTextArea();
	//object for text area
	public JTextField takecommand =new JTextField();
	//object for text field
	Employee objectforemployee = new Employee(); 
	//object for employee file 
	Database objectfordb = new Database();
	//object for database file
	
	
public void actionPerformed(ActionEvent event) {
	//method event
		
File inputFile = new File("C:/data/IDS401.dat"); 
// input file
File outputFile = new File("C:/data/IDS401.dat");
// output file		

if (event.getSource() instanceof JTextField) {
	
			readings = takecommand.getText(); 
			//adding input
			String[] funct = readings.split(" ");
            //Splitting input to match equals
			if (funct[0].equalsIgnoreCase("add")) { 

			objectfordb.add(funct[1], funct[2], funct[3], Integer.parseInt(funct[4]),Integer.parseInt(funct[5]));
			//Database object adding new employee
				
				
			fullarea.append("New employees are added \n");

			}

			else if (funct[0].equalsIgnoreCase("delete")) { 
				objectfordb.delete(funct[1], funct[2], funct[3], Integer.parseInt(funct[4]),Integer.parseInt(funct[5]));
						
				fullarea.append(objectfordb.deletion() + " employees are deleted \n");
			}

			else if (funct[0].equalsIgnoreCase("read")) {
				int kq = 0;
				
				objectfordb.read(funct[1], funct[2]);
				
				while (objectfordb.SMreading[kq] != null) {
					{
				fullarea.append("last Name = " + objectfordb.SMreading[kq].getLname() + " first name = " + objectfordb.SMreading[kq].getFname()
								+ " department = " + objectfordb.SMreading[kq].getdepartment() + " ID = " + objectfordb.SMreading[kq].getid()
								+ " jobcode = " + objectfordb.SMreading[kq].getjobcode() + "\n");
						kq++;
					}
				}
				if (kq == 0) {
					fullarea.append(" employee not found  \n");
				}
			}

			else if (funct[0].equalsIgnoreCase("modify")) { 
				objectfordb.modify(funct[1], funct[2], funct[3]);
				fullarea.append(objectfordb.modifier() + " the employees are modified \n");
			}

		}

		else if (event.getSource() instanceof JButton) {
			JButton select = (JButton) event.getSource();

			if (select == loading) { 

				try {

					FileInputStream inFileStream = new FileInputStream(inputFile); 
					
					ObjectInputStream streaminputobjects = new ObjectInputStream(inFileStream);

					//input file objects
					
					while (true) {
						Object searchobject = streaminputobjects.readObject(); 
                    // searching for objects 
						
						
						if (searchobject instanceof Employee) { 
							
objectforemployee.setLastName(((Employee) searchobject).getLname());
							
							objectforemployee.setFirstName(((Employee) searchobject).getFname());
							
							objectforemployee.setLastName(((Employee) searchobject).getLname());
							
							objectforemployee.setDepartment(((Employee) searchobject).getdepartment());
							
							objectforemployee.setId(((Employee) searchobject).getid());
							
							objectforemployee.setjobcode(((Employee) searchobject).getjobcode());
							
							objectfordb.add(objectforemployee.getLname(), objectforemployee.getFname(), objectforemployee.getdepartment(), objectforemployee.getid(), objectforemployee.getjobcode());
						
						} else
							break;
					}
					JOptionPane.showMessageDialog(null, "Database has been Loaded ");
					streaminputobjects.close(); 

				} catch (Exception excepts1) {
					System.out.println("");
				}

			}

			if (select == saving) { 

				try {
					int wa = 0;
					FileOutputStream outfilestream = new FileOutputStream(outputFile);
					ObjectOutputStream streamoutputobjects = new ObjectOutputStream(outfilestream); 
					
					for (wa = 0; wa < objectfordb.cnumber; wa++) {
						streamoutputobjects.writeObject(objectfordb.e[wa]); 
						
					}
					JOptionPane.showMessageDialog(null, "Entries have been saved in database ");
					
					fullarea.append("entries are saved");
					
					streamoutputobjects.close(); 
				
				} catch (Exception excepts2) {
				
					System.out.println("no output");
				}

			}

		}

	}

	public YadavSiddhantDatabaseGUI() {
		contp = getContentPane();
		
		contp.setBackground(Color.GRAY);
		
		setSize(1500, 350); 
		
		setTitle ("Assignment 3 user interface");
		
		setLocation(150, 150);
		
		contp.setLayout(new FlowLayout()); 

		JLabel fieldtext = new JLabel("Enter function"); 
		//entering user commands 
		
		takecommand.addActionListener(this);

		takecommand.setColumns(120);
		
		contp.add(fieldtext);
				
		contp.add(takecommand);
		
		loading = new JButton("Load"); 
		
		loading.addActionListener(this);
		
		contp.add(loading);
		
		saving = new JButton("Save"); 
		
		saving.addActionListener(this);
		
		contp.add(saving);
		
		fullarea.setColumns(45);
		
		fullarea.setRows(10);
		
		fullarea.setBorder(BorderFactory.createLineBorder(Color.blue));
		
		fullarea.setText("");
		contp.add(fullarea);

		
	}



	public static void main(String args[]) {

		YadavSiddhantDatabaseGUI dgui = new YadavSiddhantDatabaseGUI();
		dgui.setVisible(true);

	}

	class Database implements Serializable {

		public Employee[] e = new Employee[0];
		// employee array e
		int cnumber = 0;
		//initial customer count

		Employee SMreading[]; 

		int cmodify = 0; 

		public int modifier() {
			
			return cmodify;
		}

		int countdelete = 0; 
		//initial deleted customer count

		public int deletion() {
			return countdelete;
		}

		
		public boolean match(String field, String matchValue) {
			int r; 

			Boolean booln = false; 

			for (r = 0; r < cnumber; r++) {
				if (field.equals("lastName") && e[r].getLname().equals(matchValue))
					booln = true;
				else if (field.equals("firstName") && e[r].getFname().equals(matchValue))
					booln = true;
				else if (field.equals("department") && e[r].getdepartment().equals(matchValue))
					booln = true;

			}
			return booln;
		}

	
		public void delete(String lastName, String firstName, String department, int id, int jobcode) {
			int n = 0;
			int jk = 0;
			while (n<cnumber) {
				if (e[n].getLname().equals(lastName) && e[n].getFname().equals(firstName)
						&& e[n].getdepartment().equals(department) && e[n].getid() == id
						&& e[n].getjobcode() == (jobcode)) {

					e[n] = e[cnumber - 1]; 
					
					e[cnumber - 1] = null; 
					
					n--; 
					cnumber--;
					
					jk++;
				}
				n++;

			}
			JOptionPane.showMessageDialog(null, jk + " employee added deleted \n");
		

		}
		
		public void add(String lastName, String firstName, String department, int id, int jobcode) {

			Employee createnew = new Employee(); 
			
			createnew.setFirstName(firstName);
			
			createnew.setLastName(lastName);
			
			createnew.setDepartment(department);
			
			createnew.setId(id);
			
			createnew.setjobcode(jobcode);
			
			int last = 0; 

			Employee[] changenext = new Employee[e.length + 1];
			
			for (int a = 0; a < e.length; a++) {
				
				changenext[a] = e[a];
			}
			
			e = changenext;

			while (e[last] != null) {
				last++;
			}
			e[last] = createnew;
			cnumber++;

		}
		
		public void modify(String field, String matchValue, String newValue) {
			int r; 
			cmodify = 0;

			for (r = 0; r < cnumber; r++) {
				if (field.equals("lastName") && e[r].getLname().equals(matchValue)) {
					e[r].setLastName(newValue);
					cmodify++;
				} else if (field.equals("firstName") && e[r].getFname().equals(matchValue)) {
					e[r].setFirstName(newValue);
					cmodify++;
				} else if (field.equals("department") && e[r].getdepartment().equals(matchValue)) {
					e[r].setDepartment(newValue);
					cmodify++;
				}

			}

		}
		
		

		public void read(String field, String matchValue) {
			SMreading = new Employee[e.length + 1];
			int p = 0;

			for (int r = 0; r < e.length; r++) {

				if (e[r] != null) { 
					if (e[r].department.equals(matchValue) && field.equals("department")) {
						System.out.println("last Name = " + e[r].getLname() + " first Name= " + e[r].getFname() + " department = " + e[r].getdepartment() + " ID: " + e[r].getid() + " jobcode = " + e[r].getjobcode() + "\n");
					
						SMreading[p] = e[r];
						p++;

					} else if (e[r].jobcode == Integer.parseInt(matchValue) && field.equals("jobCode") ) {
						System.out.println(".Last Name: " + e[r].getLname() + " First Name: " + e[r].getFname() + " Department: " + e[r].getdepartment() + " ID: " + e[r].getid() + " JobCode:" + e[r].getjobcode() + "\n");
								
						SMreading[p] = e[r];
						p++;

					} else {

						System.out.println(" \n No result found ");
					}

				}
			}

		}
	}

	public class Employee implements Serializable { 
		private String lastName,firstName,department;
		private int id, jobcode;

		public void setLastName(String lstname) {
			lastName = lstname;
		}
		
		public String getLname() {
			return lastName;
		}


		public void setFirstName(String fstname) {
			firstName = fstname;
		}
		
		public String getFname() {
			return firstName;
		}

		public void setDepartment(String departs) {
			department = departs;
		}
		
		public String getdepartment() {
			return department;
		}

		public void setId(int Identify) {
			id = Identify;
		}
		
		public int getid() {
			return id;
		}


		public void setjobcode(int jobsd) {
			jobcode = jobsd;
		}

			public int getjobcode() {
			return jobcode;
		}

		
				
	}

}
