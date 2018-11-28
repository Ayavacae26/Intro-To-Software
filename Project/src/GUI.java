import javax.swing.JFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.*

// By: Olson Thao
// pulls up the gui and  able to select files by no additional functionality is added to it. still need to
// figure out other methods.

public class GUI extends JFrame{

    JButton transcript = new JButton("Upload Transcript");
    JButton confirm = new JButton("Calculate");
    JLabel userOutput = new JLabel("You haven't uploaded a Transcript ");
    String[] majors = 
		  {"American Indian Studies BA","Art History BA","Biopsychology BS",
				  "Accounting BA","Finance BA","Mangement BA",
				  "Management Information Systems BA","Computational Economics BA",
				  "Communications Studies BA","Computational Philosophy BA",
				  "Computer Science BA","Computer Science BS","Applied Economics BA",
				  "Economics BA","Mathematical Economics BS","Film:production track BA",
				  "Film:Theory and culture track BA", "Exercise Science BA",
				  "Health Education BA or  BS", "Physical Education BA or  BS", 
				  "Mathematics BA","New Media:Game Design BA","New Media:Web Design BA", 
				  "Nursing BS", "Physics BA","Physics BS", "Physics:Space Physics BS",
				  "Political Science:Public Policy/Change BA",
				  "Psychology: Psychology and Law  BA", "Sociology BA",
				  "Social Work  BS"};
    JComboBox majorlist = new JComboBox(majors);
    JTextArea text = new JTextArea(660,700);
    
    // default major
    String selectedMajor = "American Indian Studies BA";

    File file1 = null;
    //Checks for which file has been inputted
    Boolean buttonT = false;
    Boolean buttonD = false;
    public GUI()
    {
        this.setTitle("Read Transcript");
        this.setBounds(300,100,450,600);
        //contentPane is on default
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //put in the buttons
        this.transcript.setBounds(10, 10, 150, 40);
        this.getContentPane().add(transcript);
        this.transcript.addActionListener(new TranscriptButtonListener());

        //this.major.setBounds(180, 10, 170, 40);
        this.getContentPane().add(majorlist);
        this.majorlist.setBounds(200, 10, 170, 40);
        this.majorlist.addActionListener(new MajorlistListener());

        this.confirm.setBounds(110, 50, 130, 40);
        this.getContentPane().add(confirm);
//        this.confirm.addActionListener(new ConfirmButtonListener());

        this.userOutput.setBounds(50, 100, 100, 10);
        this.getContentPane().add(userOutput);

//        this.text.setBounds(200, 10, 170, 40);
//		this.getContentPane().add(text);
        this.text.setBounds(10,10,100,100);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(text);
        this.setLocationRelativeTo(null);
        this.setSize(600,600);
        this.setVisible(true);


        //this.hold.setLayout(new FlowLayout());

    }

    private class TranscriptButtonListener implements ActionListener
    {
        //Overrides the method from ActionListener
        public void actionPerformed(ActionEvent a)
        {
            JFileChooser transcriptFile = new JFileChooser();
            transcriptFile.setFileFilter( new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
            int chooserSuccess = transcriptFile.showOpenDialog(null);
            if( chooserSuccess == JFileChooser.APPROVE_OPTION) {
                file1 = transcriptFile.getSelectedFile();
                buttonT = true;

                //will need a global variable in case checklist is added before transcript -- vice versa
                if(buttonD.equals(false))
                {
                    userOutput.setText("You have uploaded a Transcript " +
                            "but not a Degree text file");
                }
                else
                {
                    userOutput.setText("You have uploaded a Transcript " +
                            "and a Degree text file");
                }
            }
            else {
                userOutput.setText("Uploading Transcript file has been cancelled.");
            }
        }
    }


    private class MajorlistListener implements ActionListener
    {
        //Overrides the method from ActionListener
        public void actionPerformed(ActionEvent b)
        {
            //should get the selected content from the combobox
            JComboBox majorlist = (JComboBox)b.getSource();
            String major = (String)majorlist.getSelectedItem();

        }
    }

}
