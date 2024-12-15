package Cars;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Homepage extends JPanel {
    private JButton singlePlayerButton;
    private JButton multiPlayerButton;
    private JButton howToPlayButton;
    private Image backgroundImage;

    public Homepage() {
        backgroundImage = new ImageIcon("src//Assets//1.png").getImage();
        setLayout(null);


        singlePlayerButton = createStyledButton("Single Player");
        singlePlayerButton.setLocation(300, 150);

        multiPlayerButton = createStyledButton("Multi Player");
        multiPlayerButton.setLocation(300, 225);

        howToPlayButton = createStyledButton("How to Play");
        howToPlayButton.setLocation(300, 300);

        add(singlePlayerButton);
        add(multiPlayerButton);
        add(howToPlayButton);
    }
    public void setButtonActions(ActionListener actionListener) {
        singlePlayerButton.addActionListener(actionListener);
        multiPlayerButton.addActionListener(actionListener);
        howToPlayButton.addActionListener(actionListener);
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
        }}}




