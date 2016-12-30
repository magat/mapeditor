package info.magat.mapeditor;


import static org.lwjgl.opengl.GL11.*;

public class Painter {

    int gridSide = 10;
    float spacePercent = 0.1f;
    float cellSide = 2f / (gridSide + spacePercent * (gridSide + 1));

    public Painter(int originalWidth, int originalHeight) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black and opaque
        resize(originalWidth, originalHeight);
    }

    public void resize(int width, int height) {
        int min = Math.min(width, height);
        int x = Math.max(0, width - min) / 2;
        int y = Math.max(0, height - min) / 2;
        glViewport(x, y, min, min);
    }

    public void paint() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glBegin(GL_QUADS);
        glColor3f(1.0f, 0.0f, 0.0f);

        float startX = -1.0f + cellSide * spacePercent;
        float startY = 1.0f - cellSide * spacePercent;

        for (int i = 0; i < gridSide; i++) {
            for (int j = 0; j < gridSide; j++) {
                cell(startX + i * (cellSide * (1 + spacePercent)), startY - j * (cellSide * (1 + spacePercent)));
            }
        }
        glEnd();

        glFlush();  // Render now
    }

    private void cell(float x, float y) {
        glVertex2f(x, y);
        glVertex2f(x + cellSide, y);
        glVertex2f(x + cellSide, y - cellSide);
        glVertex2f(x, y - cellSide);
    }
}
