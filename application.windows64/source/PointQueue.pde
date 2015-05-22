// An item in the PointQueue.
class QueueItem {
  
  Point p;
  QueueItem next;
  
  QueueItem(Point tp) {
    p = tp;    
    next = null;
  }
  
  void setNext(QueueItem q) {next = q;}
  
  QueueItem getNext() {return next;}
  
  Point getPoint() {return p;}
}

public class PointQueue {
  
  // The head of the queue. null if empty.
  QueueItem head;
  
  public PointQueue() {
    head = null;
  }
  
  boolean isEmpty() {return head == null;}
  
  boolean contains(Point p) {
    QueueItem cur = head;
    if (cur == null) return false;
    while (cur.getNext() != null) {
      if (cur.getPoint().equals(p)) return true;
      cur = cur.getNext();  
    }
    return false;
  }
  
  QueueItem getTail() {
    QueueItem cur = head;
    if (cur == null) return head;
    while (true) {
       if (cur.getNext() == null) return cur;
       cur = cur.getNext();
    }
  }
  
  void push(Point p) {
    QueueItem tail = getTail();
    if (tail == null) head = new QueueItem(p);
    else tail.setNext(new QueueItem(p));
  }
  
  void pushQueue(PointQueue pq) {  
    Point p;
    while (!pq.isEmpty()) {
      p = pq.pop(); 
      this.push(p);  
    }
  }
  
  Point pop() {
    if (head == null) return null;
    Point out = head.getPoint();
    head = head.getNext();
    return out;
  }
}
