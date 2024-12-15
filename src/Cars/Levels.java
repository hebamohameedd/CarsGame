package Cars;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Levels extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private CarsGame carsGame;

    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton backtoMapsbutton;
    private Image backgroundImage;

    public Levels(CarsGame carsGame, CardLayout cardLayout, JPanel contentPanel) {
        this.carsGame = carsGame;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        backgroundImage = new ImageIcon("src//Assets//1.png").getImage();
        setLayout(null);


        easyButton = createStyledButton("Easy");
        easyButton.setLocation(300, 150);

        mediumButton = createStyledButton("Medium");
        mediumButton.setLocation(300, 225);

        hardButton = createStyledButton("Hard");
        hardButton.setLocation(300, 300);


        backtoMapsbutton = createStyledButton("Back to Maps");
        backtoMapsbutton.setSize(150, 30);
        backtoMapsbutton.setLocation(20, 500);
        backtoMapsbutton.addActionListener((ActionEvent e) -> {
            cardLayout.show(contentPanel, "Maps");
        });

        add(easyButton);
        add(mediumButton);
        add(hardButton);
        add(backtoMapsbutton);
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
