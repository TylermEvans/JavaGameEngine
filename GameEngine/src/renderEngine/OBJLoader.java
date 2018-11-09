package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class OBJLoader {
	public static RawModel loadObjModel(String fileName, Loader loader){
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Couldn't load the file");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] vertexArray = null;
		float[] normalArray = null;
		float[] textureArray = null;
		int[] indexArray = null;
		try{
			while(true){
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")){
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}
				else if(line.startsWith("vt ")){
					Vector2f texture  = new Vector2f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}
				else if(line.startsWith("vn ")){
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}
				else if(line.startsWith("f ")){
					textureArray = new float[vertices.size() * 2];
					normalArray = new float[vertices.size() * 3];
					break;
				}
			}
			while(line!=null){
				if(!line.startsWith("f ")){
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVert(vertex1, indices, textures, normals, textureArray, normalArray); 
				processVert(vertex2, indices, textures, normals, textureArray, normalArray); 
				processVert(vertex3, indices, textures, normals, textureArray, normalArray); 
				line = reader.readLine();
				
			}
			reader.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		vertexArray = new float[vertices.size()*3];
		indexArray = new int[indices.size()];
		
		int vertPointer = 0;
		for(Vector3f vertex:vertices){
			vertexArray[vertPointer++] = vertex.x;
			vertexArray[vertPointer++] = vertex.y;
			vertexArray[vertPointer++] = vertex.z;
		}
		for (int i = 0; i < indices.size(); i++){
			indexArray[i] = indices.get(i);
		}
		return loader.loadToVAO(vertexArray, textureArray, indexArray);
	}
	
	private static void processVert(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalArray){
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer*2] = currentTex.x;
		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalArray[currentVertexPointer*3] = currentNorm.x;
		normalArray[currentVertexPointer*3+1] = currentNorm.y;
		normalArray[currentVertexPointer*3+2] = currentNorm.z;
		
	}
}
