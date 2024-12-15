
package Cars;

import Texture.TextureReader;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class AnimGLEventListener extends AnimListener {
    public AnimGLEventListener() {}

    String[] textureNames = {
            "ovlXrCtQ76btdfhP.jpg", "newcar.png", "car2.png", "car2.png",
            "lose.png",

    };
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    public int[] textures = new int[textureNames.length];

    int maxWidth = 100;
    int maxHeight = 100;

    // Road boundaries (percentage of maxWidth)
    double roadLeft = maxWidth * 0.25;
    double roadRight = maxWidth * 0.75;

    // Player Car variables
    double playerX = maxWidth / 2.0;
    double playerY = maxHeight / 5.0;

    // Enemy Car variables
    double enemy1X, enemy1Y, enemy2X, enemy2Y;
    float enemySpeed = 1.5f;

    // Background variables
    float backgroundOffset = 0.0f;
    float backgroundSpeed = 0.05f;
    double carRotationAngle = 0.0;

    // Game state
    boolean isGameOver = false;
    Random random = new Random();

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL.GL_TEXTURE_2D); // Enable texture mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); // Enable blending
        gl.glGenTextures(textureNames.length, textures, 0);

        // Load textures
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels()
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        // Initialize enemy car positions
        resetEnemyPositions();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        // رسم الخلفية
        DrawBackground(gl);

        // رسم سيارة اللاعب
        DrawSprite(gl, playerX, playerY, 1, 2.0f);

        // تحديث الأعداء إذا لم تنته اللعبة
        if (!isGameOver) {
            updateEnemyPositions();
        }

        // رسم الأعداء
        DrawSprite(gl, enemy1X, enemy1Y, 2, 2.0f);
        DrawSprite(gl, enemy2X, enemy2Y, 3, 2.0f);

        // التحقق من الاصطدام
        if (!isGameOver && (checkCollision(playerX, playerY, enemy1X, enemy1Y) ||
                checkCollision(playerX, playerY, enemy2X, enemy2Y))) {
            isGameOver = true;
            System.out.println("Game Over! Collision Detected.");
        }

        // عرض صورة "Game Over" عند انتهاء اللعبة
        if (isGameOver) {
            displayGameOverImage(gl);
        }
    }


    public void updateEnemyPositions() {
        // Move enemy cars down
        enemy1Y -= enemySpeed;
        enemy2Y -= enemySpeed;

        // Reset position if they go off screen
        if (enemy1Y < 0) {
            enemy1Y = maxHeight;
            enemy1X = roadLeft + random.nextDouble() * (roadRight - roadLeft); // Restrict to road
        }
        if (enemy2Y < 0) {
            enemy2Y = maxHeight + 60;
            enemy2X = roadLeft + random.nextDouble() * (roadRight - roadLeft); // Restrict to road
        }
    }

    public void resetEnemyPositions() {
        enemy1X = roadLeft + random.nextDouble() * (roadRight - roadLeft); // Restrict to road
        enemy1Y = maxHeight;

        enemy2X = roadLeft + random.nextDouble() * (roadRight - roadLeft); // Restrict to road
        enemy2Y = maxHeight + 60;
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f + backgroundOffset);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f + backgroundOffset);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f + backgroundOffset);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f + backgroundOffset);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);

        // Update background offset if the game is still running
        if (!isGameOver) {
            backgroundOffset += backgroundSpeed;
            if (backgroundOffset > 1.0f) {
                backgroundOffset -= 1.0f; // Reset offset to loop the background
            }
        }
    }

    public void DrawSprite(GL gl, double x, double y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 1.0, y / (maxHeight / 2.0) - 1.0, 0);

        //de eh ? to apply rotating on player
        if (x == playerX && y == playerY) {
            gl.glRotated(carRotationAngle, 0, 0, 1);
        }

        gl.glScaled(0.1 * scale, 0.1 * scale, 1); // Scale the sprite

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }



    public boolean checkCollision(double x1, double y1, double x2, double y2) {
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distance < 7.0; // Adjust collision threshold as needed
    }

    public void displayGameOverImage(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]); // "You Lose!" image texture

        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, 0); // Position the image in the center of the screen
        gl.glScaled(0.5, 0.5, 1); // Scale the image as needed

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {}

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (isGameOver) return; // Disable input after game over

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> playerY += 2.0;
            case KeyEvent.VK_DOWN -> playerY -= 2.0;
            case KeyEvent.VK_LEFT -> {
                playerX -= 2.0;
                carRotationAngle = 8;
            }
            case KeyEvent.VK_RIGHT -> {
                playerX += 2.0;
                carRotationAngle = -8;

            }

        }

        // Clamp player's position to road boundaries
        if (playerX < roadLeft) {
            playerX = roadLeft;
        } else if (playerX > roadRight) {
            playerX = roadRight;
        }
    }
    public void handleKeyRelease(int keyCode) {
        if (!isGameOver) {
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
                carRotationAngle = 0;
            }
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {
        handleKeyRelease(e.getKeyCode());
    }
}