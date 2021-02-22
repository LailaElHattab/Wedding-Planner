package weddingplanner.ui.client;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import weddingplanner.managers.DateUtils;
import weddingplanner.managers.EventManager;
import weddingplanner.managers.UsersManager;
import weddingplanner.model.*;
import weddingplanner.model.Event;
import weddingplanner.ui.SwingUtils;
import weddingplanner.ui.admin.AddPlannerDialog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author laila-elhattab
 */

public class ClientFrame extends JFrame implements ActionListener {

    private Client client;
    private List<Event> events;
    private JTable table;
    private DefaultTableModel tableModel;
    private Event selectedEvent;
    private int selectedRow;

    private JTextField jtPlannerName;
    private JTextField jtPlannerEmail;
    private JTextArea jtPlannerResponse;
    private JTextField jtStatus;

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
    private JButton jbNew;
    private JButton jbCancel;
    private JLabel title;

    public ClientFrame(Client client) {
        super("Client");
        this.client = client;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new BorderLayout());

        JLabel jl = new JLabel();
        String str = "<html><b>" + client.getName() + "<b><br><span style=\"color:blue\"> " + client.getEmail() + "</span></html>";
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

        if (events.size() > 0) {
            table.changeSelection(0, 0, false, false);
        } else {
            jbNew.doClick();
        }
        setVisible(true);
    }

    private JPanel eventsPanel() {

        tableModel = new DefaultTableModel(0, 0);
        String[] columnNames = {"Event", "Date", "State", "Planner"};
        tableModel.setColumnIdentifiers(columnNames);

        events = new ArrayList<>();
        List<Event> allEvents = EventManager.getInstance().loadAll();
        for (Event event : allEvents) {
            if (event.getClientEmail().equalsIgnoreCase(client.getEmail())) {
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
        panel.setBackground(new Color(245, 238, 238));
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
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;

        fieldsPanel.add(eventInfoPanel(), gbc);
        gbc.gridx++;
        fieldsPanel.add(plannerInfoPanel(), gbc);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 238, 238));
        panel.setBorder(BorderFactory.createTitledBorder("Event Details"));
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel plannerInfoPanel() {

        jtPlannerName = new JTextField();
        jtPlannerName.setEditable(false);
        jtPlannerEmail = new JTextField();
        jtPlannerEmail.setEditable(false);

        jtPlannerResponse = new JTextArea(2, 5);
        jtPlannerResponse.setEditable(false);
        jtStatus = new JTextField();
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
        plannerPanel.add(jtPlannerName, gbc);
        gbc.gridy++;
        plannerPanel.add(jtPlannerEmail, gbc);
        gbc.gridy++;
        plannerPanel.add(jtPlannerResponse, gbc);
        gbc.gridy++;
        plannerPanel.add(jtStatus, gbc);
        return plannerPanel;
    }

    private JPanel eventInfoPanel() {

        jcType = new JComboBox<>(EventType.values());

        dateModel = new UtilDateModel();
        Properties dateProp = new Properties();
        dateProp.put("text.today", "Today");
        dateProp.put("text.month", "Month");
        dateProp.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProp);
        jDatePicker = new JDatePickerImpl(datePanel, new ClientFrame.DateLabelFormatter());

        jcFood = new JComboBox<>(Catering.values());
        jcPlace = new JComboBox<>(Venue.values());
        jcTheme = new JComboBox<>(Theme.values());
        jtBudget = new JTextField();
        SwingUtils.allowDigitsOnly(jtBudget);
        jtAttendeesNumber = new JTextField();
        SwingUtils.allowDigitsOnly(jtAttendeesNumber);
        jbAttendeesFile = new JButton();
        jbAttendeesFile.setIcon(new ImageIcon(AddPlannerDialog.class.getResource("excel.png")));
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

    private JPanel buttonsPanel() {
        jbNew = new JButton("New");
        jbNew.addActionListener(this);
        jbSave = new JButton("Save");
        jbSave.addActionListener(this);
        jbCancel = new JButton("Cancel");
        jbCancel.addActionListener(this);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(245, 238, 238));
        buttonsPanel.add(jbNew);
        buttonsPanel.add(jbSave);
        buttonsPanel.add(jbCancel);
        return buttonsPanel;
    }

    private Object[] convertToRow(Event event) {
        Object[] row = new Object[4];
        row[0] = event.getType().toString();
        row[1] = DateUtils.toString(event.getEventDate());
        row[2] = event.getStatus().toString();
        row[3] = event.getPlannerEmail() != null ? UsersManager.getInstance().getUser(event.getPlannerEmail()).getName() : "";
        return row;
    }

    private void updateEvent(Event event, int row) {
        Object[] data = convertToRow(event);
        if (selectedRow == -1) {
            events.add(event);
            tableModel.addRow(data);
            table.changeSelection(events.size() - 1, 0, false, false);
        } else {
            for (int i = 0; i < data.length; i++) {
                tableModel.setValueAt(data[i], row, i);
            }
        }

    }

    private void selectEvent(Event event, int row) {
        this.selectedEvent = event;
        this.selectedRow = row;

        this.jtPlannerEmail.setText(event.getPlannerEmail());
        if (event.getPlannerEmail() != null) {
            this.jtPlannerName.setText(UsersManager.getInstance().getUser(event.getPlannerEmail()).getName());
        } else {
            this.jtPlannerName.setText("");
        }

        this.jtPlannerResponse.setText(event.getPlannerResponse());
        this.jtStatus.setText(event.getStatus().toString());

        this.jcType.setSelectedItem(event.getType());
        this.dateModel.setValue(DateUtils.toDate(event.getEventDate()));
        this.jcFood.setSelectedItem(event.getFood());
        this.jcPlace.setSelectedItem(event.getPlace());
        this.jcTheme.setSelectedItem(event.getTheme());
        this.jtBudget.setText(String.valueOf(event.getBudget()));
        this.jtAttendeesNumber.setText(String.valueOf(event.getAttendeesNumber()));
        this.jbAttendeesFile.setText(event.getAttendeesList());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbAttendeesFile)) {
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
        } else if (e.getSource().equals(jbSave)) {
            try {
                if (jcType.getSelectedItem() == null || dateModel.getValue() == null || jcTheme.getSelectedItem() == null
                        || jcFood.getSelectedItem() == null || jcPlace.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Please continue filling the empty fields");
                    return;
                }
                selectedEvent.setType((EventType) jcType.getSelectedItem());
                selectedEvent.setEventDate(dateModel.getValue().getTime());
                selectedEvent.setAttendeesNumber(Integer.valueOf(jtAttendeesNumber.getText()));
                selectedEvent.setBudget(Integer.valueOf(jtBudget.getText()));
                selectedEvent.setTheme((Theme) jcTheme.getSelectedItem());
                selectedEvent.setFood((Catering) jcFood.getSelectedItem());
                selectedEvent.setPlace((Venue) jcPlace.getSelectedItem());
                selectedEvent.setAttendeesList(jbAttendeesFile.getText());

                if (selectedRow == -1) {
                    EventManager.getInstance().add(selectedEvent);
                } else {
                    EventManager.getInstance().edit(selectedEvent);
                }
                updateEvent(selectedEvent, selectedRow);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Sorry, the event can't be updated. Please contact the administrator");
            }
        } else if (e.getSource().equals(jbCancel)) {
            if (selectedEvent != null) {
                selectEvent(selectedEvent, selectedRow);
            }
        } else if (e.getSource().equals(jbNew)) {
            table.clearSelection();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_MONTH, 1);
            Event event = new Event(client.getEmail(), EventType.ENGAGEMENT, cal.getTime().getTime());
            selectEvent(event, -1);
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
