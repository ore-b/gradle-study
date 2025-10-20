package kr.co.oreb;

public class App {

    public String getGreeting() {
        return "CustomLib identifier is: " + CustomLib.identifier;
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}