package com.norihirosunada.rajawalitest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.loader.awd.ABaseObjectBlockParser;
import org.rajawali3d.loader.awd.BlockSimpleMaterial;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;
import org.rajawali3d.util.ObjectColorPicker;
import org.rajawali3d.util.OnObjectPickedListener;
import org.rajawali3d.util.RajLog;

/**
 * Created by norihirosunada on 16/06/12.
 */
public class Renderer extends RajawaliRenderer implements OnObjectPickedListener{

    public Context context;

    private DirectionalLight directionalLight;
    private Sphere earthSphere,sphere2;
    private Cube cube;
    private Object3D floor;
    private ObjectColorPicker mPicker;
    private float touchedX,touchedY;

    public Renderer(Context context){
        super(context);
        this.context = context;
        setFrameRate(60);
    }

    public void onTouchEvent(MotionEvent event){

    }

    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j){

    }

    public void initScene(){

        directionalLight = new DirectionalLight(0f,0f,1f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(Color.WHITE);

        Material material1 = new Material();
        material1.enableLighting(false);
//        material1.setAmbientColor(Color.WHITE);
        material1.setColor(Color.WHITE);

        Texture juiceTexture = new Texture("Orange", R.drawable.orangejuice);
        try{
            material.addTexture(juiceTexture);

        } catch (ATexture.TextureException error){
            Log.d("DEBUG", "TEXTURE ERROR");
        }



        earthSphere = new Sphere(1,24,24);
        earthSphere.setMaterial(material);
//        earthSphere.setPosition();
        earthSphere.setPosition(1.0, 1.0, 0.0);
        Log.d("position", "" + earthSphere.getPosition());

//        CustomMaterial mcustomMaterial = new CustomMaterial();

        sphere2 = new Sphere(1,12,12);
        sphere2.setMaterial(material1);

        cube = new Cube(1.5f);
        cube.setMaterial(material1);

        LoaderOBJ parser = new LoaderOBJ(getContext().getResources(), mTextureManager, R.raw.floor);
        try {
            parser.parse();
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        floor = parser.getParsedObject();
        floor.setMaterial(material1);
        floor.setPosition(.0, -1.0, .0);
//        floor.setRotation(Vector3.Axis.X,-90.0);

        getCurrentScene().addChild(floor);

//        getCurrentScene().addChild(sphere2);
//        getCurrentScene().addChild(earthSphere);
//        getCurrentScene().addChild(cube);
//        getCurrentCamera().setZ(10f);
        getCurrentCamera().setPosition(.0, .0, 10f);
//        getCurrentCamera().setRotation();

//        BlockSimpleMaterial simpleMaterial = new BlockSimpleMaterial();
//        simpleMaterial.setUseColor

        mPicker = new ObjectColorPicker(this);
        mPicker.setOnObjectPickedListener(this);
        mPicker.registerObject(floor);
    }

    @Override
    public void onRender(final long elapsedTime, final double deltaTime) {
        super.onRender(elapsedTime, deltaTime);
        earthSphere.rotate(Vector3.Axis.Y, 1.0);
//        floor.rotate(Vector3.Axis.X,1.0);
//        sphere2.rotate(Vector3.Axis.Y, 1.0);

    }


    public void onObjectPicked(@NonNull Object3D object){
        Log.d("position",""+object.getX());
        Log.d("position",""+object.getY());
        object.setX(object.getX()+0.1);
        object.setY(object.getY()+0.1);
    }

    public void getObjectAt(float x, float y){
        mPicker.getObjectAt(x,y);
        touchedX = x;
        touchedY = y;
        Log.d("touched","X="+ x);
        Log.d("touched","Y="+ y);
    }

//    @Override
    public void onNoObjectPicked() {
        RajLog.w("No object picked!");
    }
}
