package noah;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {

            System.out.println("Start....");
            ACO aco = new ACO(48, 10, 100, 1.f, 5.f, 0.5f,5);
            aco.init("att49.txt");
            aco.solve();

    }
}
