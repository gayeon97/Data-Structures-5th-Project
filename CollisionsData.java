package project5;

import java.util.ArrayList;

/**
 * This class represents an AVL tree that stores all of the collision records 
 * It uses the BST tree implementation provided to us 
 * and is specialized to work with Collision class. 
 * 
 * @author Gayeon_Park
 *
 */
public class CollisionsData {
	
	// root of the tree
	protected Node<Collision> root;
	// current number of nodes in the AVL tree
	protected int numOfElements;
	//helper variable used by the remove methods 
	private boolean found;

	/**
	 * Default constructor that creates an empty tree.
	 */
	public CollisionsData(){
		this.root = null;
		numOfElements = 0;
	}
	
	/**
	 * This method adds the given Collision object to the AVL tree. 
	 * If the item is null or if item already exists, the AVL tree does not change.  
	 * 
	 * @param item the new element to be added to the tree
	 */
	public void add(Collision item) {
		if (item == null)
			return;
		root = recAdd(root, item);
	}

	/* 
	 * Actual recursive implementation of add. 
	 * If the Collision item that you are trying to add does not exist or is not null,
	 * it is added as a leaf and then the whole AVL tree is balanced from updating height & using balanceFactor. 
	 * 
	 * @param item the new element to be added to the AVL tree
	 * @return Node<Collision> If at the very bottom of the AVL tree, the newly created Node<Collision> 
	 * using Collision data is returned. Else, Node<Collision> after balancing 
	 * is returned from the previous recursion call.  
	 */
	private Node<Collision> recAdd(Node<Collision> node, Collision item){
		if (node == null){ 
			numOfElements++;
			return new Node<Collision>(item);
		}
		else if (node.data.compareTo(item) > 0){
			node.left = recAdd(node.left, item);
			
			updateHeight(node);
			if (balanceFactor(node) == -2){
				if (balanceFactor(node.left) > 0){
					node = balanceLR(node);
				} else if (balanceFactor(node.left) <= 0){
					node = balanceLL(node);
				}
			}
		}	
		else if (node.data.compareTo(item) < 0){
			node.right = recAdd(node.right, item);
			
			updateHeight(node);
			if (balanceFactor(node) == 2){
				if (balanceFactor(node.right) < 0){
					node = balanceRL(node);
				} else if (balanceFactor(node.right) >= 0){
					node = balanceRR(node);
				}
			}
		}
		return node; 
	}

	/**
	 * Returns true if the target was removed from the AVL tree and the AVL tree is changed  
	 * or returns false if the target is null or was not found in the AVL tree, hence not removed,
	 * and the AVL tree remains unchanged,.
	 * 
	 * @param target the item to be removed from this tree 
	 * @return true if the target was removed or false if the target was NOT removed
	 */
	public boolean remove(Collision target){
		root = recRemove(target, root);
		return found;
	}

	/*
	 * This is a recursive implementation of remove method: find the node to remove.  
	 * 
	 * @param target the item to be removed from this tree 
	 * @return the new root of the subtree after a node has been removed
	 */
	private Node<Collision> recRemove(Collision target, Node<Collision> node){
		if (node == null)
			found = false;
		else if (target.compareTo(node.data) < 0){
			node.left = recRemove(target, node.left);				

			updateHeight(node);
			if (balanceFactor(node) == 2){
				if (balanceFactor(node.right) < 0){
					node = balanceRL(node);
				} else if (balanceFactor(node.right) >= 0){
					node = balanceRR(node);
				}
			}
		} else if (target.compareTo(node.data) > 0){
				node.right = recRemove(target, node.right );
				
				updateHeight(node);
				if (balanceFactor(node) == -2){
					if (balanceFactor(node.left) > 0){
						node = balanceLR(node);
					} else if (balanceFactor(node.left) <= 0){
						node = balanceLL(node);
					}
				}
		} else {
			numOfElements --;
			node = removeNode(node);
			//updateHeight(node); //FORGOT TO WRITE THIS LINE
			if (balanceFactor(node) == 2){
				if (balanceFactor(node.right) < 0){
					node = balanceRL(node);
				} else if (balanceFactor(node.right) >= 0){
					node = balanceRR(node);
				}
			}
			//NEED ALL 4 ROTATIONS
//			if (balanceFactor(node) == -2){
//				if (balanceFactor(node.left) > 0){
//					node = balanceLR(node);
//				} else if (balanceFactor(node.left) <= 0){
//					node = balanceLL(node);
//				}
//			}
			found = true;
		}
		return node;
	}

	/*
	 * This is another actual recursive implementation of remove method: 
	 * 	performs the actual removal of the node to be removed from recRemove method above.  
	 * 
	 * @param target the item to be removed from this tree 
	 * @return a reference to the node itself, or to the modified subtree 
	 */
	private Node<Collision> removeNode(Node<Collision> node){
		Collision data;
		if (node.left == null)
			return node.right;
		else if (node.right  == null)
			return node.left;
		else {
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}

	/*
	 * Returns the information held in the rightmost node of subtree  
	 * This is taken from the BST_Recursive.java file given to us by Joanna.
	 * 
	 * @param subtree root of the subtree within which to search for the rightmost node 
	 * @return returns data stored in the rightmost node of subtree  
	 */
	private Collision getPredecessor(Node<Collision> subtree){
		if (subtree == null) throw new NullPointerException("getPredecessor called with an empty subtree");
		Node<Collision> temp = subtree;
		while (temp.right  != null)
			temp = temp.right ;
		return temp.data;
	}

	/**
	 * Determines the number of elements stored in this BST.
	 * This is taken from the BST_Recursive.java file given to us by Joanna.
	 * 
	 * @return number of elements in this BST
	 */
	public int size() {
		return numOfElements;
	}
	
	/*
	 * This method updates the height of the Node<Collision> n.
	 * This code is taken from the lecture note.
	 * @param current
	 */
	private void updateHeight(Node<Collision> current){
		if (current == null){
			return;
		} if (current.left == null && current.right == null){
			current.height = 0;
		} else if (current.left == null){
			current.height = current.right.height + 1;
		} else if (current.right == null){
			current.height = current.left.height + 1;
		} else 
			current.height = Math.max(current.right.height, current.left.height) + 1;
	}
	
	/*
	 * This method returns the balance factor for the Node<Collision> n.
	 * This code is taken from the lecture note.
	 * 
	 * @param n
	 * @return the integer value that represents the balanceFactor of the node.
	 */
	private int balanceFactor(Node<Collision> n){
		if (n == null){
			return -1;
		}
		if (n.right == null){
			return -n.height;
		}
		if (n.left == null){
			return n.height;
		}
		return n.right.height - n.left.height;
	}
	
	/*
	 * This method balances the AVL tree using LL rotation.
	 * This code is taken from the lecture note.
	 * 
	 * @param A
	 * @return the new node that's now the root of the sorted subtree
	 */
	private Node<Collision> balanceLL(Node<Collision> A){
		Node<Collision> B= A.left;
		
		A.left = B.right;
		B.right = A;
		
		updateHeight(A);
		updateHeight(B);
		
		return B;
	}
	
	/*
	 * This method balances the AVL tree using RR rotation.
	 * This code is taken from the lecture note.
	 * 
	 * @param A
	 * @return the new node that's now the root of the sorted subtree
	 */
	private Node<Collision> balanceRR(Node<Collision> A){
		Node<Collision> B= A.right;
		
		A.right = B.left;
		B.left = A;
		
		updateHeight(A);
		updateHeight(B);
		
		return B;
	}
	
	/*
	 * This method balances the AVL tree using LR rotation.
	 * This code is taken from the lecture note.
	 * 
	 * @param A
	 * @return the new node that's now the root of the sorted subtree
	 */
	private Node<Collision> balanceLR(Node<Collision> A){
		Node<Collision> B= A.left;
		Node<Collision> C = B.right;
		
		A.left = C.right;
		B.right = C.left;
		C.left = B;
		C.right = A;
		
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
		
		return C;
	}

	/*
	 * This method balances the AVL tree using RL rotation.
	 * This code is taken from the lecture note.
	 * 
	 * @param A
	 * @return the new node that's now the root of the sorted subtree
	 */
	private Node<Collision> balanceRL(Node<Collision> A){
		Node<Collision> B= A.right;
		Node<Collision> C = B.left;
		
		A.right = C.left;
		B.left = C.right;
		C.right = B;
		C.left = A;
		
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
		
		return C;
	}
	
	/**
	 * Returns a string representation of this tree using an inorder traversal.
	 * This is taken from the BST_Recursive.java file given to us by Joanna. 
	 * @see java.lang.Object#toString()
	 * @return string representation of this tree 
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		inOrderPrint(root, s);
		return s.toString();
	}

	/* 
	 * Actual recursive implementation of inorder traversal to produce string
	 * representation of this tree.
	 * This is taken from the BST_Recursive.java file given to us by Joanna.
	 * 
	 * @param tree the root of the current subtree
	 * @param s the string that accumulated the string representation of this BST
	 */
	private void inOrderPrint(Node<Collision> tree, StringBuilder s) {
		if (tree != null) {
			inOrderPrint(tree.left, s);
			s.append(tree.data.toString() + "  ");
			inOrderPrint(tree.right , s);
		}
	}

	/**
	 * Returns a string that contains graphical representation of this tree. 
	 * Produces tree like string representation of this BST.
	 * This is taken from the BST_Recursive.java file given to us by Joanna.
	 * 
	 * @return string containing tree-like representation of this BST.
	 */
	public String toStringTreeFormat() {
		StringBuilder s = new StringBuilder();

		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * Actual recursive implementation of preorder traversal to produce tree-like string
	 * representation of this tree.
	 * This is taken from the BST_Recursive.java file given to us by Joanna.
	 * 
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to
	 *   determine the indentation of each item
	 * @param output the string that accumulated the string representation of this
	 *   BST
	 */
	private void preOrderPrint(Node<Collision> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}
	
	//this will be used in the find() and getReport() method 
	//to store all the Collision objects that meet the criteria given by the parameters of 
	//the getReport method (same zip, date in the correct date range)
	private ArrayList<Collision> match = new ArrayList<Collision>();
	
	/*
	 * This method recursively finds the Collision object with the same zip and date that 
	 * falls in the date range given by the parameter
	 * 
	 * @param zip
	 * @param beginDate
	 * @param endDate
	 * @param n
	 */
	private void find(String zip, Date beginDate, Date endDate, Node<Collision> n){
		if (n == null){
			return;
		}
		
		//if the zip of the current node is equal to the zip given
		//and the date is within the range of the dates given, 
		//add the data of the current node to the ArrayList<Collision> match
		if (Integer.parseInt(zip) == Integer.parseInt(n.data.getZip()) 
				&& (n.data.getDate()).compareTo(beginDate) >= 0 
				&& (n.data.getDate()).compareTo(endDate) <= 0){
			match.add(n.data);
		}
		
		//if the zip of the current node is greater than the zip given, 
		//go to the left node to keep finding the node with qualifying data
		if (Integer.parseInt(zip) < Integer.parseInt(n.data.getZip())){
			find(zip,beginDate,endDate,n.left); 
		} 
		//if the zip of the current node is less than the zip given, 
		//go to the right node to keep finding the node with qualifying data
		else if (Integer.parseInt(zip) > Integer.parseInt(n.data.getZip())){
			find(zip,beginDate,endDate,n.right); 
		} else { //if the zip of the current node is equal to the zip given		
			//if the date of the current node is less than the start date
			//go to the right node to keep finding the node with qualifying data
			if (n.data.getDate().compareTo(beginDate) < 0){
				//Recur on the right subtree
				find(zip,beginDate,endDate,n.right); 
			}
			//if the date of the current node is greater than the end date
			//go to the left node to keep finding the node with qualifying data
			else if (n.data.getDate().compareTo(endDate) > 0){
				//Recur on the left subtree
				find(zip,beginDate,endDate,n.left); 
			}
			//if the date of the current node is within or equal to the date range specified by the parameter,
			//go to both left and right node to keep finding the node with qualifying data
			else if (n.data.getDate().compareTo(endDate) <= 0 && n.data.getDate().compareTo(beginDate) >= 0){
				//Recur on the left subtree
				find(zip,beginDate,endDate,n.left); 
				find(zip,beginDate,endDate,n.right); 
			} 
		}
	}
	
	/**
	 * Returns a string containing info about the total number of fatalities and injuries
	 * along with the breakdown of each for pedestrians, cyclists, and motorists
	 * for a given zip code and date range.
	 * 
	 * @param zip
	 * @param dateBegin
	 * @param dateEnd
	 * @return a string that represents the summary of the collisions that occured in the 
	 * given zip code within the specified dates, inclusive. 
	 */
	public String getReport(String zip, Date dateBegin, Date dateEnd){
		int numCollisions = 0;
		int totFatalities = 0;
		int totPedFatalities = 0;
		int totCycFatalities = 0;
		int totMotFatalities = 0;
		
		int totInjuries = 0;
		int totPedInjuries = 0;
		int totCycInjuries = 0;
		int totMotInjuries = 0;
		
		match.clear();
		find(zip,dateBegin,dateEnd,root);
		
		for (int i = 0; i < match.size(); i ++){
			numCollisions ++;
			totFatalities += match.get(i).getPersonsKilled();
			totPedFatalities += match.get(i).getPedestriansKilled();
			totCycFatalities += match.get(i).getCyclistsKilled();
			totMotFatalities += match.get(i).getMotoristsKilled();

			totInjuries += match.get(i).getPersonsInjured();
			totPedInjuries += match.get(i).getPedestriansInjured();
			totCycInjuries += match.get(i).getCyclistsInjured();
			totMotInjuries += match.get(i).getMotoristsInjured();
		}
		
		String s1 = String.format("Total number of collisions: %d", numCollisions);
		String s2 = String.format("Number of fatalities: %d", totFatalities);
		String s3 = String.format("%20s:%2d", "pedestrians", totPedFatalities);
		String s4 = String.format("%20s:%2d", "cyclists", totCycFatalities);
		String s5 = String.format("%20s:%2d", "motorists", totMotFatalities);
		String s6 = String.format("Number of injuries: %d", totInjuries);
		String s7 = String.format("%18s:%2d", "pedestrians", totPedInjuries);
		String s8 = String.format("%18s:%2d", "cyclists", totCycInjuries);
		String s9 = String.format("%18s:%2d", "motorists", totMotInjuries);

		return s1 + '\n' + s2 + '\n' + s3 + '\n' + s4 + '\n' + s5 + '\n' 
				+ s6 + '\n' + s7 + '\n' + s8 + '\n' + s9;		
	}

	/**
	 * Node class is used to represent nodes in a binary search tree.
	 * It contains a data item that has to implement Comparable interface
	 * and references to left and right subtrees. 
	 * 
	 * @author Joanna Klukowska
	 *
	 * @param <T> a reference type that implements Comparable<T> interface 
	 */
	protected static class Node <Collision extends Comparable <Collision>> 
	implements Comparable <Node <Collision> > {


		protected Node <Collision> left;  //reference to the left subtree 
		protected Node <Collision> right; //reference to the right subtree
		protected Collision data;            //data item stored in the node
		protected int height; 				//the height of the node

		/**
		 * Constructs a BSTNode initializing the data part 
		 * according to the parameter and setting both 
		 * references to subtrees to null.
		 * @param data
		 *    data to be stored in the node
		 */
		protected Node(Collision data) {
			this.data = data;
			left = null;
			right = null;
			height = 0; 
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Node<Collision> other) {
			return this.data.compareTo(other.data);
		} 

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return data.toString();
		}
	}
}
