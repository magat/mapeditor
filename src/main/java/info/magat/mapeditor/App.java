package info.magat.mapeditor;

import info.magat.mapeditor.drawable.Grid;
import info.magat.mapeditor.drawable.Layout;
import info.magat.mapeditor.input.Mouse;
import info.magat.mapeditor.painter.Painter;
import info.magat.mapeditor.window.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.lwjgl.glfw.GLFW.*;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private Window window;
    @Autowired
    private Painter painter;
    @Autowired
    private Mouse mouse;
    @Autowired
    private Layout layout;

    @Bean
    public Grid map(){
        return new Grid(10);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Run the rendering loop until the user has attempted to close
            // the window or has pressed the ESCAPE key.
            while (!glfwWindowShouldClose(window.getId())) {
                mouse.read();
                painter.paint(layout);
                window.refresh();
                // Poll for window events. All callbacks will only be invoked during this call.
                glfwPollEvents();
            }
            window.destroy();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
