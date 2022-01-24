import  java.util.*;

public class Station {
    private Track head, tail, cursor;
    private  int  totalTrack = 0;

    public int getTotalTrack() {
        return totalTrack;
    }

    public void setTotalTrack(int totalTrack) {
        this.totalTrack = totalTrack;
    }

    public Track getHead() {
        return head;
    }

    public void setHead(Track head) {
        this.head = head;
    }

    public Track getTail() {
        return tail;
    }

    public void setTail(Track tail) {
        this.tail = tail;
    }

    public Track getCursor() {
        return cursor;
    }

    public void setCursor(Track cursor) {
        this.cursor = cursor;
    }

    public Station(){}

    private void addTrack(Track newTrack){
        Track nodePtr = head;
        if (nodePtr == null) {
            head = newTrack;
            tail = newTrack;
            tail.setNext(null);
            cursor = newTrack;
            totalTrack++;
        }
        else if (nodePtr.getTrackNumber() == newTrack.getTrackNumber())
            throw new TrackAlreadyExistException();
        else if (nodePtr.getNext() == null && nodePtr.getPrev() == null) {
            if (nodePtr.getTrackNumber() == newTrack.getTrackNumber())
                throw new TrackAlreadyExistException();
            else if (nodePtr.getTrackNumber() < newTrack.getTrackNumber()) {
                head.setNext(newTrack);
                newTrack.setPrev(head);
                tail = newTrack;
                tail.setNext(null);
                cursor = tail;
                totalTrack++;
            } else {
                head.setPrev(newTrack);
                newTrack.setNext(head);
                head = newTrack;
                head.setPrev(null);
                cursor = head;
                totalTrack++;
            }
        }
        else {
            while (!(nodePtr.getNext() == null)) {
                if (nodePtr.getNext().getTrackNumber() == newTrack.getTrackNumber())
                    throw new TrackAlreadyExistException();
                else if (nodePtr.getNext().getTrackNumber() > newTrack.getTrackNumber()) {
                    break;
                } else {
                    nodePtr = nodePtr.getNext();
                }
            }
            newTrack.setNext(nodePtr.getNext());
            if(nodePtr.getNext() != null){
                nodePtr.getNext().setPrev(newTrack);
            }
            nodePtr.setNext(newTrack);
            newTrack.setPrev(nodePtr);
            cursor = newTrack;
            if(cursor.getNext() == null)
                tail = cursor;
            else if(cursor.getPrev() == null)
                head = cursor;
            totalTrack++;
        }
    }

    public Track removeSelectedTrack(){
        totalTrack--;
        if(cursor == null)
            return null;
        if(cursor.getNext() == null && cursor.getPrev() == null) {
            head = null;
            tail = null;
            cursor = null;
            return cursor;
        }
        if (cursor.getTrackNumber() == tail.getTrackNumber()) {
            cursor.getPrev().setNext(null);
            tail = cursor.getPrev();
            cursor = tail;
            return cursor;
        }
        else if (cursor.getTrackNumber() == head.getTrackNumber()) {
            if (cursor.getNext() == null) {
                head = null;
                tail = null;
                cursor = null;
                return cursor;
            } else {
                head = cursor.getNext();
                cursor.getNext().setPrev(null);
                cursor = head;
                return cursor;
            }
        }
        else {
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursor = cursor.getPrev();
            return cursor;
        }
    }

    public void printSelectedTrack(){
        System.out.println(cursor);
    }

    public boolean selectTrack(int trackToSelect){
        boolean findTrack = false;
        Track nodePtr = head;
        if(nodePtr == null)
            return findTrack;
        while(nodePtr != null){
            if(nodePtr.getTrackNumber() == trackToSelect){
                cursor = nodePtr;
                findTrack = true;
                return  findTrack;
            }
            nodePtr = nodePtr.getNext();
        }
        return findTrack;
    }

    public String toString() {
        Track nodePtr = head;
        String stationString = "";
        if (nodePtr == null)
            return "No tracks in this station";
        while (nodePtr != null) {
            stationString += ("Track " + nodePtr.getTrackNumber() + " (" + String.format("%.2f", nodePtr.getUtilizationRate())
                    + " Utilization Rate):\n"
                    +"Selected  Train Number     Train Destination         Arrival Time     Departure Time\n"
                    +"-------------------------------------------------------------------------------------\n");
            stationString += nodePtr.toString() + "\n\n";
            nodePtr = nodePtr.getNext();
        }
        return stationString;
    }

    public static void main(String [] args){
        Scanner stdin = new Scanner(System.in);
        boolean quit = false;
        Station thisStation = new Station();
        String selection;
        do{
            System.out.println("|-----------------------------------------------------------------------------|\n" +
                    "| Train Options                       | Track Options                         |\n" +
                    "|    A. Add new Train                 |    TA. Add Track                      |\n" +
                    "|    N. Select next Train             |    TR. Remove selected Track          |\n" +
                    "|    V. Select previous Train         |    TS. Switch Track                   |\n" +
                    "|    R. Remove selected Train         |   TPS. Print selected Track           |\n" +
                    "|    P. Print selected Train          |   TPA. Print all Tracks               |\n" +
                    "|-----------------------------------------------------------------------------|\n" +
                    "| Station Options                                                             |\n" +
                    "|   SI. Print Station Information                                             |\n" +
                    "|    Q. Quit                                                                  |\n" +
                    "|-----------------------------------------------------------------------------|\n");
            System.out.println("Choose an operation: ");
            selection = stdin.nextLine();
            selection = selection.toUpperCase();
            System.out.println();
            switch (selection) {
                case "A":

                    try {
                        System.out.println("Enter train number: ");
                        int trainNumber = stdin.nextInt();
                        stdin.nextLine();
                        System.out.println("Enter train destination: ");
                        String destination = stdin.nextLine();
                        System.out.println("Enter arrival time: ");
                        int arrivalTime = stdin.nextInt();
                        stdin.nextLine();
                        System.out.println("Enter train transfer time: ");
                        int transferTime = stdin.nextInt();
                        stdin.nextLine();
                        Train newTrain = new Train(trainNumber, destination, arrivalTime, transferTime);
                        if (thisStation.getCursor() == null)
                            System.out.println("Train not added: The is no Track to add the Train to!");
                        else {
                            thisStation.getCursor().addTrain(newTrain);
                            System.out.println("Train No. " + trainNumber + " to " + destination
                            + " added to Track " + thisStation.getCursor().getTrackNumber());
                        }
                    }
                    catch (InvalidArrivalTimeException ex){
                        System.out.println("Train not added: Arrival Time is invalid.");
                    }
                    catch (TrainAlreadyExistException ex){
                        System.out.println("The train entered already exist.");
                    }
                    catch (OverlappingArrivalTimeException ex){
                        System.out.println("A train is already in the station at that time.");
                    }
                    catch (InputMismatchException ex){
                        System.out.println("Incorrect values for train");
                        stdin.nextLine();
                    }
                    break;
                case "N":
                    try{
                        if(thisStation.getCursor() == null || thisStation.getCursor().getCursor() == null)
                            System.out.println("There is no train to switch to");
                        else {
                            thisStation.getCursor().selectNextTrain();
                            System.out.println("Cursor has been moved to next train.");
                        }
                    }
                    catch (InvalidSelectTrainException ex){
                        System.out.println("Selected train not updated: Already at end of Track list");
                    }
                    catch (InputMismatchException ex){
                        System.out.println("Value entered is invalid.");
                        stdin.nextLine();
                    }
                    break;
                case "V":
                    try {
                        if(thisStation.getCursor() == null || thisStation.getCursor().getCursor() == null)
                            System.out.println("There is no train to switch to");
                        else {
                            thisStation.getCursor().selectPrevTrain();
                            System.out.println("Cursor has been moved to previous train");
                        }
                    }
                    catch (InvalidSelectTrainException ex){
                        System.out.println("Selected train not updated: There is no train before this.");
                    }
                    break;
                case "R":
                    if(thisStation.getCursor() == null || thisStation.getCursor().getCursor() == null)
                        System.out.println("There are no trains to remove");
                    else {
                        System.out.println("Train No. " + thisStation.getCursor().getCursor().getTrainNumber() +
                                " to " + thisStation.getCursor().getCursor().getDestination() +
                                " has been removed.");
                        thisStation.getCursor().removeSelectedTrain();
                    }
                    break;
                case "P":
                    if(thisStation.getCursor() == null || thisStation.getCursor().getCursor() == null)
                        System.out.println("There is no train available to be printed");
                    else
                        thisStation.getCursor().printSelectedTrain();
                    break;
                case "TA":
                    int trackNumber = 0;
                    try {
                        System.out.println("Enter track number: ");
                        trackNumber = stdin.nextInt();
                        stdin.nextLine();
                        Track newTrack = new Track(trackNumber);
                        thisStation.addTrack(newTrack);
                        System.out.println("Track " + trackNumber + " added to the Station");
                    }
                    catch (TrackAlreadyExistException ex) {
                        System.out.println("Track not added: Track " + trackNumber + " already exist.");
                    }
                    catch (InputMismatchException ex){
                        System.out.println("Invalid input for track number");
                        stdin.nextLine();
                    }
                    break;
                case "TR":
                    if(thisStation.getCursor() == null)
                        System.out.println("There are no tracks to remove");
                    else{
                    int cursor = thisStation.getCursor().getTrackNumber();
                    thisStation.removeSelectedTrack();
                    System.out.println("Closed Track " + cursor);
                }
                    break;
                case "TS":
                    int switchTrack;
                    System.out.println("Enter the Track number: ");
                    switchTrack = stdin.nextInt();
                    stdin.nextLine();
                    if(!thisStation.selectTrack(switchTrack))
                        System.out.println("Could not switch to Track " + switchTrack +
                                ": Track " + switchTrack + " does not exist");
                    else
                        System.out.println("Switched to Track " + switchTrack);
                    break;
                case "TPS":
                    if(thisStation.getCursor() == null)
                        System.out.println("There are no tracks in this station");
                    else {
                        double utilizationRate = thisStation.getCursor().getUtilizationRate();
                        System.out.println("Track " + thisStation.getCursor().getTrackNumber() + " (" + String.format("%.2f", utilizationRate)
                                + "% Utilization Rate):\n"
                                + "Selected  Train Number     Train Destination         Arrival Time     Departure Time\n"
                                + "-------------------------------------------------------------------------------------");
                        thisStation.printSelectedTrack();
                    }
                    break;
                case "TPA":
                    System.out.println(thisStation);
                    break;
                case "SI":
                    System.out.println("Station (" + thisStation.getTotalTrack()+ " tracks):");
                    Track nodePtr = thisStation.getHead();
                    while(!(nodePtr == null))  {
                        System.out.println(String.format("Track %d : %d trains arriving" +
                                "(%.2f Utilization Rate)", nodePtr.getTrackNumber(), nodePtr.getTotalTrains(),
                                nodePtr.getUtilizationRate()));
                        nodePtr = nodePtr.getNext();
                    }
                    break;
                case "Q":
                    System.out.println("Program terminating normally...");
                    quit = true;
                    break;
                default:
                    System.out.println("Incorrect menu input.");
                    break;
            }
        }
        while(!quit);
        stdin.close();
    }
}

