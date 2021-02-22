package weddingplanner.ui.admin;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import weddingplanner.managers.DateUtils;
import weddingplanner.managers.EventManager;
import weddingplanner.managers.UsersManager;
import weddingplanner.model.*;
import weddingplanner.model.Event;
import weddingplanner.ui.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * @author laila-elhattab
 */

public class EventDetailsDialog extends JDialog implements ActionListener {

    private Event event;
    private int eventRow;
    private EventsTable eventsTable;

    private JComboBox<Planner> jcPlanner;
    private JTextArea jtPlannerResponse;
    private JComboBox<EventType> jcType;
    private JComboBox<Catering> jcFood;
    private JComboBox<Venue> jcPlace;
    private JComboBox<Theme> jcTheme;
    private JTextField jtBudget;

    private UtilDateModel dateModel;
    private JDatePickerImpl jDatePicker;
    private JTextField jtAttendeesNumber;
    private JButton jbAttendeesFile;
    private JButton jbSave;
    private JButton jbCancel;

    public EventDetailsDialog(JFrame owner, EventsTable eventsTable, Event event, int eventRow) {
        super(owner, "Event Details", true);
        this.event = event;
        this.eventRow = eventRow;
        this.eventsTable = eventsTable;

        setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(new Color(245, 238, 238));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;

        fieldsPanel.add(clientPanel(event), gbc);
        gbc.gridx++;
        fieldsPanel.add(plannerPanel(event), gbc);
        gbc.gridx++;
        fieldsPanel.add(eventPanel(event));


        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel(event), BorderLayout.SOUTH);
        this.pack();
        setLocationRelativeTo(owner);
    }

    private JPanel clientPanel(Event event) {
        User client = UsersManager.getInstance().getUser(event.getClientEmail());
        JTextField jtName = new JTextField(client.getName());
        jtName.setEditable(false);
        JTextField jtEmail = new JTextField(event.getClientEmail());
        jtEmail.setEditable(false);

        JPanel clientPanel = new JPanel(new GridBagLayout());
        clientPanel.setBackground(new Color(245, 238, 238));
        clientPanel.setBorder(BorderFactory.createTitledBorder("Client"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 4, 4, 4);
        clientPanel.add(new JLabel("Name: "), gbc);
        gbc.gridy++;
        clientPanel.add(new JLabel("Email: "), gbc);

        gbc.gridy = 0;
        gbc.gridx++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        clientPanel.add(jtName, gbc);
        gbc.gridy++;
        clientPanel.add(jtEmail, gbc);
        return clientPanel;
    }

    private JPanel plannerPanel(Event event) {
        java.util.List<User> users = UsersManager.getInstance().loadAll();
        List<Planner> planners = new ArrayList<>();
        for (User user: users){
            if(user instanceof Planner){
                planners.add((Planner) user);
            }
        }
        jcPlanner = new JComboBox(planners.toArray());
        Planner planner = null;
        if (event.getPlannerEmail() != null) {
            planner = (Planner) UsersManager.getInstance().getUser(event.getPlannerEmail());
        }
        jcPlanner.setSelectedItem(planner);

        JTextField jtPlannerEmail = new JTextField();
        jtPlannerEmail.setEditable(false);
        jtPlannerEmail.setText(event.getPlannerEmail());
        jcPlanner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jcPlanner.getSelectedItem() != null) {
                    jtPlannerEmail.setText(((Planner) jcPlanner.getSelectedItem()).getEmail());
                } else {
                    jtPlannerEmail.setText("");
                }
            }
        });

        jtPlannerResponse = new JTextArea(3, 15);
        jtPlannerResponse.setText(event.getPlannerResponse());
        jtPlannerResponse.setEditable(false);
        JTextField jtStatus = new JTextField(event.getStatus().toString());
        jtStatus.setEditable(false);


        JPanel plannerPanel = new JPanel(new GridBagLayout());
        plannerPanel.setBackground(new Color(245, 238, 238));
        plannerPanel.setBorder(BorderFactory.createTitledBorder("Planner"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 4, 4, 4);
        plannerPanel.add(new JLabel("Name: "), gbc);
        gbc.gridy++;
        plannerPanel.add(new JLabel("Email: "), gbc);
        gbc.gridy++;
        plannerPanel.add(new JLabel("Response: "), gbc);
        gbc.gridy++;
        plannerPanel.add(new JLabel("Status: "), gbc);

        gbc.gridy = 0;
        gbc.gridx++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        plannerPanel.add(jcPlanner, gbc);
        gbc.gridy++;
        plannerPanel.add(jtPlannerEmail, gbc);
        gbc.gridy++;
        plannerPanel.add(jtPlannerResponse, gbc);
        gbc.gridy++;
        plannerPanel.add(jtStatus, gbc);
        return plannerPanel;
    }

    private JPanel eventPanel(Event event) {

        jcType = new JComboBox<>(EventType.values());
        jcType.setSelectedItem(event.getType());

        dateModel = new UtilDateModel(DateUtils.toDate(event.getEventDate()));
        Properties dateProp = new Properties();
        dateProp.put("text.today", "Today");
        dateProp.put("text.month", "Month");
        dateProp.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProp);
        jDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());


        jcFood = new JComboBox<>(Catering.values());
        jcFood.setSelectedItem(event.getFood());

        jcPlace = new JComboBox<>(Venue.values());
        jcPlace.setSelectedItem(event.getPlace());

        jcTheme = new JComboBox<>(Theme.values());
        jcTheme.setSelectedItem(event.getPlace());

        jtBudget = new JTextField();
        jtBudget.setText(String.valueOf(event.getBudget()));
        SwingUtils.allowDigitsOnly(jtBudget);

        jtAttendeesNumber = new JTextField();
        jtAttendeesNumber.setText(String.valueOf(event.getAttendeesNumber()));
        SwingUtils.allowDigitsOnly(jtAttendeesNumber);
        jbAttendeesFile = new JButton();
        jbAttendeesFile.setIcon(new ImageIcon(AddPlannerDialog.class.getResource("excel.png")));
        jbAttendeesFile.setText(event.getAttendeesList());
        jbAttendeesFile.addActionListener(this);


        JPanel eventPanel = new JPanel(new GridBagLayout());
        eventPanel.setBackground(new Color(245, 238, 238));
        eventPanel.setBorder(BorderFactory.createTitledBorder("Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 4, 4, 4);
        eventPanel.add(new JLabel("Type: "), gbc);
        gbc.gridy++;
        eventPanel.add(new JLabel("Date: "), gbc);
        gbc.gridy++;
        eventPanel.add(new JLabel("Theme: "), gbc);
        gbc.gridy++;
        eventPanel.add(new JLabel("Venue: "), gbc);
        gbc.gridy++;
        eventPanel.add(new JLabel("Catering: "), gbc);
        gbc.gridy++;
        eventPanel.add(new JLabel("Budget: "), gbc);
        gbc.gridy++;
        eventPanel.add(new JLabel("Attendees Number: "), gbc);
        gbc.gridy++;
        eventPanel.add(new JLabel("Attendees List: "), gbc);

        gbc.gridy = 0;
        gbc.gridx++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(jcType, gbc);
        gbc.gridy++;
        eventPanel.add(jDatePicker, gbc);
        gbc.gridy++;
        eventPanel.add(jcTheme, gbc);
        gbc.gridy++;
        eventPanel.add(jcPlace, gbc);
        gbc.gridy++;
        eventPanel.add(jcFood, gbc);
        gbc.gridy++;
        eventPanel.add(jtBudget, gbc);
        gbc.gridy++;
        eventPanel.add(jtAttendeesNumber, gbc);
        gbc.gridy++;
        eventPanel.add(jbAttendeesFile, gbc);

        return eventPanel;
    }


    private JPanel buttonsPanel(Event event) {
        jbSave = new JButton("Save");
        jbSave.addActionListener(this);
        jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(245, 238, 238));
        buttonsPanel.add(jbSave);
        buttonsPanel.add(jbCancel);
        return buttonsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbSave)) {
            try {
                if (jcPlanner.getSelectedItem() != null) {
                    Planner planner = (Planner) jcPlanner.getSelectedItem();
                    event.setPlannerEmail(planner.getEmail());
                }
                event.setEventDate(dateModel.getValue().getTime());
                event.setTheme((Theme) jcTheme.getSelectedItem());
                event.setFood((Catering) jcFood.getSelectedItem());
                event.setPlace((Venue) jcPlace.getSelectedItem());
                event.setBudget(Integer.parseInt(jtBudget.getText()));
                event.setAttendeesNumber(Integer.parseInt(jtAttendeesNumber.getText()));
                event.setAttendeesList(jbAttendeesFile.getText());
                if (event.getPlannerEmail() != null && event.getStatus() == EventStatus.New) {
                    event.setStatus(EventStatus.Assigned);
                }
                EventManager.getInstance().edit(event);
                eventsTable.updateEvent(event, eventRow);

                this.setVisible(false);
            } catch (Exception ex) {
                System.out.println("Error at editEvent: " + ex);
                JOptionPane.showMessageDialog(this, "Sorry, the event can't be updated. Please contact the administrator");
            }

        } else if (e.getSource().equals(jbAttendeesFile)) {
            JFileChooser jfc = new JFileChooser("files");
            jfc.setDialogTitle("Choose the attendees list file");
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xlsx");
            jfc.addChoosableFileFilter(filter);
            jfc.setAcceptAllFileFilterUsed(false);
            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String rootPath = new File("").getAbsolutePath();
                String selectedFile = jfc.getSelectedFile().getAbsolutePath();
                if (selectedFile.startsWith(selectedFile)) {
                    selectedFile = selectedFile.substring(rootPath.length() + 1);
                }
                jbAttendeesFile.setText(selectedFile);
            }
        } else {
            setVisible(false);
        }
    }

    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }

    }
}


