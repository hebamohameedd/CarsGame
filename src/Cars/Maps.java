package Cars;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Maps extends JPanel {
    private CarsGame animInstance;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton map1Button;
    private JButton map2Button;
    private JButton map3Button;
    private JButton backtoMenubutton;
    private Image backgroundImage;


    public Maps(CarsGame animInstance, CardLayout cardLayout, JPanel contentPanel) {
        this.animInstance = animInstance;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        backgroundImage = new ImageIcon("src//Assets//1.png").getImage();
        setLayout(null);

        map1Button = createStyledButton("Map 1");
        map1Button.setLocation(300, 150);
        map1Button.addActionListener((ActionEvent e) -> {
            cardLayout.show(contentPanel, "Levels");
        });

        map2Button = createStyledButton("Map 2");
        map2Button.setLocation(300, 225);
        map2Button.addActionListener((ActionEvent e) -> {
            cardLayout.show(contentPanel, "Levels");
        });

        map3Button = createStyledButton("Map 3");
        map3Button.setLocation(300, 300);
        map3Button.addActionListener((ActionEvent e) -> {
            cardLayout.show(contentPanel, "Levels");
        });

        backtoMenubutton = createStyledButton("Back to Menu");
        backtoMenubutton.setSize(150, 30);
        backtoMenubutton.setLocation(20, 500);
        backtoMenubutton.addActionListener((ActionEvent e) -> {
            cardLayout.show(contentPanel, "Homepage");
        });

        add(map1Button);
        add(map2Button);
        add(map3Button);
        add(backtoMenubutton);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setSize(200, 60);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.WHITE);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
