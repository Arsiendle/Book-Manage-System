import MyJavabean.Book;
import MyJavabean.User;
import MyUIFrame.LoginUI;
import MyUtil.EnterUtil;

import java.text.ParseException;
import java.util.Scanner;

/*
 * @Description: ����ҳ��
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
                System.out.println("-----------��ӭ�û�����-----------");
                System.out.println("��ѡ������Ҫ�Ĺ���1���� 2���� 3��ʾ���н�����Ϣ 4����ISBN��ѯ�� 5���� 6��ʾ��������� 7�˳�ϵͳ");
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
                System.out.println("--------------��ӭ����Ա����-------------");
                System.out.println("��ѡ������Ҫ�Ĺ���1.����¼�� 2.�����Ŀ��Ϣ 3.��ʾ�����鼮��Ϣ 4.�˳�ϵͳ");
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