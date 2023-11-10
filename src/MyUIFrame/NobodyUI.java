package MyUIFrame;

import MyJavabean.Book;
import MyJavabean.User;
import MyUtil.DBUtil;
import MyUtil.EnterUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.text.ParseException;
import java.util.List;

public class NobodyUI extends Application {
    User user=new User();
    Book book = new Book();
    private  static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Button borrowButton = createNavigationButton("����", "BorrowBookScene");
        Button returnButton = createNavigationButton("����", "ReturnBookScene");
        Button displayButton = createNavigationButton("��ʾ���н�����Ϣ", "AllImformScene");
        Button searchButton = createNavigationButton("����ISBN��ѯ��", "SearchBookScene");
        Button renewButton = createNavigationButton("����", "KeepinBookScene");
        Button showAllBooksButton = createNavigationButton("��ʾ���������", "AllBookScene");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(borrowButton, returnButton, displayButton, searchButton, renewButton, showAllBooksButton);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("ͼ�����ϵͳ");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            // ������̨�ر�ʱ��ֹ����
            System.exit(0);
        });
        primaryStage.show();
    }

    private Button createNavigationButton(String buttonText, String sceneName) {
        Button button = new Button(buttonText);
        button.setOnAction(event -> navigateToScene(sceneName));
        return button;
    }

    private void navigateToScene(String sceneName) {
        Stage newStage = new Stage();
        newStage.setTitle(sceneName);

        Parent root = null;
        Scene newScene = null;

        // ����sceneName��ֵ������Ӧ�Ľ���
        if (sceneName.equals("BorrowBookScene")) {
            // �����������
            root = createBorrowBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("ReturnBookScene")) {
            // �����������
            root = createReturnBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("AllImformScene")) {
            // ������ʾ���н�����Ϣ����
            root = createAllImformScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("SearchBookScene")) {
            // ��������ISBN��ѯ�����
            root = createSearchBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("KeepinBookScene")) {
            // �����������
            root = createKeepinBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("AllBookScene")) {
            // ������ʾ������������
            root = createAllBookScene();
            newScene = new Scene(root, 600, 400);
        }


        newStage.setScene(newScene);
        newStage.show();
    }

    //    ����
    private Parent createBorrowBookScene() {

        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Label isbnLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();
        Label showBookInform = new Label("��Ϣչʾ:");
        TextArea informTextArea = new TextArea(); // ʹ��TextArea����ʾ��Ϣ���ɹ�����ʾ
        informTextArea.setEditable(false); // ������Ϣչʾ���򲻿ɱ༭

        Button searchButton = new Button("����");

        searchButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            // ����ISBN�����鼮��Ϣ
            String bookInformation = searchBookByISBN(book,isbn);

            // �����������鼮��Ϣ��ʾ����ϢչʾTextArea��
            informTextArea.setText(bookInformation);

        });

        Button borrowButton = new Button("����");

        borrowButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();

            // ִ�н������
            // ...
            try {
                book.borrow(EnterUtil.input, isbn);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            // ������ʾ�����½���Ȳ���
            // ...
        });

        GridPane.setHalignment(isbnLabel, HPos.RIGHT);
        GridPane.setHalignment(showBookInform, HPos.RIGHT);
        GridPane.setHalignment(searchButton, HPos.LEFT);

        // ����Ϣչʾ��������ڹ��������
        ScrollPane scrollPane = new ScrollPane(informTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200); // ���ù������ĸ߶�

        root.add(isbnLabel, 0, 0);
        root.add(isbnTextField, 1, 0);
        root.add(searchButton, 2, 0);
        root.add(showBookInform, 0, 1);
        root.add(scrollPane, 1, 1, 2, 1);
        root.add(borrowButton, 1, 2);

        return root;
    }

    private String searchBookByISBN(Book book,String isbn) {
        // ����ISBN�����鼮��Ϣ���߼�
        // ...
        // �������������鼮��Ϣ

        return "�鼮��Ϣ��\n" + DBUtil.dbSelectBookById(book,isbn);
    }

    //����
    private Parent createReturnBookScene() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Label isbnLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();

        Button returnButton = new Button("����");

        returnButton.setOnAction(event -> {
            Book book1=new Book();
            String isbn = isbnTextField.getText();
            DBUtil.dbSelectBookMatch(book1,isbn);
            if (!DBUtil.isFind) {
                showDialog("���鲻����");
            } else {
                if (DBUtil.dbreturnbook(EnterUtil.input.getName(),isbn)) {
                    showDialog("����ɹ�");
                }else {
                    showDialog("��δ�����");
                }
            }
            // ִ�л������
            // ...

            // ������ʾ�����½���Ȳ���
            // ...
        });

        GridPane.setHalignment(isbnLabel, HPos.RIGHT);

        root.add(isbnLabel, 0, 0);
        root.add(isbnTextField, 1, 0);
        root.add(returnButton, 2, 0);

        return root;
    }

    //��ʾ���н�����Ϣ
    private Parent createAllImformScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        // �������ؼ�
        TableView<Book> tableView = new TableView<>();

        // �����У����������������԰�
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        TableColumn<Book, String> nameColumn = new TableColumn<>("name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Book, String> cateColumn = new TableColumn<>("category");
        cateColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, String> exdateColumn = new TableColumn<>("expiredate");
        exdateColumn.setCellValueFactory(new PropertyValueFactory<>(
                "expireDate"));

        // ������ӵ����
        tableView.getColumns().addAll(isbnColumn, nameColumn,
                cateColumn,exdateColumn);

        // ��ȡ����е������鼮��Ϣ
        ObservableList<Book> bookList = getBookListFromDatabase();

        // ���鼮������ӵ����
        tableView.setItems(bookList);

        root.getChildren().add(tableView);

        return root;
    }

    public ObservableList<Book> getBookListFromDatabase() {
        // ģ������ݿ��л�ȡ����е������鼮��Ϣ
        ObservableList<Book> bookList =
                FXCollections.observableArrayList(DBUtil.dbshowinfo(EnterUtil.input));
        return bookList;
    }

    //����ISBN��ѯ
    private Parent createSearchBookScene() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Label searchLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();

        Label categoryNumberLabel = new Label("Category Number:");
        Label canumText = new Label();
        Label nameLabel = new Label("Name:");
        Label nameText = new Label();
        Label categoryLabel = new Label("Category:");
        Label cateText = new Label();
        Label priceLabel = new Label("Price:");
        Label priceText = new Label();
        Label numberLabel = new Label("Number:");
        Label numText = new Label();

        Button searchButton = new Button("����");

        searchButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            String[] s = DBUtil.dbSelectBookByIdshuzu(book, isbn);
            canumText.setText(s[1]);
            nameText.setText(s[2]);
            cateText.setText(s[3]);
            priceText.setText(s[4]);
            numText.setText(s[5]);
            // ִ�в�ѯ����
            // ...

            // ������ʾ�����½���Ȳ���
            // ...
        });


        root.add(searchLabel, 0, 0);
        root.add(isbnTextField, 1, 0);

        root.add(categoryNumberLabel, 0, 1);
        root.add(canumText, 1, 1);
        root.add(nameLabel, 0, 2);
        root.add(nameText, 1, 2);
        root.add(categoryLabel, 0, 3);
        root.add(cateText, 1, 3);
        root.add(priceLabel, 0, 4);
        root.add(priceText, 1, 4);
        root.add(numberLabel, 0, 5);
        root.add(numText, 1, 5);

        root.add(searchButton, 2, 0);

        return root;
    }

    //����
    private Parent createKeepinBookScene() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Label isbnLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();

        Button keepinButton = new Button("����");

        keepinButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            if (!DBUtil.dbrenew(EnterUtil.input, isbn)) {
                showDialog("û�н��Ȿ��");
            } else {
                showDialog("����ɹ�");
                /*������Ӵ��룬��this.getDate(i)�޸����ݿ��Ӧλ�õ�date*/
            }
            // ִ���������
            // ...

            // ������ʾ�����½���Ȳ���
            // ...
        });

        GridPane.setHalignment(isbnLabel, HPos.RIGHT);

        root.add(isbnLabel, 0, 0);
        root.add(isbnTextField, 1, 0);
        root.add(keepinButton, 2, 0);

        return root;
    }

    //��ʾ���������
    private Parent createAllBookScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        // �������ؼ�
        TableView<Book> tableView = new TableView<>();

        // �����У����������������԰�
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        TableColumn<Book, String> canumColumn = new TableColumn<>("category_number");
        canumColumn.setCellValueFactory(new PropertyValueFactory<>("category_number"));

        TableColumn<Book, String> nameColumn = new TableColumn<>("name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Book, String> cateColumn = new TableColumn<>("category");
        cateColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, Integer> numColumn = new TableColumn<>("number");
        numColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        // ������ӵ����
        tableView.getColumns().addAll(isbnColumn, canumColumn, nameColumn, cateColumn, priceColumn, numColumn);

        // ��ȡ����е������鼮��Ϣ
        ObservableList<Book> bookList = getAllBookListFromDatabase();

        // ���鼮������ӵ����
        tableView.setItems(bookList);

        root.getChildren().add(tableView);

        return root;
    }

    public ObservableList<Book> getAllBookListFromDatabase() {
        // ģ������ݿ��л�ȡ����е������鼮��Ϣ
        ObservableList<Book> bookList =
                FXCollections.observableArrayList(DBUtil.dbshowallbook(EnterUtil.input));
        return bookList;
    }

    //    ����
    //    ����
    public static void showDialog(String message) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);

        VBox dialogVBox = new VBox(20);
        dialogVBox.setAlignment(Pos.CENTER);

        Label label = new Label(message);
        dialogVBox.getChildren().add(label);

        Scene dialogScene = new Scene(dialogVBox, 200, 75); // �����Ի���Ŀ�Ⱥ͸߶�
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
}