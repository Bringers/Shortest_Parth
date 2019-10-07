import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        run_program();
    }

    public static void run_program(){
        System.out.println("Hello World!");
        JFrame frame = new JFrame("Circle Button Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());

        GridPlane drawpanel = new GridPlane(new GridBagLayout());

        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel(new GridBagLayout());


        OverlayLayout overlay = new OverlayLayout(panel3);
        panel3.setLayout(overlay);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;

        final int gridsize = 25;

        Graph graph = new Graph(gridsize, c, panel);

        System.out.println("All calculated.");

        //Add a new panel with a new button that if pressed checks which nodes are selected and then makes the path
        JPanel option_panel = new JPanel();
        JButton d_button = new JButton("Dijkstras");
        JButton b_button = new JButton("Bellman Ford");
        option_panel.add(d_button);
        option_panel.add(b_button);

        d_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //get the required data and then run the dijkstras method

                graph.reset_path();
                System.out.println("We have selected node " + graph.getStart().getId() + " " + graph.getEnd().getId());
                System.out.println(graph.dijkstra());

                graph.getStart().getCircleButton().setMouseClicked(false);
                graph.getEnd().getCircleButton().setMouseClicked(false);
                graph.getStart().setSelected(false);
                graph.getEnd().setSelected(false);
                graph.setEnd(null);
                graph.setStart(null);

                drawpanel.repaint();
                panel3.repaint();
                option_panel.repaint();

            }
        });
/*
        b_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WE TRIED TO RESTART!");
                panel.removeAll();
                graph.resetGraph(gridsize, c, panel);
                drawpanel.add_Edges(graph.getEdges());
                drawpanel.repaint();

                panel3.repaint();
            }
        });
*/
        drawpanel.add_Edges(graph.getEdges());
        panel3.add(drawpanel);
        panel3.add(panel);

        panel4.add(panel3);
        panel4.add(option_panel);


        frame.add(panel4);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Connecting nodes with edges
     * Choose the first node
     * If node is full, choose next node
     * Randomly choose another node
     * If both nodes has a spot to fill, connect them
     * Otherwise choose another node
     * Continue until all nodes has filled their edges
     *
     * (we then calculate edge lengths)
     */

}
