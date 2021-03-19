package Controller;

import Model.Record;

import java.util.LinkedList;

public class Functions {

    int[] num = new int[5];
    int[] index = new int[20];
    int dataSize;

   
    public Functions(int dataSize) {

        this.dataSize = dataSize;
    }

    public int[] RandomGeneration() {

        for (int i = 0; i < 5; i++) {
            num[i] = (int) (Math.random() * (dataSize - 1) + 1); // 임의의 수 넣기
            for (int j = 0; j < i; j++) { // 만든 수를 num의 다른 수와 비교
                if (num[i] == num[j]) {
                    i--; // 같으면 i빼주고 다시 초기화작업
                    break;
                }
            }
        }

        return num;
    }

    public LinkedList<Record> Search(LinkedList<Record> data, String search_type, String search_string) {
        LinkedList<Record> temp_record = new LinkedList<Record>(); // 검색된 음식점들 저장하는 곳
        System.out.println(data.size());

        if (search_type.equals("음식점명 검색")) {
            System.out.println("음식점명 검색: " + search_string);
            System.out.println(search_string);
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getName().contains(search_string)) {
                    temp_record.add(data.get(i));
                }
            }
        } else if (search_type.equals("주소로 검색")) {
            System.out.println("주소로 검색: " + search_string);
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getAddress().contains(search_string) || data.get(i).getCity().contains(search_string)
                        || data.get(i).getState().contains(search_string)) {
                    temp_record.add(data.get(i));
                }
            }
        } else if (search_type.equals("메뉴로 검색")) {
            System.out.println("메뉴로 검색: " + search_string);
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getMenu().contains(search_string)) {
                    temp_record.add(data.get(i));
                }
            }
        }

        if (temp_record.size() >= 1) {
            LinkedList<Record> rand_record = new LinkedList<>();
            if(temp_record.size() > 20) {
                for (int i = 0; i < 20; i++) {
                    index[i] = (int) (Math.random() * (temp_record.size() - 1) + 1); // 임의의 수 넣기
                    for (int j = 0; j < i; j++) { // 만든 수를 num의 다른 수와 비교
                        if (index[i] == index[j]) {
                            i--; // 같으면 i빼주고 다시 초기화작업
                            break;
                        }
                    }
                }
                for(int i=0; i<20; i++) {
                    rand_record.add(temp_record.get(index[i]));
                }
                System.out.println("Search() 결과");
                for (int i = 0; i < rand_record.size(); i++) {
                    System.out.println("선정된 레코드 [" + i + "] = " + rand_record.get(i).getName());
                }
                return rand_record;
            } else {
                System.out.println("Search() 결과");
                for (int i = 0; i < temp_record.size(); i++) {
                    System.out.println("검색된 레코드 [" + i + "] = " + temp_record.get(i).getName());
                }
                return temp_record;
            }
        } else {
            System.out.println("Search() 결과");
            System.out.println("검색 결과가 없습니다.");
            return null;
        }
    }
}