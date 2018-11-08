package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		Matrix4f.translate(translation, mat, mat);
		Matrix4f.rotate((float)Math.toRadians(rx), new Vector3f(1,0,0), mat, mat);
		Matrix4f.rotate((float)Math.toRadians(ry), new Vector3f(0,1,0), mat, mat);
		Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0,0,1), mat, mat);
		Matrix4f.scale(new Vector3f(scale, scale, scale), mat, mat);
		return mat;
	}
	
	public static Matrix4f createViewMatrix(Camera cam){
		Matrix4f viewMat = new Matrix4f();
		viewMat.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(cam.getPitch()), new Vector3f(1,0,0), viewMat, viewMat);
		Matrix4f.rotate((float)Math.toRadians(cam.getYaw()), new Vector3f(0,1,0), viewMat, viewMat);
		Vector3f camPos = cam.getPosition();
		Vector3f negCamPos = new Vector3f(-camPos.x, -camPos.y, -camPos.z);
		Matrix4f.translate(negCamPos, viewMat, viewMat); //may need modified
		return viewMat;
	}
}
