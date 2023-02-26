import util.Password;

public class Test {
    public static void main(String[] args) {
        System.out.println(Password.encode("password"));
        System.out.println(Password.encode("password"));
        System.out.println(Password.encode("password").length());
    } 
}