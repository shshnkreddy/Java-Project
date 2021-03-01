import java.util.*;

public class MyScene extends Scene{
    MyScene(){
        //actors = new ArrayList<DemoSceneObject>();
        //obstacles = new ArrayList<DemoSceneObject>();
        futureBBox = new ArrayList<BBox>();
    }
    protected void checkScene(){
        clear(futureBBox);
        num = obstacles.size();
        //System.out.println("Size:" + actors.size());
        for(int i = 0; i < actors.size(); ++i){
            futureBBox.add(actors.get(i).getBBox());
        }
        for(int i = 0; i < obstacles.size(); ++i){
            futureBBox.add(obstacles.get(i).getBBox());
        }

        for(int i = 0; i < actors.size(); ++i){
            actors.get(i).updatePos(View.delT);
        }

    }

    public static void clear(ArrayList<BBox> b){
        while(!b.isEmpty()){
            b.remove(0);
        }
    }
    
    /*public void addObstacle(DemoSceneObject so) {
        obstacles.add(so);
        super.addObstacle(so);
    }*/

    /*public void addActor(DemoSceneObject so) {
        actors.add(so);
        super.addActor(so);
    }*/

    @Override
    public ArrayList<SceneObject> getObstacles(){
        ArrayList<SceneObject>  o = new ArrayList<>();
        for(int i = 0; i < obstacles.size(); ++i){
            o.add((SceneObject)obstacles.get(i));
        }
        return o;
    }

    @Override
    public ArrayList<SceneObject> getActors(){
        ArrayList<SceneObject>  o = new ArrayList<>();
        for(int i = 0; i < actors.size(); ++i){
            o.add((SceneObject)actors.get(i));
        }
        return o;
    }

    /*@Override
    public void render() {
		view.clear();
		for (DemoSceneObject s : obstacles) {
			view.render(s);
		}
		for (DemoSceneObject s : actors) {
			view.render(s);
		}
		
		view.updateView();
	}*/
        
    public void animate() {
		System.out.println("Starting animation");
		
		for (SceneObject s : actors) {
			s.start();
		}

		for (int i = 0; i < 2 * View.maxUpdates; i++) {
			
			checkScene(); // pre-process the scene before re-rendering
			
			render();
			
			try {
				Thread.sleep(View.delT/2);
			} catch (InterruptedException e) {	
				e.printStackTrace();
			}
		}
	}
    
    public static ArrayList<BBox> futureBBox;
    public static Integer num = 0;
    //public static ArrayList<DemoSceneObject> actors;
    //public static ArrayList<DemoSceneObject> obstacles;
}