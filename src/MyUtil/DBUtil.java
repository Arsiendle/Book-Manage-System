package MyUtil;

import MyJavabean.Book;
import MyJavabean.User;
import MyUIFrame.*;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/*
 * @Description: ���ݿ���ع��ܵĹ�����
 */
@SuppressWarnings("all")
public final class DBUtil {

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306" +
            "/libms?characterEncoding=utf8&useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    public static String tableName = "Nobody";
    public static boolean isFind = false;

    private DBUtil() {
    }

    public static void dbEnterMatch(User input) {
        PreparedStatement preparedStatement = null;
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL ��ѯ
            String querynobody = "SELECT password FROM " + "nobody" + " WHERE username = ?";
            String queryadmin = "SELECT password FROM " + "admin" + " WHERE username = ?";

            // ����ִ�� SQL ��������
            if (input.getType() == 1) {
                preparedStatement = connection.prepareStatement(queryadmin);
            } else {
                preparedStatement = connection.prepareStatement(querynobody);
            }


            // ���ò���
            preparedStatement.setString(1, input.getName());

            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();

            // ������������������
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                if (!password.equals(input.getPassword())) {
                    System.out.println("�������,�����µ���");
                    LoginUI.showDialog("�������");
                } else {
                    switch (ComUtil.flag) {
                        case 0: {
                            if (input.getType() == 1) {
                                System.out.println("�𾴵Ĺ���Ա,��ϲ����½�ɹ�");
                                LoginUI.getInstance().closeInterface();
                                input.setIslogin(true);
                                UserUI.launch(UserUI.class);
                            } else {
                                System.out.println("�𾴵��û�,��ϲ����½�ɹ�");
                                LoginUI.getInstance().closeInterface();
                                input.setIslogin(true);
                                NobodyUI.launch(NobodyUI.class);
                            }
                        }
                        break;

                        case 2: {
                            System.out.println("�˺�������ȷ");
                            LoginUI.showDialog("��������\"�޸�����\"����");
                            LoginUI.getInstance().closeInterface();
                            ModifUI.getInstance().showInterface();
                        }
                        break;
                        default:
                            System.out.println("���ݴ���");
                            break;
                    }

                }
            } else {
                System.out.println("�˺Ų�����");
                input.setIslogin(false);
                LoginUI.showDialog("�˺Ų�����");
            }


            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dbRegitMatch(User input) {
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL ��ѯ
            if (input.getType() == 1) {
                tableName = "admin";
            } else {
                tableName = "nobody";
            }
            String query = "SELECT password FROM " + tableName + " WHERE username = ?";

            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // ���ò���
            preparedStatement.setString(1, input.getName());

            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();

            // ������������������
            if (resultSet.next()) {
                System.out.println("�˺��Ѵ���");
                LoginUI.showDialog("�˺��Ѵ���");
            } else {
                // ���� SQL �������
                String queryInsert = "INSERT INTO " + tableName + " (username, password) VALUES (?, ?)";
                // ����ִ�� SQL ��������
                preparedStatement = connection.prepareStatement(queryInsert);

                preparedStatement.setString(1, input.getName());
                preparedStatement.setString(2, input.getPassword());

                // ִ�в������
                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("ע��ɹ���");
                    LoginUI.showDialog("ע��ɹ�");
                    RegitUI.getInstance().closeInterface();
                    LoginUI.getInstance().showInterface();
                } else {
                    System.out.println("ע��ʧ�ܡ�");
                    LoginUI.showDialog("ע��ʧ��");
                }
            }


            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dbModifMatch(User input) {
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL ��ѯ
            String query = "UPDATE " + tableName + " SET password = ? WHERE username = ?";

            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // ���ò���
            preparedStatement.setString(1, input.getPassword());
            preparedStatement.setString(2, input.getName());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("�޸ĳɹ���");
                ModifUI.showDialog("�޸ĳɹ�");
                ModifUI.getInstance().closeInterface();
                LoginUI.getInstance().showInterface();
            } else {
                System.out.println("�޸�ʧ�ܡ�");
                ModifUI.showDialog("�޸�ʧ��");
            }

            // �ر����Ӻ������Դ
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * ¼������ķ���
     *
     * @param user
     * @param book
     */
    public static void dbcollectMatch(User user, Book book) {
        int flag = 1;
        tableName = "book";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL ��ѯ
            String query = "SELECT ISBN FROM " + tableName;

            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();

            // ������������������
            while (resultSet.next()) {
                if (book.getISBN().equals(resultSet.getString(1))) {
                    UserUI.showDialog("��ͼ���Ѵ���");
                    flag = 0;
                    break;
                }
            }

            if (flag == 1) {
                // ���� SQL �������
                String queryInsert = "INSERT INTO " + tableName + " (ISBN, Category_number,Name,Category,price,number) VALUES (?,?,?,?,?,?)";
                // ����ִ�� SQL ��������
                preparedStatement = connection.prepareStatement(queryInsert);

                preparedStatement.setString(1, book.getISBN());
                preparedStatement.setString(2, book.getCategory_number());
                preparedStatement.setString(3, book.getName());
                preparedStatement.setString(4, book.getCategory());
                preparedStatement.setDouble(5, book.getPrice());
                preparedStatement.setInt(6, book.getNumber());

                // ִ�в������
                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    UserUI.showDialog("¼������ɹ�");
                } else {
                    UserUI.showDialog("¼������ʧ��");
                }
            }
            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ISBN����ͼ��
     *
     * @param book
     * @param ISBN
     */
    public static void dbSelectBookMatch(Book book, String ISBN) {
        int flag = 0;
        tableName = "book";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL �޸�
            String query = "SELECT ISBN FROM " + tableName;
            String queryinfo = "select * from book where ISBN=" + ISBN;
            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement ps = connection.prepareStatement(queryinfo);

            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            // ������������������
            while (resultSet.next()) {
                if (ISBN.equals(resultSet.getString(1))) {
                    System.out.println("����Ѱ����ͼ��,��ͼ�����ϢΪ");
                    System.out.println("ISBN " + rs.getString(1));
                    System.out.println("Category_number " + rs.getString(2));
                    System.out.println("Name " + rs.getString(3));
                    System.out.println("Category " + rs.getString(4));
                    System.out.println("price " + rs.getString(5));
                    System.out.println("number " + rs.getString(6));
                    flag = 1;
                    isFind = true;
                    break;
                }
            }
            if (flag == 0) {
                System.out.println("δ�ҵ���ͼ��");
                isFind = false;
            }
            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ID��ѯͼ��
     *
     * @param book
     * @param ISBN
     * @return �����, ����һ���ַ���
     */
    public static String dbSelectBookById(Book book, String ISBN) {
        StringBuilder stringBuilder = new StringBuilder();
        int flag = 0;
        tableName = "book";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL �޸�
            String query = "SELECT ISBN FROM " + tableName;
            String queryinfo = "select * from book where ISBN=" + ISBN;
            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement ps = connection.prepareStatement(queryinfo);

            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            // ������������������
            while (resultSet.next()) {
                if (ISBN.equals(resultSet.getString(1))) {
                    stringBuilder.append("ISBN " + rs.getString(1) +
                            "\n");
                    stringBuilder.append("Category_number " + rs.getString(2) + "\n");
                    stringBuilder.append("Name " + rs.getString(3) + "\n");
                    stringBuilder.append("Category " + rs.getString(4) + "\n");
                    stringBuilder.append("price " + rs.getString(5) + "\n");
                    stringBuilder.append("number " + rs.getString(6) + "\n");
                    flag = 1;
                    isFind = true;
                    break;
                }
            }
            if (flag == 0) {
                System.out.println("δ�ҵ���ͼ��");
                isFind = false;
            }
            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String[] dbSelectBookByIdshuzu(Book book,
                                                 String ISBN) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] s = new String[6];
        int flag = 0;
        tableName = "book";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL �޸�
            String query = "SELECT ISBN FROM " + tableName;
            String queryinfo = "select * from book where ISBN=" + ISBN;
            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement ps = connection.prepareStatement(queryinfo);

            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            // ������������������
            while (resultSet.next()) {
                if (ISBN.equals(resultSet.getString(1))) {
                    s[0] = (" " + rs.getString(1) +
                            "\n");
                    s[1] = (" " + rs.getString(2) +
                            "\n");
                    s[2] = (" " + rs.getString(3) + "\n");
                    s[3] = (" " + rs.getString(4) + "\n");
                    s[4] = (" " + rs.getString(5) + "\n");
                    s[5] = (" " + rs.getString(6) + "\n");
                    flag = 1;
                    isFind = true;
                    break;
                }
            }
            if (flag == 0) {
                System.out.println("δ�ҵ���ͼ��");
                isFind = false;
            }
            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int dbSelectBookNumMatch(Book book, String ISBN) {
        // �����������
        int num = 0;
        int flag = 0;
        tableName = "book";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL �޸�
            String query = "SELECT ISBN FROM " + tableName;
            String queryinfo = "select number from book where ISBN=" + ISBN;
            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement ps = connection.prepareStatement(queryinfo);

            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet rs = ps.executeQuery();
            // ������������������
            while (resultSet.next()) {
                if (ISBN.equals(resultSet.getString("ISBN"))) {
                    while (rs.next()) {
                        num = rs.getInt("number");
                        flag = 1;
                        break;
                    }
                }
            }
            if (flag == 0) {
                num = -1;
            }
            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static int dbSelectBorrowedBookNumMatch(Book book, String ISBN) {
        /*
          �˷������ڲ�ѯ���ȥ���������
         */
        // �����������
        int num = 0;
        int flag = 0;
        tableName = "borrowedbook";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL �޸�
            String query = "SELECT * FROM " + tableName + " WHERE ISBN=" + ISBN;
            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();
            // ������������������
            while (resultSet.next()) {
                for (int i = 0; i < 9; i++) {
                    if (resultSet.getString(flag + 1) != null) {
                        flag++;
                    } else {
                        num = flag / 2;
                    }
                }
            }
            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static void dbUpdateBookMatch(Book book, String ISBN) throws SQLException {
        tableName = "book";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);
            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
// ���� SQL �޸�
            String update = "UPDATE book SET ISBN=?," +
                    "Category_number=?,Name=?,Category=?,price=?" +
                    "where ISBN=" + ISBN;
// ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(update);
// ���ò���ֵ

            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, book.getCategory_number());
            preparedStatement.setString(3, book.getName());
            preparedStatement.setString(4, book.getCategory());
            preparedStatement.setDouble(5, book.getPrice());
// ִ�и���
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate == 0) {
                UserUI.showDialog("�޸�ʧ��");
            } else {
                UserUI.showDialog("�޸ĳɹ�");
            }
            // �ر����Ӻ������Դ
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dbChangeBookMatch(Book book, String ISBN, int option) {
        tableName = "book";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

// ���� SQL �޸�
            String update = "UPDATE book SET ";
            String columnName = "";
            switch (option) {
                case 1:
                    columnName = "Category_number";
                    break; // ������
                case 2:
                    columnName = "Name";
                    break; // ����
                case 3:
                    columnName = "Category";
                    break; // ������
                case 4:
                    columnName = "Price";
                    break; // �۸�
                case 5:
                    columnName = "Number";
                    break; // ����
            }

            update += columnName + "=? WHERE ISBN=" + ISBN;

// ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(update);

// ���ò���ֵ
            switch (option) {
                case 1:
                    preparedStatement.setString(1, book.getCategory_number());
                    break;
                case 2:
                    preparedStatement.setString(1, book.getName());
                    break;
                case 3:
                    preparedStatement.setString(1, book.getCategory());
                    break;
                case 4:
                    preparedStatement.setDouble(1, book.getPrice());
                    break;
                case 5:
                    preparedStatement.setInt(1, book.getNumber());
                    break;
            }

// ִ�и���
            preparedStatement.executeUpdate();


            // �ر����Ӻ������Դ
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /*������Ӵ��룬���ݱ��ISBN�����ݿ⣬�Ѷ�Ӧ���borrower��date��Ϣ��ֵ��book����ֵ
     * ������setBorrower(String[] borrower)�����飬Ҳ������setBorrower(int index,String borrower)��
     * ����String��ֵ��borrower[i]��dateͬ�����88��*/
    public static void dbChangeBookBDMatch(Book book, String ISBN) throws ParseException {
        tableName = "borrowedbook";
        int flag = 0;
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL �޸�
            String query = "SELECT ISBN FROM " + tableName;
            String queryinfo = "select * from borrowedbook where ISBN=" + ISBN;
            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement ps = connection.prepareStatement(queryinfo);

            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet rs = ps.executeQuery();
            rs.next();
            // ������������������
            while (resultSet.next()) {
                if (ISBN.equals(resultSet.getString(1))) {
                    for (int i = 0; i < 4; i++) {
                        String borrower = rs.getString(2 + i * 2);
                        if (!rs.wasNull()) {
                            // �����ȡ��ֵ��Ϊ null��������Ӧ�Ĵ���
                            book.setBorrower(i, borrower);
                            String dateStr = rs.getString(3 + i * 2);
                            if (!rs.wasNull()) {
                                // �����ȡ�������ַ�����Ϊ null������ת��Ϊ���ڶ���
                                book.setDate(i, DateUtil.toDate(dateStr));
                            }
                        }
                    }
                    flag = 1;
                    break;
                }
            }

            if (flag == 0) {
                String insert = "insert into borrowedbook (ISBN) values (?);";
                PreparedStatement pss = connection.prepareStatement(insert);
                pss.setString(1, ISBN);
                pss.executeUpdate();
                pss.close();
            }
            // �ر����Ӻ������Դ
            rs.close();
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dbUpdateBookBDMatch(Book book, String ISBN, int count) {
        tableName = "borrowedbook";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            for (int i = 0, j = 1; i <= count; i++, j++) {
                String updateBorrower = "update borrowedbook set borrower" + j + "=" + "? where isbn=" + ISBN;
                PreparedStatement pss1 = connection.prepareStatement(updateBorrower);
                pss1.setString(1, book.getBorrower(i));
                pss1.executeUpdate();
                pss1.close();
            }
            for (int i = 0, j = 1; i <= count; i++, j++) {
                String updateDate = "update borrowedbook set expireDate" + j + "=" + "? where isbn=" + ISBN;
                PreparedStatement pss2 = connection.prepareStatement(updateDate);
                pss2.setString(1, book.getDate(i));
                pss2.executeUpdate();
                pss2.close();
            }
            // �ر����Ӻ������Դ


            String updatenumber = "update book set number=number-1 where isbn=" + ISBN;
            PreparedStatement pss2 = connection.prepareStatement(updatenumber);
            pss2.executeUpdate();
            pss2.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean dbreturnbook(String name, String ISBN) {
        //         * �ڽ�������������������ڵ���һ��,�ҵ����û�
        //         * (û���ҵ�,��ʾ����δ��)
        // �����������
        int flag = 0;
        tableName = "borrowedbook";
        try {
            // �������ݿ���������
            Class.forName(DRIVER);

            // �������ݿ�����
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // ���� SQL �޸�
            String query = "SELECT ISBN FROM " + tableName + " WHERE ISBN=" + ISBN;
//            ������û���Ȿ��,�����Ȿ������û�������,������޸�
            String queryUser = "SELECT * from borrowedbook where ISBN=" + ISBN;
//            String update = "update borrowedbook set borrower1='null',expireDate1='2013-02-22' where ISBN="+ISBN;
            // ����ִ�� SQL ��������
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement statement = connection.prepareStatement(queryUser, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // ִ�в�ѯ����ȡ�����
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet query1 = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(flag + 1).equals(ISBN)) {
                    flag = 1;
                }
            }
            // ������������������
            if (flag == 1) {
                //�ҵ��Ȿ����
                //������û����ڵ��ֶ�
                while (query1.next()) {
                    for (int i = 1; i <= 9; i++) {
                        if (name.equals(query1.getString(i))) {
                            query1.updateString(i, null);
                            query1.updateString(i + 1, null);
                            query1.updateRow();
                            String updatenumber = "update book set number=number+1 where isbn=" + ISBN;
                            PreparedStatement pss2 = connection.prepareStatement(updatenumber);
                            pss2.executeUpdate();
                            pss2.close();
                            return true;
                        }
                    }
                }
            }
            //�ҵ��ֶ���,�޸����ֺ�����+1���ֶ�Ϊnull

            // �ر����Ӻ������Դ
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Book> dbshowinfo(User user) {
        ArrayList<String> arrayList = new ArrayList<String>();
        List<Book> bookList = new ArrayList<Book>();

        Book book1 = new Book();
        Book book2 = new Book();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            /*
              1.����һ���û���,��borrowedbook����������Ŀ�б�����ѯ����û���,�ҵ��˾ͼ�¼��ı��
              2.���ݼ�¼��������ı��,��book���в�ѯ��ȡ��������Ϣ���
             */
            String showsql = "SELECT * FROM borrowedbook";
            String showbook = "SELECT * FROM book WHERE ISBN=?";
            String[] allISBN = new String[100];
            String[] allExpiredDay = new String[100];
            int count = 0;
            preparedStatement = conn.prepareStatement(showsql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                for (int i = 2; i <= 9; i++) {
                    if (user.getName().equals(resultSet.getString(i))) {
                        allExpiredDay[count] =
                                resultSet.getString(i+1);
                        allISBN[count] = resultSet.getString(1);
                        count++;
                    }
                }
            }
            for (int i = 0; i < count; i++) {
                ps = conn.prepareStatement(showbook);
                ps.setString(1, allISBN[i]);
                rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.print(rs.getString(1) + "\t");//ISBN
                    System.out.print(rs.getString(3) + "\t");//name
                    System.out.print(rs.getString(4) + "\t");//catagory
                    System.out.println();
                    arrayList.add(rs.getString(1));
                    arrayList.add(rs.getString(3));
                    arrayList.add(rs.getString(4));
                }
            }
            System.out.println(arrayList);
            for (int i = 0; i < arrayList.size() / 3; i++) {
                Book book = new Book();
                book.setISBN(arrayList.get(i * 3));
                book.setName(arrayList.get(i * 3 + 1));
                book.setCategory(arrayList.get(i * 3 + 2));
                book.setExpireDate(allExpiredDay[i]);
                bookList.add(book);
            }
            System.out.println(bookList);
        } catch (NullPointerException exception) {
            throw exception;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        return bookList;
    }

    public static List<Book> dbshowallbook(User user) {
        /*
          �˷���������ʾ���е�ͼ����Ϣ
         */
        List<Book> bookList = new ArrayList<Book>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String showsql = "SELECT * FROM book";
            ps = conn.prepareStatement(showsql);
            rs = ps.executeQuery();
            System.out.println("ISBN" + "\t" + "catagory_number" + "\t" + "    name" + "\t\t\t" + "catagory" + "\t\t" + "price" + "\t\t" + "number");
            while (rs.next()) {
                Book book = new Book();
                String ISBN = rs.getString(1);
                String catagory_number = rs.getString(2);
                String name = rs.getString(3);
                String catagory = rs.getString(4);
                float price = rs.getFloat(5);
                int number = rs.getInt(6);
                book.setISBN(ISBN);
                book.setCategory_number(catagory_number);
                book.setName(name);
                book.setCategory(catagory);
                book.setPrice(price);
                book.setNumber(number);
                bookList.add(book);
                String ISBN_formatted = String.format("%-15s", ISBN);
                String catagory_number_formatted = String.format("%-10s", catagory_number);
                String name_formatted = String.format("%-15s", name);
                String catagory_formatted = String.format("%-15s", catagory);
                String price_formatted = String.format("%-10.2f", price);
                String number_formatted = String.format("%-10d", number);
//                System.out.print(ISBN+"\t"+catagory_number+"\t"+name+"\t\t"+catagory+"\t\t"+price+"\t"+number);
                System.out.print(ISBN_formatted + "\t" + catagory_number_formatted + "\t" + name_formatted + "\t" + catagory_formatted + "\t" + price_formatted + "\t" + number_formatted);
                System.out.println();
            }

        } catch (NullPointerException exception) {
            throw exception;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookList;
    }


    public static boolean dbrenew(User user, String ISBN) {
        int flag = 0;
        Connection connnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(DRIVER);
            connnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            /*
              1.�ȿ�����û�н������û���Ȿ��
              2.�ٿ����Ȿ������û�������
              3.������˵Ĺ���ʱ������һ��
             */
            // ���� SQL �޸�
            String query = "SELECT ISBN FROM borrowedbook" + " WHERE ISBN=" + ISBN;
//            ������û���Ȿ��,�����Ȿ������û�������,������޸�
            String queryUser = "SELECT * from borrowedbook where ISBN=" + ISBN;
            // ����ִ�� SQL ��������
            preparedStatement = connnection.prepareStatement(query);
            PreparedStatement statement = connnection.prepareStatement(queryUser, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // ִ�в�ѯ����ȡ�����
            resultSet = preparedStatement.executeQuery();
            ResultSet query1 = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(flag + 1).equals(ISBN)) {
                    flag = 1;
                }
            }
            // ������������������
            if (flag == 1) {
                //�ҵ��Ȿ����
                //������û����ڵ��ֶ�
                while (query1.next()) {
                    for (int i = 2; i <= 9; i++) {
                        if (user.getName().equals(query1.getString(i))) {
                            query1.updateString(i + 1, DateUtil.getNewReturnDate(query1.getString(i + 1)));
                            query1.updateRow();
                            return true;
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connnection != null) {
                    connnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}

