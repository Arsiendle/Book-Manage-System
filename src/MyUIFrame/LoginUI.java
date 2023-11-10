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
        this.setVisible(false); // ���ص�ǰ����
    }

    public void showInterface() {
        // ����˺ź������ı���
        usernameField.setText("");
        passwordField.setText("");
        // ���ý�ɫ������Ĭ��ѡ��
        roleComboBox.setSelectedIndex(0);

        this.setVisible(true); // ��ʾ��ǰ����
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

        // �˺ű�ǩ
        JLabel usernameLabel = new JLabel("�˺�:");
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(usernameLabel, constraints);

        // �˺������
        usernameField = new JTextField(20);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(usernameField, constraints);

        // �����ǩ
        JLabel passwordLabel = new JLabel("����:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(passwordLabel, constraints);

        // ���������
        passwordField = new JPasswordField(20);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(passwordField, constraints);

        // ��ɫ��ǩ
        JLabel roleLabel = new JLabel("��ɫ:");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(roleLabel, constraints);

        // ��ɫ������
        String[] roles = {"��ͨ�û�", "����Ա"};
        roleComboBox = new JComboBox<>(roles);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(roleComboBox, constraints);

        // ��¼��ť
        JButton loginButton = new JButton("��¼");
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(loginButton, constraints);

        // ��¼��ť����¼�
        loginButton.addActionListener(e -> {
            enter(0);
        });

        // �޸����밴ť
        JButton forgotPasswordButton = new JButton("�޸�����");
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(forgotPasswordButton, constraints);

        // �޸����밴ť����¼�
        forgotPasswordButton.addActionListener(e -> {
            enter(2);
            // ִ���޸������߼�
            System.out.println("�޸�����");
        });

        // ע�ᰴť
        JButton registerButton = new JButton("ע��");
        constraints.gridx = 3;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(registerButton, constraints);

        // ע�ᰴť����¼�
        registerButton.addActionListener(e -> {
            // ִ��ע���߼�
            this.closeInterface();
            RegitUI.getInstance().showInterface();
        });

        // �������ӵ�������
        add(panel);

        setTitle("��¼");  // ���ô��ڱ���
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ���ô��ڹرղ���
        pack();  // ����Ӧ��С
        setLocationRelativeTo(null);  // ���ھ�����ʾ
        setVisible(true);  // ��ʾ����
    }

    private void enter(int flag) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        System.out.println("�û���: " + username);
        System.out.println("����: " + password);
        System.out.println("��ɫ: " + role);

        // ִ�е�¼�߼�
        EnterUtil.EnterStart(username, password, Objects.equals(role, "����Ա") ? 1 : 0,flag);
    }

    private void initView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // ���������ʽΪϵͳ���
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    ����
    public static void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
