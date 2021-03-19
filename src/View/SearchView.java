package View;

import Controller.Functions;
import Model.Record;
import View.springLayout.SpringUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.LinkedList;

public class SearchView extends JFrame {

    LinkedList<Record> data;
    LinkedList<Record> records_searched;
    String searchType;
    String searchString;
    Functions f;

    public SearchView(LinkedList<Record> data, String searchType, String searchString) {
        System.out.println(data.size());
        this.data = data;
        this.searchType = searchType;
        this.searchString = searchString;

        setTitle("'" + searchString + "' 검색 결과");
        System.out.println(searchType);

        Container container = getContentPane();
        container.setLayout(null); // 정해진 레이아웃을 사용하지 않음

        /*
         * 상위 라벨
         */
        JLabel labelTitle1, labelTitle2;
        Font titleFont;
        titleFont = new Font("고딕", Font.BOLD, 20);
        labelTitle1 = new JLabel(searchString);
        labelTitle1.setFont(titleFont);
        labelTitle1.setBounds(0, 20, 360, 30);
        labelTitle1.setHorizontalAlignment(JLabel.CENTER);
        labelTitle2 = new JLabel("(으)로 검색한 결과");
        titleFont = new Font("고딕", Font.BOLD, 24);
        labelTitle2.setFont(titleFont);
        labelTitle2.setBounds(0, 50, 360, 30);
        labelTitle2.setHorizontalAlignment(JLabel.CENTER);
        container.add(labelTitle1);
        container.add(labelTitle2);

        /*
         * 테이블 만들기 전 - selected Item에 따른 Search함수 실행
         */
        if (searchType.equals("음식점명 검색")) {
            System.out.println("음식점명으로 검색 함수 실행");
        } else if (searchType.equals("주소로 검색")) {
            System.out.println("주소로 검색 함수 실행");
        } else if (searchType.equals("메뉴로 검색")) {
            System.out.println("메뉴로 검색 함수 실행");
        }
        f = new Functions(data.size());
        records_searched = f.Search(data, searchType, searchString);

        /*
         * 테이블 만들기 전 - 검색 결과 데이터 확인
         */
        int rowSize = 0;
        if (records_searched == null) {
            System.out.println("null 테스트 : 검색 결과가 없습니다.");
            JLabel label = new JLabel("검색 결과가 없습니다.");
            label.setBounds(0, 130, 360, 30);
            label.setFont(new Font("고딕", Font.BOLD, 26));
            label.setHorizontalAlignment(JLabel.CENTER);
            container.add(label);
            label = new JLabel("검색어와 구분을 확인하세요.");
            label.setFont(new Font("고딕", Font.PLAIN, 18));
            label.setBounds(0, 170,360, 20);
            label.setHorizontalAlignment(JLabel.CENTER);
            container.add(label);
            setSize(360, 240);
//        setSize(365, 660);
            setResizable(false); //크기 고정
            setVisible(true);
        } else {
            System.out.println("null 테스트 : 검색 결과가 있네요");
            rowSize = records_searched.size();
            System.out.println("검색된 행의 개수 : 총 " + rowSize + " 개");

            /*
             * 테이블 구성
             */
            Object[] columnNames = new Object[]
                    {"업소명", "업종", "시군구", "상세보기"};
            Object[][] rowData = new Object[rowSize][columnNames.length];
            for (int i = 0; i < rowSize; i++) {
                Record tempRecord = records_searched.get(i);
                Object[] tempData = {tempRecord.getName(), tempRecord.getType(), tempRecord.getCity(), new JButton()};

                for (int j = 0; j < tempData.length; j++) {
                    rowData[i][j] = tempData[j];
                }
            }

            // 테이블 모델에 배열 연결
            DefaultTableModel dtm = new DefaultTableModel(rowData, columnNames);
            JTable jtable = new JTable(dtm);
            // JTable은 JScrollPane에 반드시 연결해주어야 한다.
            // ContentPane 안의 ScrollPane 영역이 JTable으로 채워져 있다고 생각하면 된다.
            JScrollPane scrollpane = new JScrollPane(jtable);

            jtable.getColumnModel().getColumn(columnNames.length-1).setCellRenderer(new Search_TableCell(jtable, records_searched));
            jtable.getColumnModel().getColumn(columnNames.length-1).setCellEditor(new Search_TableCell(jtable, records_searched));

            jtable.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
            int tableSizeHeight = 25+rowSize*16; // 테이블뷰의 높이가 출력물의 개수에 따라 동적으로 바뀌게 하기 위한 변수
            scrollpane.setBounds(5, 100, 340, tableSizeHeight); // 스크롤페인 크기 설정 (= 테이블크기 설정)
            container.add(scrollpane);


            container.setBackground(Color.white);
            int containerSizeHeight = 145 + tableSizeHeight;
            System.out.println("창 세로 크기: "+containerSizeHeight);
            setSize(360, containerSizeHeight);
            setResizable(false); //크기 고정
            setVisible(true);
        }


    }
    class Search_TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        JButton jb;

        public Search_TableCell(JTable table, LinkedList<Record> record) {
            jb = new JButton("클릭");
            jb.addActionListener(e -> {
                System.out.println("Button Clicked!");
                int row = table.getSelectedRow();
                for(int i=0; i<table.getColumnCount()-1; i++) {
                    System.out.println(table.getValueAt(row, i).toString()+"\t");
                }
                System.out.println(record.get(row).getName()+" 상세정보 창 띄우기");
                new Search_Dialog(record.get(row));
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
        private class Search_Dialog extends JDialog {

            public Search_Dialog(Record record){
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
                    Font fontForName = new Font("고딕", Font.BOLD, 28);
                    Font fontForDetail = new Font("고딕", Font.PLAIN, 28);

                    for (int i = 0; i < pairs; i++) {

                        JLabel colName = new JLabel(colNames[i]);
                        colName.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
                        colName.setFont(fontForName);
                        colName.setBackground(Color.lightGray);
                        colName.setOpaque(true);
                        panel.add(colName);

                        JLabel colDetail = new JLabel(colDetails[i]);
                        colDetail.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
                        colDetail.setFont(fontForDetail);
                        panel.add(colDetail);
                    }

                    // 격자 형태로 UI를 잡아주는 라이브러리입니다.
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
}
