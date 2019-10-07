import java.util.Random;

public class Node {
    private int id, pointer;
    private int xpos, ypos;
    private Edge[] edges;
    private CircleButton circleButton;
    private boolean selected;
    private double value;
    private Node previous;
    private boolean visited;

    public Node(int id){
        Random rand = new Random();
        this.id = id;
        this.circleButton = new CircleButton("");
        this.edges = new Edge[3]; // this will later be of a random size
        this.pointer = 0;
        this.value = Double.POSITIVE_INFINITY;
        this.previous = null;
        this.visited = false;
    }

    public void add_Edge(Edge e){
        edges[pointer] = e;
        pointer++;
    }

    public CircleButton getCircleButton(){
        return circleButton;
    }

    public int getId(){ return id;
    }

    public boolean isFull(){
        if(pointer+1 >= edges.length){
            return true;
        }else{
            return false;
        }
    }

    public int getXpos(){
        return xpos;
    }

    public int getYpos(){
        return ypos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public void setYpos(int ypos){
        this.ypos = ypos;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean state){
        selected = state;
    }

    public boolean isConnected(int id){
        for(int i = 0; i<edges.length; i++){
            if(edges[i] != null) {
                if (edges[i].isConnected(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Edge[] getEdges(){
        return edges;
    }

    public Node[] getConnectedNodes(){
        Node[] temp;
        Node[] nodes = new Node[edges.length];
        for(int i = 0; i<edges.length; i++){
            if(edges[i] != null){
                //System.out.println("getting connected nodes");
                temp = edges[i].getNodes();
                //System.out.println("Edge " + edges[i].getId() + " has nodes " + temp[0].getId() + " " + temp[1].getId());
                for(int k = 0; k<temp.length; k++){
                    if(temp[k].getId() != this.id){
                        nodes[i] = temp[k];
                    }
                }
            }
            System.out.print("The neighbors are: ");
            for(Node n : nodes){
                if(n != null){
                    System.out.print(n.getId() + " ");
                }
            }
            System.out.println();
        }
        return nodes;
    }

    public void setValue(double v){
        this.value = v;
    }

    public double getValue(){
        return value;
    }

    public void setPrevious(Node n){
        this.previous = n;
    }

    public Node getPrevious(){
        return previous;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
