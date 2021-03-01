/*//package imt2018069;

import java.util.ArrayList;

//import animation.BBox;
//import animation.Point;
//import animation.SceneObject;

public class MySceneObject extends SceneObject implements BBox{
	MySceneObject(){
		bound = this;
		height = 2;
		width = 2;
		nextMove = new Move();
		nextMove.Inititalise();
		position = new Point(-1,-1);
		this.name = "" + counter;
		counter += 1;
	}
	@Override
	public String getObjName() {
		return this.name;
	}

	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(int x, int y) {
		this.position.setPos(x, y);;
		
	}

	public void setDestPosition(int x, int y) {
		this.destination = new Point(x, y);
		nextMove.destinationSet(position, destination);
		nextMove.genNextMove(-1);
	}
	
	@Override
	public BBox getBBox() {
		DemoSceneObject future = new DemoSceneObject();
		future.setPosition(this.getPosition().getX(),this.position.getY());
	
		if(nextMove.next().equals(0)){
			future.position.setPos(future.position.getX(), future.position.getY() + 1);
		}
		else if(nextMove.next().equals(1)){
			future.position.setPos(future.position.getX(), future.position.getY() - 1);
		}
		else if(nextMove.next().equals(2)){
			future.position.setPos(future.position.getX() - 1, future.position.getY());
		}
		else if(nextMove.next().equals(3)){
			future.position.setPos(future.position.getX() + 1, future.position.getY());
		}
		else if(nextMove.next().equals(-1)){
			future.position.setPos(future.position.getX(), future.position.getY());
		}
		
		return future;
	}

	@Override
	protected ArrayList<Point> getOutline() { //Shape of the object is a square
		ArrayList<Point> outline = new ArrayList<>();
		Point p1 = new Point(bound.getMinPt());
		Point p2 = new Point(bound.getMaxPt().getX(),bound.getMinPt().getY());
		Point p3 = new Point(bound.getMaxPt());
		Point p4 = new Point(bound.getMinPt().getX(),bound.getMaxPt().getY());

		outline.add(p1);
		outline.add(p2);
		outline.add(p3);
		outline.add(p4);
		return outline;
	}

	protected void updatePosition(int deltaT) {
		//System.out.println("Move just made: " + nextMove.next());
		if(nextMove.next().equals(0)){
			position.setPos(position.getX(), position.getY() + 1);
		}
		else if(nextMove.next().equals(1)){
			position.setPos(position.getX(), position.getY() - 1);
		}
		else if(nextMove.next().equals(2)){
			position.setPos(position.getX() - 1, position.getY());
		}
		else if(nextMove.next().equals(3)){
			position.setPos(position.getX() + 1, position.getY());
		}
		else{
			//do nothing
		}
		nextMove.genNextMove(-1);

	}

	@Override 
	protected void updatePos(int deltaT){
		if(position.getX() == destination.getX() && position.getY() == destination.getY()) return;
		removeCollisions();
		updatePosition(deltaT);
		nextMove.destinationSet(position, destination);
	}

	public Point getMinPt(){
		Point min = new Point(position.getX() - width / 2, position.getY() - height / 2);
		return min;
	}

	public void removeCollisions(){
		for(int i = 0; i < DemoScene.futureBBox.size(); ++i){
			while(intersects(DemoScene.futureBBox.get(i)) && !(Integer.valueOf(name).equals(i + DemoScene.num))){
				backtrack();
				System.out.println("name " + name + " i: " + (i) + Integer.valueOf(name).equals(i + 1));
				//System.out.println("Co-ordinates:" + position + "  max: " + DemoScene.futureBBox.get(i).getMaxPt() + "  min: " + DemoScene.futureBBox.get(i).getMinPt());
			}
		}
        
    }

	public Point getMaxPt(){
		Point max = new Point(position.getX() + width / 2, position.getY() + height / 2);
		return max;
	}

	public boolean intersects(BBox b){
		//Points of foreign Bbox vs bound(local BBox)
		Point p1 = new Point(b.getMaxPt().getX(),b.getMinPt().getY());
		Point p2 =  new Point(b.getMinPt().getX(),b.getMaxPt().getY());
		if(liesWithin(b.getMinPt(), bound) || liesWithin(b.getMaxPt(), bound)) return true;

		else if(liesWithin(p1,bound) || liesWithin(p2, bound)) return true;

		//Points of bound(local BBox) vs foreign Bbox
		Point p3 = new Point(getMaxPt().getX(),getMinPt().getY());
		Point p4 = new Point(getMinPt().getX(),getMaxPt().getY());
		if(liesWithin(getMinPt(), b) || liesWithin(getMaxPt(), b)) return true;

		else if(liesWithin(p3,b) || liesWithin(p4, b)) return true;

		return false;

	}
	public boolean liesWithin(Point p, BBox b){
		if(p.getX() >= b.getMinPt().getX() && p.getX() <= b.getMaxPt().getX()){
			if(p.getY() >= b.getMinPt().getY() && p.getY() <= b.getMaxPt().getY()) return true;	
		}
		return false;
	}

	public void backtrack(){
		Integer collisionDirection = nextMove.next();
		nextMove.updatePrevious();
		updatePosition(1);
		for(int i = 0; i < height; ++i) {
			nextMove.genNextMove(collisionDirection);
			updatePosition(1);
		}
	}

	private String name;
	private Point position;
	private Point destination;
	public BBox bound;
	public Move nextMove;
	public static Integer height, width;
	private static Integer counter = 0;
}

class Move{
	Move(){
		movesRequired = new Integer[4];
		for(int i = 0; i < 4; ++i) movesRequired[i] = 0;
		alternate = 0;
	}
	public Integer next(){
		return this.nextMoveType;
	}
	public void destinationSet(Point currPosition, Point destination){
		Integer y_diff = -1 * (currPosition.getY() - destination.getY());
		Integer x_diff = -1 * (currPosition.getX() - destination.getX());
		Integer factor = 1;

		if(y_diff >= 0) movesRequired[0] += y_diff * factor;

		else movesRequired[1] += -1 * y_diff * factor;

		if(x_diff >= 0) movesRequired[3] += x_diff * factor;

		else movesRequired[2] += -1 * x_diff * factor;

		//for(Integer i : movesRequired) System.out.println(i);
	}

	public void genNextMove(Integer collision){     //-1 -> no collision
		reverseMoveType = nextMoveType;
		Integer flag = 1;
		if(collision.equals(-1)){
			if(alternate.equals(0)){
				alternate = 1;
				for(int i = 0; i < 4; ++i){
					//System.out.println("Checking move: " + i + " : " + movesRequired[i]);
					if(!movesRequired[i].equals(0)){
					nextMoveType = i;
					movesRequired[i] -= 1;
					flag = 0;
					break;
					}
					if(flag.equals(1)) nextMoveType = -1;
				}
			
			}
			else{
				alternate = 0;
				for(int i = 3; i >= 0; --i){
					//System.out.println("Checking move: " + i + " : " + movesRequired[i]);
					if(!movesRequired[i].equals(0)){
					nextMoveType = i;
					movesRequired[i] -= 1;
					flag = 0;
					break;
					}
					if(flag.equals(1)) nextMoveType = -1;
				}

			}
		}
		
		else{
			alternate = (alternate  + 1) % 2;
			
			Integer i = (collision + 2) % 4;
			nextMoveType = i;

			if(i.equals(0)) movesRequired[1] += 1;

			else if(i.equals(1)) movesRequired[0] += 1;

			else if(i.equals(2)) movesRequired[3] += 1;

			else if(i.equals(3)) movesRequired[2] += 1;
			
		}
		//System.out.println("NextMoveType:" + nextMoveType);
		
	}
	void Inititalise(){
		nextMoveType = -1;
	}
	void updatePrevious(){
		if(reverseMoveType.equals(0)) reverseMoveType = 1;

		else if(reverseMoveType.equals(1)) reverseMoveType = 0;

		else if(reverseMoveType.equals(2)) reverseMoveType = 3;

		else if(reverseMoveType.equals(3)) reverseMoveType = 2;

		nextMoveType = reverseMoveType;
	}
	private Integer nextMoveType; // -1 -> StayStill, 0 -> Up, 1 -> Down, 2 -> Left, 3 -> Right
	private Integer reverseMoveType;
	private Integer[] movesRequired;  //Up,Down,Left,Right
	private Integer alternate;
}
*/