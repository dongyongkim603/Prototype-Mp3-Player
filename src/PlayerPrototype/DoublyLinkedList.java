/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerPrototype;

/**
 *
 * @author John
 */
public class DoublyLinkedList <E>
{
//-------------------nested node class-----------------------------------------
    private static class Node <E>
    {
        //reference to the element stored at this node
        public E element; 
        
        //reference to the previous node in the list
        public Node<E> prev;
        
        //reference to the next node in the list
        public Node<E> next;
        
        /**
	 * The constructor for the DoublyLinkedList.
	 * 
	 * @param e the Object to set as the element
	 * @param p the DoublyLinkedList that is previous in the list
	 * @param n the DoublyLinkedList that is next in the list
	 */
        public Node(E e, Node<E> p, Node<E> n)
        {
            element = e;
            prev = p;
            next = n;
        }
        
        //acess methods
        public E getElement()
        {
            return element;
        }
        
        public Node<E> getPrev()
        {
            return prev;
        }
        
        public Node<E> getNext()
        {
            return next;
        }
        
        public void setNext(Node<E> n)
        {
            next = n;
        }
        
        public void setPrev(Node<E> p)
        {
            prev = p;
        }
    }
//--------------end of nested class---------------------------------------------
    
    //instance variables
    
    //header sentinal
    private Node<E> head;
    
    //trailer sentinal
    private Node<E> tail;
    
    //number of elements
    private int size = 0;
    
    /**
    * The default constructor
    */
    public DoublyLinkedList()
    {
        //create header
        head = new Node<>(null, null, null);
        
        //trailer is preceded by header
        tail = new Node<>(null, head, null);
        
        //header is followed by trailer
        head.setNext(tail);
    }
    
    /**
     * returns number of elements in list
     * @return size
     */
    public int size()
    {
        return size;
    }
    
    /**
     * tests whether list is empty
     * @return true if no nodes are in list
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    /**
     * returns but does not remove the first element in the list
     * @return E of first Node
     */
    public E first()
    {
        if (isEmpty())
        {
            return null;
        }
        //first element is beyond header
        return head.getNext().getElement();
    }
    
    /**
     * returns but does not remove the last element of the list
     * @return E of last node
     */
    public E last()
    {
        if(isEmpty())
        {
            return null;
        }
        //last element is before trailer
        return tail.getPrev().getElement();
    }
    
//---------------public update methods------------------------------------------
    
    /**
     * Adds element e to the front of the list
     * @param e where e is real data 
     */
    public void addFirst(E e)
    {
        addBetween(e, head, head.getNext());
    }
    
    /**
     * Adds element e to the list after first element
     * @param e real data to be added
     */
    public void addNext(E e)
    {
        Node<E> next = head.getNext();
        addBetween(e, next, next.getNext());
    }
    
    /**
     * Adds element e to the end of the list
     * @param e where e is real data
     */
    public void addLast(E e)
    {
        addBetween(e, tail.getPrev(), tail);
    }    
    
    /**
     * removes and returns last element in the list
     * @return e of last node
     */
    public E removeLast()
    {
        //special case if list is empty
        if(isEmpty())
        {
            return null;
        }
        //last element is before trailer
        return remove(tail.getPrev());
    }
    
    /**
     * removes and returns first element in the list
     * @return e of last node
     */
    public E removeFirst()
    {
        //special case if list is empty
        if(isEmpty())
        {
            return null;
        }
        //first element is beyond header
        return remove(head.getNext());
    }
    
    /**
     * concatList concatenates two doublyLinkedLists by creating a new node newest.
     * newest takes the E data from the last element of list L while newest next 
     * refers to M head node. L tail node is removed so that newest prev may point 
     * to correct element. Size of new list is size L + size M
     * @param L list for list M to be appended to
     * @param M list to be appended
     */
    public void concatLits(DoublyLinkedList L, DoublyLinkedList M)
    { 
        //new middle node with data of last L node
        Node<E> newest = new Node<>(L.tail.getPrev().getElement(), L.tail.getPrev(), M.head.getNext());
        
        //removed last L node to prevent duplicate nodes
        L.removeLast();
        
        //set L's previous to trailer node's next to refer to newest 
        L.tail.getPrev().setNext(newest);
        
        //set new concatinated list L's tail node to M's tail node
        L.tail = M.tail;
        
        //add size of nodes together to get new node size
        L.size = L.size + M.size;
    }

    /**
     * This method returns the entire list in a String form
     * 
     * @return the String form of the list
     */    
    @Override
    public String toString() 
    {
        
        String out = "The DLList contains: \n";
        Node<E> ref = head;
        
        if(head == null)
        {
            return out + "0 nodes.";
        }       
        else
        {
            out += "head -->\t";
        }       

        while(ref != tail) 
        {
                out += ref.element + "\t<-->\t";
                ref = ref.next;
        }
        
        out += ref.element + "\t<-- tail";
        
        return out;
    }
    
//--------------------private update methods------------------------------------
    
    /**
     * Adds element e to the linked list in between the given nodes
     * @param e the real data to be added
     * @param predecessor the tail node reference
     * @param successor the head node reference
     */
    private void addBetween(E e, Node<E> predecessor, Node<E> successor)
    {
        //create and link new node
        Node<E> newest = new Node<>(e, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size++;
    }
    
    /**
     * Removes and returns the given element from the node in the list
     * @param node the node to be removed
     * @return the data E of the node to be removed
     */
    private E remove(Node<E> node)
    {
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        return node.getElement();
    }
}
