
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private Node head = null;
    private Node tail = null;
    private int size = 0;
    
    private class Node
    {
        public Node(Item i)
        {
            item = i;
        }
        Item item;
        Node next;
        Node prev;
    }
    
    // construct an empty deque
    public Deque()
    {
    }
    
    // is the deque empty?
    public boolean isEmpty()
    {
        return head == null;
    }
    
    // return the number of items on the deque
    public int size()
    {
        return size;
    }
    
    // insert the item at the front
    public void addFirst(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException("Null item");
        
        Node oldhead = head;
        head = new Node(item);
        head.next = oldhead;
        
        if (size == 0)
        {
            head = tail;
        }
        
        ++size;
    }
    
    // insert the item at the end
    public void addLast(Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException("Null item");
        
        Node oldtail = tail;
        tail = new Node(item);
        tail.prev = oldtail;
        
        if (size == 0)
        {
            head = tail;
        }
        
        ++size;
    }
    
    // delete and return the item at the front
    public Item removeFirst()
    {
        if (isEmpty())
            throw new java.lang.UnsupportedOperationException("Empty deque");
        
        Node temp = head;
        head = temp.next;
        temp.prev = null;
        temp.next = null;
        --size;
        return temp.item;
    }
    
    // delete and return the item at the end
    public Item removeLast()
    {
        if (isEmpty())
            throw new java.lang.UnsupportedOperationException("Empty deque");
        
        Node temp = tail;
        tail = temp.prev;
        temp.prev = null;
        temp.next = null;
        --size;
        return temp.item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {
        return new DeqeueIterator();
    }
    
    private class DeqeueIterator implements Iterator<Item>
    {
        private Node current = head;
        public boolean hasNext() { return current != null; }
        public void remove()
        {
            throw new java.lang.UnsupportedOperationException ("No remove");
        } 
        public Item next()
        {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    // unit testing
    public static void main(String[] args)
    {
        Deque<Integer> kewl = new Deque<Integer>();
        kewl.addFirst(69);
        kewl.addLast(3);
        kewl.addLast(4);
        
        for (int i : kewl)
            StdOut.println(i);
    }
}