/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PlanetSystemSimulation extends Application {

    private static final String spaceURL = "file:images/avaruustausta.jpg";
    private static final String skeleURL = "file:images/skeleton.gif";
    private static final String ussrURL = "file:images/ussr.png";
    double mouse_x = 0;
    double mouse_y = 0;
    double canvas_x = 0;
    double canvas_y = 0;
    int time_x = 100;
    int time_y = 100;
    double standard = 3E9;
    double ogStandard = standard;
    int height = 700;
    int width = 1100;
    ArrayList<Planet> planets = new ArrayList<>();
    double drag_y = 0;
    double drag_x = 0;
    double midWidth = width / 2;
    double midHeight = height / 2;
    Integer timestep = 6000;
    Integer ogTimestep = timestep;
    Integer normalTimestep = timestep;
    boolean follow = false;
    boolean spoopy = false;
    ArrayList<Circle> circles = new ArrayList<>();
    int followId = 1;

    public static void main(String[] args) {
        launch(PlanetSystemSimulation.class);
    }

    @Override
    public void start(Stage window) {

        //Planet name change to body? because sun, moon and chury are not planets
        Planet sun = new Planet("Sun", 0, 0, 0, 0, 1.989E30, Color.YELLOW);
        //Planet earth = new Planet("Earth", 7.649815710400691E10, 1.2825871174194992E11, -25608.972746907584, 15340.707015973465, 5.974E24, Color.BLUE);
        Planet earth = new Planet("Earth", 1.521E11, 0, 0, 29290, 5.97237E24, Color.BLUE);
        Planet mars = new Planet("Mars", 2.279392E11, 0, 0, 24077, 6.4171E23, Color.TOMATO);
        Planet moon = new Planet("Moon", 1.52484399E11, 0, 0, 30312, 7.342E22, Color.GREY);
        Planet venus = new Planet("Venus", 1.08208E11, 0, 0, 35020, 4.8675E24, Color.GOLDENROD);
        Planet mercury = new Planet("Mercury", 5.790905E10, 0, 0, 47362, 3.3011E23, Color.GREY);
        Planet jupiter = new Planet("Jupiter", 7.78412E11, 0, 0, 13070, 1.899E27, Color.LIGHTSALMON);
        Planet callisto = new Planet("Callisto", 7.802947E11, 0, 0, 21274, 1.075938E23, Color.GREY);
        //Planet tuhoaja = new Planet(1E12, 0, 0, 10000, 7.322E29);

        Planet Churyomov = new Planet("67P/Churyumov–Gerasimenko", 1.8598E11, 0, 0, 34220, 0);
        Planet nopee = new Planet("Nopee", 0, 2.99E10, -89353, 0, 0);
        planets.add(sun);
        planets.add(earth);
        planets.add(mars);
        planets.add(moon);
        planets.add(Churyomov);
        planets.add(nopee);
        planets.add(venus);
        planets.add(mercury);
        planets.add(jupiter);
        //planets.add(callisto);
        //planets.add(tuhoaja);
        for (Planet p : planets) {
            Circle circle = makeCircle(p);
            circles.add(circle);
        }

        Canvas canvas = new Canvas(width, height);
        GraphicsContext drawer = canvas.getGraphicsContext2D();
        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);

        PlanetSystem starsystem = new PlanetSystem(planets);

        new AnimationTimer() {
            long prev = 0;
            Image space = new Image(spaceURL);
            Image uusr = new Image(skeleURL);
            Image ussr = new Image(ussrURL);
            Double days = 0.0;
            double kerroin = 1E6;

            @Override
            public void handle(long now) {
                if (now - prev < 1E7) {
                    return;
                }
//                if (standard < 1E8) {
//                    kerroin = 1E8;
//                }
//                if (standard > 5E9) {
//                    kerroin = -1E8;
//                }
                //standard += kerroin;

                days = days + 1.0 * timestep / (60 * 60 * 24);

                if (spoopy) {
                    drawer.drawImage(space, 0, 0, 1100, 700);
                } else {
                    drawer.setFill(Color.BLACK);
                    drawer.fillRect(0, 0, width * height, width * height);

                }

                drawer.setFill(Color.WHITE);
                double dt = timestep / 60.0;
                Double years = days / 365.25;
                String year = String.format("%.02f", years);
                String day = String.format("%.00f", days);
                String dstep = String.format("%.00f", dt);

                //Follow earth
                if (follow) {
                    midWidth = width / 2 - planets.get(followId).getPos().getX() / standard;
                    midHeight = height / 2 - planets.get(followId).getPos().getY() / standard;
                    drawer.fillText(day + " days\n" + year + " years\n" + "timestep: " + dstep + " h\nTarget: " + planets.get(followId).getName(), time_x, time_y);

                } else {
                    drawer.fillText(day + " days\n" + year + " years\n" + "timestep: " + dstep + " h", time_x, time_y);

                }
                for (Planet p : planets) {
                    int i = 0;
                    drawer.setFill(p.getColor());
                    //Kuun plottaus poista aikanaan !!!!!!!!!!!!
                    if (p.getMass() == 7.342E22 || p.getMass() == 1.075938E23) {
                        drawer.fillOval(midWidth + p.getPos().getX() / standard, midHeight + p.getPos().getY() / standard, 3, 3);
                    } else if (p.getMass() == 1.899E27 && spoopy) {

                        drawer.drawImage(ussr, midWidth + p.getPos().getX() / standard, midHeight + p.getPos().getY() / standard, 430, 400);

//                        drawer.fillText("Pirtti ei aio\npitää tupareita", width / 2 + p.getPos().getX() / standard, height / 2 + p.getPos().getY() / standard);
                    } else if (spoopy) {
                        drawer.drawImage(uusr, midWidth + p.getPos().getX() / standard, midHeight + p.getPos().getY() / standard, 300, 300);
                    } else {

                        drawer.fillOval(midWidth + p.getPos().getX() / standard - 2, midHeight + p.getPos().getY() / standard - 2, 5, 5);
                    }
                    i++;
                }
                this.prev = now;
                starsystem.Update(timestep);
            }

        }.start();
        //layout.getChildren().addAll(circles);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        //MOVE CANVAS
        canvas.setOnMouseMoved(e -> mouseCoord(e));
        dragScreen(canvas);
        controls(scene);
        clickPlanets(canvas);

    }

    private void mouseCoord(MouseEvent e) {
        mouse_x = e.getSceneX();
        mouse_y = e.getSceneY();
    }

    private void dragScreen(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown()) {
                    double change_y = e.getSceneY() - mouse_y;
                    midHeight += change_y;
                    mouse_y = e.getSceneY();
                    double change_x = e.getSceneX() - mouse_x;
                    midWidth += change_x;
                    mouse_x = e.getSceneX();
                    follow = false;
                } else if (e.isSecondaryButtonDown()) {
                    double change_y = e.getSceneY() - mouse_y;
                    standard += change_y * 1E7;
                    mouse_y = e.getSceneY();
                }

            }
        });
    }

    private void clickPlanets(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                int i = 0;
                for (Planet p : planets) {
                    double posX = midWidth + p.getPos().getX() / standard - 2;
                    double posY = midHeight + p.getPos().getY() / standard - 2;
                    if (e.getSceneX() > posX && e.getSceneX() < posX + 5 && e.getSceneY() > posY && e.getSceneY() < posY + 5) {
                        followId = i;
                        follow = true;

                    }

                    i++;
                }

            }
        });
    }

    private void controls(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.P) {
                if (timestep == 0) {
                    timestep = normalTimestep;
                } else {
                    normalTimestep = timestep;
                    timestep = 0;
                }
            }
            if (e.getCode() == KeyCode.SPACE) {
                timestep = ogTimestep;

            }
            if (e.getCode() == KeyCode.PERIOD) {
                timestep = timestep * 5;
            }
            if (e.getCode() == KeyCode.COMMA) {
                timestep = timestep / 5;
            }
            if (e.getCode() == KeyCode.B) {
                timestep = timestep * -1;
            }
            if (e.getCode() == KeyCode.R) {
                standard = ogStandard;
                midWidth = width / 2;
                midHeight = height / 2;
                follow = false;
            }
            if (e.getCode() == KeyCode.DIGIT1) {
                follow = !follow;
            }
            if (e.getCode() == KeyCode.S) {
                spoopy = !spoopy;
            }
        });
    }

    private Circle makeCircle(Planet p) {
        Circle circle = new Circle(width / 2 + p.getPos().getX() / standard, height / 2 + p.getPos().getY() / standard, 3);
        circle.setFill(p.getColor());
        return circle;

    }

    private void addPlanet(String name, double posX, double posY, double velX, double velY, double mass, Color color) {
        Planet planet = new Planet(name, posX, posY, velX, velY, mass, color);
        planets.add(planet);
    }

    private void addPlanet(String name, double posX, double posY, double velX, double velY, double mass) {
        Planet planet = new Planet(name, posX, posY, velX, velY, mass);
        planets.add(planet);
    }

}
