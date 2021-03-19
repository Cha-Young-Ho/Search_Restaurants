package Controller;

import Model.Record;
import View.ErrorView;
import View.MainView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class ReadFileCSV {

	LinkedList<Record> records = new LinkedList<Record>();
	String path;
	public ReadFileCSV() throws IOException { // �����ڸ� �̿��ؼ� �о����.
		try {
			System.out.println(records.size());
			if(records.size() == 0) {
				this.path = System.getProperty("user.dir"); // ���������� ���丮 ������ ����.
				System.out.println(path);
				File file = new File(path + "\\Data.csv"); // ���� ������ ���丮�� ���� �����س��� ��� ����.

				BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(file))); // ���۸��� �����.

				String line = "";
				int num = 0;
				while ((line = br.readLine()) != null) { // �� ���ξ� �о����.

					// �� �κп��� ū ����ǥ�� ������.
					String first_Cut_string[] = line.replace(" ", "").split("\""); // ū ����ǥ ���� -> ������� ���� -> ������ ","��
					// �̿��Ͽ� �ڸ����� Cut_string�� �ֱ�

					if (num != 0) {

						// �� �κп��� �޸��� ������.
						for (int i = 0; i < first_Cut_string.length; i++) {
							if (first_Cut_string[i].compareTo(",") == 0) {
								first_Cut_string[i] = "";
							}
						}
						// ���� �迭�� �ϳ��� ���ڿ��� ��ġ��
						String temp_string = "";
						for (int i = 0; i < first_Cut_string.length; i++) {

							if (i != first_Cut_string.length - 1) {
								temp_string += first_Cut_string[i] + ";";
							} else {
								temp_string += first_Cut_string[i];
							}
						}
						// System.out.println(temp_string);

						String[] Second_Cut_String = temp_string.replace(";;", ";").split(";");

						records.add(new Record(Second_Cut_String[1], Second_Cut_String[2], Second_Cut_String[3],
								Second_Cut_String[4], Second_Cut_String[5], Second_Cut_String[6], Second_Cut_String[7]));

					}
					num++;
				}
				new MainView(records);
			} else {
			}
		}

		/*
		 * ������ ��ο� Data.csv ������ ã�� �� ���� ���
		 */
		catch(FileNotFoundException e) {
			System.out.println("path = " + path + "\\Data.csv");
			new ErrorView(path);
		}
	}
}