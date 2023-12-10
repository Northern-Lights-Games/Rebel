package newapi;

import static newapi.Game.*;

public class NewAPITest {
    public static void main(String[] args) {


        var player = new GameObject() {
            float x, y;
            float dx, dy;
            float width, height;


        };

        player.dx = (float) (300 * Math.random());
        player.dy = (float) (300 * Math.random());
        player.width = 120;
        player.height = 120;

        play("My game!", 640, 480, () -> {

            player.x += player.dx * Time.deltaTime;
            player.y += player.dy * Time.deltaTime;

            if(player.x + player.width >= gameWidth() || player.x <= 0)
                player.dx *= -1f;

            if(player.y + player.height >= gameHeight() || player.y <= 0)
                player.dy *= -1f;

            clearBackground(1f, 1f, 1f, 1f);
            drawTexture("project/logo.png", player.x, player.y, player.width, player.height);
        });


    }
}
