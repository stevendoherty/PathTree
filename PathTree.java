import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;



public class PathTree<E> implements Iterable<E>{

	private Node root=new Node();
	private int size=0;

	//default constructor
	public PathTree() {

	}

	/**
	 * Puts all the elements of the tree into the array in depth first order.
	 * 
	 * @param array
	 * @return E[]
	 */
	public E[] toArray(E[] array) {

		buildArray(root, array, 0);

		return array;
	}
	
	
	//private class that recursively fills an array with the elements of the tree
	//does a depth first search
	private int buildArray(Node r, E[] array, int i) {
		LinkedList<Node> children=r.getChildren();

		for(Node n: children) {
			if (n==null) {
				return i;
			}else {
				i=buildArray(n, array, i);
				array[i]=(E) n.getData();
				i++;
			}
		}
		return i;
	}

	
	/**
	 * adds the object as a child of the root node, returns false if the child already exists
	 * 
	 * @param e
	 * @return boolean
	 */
	public boolean add(Object e) {
		if (root.contains(e)) {
			return false;
		}
		Node temp=new Node(e);
		root.addChild(temp);
		size++;
		return true;
	}

	/**
	 * adds the contents of the collection into the tree, returns true if a new path was made
	 * 
	 * @param path
	 * @return boolean
	 */
	public boolean add(Collection<E> path) {
		Node currentRoot=root;
		boolean ret=false;
		
		for(Object n: path) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				Node temp=new Node(n);
				currentRoot.addChild(temp);
				size++;
				currentRoot=currentRoot.getChild(n);
				ret=true;
			}else {
				currentRoot=child;
			}
		}
		return ret;
	}

	/**
	 * adds the contents of the array into the tree, returns true if a new path was made
	 * 
	 * @param path
	 * @return boolean
	 */
	public boolean add(Object[] path) {
		Node currentRoot=root;
		boolean ret=false;
		
		for(Object n: path) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				Node temp=new Node(n);
				currentRoot.addChild(temp);
				size++;
				currentRoot=currentRoot.getChild(n);
				ret=true;
			}else {
				currentRoot=child;
			}
		}
		return ret;
	}

	



	/**
	 * checks if the path is in the tree, returns 1+the maximum index in the array if it is found, returns the index of first spot that doesn't exist in the path
	 * 
	 * @param path
	 * @return integer
	 */
	public int contains(Object[] path) {
		Node currentRoot=root;
		int i=0;
		for(Object n: path) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				return i;
			}else {
				currentRoot=child;
			}
			i++;
		}
		return i;
	}
	
	
	/**
	 * checks if the path is in the tree, returns 1+the maximum index in the collection if it is found, returns the index of first spot that doesn't exist in the path
	 * 
	 * @param path
	 * @return integer
	 */
	public int containsdata(Collection<E> path) {
		Node currentRoot=root;
		int i=0;
		for(Object n: path) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				return i;
			}else {
				currentRoot=child;
			}
			i++;
		}
		return i;
	}




	/**
	 * Returns an array of the children for the path given, if the path does not exist it will return null
	 * 
	 * @param path
	 * @return Object[]
	 */
	public Object[] getChildren(Collection<E> path) {
		Node currentRoot=root;

		for(Object n: path) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				return null;
			}else {
				currentRoot=child;
			}
		}
		return currentRoot.getChildrenData().toArray();
	}
	
	
	/**
	 * Returns an array of the children for the path given, if the path does not exist it will return null
	 * 
	 * @param path
	 * @return Object[]
	 */
	public Object[] getChildren(Object[] path) {
		Node currentRoot=root;

		for(Object n: path) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				return null;
			}else {
				currentRoot=child;
			}
		}
		return currentRoot.getChildrenData().toArray();
	}

	
	
	/**
	 * returns an array of the children of the root
	 * 
	 * @return
	 */
	public Object[] getRootChildren() {
		return root.getChildrenData().toArray();
	}

	/**
	 * Clears the data structure
	 * 
	 */
	public void clear() {

		clear(root);
		root=new Node();
		size=0;
		return ;

	}

	//private function that clears the structure
	private void clear(Node root) {

		LinkedList<Node> children=root.getChildren();

		for(Node n: children) {
			if (n==null) {
				return;
			}else {
				clear(n);
				n=null;
			}
		}
		return ;

	}

	/**
	 * returns true if the tree is empty, else returns false
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return size==0;
	}

	/**
	 * returns an iterator for the tree, it iterates in depth first order
	 *
	 * @return Iterator
	 */
	public Iterator iterator() {
		return new Itr();
	}

	/**
	 * returns the size of the structure
	 * 
	 * @return integer
	 */
	public int size() {
		return size;
	}

	private class Itr implements Iterator<E> {
		private int index=0;
		private E[] items;

		private Itr(){
			items=(E[]) new Object[size];
			items=toArray(items);
		}

		@Override
		public boolean hasNext() {
			return index<items.length;
		}

		@Override
		public E next() {
			E ret=items[index];
			index++;
			return ret;
		}

	}


	private class Node<E>{
		private E data;
		private LinkedList<Node> children=new LinkedList<Node>();

		private Node() {

		}
		private Node(Object element) {
			data=(E) element;
		}
		private boolean addChild(Node child) {
			children.add(child);
			return true;
		}
		private boolean addChild(Object child) {
			Node c=new Node(child);
			children.add(c);
			return true;
		}
		private E getData() {
			return data;
		}

		private boolean contains(Object n) {
			for(Node c: children) {
				if (c.data.equals(n)) {
					return true;
				}
			}
			return false;
		}
		private Node getChild(Object n) {
			for(Node c: children) {
				if (c.data.equals(n)) {
					return c;
				}
			}

			return null;
		}
		private LinkedList<Node> getChildren() {
			return children;
		}
		private LinkedList<E> getChildrenData() {
			LinkedList<E> ret=new LinkedList<E>();

			for (Node n: children) {
				ret.add((E)n.getData());
			}

			return ret;
		}

	}



}

