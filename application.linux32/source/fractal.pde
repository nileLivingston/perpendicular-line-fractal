/*
    Perpendicular Line Fractal
    Idea by a post on http://classymathematics.tumblr.com
    
    Code by Nile Livingston (c) 2015
    
    Use the up and down arrow keys to adjust the fractal size.
*/

int SIZE = 700;

// The length of a line.
float UNIT_LENGTH = 10;
// Half the unit length.
float UNIT_HALF = UNIT_LENGTH/2;
// The number of iterations to draw.
int LIMIT = 1;
int MAX = 128;

// Return true iff p has already been drawn.
boolean isDrawn(Point p) {
  return get(int(p.getX()), int(p.getY())) == color(0);
}

// Return true iff p is an intersection of already drawn lines.
boolean isIntersection(Point p) {
  if (p.isVertical()) {
    return isDrawn(new Point(p.getX() - 1, p.getY(), true)) && isDrawn(new Point(p.getX() + 1, p.getY(), true));
  }
  else {
    return isDrawn(new Point(p.getX(), p.getY() - 1, false) ) && isDrawn(new Point(p.getX(), p.getY() + 1, false));
  }  
}

// Draw the recursive perpendicular line structure.
void drawLines() {
  // To count iterations of drawing.
  int count = 0;
  // To hold the points to be drawn on a given iteration.
  PointQueue pq = new PointQueue();
  // The initial point.
  Point start = new Point(SIZE/2, SIZE/2, true);
  pq.push(start);
  
  // To hold the points to be added for the next iteration.
  PointQueue next_iter = new PointQueue();
  
  while (count < LIMIT) {
    // While we still have (potential) lines to draw.
    while (!pq.isEmpty()) {
      Point next = pq.pop();
      if (next.isVertical()) {
        // Get the endpoint coordinates.
        float x = next.getX();
        float y1 = next.getY() - UNIT_HALF;
        float y2 = next.getY() + UNIT_HALF;
        // Only draw line and add endpoints if not an intersection.
        if (!isIntersection(next)) {
          line(x, y1, x, y2); 
          // Add the endpoints to the next_iter queue.
          next_iter.push(new Point(x, y1, false));
          next_iter.push(new Point(x, y2, false));
        }
      }
      else {
        // Get the endpoint coordinates.
        float x1 = next.getX() - UNIT_HALF;
        float x2 = next.getX() + UNIT_HALF;
        float y = next.getY();
        // Only draw line and add endpoints if not an intersection.
        if (!isIntersection(next)) {
          line(x1, y, x2, y); 
          // Add the endpoints to the next_iter queue.
          next_iter.push(new Point(x1, y, true));
          next_iter.push(new Point(x2, y, true));  
        }
      }
    }
    // Add all of the next_iter points to the pq queue.
    pq.pushQueue(next_iter);
    count++;  
  }  
}

void setup() {
  size(SIZE,SIZE);
  frameRate(8);
  smooth();
}

void draw() {
  background(255);
  stroke(0);
  drawLines();
  
  fill(0);
  textSize(18);
  text("n = " + LIMIT, SIZE/30, SIZE/30);
}

void keyPressed() {
  if (key == CODED) {
    if (keyCode == UP && LIMIT < MAX) {
      LIMIT++;
    }
    else if (keyCode == DOWN && LIMIT > 1) {
      LIMIT--;
    }
  }
}
