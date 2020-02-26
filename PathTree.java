import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;



public class PathTree<E> implements Iterable<E>{

	private Node root=new Node();
	private int size=0;




	//default constructor
	public PathTree() {

	}

	public E[] toArray(E[] array) {

		buildArray(root, array, 0);

		return array;
	}

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

	public boolean add(Object e) {
		if (root.contains(e)) {
			return false;
		}
		Node temp=new Node(e);
		root.addChild(temp);
		size++;
		return true;
	}

	public boolean addAll(Collection data) {
		Node currentRoot=root;

		for(Object n: data) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				Node temp=new Node(n);
				currentRoot.addChild(temp);
				size++;
				currentRoot=currentRoot.getChild(n);
			}else {
				currentRoot=child;
			}
		}
		return true;
	}

	public boolean addAll(Object[] data) {
		Node currentRoot=root;

		for(Object n: data) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				Node temp=new Node(n);
				currentRoot.addChild(temp);
				size++;
				currentRoot=currentRoot.getChild(n);
			}else {
				currentRoot=child;
			}
		}
		return true;
	}

	public boolean addRootChild(Object n) {
		if(getRootChildren().contains(n)) {
			return false;
		}
		Node temp=new Node(n);
		root.addChild(temp);
		size++;
		return true;

	}



	public int contains(Object[] data) {
		Node currentRoot=root;
		int i=0;
		for(Object n: data) {
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
	public int contains(Collection data) {
		Node currentRoot=root;
		int i=0;
		for(Object n: data) {
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




	public LinkedList<E> getChildren(Collection data) {
		Node currentRoot=root;

		for(Object n: data) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				return new LinkedList<E>();
			}else {
				currentRoot=child;
			}
		}
		return currentRoot.getChildrenData();
	}
	public LinkedList<E> getChildren(Object[] data) {
		Node currentRoot=root;

		for(Object n: data) {
			Node child=currentRoot.getChild(n);
			if(child==null) {
				return new LinkedList<E>();
			}else {
				currentRoot=child;
			}
		}
		return currentRoot.getChildrenData();
	}

	public LinkedList<E> getRootChildren() {
		return root.getChildrenData();
	}

	public void clear() {

		clear(root);
		root=new Node();
		size=0;
		return ;

	}

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

	public boolean isEmpty() {
		return size==0;
	}

	public Iterator iterator() {
		return new Itr();
	}

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

	public static void main(String args[]) {
		PathTree<String> tree=new PathTree();
		LinkedList<String> list=new LinkedList();
		list.add("first");
		list.add("second");
		list.add("third");
		tree.addAll(list);
		tree.addRootChild("root2");
		tree.addRootChild("root3");
		String root=null;
		list=new LinkedList();
		String[] s= {"second", "first", "nest"};
		list.add("second");
		list.add("first");
		list.add("nest");
		tree.addAll(s);
		String[] s1= {"first", "one", "nest"};
		tree.addAll(s1);
		String[] s2= {"first", "one", "hello"};
		tree.addAll(s2);


		String[] l=new String[tree.size()];
		l=tree.toArray(l);


		System.out.println("__________");
		for(String n: tree) {
			System.out.println(n);
		}
		
		tree.clear();
		System.out.println("__________");
		for(String n: tree) {
			System.out.println(n);
		}
		

	}






}

