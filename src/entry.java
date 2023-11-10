import MyJavabean.Book;
import MyJavabean.User;
import MyUIFrame.LoginUI;
import MyUtil.EnterUtil;

import java.text.ParseException;
import java.util.Scanner;

/*
 * @Description: 进入页面
 */
public class entry {
    public static void main(String[] args) throws ParseException {
        start();
    }


    public static void start() throws ParseException {
        Scanner in = new Scanner(System.in);
        Book book = new Book();
        LoginUI.getInstance();
        User user= EnterUtil.input;

        if (user.getType() == 0&& user.getIslogin()) {
            while (true) {
                System.out.println("-----------欢迎用户登入-----------");
                System.out.println("请选择您想要的功能1借书 2还书 3显示所有借阅信息 4根据ISBN查询书 5续借 6显示书库所有书 7退出系统");
                int num = in.nextInt();
                in.nextLine();
                switch (num) {
//                    case 1 :book.borrow(user);break;
                    case 2 :book.returnBook(user);break;
                    case 3 :book.showInfo(user);break;
//                    case 4 :book.select(user);break;
//                    case 5 :book.renew(user);break;
//                    case 6 :book.showallbook(user);break;
                    case 7 :System.exit(0);break;
                }
            }
        } else if (user.getType() == 1 && user.getIslogin()) {
            while (true) {
                System.out.println("--------------欢迎管理员登入-------------");
                System.out.println("请选择您想要的功能1.新书录入 2.变更书目信息 3.显示所有书籍信息 4.退出系统");
                int num = in.nextInt();
                in.nextLine();
                switch (num) {
                    case 1:
                        book.collect(user);
                        break;
                    case 2:
                        book.change(user);
                        break;
                    case 3:
//                        book.showallbook(user);
                        break;
                    case 4:
                        System.exit(0);
                        break;
                }
            }
        }

    }
}