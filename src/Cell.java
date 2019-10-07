import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Cell extends JButton {
    public Cell(String path){
        try {
            setIcon(new ImageIcon(ImageIO.read(getClass().getResource(path))));
        }catch (Exception e){
            System.out.println(e);
        }
        setMargin(new Insets(0, 0, 0, 0));
        setBackground(Color.white);
        setBorder(null);
        setPreferredSize(new Dimension(25,25));
    }
}