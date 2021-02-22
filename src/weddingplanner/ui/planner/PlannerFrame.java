package weddingplanner.ui.planner;

import weddingplanner.managers.DateUtils;
import weddingplanner.managers.EmailManager;
import weddingplanner.managers.EventManager;
import weddingplanner.managers.UsersManager;
import weddingplanner.model.*;
import weddingplanner.model.Event;
import weddingplanner.ui.SwingUtils;
import weddingplanner.ui.admin.AddPlannerDialog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laila-elhattab
 */

public class PlannerFrame extends JFrame implements ActionListener {

    private Planner planner;
    private List<Event> events;
    private JTable table;
    private DefaultTableModel tableModel;
    private Event selectedEvent;
    private int selectedRow;

    private JTextField jtClientName;
    private JTextField jtClientEmail;
    private JTextArea jtPlannerResponse;
    private JTextField jtStatus;
    private JRadioButton jrAcceptYes;
    private JRadioButton jrAcceptNo;
    private ButtonGroup bgAcceptReject;
    private JLabel title;

    private JTextField jtType;
    private JComboBox<Catering> jcFood;
    private JComboBox<Venue> jcPlace;
    private JComboBox<Theme> jcTheme;
    private JTextField jtBudget;

    private JTextField jtDate;
    private JTextField jtAttendeesNumber;
    private JButton jbAttendeesFile;
    private JButton jbSave;
    private JButton jbCancel;


    public PlannerFrame(Planner planner) {
        super("Planner");
        this.planner = planner;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new BorderLayout());

        JLabel jl = new JLabel();
        String str = "<html><b>" + planner.getName() + "<b><br><span style=\"color:blue\"> " + planner.getEmail() + "</span></html>";
        jl.setText(str);
        jl.setHorizontalAlignment(SwingConstants.RIGHT);
        jl.setVerticalAlignment(SwingConstants.CENTER);
        jl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        add(jl, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, eventsPanel(), eventDetails());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);

        add(splitPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent selectionEvent) {
                if (table.getSelectedRow() > -1) {
                    Event event = events.get(table.getSelectedRow());
                    selectEvent(event, table.getSelectedRow());
                }
            }
        });
        if(events.size()>0){
            table.changeSelection(0, 0, false, false);
        }
        setVisible(true);
    }

    private JPanel eventsPanel() {

        tableModel = new DefaultTableModel(0, 0);
        String[] columnNames = {"Client", "Event", "Date", "State"};
        tableModel.setColumnIdentifiers(columnNames);

        events = new ArrayList<>();
        List<Event> allEvents = EventManager.getInstance().loadAll();
        for (Event event : allEvents) {
            if (event.getPlannerEmail() != null && event.getPlannerEmail().equalsIgnoreCase(planner.getEmail())) {
                events.add(event);
            }
        }


        for (int i = 0; i < events.size(); i++) {
            tableModel.addRow(convertToRow(events.get(i)));
        }

        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(219, 201, 201));

        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel eventDetails() {

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(new Color(245, 238, 238));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;

        fieldsPanel.add(clientInfoPanel(), gbc);
        gbc.gridx++;
        fieldsPanel.add(plannerInfoPanel(), gbc);
        gbc.gridx++;
        fieldsPanel.add(eventInfoPanel());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Event Details"));
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel(), BorderLayout.SOUTH);
        panel.setBackground(new Color(245, 238, 238));
        return panel;
    }

    private JPanel clientInfoPanel() {
        jtClientName = new JTextField();
        jtClientName.setEditable(false);
        jtClientEmail = new JTextField();
        jtClientEmail.setEditable(false);

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
        clientPanel.add(jtClientName, gbc);
        gbc.gridy++;
        clientPanel.add(jtClientEmail, gbc);
        return clientPanel;
    }

    private JPanel plannerInfoPanel() {

        jtPlannerResponse = new JTextArea(3, 15);
        jtStatus = new JTextField();
        jtStatus.setEditable(false);
        jrAcceptYes = new JRadioButton("Yes");
        jrAcceptNo = new JRadioButton("No");
        bgAcceptReject = new ButtonGroup();
        bgAcceptReject.add(jrAcceptYes);
        bgAcceptReject.add(jrAcceptNo);

        JPanel plannerPanel = new JPanel(new GridBagLayout());
        plannerPanel.setBackground(new Color(245, 238, 238));
        plannerPanel.setBorder(BorderFactory.createTitledBorder("Planner"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 4, 4, 4);
        plannerPanel.add(new JLabel("Status: "), gbc);
        gbc.gridy++;
        plannerPanel.add(new JLabel("Response: "), gbc);
        gbc.gridy++;
        plannerPanel.add(new JLabel("Accept: "), gbc);

        gbc.gridy = 0;
        gbc.gridx++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        plannerPanel.add(jtStatus, gbc);
        gbc.gridy++;
        plannerPanel.add(jtPlannerResponse, gbc);
        gbc.gridy++;
        JPanel radioPanel = new JPanel();
        radioPanel.add(jrAcceptYes);
        radioPanel.add(jrAcceptNo);
        plannerPanel.add(radioPanel, gbc);

        return plannerPanel;
    }

    private JPanel eventInfoPanel() {

        jtType = new JTextField();
        jtType.setEditable(false);
        jtDate = new JTextField();
        jtDate.setEditable(false);
        jcFood = new JComboBox<>(Catering.values());
        jcPlace = new JComboBox<>(Venue.values());
        jcTheme = new JComboBox<>(Theme.values());
        jtBudget = new JTextField();
        SwingUtils.allowDigitsOnly(jtBudget);
        jtAttendeesNumber = new JTextField();
        SwingUtils.allowDigitsOnly(jtAttendeesNumber);
        jbAttendeesFile = new JButton();
        jbAttendeesFile.setIcon(new ImageIcon(AddPlannerDialog.class.getResource("excel.png")));
        jbAttendeesFile.setEnabled(false);
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
        eventPanel.add(jtType, gbc);
        gbc.gridy++;
        eventPanel.add(jtDate, gbc);
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

    private JPanel buttonsPanel() {
        jbSave = new JButton("Save");
        jbSave.addActionListener(this);
        jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(jbSave);
        buttonsPanel.add(jbCancel);
        return buttonsPanel;
    }

    private Object[] convertToRow(Event event) {
        Object[] row = new Object[4];
        row[0] = UsersManager.getInstance().getUser(event.getClientEmail()).getName();
        row[1] = event.getType().toString();
        row[2] = DateUtils.toString(event.getEventDate());
        row[3] = event.getStatus().toString();
        return row;
    }

    private void updateEvent(Event event, int row) {
        Object[] data = convertToRow(event);
        for (int i = 0; i < data.length; i++) {
            tableModel.setValueAt(data[i], row, i);
        }
    }

    private void selectEvent(Event event, int row) {
        this.selectedEvent = event;
        this.selectedRow = row;

        this.jtClientName.setText(UsersManager.getInstance().getUser(event.getClientEmail()).getName());
        this.jtClientEmail.setText(event.getClientEmail());

        this.jtPlannerResponse.setText(event.getPlannerResponse());
        this.jtStatus.setText(event.getStatus().toString());
        bgAcceptReject.clearSelection();
        if (event.getStatus() == EventStatus.Rejected) {
            this.jrAcceptNo.setSelected(true);
        } else if (event.getStatus() != EventStatus.Assigned) {
            this.jrAcceptNo.setSelected(true);
        }

        this.jtType.setText(event.getType().toString());
        this.jtDate.setText(DateUtils.toString(event.getEventDate()));
        this.jcFood.setSelectedItem(event.getFood());
        this.jcPlace.setSelectedItem(event.getPlace());
        this.jcTheme.setSelectedItem(event.getTheme());
        this.jtBudget.setText(String.valueOf(event.getBudget()));
        this.jtAttendeesNumber.setText(String.valueOf(event.getAttendeesNumber()));
        this.jbAttendeesFile.setEnabled(event.getAttendeesList() != null && event.getAttendeesList() != "");
        this.jbAttendeesFile.setText(event.getAttendeesList());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbAttendeesFile)) {
            try {
                Runtime.getRuntime().exec("open " + selectedEvent.getAttendeesList());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource().equals(jbSave)){
            try {
                EventStatus oldStatus = selectedEvent.getStatus();
                boolean sendRejectEmail=false, sendAcceptEmail=false;
                if(jrAcceptNo.isSelected() && oldStatus != EventStatus.Rejected){
                    selectedEvent.setStatus(EventStatus.Rejected);
                    sendRejectEmail = true;
                }
                else if(jrAcceptYes.isSelected() && oldStatus == EventStatus.Assigned){
                    selectedEvent.setStatus(EventStatus.Accepted);
                    sendAcceptEmail = true;
                }
                selectedEvent.setPlannerResponse(jtPlannerResponse.getText());
                selectedEvent.setAttendeesNumber(Integer.valueOf(jtAttendeesNumber.getText()));
                selectedEvent.setBudget(Integer.valueOf(jtBudget.getText()));
                selectedEvent.setTheme((Theme) jcTheme.getSelectedItem());
                selectedEvent.setFood((Catering) jcFood.getSelectedItem());
                selectedEvent.setPlace((Venue) jcPlace.getSelectedItem());

                EventManager.getInstance().edit(selectedEvent);
                updateEvent(selectedEvent, selectedRow);
                if(sendAcceptEmail){
                    EmailManager.sendAcceptEventEmail(selectedEvent.getClientEmail(),jtClientName.getText(), this.planner.getName(), selectedEvent);
                }else if(sendRejectEmail){
                    EmailManager.sendRejectEventEmail(selectedEvent.getClientEmail(),jtClientName.getText(), this.planner.getName(), selectedEvent);
                }
            }catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Sorry, the event can't be updated. Please contact the administrator");
            }
        }else if(e.getSource().equals(jbCancel)){
            if(selectedEvent != null){
                selectEvent(selectedEvent, selectedRow);
            }
        }
    }
}
