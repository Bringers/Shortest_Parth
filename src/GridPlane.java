import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridPlane extends JPanel {
    private ArrayList<Edge> edges;
    private int[] poses;

    public GridPlane(LayoutManager l){
        super.setLayout(l);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        if(edges.size() != 0){
            for(int i = 0; i<edges.size(); i++){
                g2.setPaint(Color.BLACK);
                poses = edges.get(i).positions();
                if(edges.get(i).isPath()){
                    g2.setPaint(Color.RED);
                }
                g2.drawLine(poses[0],poses[1],poses[2],poses[3]);   //thick
            }
        }
    }

    public void add_Edges(ArrayList<Edge> all_edges){
        edges = all_edges;
    }
}
