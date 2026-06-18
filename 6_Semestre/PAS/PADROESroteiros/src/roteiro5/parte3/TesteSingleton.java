package roteiro5.parte3;

public class TesteSingleton {
    public static void main(String[] args) {
        InocenteSingleton n1 = InocenteSingleton.getInstance();
        InocenteSingleton n2 = InocenteSingleton.getInstance();

        System.out.println(n1 == n2 ? "Instâncias iguais" : "Instâncias diferentes");

        LazySingleton n3 = LazySingleton.getInstance();
        LazySingleton n4 = LazySingleton.getInstance();

        System.out.println(n3 == n4 ? "Instâncias iguais" : "Instâncias diferentes");

        EagerSingleton n5 = EagerSingleton.getInstance();
        EagerSingleton n6 = EagerSingleton.getInstance();

        System.out.println(n5 == n6 ? "Instâncias iguais" : "Instâncias diferentes");

        StaticSingleton n7 = StaticSingleton.instance;
        StaticSingleton n8 = StaticSingleton.instance;

        System.out.println(n7 == n8 ? "Instâncias iguais" : "Instâncias diferentes");
    }
}
