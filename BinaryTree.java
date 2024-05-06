import java.io.File;

public class BinaryTree<T extends Comparable<T>> {

	protected Node<T> root;

	public BinaryTree() {

	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public void traverseInOrder() {
		if(root==null) {
			System.out.println("Tree is empty");
		}else {
			traversalInOrder(root);
		}

	}

	private void traversalInOrder(Node node) {
		if(node!=null) {
			traversalInOrder(node.getLeft());
			System.out.println(node.toString());
			traversalInOrder(node.getRight());
		}
	}

	public void traversePreOrder() {
		if(root==null) {
			System.out.println("Tree is empty");
		}else {
			traversalPreOrder(root);
		}
	}

	private void traversalPreOrder(Node node) {

		if(node!=null) {
			System.out.println(node.getData());
			traversalPreOrder(node.getLeft());
			traversalPreOrder(node.getRight());
		}
	}

	public void traversePostOrder() {
		if(root==null) {
			System.out.println("Tree is empty");
		}else {
			traversalPostOrder(root);
		}
	}

	private void traversalPostOrder(Node node) {
		if(node!=null) {
			traversalPostOrder(node.getLeft());
			traversalPostOrder(node.getRight());
			System.out.println(node.getData());
		}
	}

	public Node Search(T data) {
		if(root==null) {
			return null;
		}else {
			return Search(root,data);
		}
	}

	private Node Search(Node node,T data) {

		if(node!=null) {
			if(node.getData().equals(data)) {
				return node;
			}else {
				if(node.getData().compareTo(data)>0) {
					return Search(node.getLeft(),data);
				}else {
					return Search(node.getRight(),data);

				}

			}

		}else {
			return null;
			
		}

	}

	public int Length() {
		if(root==null) {
			//	System.out.println("Tree is empty");
			return 0;
		}else {
			return length(root);
		}
	}

	private int length(Node node) {

		if(node!=null) {
			return 1+length(node.getLeft())+length(node.getRight());
		}
		return 0;
	}

	public void insert(T data,File file) {
		insert(root,data,file);
	}

	private void insert(Node node,T data,File file) {
		if(root==null) {
			root = new Node(null,null,data,file);
		}else {
			if(node.getData().compareTo(data)>=0) {
				if(node.getLeft()==null) {
					node.setLeft(new Node(null,null,data,file));
				}else {
					insert(node.getLeft(),data,file);
				}
			}else {
				if(node.getRight()==null) {
					node.setRight(new Node(null,null,data,file));
				}
				else {
					insert(node.getRight(),data,file);
				}

			}

		}


	}


	public void levelOrderTraversal() {

		if(root == null) {
			System.out.println("Tree is empty");
			return;
		}

		LinkedQueue<Node<T>> temp = new LinkedQueue<Node<T>>();

		temp.enqueue(root);

		while(!temp.isEmpty()) {

			QNode<T> temp3 = (QNode<T>) temp.dequeue();

			Node<T> temp2 = (Node<T>) temp3.getData();

			System.out.println(temp2.getData());

			if(temp2.getLeft()!=null) {  //left and right can be switched
				temp.enqueue(temp2.getLeft());
			}

			if(temp2.getRight()!=null) {
				temp.enqueue(temp2.getRight());
			}



		}



	}

	public  boolean isValidBST() { // to make sure my code works perfectly
		return isValidBST(root, null, null);
	}

	private  boolean isValidBST(Node root, Integer max, Integer min) {

		// an empty binary trees is a valid BST.
		if (root == null) return true; 

		
       		 if (max != null && root.getData().compareTo( max)>0) return false;

		if (min != null && root.getData().compareTo( min) <=0 ) return false;

		return isValidBST(root.getLeft(), (Integer) root.getData(), min) && 
				isValidBST(root.getRight(), max, (Integer) root.getData());
	}


	public int Largest() {
		if(root==null) {
			return 0;
		}else {
			return largest(root);
		}
	}

	private int largest(Node node) {

		if(node!=null) {
			if(!node.hasRight()) {
				return (Integer)node.getData();
			}else {
				return largest(node.getRight());
			}
		}else {
			return 0;
		}


	}

	public int Smallest() {
		if(root==null) {
			return 0;
		}else {
			return smallest(root);
		}
	}

	private int smallest(Node node) { // might need to convert to T need to test

		if(node!=null) {
			if(!node.hasLeft()) {
				return (Integer)node.getData();
			}else {
				return smallest(node.getLeft());
			}
		}else {
			return 0;
		}


	}

	public int Height() {
		if(root==null) {
			return 0;
		}else {
			return height(root);
		}
	}

	protected int height(Node node) {
		if(node==null)
			return 0;
		if(node.isLeaf())
			return 1;

		int left=0;
		int right=0;

		if(node.hasLeft()) {
			left = height(node.getLeft());
		}
		if(node.hasRight()) {
			right=height(node.getRight());
		}

		return (left>right)?(left+1):(right+1);
	}

	public Node getSuccessor(Node node) { // does not work properly ask doctor about it

		Node parentOfSuccessor = node;
		Node successor = node;
		Node current = node.getRight();
		while (current != null) {
			parentOfSuccessor = successor;
			successor = current;
			current = current.getLeft();
		}
		if (successor != node.getRight()) { // fix successor connections
			parentOfSuccessor.setLeft(successor.getRight());
			successor.setRight(node.getRight());
		}
		return successor; 
	}


	public Node delete(T data) {

		Node current = root;
		Node parent = root;
		boolean isLeftChild = false;
		if (isEmpty()) return null; // tree is empty
		while (current != null && !current.getData().equals(data)) {
			parent = current;
			if (data.compareTo((T)current.getData()) < 0) {
				current = current.getLeft();
				isLeftChild = true;
			} else {
				current = current.getRight();
				isLeftChild = false;
			}
		}
		if (current == null) return null; // node to be deleted not found
		// case 1: node is a leaf
		if (!current.hasLeft() && !current.hasRight()) {
			if (current == root) // tree has one node
				root = null;
			else {
				if (isLeftChild) parent.setLeft(null);
				else parent.setRight(null);
			}
		}

		// Case 2 broken down further into 2 separate cases
		else if (current.hasLeft()&&!current.hasRight()) { // current has left child only
			if (current == root) {
				root = current.getLeft();
			} else if (isLeftChild) {
				parent.setLeft(current.getLeft());
			} else {
				parent.setRight(current.getLeft()); 
			}
		} else if (current.hasRight()&&!current.hasLeft()) { // current has right child only
			if (current == root) {
				root = current.getRight();
			} else if (isLeftChild) {
				parent.setLeft(current.getRight());
			} else {
				parent.setRight(current.getRight());
			}
		}
		// case 3: node to be deleted has 2 children
		else {
			Node successor = getSuccessor(current);
			if (current == root)
				root = successor;
			else if (isLeftChild) {
				parent.setLeft(successor);
			} else {
				parent.setRight(successor);
			}
			successor.setLeft(current.getLeft()); 
		}
		// other cases
		return current;
	}

	//	 public void delete(Integer data) { 
	//	        deleteNode(this.root, data);
	//	    }

	/*	private Node deleteNode(Node root, Integer data) { // only works for integers

        if(root == null) return root;

        if(data < (Integer) root.getData()) {
            root.setLeft(deleteNode(root.getLeft(), data));
        } else if(data >(Integer) root.getData()) {
            root.setRight(deleteNode(root.getRight(), data));
        } else {
            // node with no leaf nodes
            if(root.getLeft() == null && root.getRight() == null) {
             //   System.out.println("deleting "+data);
                return null;
            } else if(root.getLeft() == null) {
                // node with one node (no left node)
             //   System.out.println("deleting "+data);
                return root.getRight();
            } else if(root.getRight() == null) {
                // node with one node (no right node)
              //  System.out.println("deleting "+data);
                return root.getLeft();
            } else {
                // nodes with two nodes
                // search for min number in right sub tree
                Integer minValue = smallest(root.getRight());
                root.setData(minValue);
                root.setRight(deleteNode(root.getRight(), minValue));
            //    System.out.println("deleting "+data);
            }
        }

        return root;
    } */

	public boolean isEmpty() {
		return root==null;
	}


}
