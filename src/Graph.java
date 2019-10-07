import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private ArrayList<Edge> edges;
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edge_path;
    private Node start, end;
    private int size;
    private GridBagConstraints c;
    private JPanel panel;
    private Random rand;

    public Graph(int size, GridBagConstraints c, JPanel panel){
        this.size = size;
        this.c = c;
        this.panel = panel;
        this.rand = new Random();
        gen_Nodes();
        gen_Edges();
        calcValues();
    }

    private void gen_Nodes() {
        this.nodes = new ArrayList<>();

        for(int i = 0; i<size*2; i++) {
            Node node = new Node(i);
            nodes.add(node);
            CircleButton cbutton = node.getCircleButton();
            cbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(node.isSelected()){
                        node.setSelected(false);
                        if(isStart(node)){
                            setStart(null);
                        }else if(isEnd(node)){
                            setEnd(null);
                        }
                    }else{
                        node.setSelected(true);
                        if(getStart()==null){
                            setStart(node);
                        }else{
                            setEnd(node);
                        }
                    }
                    Edge[] edgeTemp = node.getEdges();
                    System.out.println(node.getId());
                    //System.out.println(edgeTemp[0].getValue() + " " + edgeTemp[1].getValue());
                    System.out.println("Clicked!");
                    System.out.println("State is " + node.isSelected());
                }
            });
            node.setXpos(rand.nextInt(size));
            node.setYpos(rand.nextInt(size));

            c.gridx = node.getXpos();
            c.gridy = node.getYpos();
            panel.add(cbutton, c);
        }

        for(int i = 0; i<size; i++){
            for(int y = 0; y<size; y++){
                JPanel panel2 = new JPanel();
                panel2.setPreferredSize(new Dimension(25,25));
                c.gridx = i;
                c.gridy = y;
                panel.add(panel2, c);
            }
        }
        System.out.println("NODES GENERATED");
    }

    private void gen_Edges(){
        this.edges = new ArrayList<>();
        Node n, n2;
        Edge e;
        int edgeid = 0;
        //Connects the nodes
        for(int i = 0; i<nodes.size(); i++){
            //For each node in nodes
            n=nodes.get(i);
            while(!n.isFull()){
                //While the node isn't full of edges, fill the edges
                n2 = nodes.get(rand.nextInt((nodes.size()-1 - i) + 1) + i);
                //This can get stuck if a full node is chosen over and over again but that should be of a very low possibility, if we can avoid this we should
                if(!n2.isFull() && !n.isConnected(n2.getId()) && (n2.getId()!= n.getId())){
                    e = new Edge(edgeid);
                    e.add_Node(n);
                    e.add_Node(n2);
                    n.add_Edge(e);
                    n2.add_Edge(e);
                    edgeid++;
                    //If the second node has room for an edge
                    //Connect the nodes with a new edge
                    //Edges should be placed in an array for easy manipulation of their weights
                    //Maybe each node should have a random amount of edges from 0 to 5
                    //Exit condition of the while loop then has to be edited so that it doesn't get stuck on the last node not "filled"
                }
            }
        }
        System.out.println("EDGES GENERATED");

    }

    public JPanel getPanel(){
        return panel;
    }

    public void resetGraph(int size, GridBagConstraints c, JPanel panel){
        this.size = size;
        this.c = c;
        this.panel = panel;
        this.rand = new Random();
        gen_Nodes();
        gen_Edges();
        calcValues();
    }

    public ArrayList<Node> getNodes(){
        return nodes;
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public void setStart(Node start){
        this.start = start;
    }

    public void setEnd(Node end){
        this.end = end;
    }

    public boolean isStart(Node node){
        if(start==null){
            return false;
        }
        return node.getId() == start.getId();
    }

    public boolean isEnd(Node node){
        if(end==null){
            return false;
        }
        return node.getId() == end.getId();
    }

    public Node getStart(){
        return start;
    }

    public Node getEnd() {
        return end;
    }

    private Node findSmallestValue(ArrayList<Node> Q){
        double val = Double.POSITIVE_INFINITY;
        Node u = Q.get(0);
        for(Node n : Q){
            if(n.getValue()<val){
                val = n.getValue();
                u = n;
            }
        }
        return u;
    }

    private double distance(Node u, Node v){
//        double dist = Double.POSITIVE_INFINITY;

        double dist = 0;
        for(Edge e : u.getEdges()){
            if(e != null && v != null) {
                if (e.isConnected(u.getId()) && e.isConnected(v.getId())) {
                    dist = e.getValue();
                }
            }
        }
        return dist;
    }

    private void calcValues(){
        for(int i = 0; i<this.edges.size(); i++){
            this.edges.get(i).calcValue();
        }
    }

    public double dijkstra(){
        Node u;
        double alt = 0;
        //Q is the set with unvisited nodes

        ArrayList<Node> Q = new ArrayList<Node>(nodes);
        //ArrayList<Node> Q = nodes;
        start.setValue(0);
        int itera = 0;
        while(!Q.isEmpty()){
            System.out.println("While loop iteration " + itera);
            itera++;
            u = findSmallestValue(Q);
            System.out.println(u.getId() + " is selected");
            if(u.getValue() == Double.POSITIVE_INFINITY){
                System.out.println("THE SMALLEST VALUE WAS INFINITY MEANING THE PATHS ARE NOT CONNECTED");
                break;
            }
            for(Node v : u.getConnectedNodes()){
                if(v != null){
                    alt = u.getValue() + distance(u,v);
                    if(alt<v.getValue()){
                        v.setValue(alt);
                        v.setPrevious(u);
                        nodes.get(v.getId()).setPrevious(nodes.get(u.getId()));

                    }
                }
            }
            // We check for end after the above part so that previous is updated
            if(u.getId() == end.getId()){
                System.out.println("The algorithm is done! We reached the end!");
                break;
            }
            make_path();
            System.out.println("path is " + alt);
            Q.remove(u);
        }
        //Here we go backwards from target through previously until we reach start, we want to mark the edges between them so we can paint the path
        //We use the nodes set since the unvisited set has removed nodes etc


        return alt;
    }

    public void make_path(){
        System.out.print("Path from end to start is edges: ");
        edge_path = new ArrayList<>();

        Node n = end;
        while(n.getPrevious() != null){
            Edge[] temp = n.getEdges();
            for(Edge e : temp){
                if(e != null){
                    if(e.isConnected(n.getPrevious().getId())){
                        System.out.print(e.getId() + " ");
                        e.setPath(true);
                        edge_path.add(e);
                    }
                }
            }
            n = n.getPrevious();
        }
        System.out.println();
    }

    public void reset_path(){
        if(edge_path != null) {
            for (Edge e : edge_path) {
                e.setPath(false);
            }
            for(Node n : nodes){
                n.setPrevious(null);
                n.setValue(Double.POSITIVE_INFINITY);
            }
            System.out.println("Path is reset!");
        }
    }
    public void bellman_ford(){

    }
}
