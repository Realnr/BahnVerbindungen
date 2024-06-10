package control;

import model.Edge;
import model.Graph;
import model.List;
import model.Vertex;

public class MainController {

    //Attribute

    //Referenzen
    private Graph trainNetwork;

    public MainController(){
        trainNetwork = new Graph();
        createSomeStations();
    }

    /**
     * Fügt Stationen dem Bahnnetzwerk hinzu.
     */
    private void createSomeStations(){
        insertStation("Dortmund");
        insertStation("Münster");
        insertStation("Bochum");
        insertStation("Berlin");
        connect("Dortmund", "Münster", 60);
        connect("Dortmund", "Bochum", 15);
    }

    /**
     * Fügt eine Station hinzu, falls diese noch nicht existiert.
     * @param name
     * @return true, falls eine neue Station hinzugefügt wurde, sonst false.
     */
    public boolean insertStation(String name){
        //COMPLETE 05: Station dem Netzwerk hinzufügen.
        if(trainNetwork.getVertex(name) == null){
            trainNetwork.addVertex(new Vertex(name));
            return true;
        }
        return false;
    }

    /**
     * Löscht eine Station, falls dieser existiert. Alle Verbindungen zu anderen Stationen werden ebenfalls gelöscht.
     * @param name
     * @return true, falls eine Station gelöscht wurde, sonst false.
     */
    public boolean deleteStation(String name){
        //COMPLETE 07: Station aus dem Netzwerk entfernen.
        if(trainNetwork.getVertex(name) != null){
            trainNetwork.removeVertex(new Vertex(name));
            return true;
        }
        return false;
    }

    /**
     * Falls Stationen vorhanden sind, so werden ihre Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     * @return
     */
    public String[] getTrainNetwork(){
        //COMPLETE 06: String-Array mit allen Stationennamen erstellen.
        List<Vertex> vertices = trainNetwork.getVertices();
        if(vertices != null){
            int count = 0;
            vertices.toFirst();
            while (vertices.hasAccess()){
                count++;
                vertices.next();
            }
            if(count == 0)
                return null;
            String[] result = new String[count];
            vertices.toFirst();
            for (int i = 0; i < count; i++) {
                result[i] = vertices.getContent().getID();
                vertices.next();
            }
            return result;
        }
        return null;
    }

    /**
     * Falls die Station vorhanden ist und Verbindungen hat, so werden deren Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     * @param name
     * @return
     */
    public String[] getAllConnectedStationsFrom(String name){
        //TODO 09: Verbundene Stationen einer Station als String-Array erstellen.
        return null;
    }

    /**
     * Bestimmt den Zentralitätsgrad einer Station im Netzwerk, falls sie vorhanden ist. Sonst wird -1.0 zurückgegeben.
     * Der Zentralitätsgrad ist der Quotient aus der Anzahl der direkten Verbindungen einer Station und der um die Station selbst verminderten Anzahl an Stationen im Netzwerk.
     * Gibt also den Prozentwert an Stationen im Netzwerk an, mit der die Station verbunden ist.
     * @param name
     * @return
     */
    public double centralityDegreeOfStation(String name){
        //TODO 10: Prozentsatz der vorhandenen direkten Verbindungen einer Station von allen theoretisch möglichen Verbindungen der Station.
        return 0.125456;
    }

    /**
     * Zwei Stationen des Netzwerkes werden (gewichtet) verbunden, falls sie sich im Netzwerk befinden und noch keine Verbindung existiert sind.
     * @param name01
     * @param name02
     * @param weight
     * @return true, falls eine neue Verbindung entstanden ist, ansonsten false.
     */
    public boolean connect(String name01, String name02, int weight){
        //COMPLETE 08: Verbindung bauen.
        Vertex vertex1 = trainNetwork.getVertex(name01);
        Vertex vertex2 = trainNetwork.getVertex(name02);
        if(vertex1 != null && vertex2 != null && trainNetwork.getEdge(vertex1,vertex2) == null){
            trainNetwork.addEdge(new Edge(vertex1,vertex2,weight));
            return true;
        }
        return false;
    }

    /**
     * Die Verbindung zweier Stationen wird gekappt (Baustelle oder so), falls sie sich im Netzwerk befinden und verbunden sind.
     * @param name01
     * @param name02
     * @return true, falls ihre Verbindung entfernt wurde, ansonsten false.
     */
    public boolean disconnect(String name01, String name02){
        //TODO 11: Freundschaften beenden.
        return false;
    }

    /**
     * Bestimmt die Dichte des Netzwerks und gibt diese zurück.
     * Die Dichte ist der Quotient aus der Anzahl aller vorhandenen Verbindungen und der Anzahl der maximal möglichen Verbindungen.
     * @return
     */
    public double dense(){
        //TODO 12: Dichte berechnen.
        return 0.12334455676;
    }

    /**
     * Gibt eine mögliche Verbindung zwischen zwei Stationen im Netzwerk als String-Array zurück,
     * falls die Stationen vorhanden sind und sie über eine oder mehrere Verbindungen miteinander verbunden sind.
     * @param name01
     * @param name02
     * @return
     */
    public String[] getLinksBetween(String name01, String name02){
        Vertex station01 = trainNetwork.getVertex(name01);
        Vertex station02 = trainNetwork.getVertex(name02);
        if(station01 != null && station02 != null){
            //TODO 13: Schreibe einen Algorithmus, der mindestens eine Verbindung von einer Station (über ggf. Zwischenstationen) zu einer anderen Station bestimmt. Happy Kopfzerbrechen!
        }
        return null;
    }


    /**
     * Gibt eine kürzeste Verbindung zwischen zwei Stationen des Netzwerkes als String-Array zurück,
     * falls die Stationen vorhanden sind und sie über eine oder mehrere Stationen miteinander verbunden sind. (Dijkstra)
     * @param name01
     * @param name02
     * @return Verbindung als String-Array oder null, falls es keine Verbindung gibt.
     */
    public String[] shortestPath(String name01, String name02){
        Vertex station01 = trainNetwork.getVertex(name01);
        Vertex station02 = trainNetwork.getVertex(name02);
        if(station01 != null && station02 != null){
            //TODO 14: Schreibe einen Algorithmus, der die kürzeste Verbindung zwischen den Stationen name01 und name02 als String-Array zurückgibt. Beachte die Kantengewichte!
        }
        return null;
    }

}
