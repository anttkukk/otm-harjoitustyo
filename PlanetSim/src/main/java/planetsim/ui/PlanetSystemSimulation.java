/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.ui;

import planetsim.domain.Planet;
import planetsim.domain.PlanetSystem;
import planetsim.domain.Vector;
import planetsim.database.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PlanetSystemSimulation extends Application {

    private static final String SPACEURL = "file:images/avaruustausta.jpg";
    private static final String SKELEURL = "file:images/skeleton.gif";
    private static final String USSRURL = "file:images/ussr.png";
    private Database database;
    private PlanetDao planetDao;
    PlanetSystem starsystem;
    int system = 1;
    double mouseX = 0;
    double mouseY = 0;
    double canvasX = 0;
    double canvasY = 0;
    int timeX = 100;
    int timeY = 100;
    double standard = 3E9;
    double ogStandard = standard;
    int height = 700;
    int width = 1100;
    ArrayList<Planet> planets = new ArrayList<>();
    double dragY = 0;
    double dragX = 0;
    double midWidth = width / 2;
    double midHeight = height / 2;
    Integer timestep = 6000;
    Integer ogTimestep = timestep;
    Integer normalTimestep = timestep;
    boolean follow = false;
    boolean subTarget = false;
    boolean spoopy = false;
    ArrayList<Circle> circles = new ArrayList<>();
    int followId = 1;
    int subTargetId = 1;
    double createX = 0;
    double createY = 0;
    double createVelX = 0;
    double createVelY = 0;
    boolean createMode = false;
    boolean draw = false;
    double changeX = 0;
    double changeY = 0;
    int circlesSize = 0;

    public static void main(String[] args) {
        launch(PlanetSystemSimulation.class);
    }

    @Override
    public void start(Stage window) throws Exception {

        //Planet name change to body? because sun, moon and chury are not planets
//        Planet sun = new Planet("Sun", 0, 0, 0, 0, 1.989E30, Color.YELLOW, 4);
//        //Planet earth = new Planet("Earth", 7.649815710400691E10, 1.2825871174194992E11, -25608.972746907584, 15340.707015973465, 5.974E24, Color.BLUE);
//        Planet earth = new Planet("Earth", 1.521E11, 0, 0, 29290, 5.97237E24, Color.BLUE, 3);
//        Planet mars = new Planet("Mars", 2.279392E11, 0, 0, 24077, 6.4171E23, Color.TOMATO, 3);
//        Planet moon = new Planet("Moon", 1.52484399E11, 0, 0, 30312, 7.342E22, Color.GREY, 2);
//        Planet venus = new Planet("Venus", 1.08208E11, 0, 0, 35020, 4.8675E24, Color.GOLDENROD, 3);
//        Planet mercury = new Planet("Mercury", 5.790905E10, 0, 0, 47362, 3.3011E23, Color.GREY, 3);
//        Planet jupiter = new Planet("Jupiter", 7.78412E11, 0, 0, 13070, 1.899E27, Color.LIGHTSALMON, 3);
//        Planet callisto = new Planet("Callisto", 7.802947E11, 0, 0, 21274, 1.075938E23, Color.GREY, 3);
//        //Planet tuhoaja = new Planet(1E12, 0, 0, 10000, 7.322E29);
//
//        Planet chury = new Planet("67P/Churyumov–Gerasimenko", 1.8598E11, 0, 0, 34220, 0);
//        Planet fast = new Planet("Unknown Comet", 0, 2.99E10, -89353, 0, 0);
//        planets.add(sun);
//        planets.add(earth);
//        planets.add(mars);
//        planets.add(moon);
//        planets.add(chury);
//        planets.add(fast);
//        planets.add(venus);
//        planets.add(mercury);
//        planets.add(jupiter);
//        //planets.add(callisto);
//        //planets.add(tuhoaja);
        Canvas canvas = new Canvas(width, height);
        GraphicsContext drawer = canvas.getGraphicsContext2D();
        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);

        starsystem = getPlanets(layout);
        standard = starsystem.getFurthest().getPos().length() / 300;
        ogStandard = standard;
        System.out.println(starsystem.getFurthest().getName());
        AnimationTimer timer = new AnimationTimer() {
            long prev = 0;
            Image space = new Image(SPACEURL);
            Image uusr = new Image(SKELEURL);
            Image ussr = new Image(USSRURL);
            Double days = 0.0;
            double kerroin = 1E6;

            @Override
            public void handle(long now) {
                if (now - prev < 1E7) {
                    return;
                }

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

                drawer.setStroke(Color.WHITE);
                drawer.strokeLine(width * 1 / 3, height * 7 / 8, width * 1 / 3 + 99.7, height * 7 / 8);
                drawer.strokeLine(width * 1 / 3, height * 7 / 8 - 2, width * 1 / 3, height * 7 / 8 + 2);
                drawer.strokeLine(width * 1 / 3 + 99.7, height * 7 / 8 - 2, width * 1 / 3 + 99.7, height * 7 / 8 + 2);
                String string = String.format("%.02f", 99.7 * standard / (1.495978707E11));
                drawer.fillText(string + " au", width * 1 / 3 + 25, height * 7 / 8 - 20);

                //Follow target
                if (follow) {
                    midWidth = width / 2 - planets.get(followId).getPos().getX() / standard;
                    midHeight = height / 2 - planets.get(followId).getPos().getY() / standard;
                    drawer.fillText(day + " days\n" + year + " years\n" + "timestep: " + dstep + " h\nTarget: " + planets.get(followId).getName(), timeX, timeY);
                    if (subTarget) {
                        drawer.fillText("Subtarget: " + planets.get(subTargetId).getName(), timeX, timeY + 60);
                        double distance = planets.get(followId).distance(planets.get(subTargetId));
                        String str = "";
                        if (distance / 1.495978707E11 < 0.1) {
                            str = String.format("%.0f", distance / 1000) + " km";

                        } else {
                            str = String.format("%.02f", distance / 1.495978707E11) + " au";

                        }
                        drawer.fillText("Distance: " + str, timeX, timeY + 74);
                        drawer.strokeLine(midWidth + planets.get(followId).getPos().getX() / standard, midHeight + planets.get(followId).getPos().getY() / standard, midWidth + planets.get(subTargetId).getPos().getX() / standard, midHeight + planets.get(subTargetId).getPos().getY() / standard);
                    }

                } else {
                    drawer.fillText(day + " days\n" + year + " years\n" + "timestep: " + dstep + " h", timeX, timeY);

                }
                if (createMode) {
                    drawer.fillText("Create mode", timeX, timeY - 14);
                    if (draw) {
                        double x = mouseX;
                        double y = mouseY;
                        drawer.setStroke(Color.WHITE);
                        drawer.strokeLine(mouseX, mouseY, mouseX - changeX, mouseY - changeY);
                        String txt = String.format("%.01f", new Vector(changeX, changeY).length() * 0.2);
                        drawer.fillText(txt + " km/s", x + 30, y);

                    }
                }
                int i = 0;
                for (Planet p : planets) {
                    drawer.setFill(p.getColor());
                    if (p.getMass() == 1.899E27 && spoopy) {

                        drawer.drawImage(ussr, midWidth + p.getPos().getX() / standard, midHeight + p.getPos().getY() / standard, 430, 400);

//                        drawer.fillText("Pirtti ei aio\npitää tupareita", width / 2 + p.getPos().getX() / standard, height / 2 + p.getPos().getY() / standard);
                    } else if (spoopy) {
                        drawer.drawImage(uusr, midWidth + p.getPos().getX() / standard, midHeight + p.getPos().getY() / standard, 300, 300);
                    } else {
                        //drawer.fillOval(midWidth + p.getPos().getX() / standard - 2, midHeight + p.getPos().getY() / standard - 2, 5, 5);

                        circles.get(i).setCenterX(midWidth + p.getPos().getX() / standard);
                        circles.get(i).setCenterY(midHeight + p.getPos().getY() / standard);
                        //if(planets.get(i).getPos().length() > )
                    }
                    i++;
                }
                this.prev = now;
                starsystem.update(timestep);
                if (circlesSize < circles.size()) {
                    layout.getChildren().add(circles.get(circles.size() - 1));
                    circlesSize = circles.size();
                }
            }

        };
        //layout.getChildren().addAll(circles);

        //Start screen and buttons 
        VBox startBox = new VBox();
        Button startBut = new Button("start!");
        Button systemChange = new Button("Change system");
        Label label = new Label("Selected system: " + system);

        BorderPane startLayout = new BorderPane();
        Button nappi = new Button("takaisin!");
        Button toinen = new Button("kakka");
        layout.getChildren().add(nappi);
        HBox hox = new HBox(nappi);
        hox.setLayoutX(100);
        hox.setLayoutY(45);
        layout.getChildren().add(hox);
        Scene scene = new Scene(layout);

        startBut.setOnAction(e -> {
            window.setScene(scene);
            timestep = ogTimestep;
            timer.start();
        });

        systemChange.setOnAction(e -> {
            if (system == 1) {
                system = 2;
            } else {
                system = 1;
            }
            label.setText("Selected system: " + system);
            try {
                starsystem = getPlanets(layout);
            } catch (Exception ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            standard = starsystem.getFurthest().getPos().length() / 300;
            ogStandard = standard;

        });

        startBox.getChildren().addAll(startBut, systemChange, label);
        startLayout.setCenter(startBox);
        timestep = 0;
        Scene startScreen = new Scene(startLayout, width, height);

        nappi.setOnAction(e -> {
            window.setScene(startScreen);
            normalTimestep = timestep;
            timestep = 0;
            timer.stop();
        });
        toinen.setOnAction(e -> {
            spoopy = !spoopy;
        });

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(1, 1, 1, 1));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        //hbox.getChildren().addAll(nappi, toinen);
        StackPane stackpane = new StackPane();
        //layout.setTop(hbox);
//        start screen:

        window.setScene(startScreen);

        window.show();

        //MOVE CANVAS
        canvas.setOnMouseMoved(e -> mouseCoord(e));
        dragScreen(canvas);
        create(canvas);
        clickPlanets(canvas);

        controls(scene, layout);

    }

    private PlanetSystem getPlanets(BorderPane layout) throws Exception {
        //Initialize planets from database and draw circles for each planet
        planets.clear();
        layout.getChildren().removeAll(circles);
        circles.clear();
        this.database = new Database("jdbc:sqlite:database.db");
        PlanetDao planetDao = new PlanetDao(database);
        planets = planetDao.findAllFromSystem(system);
        for (Planet p : planets) {
            Circle circle = makeCircle(p);
            circles.add(circle);
        }
        circlesSize = circles.size();
        layout.getChildren().addAll(circles);
        return new PlanetSystem(planets);
    }

    private void mouseCoord(MouseEvent e) {
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
    }

    private void dragScreen(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {

                if (e.isPrimaryButtonDown()) {
                    if (!createMode) {
                        double changeY = e.getSceneY() - mouseY;
                        midHeight += changeY;
                        mouseY = e.getSceneY();
                        double changeX = e.getSceneX() - mouseX;
                        midWidth += changeX;
                        mouseX = e.getSceneX();
                        follow = false;
                    }
                } else if (e.isSecondaryButtonDown()) {
                    double changeY = e.getSceneY() - mouseY;
                    if (standard + changeY * 1E7 > 1E6) {
                        standard += changeY * 1E7;
                    }
                    mouseY = e.getSceneY();
                }
                if (createMode) {
                    changeX = e.getSceneX() - mouseX;
                    changeY = e.getSceneY() - mouseY;
                }

            }
        });
    }

    private void create(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (createMode) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        createX = (e.getSceneX() - midWidth) * standard;
                        createY = (e.getSceneY() - midHeight) * standard;
                        mouseX = e.getSceneX();
                        mouseY = e.getSceneY();
                        //line.setVisible(true);
                        draw = true;
                    }
                }
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                if (createMode) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        changeY = e.getSceneY() - mouseY;
                        createVelY = -changeY * 200;
                        changeX = e.getSceneX() - mouseX;
                        createVelX = -changeX * 200;
                        Planet newP = new Planet(null, createX, createY, createVelX, createVelY, 0);
                        planets.add(newP);
                        circles.add(makeCircle(newP));
                        //line.setVisible(false);
                        draw = false;
                    }
                }

            }
        });

    }

    private void clickPlanets(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                if (!createMode) {
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
            }
        });

    }

    private void controls(Scene scene, BorderPane layout) {
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
                if (!spoopy) {
                    layout.getChildren().removeAll(circles);
                } else if (spoopy) {
                    layout.getChildren().addAll(circles);
                }
                spoopy = !spoopy;
            }
            if (e.getCode() == KeyCode.C) {
                createMode = !createMode;
            }
            if (e.getCode() == KeyCode.LEFT && e.isControlDown()) {
                if (subTargetId == 0) {
                    subTargetId = planets.size();
                }
                subTargetId--;
            } else if (e.getCode() == KeyCode.LEFT) {
                if (followId == 0) {
                    followId = planets.size();
                }
                followId--;
            }
            if (e.getCode() == KeyCode.RIGHT && e.isControlDown()) {
                if (subTargetId == planets.size() - 1) {
                    subTargetId = -1;
                }
                subTargetId++;
            } else if (e.getCode() == KeyCode.RIGHT) {
                if (followId == planets.size() - 1) {
                    followId = -1;
                }
                followId++;
            }

        });
    }

    private Circle makeCircle(Planet p) {
        Circle circle = new Circle(width / 2 + p.getPos().getX() / standard, height / 2 + p.getPos().getY() / standard, p.getDrawSize());
        circle.setFill(p.getColor());
        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int i = 0;
            int x = 0;
            for (Planet planet : planets) {
                if (p.equals(planet)) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        if (followId == i) {
                            follow = false;
                            subTarget = false;

                        } else {
                            if (subTargetId == i) {
                                subTargetId = followId;
                            }
                            followId = i;
                        }

                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        if (subTargetId == i) {
                            subTarget = !subTarget;
                        } else {
                            subTarget = true;
                            if (followId != i) {
                                subTargetId = i;
                            }
                        }
                    }

                    follow = true;

                }
                i++;
            }

        });

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
