import java.io.File;
import java.io.IOException;

public class Node<T extends Comparable<T>> {
	
	private Node<T> left;
	private Node<T> right;
	private T data;
	private File studentInfo;
	
	
	public Node(Node<T> left, Node<T> right, T data,File file) {

		this.left = left;
		this.right = right;
		this.data = data;
		studentInfo = file;
	}
	public Node() {

	}
	public Node<T> getLeft() {
		return left;
	}
	public void setLeft(Node<T> left) {
		this.left = left;
	}
	public Node<T> getRight() {
		return right;
	}
	public void setRight(Node<T> right) {
		this.right = right;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public boolean isLeaf() {
		return left==null&&right==null;
	}
	
	public boolean hasLeft() {
		return left!=null;
	}
	
	public boolean hasRight() {
		return right!=null;
	}
	
	public String forFile() throws IOException{
		return data+"/"+studentInfo.getPath();
	//	return "";
	}
	
	public String toString() {
		return data+"";
	}
	public File getStudentInfo() {
		return studentInfo;
	}
	public void setStudentInfo(File studentInfo) {
		this.studentInfo = studentInfo;
	}
	
}
