package HotelManagement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.awt.Component;

public class HotelManagementUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Khai báo cho Log Console
    private JTextArea outputArea; 
    
    // Khai báo cho Bảng Dữ liệu
    private JTable roomTable;
    private DefaultTableModel tableModel;

    private RoomList roomList = new RoomList(); 
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    // --- KHAI BÁO CÁC HẰNG SỐ MÀU Ở CẤP ĐỘ LỚP ---
    private final Color HEADER_BG = new Color(41, 128, 185); 
    private final Color SIDEBAR_BG = new Color(44, 62, 80);  
    private final Color DEFAULT_COLOR = new Color(52, 152, 219); 
    private final Color HOVER_COLOR = new Color(41, 128, 185); 
    private final Color EXIT_COLOR = new Color(231, 76, 60);     
    private final Color EXIT_HOVER_COLOR = new Color(192, 57, 43); 
    private final Color CONTENT_BG = new Color(236, 240, 241);
    private final Color OUTPUT_TEXT_COLOR = new Color(52, 73, 94);
    private final Color GROUP_TITLE_COLOR = new Color(155, 165, 175); 
    // ------------------------------------------------------------------------------------


    public HotelManagementUI() {
        setTitle("🏨 Hotel Management System - Premium UI");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel dashboardPanel = createDashboardPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(dashboardPanel, "Dashboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        // Lưu ý: Class BackgroundPanel và file hotel.jpg phải có sẵn trong dự án của bạn
        BackgroundPanel panel = new BackgroundPanel("/HotelManagement/hotel.jpg"); 

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("🏨 Welcome to Luxury Hotel");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(Color.BLUE);

        JTextField txtUser = new JTextField(15);
        JPasswordField txtPass = new JPasswordField(15);
        
        // Nâng cấp nút Login
        JButton btnLogin = new JButton("🔑 Login");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnLogin.setBackground(new Color(46, 204, 113)); 
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(title, gbc);
        gbc.gridy++; JLabel lblUser = new JLabel("👤 Username:"); lblUser.setForeground(Color.WHITE); panel.add(lblUser, gbc);
        gbc.gridy++; panel.add(txtUser, gbc);
        gbc.gridy++; JLabel lblPass = new JLabel("🔒 Password:"); lblPass.setForeground(Color.WHITE); panel.add(lblPass, gbc);
        gbc.gridy++; panel.add(txtPass, gbc);
        gbc.gridy++; panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());
            if (user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "✅ Login successful!");
                cardLayout.show(mainPanel, "Dashboard");
                // Tải dữ liệu ban đầu khi vào Dashboard (tùy chọn)
                // updateRoomTable(); 
            } else {
                JOptionPane.showMessageDialog(this, "❌ Wrong username or password!");
            }
        });

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // --- 1. Header Đẳng cấp ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel header = new JLabel("🏨 Hotel Management Dashboard", JLabel.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        headerPanel.add(header);

        // --- 2. Sidebar Menu (Dùng BoxLayout để phân cấp VÀ CĂNG ĐỀU) ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS)); 
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        sidebar.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái

        String[] btnNames = {
            "➕ Add Room", "✏️ Update Room", "🗑 Delete Room",
            "---DIVIDER---",  
            "🔍 Find Room", "📋 Show All", "💰 Most Expensive",
            "📊 Count Rooms", "🗓 Show With Dates"
        };

        // Bắt đầu tạo nhóm "QUẢN LÝ PHÒNG"
        sidebar.add(createGroupTitle("QUẢN LÝ PHÒNG"));
        sidebar.add(Box.createVerticalStrut(5)); 

        for (String name : btnNames) {
            if (name.equals("---DIVIDER---")) {
                sidebar.add(Box.createVerticalStrut(15));
                sidebar.add(createGroupTitle("BÁO CÁO & THỐNG KÊ"));
                sidebar.add(Box.createVerticalStrut(5));
                continue;
            }

            JButton btn = new JButton(name);
            btn.setFocusPainted(false);
            
            final Color initialColor = DEFAULT_COLOR;
            final Color hoverColor = HOVER_COLOR; 
            
            btn.setBackground(initialColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 15));
            btn.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
            
            // Buộc mở rộng chiều rộng và căn trái
            btn.setAlignmentX(Component.LEFT_ALIGNMENT); 
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, btn.getPreferredSize().height)); 

            // Hiệu ứng Hover
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    ((JButton) evt.getSource()).setBackground(hoverColor);
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    ((JButton) evt.getSource()).setBackground(initialColor);
                }
            });

            btn.addActionListener(e -> handleAction(name));
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(5)); 
        }
        
        // Đẩy nút Exit xuống cuối
        sidebar.add(Box.createVerticalGlue()); 
        
        // Thêm nút Exit (riêng biệt)
        JButton exitBtn = new JButton("❌ Exit");
        exitBtn.setBackground(EXIT_COLOR);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        exitBtn.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        
        // Buộc mở rộng chiều rộng và căn trái
        exitBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        exitBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, exitBtn.getPreferredSize().height));
        
        final Color exitInitialColor = EXIT_COLOR;
        final Color exitHoverColor = EXIT_HOVER_COLOR;

        exitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ((JButton) evt.getSource()).setBackground(exitHoverColor);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ((JButton) evt.getSource()).setBackground(exitInitialColor);
            }
        });

        exitBtn.addActionListener(e -> handleAction("❌ Exit"));
        sidebar.add(exitBtn);


        // --- 3. Content Area (Dùng JTable và Log Console) ---
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20)); 
        contentPanel.setBackground(CONTENT_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // KHỐI 1: Bảng Dữ liệu (JTable)
        String[] columnNames = {"ID", "Khách hàng", "Giá cơ bản", "Tổng ngày", "Thành tiền", "Loại phòng"};
        tableModel = new DefaultTableModel(columnNames, 0) {
             @Override // Không cho phép chỉnh sửa trực tiếp trên bảng
             public boolean isCellEditable(int row, int column) { return false; }
        };
        roomTable = new JTable(tableModel);

        roomTable.setAutoCreateRowSorter(true); 
        roomTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomTable.setRowHeight(25);
        roomTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        roomTable.getTableHeader().setBackground(new Color(230, 230, 230)); 

        JScrollPane tableScrollPane = new JScrollPane(roomTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(HEADER_BG),
                "DỮ LIỆU PHÒNG", // Tiêu đề Data View
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16), HEADER_BG)); 
        
        // KHỐI 2: Log Console (JTextArea)
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setEditable(false);
        outputArea.setBackground(Color.WHITE); 
        outputArea.setForeground(OUTPUT_TEXT_COLOR);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        JScrollPane logScrollPane = new JScrollPane(outputArea);
        logScrollPane.setPreferredSize(new Dimension(800, 120)); // Cố định chiều cao cho log
        logScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(OUTPUT_TEXT_COLOR),
            "LOG CONSOLE", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("SansSerif", Font.BOLD, 12), OUTPUT_TEXT_COLOR)); 

        // Gộp 2 khối vào một Panel trung tâm (Table ở trên, Log ở dưới)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        centerPanel.add(logScrollPane, BorderLayout.SOUTH);
        centerPanel.setBackground(CONTENT_BG);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Add vào layout
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(sidebar, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // --- PHƯƠNG THỨC HỖ TRỢ SIDEBAR ---
    private JLabel createGroupTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(this.GROUP_TITLE_COLOR); 
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        label.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height)); 
        return label;
    }
    // ----------------------------------------------------------------


    // ---------- Helpers for dialogs ----------
    // Create a date spinner with initial value now
    private JSpinner createDateSpinner(Date initial) {
        JSpinner spinner = new JSpinner(new SpinnerDateModel(initial, null, null, java.util.Calendar.MINUTE));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy HH:mm");
        spinner.setEditor(editor);
        return spinner;
    }

    // Build and show add-room dialog (VIP or Normal)
    private Room showAddRoomDialog(boolean vip) {
        JPanel panel = new JPanel(new GridBagLayout());
        
        // Thêm TitledBorder cho Dialog
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_BG, 1),
            vip ? "NHẬP THÔNG TIN PHÒNG VIP" : "NHẬP THÔNG TIN PHÒNG THƯỜNG",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 16), HEADER_BG));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField baseCostField = new JTextField(10);
        JSpinner checkinSpinner = createDateSpinner(new Date());
        JSpinner checkoutSpinner = createDateSpinner(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000)); // +1 day

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Room ID:"), gbc);
        gbc.gridx = 1; panel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Base Cost per Day:"), gbc);
        gbc.gridx = 1; panel.add(baseCostField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Check-in (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 1; panel.add(checkinSpinner, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Check-out (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 1; panel.add(checkoutSpinner, gbc);

        JCheckBox jacuzziBox = new JCheckBox("Has Jacuzzi");
        JCheckBox breakfastBox = new JCheckBox("Include Free Breakfast");
        JSpinner floorSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JCheckBox windowBox = new JCheckBox("Has Window");

        if (vip) {
            gbc.gridx = 0; gbc.gridy++; panel.add(jacuzziBox, gbc);
            gbc.gridx = 1; panel.add(breakfastBox, gbc);
        } else {
            gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Floor number:"), gbc);
            gbc.gridx = 1; panel.add(floorSpinner, gbc);
            gbc.gridx = 0; gbc.gridy++; panel.add(windowBox, gbc);
        }

        int result = JOptionPane.showConfirmDialog(this, panel,
                vip ? "Thêm Phòng VIP" : "Thêm Phòng Thường", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                double baseCost = Double.parseDouble(baseCostField.getText().trim());
                Date checkin = (Date) checkinSpinner.getValue();
                Date checkout = (Date) checkoutSpinner.getValue();
                if (checkout.before(checkin)) {
                    JOptionPane.showMessageDialog(this, "Check-out must be after check-in.");
                    return null;
                }

                if (vip) {
                    boolean jacuzzi = jacuzziBox.isSelected();
                    boolean breakfast = breakfastBox.isSelected();
                    return new VipRoom(id, name, baseCost, checkin, checkout, jacuzzi, breakfast);
                } else {
                    int floor = (Integer) floorSpinner.getValue();
                    boolean hasWindow = windowBox.isSelected();
                    return new NormalRoom(id, name, baseCost, checkin, checkout, floor, hasWindow);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format: " + ex.getMessage());
                return null;
            }
        }
        return null;
    }

    // Xây dựng and hiển thị update dialog for a found room (edit fields)
    private boolean showUpdateDialog(Room room) {
        if (room == null) {
            return false;
        }
        JPanel panel = new JPanel(new GridBagLayout());
        
        // Thêm TitledBorder cho Dialog
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_BG, 1),
            "CẬP NHẬT PHÒNG: " + room.getId(),
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 16), HEADER_BG));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(room.getName(), 15);
        JTextField baseCostField = new JTextField(String.valueOf(room.getBaseCost()), 10);
        Date ci = room.getCheckinDate() == null ? new Date() : room.getCheckinDate();
        Date co = room.getCheckoutDate() == null ? new Date(System.currentTimeMillis() + 24 * 3600 * 1000) : room.getCheckoutDate();
        JSpinner checkinSpinner = createDateSpinner(ci);
        JSpinner checkoutSpinner = createDateSpinner(co);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Room ID:"), gbc);
        gbc.gridx = 1; panel.add(new JLabel(room.getId()), gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Base Cost per Day:"), gbc);
        gbc.gridx = 1; panel.add(baseCostField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Check-in (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 1; panel.add(checkinSpinner, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Check-out (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 1; panel.add(checkoutSpinner, gbc);

        JCheckBox jacuzziBox = new JCheckBox("Has Jacuzzi");
        JCheckBox breakfastBox = new JCheckBox("Include Free Breakfast");
        JSpinner floorSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JCheckBox windowBox = new JCheckBox("Has Window");

        // subtype specific
        if (room instanceof VipRoom) {
            VipRoom v = (VipRoom) room;
            jacuzziBox.setSelected(v.isHasJacuzzi());
            breakfastBox.setSelected(v.isFreeBreakfast());
            gbc.gridx = 0; gbc.gridy++; panel.add(jacuzziBox, gbc);
            gbc.gridx = 1; panel.add(breakfastBox, gbc);
        } else if (room instanceof NormalRoom) {
            NormalRoom n = (NormalRoom) room;
            floorSpinner.setValue(n.getFloorNumber());
            windowBox.setSelected(n.isHasWindow());
            gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Floor number:"), gbc);
            gbc.gridx = 1; panel.add(floorSpinner, gbc);
            gbc.gridx = 0; gbc.gridy++; panel.add(windowBox, gbc);
        }

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Room: " + room.getId(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                room.setName(nameField.getText().trim());
                room.setBaseCost(Double.parseDouble(baseCostField.getText().trim()));
                Date checkin = (Date) checkinSpinner.getValue();
                Date checkout = (Date) checkoutSpinner.getValue();
                if (checkout.before(checkin)) {
                    JOptionPane.showMessageDialog(this, "Check-out must be after check-in.");
                    return false;
                }
                room.setCheckinDate(checkin);
                room.setCheckoutDate(checkout);

                if (room instanceof VipRoom) {
                    VipRoom v = (VipRoom) room;
                    v.setHasJacuzzi(jacuzziBox.isSelected());
                    v.setFreeBreakfast(breakfastBox.isSelected());
                } else if (room instanceof NormalRoom) {
                    NormalRoom n = (NormalRoom) room;
                    n.setFloorNumber((Integer) floorSpinner.getValue());
                    n.setHasWindow(windowBox.isSelected());
                }
                return true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format: " + ex.getMessage());
                return false;
            }
        }
        return false;
    }

    // --- PHƯƠNG THỨC MỚI: CẬP NHẬT JTABLE ---
    private void updateRoomTable(boolean includeDates) {
        if (tableModel == null) return;
        
        tableModel.setRowCount(0); 

        java.util.List<Room> rooms = roomList.rl.stream().collect(Collectors.toList());
        
        if (includeDates) {
            // Sắp xếp theo ngày check-in nếu là "Show With Dates"
            rooms.sort(Comparator.comparing(Room::getCheckinDate, Comparator.nullsLast(Comparator.naturalOrder())));
        }

        for (Room r : rooms) {
            String type = (r instanceof VipRoom) ? "VIP" : "Normal";
            tableModel.addRow(new Object[]{
                r.getId(),
                r.getName(),
                String.format("%.2f", r.getBaseCost()), // Base Cost
                r.calculateDaysOfStay(),
                String.format("%.2f", r.calculateCost()), // Final Cost
                type
            });
        }
    }

    // ---------- Handle sidebar actions ----------
    private void handleAction(String action) {
        // Cập nhật JTable cho các thao tác hiển thị
        if (action.equals("📋 Show All")) {
            updateRoomTable(false); // Không sắp xếp theo ngày
            outputArea.setText("✅ Dữ liệu tất cả phòng đã được tải vào bảng 'DỮ LIỆU PHÒNG'.");
            return;
        }
        if (action.equals("🗓 Show With Dates")) {
             updateRoomTable(true); // Sắp xếp theo ngày
             outputArea.setText("✅ Dữ liệu phòng đã được tải và sắp xếp theo ngày Check-in.");
             return;
        }

        switch (action) {
            case "➕ Add Room": {
                 String[] options = {"VIP Room", "Normal Room"};
                 int type = JOptionPane.showOptionDialog(this,
                         "Chọn loại phòng để thêm:", "Thêm Phòng Mới",
                         JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                         null, options, options[0]);
 
                 if (type == 0) { // VIP
                     Room vip = showAddRoomDialog(true);
                     if (vip != null) {
                         roomList.addRoom(vip);
                         outputArea.setText("✅ Added VIP Room: " + vip.getId() + " | " + vip.getName());
                         updateRoomTable(false);
                     }
                 } else if (type == 1) { // Normal
                     Room normal = showAddRoomDialog(false);
                     if (normal != null) {
                         roomList.addRoom(normal);
                         outputArea.setText("✅ Added Normal Room: " + normal.getId() + " | " + normal.getName());
                         updateRoomTable(false);
                     }
                 }
                 break;
            }
            case "✏️ Update Room": {
                String uid = JOptionPane.showInputDialog(this, "Enter Room ID to update:");
                if (uid == null || uid.trim().isEmpty()) { break; }
                Room r = roomList.findRoomById(uid.trim());
                if (r == null) {
                    outputArea.setText("❌ Room not found!");
                } else {
                    boolean ok = showUpdateDialog(r);
                    if (ok) {
                        outputArea.setText("✅ Room updated: " + r.getId());
                        updateRoomTable(false);
                    } else {
                        outputArea.setText("⚠️ Update cancelled or error");
                    }
                }
                break;
            }
            case "🗑 Delete Room": {
                String did = JOptionPane.showInputDialog(this, "Enter Room ID to delete:");
                if (did == null || did.trim().isEmpty()) { break; }
                boolean removed = roomList.deleteRoomById(did.trim());
                outputArea.setText(removed ? "🗑 Room deleted: " + did : "❌ Room not found!");
                if (removed) { updateRoomTable(false); }
                break;
            }
            case "🔍 Find Room": {
                String fid = JOptionPane.showInputDialog(this, "Enter Room ID to find:");
                if (fid == null || fid.trim().isEmpty()) { break; }
                Room found = roomList.findRoomById(fid.trim());
                if (found != null) {
                    StringBuilder sb = new StringBuilder("✅ Found Room:\n");
                    sb.append("ID: ").append(found.getId()).append(" | Name: ").append(found.getName()).append("\n");
                    sb.append("Base Cost: ").append(String.format("%.2f", found.getBaseCost())).append("\n");
                    sb.append("Check-in: ").append(found.getCheckinDate() == null ? "N/A" : sdf.format(found.getCheckinDate())).append("\n");
                    sb.append("Check-out: ").append(found.getCheckoutDate() == null ? "N/A" : sdf.format(found.getCheckoutDate())).append("\n");
                    sb.append("Final Cost: ").append(String.format("%.2f", found.calculateCost())).append("\n");
                    outputArea.setText(sb.toString());
                    // Có thể highlight hàng trong JTable nếu bạn muốn UX cao hơn
                } else {
                    outputArea.setText("❌ Room not found!");
                }
                break;
            }
            case "💰 Most Expensive": {
                Room expensive = roomList.findMostExpensiveRoom();
                if (expensive != null) {
                    outputArea.setText("💰 Most Expensive Room:\n"
                            + "ID: " + expensive.getId() + "\n"
                            + "Name: " + expensive.getName() + "\n"
                            + "Final Cost: " + String.format("%.2f", expensive.calculateCost()));
                } else {
                    outputArea.setText("❌ No rooms found!");
                }
                break;
            }
            case "📊 Count Rooms": {
                int vip = 0, normal = 0;
                for (Room r : roomList.rl) {
                    if (r instanceof VipRoom) { vip++; } else if (r instanceof NormalRoom) { normal++; }
                }
                outputArea.setText("📊 Room Count:\nVIP Rooms: " + vip + "\nNormal Rooms: " + normal);
                break;
            }
            case "❌ Exit":
                System.exit(0);
                break;
        }
    }

    public static void main(String[] args) {
        // Sử dụng Nimbus Look and Feel để có giao diện hiện đại hơn
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> new HotelManagementUI().setVisible(true));
    }

}