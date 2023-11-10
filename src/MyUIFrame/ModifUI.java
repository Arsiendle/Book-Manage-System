package MyUIFrame;

import MyUtil.EnterUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ModifUI extends JFrame {
    private static ModifUI instance;

    public static ModifUI getInstance() {
        if (instance == null) {
            instance = new ModifUI();
        }
        return instance;
    }

    public void closeInterface() {
        this.setVisible(false); // 隐藏当前界面
    }

    public void showInterface() {
        // 清空账号和密码文本框
        newPasswordField.setText("");
        repeatPasswordField.setText("");

        this.setVisible(true); // 显示当前界面
    }

    private JTextField newPasswordField;
    private JPasswordField repeatPasswordField;

    private ModifUI() {
        initView();
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);


        // 新密码标签
        JLabel newPasswordLabel = new JLabel("新密码:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(newPasswordLabel, constraints);

        // 新密码输入框
        newPasswordField = new JTextField(20);
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

        // 注册按钮
        JButton registerButton = new JButton("确定");
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        panel.add(registerButton, constraints);

        // 注册按钮点击事件
        registerButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getText());
            String repeatPassword = new String(repeatPasswordField.getPassword());

            System.out.println("新密码: " + newPassword);
            System.out.println("重复密码: " + repeatPassword);

            if (Objects.equals(newPassword, "")) {
                showDialog("新密码不能为空");
                return;
            } else if (Objects.equals(repeatPassword, "")) {
                showDialog("重复密码不能为空");
                return;
            } else if (!newPassword.equals(repeatPassword)) {
                showDialog("新密码和重复密码不匹配");
                return;
            }

            // 执行修改密码逻辑
            EnterUtil.modifStart(newPassword);
        });

        // 将面板添加到窗口中
        add(panel);

        setTitle("修改密码");  // 设置窗口标题
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