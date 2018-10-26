package com.github.jdthomason;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;
import com.sun.javafx.geom.Vec3d;

public class AttractorGenerator extends Application {

    private static final int WIDTH = 950;
    private static final int HEIGHT = 800;

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private static final double BETA = (8/3);
    private static final double RHO = 28;
    private static final double SIGMA = 10;

    @Override
    public void start(Stage primaryStage){
        Group group = makeGroup();
        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(group, WIDTH, HEIGHT);

        scene.setFill(Color.DARKGRAY);
        scene.setCamera(camera);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    camera.translateZProperty().set(camera.getTranslateZ() + 100);
                    break;
                case A:
                    camera.translateXProperty().set(camera.getTranslateX() - 10);
                    break;
                case S:
                    camera.translateZProperty().set(camera.getTranslateZ() - 100);
                    break;
                case D:
                    camera.translateXProperty().set(camera.getTranslateX() + 10);
                case Q:
                    //noinspection IntegerDivisionInFloatingPointContext
                    group.getTransforms().add(new Rotate(10, (WIDTH/2), (HEIGHT/2), 0, new Point3D(0,0.1,0)));
            }
        });

        primaryStage.setTitle("This is the title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main (String[] args){
        launch(args);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private static Group makeGroup(){
        ArrayList<Vec3d> points = makePoints();
        Group newGroup = new Group();

        for(Vec3d item : points){
            Sphere s = new Sphere(1);
            PhongMaterial m = new PhongMaterial();

            m.setDiffuseColor(generateColor());
            s.setTranslateX(item.x + (WIDTH/2));
            s.setTranslateY(item.y + (HEIGHT/2));
            s.setTranslateZ(item.z);
            s.setMaterial(m);

            newGroup.getChildren().add(s);
        }

        return newGroup;
    }

    private static ArrayList<Vec3d> makePoints(){

        // Initial values:
        double x = 1;
        double y = 1;
        double z = 1;

        // Use dt to signify time distance.
        double dt = 0.01;

        // Store the points in an array of vectors:
        ArrayList<Vec3d> pointsToDraw = new ArrayList<>();

        // Generate the points to draw here.
        for(int t = 0; t < 10000; t++){
            double dx = (SIGMA * (y - x)) * dt;
            double dy = (x * (RHO - z) - y) * dt;
            double dz = ((x * y) - (BETA * z)) * dt;
            x = x + dx;
            y = y + dy;
            z = z + dz;
            pointsToDraw.add(new Vec3d(x, y, z));
            //System.out.println("T: " + t + ", X: " + x + ", Y: " + y + ", Z: " + z);
        }

        return pointsToDraw;
    }

    private static Color generateColor(){
        Random rand = new Random();

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        return Color.color(r, g, b);
    }
}