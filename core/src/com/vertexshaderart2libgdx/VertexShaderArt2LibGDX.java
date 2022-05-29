/*
 * @author : Abdulmajid, Olivier NASSER
 * 
 */

package com.vertexshaderart2libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class VertexShaderArt2LibGDX extends ApplicationAdapter {

  private static  int NUM_VERTICES = 99999;
  private static final int MAX_VERTICES = 10000000;
  private static final int[] GL_MODES =  {GL20.GL_TRIANGLES, GL20.GL_POINTS, GL20.GL_LINES, 
		  GL20.GL_TRIANGLE_FAN, GL20.GL_TRIANGLE_STRIP, GL20.GL_LINE_LOOP, GL20.GL_LINE_STRIP};
  private int indexMode = 0;
  private Mesh mesh;
  private ShaderProgram shader;
  private float time = 0;
  
  //private SpriteBatch batch;
  //private Sprite bg;
  private float timeSpeed = 1;
  
  
  private final Vector2 mouse = new Vector2();

  @Override
  public void create() {
	  Gdx.app.log("GDX version", com.badlogic.gdx.Version.VERSION);
	//this.bg = new Sprite(new Texture(Gdx.files.internal("space.jpg")));
	//this.bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
    mesh = new Mesh(true, MAX_VERTICES, 0, new VertexAttribute(Usage.Generic, 1, "vertexId"));
    float[] ids = new float[NUM_VERTICES];
    for (int i = 0; i < NUM_VERTICES; i++) {
      ids[i] = i;
    }

    mesh.setVertices(ids);
    shader = new ShaderProgram(Gdx.files.internal("test3.vs.glsl"), Gdx.files.internal("test.fs.glsl"));
    ShaderProgram.pedantic = false;

    if (shader.getLog().length() != 0) {
      System.out.println(shader.getLog());
    }
    
    //batch = new SpriteBatch();
    
  }

@Override
  public void render() {
	//this.bg.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	//System.out.println(bg.getWidth() == Gdx.graphics.getWidth());
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    
    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    Gdx.gl.glEnable(0x8642);
    Gdx.gl.glEnable(GL20.GL_BLEND_COLOR);
    
   /* batch.begin();
    this.bg.draw(batch);
    batch.end();*/
    time += Gdx.graphics.getDeltaTime() * timeSpeed ;
    mouse.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())
    .scl(1.0f / Gdx.graphics.getWidth(), 1.0f / Gdx.graphics.getHeight())
    .scl(2).sub(1, 1);

    shader.bind();
    shader.setUniformf("time", time);
    shader.setUniformf("vertexCount", NUM_VERTICES);
    shader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    shader.setUniformf("mouse", mouse.x, mouse.y);
    mesh.render(shader, VertexShaderArt2LibGDX.GL_MODES[this.indexMode]);
 
    
    
    if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
    	System.out.print("pressed M");
    	this.indexMode++;
    	if(this.indexMode > VertexShaderArt2LibGDX.GL_MODES.length-1){
    		this.indexMode = 0;
    	}
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.A)){
    	System.out.print("pressed A");
    	timeSpeed += 0.01;
    }
    if(Gdx.input.isKeyPressed(Input.Keys.Z)){
    	System.out.print("pressed Z");
    	timeSpeed -= 0.01;
    	//System.out.println(timeSpeed);
    }
    
    int nv = NUM_VERTICES;
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){//
    	nv += 100;
    	
    }
    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){//
    	
    	nv -=100;
   
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.UP)){
    	nv ++;
    	//System.out.println(nv);
    }
    if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
    	nv --;
    }
    
    if(nv != NUM_VERTICES){
    	if(nv < 0){
    		nv = 0;
    	}else{
    		if(nv > MAX_VERTICES){
    			nv = MAX_VERTICES;
    		}
    	}
    	NUM_VERTICES = nv;
    	float[] ids = new float[NUM_VERTICES];
        for (int i = 0; i < NUM_VERTICES; i++) {
          ids[i] = i;
        }

        mesh.setVertices(ids);
    }
    
   //System.out.println(Gdx.graphics.getFramesPerSecond());
  }

  @Override
  public void dispose() {
    mesh.dispose();
    shader.dispose();
    //batch.dispose();
  }
}
