import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * <p>
 * 
 * @author Matt Bunch
 * @version 1.0
 * @param sorts an array in multiple methods through javafx GUI.
 * @since 30-11-2020
 */

public class MainClass extends Application {

	Random rand = new Random();

	// Size of window
	private static final int WIN_WIDTH = 500;
	private static final int WIN_HEIGHT = 500;

	private static final int MAIN_SCENE_WIN_WIDTH = (WIN_WIDTH * 2) - 1;

	private static final int ANIMATION_DURATION = 1;

	// array
	int[] mainArray;

	// stage
	Stage window;

	Scene menuScene;
	Scene mainScene;

	VBox layout;
	Canvas canvas;
	GraphicsContext gc;

	Timeline t;

	// title
	Label titleLabel = new Label("Sorting Visualizer");

	// buttons
	Button bubbleSortButton = new Button("bubble sort");
	Button insertionSortButton = new Button("insertion sort");
	Button quickSortButton = new Button("quick sort");
	Button heapSortButton = new Button("heap sort");
	Button quitButton = new Button("quit");

	int i = 0;
	int j = 0;

	static int counter = 1;

	boolean animationRunning = false;

	public static void main(String[] args) {
		Application.launch();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		window = arg0;

		// initialize array
		mainArray = initializeArray(WIN_WIDTH);
		printArray();

		window.setTitle("Sorting visualizer");
		window.setResizable(false);
		window.setOnCloseRequest(e -> System.exit(0));

		menuScene = createMenu(window);
		mainScene = createMainScene(window);

		menuScene.getStylesheets().add("style.css");
		titleLabel.setId("title-text");

		window.setScene(menuScene);
		window.show();
	}

	private Scene createMenu(Stage stage) {

		layout = new VBox();
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(5);

		layout.getChildren().addAll(titleLabel, bubbleSortButton, insertionSortButton, quickSortButton, heapSortButton,
				quitButton);

		bubbleSortButton.setOnAction(e -> {
			stage.setScene(mainScene);
			runBubbleSortAnimation(mainArray, canvas, gc);
		});
		
		insertionSortButton.setOnAction(e -> {
			stage.setScene(mainScene);
			runInsertionSortAnimation(mainArray, canvas, gc);
		});
		
		quitButton.setOnAction(e -> {
			stage.close();
		});

		return new Scene(layout, MAIN_SCENE_WIN_WIDTH, WIN_HEIGHT);
	}

	private Scene createMainScene(Stage stage) {

		layout = new VBox();
		layout.setAlignment(Pos.CENTER);

		// canvas setup
		canvas = new Canvas(WIN_WIDTH * 2, WIN_HEIGHT);
		gc = canvas.getGraphicsContext2D();

		layout.getChildren().add(canvas);

		Scene output = new Scene(layout, MAIN_SCENE_WIN_WIDTH, WIN_HEIGHT);

		output.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			// pause game
			if (key.getCode() == KeyCode.ESCAPE) {
				escapeFunction(window, menuScene, mainArray, canvas, gc, WIN_WIDTH);
//				window.setScene(menuScene);
//				reset(canvas, gc, mainArray, WIN_WIDTH, t);
			}
			// TODO: do pause, slowdown, speedup, etc. here
			else if (key.getCode() == KeyCode.SPACE) {
				pausePlay();
				System.out.println("space key pressed");
			} else if (key.getCode() == KeyCode.LEFT) {
				slowdown();
				System.out.println("left key pressed");
			} else if (key.getCode() == KeyCode.DOWN) {
				normalSpeed();
				System.out.println("down key pressed");
			} else if (key.getCode() == KeyCode.RIGHT) {
				speedup();
				System.out.println("right key pressed");
			} else if (key.getCode() == KeyCode.I) {
				// TODO: print array in new window pop up
				System.out.println("'i' key pressed");
			} else if (key.getCode() == KeyCode.H) {
				// help window pop up
			}
		});

		return output;
	}

	private void escapeFunction(Stage stage, Scene scene, int[] input, Canvas canvas, GraphicsContext gc, int width) {
		stage.setScene(scene);
		reset(canvas, gc, input, width);
		createMainScene(stage);
	}

	private int[] initializeArray(int array_size) {

		int[] output = new int[array_size];
		for (int i = 0; i < array_size; i++)
			output[i] = rand.nextInt(array_size);

		return output;
	}

	private void randomizeArray(int[] input, int array_size) {
		for (int i = 0; i < input.length; i++) {
			input[i] = rand.nextInt(array_size);
		}
	}

	private void printArray(int[] input) {
		for (int i = 0; i < input.length; i++)
			System.out.println(input[i]);
	}

	private void printArray() {
		System.out.println("\n############## NEW ARRAY #" + counter++ + "  ##################\n");
		printArray(mainArray);
	}

	/*
	 * CANVAS STUFF
	 */

	private void drawArray(int[] input, GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		for (int i = 0; i < input.length; i++) {
			gc.strokeLine(2 * i, WIN_WIDTH, 2 * i, WIN_WIDTH - input[i]);
			gc.strokeLine(2 * i + 1, WIN_WIDTH, 2 * i + 1, WIN_WIDTH - input[i]);
		}
	}

	private void drawRedLine(int i, int[] input, GraphicsContext gc) {
		gc.setStroke(Color.RED);
		gc.strokeLine(2 * i, WIN_WIDTH, 2 * i, WIN_WIDTH - input[i]);
		gc.strokeLine(2 * i + 1, WIN_WIDTH, 2 * i + 1, WIN_WIDTH - input[i]);

	}

	private void clearCanvas(Canvas canvas, GraphicsContext gc) {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	private void refreshCanvas(int num, int[] input, Canvas canvas, GraphicsContext gc) {
		// clear screen
		clearCanvas(canvas, gc);
		// draw updated array
		drawArray(input, gc);
		drawRedLine(num, input, gc);
	}

	// SORTS

	private void runBubbleSortAnimation(int[] input, Canvas canvas, GraphicsContext gc) {
		// KeyFrame
		KeyFrame frame = new KeyFrame(Duration.millis(ANIMATION_DURATION), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (input[j] > input[j + 1]) {

					int temp = input[j];
					input[j] = input[j + 1];
					input[j + 1] = temp;
				}

				j = j + 1;

				if (j >= input.length - 1) {
					j = 0;
					i++;
				}

				// refresh canvas
				refreshCanvas(j, input, canvas, gc);
			}
		});

		startTimeline(frame);
	}

	private void runInsertionSortAnimation(int[] input, Canvas canvas, GraphicsContext gc) {

		i = 1;

		// KeyFrame
		KeyFrame frame = new KeyFrame(Duration.millis(ANIMATION_DURATION), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				// TODO:
				// insertion sort
				int key = input[i];
				j = i - 1;

				/*
				 * Move elements of input[0...i-1], that are greater than key, to one position
				 * ahead of their current position
				 */
				while (j >= 0 && input[j] > key) {
					input[j + 1] = input[j];
					j = j - 1;
				}

				input[j + 1] = key;

				// iterate i
				i++;

				// refresh canvas
				refreshCanvas(j, input, canvas, gc);
			}
		});

		startTimeline(frame);
	}

	private void runHeapSortAnimation(int[] input, Canvas canvas, GraphicsContext gc) {
		// KeyFrame
		KeyFrame frame = new KeyFrame(Duration.millis(ANIMATION_DURATION), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				// TODO:
				// heap sort

				// refresh canvas
				refreshCanvas(j, input, canvas, gc);
			}
		});

		startTimeline(frame);
	}

	/*
	 * TIMELINE
	 */

	private void startTimeline(KeyFrame frame) {
		t = new Timeline(frame);
		t.setCycleCount(Animation.INDEFINITE);
		playAnimation();
	}

	private void pausePlay() {

		if (t == null) {
			return;
		}

		if (animationRunning) {
			pauseAnimation();
		} else {
			playAnimation();
		}
	}

	private void pauseAnimation() {
		t.pause();
		animationRunning = false;
	}

	private void playAnimation() {
		t.play();
		animationRunning = true;
	}

	private void slowdown() {
		t.setRate(0.5);
	}

	private void normalSpeed() {
		t.setRate(1);
	}

	private void speedup() {
		t.setRate(2);
	}

	private void reset(Canvas canvas, GraphicsContext gc, int[] input, int width) {
//		t.stop();
		t = null;
		clearCanvas(canvas, gc);
		randomizeArray(input, width);
		i = 0;
		j = 0;
		printArray();
	}

}
