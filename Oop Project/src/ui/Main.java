package ui;

import model.*;
import service.RideService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    private JFrame frame;
    private JLabel result;
    private JLabel statusLabel;
    private JButton acceptBtn, completeBtn, cancelBtn;
    private Ride currentRide;
    private ArrayList<Driver> drivers = new ArrayList<>();

    public Main() {
        drivers.add(new Driver("Ali Hamza", new Car()));
        drivers.add(new Driver("Saleem", new Bike()));
        drivers.add(new Driver("Ammna", new Car()));
    }

    public void createAndShowGUI() {
        frame = new JFrame("LetsRyde - Ride Booking");
        frame.setSize(800, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(173, 216, 230));

        // Heading
        JLabel title = new JLabel("LetsRyde - Book Your Ride", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(title, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(135, 206, 250));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        // Rider Name
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(new JLabel("Rider Name:"), gbc);
        gbc.gridx = 1;
        JTextField riderField = new JTextField(20);
        centerPanel.add(riderField, gbc);

        // From
        gbc.gridx = 0; gbc.gridy = 1;
        centerPanel.add(new JLabel("From:"), gbc);
        gbc.gridx = 1;
        JTextField originField = new JTextField(20);
        centerPanel.add(originField, gbc);

        // To
        gbc.gridx = 0; gbc.gridy = 2;
        centerPanel.add(new JLabel("To:"), gbc);
        gbc.gridx = 1;
        JTextField destField = new JTextField(20);
        centerPanel.add(destField, gbc);

        // Distance
        gbc.gridx = 0; gbc.gridy = 3;
        centerPanel.add(new JLabel("Distance (km):"), gbc);
        gbc.gridx = 1;
        JTextField distanceField = new JTextField(20);
        centerPanel.add(distanceField, gbc);

        // Drivers
        gbc.gridx = 0; gbc.gridy = 4;
        centerPanel.add(new JLabel("Available Drivers:"), gbc);
        gbc.gridx = 1;
        DefaultListModel<String> driverModel = new DefaultListModel<>();
        for (Driver d : drivers) {
            driverModel.addElement(d.getName() + " (" + d.getVehicle().getType() + ")");
        }
        JList<String> driverList = new JList<>(driverModel);
        driverList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        driverList.setVisibleRowCount(3);
        centerPanel.add(new JScrollPane(driverList), gbc);

        // Book Ride Button
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JButton bookBtn = new JButton("Book Ride");
        bookBtn.setBackground(new Color(0, 102, 204));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setPreferredSize(new Dimension(150, 35));
        centerPanel.add(bookBtn, gbc);

        // Status Label
        gbc.gridy = 6;
        statusLabel = new JLabel("Ride Status: N/A", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(new Color(0, 80, 160));
        centerPanel.add(statusLabel, gbc);

        // Status Buttons Panel
        gbc.gridy = 7;
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        statusPanel.setBackground(new Color(135, 206, 250));

        acceptBtn = new JButton("✓ Accept Ride");
        acceptBtn.setBackground(new Color(0, 153, 76));
        acceptBtn.setForeground(Color.WHITE);
        acceptBtn.setEnabled(false);

        completeBtn = new JButton("✓ Complete Ride");
        completeBtn.setBackground(new Color(0, 102, 204));
        completeBtn.setForeground(Color.WHITE);
        completeBtn.setEnabled(false);

        cancelBtn = new JButton("✗ Cancel Ride");
        cancelBtn.setBackground(new Color(204, 0, 0));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setEnabled(false);

        statusPanel.add(acceptBtn);
        statusPanel.add(completeBtn);
        statusPanel.add(cancelBtn);
        centerPanel.add(statusPanel, gbc);

        // View History Button
        gbc.gridy = 8;
        JButton historyBtn = new JButton("View History");
        historyBtn.setBackground(new Color(0, 153, 255));
        historyBtn.setForeground(Color.WHITE);
        historyBtn.setPreferredSize(new Dimension(150, 30));
        centerPanel.add(historyBtn, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Bottom result panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(173, 216, 230));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        result = new JLabel(" ");
        result.setFont(new Font("Arial", Font.BOLD, 15));
        bottomPanel.add(result);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Book Ride Action
        bookBtn.addActionListener(e -> {
            try {
                String riderName = riderField.getText().trim();
                String origin = originField.getText().trim();
                String destination = destField.getText().trim();
                double distance = Double.parseDouble(distanceField.getText().trim());

                if (riderName.isEmpty() || origin.isEmpty() || destination.isEmpty()) {
                    setResult("Please fill all fields.", Color.RED);
                    return;
                }
                if (distance <= 0) {
                    setResult("Distance must be positive.", Color.RED);
                    return;
                }
                int selectedIndex = driverList.getSelectedIndex();
                if (selectedIndex == -1) {
                    setResult("Please select a driver.", Color.RED);
                    return;
                }

                Driver selectedDriver = drivers.get(selectedIndex);
                Rider rider = new Rider(riderName);
                currentRide = new Ride(rider, selectedDriver, distance);
                RideService.saveRide(currentRide);

                setResult(String.format("Ride booked from %s to %s with %s! Fare: Rs %.2f",
                        origin, destination, selectedDriver.getName(), currentRide.getFare()),
                        new Color(0, 128, 0));

                updateStatus("Pending");
                acceptBtn.setEnabled(true);
                cancelBtn.setEnabled(true);
                completeBtn.setEnabled(false);

            } catch (NumberFormatException ex) {
                setResult("Please enter a valid number for distance.", Color.RED);
            } catch (Exception ex) {
                setResult("Error booking ride. Try again.", Color.RED);
            }
        });

        // Accept Ride Action
        acceptBtn.addActionListener(e -> {
            if (currentRide != null) {
                currentRide.setStatus("On the Way");
                updateStatus("On the Way");
                acceptBtn.setEnabled(false);
                completeBtn.setEnabled(true);
                cancelBtn.setEnabled(true);
                setResult("Driver is on the way!", new Color(0, 102, 0));
            }
        });

        // Complete Ride Action
        completeBtn.addActionListener(e -> {
            if (currentRide != null) {
                currentRide.setStatus("Completed");
                updateStatus("Completed");
                completeBtn.setEnabled(false);
                acceptBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                setResult("Ride completed! Thank you for using LetsRyde.", new Color(0, 128, 0));
            }
        });

        // Cancel Ride Action
        cancelBtn.addActionListener(e -> {
            if (currentRide != null) {
                currentRide.setStatus("Cancelled");
                updateStatus("Cancelled");
                acceptBtn.setEnabled(false);
                completeBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                setResult("Ride has been cancelled.", Color.RED);
            }
        });

        // View History Action
        historyBtn.addActionListener(e -> {
            ArrayList<String> rides = RideService.loadRides();
            if (rides.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No ride history found.", "Ride History", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%-15s %-15s %-8s %-8s %-10s %-12s%n",
                        "Rider", "Driver", "Type", "Dist", "Fare", "Status"));
                sb.append("-".repeat(70)).append("\n");
                for (String r : rides) {
                    String[] parts = r.split(",");
                    if (parts.length >= 5) {
                        String status = parts.length >= 6 ? parts[5] : "N/A";
                        sb.append(String.format("%-15s %-15s %-8s %-8s %-10s %-12s%n",
                                parts[0], parts[1], parts[2], parts[3], parts[4], status));
                    }
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
                JScrollPane historyScroll = new JScrollPane(textArea);
                historyScroll.setPreferredSize(new Dimension(600, 300));
                JOptionPane.showMessageDialog(frame, historyScroll, "Ride History", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private void updateStatus(String status) {
        statusLabel.setText("Ride Status: " + status);
        switch (status) {
            case "Pending":
                statusLabel.setForeground(new Color(204, 102, 0));
                break;
            case "On the Way":
                statusLabel.setForeground(new Color(0, 102, 204));
                break;
            case "Completed":
                statusLabel.setForeground(new Color(0, 153, 0));
                break;
            case "Cancelled":
                statusLabel.setForeground(Color.RED);
                break;
        }
    }

    private void setResult(String msg, Color color) {
        result.setText(msg);
        result.setForeground(color);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.createAndShowGUI();
        });
    }
}