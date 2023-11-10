package MyUIFrame;

import MyUtil.EnterUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RegitUI extends JFrame {
    private static RegitUI instance;

    public static RegitUI getInstance() {
        if (instance == null) {
            instance = new RegitUI();
        }
        return instance;
    }

    public void closeInterface() {
        this.setVisible(false); // 隐藏当前界面
    }

    public void showInterface() {
        // 清空账号和密码文本框
        accountField.setText("");
        newPasswordField.setText("");
        repeatPasswordField.setText("");
        // 设置角色下拉框默认选项
        roleComboBox.setSelectedIndex(0);

        this.setVisible(true); // 显示当前界面
    }

    private JTextField accountField;
    private JPasswordField newPasswordField;
    private JPasswordField repeatPasswordField;
    private JComboBox<String> roleComboBox;

    private RegitUI() {
        initView();
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // 账号标签
        JLabel accountLabel = new JLabel("账号:");
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(accountLabel, constraints);

        // 账号输入框
        accountField = new JTextField(20);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(accountField, constraints);

        // 新密码标签
        JLabel newPasswordLabel = new JLabel("新密码:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(newPasswordLabel, constraints);

        // 新密码输入框
        newPasswordField = new JPasswordField(20);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(newPasswordField, constraints);

        // 重复密码标签
        JLabel repeatPasswordLabel = new JLabel("重复密码:");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(repeatPasswordLabel, constraints);

        // 重复密码输入框
        repeatPasswordField = new JPasswordField(20);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(repeatPasswordField, constraints);

        // 角色标签
        JLabel roleLabel = new JLabel("角色:");
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(roleLabel, constraints);

        // 角色下拉框
        String[] roles = {"普通用户", "管理员"};
        roleComboBox = new JComboBox<>(roles);
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(roleComboBox, constraints);

        // 注册按钮
        JButton registerButton = new JButton("确定");
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        panel.add(registerButton, constraints);

        // 注册按钮点击事件
        registerButton.addActionListener(e -> {
            String username = accountField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            String repeatPassword = new String(repeatPasswordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            System.out.println("账号: " + username);
            System.out.println("新密码: " + newPassword);
            System.out.println("重复密码: " + repeatPassword);
            System.out.println("角色: " + role);

            if (!newPassword.equals(repeatPassword)) {
                showDialog("新密码和重复密码不匹配");
                return;
            }

            // 执行注册逻辑
            EnterUtil.EnterStart(username, newPassword, Objects.equals(role, "管理员") ? 1 : 0,1);
        });

        // 将面板添加到窗口中
        add(panel);

        setTitle("注册");  // 设置窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置窗口关闭操作
        pack();  // 自适应大小
        setLocationRelativeTo(null);  // 窗口居中显示
        setVisible(true);  // 显示窗口
    }

    private void initView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // 设置外观样式为系统外观
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 弹窗
    public static void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}