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
    JButton confirm = new JButton("Confirm");
    JLabel userOutput = new JLabel("You haven't uploaded a Transcript ");
    String[] majors = {"American Indian Studies BA","Art History BA","Art Education BA","Graphic Design BA",
            "Studio Art BA","Biology BA","Biology BS"," Biopsychology BS","Life Sciences BA","Accounting BA",
            "Finance BA","International Business BA","Mangement BA","Marketing BA","Management Information Systems BA"
            ,"Business Administration:Music Business BA","Cross-Cultural Studies BA","Chemistry(ACS certified) BS",
            "Chemistry BA","Chemistry(NON-ACS certified) BS","Communications Studies BA","Computational Economics BA",
            "Computational Philosophy BA","Computer Science BA","Computer Science BS","Applied Economics BA",
            "Combined major in Business/Economics BA", "Economics BA","Mathematical Economics BS","Elementary Education BS"
            ,"Elementary Education with communication Arts Endorsenments BS","Elementary Education with General Science Endorsenments BS"
            ,"Elementary Education with Mathematics Endorsenments BS", "Elementary Education with Social Studies Endorsenments BS","Communication Arts/Literature BA", "English:Creative Writing BA", "English literature,language and theory BA",
            "Environmental Studies BA", "Environmental Studies: HECUA TRACK BA", "Secondary Education Licensure Courses","K-12 English as a Second Language BA"
            ,"Film:production track BA","Film:Theory and culture track BA","FRENCH BA","German BA","History BA", "Exercise Science BA",
            "Exercise Science:Pre-Health Science BS", "Health Education BA or  BS", "Physical Education BA or  BS", "Interdisciplinary Studies  BA",
            "International Relations BA", "International Relations:Intl Business BA", "Mathematics BA", "Mathematics BS", "Mathematics: Secondary Teaching licensure major BS",
            "Medieval Studies BA", "Music BA", "Music:Music Business BA", "Music Education BM", "Music Performance BM",
            "Music Therapy BS", "New Media BA", "New Media:Game Design BA", "New Media:Web Design BA", "Nursing BS", "Philosophy BA",
            "Physics: Biophysics BS", "Physics BA","Physics BS", "Physics:Space Physics BS","Political Science and Economics BA",
            "Political Science BA", "Political Science:Pre-Law BA","Political Science:Public Policy/Change BA","Psychology: Clinical Psychology  BA",
            "Psychology  BA","Psychology: Psychology and Law  BA", "Psychology: Social Psychology BA", "Religion BA", "Theology and Public Leadership BA",
            "Sociology BA", "Spanish BA", "Special Education: Academic Behavioral Strategist BA", "Social Work  BS", "Theater  BA",
            "Theater:Design/Technical  BA","Theater:Directing/Dramaturgy/Playwriting BA", "Theater: Performance   BA","Urban Studies  BA","Gender, Sexuality and Women’s Studies  BA"};
    JComboBox majorlist = new JComboBox(majors);
    JTextArea text = new JTextArea(660,700);

    File file1 = null;
    File file2 = null;
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
        this.confirm.addActionListener(new ConfirmButtonListener());

        this.userOutput.setBounds(50, 100, 100, 10);
        this.getContentPane().add(userOutput);

//        this.text.setBounds(200, 10, 170, 40);
//		this.getContentPane().add(text);
        this.text = new JTextArea(50,50);
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



    private class AreaTextListListener implements ActionListener
    {
        //Overrides the method from ActionListener
        public void actionPerformed(ActionEvent b)
        {
            text = new JTextArea(50,50);
            setLayout(new FlowLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            add(text);
            setLocationRelativeTo(null);
            setSize(600,600);
            setVisible(true);
        }
    }
    private class ConfirmButtonListener implements ActionListener
    {
        //Overrides
        public void actionPerformed(ActionEvent c)
        {
            if(file1.equals(null) || file2.equals(null))
            {
                if(file1.equals(null) && file2.equals(null))
                {
                    userOutput.setText("You haven't uploaded a Transcript"
                            + " or Degree text file yet.");
                }
                else if(file1.equals(null))
                {
                    userOutput.setText("You haven't uploaded a Degree text"
                            + " file yet.");
                }
                else
                {
                    userOutput.setText("You haven't uploaded a Transcript"
                            + " text file yet.");
                }
            }
            else
            {
//        		TranscriptParser.readFile(file1, "transcript.txt");
//            	TranscriptParser.readFile(file2, "degree.txt");
                //read generated files and compare them
                TranscriptParser.compareTranscriptAndDegree();
                userOutput.setText("Your output is located in the console.");
            }
        }
    }
}
