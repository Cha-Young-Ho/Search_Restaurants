package Model;

public class Record {

	private String name = "등록되지 않음"; //업소명
	private String type = "등록되지 않음"; // 업태
	private String state = "등록되지 않음"; // 시도
	private String city = "등록되지 않음"; // 시군구
	private String address = "등록되지 않음"; // 주소
	private String menu = "등록되지 않음"; //주메뉴
	private String number = "등록되지 않음"; //전화번호

	public Record(String state, String city, String type, String menu, String name, String address, String number){
		this.state = state;
		this.name = name;
		this.type = type;
		this.city = city;
		this.type = type;
		this.menu = menu;
		this.address = address;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}

	public String getMenu() {
		return menu;
	}

	public String getNumber() {
		return number;
	}
}
