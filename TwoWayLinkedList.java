import java.util.AbstractSequentialList;

import javax.xml.soap.Node;

import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.ListIterator;


public class TwoWayLinkedList<E> extends AbstractSequentialList<E>{
	private Node<E> list;
	private int size;
	private Node<E> head;
	private Node<E> tail;
	private Node<E> cursor;

	
	public TwoWayLinkedList(){
		clear();
	}
	
	public TwoWayLinkedList(E[] objects){
		super();
	}
	
	public E getFirst(){
		if(size==0){
			return null;
		}
		else{
			return head.element;
		}
	}
	
	public void addFirst(E e){
		add(0, e);
	}
	
	public void addLast(E e){
		add(size(), e);	
	}
	
	public boolean add(E e){
		add(size(), e);
		return true;
	}
	
	@Override
	public void add(int index, E e){
		addBefore(getNode(index, 0, size()), e);
	}
	
	public void addBefore(Node<E> p, E e){
		Node<E> newNode = new Node<E>(e, p.prev, p);
		newNode.prev.next = newNode;
		
		p.prev = newNode;
		size++;

	}
	
	public E removeFirst(){
		if(size==0)
			return null;
		else{
			Node<E> temp = head;
			head = head.next;
			size--;
			if(head==null)
				tail = null;
			

			return temp.element;
		}
	}
	
	public E removeLast(){
		if(size==0)
			return null;
		else if(size==1){
			Node<E> temp = head;
			head = tail = null;
			size=0;
			return temp.element;
		}
		else{
			Node<E> current = head;
			for(int i=0; i<size-2; i++){
				current = current.next;
			}

			Node<E> temp = tail;
			tail = current;
			tail.next = null;
			size--;
			return temp.element;
		}
	}
	
	@Override
	public E remove(int index){
		return remove(getNode(index));
	}

	private E remove(Node<E> p){
		p.next.prev = p.prev;
		p.prev.next = p.next;
		size--;
		return p.element;
	}
	
	public int size(){
		return size;
	}

	public boolean isEmpty(){
		return size() == 0;
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder("[");
		Node<E> current = head;
		for(int i=0; i<size; i++){
			result.append(current.element);
			current = current.next;
			if(current != null){
				result.append(", ");
			}
			else{
				result.append("]");
			}
		}
		return result.toString();
	}
	
	@Override
	public void clear(){
		head = new Node<E>(null, null, null);
		tail = new Node<E>(null, head, null);
		head.next = tail;
		size=0;
	}
	
	public boolean contains(Object e){
		return indexOf(e) >= 0;
	}
	
	@Override
	public E get(int index){
		return getNode(index).element;
	}
	
	public E getLast(){
		return (E) getNode(size()-1);
	}
	
	public int indexOf(Object e){
		Node current = head;
		int result = 0;
		for(int i=0; i<size; i++){
			if(e.equals(current.element)){
				result = i;
				return result;
			}
		}
		return result;
	}
	
	public int lastIndexOf(Object e){
		return indexOf(e);
	}
	
	
	public boolean removeFirstOccurence(E e){
		int result = (Integer) remove(indexOf(e));
		if(result==0)
			return false;
		else
			return true;
	}
	
	public boolean removeLastOccurence(E e){
		int result = (Integer) remove(lastIndexOf(e));
		if(result==0)
			return false;
		else
			return true;
	}
	
	@Override	
	public E set(int index, E newValue){
		Node<E> p = getNode(index);
		E oldValue = p.element;
		p.element = newValue;
		return oldValue;
	}
	

	private Node<E> getNode(int index){
		return getNode(index, 0, size()-1);
	}
	
	@Override
	public java.util.Iterator<E> iterator(){
		return new LinkedListIterator();
	}
	
	
	private class LinkedListIterator implements java.util.Iterator<E>{
		private Node<E> current = head.next;
		private boolean okToRemove = false;
		
		@Override
		public boolean hasNext(){
			return current != tail;
		}
		
		@Override
		public E next(){
			if(!hasNext())
				throw new java.util.NoSuchElementException();
			E nextItem = current.element;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}
		
		@Override
		public void remove(){
			if(!okToRemove)
				throw new IllegalStateException();
			TwoWayLinkedList.this.remove(current.prev);
			okToRemove = false;
		}
	}
	
	public ListIterator<E> listIterator(){
		cursor = head;
		return (ListIterator<E>) head;
		

	}
	
	public Node<E> getNext(E e){
		return cursor;
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<E> listIterator(int index){
		E after = null;
		E before = null;
		
		if(index == 0){
			before = null;
			after = (E) head;
		}
		else{
			before = get(index-1);
			after = get(index+2);
		}
		return (ListIterator<E>) before;
	}
	
	private Node<E> getNode(int index, int lower, int upper){
		Node<E> p;
		if(index<lower || index>upper)
			throw new IndexOutOfBoundsException("getNode index: " + index + "; size: " + size());
		if(index<size()/2){
			p = head.next;
			for(int i=0; i<index; i++){
				p = p.next;
			}
		}
		else{
			p = tail;
			for(int i=size(); i>index; i--)
				p=p.prev;
		}
		return p;
	}
	
	
	private static class Node<E>{
		
		public Node(E element, Node<E> p, Node<E> n){
			this.element = element;
			prev = p;
			next = n;
		}
		public E element;
		public Node<E> prev;
		public Node<E> next;
	}
	
}
