
public class HNode<Object> {


	private Object data;
	private char flag;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public char getFlag() {
		return flag;
	}
	public void setFlag(char flag) {
		this.flag = flag;
	}
	public HNode(Object data, char flag) {
		super();
		this.data = data;
		this.flag = flag;
	}

	public String toString(){

		return data+"";
	}
	






}
