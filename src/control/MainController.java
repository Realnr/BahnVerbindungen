package control;

import model.*;

import java.util.Objects;

public class MainController {

    //Attribute

    //Referenzen
    private Graph trainNetwork;

    public MainController() {
        trainNetwork = new Graph();
        createSomeStations();
    }

    /**
     * Fügt Stationen dem Bahnnetzwerk hinzu.
     */
    private void createSomeStations() {
        insertStation("Dortmund");
        insertStation("Münster");
        insertStation("Bochum");
        insertStation("Berlin");
        connect("Dortmund", "Münster", 60);
        connect("Dortmund", "Bochum", 15);
    }

    /**
     * Fügt eine Station hinzu, falls diese noch nicht existiert.
     *
     * @param name
     * @return true, falls eine neue Station hinzugefügt wurde, sonst false.
     */
    public boolean insertStation(String name) {
        //COMPLETE 05: Station dem Netzwerk hinzufügen.
        if (trainNetwork.getVertex(name) == null) {
            trainNetwork.addVertex(new Vertex(name));
            return true;
        }
        return false;
    }

    /**
     * Löscht eine Station, falls dieser existiert. Alle Verbindungen zu anderen Stationen werden ebenfalls gelöscht.
     *
     * @param name
     * @return true, falls eine Station gelöscht wurde, sonst false.
     */
    public boolean deleteStation(String name) {
        //COMPLETE 07: Station aus dem Netzwerk entfernen.
        Vertex vertex = trainNetwork.getVertex(name);
        if (vertex != null) {
            trainNetwork.removeVertex(vertex);
            return true;
        }
        return false;
    }

    /**
     * Falls Stationen vorhanden sind, so werden ihre Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     *
     * @return
     */
    public String[] getTrainNetwork() {
        if (trainNetwork.isEmpty()) {
            return null;
        }
        List<Vertex> vertices = trainNetwork.getVertices();
        String[] result = new String[countList(vertices)];
        vertices.toFirst();
        for (int i = 0; i < result.length; i++) {
            result[i] = vertices.getContent().getID();
            vertices.next();
        }
        return result;
    }

    /**
     * Falls die Station vorhanden ist und Verbindungen hat, so werden deren Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     *
     * @param name
     * @return
     */
    public String[] getAllConnectedStationsFrom(String name) {
        Vertex station = trainNetwork.getVertex(name);
        List<Vertex> connectedStations = trainNetwork.getNeighbours(station);
        if (connectedStations.isEmpty()) {
            return null;
        }
        String[] result = new String[countList(connectedStations)];
        connectedStations.toFirst();
        for (int i = 0; i < result.length; i++) {
            result[i] = connectedStations.getContent().getID();
            connectedStations.next();
        }
        return result;

    }

    /**
     * Bestimmt den Zentralitätsgrad einer Station im Netzwerk, falls sie vorhanden ist. Sonst wird -1.0 zurückgegeben.
     * Der Zentralitätsgrad ist der Quotient aus der Anzahl der direkten Verbindungen einer Station und der um die Station selbst verminderten Anzahl an Stationen im Netzwerk.
     * Gibt also den Prozentwert an Stationen im Netzwerk an, mit der die Station verbunden ist.
     *
     * @param name
     * @return
     */
    public double centralityDegreeOfStation(String name) {
        //COMPLETE 10: Prozentsatz der vorhandenen direkten Verbindungen einer Station von allen theoretisch möglichen Verbindungen der Station.
        if(trainNetwork.getVertex(name) == null){
            return -1;
        }
        String[] connectedStations = getAllConnectedStationsFrom(name);
        double allVerticesCount = countList(trainNetwork.getVertices()) - 1;

        if (connectedStations == null) {
            return 0;
        }
        if (allVerticesCount == 0) { // wenn es nur 1 Knoten gibt 
            return 1;
        }
        return (double) connectedStations.length / allVerticesCount;
    }

    /**
     * Zwei Stationen des Netzwerkes werden (gewichtet) verbunden, falls sie sich im Netzwerk befinden und noch keine Verbindung existiert sind.
     *
     * @param name01
     * @param name02
     * @param weight
     * @return true, falls eine neue Verbindung entstanden ist, ansonsten false.
     */
    public boolean connect(String name01, String name02, int weight) {
        Vertex vertex1 = trainNetwork.getVertex(name01);
        Vertex vertex2 = trainNetwork.getVertex(name02);
        if (vertex1 != null && vertex2 != null && trainNetwork.getEdge(vertex1, vertex2) == null) {
            trainNetwork.addEdge(new Edge(vertex1, vertex2, weight));
            return true;
        }
        return false;
    }

    /**
     * Die Verbindung zweier Stationen wird gekappt (Baustelle oder so), falls sie sich im Netzwerk befinden und verbunden sind.
     *
     * @param name01
     * @param name02
     * @return true, falls ihre Verbindung entfernt wurde, ansonsten false.
     */
    public boolean disconnect(String name01, String name02) {
        Vertex vertex1 = trainNetwork.getVertex(name01);
        Vertex vertex2 = trainNetwork.getVertex(name02);
        if (vertex1 == null && vertex2 == null) {
            return false;
        }
        Edge edge = trainNetwork.getEdge(vertex1, vertex2);
        if (edge != null) {
            trainNetwork.removeEdge(edge);
            return true;
        }
        return false;
    }

    /**
     * Bestimmt die Dichte des Netzwerks und gibt diese zurück.
     * Die Dichte ist der Quotient aus der Anzahl aller vorhandenen Verbindungen und der Anzahl der maximal möglichen Verbindungen.
     *
     * @return
     */
    public double dense() {
        //COMPLETE 12: Dichte berechnen.
        if(trainNetwork.getVertices() == null)
            return -1;
        int edgeCount = countList(trainNetwork.getEdges());
        int verticeCount = countList(trainNetwork.getVertices()) - 1;
        int possibleConnections = (verticeCount*(verticeCount+1))/2;
        return (double) edgeCount / possibleConnections;
    }

    /**
     * Gibt eine mögliche Verbindung zwischen zwei Stationen im Netzwerk als String-Array zurück,
     * falls die Stationen vorhanden sind und sie über eine oder mehrere Verbindungen miteinander verbunden sind.
     *
     * @param name01
     * @param name02
     * @return
     */
    public String[] getLinksBetween(String name01, String name02) {
        Vertex station01 = trainNetwork.getVertex(name01);
        Vertex station02 = trainNetwork.getVertex(name02);
        if (station01 == null || station02 == null) {
            return null;
        }

        trainNetwork.setAllVertexMarks(false);
        Queue<String> queue = new Queue<>();
        queue.enqueue(station01.getID());
        station01.setMark(true);

        while(!queue.isEmpty()) {
            String[] pathArray = queue.front().split(",");
            Vertex currentVertex = trainNetwork.getVertex(pathArray[pathArray.length - 1]);
            if (currentVertex.getID().equals(name02)) {
                return pathArray;
            }

            List<Vertex> neighbours = trainNetwork.getNeighbours(currentVertex);
            neighbours.toFirst();
            while (neighbours.hasAccess()) {
                if(!neighbours.getContent().isMarked())queue.enqueue(queue.front() + "," + neighbours.getContent().getID());
                neighbours.next();
            }
            currentVertex.setMark(true);
            queue.dequeue();
        }


        return null;
    }


    /**
     * Gibt eine kürzeste Verbindung zwischen zwei Stationen des Netzwerkes als String-Array zurück,
     * falls die Stationen vorhanden sind und sie über eine oder mehrere Stationen miteinander verbunden sind. (Dijkstra)
     *
     * @param name01
     * @param name02
     * @return Verbindung als String-Array oder null, falls es keine Verbindung gibt.
     */
    public String[] shortestPath(String name01, String name02) {
        Vertex station01 = trainNetwork.getVertex(name01);
        Vertex station02 = trainNetwork.getVertex(name02);
        if (station01 == null || station02 == null) {
            return null;
        }

        trainNetwork.setAllVertexMarks(false);
        Queue<String> queue = new Queue<>();
        queue.enqueue(station01.getID());
        station01.setMark(true);

        while(!queue.isEmpty()) {
            String[] pathArray = queue.front().split(",");
            Vertex currentVertex = trainNetwork.getVertex(pathArray[pathArray.length - 1]);
            if (currentVertex.getID().equals(name02)) {
                return pathArray;
            }

            List<Vertex> neighbours = trainNetwork.getNeighbours(currentVertex);
            neighbours.toFirst();
            while (neighbours.hasAccess()) {
                if(!neighbours.getContent().isMarked())queue.enqueue(queue.front() + "," + neighbours.getContent().getID());
                neighbours.next();
            }
            currentVertex.setMark(true);
            queue.dequeue();
            sortQueue(queue);
        }


        return null;
    }

    private Queue<String> sortQueue(Queue<String> queue){

        List<String> helpList = new List<>();
        helpList.insert(queue.front());
        queue.dequeue();


        while (!queue.isEmpty()) {
            double pathWeight = getPathWeight(queue.front());

            helpList.toFirst();
            while (helpList.hasAccess()) {
                if (pathWeight < getPathWeight(helpList.getContent())) {
                    helpList.insert(queue.front());
                    break;
                }
                helpList.next();
                if (!helpList.hasAccess()) helpList.append(queue.front());
            }

            queue.dequeue();
        }

        helpList.toFirst();
        while(helpList.hasAccess()){
            queue.enqueue(helpList.getContent());
            helpList.next();
        }

        return queue;
    }
    private double getPathWeight(String string){
        String[] pathArray = string.split(",");
        double pathWeight = 0;
        for (int i = 0; i < pathArray.length - 1; i++) {
            Vertex vertex01 = trainNetwork.getVertex(pathArray[i]);
            Vertex vertex02 = trainNetwork.getVertex(pathArray[i+1]);
            pathWeight+= trainNetwork.getEdge(vertex01,vertex02).getWeight();
        }
        return pathWeight;
    }

    private int countList(List list) {
        int count = 0;
        list.toFirst();
        while (list.hasAccess()) {
            count++;
            list.next();
        }
        return count;
    }

}
