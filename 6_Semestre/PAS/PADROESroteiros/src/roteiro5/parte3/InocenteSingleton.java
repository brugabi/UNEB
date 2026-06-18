package roteiro5.parte3;

public class InocenteSingleton {

    private InocenteSingleton() {
    }

    public static InocenteSingleton getInstance(){
        return new InocenteSingleton();
    }
}
