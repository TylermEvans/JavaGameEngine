package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

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
}
