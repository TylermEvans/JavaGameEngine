package shaders;

import org.lwjgl.util.vector.Matrix4f;

import toolbox.Maths;
import entities.Camera;

public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMat;
	
	public StaticShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	@Override
	protected void bindAttributes(){
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMat = super.getUniformLocation("viewMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f mat){
		super.loadMatrix(this.location_transformationMatrix, mat); 
	}
	
	public void loadProjectionMatrix(Matrix4f mat){
		super.loadMatrix(location_projectionMatrix, mat);
	}
	public void loadViewMatrix(Camera cam){
		Matrix4f viewMat = Maths.createViewMatrix(cam);
		super.loadMatrix(this.location_viewMat, viewMat);
	}
}
