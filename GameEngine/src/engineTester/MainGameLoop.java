package engineTester;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import renderEngine.DisplayManager;
import renderEngine.Loader;
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
		Renderer renderer = new Renderer(shader);
		 
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0,0,-50),0,0,0,1);
		Camera cam = new Camera();
		
		
		
		while(!Display.isCloseRequested()){
			entity.rotate(0, 1, 0);
			cam.move();
			renderer.prerender();
			shader.start();
			shader.loadViewMatrix(cam);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
			
			
		}
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.destroyDisplay();
	}
	
	
}
