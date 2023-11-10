package MyJavabean;

import MyUIFrame.NobodyUI;
import MyUtil.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Book {

    Scanner sc = new Scanner(System.in);
    DateUtil dateUtil = new DateUtil();
    private static final int max_number = 4;

    private String ISBN;
    private String category_number;
    private String name;
    private String category;
    private double price;
    private int number;
    private String[] borrower = new String[max_number];
    private Date[] date = new Date[max_number];
    private String expireDate;

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    User user = null;

    public Book() {
    }

    public Book(String ISBN,String name,String category) {
        this.ISBN = ISBN;
        this.name = name;
        this.category = category;
    }

    public Book(String ISBN, String category_number, String name, String category, float price, int number, String[] borrower, Date[] date) {
        this.ISBN = ISBN;
        this.category_number = category_number;
        this.name = name;
        this.category = category;
        this.price = price;
        this.number = number;
        this.borrower = borrower;
        this.date = date;
    }


    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getCategory_number() {
        return category_number;
    }

    public void setCategory_number(String category_number) {
        this.category_number = category_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBorrower() {
        return Arrays.toString(borrower);
    }

    public String getBorrower(int index) {
        return borrower[index];
    }

    public void setBorrower(String[] borrower) {
        this.borrower = borrower;
    }

    public void setBorrower(int index, String borrower) {
        this.borrower[index] = borrower;
    }

    public String getDate() {
        return Arrays.toString(date);
    }

    public String getDate(int index) {
        return DateUtil.getReturnDate();
    }

    public void setDate(Date[] date) {
        this.date = date;
    }

    public void setDate(int index, Date date) {
        this.date[index] = date;
    }

    public String toString() {
        return "Book{ISBN = " + this.getISBN() + ", category_number" +
                " = " + this.getCategory_number() + ", name = " + this.getName() + ", category = " + this.getCategory() + ", price = " + this.getPrice() + ", number = " + this.getNumber() + ", borrower = " + this.getBorrower() + ", date = " + this.getDate() +", expiredate = "+this.getExpireDate() + "}";
    }

    //����¼��
    public void collect(User user) {
        System.out.println("�밴˳��¼�����飨��š������š����������������۸�������һ��һ�����룩");
        this.setISBN(sc.nextLine());
        this.setCategory_number(sc.nextLine());
        this.setName(sc.nextLine());
        this.setCategory(sc.nextLine());
        this.setPrice(sc.nextFloat());
        this.setNumber(sc.nextInt());
        sc.nextLine();
        CollectUtil.CollectStart(user, this);
    }

    //��ѯ�鼮
    public void select(User user) {
        System.out.println("������Ҫ��ѯ���鼮���");
        String ISBN = sc.nextLine();
        SelectBookUtile.SelectStart(user, this, ISBN);
    }

    //�����Ϣ����change()�������ֵ���޸����ݿ�
    public void change(User user) {
        if (user.getType() == 1) {
//            System.out.println("������Ҫ�޸��鼮�ı��");
//            String ISBN = sc.nextLine();
            System.out.println("������Ҫ��ѯ���鼮���");
            String ISBN = sc.nextLine();
            SelectBookUtile.SelectStart(user, this, ISBN);
            if (!DBUtil.isFind) {
                return;
            }
            while (true) {
                System.out.println("��������1,2,3,4,5��Ӧ�޸ķ����š����������������۸�,����");
                int num = sc.nextInt();
                sc.nextLine(); // ���������

                switch (num) {
                    case 1:
                        System.out.println("�������µķ�����");
                        this.setCategory_number(sc.nextLine());
                        break; // ������
                    case 2:
                        System.out.println("�������µ�����");
                        this.setName(sc.nextLine());
                        break; // ����
                    case 3:
                        System.out.println("�������µķ�����");
                        this.setCategory(sc.nextLine());
                        break; // ������
                    case 4:
                        System.out.println("�������µļ۸�");
                        this.setPrice(sc.nextFloat());
                        sc.nextLine(); // ���������
                        break; // �۸�
                    case 5:
                        System.out.println("�������µ�����");
                        this.setNumber(sc.nextInt());
                        sc.nextLine(); // ���������
                        break; // ����
                }

                ChangeBookUtile.changeBookStart(user, this, ISBN, num);
                System.out.println("�����޸�1�����˳�0");
                if (sc.nextLine().equals("0")) {
                    break;
                }
            }
        } else {
            System.out.println("�����ǹ���Ա,��Ȩ�޽��д˲���");
        }

    }

    //����
    public void borrow(User user, String ISBN) throws ParseException {
        user = EnterUtil.input;

        int flag = 3;//0�޴���,1��ʣ�࣬2�ѽ��飬3���Խ���
//        System.out.println("������Ҫ����鼮���");
//        String ISBN = sc.nextLine();
        int number = DBUtil.dbSelectBookNumMatch(this, ISBN);
        this.setNumber(number);
        int j = DBUtil.dbSelectBorrowedBookNumMatch(this, ISBN);  //j������ǽ��ȥ���ٱ�
        if (number == -1) {
            flag = 0;
        }
        if (number == 0) {
            flag = 1;
        }
        /*������Ӵ��룬���ݱ��ISBN�����ݿ⣬�Ѷ�Ӧ���borrower��date��Ϣ��ֵ��book����ֵ
         * ������setBorrower(String[] borrower)�����飬Ҳ������setBorrower(int index,String borrower)��
         * ����String��ֵ��borrower[i]��dateͬ�����88��*/
        DBUtil.dbChangeBookBDMatch(this, ISBN);  //Ϊbook��ֵborrower��date
        for (int i = 0; i < j; i++) {
            if (user.getName().equals(this.getBorrower(i))) {
                flag = 2;
                break;
            }
        }

        switch (flag) {
            case 0:
                NobodyUI.showDialog("���鲻����");
                break;
            case 1:
                NobodyUI.showDialog("��ʣ��ͼ��");
                break;
            case 2:
                NobodyUI.showDialog("�ѽ����");
                break;
            case 3:
                this.setBorrower(j, user.getName());
                this.setDate(j, dateUtil.toDate(dateUtil.getReturnDate()));
                /*������Ӵ��룬�޸����ݿ�borrower��date*/
                DBUtil.dbUpdateBookBDMatch(this, ISBN, j);
                NobodyUI.showDialog("����ɹ�");
        }
    }

    //����
    public void returnBook(User user) {
        /**
         * 1.��ȡ�û���
         * 2.���뻹����
         * 3.�ڽ�������������������ڵ���һ��,�ҵ����û�
         * (û���ҵ�,��ʾ����δ��)
         * select * from borrowedbook where ISBN=2;
         * 4.�����û��͹黹ʱ����Ϊnull
         * 5.����иñ�ŵ����number+1
         * 6.�������
         */

        int flag = 1;//1û�н��Ȿ�飬2���Ի���
        String userName = user.getName();
//        System.out.println("�������Ż���");
//        String ISBN = sc.nextLine();
        /*������Ӵ���,���ݱ��ISBN�����ݿ⣬�Ѷ�Ӧ���borrower��date��Ϣ��ֵ��book����ֵ����borrow����һ����*/

        if (DBUtil.dbreturnbook(userName, ISBN)) {
            flag = 2;
        }
        if (flag == 1) System.out.println("û�н��Ȿ��");
        else {
            System.out.println("����ɹ�");
        }
    }

    //����
    public void renew(User user) throws ParseException {
        int flag = 1;//1û�н��Ȿ�飬2����ɹ�
        System.out.println("��������������");
        String ISBN = sc.nextLine();
        if (!DBUtil.dbrenew(user, ISBN)) {
            System.out.println("û�н��Ȿ��");
        } else {
            System.out.println("����ɹ�");
            /*������Ӵ��룬��this.getDate(i)�޸����ݿ��Ӧλ�õ�date*/
        }
    }

    //��ʾ������Ϣ
    public void showInfo(User user) {//��ʾ�û����н�����Ϣ
        /*������Ӵ��룬�����û�����ѯ���ݿ��������ѽ�ͼ�飬��ӡ�鼮��ţ����ԵĻ��ٴ�ӡ���������ࣩ����������*/
        try {
            DBUtil.dbshowinfo(user);
        } catch (Exception e) {
            System.out.println("��δ�����");
        }
    }

    //��ʾ���������Ϣ
    public void showallbook(User user) {
        DBUtil.dbshowallbook(user);
    }
}