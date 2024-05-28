import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HotelReservationSystem extends JFrame {
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    public HotelReservationSystem() {
        setTitle("Hotel Reservation System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        initializeRooms();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton searchRoomsButton = new JButton("Search Rooms");
        JButton makeReservationButton = new JButton("Make Reservation");
        JButton viewBookingsButton = new JButton("View Bookings");

        searchRoomsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchRoomsPanel(rooms).setVisible(true);
            }
        });

        makeReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MakeReservationPanel(rooms, reservations).setVisible(true);
            }
        });

        viewBookingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewBookingsPanel(reservations).setVisible(true);
            }
        });

        panel.add(searchRoomsButton);
        panel.add(makeReservationButton);
        panel.add(viewBookingsButton);

        add(panel);
    }

    private void initializeRooms() {
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i, "Single"));
            rooms.add(new Room(i + 100, "Double"));
            rooms.add(new Room(i + 200, "Suite"));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HotelReservationSystem().setVisible(true);
            }
        });
    }
}

class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + category + ")";
    }
}

class Reservation {
    private Room room;
    private String guestName;
    private String startDate;
    private String endDate;

    public Reservation(Room room, String guestName, String startDate, String endDate) {
        this.room = room;
        this.guestName = guestName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Reservation: " + guestName + " - Room " + room.getRoomNumber() + " from " + startDate + " to " + endDate;
    }
}

class SearchRoomsPanel extends JFrame {
    public SearchRoomsPanel(ArrayList<Room> rooms) {
        setTitle("Search Rooms");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel categoryLabel = new JLabel("Room Category:");
        JTextField categoryField = new JTextField(10);
        JTextArea resultsArea = new JTextArea(5, 20);
        resultsArea.setEditable(false);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String category = categoryField.getText();
                resultsArea.setText("");
                for (Room room : rooms) {
                    if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                        resultsArea.append(room + "\n");
                    }
                }
            }
        });

        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(searchButton);
        panel.add(new JScrollPane(resultsArea));

        add(panel);
    }
}

class MakeReservationPanel extends JFrame {
    public MakeReservationPanel(ArrayList<Room> rooms, ArrayList<Reservation> reservations) {
        setTitle("Make Reservation");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel roomNumberLabel = new JLabel("Room Number:");
        JTextField roomNumberField = new JTextField(10);
        JLabel guestNameLabel = new JLabel("Guest Name:");
        JTextField guestNameField = new JTextField(10);
        JLabel startDateLabel = new JLabel("Start Date:");
        JTextField startDateField = new JTextField(10);
        JLabel endDateLabel = new JLabel("End Date:");
        JTextField endDateField = new JTextField(10);

        JButton reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                String guestName = guestNameField.getText();
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();

                for (Room room : rooms) {
                    if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                        room.setAvailable(false);
                        Reservation reservation = new Reservation(room, guestName, startDate, endDate);
                        reservations.add(reservation);
                        JOptionPane.showMessageDialog(null, "Reservation successful!");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Room not available!");
            }
        });

        panel.add(roomNumberLabel);
        panel.add(roomNumberField);
        panel.add(guestNameLabel);
        panel.add(guestNameField);
        panel.add(startDateLabel);
        panel.add(startDateField);
        panel.add(endDateLabel);
        panel.add(endDateField);
        panel.add(reserveButton);

        add(panel);
    }
}

class ViewBookingsPanel extends JFrame {
    public ViewBookingsPanel(ArrayList<Reservation> reservations) {
        setTitle("View Bookings");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JTextArea bookingsArea = new JTextArea(10, 20);
        bookingsArea.setEditable(false);

        for (Reservation reservation : reservations) {
            bookingsArea.append(reservation + "\n");
        }

        panel.add(new JScrollPane(bookingsArea));
        add(panel);
    }
}
