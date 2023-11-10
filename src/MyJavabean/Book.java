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

    //新书录入
    public void collect(User user) {
        System.out.println("请按顺序录入新书（编号、分类编号、书名、分类名、价格、数量，一行一行输入）");
        this.setISBN(sc.nextLine());
        this.setCategory_number(sc.nextLine());
        this.setName(sc.nextLine());
        this.setCategory(sc.nextLine());
        this.setPrice(sc.nextFloat());
        this.setNumber(sc.nextInt());
        sc.nextLine();
        CollectUtil.CollectStart(user, this);
    }

    //查询书籍
    public void select(User user) {
        System.out.println("请输入要查询的书籍编号");
        String ISBN = sc.nextLine();
        SelectBookUtile.SelectStart(user, this, ISBN);
    }

    //变更信息，用change()变更属性值并修改数据库
    public void change(User user) {
        if (user.getType() == 1) {
//            System.out.println("请输入要修改书籍的编号");
//            String ISBN = sc.nextLine();
            System.out.println("请输入要查询的书籍编号");
            String ISBN = sc.nextLine();
            SelectBookUtile.SelectStart(user, this, ISBN);
            if (!DBUtil.isFind) {
                return;
            }
            while (true) {
                System.out.println("请输入编号1,2,3,4,5对应修改分类编号、书名、分类名、价格,数量");
                int num = sc.nextInt();
                sc.nextLine(); // 清除缓冲区

                switch (num) {
                    case 1:
                        System.out.println("请输入新的分类编号");
                        this.setCategory_number(sc.nextLine());
                        break; // 分类编号
                    case 2:
                        System.out.println("请输入新的书名");
                        this.setName(sc.nextLine());
                        break; // 书名
                    case 3:
                        System.out.println("请输入新的分类名");
                        this.setCategory(sc.nextLine());
                        break; // 分类名
                    case 4:
                        System.out.println("请输入新的价格");
                        this.setPrice(sc.nextFloat());
                        sc.nextLine(); // 清除缓冲区
                        break; // 价格
                    case 5:
                        System.out.println("请输入新的数量");
                        this.setNumber(sc.nextInt());
                        sc.nextLine(); // 清除缓冲区
                        break; // 数量
                }

                ChangeBookUtile.changeBookStart(user, this, ISBN, num);
                System.out.println("继续修改1或者退出0");
                if (sc.nextLine().equals("0")) {
                    break;
                }
            }
        } else {
            System.out.println("您不是管理员,无权限进行此操作");
        }

    }

    //借书
    public void borrow(User user, String ISBN) throws ParseException {
        user = EnterUtil.input;

        int flag = 3;//0无此书,1无剩余，2已借书，3可以借书
//        System.out.println("请输入要借的书籍编号");
//        String ISBN = sc.nextLine();
        int number = DBUtil.dbSelectBookNumMatch(this, ISBN);
        this.setNumber(number);
        int j = DBUtil.dbSelectBorrowedBookNumMatch(this, ISBN);  //j代表的是借出去多少本
        if (number == -1) {
            flag = 0;
        }
        if (number == 0) {
            flag = 1;
        }
        /*这里添加代码，根据编号ISBN查数据库，把对应书的borrower和date信息赋值给book属性值
         * 可以用setBorrower(String[] borrower)传数组，也可以用setBorrower(int index,String borrower)把
         * 单个String赋值给borrower[i]，date同理，详见88行*/
        DBUtil.dbChangeBookBDMatch(this, ISBN);  //为book赋值borrower和date
        for (int i = 0; i < j; i++) {
            if (user.getName().equals(this.getBorrower(i))) {
                flag = 2;
                break;
            }
        }

        switch (flag) {
            case 0:
                NobodyUI.showDialog("此书不存在");
                break;
            case 1:
                NobodyUI.showDialog("无剩余图书");
                break;
            case 2:
                NobodyUI.showDialog("已借此书");
                break;
            case 3:
                this.setBorrower(j, user.getName());
                this.setDate(j, dateUtil.toDate(dateUtil.getReturnDate()));
                /*这里添加代码，修改数据库borrower和date*/
                DBUtil.dbUpdateBookBDMatch(this, ISBN, j);
                NobodyUI.showDialog("借书成功");
        }
    }

    //还书
    public void returnBook(User user) {
        /**
         * 1.获取用户名
         * 2.输入还书编号
         * 3.在借书表中锁定还书编号所在的那一行,找到该用户
         * (没有找到,提示该书未借)
         * select * from borrowedbook where ISBN=2;
         * 4.将该用户和归还时间设为null
         * 5.书表中该编号的书的number+1
         * 6.还书结束
         */

        int flag = 1;//1没有借这本书，2可以还书
        String userName = user.getName();
//        System.out.println("请输入编号还书");
//        String ISBN = sc.nextLine();
        /*下面添加代码,根据编号ISBN查数据库，把对应书的borrower和date信息赋值给book属性值（和borrow方法一样）*/

        if (DBUtil.dbreturnbook(userName, ISBN)) {
            flag = 2;
        }
        if (flag == 1) System.out.println("没有借这本书");
        else {
            System.out.println("还书成功");
        }
    }

    //续借
    public void renew(User user) throws ParseException {
        int flag = 1;//1没有借这本书，2续借成功
        System.out.println("请输入续借书编号");
        String ISBN = sc.nextLine();
        if (!DBUtil.dbrenew(user, ISBN)) {
            System.out.println("没有借这本书");
        } else {
            System.out.println("续借成功");
            /*这里添加代码，用this.getDate(i)修改数据库对应位置的date*/
        }
    }

    //显示个人信息
    public void showInfo(User user) {//显示用户所有借书信息
        /*这里添加代码，根据用户名查询数据库中所有已借图书，打印书籍编号（可以的话再打印书名、分类），还书日期*/
        try {
            DBUtil.dbshowinfo(user);
        } catch (Exception e) {
            System.out.println("您未借过书");
        }
    }

    //显示书库所有信息
    public void showallbook(User user) {
        DBUtil.dbshowallbook(user);
    }
}