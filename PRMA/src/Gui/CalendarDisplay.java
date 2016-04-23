package Gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Employee;

/*
 * This code is not our own work we have done for CS 461.
 * The following program is taken from: http://www.dreamincode.net/forums/topic/25042-creating-a-calendar-viewer-application/
 */

public class CalendarDisplay extends JFrame {

	static JLabel lblMonth, lblYear, results;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    static JComboBox cmbYear;
    static JFrame frmMain;
    static Container pane;
    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar, labelScroll; //The scrollpane
    static JPanel pnlCalendar;
    static int realYear, realMonth, realDay, currentYear, currentMonth;	
	private JPanel contentPane;
	private ArrayList<Employee> employees;

	public CalendarDisplay(ArrayList<Employee> employees) {
		this.employees = employees;
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
		frmMain = new JFrame ("Calendar View"); //Create frame
		frmMain.setSize(800, 375); //Set size to 400x400 pixels
		pane = frmMain.getContentPane(); //Get content pane
		pane.setLayout(null); //Apply null layout
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked

		//Create controls
		lblMonth = new JLabel ("January");
		lblYear = new JLabel ("Change year:");
		results = new JLabel ("Results: ");
		results.setVerticalAlignment(SwingConstants.TOP);
		cmbYear = new JComboBox();
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		labelScroll = new JScrollPane(results);
		pnlCalendar = new JPanel(null);
		
		tblCalendar.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        int row = tblCalendar.rowAtPoint(e.getPoint());
        int column = tblCalendar.columnAtPoint(e.getPoint());
        if(tblCalendar.getValueAt(row, column)!=null){
        	//String[] months =  {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        	String resultString = "<html>";
        	for(int i = 0; i<employees.size();i++){
        		for(int j = 0; j<employees.get(i).mySchedule.size();j++){
        			System.out.println("here");
        			//String dateOfTask = employees.get(i).mySchedule.get(j).getTaskStart().toString();
        			System.out.println("employees.get(i).mySchedule.get(j).getTaskStart().getYear(): "+(employees.get(i).mySchedule.get(j).getTaskStart().getYear()+1900)+ " currentYear: "+currentYear );
        			if(employees.get(i).mySchedule.get(j).getTaskStart().getYear()+1900==currentYear&&employees.get(i).mySchedule.get(j).getTaskStart().getMonth()==currentMonth&&employees.get(i).mySchedule.get(j).getTaskStart().getDate()==Integer.parseInt(tblCalendar.getValueAt(row, column).toString())){
        				System.out.println("here2");
        				//System.out.println("emplooye "+this.id+" schedule "+i+"taskName: "+mySchedule.get(i).getTaskName()+": taskStart: " +mySchedule.get(i).getTaskStart()+" taskEnd: "+mySchedule.get(i).getTaskEndDate()+" taskDuration: "+mySchedule.get(i).getTaskDuration()+" taskBelongsTo: "+mySchedule.get(i).getBelongsTo().getId());
        				resultString += "<br> employee "+employees.get(i).getId()+" taskName: "+employees.get(i).mySchedule.get(j).getTaskName()+" taskEnd: "+employees.get(i).mySchedule.get(j).getTaskEndDate()+" taskDuration: "+employees.get(i).mySchedule.get(j).getTaskDuration()+" taskBelongsTo: "+employees.get(i).mySchedule.get(j).getBelongsTo().getId();
        			}
        		}
        	}
        	//results.setText(tblCalendar.getValueAt(row, column)+"");
        	resultString += "</html>";
        	results.setText(resultString);
        }
        //results.setText("row: "+row+", column: "+column);        
        System.out.println("row: "+row+", column: "+column);
    }
	});

		//Set border
		pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		//Register action listeners
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());
		
		//Add controls to pane
		pane.add(pnlCalendar);
		pnlCalendar.add(lblMonth);
		pnlCalendar.add(labelScroll);
		pnlCalendar.add(lblYear);
		pnlCalendar.add(cmbYear);
		pnlCalendar.add(btnPrev);
		pnlCalendar.add(btnNext);
		pnlCalendar.add(stblCalendar);
		
		//Set bounds
		pnlCalendar.setBounds(0, 0, 795, 340);
		lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25);
		lblYear.setBounds(72, 295, 87, 38);
		cmbYear.setBounds(230, 305, 80, 20);
		btnPrev.setBounds(10, 25, 50, 25);
		btnNext.setBounds(260, 25, 50, 25);
		stblCalendar.setBounds(10, 50, 300, 250);
		labelScroll.setBounds(327, 50, 450, 283);
		
		//Make frame visible
		frmMain.setResizable(false);
		frmMain.setVisible(true);
		
		//Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;
		
		//Add headers
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			mtblCalendar.addColumn(headers[i]);
		}
		
		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

		//No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tblCalendar.setRowHeight(38);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);
		
		//Populate table
		for (int i=realYear-100; i<=realYear+100; i++){
			cmbYear.addItem(String.valueOf(i));
		}
		
		//Refresh calendar
		refreshCalendar (realMonth, realYear); //Refresh calendar
	}

	public static void refreshCalendar(int month, int year){
		//Variables
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
		if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
		lblMonth.setText(months[month]); //Refresh the month label (at the top)
		lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
		cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(255, 220, 220));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){
				currentMonth = 0;
				currentYear += 1;
			}
			else{
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (cmbYear.getSelectedItem() != null){
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
}
