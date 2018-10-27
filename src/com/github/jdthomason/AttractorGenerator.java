package com.github.jdthomason;

import peasy.PeasyCam;
import processing.core.*;
import java.util.ArrayList;
import com.sun.javafx.geom.Vec3d;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class AttractorGenerator extends PApplet {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 600;

    private static final float BETA = (8/3);
    private static final float RHO = 28;
    private static final float SIGMA = 10;

    private static float x = 1;
    private static float y = 1;
    private static float z = 1;

    public static void main (String[] args){
        PApplet.main("com.github.jdthomason.AttractorGenerator");
    }

    public void settings(){
        size(WIDTH, HEIGHT, P3D);
    }

    public void setup(){
        PeasyCam camera = new PeasyCam(this, 1000);
        surface.setTitle("This is the title");
    }

    public void draw(){
        int count = 0;
        background(0);
        // Translate position so we start in the center

        // This is so we can see things
        scale(5);

        ArrayList<Vec3d> points = makePoints();

        for(Vec3d item : points){
            // Deal with the color based on count number
            if (count >= 0 && count < 3000){
                stroke(255,0,0);
                count++;
                point((float)item.x, (float)item.y, (float)item.z);
            }
            else if (count >= 3000 && count < 6000){
                stroke(255);
                count++;
                point((float)item.x, (float)item.y, (float)item.z);
            }
            else if (count >= 6000 && count < 9000){
                stroke(0,0,255);
                count++;
                point((float)item.x, (float)item.y, (float)item.z);
            }
            else if (count == 9000){
                point((float)item.x, (float)item.y, (float)item.z);
                noLoop();
            }
        }
    }

    private static ArrayList<Vec3d> makePoints(){

        // Initial values:
        float x = 2;
        float y = 1;
        float z = 1;

        // Use dt to signify time distance.
        float dt = 0.01f;

        // Store the points in an array of vectors:
        ArrayList<Vec3d> pointsToDraw = new ArrayList<>();

        // Generate the points to draw here.
        for(int t = 0; t < 9000; t++){
            float dx = (SIGMA * (y - x)) * dt;
            float dy = (x * (RHO - z) - y) * dt;
            float dz = ((x * y) - (BETA * z)) * dt;
            x = x + dx;
            y = y + dy;
            z = z + dz;
            pointsToDraw.add(new Vec3d(x, y, z));
        }

        return pointsToDraw;
    }
}