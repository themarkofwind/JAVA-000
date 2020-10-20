import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        // load class
        Class clazz = new MyClassLoader().findClass("Hello");
        // execute method with reflecting
        if (null != clazz) {
            Method method = clazz.getDeclaredMethod("hello");
            method.invoke(clazz.newInstance());
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        // load converted class file
        Path path = Paths.get("./" + name + ".xlass");
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println("can not find class file");
            return null;
        }

        // resume class file
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }

        return defineClass(name, bytes, 0, bytes.length);
    }
}
