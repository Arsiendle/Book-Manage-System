package MyUIFrame;

import MyUtil.EnterUtil;

import javax.swing.*;
import java.awt.*;

import java.util.Objects;

public class LoginUI extends JFrame {
    private static LoginUI instance;

    public static LoginUI getInstance() {
        if (instance == null) {
            instance = new LoginUI();
        }
        return instance;
    }

    public void closeInterface() {
        this.setVisible(false); // 隐藏当前界面
    }

    public void showInterface() {
        // 清空账号和密码文本框
        usernameField.setText("");
        passwordField.setText("");
        // 设置角色下拉框默认选项
        roleComboBox.setSelectedIndex(0);

        this.setVisible(true); // 显示当前界面
    }


    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private LoginUI() {
        initView();
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // 账号标签
        JLabel usernameLabel = new JLabel("账号:");
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(usernameLabel, constraints);

        // 账号输入框
        usernameField = new JTextField(20);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(usernameField, constraints);

        // 密码标签
        JLabel passwordLabel = new JLabel("密码:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(passwordLabel, constraints);

        // 密码输入框
        passwordField = new JPasswordField(20);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(passwordField, constraints);

        // 角色标签
        JLabel roleLabel = new JLabel("角色:");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(roleLabel, constraints);

        // 角色下拉框
        String[] roles = {"普通用户", "管理员"};
        roleComboBox = new JComboBox<>(roles);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(roleComboBox, constraints);

        // 登录按钮
        JButton loginButton = new JButton("登录");
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(loginButton, constraints);

        // 登录按钮点击事件
        loginButton.addActionListener(e -> {
            enter(0);
        });

        // 修改密码按钮
        JButton forgotPasswordButton = new JButton("修改密码");
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(forgotPasswordButton, constraints);

        // 修改密码按钮点击事件
        forgotPasswordButton.addActionListener(e -> {
            enter(2);
            // 执行修改密码逻辑
            System.out.println("修改密码");
        });

        // 注册按钮
        JButton registerButton = new JButton("注册");
        constraints.gridx = 3;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(registerButton, constraints);

        // 注册按钮点击事件
        registerButton.addActionListener(e -> {
            // 执行注册逻辑
            this.closeInterface();
            RegitUI.getInstance().showInterface();
        });

        // 将面板添加到窗口中
        add(panel);

        setTitle("登录");  // 设置窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置窗口关闭操作
        pack();  // 自适应大小
        setLocationRelativeTo(null);  // 窗口居中显示
        setVisible(true);  // 显示窗口
    }

    private void enter(int flag) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        System.out.println("用户名: " + username);
        System.out.println("密码: " + password);
        System.out.println("角色: " + role);

        // 执行登录逻辑
        EnterUtil.EnterStart(username, password, Objects.equals(role, "管理员") ? 1 : 0,flag);
    }

    private void initView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // 设置外观样式为系统外观
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    弹窗
    public static void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
