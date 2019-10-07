import static java.lang.Math.*;

public class Edge {
    private int id, pointer;
    private Node[] nodes;
    private double value;
    private boolean path;

    public Edge(int id){
        this.id = id;
        this.pointer = 0;
        this.nodes = new Node[2];
    }

    public void add_Node(Node n){
        nodes[pointer] = n;
        pointer++;
    }

    public int[] positions(){
        int offset = 12;
        int[] poses = new int[4];
        poses[0] = nodes[0].getCircleButton().getX()+offset;
        poses[1] = nodes[0].getCircleButton().getY()+offset;
        poses[2] = nodes[1].getCircleButton().getX()+offset;
        poses[3] = nodes[1].getCircleButton().getY()+offset;

        return poses;
    }

    public boolean isConnected(int id){
        for(int i = 0; i<nodes.length; i++){
            if(nodes[i] != null){
                if(nodes[i].getId()==id){
                    return true;
                }
            }
        }
        return false;
    }

    public void calcValue(){
        double temp = pow(abs(nodes[0].getXpos() - nodes[1].getXpos()), 2) + pow(abs(nodes[0].getYpos() - nodes[1].getYpos()),2);
        this.value = sqrt(temp);
        System.out.println("Value is : " + value);
    }

    public double getValue(){
        return value;
    }

    public Node[] getNodes(){ return nodes;}

    public int getId(){return id;}

    public boolean isPath(){
        return path;
    }

    public void setPath(boolean path){
        this.path = path;
    }
}
