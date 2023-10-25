import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GPACalculatorMcGill {
    // converting letter to number grade
    public static double getGradePoints(String grade) {
        char[] letterGrade = {grade.charAt(0), grade.charAt(1)};
        String letter = new String(letterGrade);

        if (letter.equals("A:")) {
            return 4.0;
        }
        else if (letter.equals("A-")){
            return 3.7;
        }
        else if (letter.equals("B+")){
            return 3.3;
        }
        else if (letter.equals("B:")){
            return 3.0;
        }
        else if (letter.equals("B-")){
            return 2.7;
        }
        else if (letter.equals("C+")){
            return 2.3;
        }
        else if (letter.equals("C")){
            return 2.0;
        }
        else if (letter.equals("D")){
            return 1.0;
        }
        else {
            return 0;
        }
    }
    public static void main(String[] args) {
        // create frame, make it exit the program upon closing the frame
        JFrame frame = new JFrame("GPA Calculator - McGill");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        int frameWidth = 750;
        int frameHeight = 700;

        // label with instructions to fill out coursePanel
        JPanel labelPanel = new JPanel(null);
        JLabel courseLabel = new JLabel("Please fill out the grade and number of credits for each course:");
        labelPanel.add(courseLabel);
        courseLabel.setBounds(0,0,courseLabel.getPreferredSize().width, courseLabel.getPreferredSize().height);

        // coursePanel dynamically adds rows for each class to input course grade and credit info
        // TODO: (later) add another column to specify "course #1" for each course, using panelRow
        // this is where i would add an editable column so they can label which course is associated with the credits/grade

        final int[] panelRow = {0};

        JPanel coursePanel = new JPanel(new GridBagLayout());

        GridBagConstraints gradeConstraints = new GridBagConstraints();
        gradeConstraints.gridy = panelRow[0];
        gradeConstraints.gridx = 0;
        gradeConstraints.anchor = GridBagConstraints.NORTHEAST;

        // TODO: (later) figure out how to make the credits column skinnier
        GridBagConstraints creditConstraints = new GridBagConstraints();
        creditConstraints.gridy = panelRow[0];
        creditConstraints.gridx = 1;
        creditConstraints.weightx = 1;
        creditConstraints.fill = GridBagConstraints.HORIZONTAL;

        // combo box for grade options
        String[] gradeOptions = {"A: 85-100%", "A-: 80-84%", "B+: 75-79%", "B: 70-74%", "B-: 65-69%", "C+: 60-64%", "C: 55-59%", "D: 50-54%", "F: 0-49%"};
        JComboBox gradeCombos = new JComboBox(gradeOptions);
        coursePanel.add(gradeCombos, gradeConstraints);

        // credit field for course
        JTextField credit = new JTextField();
        coursePanel.add(credit, creditConstraints);

        // buttonPanel
        JPanel buttonPanel = new JPanel(new GridLayout(1,3));
        // button that dynamically adds rows to coursePanel
        JButton addCourse = new JButton("Add another course");
        buttonPanel.add(addCourse);
        addCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelRow[0]++;
                gradeConstraints.gridy = panelRow[0];
                creditConstraints.gridy = panelRow[0];

                JComboBox gradeCombos = new JComboBox(gradeOptions);
                coursePanel.add(gradeCombos, gradeConstraints);

                JTextField credit = new JTextField();
                coursePanel.add(credit, creditConstraints);

                coursePanel.setBounds(0,52,350,25*(panelRow[0] + 1));

                coursePanel.repaint();
                coursePanel.revalidate();
            }
        });
        // button that dynamically deletes rows from coursePanel
        JButton delCourse = new JButton("Delete a course");
        buttonPanel.add(delCourse);
        delCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component[] components = coursePanel.getComponents();

                if (panelRow[0] > 0) {
                    coursePanel.remove(components[(panelRow[0]*2)]);
                    coursePanel.remove(components[(panelRow[0]*2 + 1)]);

                    coursePanel.setBounds(0,52,350,25*(panelRow[0] + 1));

                    coursePanel.repaint();
                    coursePanel.revalidate();

                    panelRow[0]--;
                }
            }
        });
        // termGPA button to calculate semester GPA once all courses are added
        JButton termGPA = new JButton("Calculate term GPA");
        buttonPanel.add(termGPA);
        // cGPA button to calculate cumulative GPA
        JButton cGPA = new JButton("Calculate cumulative GPA");
        buttonPanel.add(cGPA);
        cGPA.setVisible(false);
        // panel for termGPA message and cGPA messages
        JPanel tGPAPanel = new JPanel(null);
        JLabel tGPALabel = new JLabel();
        tGPAPanel.add(tGPALabel);

        JLabel cGPAQ = new JLabel();
        tGPAPanel.add(cGPAQ);
        JLabel cGPACurrent = new JLabel();
        tGPAPanel.add(cGPACurrent);
        JLabel cGPACredits = new JLabel();
        tGPAPanel.add(cGPACredits);
        // labels for calculating cGPA
        cGPAQ.setText("To calculate cumulative GPA, enter current total credits and GPA (before this semester).");
        cGPAQ.setBounds(0,17,frameWidth-100, 16);
        cGPAQ.setVisible(false);

        cGPACredits.setText("Current total credits:");
        cGPACredits.setBounds(0,(17)*2,frameWidth-100, 16);
        cGPACredits.setVisible(false);

        cGPACurrent.setText("Current cGPA: ");
        cGPACurrent.setBounds(0,(17)*3,frameWidth-100,16);
        cGPACredits.setVisible(false);
        // label for cGPA calc answer
        JLabel cGPAMessage = new JLabel();
        tGPAPanel.add(cGPAMessage);
        cGPAMessage.setBounds(0,17*4,frameWidth-100, 16);
        cGPAMessage.setVisible(false);
        // jtextfields for current credit total and cGPA
        JTextField creditTotal = new JTextField();
        creditTotal.setBounds(150,17*2,100,16);
        tGPAPanel.add(creditTotal);
        creditTotal.setVisible(false);

        JTextField curcGPA = new JTextField();
        curcGPA.setBounds(150,17*3,100,16);
        tGPAPanel.add(curcGPA);
        curcGPA.setVisible(false);

        final double[] termCredits = {0};
        final double[] tGPATotal = {0};

        termGPA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component[] components = coursePanel.getComponents();

                termCredits[0] = 0;
                tGPATotal[0] = 0;
                double curGrade = 0;
                double curCredits;

                for (int i = 0; i < components.length; i++) {
                    if (i%2 == 0) {
                        // component is jcombo box (grade received)
                        JComboBox thisGrade = (JComboBox) components[i];
                        String grade = (String) thisGrade.getSelectedItem();
                        curGrade = getGradePoints(grade);
                    }
                    else {
                        // component is jtextfield (# of credits)
                        JTextField thisCredit = (JTextField) components[i];
                        String credits = thisCredit.getText();
                        curCredits = Double.parseDouble(credits);

                        termCredits[0] += curCredits;
                        tGPATotal[0] += curGrade * curCredits;
                    }
                }
                // calculate termGPA
                tGPATotal[0] = tGPATotal[0] / termCredits[0];
                // label with calculated termGPA
                int labelHeight = 16;
                tGPALabel.setText("Your term GPA is: " + tGPATotal[0]);
                tGPALabel.setBounds(0,0, frameWidth-100, labelHeight);

                tGPAPanel.setBounds(0,25*(panelRow[0] + 2) + 55,frameWidth-100,(labelHeight+1)*5);
                cGPA.setVisible(true);
                cGPAQ.setVisible(true);
                cGPACredits.setVisible(true);
                cGPACurrent.setVisible(true);
                creditTotal.setVisible(true);
                curcGPA.setVisible(true);

                tGPAPanel.repaint();
                tGPAPanel.revalidate();
                buttonPanel.repaint();
                buttonPanel.revalidate();
            }
        });

        cGPA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                termGPA.doClick();

                double totalCredits = Double.parseDouble(creditTotal.getText());
                double totalcGPA = Double.parseDouble(curcGPA.getText());

                double cGPA = (tGPATotal[0]*termCredits[0] + totalCredits*totalcGPA)/(termCredits[0]+totalCredits);

                cGPAMessage.setText("Your cumulative GPA is: " + cGPA);
                cGPAMessage.setVisible(true);

                tGPAPanel.repaint();
                tGPAPanel.revalidate();
            }
        });

        // add things to frame
        labelPanel.setBounds(0,0,frameWidth,25);
        frame.add(labelPanel);

        buttonPanel.setBounds(0,26,frameWidth,25);
        frame.add(buttonPanel);

        coursePanel.setBounds(0,52,350,25*(panelRow[0] + 1));
        frame.add(coursePanel);

        tGPAPanel.setBounds(0,25*(panelRow[0] + 1) + 52,frameWidth,25);
        frame.add(tGPAPanel);

        // format the frame, make it visible
        frame.pack();
        frame.setSize(frameWidth,frameHeight);
        frame.setVisible(true);
    }
}
