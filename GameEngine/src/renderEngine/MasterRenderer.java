package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import shaders.StaticShader;

public class MasterRenderer {
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	public void render(Light sun, Camera cam){
		renderer.prerender();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(cam);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(Entity entity){
		TexturedModel model = entity.getModel();
		List<Entity> batch = entities.get(model);
		if (batch!=null){
			batch.add(entity);
		}
		else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(model,newBatch);
		}
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}
}
