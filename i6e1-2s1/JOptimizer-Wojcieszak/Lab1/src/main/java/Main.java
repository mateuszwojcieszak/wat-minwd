import com.joptimizer.exception.JOptimizerException;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;

public class Main {
    public static void main(String[] args) {
        LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(new double[]{4,3},0);

        ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[6];
        // x1 >= 0
        inequalities[0] = new LinearMultivariateRealFunction(new double[]{-1.0, 0.00}, 0.0);  // focus: -x1+0 <= 0
        // x2 >= 0
        inequalities[1] = new LinearMultivariateRealFunction(new double[]{0.0, -1.00}, 0.0);  // focus: -x2+0 <= 0
        // 6x1 + 3x2 >= 12
        inequalities[2] = new LinearMultivariateRealFunction(new double[]{-6.0, -3.00}, 12.0); // focus: -6x1-3x2+12 <= 0
        // x1 + 3x2 >= 6
        inequalities[3] = new LinearMultivariateRealFunction(new double[]{-1.0, -3.00}, 6.0);// focus: -x1-3x2+6 <= 0
        // 9x1 + x2 >= 3.6
        inequalities[4] = new LinearMultivariateRealFunction(new double[]{-9.0,-1.0},3.6);// focus: -9x1-x2+3.6 <= 0
        // 6x1 + 6x2 >= 18
        inequalities[5] = new LinearMultivariateRealFunction(new double[]{-6.0,-6.0},18);// focus: -6x1-6x2+18 <= 0

        //optimization problem
        OptimizationRequest optimizationRequest = new OptimizationRequest();
        optimizationRequest.setF0(objectiveFunction);
        optimizationRequest.setFi(inequalities);
        optimizationRequest.setToleranceFeas(1.e-9);
        optimizationRequest.setTolerance(1.e-9);

        JOptimizer optimizer = new JOptimizer();
        optimizer.setOptimizationRequest(optimizationRequest);
        try {
            optimizer.optimize();
        } catch (JOptimizerException e) {
            e.printStackTrace();
        }

        double[] solution = optimizer.getOptimizationResponse().getSolution();
        System.out.println("Length: " + solution.length);
        for (int i=0; i<solution.length/2; i++){
            System.out.println( "X1: " + Math.round(solution[i]) + "\tX2: " + Math.round(solution[i+1]) );
        }

        System.out.println("Y =" + (4d*Math.round(solution[0])+3d*Math.round(solution[1])));

    }
}