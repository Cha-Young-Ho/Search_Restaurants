package View;


import Controller.Functions;
import Model.Record;
import View.springLayout.SpringUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Objects;

public class MainView extends JFrame {
    int[] recordNum = new int[5];
    Functions f;

    public MainView(LinkedList<Record> records) {
        /*
         * 링크드리스트로 구성된 레코드가 잘 넘어왔는지
         * 테스트하려면 다음 함수를 실행하세요.
         */
        TestForRecords(records);

        /*
         * 프레임 설정
         * 그림판을 키면 흰 화면에서 그림을 그릴 수 있는 것처럼 흰 화면(초기화면) 세팅 역할을 한다.
         * 컨테이너위에 버튼, 라벨 등의 컴포넌트 들을 부착(add)하는 방식이다.
         */
        setTitle("경상남도 모범음식점 검색 프로그램"); // 프로그램 창 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 최소화, 최대화, X버튼 중 X 버튼 누르면 프로세스가 종료된다.


        Container container = getContentPane();
        container.setLayout(null); // 정해진 레이아웃을 사용하지 않음

        // -----------------------------------------------------------------------------------------------------
        /*
         * 화면 상단 제목 라벨
         * 이하 모든 컴포넌트들의 생성 과정은 다음과 같습니다.
         * 컴포넌트 생성
         * 컴포넌트 크기와 레이아웃 설정
         * 컴포넌트를 컨테이너에 부착
         */
        JLabel labelTitle1, labelTitle2;
        Font titleFont;
        titleFont = new Font("고딕", Font.BOLD, 20);
        labelTitle1 = new JLabel("경상남도 모범음식점");
        labelTitle1.setFont(titleFont);
        labelTitle1.setBounds(0, 20, 360, 30);
        labelTitle1.setHorizontalAlignment(JLabel.CENTER);
        labelTitle2 = new JLabel("검색 프로그램");
        titleFont = new Font("고딕", Font.BOLD, 24);
        labelTitle2.setFont(titleFont);
        labelTitle2.setBounds(0, 50, 360, 30);
        labelTitle2.setHorizontalAlignment(JLabel.CENTER);
        container.add(labelTitle1);
        container.add(labelTitle2);

        /*
         * 검색 영역
         */
        JComboBox combobox;
        JLabel labelType;
        String[] search_rule = {"음식점명 검색", "주소로 검색", "메뉴로 검색"};

        combobox = new JComboBox(search_rule);
        combobox.setBounds(5,120,110,40);
        container.add(combobox);

        JTextField textField = new JTextField();
        textField.setBounds(120, 120,160,40);
        container.add(textField);

        // 검색 버튼
        JButton button_search = new JButton();
        button_search.setText("검색");
        button_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = "";
                String searchString = "";
                try {
                    selectedItem = Objects.requireNonNull(combobox.getSelectedItem()).toString();
                    searchString = textField.getText();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(button_search, "비정상적인 접근입니다.", "경고", JOptionPane.WARNING_MESSAGE);
                }
                if (selectedItem.equals("")) {
                    JOptionPane.showMessageDialog(button_search, "구분을 선택하세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
                } else if (searchString.equals("")) {
                    JOptionPane.showMessageDialog(button_search, "검색할 단어를 입력하세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println(selectedItem);
                    System.out.println(searchString);
                    System.out.println("새 창 띄우기");
                    new SearchView(records, selectedItem, searchString);
                }
            }
        });
        button_search.setBounds(285, 120, 60, 40);
        container.add(button_search);

        /*
         * combobox에서 선택한 아이템을 꺼내는 테스트용 라벨
         */
        labelType = new JLabel("검색 구분을 선택하고 검색어를 입력하세요.");
        labelType.setBounds(0,180,360,40);
        labelType.setHorizontalAlignment(JLabel.CENTER);
        container.add(labelType);
        combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = combobox.getSelectedItem().toString();
                labelType.setText("선택된 방법:  " + selectedItem);
            }
        });


        /*
         * 랜덤 추천 영역 중 테이블 제목
         */
        JLabel randomLabel = new JLabel();
        randomLabel.setText("랜덤 추천");
        randomLabel.setFont(new Font("고딕", Font.BOLD, 36));
        randomLabel.setBounds(50, 330, 260, 50);
//        randomLabel.setOpaque(true);
//        randomLabel.setBackground(Color.GREEN);
        randomLabel.setHorizontalAlignment(JLabel.CENTER);
        container.add(randomLabel);

        /*
         * 랜덤 추천 영역 중 새로고침
         */
        ImageIcon icon_Reload = new ImageIcon("image/reload.png");
        JButton button_Reload = new JButton(icon_Reload);
        button_Reload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                container.removeAll();
                container.add(labelTitle1);
                container.add(labelTitle2);
                container.add(combobox);
                container.add(textField);
                container.add(button_search);
                container.add(labelType);
                container.add(randomLabel);
                container.add(button_Reload);
                container.setBackground(Color.white);

                f = new Functions(records.size());
                recordNum = f.RandomGeneration();
                Record[] record = new Record[5];
                for (int i=0; i<recordNum.length; i++) {
                    record[i] = records.get(recordNum[i]);
                }

                Object[] columnNames = new Object[]
                        { "업소명", "업종", "상세보기" };
                Object[][] rowData = new Object[][] {
                        { record[0].getName(), record[0].getType(), new JButton() },
                        { record[1].getName(), record[1].getType(), new JButton() },
                        { record[2].getName(), record[2].getType(), new JButton() },
                        { record[3].getName(), record[3].getType(), new JButton() },
                        { record[4].getName(), record[4].getType(), new JButton() }
                };

                // 테이블 모델에 배열 연결
                DefaultTableModel dtm = new DefaultTableModel(rowData, columnNames);
                JTable jtable = new JTable(dtm);
                // JTable은 JScrollPane에 반드시 연결해주어야 한다.
                // ContentPane 안의 ScrollPane 영역이 JTable으로 채워져 있다고 생각하면 된다.
                JScrollPane scrollpane = new JScrollPane(jtable);

                jtable.getColumnModel().getColumn(2).setCellRenderer(new Main_TableCell(jtable, record));
                jtable.getColumnModel().getColumn(2).setCellEditor(new Main_TableCell(jtable, record));

                jtable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
                scrollpane.setBounds(5, 380, 340, 105); //스크롤페인 크기 설정 (= 테이블크기 설정)
                container.add(scrollpane);
            }
        });
        button_Reload.setBounds(310,340,32,32);
        container.add(button_Reload);

        /*
         * 랜덤 추천 테이블 내부
         * 테이블 모델은 배열로 이루어져 있어
         * 내용을 나타내는 배열을 먼저 선언해줘야 한다.
         */
        f = new Functions(records.size());
        recordNum = f.RandomGeneration();
        Record[] records_random = new Record[5];
        for (int i=0; i<recordNum.length; i++) {
            records_random[i] = records.get(recordNum[i]);
        }

        Object[] columnNames = new Object[]
                { "업소명", "업종", "상세보기" };
        Object[][] rowData = new Object[][] {
                { records_random[0].getName(), records_random[0].getType(), new JButton() },
                { records_random[1].getName(), records_random[1].getType(), new JButton() },
                { records_random[2].getName(), records_random[2].getType(), new JButton() },
                { records_random[3].getName(), records_random[3].getType(), new JButton() },
                { records_random[4].getName(), records_random[4].getType(), new JButton() }
        };

        // 테이블 모델에 배열 연결
        DefaultTableModel dtm = new DefaultTableModel(rowData, columnNames);
        JTable jtable = new JTable(dtm);
        // JTable은 JScrollPane에 반드시 연결해주어야 한다.
        // ContentPane 안의 ScrollPane 영역이 JTable으로 채워져 있다고 생각하면 된다.
        JScrollPane scrollpane = new JScrollPane(jtable);

        jtable.getColumnModel().getColumn(2).setCellRenderer(new Main_TableCell(jtable, records_random));
        jtable.getColumnModel().getColumn(2).setCellEditor(new Main_TableCell(jtable, records_random));

        jtable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
        scrollpane.setBounds(5, 380, 340, 105); //스크롤페인 크기 설정 (= 테이블크기 설정)
        container.add(scrollpane);




        /*
         * 컨테이너 크기 등 설정
         */
        container.setBackground(Color.white);
        setSize(360, 530);
        setResizable(false); //크기 고정
        setVisible(true);
    }

        class Main_TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
            JButton jb;

            public Main_TableCell(JTable table,Record[] record) {
                jb = new JButton("클릭");
                jb.addActionListener(e -> {
                    System.out.println("Button Clicked!");
                    int row = table.getSelectedRow();
                    for(int i=0; i<table.getColumnCount()-1; i++) {
                        System.out.println(table.getValueAt(row, i).toString()+"\t");
                    }
                    System.out.println(record[row].getName()+" 상세정보 창 띄우기");
                    new Main_Dialog(record[row]);
                });
            }
        // 필요없는 오버라이드들

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return jb;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                                                     int column) {
            return jb;
        }

        // 상세보기를 클릭하면 실행되는 메서드입니다.
        // 음식점의 상세한 정보가 담긴 창을 띄워줍니다.
        private class Main_Dialog extends JDialog {

            public Main_Dialog(Record record){

                String[] colNames = {
                        "업소명: ",
                        "업태: ",
                        "주메뉴: ",
                        "시도: ",
                        "시군구: ",
                        "주소: ",
                        "전화번호: "
                };
                String[] colDetails = {
                        record.getName(),
                        record.getType(),
                        record.getMenu(),
                        record.getState(),
                        record.getCity(),
                        record.getAddress(),
                        record.getNumber()
                };

                JPanel panel = new JPanel(new SpringLayout());

                if(colNames.length != colDetails.length) {
                    System.out.println("테이블이 올바르지 않습니다.");
                } else {
                    int pairs = colNames.length;

                    // 패널 내부를 채웁니다. (0,0), (0,1), (1,0)...(5,1) 순서입니다.
                    Font fontforName = new Font("고딕", Font.BOLD, 28);
                    Font fontforDetail = new Font("고딕", Font.PLAIN, 28);

                    for (int i = 0; i < pairs; i++) {

                        JLabel colName = new JLabel(colNames[i]);
                        colName.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
                        colName.setFont(fontforName);
                        colName.setBackground(Color.lightGray);
                        colName.setOpaque(true);
                        panel.add(colName);

                        JLabel colDetail = new JLabel(colDetails[i]);
                        colDetail.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
                        colDetail.setFont(fontforDetail);
                        panel.add(colDetail);
                    }

                    // 격자 형태로 UI를 잡아주는 메서드입니다.
                    // https://docs.oracle.com/javase/tutorial/displayCode.html
                    SpringUtilities.makeCompactGrid(panel,
                            pairs, 2, //rows, cols
                            6, 15,        //initX, initY
                            6, 0);       //xPad, yPad
                }

                // 만든 JPanel을 JFrame에 연결하기
                // JPanel 만으로는 창을 띄울 수 없습니다. JFrame을 이용해서 띄워야 합니다.
                JFrame frame = new JFrame(record.getName()+" 상세정보");
                panel.setOpaque(true);  //content panes must be opaque
                panel.setBackground(Color.white);
                frame.setContentPane(panel);

                //JFrame 기타 설정
                frame.pack();
                frame.setVisible(true);
                frame.setLayout(null); // 창의 사이즈를 조절할 수 없음
                frame.setResizable(false); //크기 고정
            }
        }
    }

    private void TestForRecords(LinkedList<Record> records) {
        /*
         * 레코드가 잘 저장되어 넘어왔는지에 대한 테스트 구문입니다.
         */
        System.out.println("레코드의 열의 개수: "+records.size());
        Record testFirstRecord = records.get(0);
        int rand = (int) (Math.random() * (records.size() - 1));
        Record testRandomRecord = records.get(rand-1);
        Record testLastRecord = records.get(records.size()-1);
        System.out.println("--------첫 값 테스트--------"+
                "\n시도 : "+testFirstRecord.getState()+
                "\n시군구 : "+testFirstRecord.getCity()+
                "\n업태 : "+testFirstRecord.getType()+
                "\n주메뉴 : "+testFirstRecord.getMenu()+
                "\n업소명 : "+testFirstRecord.getName()+
                "\n주소 : "+testFirstRecord.getAddress()+
                "\n전화번호 : "+testFirstRecord.getNumber()+
                "\n---------------------------\n"
        );
        System.out.println("--------"+rand+"번째 값 테스트--------"+
                "\n시도 : "+testRandomRecord.getState()+
                "\n시군구 : "+testRandomRecord.getCity()+
                "\n업태 : "+testRandomRecord.getType()+
                "\n주메뉴 : "+testRandomRecord.getMenu()+
                "\n업소명 : "+testRandomRecord.getName()+
                "\n주소 : "+testRandomRecord.getAddress()+
                "\n전화번호 : "+testRandomRecord.getNumber()+
                "\n---------------------------\n"
        );
        System.out.println("--------마지막 값 테스트--------"+
                "\n시도 : "+testLastRecord.getState()+
                "\n시군구 : "+testLastRecord.getCity()+
                "\n업태 : "+testLastRecord.getType()+
                "\n주메뉴 : "+testLastRecord.getMenu()+
                "\n업소명 : "+testLastRecord.getName()+
                "\n주소 : "+testLastRecord.getAddress()+
                "\n전화번호 : "+testLastRecord.getNumber()+
                "\n---------------------------\n"
        );
    }
}
