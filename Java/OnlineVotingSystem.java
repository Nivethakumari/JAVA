import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.metal.MetalRadioButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class OnlineVotingSystem extends JFrame {
    private JLabel headingLabel, nameLabel, dobLabel, ageLabel, genderLabel, phoneLabel, cityLabel, stateLabel, candidateLabel, messageLabel;
    private JTextField nameField, ageField, phoneField;
    private JComboBox<String> dobDayComboBox, dobMonthComboBox, dobYearComboBox, cityComboBox, stateComboBox;
    private JRadioButton maleRadioButton, femaleRadioButton, notSpecifiedRadioButton;
    private ButtonGroup genderGroup;
    private JComboBox<String> candidateComboBox;
    private JButton voteButton;

    private Map<String, Integer> candidateVotes;
    private Set<String> votedVoters;
    private Map<String, String> cityToStateMap; // Map each city to its corresponding state

    private String[] states = {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    private String[] cities = {"Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad", "Calicut", "Chennai", "Kochi", "Kolkata", "Surat", "Pune", "Jaipur", "Lucknow", "Kanpur", "Nagpur", "Visakhapatnam", "Indore", "Thane", "Bhopal", "Patna", "Vadodara", "Ghaziabad", "Ludhiana", "Coimbatore", "Agra", "Madurai", "Nashik", "Faridabad", "Meerut", "Rajkot", "Varanasi", "Srinagar"};

    public OnlineVotingSystem() {
        setTitle("Online Voting System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 550);
        setLayout(new GridLayout(17, 2, 5, 5));

        // Set background color to light blue
        getContentPane().setBackground(new Color(173, 216, 230)); // Light blue color

        headingLabel = new JLabel("<html><div style='text-align: center;'><html><span style='text-decoration: underline; font-weight: bold; text-transform: uppercase;'>VOTE FOR YOUR CANDIDATE</span></html></div>");
        nameLabel = new JLabel("Name:");
        dobLabel = new JLabel("Date of Birth:");
        ageLabel = new JLabel("Age:");
        genderLabel = new JLabel("Gender:");
        phoneLabel = new JLabel("Phone Number:");
        cityLabel = new JLabel("City:");
        stateLabel = new JLabel("State:");
        candidateLabel = new JLabel("Select Candidate:");
        messageLabel = new JLabel("");

        nameField = new JTextField();
        ageField = new JTextField();
        ageField.setEditable(false); // Disable manual editing of the age field
        phoneField = new JTextField();

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        String[] years = new String[100];
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(1920 + i);
        }

        dobDayComboBox = new JComboBox<>(days);
        dobMonthComboBox = new JComboBox<>(months);
        dobYearComboBox = new JComboBox<>(years);

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        notSpecifiedRadioButton = new JRadioButton("Not Specified");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderGroup.add(notSpecifiedRadioButton);

        // Initially select maleRadioButton by default
        maleRadioButton.setSelected(true);

        // Set custom UI to make radio buttons' background white
        maleRadioButton.setUI(new WhiteRadioButtonUI());
        femaleRadioButton.setUI(new WhiteRadioButtonUI());
        notSpecifiedRadioButton.setUI(new WhiteRadioButtonUI());

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Center the radio buttons with space between them
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        genderPanel.add(notSpecifiedRadioButton);

        // Set background color of gender panel to match the background color of the frame
        genderPanel.setBackground(new Color(173, 216, 230)); // Light blue color

        nameLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding to nameLabel
        dobLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding to dobLabel
        ageLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding to ageLabel
        genderLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding to genderLabel
        phoneLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding to phoneLabel
        cityLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding to cityLabel
        stateLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding to stateLabel

        String[] candidates = {"Candidate 1 (Ashok)", "Candidate 2 (Keerthi)", "Candidate 3 (Rajesh)", "Candidate 4 (Ayesha)", "Candidate 5 (David)"};
        candidateComboBox = new JComboBox<>(candidates);
        voteButton = new JButton("Vote");

        // Set background color of candidate combo box to white
        candidateComboBox.setBackground(Color.WHITE);

        // Sort states and cities alphabetically
        Arrays.sort(states);
        Arrays.sort(cities);

        // Create the city to state mapping
        cityToStateMap = new HashMap<>();
        cityToStateMap.put("Mumbai", "Maharashtra");
        cityToStateMap.put("Delhi", "Delhi");
        cityToStateMap.put("Bangalore", "Karnataka");
        cityToStateMap.put("Hyderabad", "Telangana");
        cityToStateMap.put("Ahmedabad", "Gujarat");
        cityToStateMap.put("Chennai", "Tamil Nadu");
        cityToStateMap.put("Kolkata", "West Bengal");
        cityToStateMap.put("Surat", "Gujarat");
        cityToStateMap.put("Pune", "Maharashtra");
        cityToStateMap.put("Jaipur", "Rajasthan");
        cityToStateMap.put("Lucknow", "Uttar Pradesh");
        cityToStateMap.put("Kanpur", "Uttar Pradesh");
        cityToStateMap.put("Nagpur", "Maharashtra");
        cityToStateMap.put("Visakhapatnam", "Andhra Pradesh");
        cityToStateMap.put("Indore", "Madhya Pradesh");
        cityToStateMap.put("Thane", "Maharashtra");
        cityToStateMap.put("Bhopal", "Madhya Pradesh");
        cityToStateMap.put("Patna", "Bihar");
        cityToStateMap.put("Vadodara", "Gujarat");
        cityToStateMap.put("Ghaziabad", "Uttar Pradesh");
        cityToStateMap.put("Ludhiana", "Punjab");
        cityToStateMap.put("Coimbatore", "Tamil Nadu");
        cityToStateMap.put("Agra", "Uttar Pradesh");
        cityToStateMap.put("Madurai", "Tamil Nadu");
        cityToStateMap.put("Nashik", "Maharashtra");
        cityToStateMap.put("Faridabad", "Haryana");
        cityToStateMap.put("Meerut", "Uttar Pradesh");
        cityToStateMap.put("Rajkot", "Gujarat");
        cityToStateMap.put("Varanasi", "Uttar Pradesh");
        cityToStateMap.put("Srinagar", "Jammu and Kashmir");
        cityToStateMap.put("Calicut", "Kerala");
        cityToStateMap.put("Kochi", "Kerala");

        cityComboBox = new JComboBox<>(cities);
        stateComboBox = new JComboBox<>(states);

        cityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Automatically generate the state after selecting the city
                String selectedCity = (String) cityComboBox.getSelectedItem();
                String selectedState = cityToStateMap.get(selectedCity);
                if (selectedState != null) {
                    stateComboBox.setSelectedItem(selectedState);
                    stateComboBox.setEnabled(false); // Disable the state field
                }
            }
        });

        // Restrict input in name field to only accept characters
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    e.consume(); // Ignore the input if it's not a letter or space
                }
            }
        });

        add(headingLabel);
        add(new JLabel()); // Empty label for layout purposes
        add(nameLabel);
        add(nameField);
        add(dobLabel);
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        dobPanel.setBackground(Color.WHITE);
        dobPanel.add(dobDayComboBox);
        dobPanel.add(dobMonthComboBox);
        dobPanel.add(dobYearComboBox);
        add(dobPanel);
        add(ageLabel);
        add(ageField);
        add(genderLabel);
        add(genderPanel); // Add the panel containing radio buttons
        add(phoneLabel);
        add(phoneField);
        add(cityLabel);
        add(cityComboBox);
        add(stateLabel);
        add(stateComboBox);
        add(candidateLabel);
        add(candidateComboBox);
        add(new JLabel()); // Empty label for layout purposes
        add(voteButton);
        add(messageLabel);

        candidateVotes = new LinkedHashMap<>(); // Use LinkedHashMap to maintain insertion order
        for (String candidate : candidates) {
            candidateVotes.put(candidate, 0);
        }

        votedVoters = new HashSet<>();

        dobDayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAge();
            }
        });
        dobMonthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAge();
            }
        });
        dobYearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAge();
            }
        });

        voteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vote();
            }
        });

        // Apply a different look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to the content pane
    }

    private void calculateAge() {
        // Get selected day, month, and year
        int day = Integer.parseInt((String) dobDayComboBox.getSelectedItem());
        int month = dobMonthComboBox.getSelectedIndex() + 1; // Month starts from 0
        int year = Integer.parseInt((String) dobYearComboBox.getSelectedItem());

        // Get current date
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1; // Month starts from 0
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        // Calculate age
        int age = currentYear - year;
        if (currentMonth < month || (currentMonth == month && currentDay < day)) {
            age--;
        }

        // Update ageField with calculated age
        ageField.setText(String.valueOf(age));
    }

    private void vote() {
        String userName = nameField.getText().toLowerCase(); // Convert name to lowercase
        String userAgeStr = ageField.getText();
        String city = (String) cityComboBox.getSelectedItem();
        String state = (String) stateComboBox.getSelectedItem();
        String phone = phoneField.getText();
        String selectedCandidate = (String) candidateComboBox.getSelectedItem();
        String gender = "";
        if (maleRadioButton.isSelected()) {
            gender = "Male";
        } else if (femaleRadioButton.isSelected()) {
            gender = "Female";
        } else if (notSpecifiedRadioButton.isSelected()) {
            gender = "Not Specified";
        }

        // Check if all fields are filled
        if (userName.isEmpty() || userAgeStr.isEmpty() || city.isEmpty() || state.isEmpty() || phone.isEmpty() || (!maleRadioButton.isSelected() && !femaleRadioButton.isSelected() && !notSpecifiedRadioButton.isSelected())) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        int userAge;
        try {
            userAge = Integer.parseInt(userAgeStr);
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid age.");
            return;
        }

        String userKey = userName + ":" + userAge;

        if (!votedVoters.contains(userKey)) {
            if (userAge >= 18) {
                // Check if phone number contains exactly 10 digits
                if (!phone.matches("\\d{10}")) {
                    messageLabel.setText("Please enter a valid phone number with exactly 10 digits.");
                    return;
                }

                int votes = candidateVotes.get(selectedCandidate);
                candidateVotes.put(selectedCandidate, votes + 1);

                votedVoters.add(userKey);
                messageLabel.setText("Thank you for voting, " + userName + "!");
            } else {
                messageLabel.setText("I'm sorry, you are not eligible to vote.");
            }
        } else {
            messageLabel.setText("You have already voted. Thank you for your participation.");
        }

        // Display the final vote count
        StringBuilder result = new StringBuilder("Candidate Votes:\n");
        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append(" votes\n");
        }
        JOptionPane.showMessageDialog(this, result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OnlineVotingSystem votingSystem = new OnlineVotingSystem();
                votingSystem.setVisible(true);
            }
        });
    }

    // Custom UI class to make radio buttons' background white
    class WhiteRadioButtonUI extends MetalRadioButtonUI {
        protected Color getSelectColor() {
            return Color.WHITE; // Set the background color of the radio button when selected
        }

        protected Color getDisabledTextColor() {
            return Color.BLACK; // Set the text color of the radio button when disabled
        }

        protected Color getFocusColor() {
            return Color.WHITE; // Set the background color of the radio button when focused
        }

        protected Color getSelectedTextColor() {
            return Color.BLACK; // Set the text color of the radio button when selected
        }

        protected Color getTextColor() {
            return Color.BLACK; // Set the default text color of the radio button
        }
    }
}
