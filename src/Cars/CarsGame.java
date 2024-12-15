package Cars;
import javax.swing.*;
import java.awt.*;

public class CarsGame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JLayeredPane layeredPane;

    public static void main(String[] args) { new CarsGame();}
     public CarsGame() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        Homepage homePage = new Homepage();
        Maps mapsPanel = new Maps(this, cardLayout, contentPanel);
        Levels levelsPanel = new Levels(this, cardLayout, contentPanel);
        homePage.setButtonActions(e -> {
            String command = e.getActionCommand();
            if ("Single Player".equals(command) || "Multi Player".equals(command)) {
                cardLayout.show(contentPanel, "Maps");
            } else if ("How to Play".equals(command)) {
                System.out.println("How to Play button pressed!");
            }});

        contentPanel.add(homePage, "Homepage");
        contentPanel.add(mapsPanel, "Maps");
        contentPanel.add(levelsPanel, "Levels");

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(800, 600));
        contentPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(contentPanel, JLayeredPane.DEFAULT_LAYER);

        setTitle("Cars Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().add(layeredPane);
        setVisible(true);
    }
}

