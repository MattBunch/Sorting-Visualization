import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class DisplayGraphics extends Canvas {

	Random rand = new Random();
	private int[] mainArray;
	ArrayList<String> mainArrayList = new ArrayList<String>();

	public DisplayGraphics() {
//		mainArray = new int[MainClass.WIN_WIDTH_TRUE];
//		for (int i = 0; i < MainClass.WIN_WIDTH_TRUE; i++)
//			mainArray[i] = rand.nextInt(MainClass.WIN_HEIGHT_TRUE);// rand.nextInt(MainClass.WIN_HEIGHT)
		
		BubbleSort.runBubbleSort(mainArray);
		
//		System.out.println(Arrays.toString(mainArray));
//		new HeapSort();
//		HeapSort.runHeapSort(mainArray);
//		System.out.println(Arrays.toString(mainArray));

	}
	
	public void swap(int a, int b) {
		 int temp = a; 
         a = b; 
         b = temp;
		
	}
	
//	public void sleepFor(long delay) {
//		long timeElapsed;
//		final long startTime = System.nanoTime();
//		do {
//			timeElapsed = System.nanoTime() - startTime;
//		} while(timeElapsed < delay);
//		try {
//            Thread.sleep(delay);
//        } catch (InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
//		
//	}

	
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D panelGraphics = (Graphics2D) g.create();
		setBackground(Color.BLACK);
		g.setColor(Color.white);
		
		drawLines(panelGraphics);
//		
//		for (int i = 0; i < mainArray.length; i++) {
//			g.drawLine(i, MainClass.WIN_HEIGHT_TRUE, i, MainClass.WIN_HEIGHT_TRUE - mainArray[i]);
////			g.fillRect(i, 0, 1, mainArray[i]);
//		}	
	}
	
	public void drawLines(Graphics2D panelGraphics) {

		panelGraphics.setColor(Color.white);
		for (int i = 0; i < mainArray.length; i++) {
//			panelGraphics.drawLine(i, MainClass.WIN_HEIGHT_TRUE, i, MainClass.WIN_HEIGHT_TRUE - mainArray[i]);
//			g.fillRect(i, 0, 1, mainArray[i]);
		}	
	}

}
