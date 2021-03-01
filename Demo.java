/*import animation.Scene;
import animation.SceneObject;
import animation.View;
import test.TestObject;
import test.TestScene;*/


// Driver class to set up and exercise the animation
public class Demo {

	public static void main(String[] args) {
		MyScene scene = new MyScene(); // replace with your implementation

		// populate the scene with objects implemented by the team
		for (int i = 0; i < 6; i++) {
			SceneObject s = new MySceneObject(); // replace with your implementation
			s.setPosition(i * 50, i * 50);
			scene.addObstacle(s); // using appropriate derived classes
		}

		for (int i = 0; i < 6; i++) {
			SceneObject s = new MySceneObject(); // replace with your implementation
			s.setPosition(500 - i * 50, 300 + i * 50); // these will be changed for the demo
			s.setDestPosition(0, 0);
			scene.addActor(s); // using appropriate derived classes
		}

		/*SceneObject s = new DemoSceneObject();
		s.setPosition(15, 10);
		s.setDestPosition(0, 10);
		scene.addActor((DemoSceneObject)s);

		SceneObject a = new DemoSceneObject();
		a.setPosition(0, 10);
		a.setDestPosition(15, 10);;
		scene.addActor((DemoSceneObject)a);*/

		View view = new DemoTextView();
		//View view = new DemoSwingView();


		scene.setView(view);

		view.init();

	}

}
