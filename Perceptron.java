import java.util.*;
import java.io.*;
public class Perceptron {

    float[] weights;
    float lr = 0f;
    Random rand = new Random();
    BufferedReader br = null;
    String str;
    int[] inputs = new int[4];//r g b 1

    public Perceptron(float lr) throws IOException {

    	this.lr = lr;
    	weights = new float[4];

    	for(int i = 0; i < 4; i++) {
        	weights[i] = rand.nextFloat() * 2 - 1;
        	System.out.println(weights[i]);
        }

        str = null;
    	br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("red.txt"))));
        while((str = br.readLine()) != null) {
            inputs = inputsWithBias(toIntArray(str.split(",")));
            train(inputs, -1);
        }

	str = null;
	br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("blue.txt"))));
        while((str = br.readLine()) != null) {
            inputs = inputsWithBias(toIntArray(str.split(",")));
            train(inputs, 1);
        }

        br.close();
    }
    public int[] inputsWithBias(int arr[]) {
    	int inp[] = new int[4];
	for(int i = 0; i < arr.length; i++)
		inp[i] = arr[i];
	inp[3] = 1;
    	return inp;
    }

    public int[] toIntArray(String arr[]) {
        return Arrays.stream(arr).mapToInt(Integer :: parseInt).toArray();
    }

    public int feedForward(int inputs[]) {
    	float sum = 0;
    	for(int i = 0; i < inputs.length; i++) {
    		sum += inputs[i] * weights[i];
    	}
    	return sum > 0 ? 1 : -1;
    }

    public void train(int inputs[], int targetOutput) {
    	int currentGuess = feedForward(inputs);
    	int error = targetOutput - currentGuess;
    	for(int i = 0; i < weights.length; i++) {
    		weights[i] += (lr * error * inputs[i]);
    	}
    }

    public static void main(String args[]) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	Perceptron p = new Perceptron(0.02f);
        while(true) {
        	String st = br.readLine();
        	if(st.equals("exit"))
        		return;
        	System.out.println(st);
        	String rgb[] = st.trim().split(" ");
        	System.out.println(p.feedForward(p.toIntArray(rgb)) == -1 ? "Reddish" : "Bluish");
        }
    }
}
