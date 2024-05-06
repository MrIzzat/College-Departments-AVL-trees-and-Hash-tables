import java.io.File;
import java.io.IOException;

public class AVLTree<T extends Comparable<T>> extends BinaryTree<T>{

	//private Node<T> root;

	public AVLTree () {
		super();
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	private Node rotateRight(Node n) {
		if(n!=null) {
			Node<T> c = n.getLeft();
			if(c!=null) {
				n.setLeft(c.getRight());
				c.setRight(n);
				return c;
			}else return null;
		}else return null;
	}

	private Node rotateLeft(Node n) {
		if(n!=null) {
			Node<T> c = n.getRight();
			if(c!=null) {
				n.setRight(c.getLeft());
				c.setLeft(n);
				return c;
			}else return null;
		} return null;
	}

	private Node rotateRightLeft(Node n) { // needs review

		if(n!=null) {
			Node c = n.getRight();
			if(c!=null) {
				n.setRight(rotateRight(c));
				return rotateLeft(n);
			}else return null;
		}
		else return null;
	}

	private Node rotateLeftRight(Node n) {
		Node c = n.getLeft();
		n.setLeft(rotateLeft(c));
		return rotateRight(n);
	}

	private int getHeightDifference(Node n) {

		if(n!=null) {
			return super.height(n.getLeft())-super.height(n.getRight());
		}
		return 0;

	}

	public void insert(T data,File file) {

		if(isEmpty()) root = new Node<>(null,null,data,file);
		else {
			Node rooNode = root;
			addEntry(data, rooNode,file);
			root = rebalance(rooNode);
		}
	} 

	public void addEntry(T data, Node rooNode,File file){
		assert rooNode != null;
		if(data.compareTo((T)rooNode.getData()) < 0){ // right into left subtree
			if(rooNode.hasLeft()){
				Node leftChild = rooNode.getLeft();
				addEntry(data, leftChild,file);
				rooNode.setLeft(rebalance(leftChild));
			}
			else rooNode.setLeft(new Node(null,null,data,file));
		}
		else { // right into right subtree
			if(rooNode.hasRight()){
				Node rightChild = rooNode.getRight();
				addEntry(data, rightChild,file);
				rooNode.setRight(rebalance(rightChild));
			}
			else rooNode.setRight(new Node(null,null,data,file));
		}
	}


	private Node rebalance(Node nodeN){

		if(nodeN!=null) {
			int diff = getHeightDifference(nodeN);
			if ( diff > 1) { // addition was in node's left subtree
				if(getHeightDifference(nodeN.getLeft())>0)
					nodeN = rotateRight(nodeN);
				else
					nodeN = rotateLeftRight(nodeN);
			}
			else if ( diff < -1){ // addition was in node's right subtree
				if(getHeightDifference(nodeN.getRight())<0)
					nodeN = rotateLeft(nodeN);
				else
					nodeN = rotateRightLeft(nodeN);
			}
			return nodeN;
		}
		return null;
	}

	public Node delete(T data) {
		Node temp = super.delete(data);
		if(temp!= null){
			Node rooNode = root;
			root = rebalance(rooNode);
		}
		return temp;
	}

	public boolean isEmpty() {
		return root==null;
	}

	public boolean isBalanced(Node node){
		int lh; 

		int rh; 


		if (node == null)
			return true;

		lh = height(node.getLeft());
		rh = height(node.getRight());

		if (Math.abs(lh - rh) <= 1
				&& isBalanced(node.getLeft())
				&& isBalanced(node.getRight()))
			return true;

		/* If we reach here then tree is not height-balanced */
		return false;
	}
	
	public String stringInOrder() {
		if(root==null) {
			//System.out.println("Tree is empty");
			return "Tree is Empty";
		}else {
			return stringInOrder(root,"");
		}

	}

	private String stringInOrder(Node node,String str) {
		if(node!=null) {	
			return 	stringInOrder(node.getLeft(),str)+node.toString()+"\n"+	stringInOrder(node.getRight(),str);
		}
		return "";
	}
	
	public String fileInOrder() throws IOException {
		if(root==null) {
			//System.out.println("Tree is empty");
			return "Tree is Empty";
		}else {
			return fileInOrder(root,"");
		}

	}

	private String fileInOrder(Node node,String str) throws IOException {
		if(node!=null) {	
			
			//System.out.println(stringInOrder(node.getLeft(),str)+node.forFile()+"\n"+	stringInOrder(node.getRight(),str));
			return 	fileInOrder(node.getLeft(),str)+node.forFile()+"\n"+fileInOrder(node.getRight(),str);
		}
		return "";
	}
}
