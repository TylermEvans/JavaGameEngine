package engineTester;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;
public class MainGameLoop {
	
	public static void main(String args[]){
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();

		 
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("greystone"));
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		Entity entity = new Entity(texturedModel, new Vector3f(0,-5,-25),0,0,0,1);
		Light light = new Light(new Vector3f(0,0,-20),new Vector3f(1,1,1));
		
		Camera cam = new Camera();
		
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()){
			entity.rotate(0, 1, 0);
			cam.move();
			renderer.processEntity(entity);
			renderer.render(light,cam);
			DisplayManager.updateDisplay();
			
			
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.destroyDisplay();
	}
	
	
}
