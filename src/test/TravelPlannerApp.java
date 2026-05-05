package test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TravelPlannerApp extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;

    public TravelPlannerApp() {
        setTitle("🌈 My Super Cute Travel Planner ✈️");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 메인 캔디 그라데이션 패널
        JPanel contentPane = new CandyGradientPanel();
        contentPane.setLayout(new BorderLayout(25, 25));
        contentPane.setBorder(new EmptyBorder(30, 30, 30, 30));
        setContentPane(contentPane);

        // 상단 헤더: 이모티콘 듬뿍! (커스텀 섀도우 라벨 사용)
        TitleLabel titleLabel = new TitleLabel("✨ 𝓅𝓁𝒶𝓃 𝓎𝑜𝓊𝓇 𝓂𝒶𝑔𝒾𝒸 𝓉𝓇𝒾𝓅 🌈 🎒");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 42));
        add(titleLabel, BorderLayout.NORTH);

        // 메인 컨텐츠 영역
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 30, 0));
        mainContent.setOpaque(false);

        // 1. 귀여운 체크리스트 패널
        CutePanel prepPanel = new CutePanel(new Color(255, 255, 255, 220));
        prepPanel.setLayout(new BoxLayout(prepPanel, BoxLayout.Y_AXIS));
        prepPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel prepTitle = new JLabel("🍭 Packing List 🧸");
        prepTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        prepTitle.setForeground(new Color(255, 105, 180)); // Hot Pink
        prepPanel.add(prepTitle);
        prepPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        addCutePrepGroup(prepPanel, "👕 𝓒𝓵𝓸𝓽𝓱𝓮𝓼", new String[]{"🧦 Socks", "👕 T-shirts", "👖 Jeans"});
        prepPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addCutePrepGroup(prepPanel, "🪥 𝓣𝓸𝓲𝓵𝓮𝓽𝓻𝓲𝓮𝓼", new String[]{"🪥 Toothbrush", "🧴 Sunscreen", "🧼 Soap"});
        prepPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addCutePrepGroup(prepPanel, "🔌 𝓖𝓪𝓭𝓰𝓮𝓽𝓼", new String[]{"🔋 Charger", "💻 Laptop", "🎧 AirPods"});

        mainContent.add(new JScrollPane(prepPanel) {{
            setOpaque(false);
            getViewport().setOpaque(false);
            setBorder(null);
        }});

        // 2. 화려한 일정 패널
        CutePanel schedulePanel = new CutePanel(new Color(255, 255, 255, 220));
        schedulePanel.setLayout(new BorderLayout(15, 15));
        schedulePanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel scheduleTitle = new JLabel("📅 Adventure Log 🗺️");
        scheduleTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        scheduleTitle.setForeground(new Color(30, 144, 255)); // Dodger Blue
        schedulePanel.add(scheduleTitle, BorderLayout.NORTH);

        // 일정 데이터
        String[] columnNames = {"🕒 Date", "🎭 Activity", "📝 Memo"};
        Object[][] data = {
                {"2022-11-07", "🛫 Departure Flight", "Don't forget mask! 😷"},
                {"2022-11-08", "🍂 Central Park", "Rent a bike 🚲"},
                {"2022-11-09", "🍕 Joe's Pizza", "Delicious! 😋"},
                {"2022-11-11", "🗽 Statue of Liberty", "Ferry ride ⛴️"},
                {"2022-11-12", "🥪 Katz's Deli", "Pastrami Power! 💪"},
                {"2022-11-13", "🎵 Blue Note Club", "Jazz night 🎷"},
                {"2022-11-14", "🍽️ Keens Steak", "Fancy dinner 🍷"},
                {"2022-11-15", "🛬 Arrival Flight", "Home sweet home 🏠"},
                {"Mon 12:00", "🇰🇷 Seoul Arrival", "Korean Air ✈️"}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        styleCuteTable(table);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setOpaque(false);
        tableScroll.getViewport().setOpaque(false);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 2)); // Pink border
        schedulePanel.add(tableScroll, BorderLayout.CENTER);

        // CRUD 버튼: 더욱 크고 화려하게!
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setOpaque(false);

        JButton addButton = createSuperCuteButton("➕ Add Magic", new Color(50, 205, 50)); // Lime Green
        JButton editButton = createSuperCuteButton("✏️ Edit Plan", new Color(255, 165, 0)); // Orange
        JButton deleteButton = createSuperCuteButton("🗑️ Remove", new Color(255, 69, 0)); // Red Orange

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteSelectedRow());

        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        schedulePanel.add(actionPanel, BorderLayout.SOUTH);

        mainContent.add(schedulePanel);
        add(mainContent, BorderLayout.CENTER);

        // 하단 문구
        JLabel footerLabel = new JLabel("💖 Handcrafted for your amazing journey 💖", JLabel.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
        add(footerLabel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addCutePrepGroup(JPanel panel, String title, String[] items) {
        JLabel groupLabel = new JLabel(title);
        groupLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        groupLabel.setForeground(new Color(100, 100, 100));
        groupLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(groupLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (String item : items) {
            JCheckBox cb = new JCheckBox(item);
            cb.setOpaque(false);
            cb.setFont(new Font("SansSerif", Font.PLAIN, 15));
            cb.setForeground(new Color(80, 80, 80));
            cb.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(cb);
        }
    }

    private void styleCuteTable(JTable table) {
        table.setRowHeight(45);
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(255, 240, 245)); // Lavender Blush
        table.getTableHeader().setForeground(new Color(219, 112, 147)); // Pale Violet Red
        table.setSelectionBackground(new Color(255, 192, 203)); // Pink
        table.setSelectionForeground(Color.DARK_GRAY);
        table.setGridColor(new Color(255, 228, 225)); // Misty Rose
        table.setShowGrid(true);
    }

    private JButton createSuperCuteButton(String text, Color baseColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(baseColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // 마우스 호버 효과
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(baseColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(baseColor);
            }
        });
        
        return btn;
    }

    private void showAddDialog() {
        JTextField dateField = new JTextField();
        JTextField activityField = new JTextField();
        JTextField memoField = new JTextField();

        Object[] message = {
            "🕒 Date & Time:", dateField,
            "🎭 Activity Name:", activityField,
            "📝 Special Memo:", memoField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "🌟 Add a New Adventure! 🌟", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            tableModel.addRow(new Object[]{dateField.getText(), activityField.getText(), memoField.getText()});
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Oops! Please pick an activity to change! 🧚‍♀️");
            return;
        }

        JTextField dateField = new JTextField((String) tableModel.getValueAt(row, 0));
        JTextField activityField = new JTextField((String) tableModel.getValueAt(row, 1));
        JTextField memoField = new JTextField((String) tableModel.getValueAt(row, 2));

        Object[] message = {
            "🕒 Date & Time:", dateField,
            "🎭 Activity Name:", activityField,
            "📝 Special Memo:", memoField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "🌈 Update your Plan 🌈", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(dateField.getText(), row, 0);
            tableModel.setValueAt(activityField.getText(), row, 1);
            tableModel.setValueAt(memoField.getText(), row, 2);
        }
    }

    private void deleteSelectedRow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an activity to remove! 🧸");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this magic moment? 🥺", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
        }
    }

    // 선명하고 귀여운 캔디 그라데이션 배경
    class CandyGradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 핑크에서 보라로 흐르는 선명한 그라데이션
            GradientPaint gp = new GradientPaint(0, 0, new Color(255, 110, 196), 
                                                getWidth(), getHeight(), new Color(120, 115, 245));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // 부드럽고 선명한 흰색 둥근 패널
    class CutePanel extends JPanel {
        private Color bgColor;
        public CutePanel(Color color) {
            this.bgColor = color;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40));
            
            // 얇은 핑크 테두리 추가
            g2.setColor(new Color(255, 182, 193, 150));
            g2.setStroke(new BasicStroke(3));
            g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 40, 40));
            
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 그림자 효과가 있는 텍스트 라벨
    class TitleLabel extends JLabel {
        public TitleLabel(String text) {
            super(text);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // 그림자 그리기
            g2.setFont(getFont());
            g2.setColor(new Color(0, 0, 0, 60));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = fm.getAscent() + (getHeight() - fm.getHeight()) / 2;
            g2.drawString(getText(), x + 3, y + 3);
            
            // 본문 텍스트 그리기
            g2.setColor(getForeground());
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new TravelPlannerApp());
    }
}
