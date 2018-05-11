/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.ui;

import java.sql.SQLException;
import planetsim.domain.Planet;
import planetsim.domain.PlanetSystem;
import planetsim.domain.Vector;
import planetsim.database.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PlanetSystemSimulation extends Application {

    private static final String SPACEURL = "file:images/avaruustausta.jpg";
    private static final String SKELEURL = "file:images/skeleton.gif";
    private static final String USSRURL = "file:images/ussr.png";
    private static final String PLANETURL = "file:tausta.jpg";
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
    double days;
    Integer timestep = 6000;
    Integer ogTimestep = timestep;
    Integer normalTimestep = timestep;
    boolean follow = false;
    boolean subTarget = false;
    boolean spoopy = false;
    boolean dtWarning = false;
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
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext drawer = canvas.getGraphicsContext2D();
        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);

        this.database = new Database("jdbc:sqlite:database.db");
        planetDao = new PlanetDao(database);

        starsystem = getPlanets(layout);
        if (starsystem.getFurthest() != null) {
            standard = starsystem.getFurthest().getPos().length() / 300;
        }
        ogStandard = standard;
        days = 0.0;
        AnimationTimer timer = new AnimationTimer() {
            long prev = 0;
            Image space = new Image(SPACEURL);
            Image uusr = new Image(SKELEURL);
            Image ussr = new Image(USSRURL);

            double kerroin = 1E6;

            @Override
            public void handle(long now) {
                if (now - prev < 1E7) {
                    return;
                }

                days = days + 1.0 * timestep / (60 * 60 * 24);

                if (spoopy) {
                    drawer.drawImage(space, 0, 0, width, height);
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
                //Warning for too high timestep
                if (Math.abs(dt) > 2000) {
                    drawer.fillText("WARNING! Big timesteps can cause big problems!", width * 1 / 3, height * 1 / 8);
                }
                //Follow target
                if (follow) {
                    midWidth = width / 2 - planets.get(followId).getPos().getX() / standard;
                    midHeight = height / 2 - planets.get(followId).getPos().getY() / standard;
                    drawer.fillText(day + " days\n" + year + " years\n" + "timestep: " + dstep + " min\nTarget: " + planets.get(followId).getName(), timeX, timeY);
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
                    drawer.fillText(day + " days\n" + year + " years\n" + "timestep: " + dstep + " min", timeX, timeY);

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

                    } else if (spoopy) {
                        drawer.drawImage(uusr, midWidth + p.getPos().getX() / standard, midHeight + p.getPos().getY() / standard, 300, 300);
                    } else {
                        circles.get(i).setCenterX(midWidth + p.getPos().getX() / standard);
                        circles.get(i).setCenterY(midHeight + p.getPos().getY() / standard);
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

        Button back = new Button("Back!");

        VBox changeBox = new VBox();
        changeBox.setAlignment(Pos.CENTER);
        changeBox.setStyle("-fx-background-color: darkgray");
        Label addSysLabel = new Label("Give system name:");
        TextField textfield = new TextField();
        textfield.setMaxWidth(150);
        Label addSysWarning = new Label("Name must be unique and can't be empty!");
        Button addButton = new Button("Create System!");
        Button returnToStart = new Button("Go back");
        changeBox.getChildren().addAll(addSysLabel, textfield, addSysWarning, addButton, returnToStart);

        BorderPane addSystem = new BorderPane();
        addSystem.setCenter(changeBox);
        Scene systemAdd = new Scene(addSystem, width, height);

        HBox hox = new HBox();
        hox.setLayoutX(100);
        hox.setLayoutY(45);
        back.setMinWidth(75);
        hox.getChildren().add(back);
        layout.getChildren().add(hox);
        Scene scene = new Scene(layout);

        //Add system screen buttons
        addButton.setOnAction(e -> {
            try {
                if (!textfield.getText().trim().isEmpty() && !planetDao.getSystems().contains(textfield.getText().trim())) {
                    planetDao.addSystem(textfield.getText());
                    window.setScene(setAddPlanetsScreen(window, systemAdd, scene, timer, layout));
                    textfield.clear();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        returnToStart.setOnAction(e -> {
            try {
                window.setScene(setStartScreen(window, systemAdd, scene, timer, layout));
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        back.setOnAction(e -> {
            try {
                window.setScene(setStartScreen(window, systemAdd, scene, timer, layout));
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            normalTimestep = timestep;
            timestep = 0;
            timer.stop();
        });

//        start screen:
        window.setScene(setStartScreen(window, systemAdd, scene, timer, layout));

        window.show();

        //MOVE CANVAS
        canvas.setOnMouseMoved(e -> mouseCoord(e));
        dragScreen(canvas);
        create(canvas);
        clickPlanets(canvas);

        controls(scene, layout);

    }

    private Scene setAddPlanetsScreen(Stage window, Scene systemAdd, Scene scene, AnimationTimer timer, BorderPane layout) throws SQLException {
        VBox planetInfo = new VBox();
        planetInfo.setSpacing(15);
        planetInfo.setAlignment(Pos.CENTER_LEFT);
        planetInfo.setStyle("-fx-background-color: darkgray");
        Label planetAdd = new Label("Give planet information!");

        HBox addPlanetBox = new HBox();

        VBox names = new VBox();
        names.setSpacing(16);
        VBox texts = new VBox();
        texts.setSpacing(5);

        Label nameLabel = new Label("Name (Unique and not empty):");
        TextField nameField = new TextField();
        nameField.setMaxWidth(200);

        Label xPos = new Label("x-coordinate of the planet(in AU):   ");
        TextField xPosField = new TextField();
        xPosField.setMaxWidth(200);

        Label yPos = new Label("y-coordinate of the planet(in AU):   ");
        TextField yPosField = new TextField();
        yPosField.setMaxWidth(200);

        Label xVel = new Label("x-coordinate of the velocity(in m/s): ");
        TextField xVelField = new TextField();
        xVelField.setMaxWidth(200);

        Label yVel = new Label("y-coordinate of the velocity(in m/s): ");
        TextField yVelField = new TextField();
        yVelField.setMaxWidth(200);

        Label mass = new Label("Mass (kg): ");
        TextField massField = new TextField();
        massField.setMaxWidth(200);

        Label size = new Label("Drawsize (1-5):");
        TextField sizeField = new TextField();
        sizeField.setMaxWidth(200);

        names.getChildren().addAll(nameLabel, xPos, yPos, xVel, yVel, mass, size);
        texts.getChildren().addAll(nameField, xPosField, yPosField, xVelField, yVelField, massField, sizeField);

        Button addPlanetButton = new Button("Add planet!");
        addPlanetButton.setOnAction(e -> {
            String name = nameField.getText();
            String posX = xPosField.getText();
            String posY = yPosField.getText();
            String velX = xVelField.getText();
            String velY = yVelField.getText();
            String massStr = massField.getText();
            String sizeStr = sizeField.getText();
            Planet p;
            try {
                p = newPlanet(name, posX, posY, velX, velY, massStr, sizeStr);
                if (p != null) {
                    planetDao.save(p);
                    window.setScene(setAddPlanetsScreen(window, systemAdd, scene, timer, layout));
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        addPlanetBox.getChildren().addAll(names, texts);
        Label disclaimer = new Label("Give all values in correct form: \nname = String, drawsize = integer, rest are double");
        Label auExplain = new Label("AU = average distance between Earth and Sun. \n1 AU = 1.496E11 meters");
        planetInfo.getChildren().addAll(planetAdd, addPlanetBox, addPlanetButton, disclaimer, auExplain);
        planetInfo.setPadding(new Insets(0, 0, 0, width / 10));

        BorderPane addPlanets = new BorderPane();
        VBox planetBox = new VBox();
        planetBox.setAlignment(Pos.CENTER);
        planetBox.setPadding(new Insets(0, width / 6, 0, 0));
        planetBox.setStyle("-fx-background-color: darkgray");
        ArrayList<String> planetNames = planetDao.findAllPlanetsOutsideSystem(planetDao.getHighestSystemId());
        ListView<String> nameList = new ListView<>();
        ObservableList<String> nameItems = FXCollections.observableArrayList(planetNames);
        nameList.setItems(nameItems);
        nameList.setMaxWidth(400);
        nameList.setPrefHeight(200);
        nameList.getSelectionModel().select(0);
        Label chooseLabel = new Label("Choose object to system!");
        HBox addAndDel = new HBox();
        addAndDel.setAlignment(Pos.CENTER);
        addAndDel.setSpacing(10);
        Button addPlanet = new Button("Add Object!");
        Button deletePlanet = new Button("Delete Object");
        Label deleteWarning = new Label("Default Objects cannot be deleted!");
        Button finishSystem = new Button("Finish system!");
        addAndDel.getChildren().addAll(addPlanet, deletePlanet);
        planetBox.getChildren().addAll(chooseLabel, nameList, addAndDel, deleteWarning, finishSystem);
        planetBox.setSpacing(5);

        addPlanets.setCenter(planetInfo);
        addPlanets.setRight(planetBox);
        addPlanet.setOnAction(e -> {
            try {
                planetDao.savePlanetToSystem(nameList.getSelectionModel().getSelectedItem());
                window.setScene(setAddPlanetsScreen(window, systemAdd, scene, timer, layout));
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        deletePlanet.setOnAction(e -> {
            try {
                planetDao.delete(nameList.getSelectionModel().getSelectedItem());
                window.setScene(setAddPlanetsScreen(window, systemAdd, scene, timer, layout));
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        finishSystem.setOnAction(e -> {
            try {
                window.setScene(setStartScreen(window, systemAdd, scene, timer, layout));
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return new Scene(addPlanets, width, height);
    }

    private Planet newPlanet(String name, String posX, String posY, String velX, String velY, String mass, String size) throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        for (Planet p : planetDao.findAll()) {
            names.add(p.getName());
        }
        if (name.trim().isEmpty() || names.contains(name.trim())) {
            return null;
        }
        Double doublePosX = 1.496E11;
        Double doublePosY = 1.496E11;
        Double doubleVelX;
        Double doubleVelY;
        Double doubleMass;
        Integer drawSize;
        try {
            doublePosX = doublePosX * Double.parseDouble(posX);
            doublePosY = doublePosY * Double.parseDouble(posY);
            doubleVelX = Double.parseDouble(velX);
            doubleVelY = Double.parseDouble(velY);
            doubleMass = Double.parseDouble(mass);
            drawSize = Integer.parseInt(size);
        } catch (Exception e) {
            return null;
        }
        if (drawSize < 1) {
            drawSize = 1;
        } else if (drawSize > 5) {
            drawSize = 5;
        }
        return new Planet(name, doublePosX, doublePosY, doubleVelX, doubleVelY, doubleMass, drawSize);
    }

    private Scene setStartScreen(Stage window, Scene systemAdd, Scene scene, AnimationTimer timer, BorderPane layout) throws SQLException {
        BorderPane startLayout = new BorderPane();
        VBox startBox = new VBox();
        startBox.setAlignment(Pos.CENTER);
        startBox.setSpacing(5);
        startBox.setPadding(new Insets(50, 200, 0, 0));

        //backgroundImage
        startBox.setStyle("-fx-background-image: url(" + PLANETURL + ")");

        Button startBut = new Button("start!");
        Button systemChange = new Button("Change system");
        Label label = new Label("Selected system: " + planetDao.getSystemName(system));
        label.setTextFill(Color.AZURE);
        Label changeWarning = new Label("System will reset on system change!");
        changeWarning.setTextFill(Color.CORAL);

        ArrayList<String> systems = planetDao.getSystems();
        ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(systems);
        list.setItems(items);
        list.setMaxWidth(150.0);
        list.setPrefHeight(70);
        list.getSelectionModel().select(0);

        HBox addAndDeleteSystemBox = new HBox();
        addAndDeleteSystemBox.setSpacing(10);
        addAndDeleteSystemBox.setAlignment(Pos.CENTER);
        Button newSystem = new Button("Add system");
        Button deleteSystem = new Button("Delete system");
        addAndDeleteSystemBox.getChildren().addAll(newSystem, deleteSystem);

        newSystem.setOnAction(e -> {
            window.setScene(systemAdd);
        });

        deleteSystem.setOnAction(e -> {
            try {
                if (list.getSelectionModel().getSelectedIndex() > 1) {
                    if (system == list.getSelectionModel().getSelectedIndex() + 1) {
                        system = 1;
                    }
                    planetDao.deleteSystem(list.getSelectionModel().getSelectedItem(), planetDao.getSystemId(list.getSelectionModel().getSelectedItem()));
                    window.setScene(setStartScreen(window, systemAdd, scene, timer, layout));
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        startBut.setOnAction(e -> {
            window.setScene(scene);
            timestep = ogTimestep;
            followId = 0;
            resetScreen();
            timer.start();
        });
        systemChange.setOnAction(e -> {
            try {
                changeSystem(list.getSelectionModel().getSelectedIndex());
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                label.setText("Selected system: " + planetDao.getSystemName(system));
            } catch (SQLException ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                starsystem = getPlanets(layout);
            } catch (Exception ex) {
                Logger.getLogger(PlanetSystemSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (starsystem.getFurthest() != null) {
                standard = starsystem.getFurthest().getPos().length() / 300;
            }
            ogStandard = standard;
            days = 0.0;

        });

        startBox.getChildren().addAll(startBut, systemChange, label, changeWarning, list, addAndDeleteSystemBox);
        startLayout.setCenter(startBox);
        return new Scene(startLayout, width, height);
    }

    private void changeSystem(int i) throws SQLException {
        system = 1 + i;
    }

    private PlanetSystem getPlanets(BorderPane layout) throws Exception {
        //Initialize planets from database and draw circles for each planet
        planets.clear();
        layout.getChildren().removeAll(circles);
        circles.clear();

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
            if (e.getCode() == KeyCode.BACK_SPACE) {
                timestep = ogTimestep;

            }
            if (e.getCode() == KeyCode.PERIOD) {
                timestep = timestep * 5;
            }
            if (e.getCode() == KeyCode.COMMA) {
                if (Math.abs(timestep) > 50) {
                    timestep = timestep / 5;
                }
            }
            if (e.getCode() == KeyCode.B) {
                timestep = timestep * -1;
            }
            if (e.getCode() == KeyCode.R) {
                resetScreen();
            }
            if (e.getCode() == KeyCode.DIGIT1) {
                follow = !follow;
            }
//            if (e.getCode() == KeyCode.S) {
//                if (!spoopy) {
//                    layout.getChildren().removeAll(circles);
//                } else if (spoopy) {
//                    layout.getChildren().addAll(circles);
//                }
//                spoopy = !spoopy;
//            }
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

    private void resetScreen() {
        standard = ogStandard;
        midWidth = width / 2;
        midHeight = height / 2;
        follow = false;
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

}
