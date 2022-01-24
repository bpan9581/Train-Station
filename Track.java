public class Track {
    private Train head;
    private Train tail;
    private Train cursor;
    private Track next;
    private Track prev;
    private double utilizationRate;
    private final double minInDay = 1440.0;
    private int trackNumber = 0, totalWaitTime = 0, totalTrains = 0;

    public double getMinInDay() {
        return minInDay;
    }

    public int getTotalWaitTime() {
        return totalWaitTime;
    }

    public void setTotalWaitTime(int totalWaitTime) {
        this.totalWaitTime = totalWaitTime;
    }

    public int getTotalTrains() {
        return totalTrains;
    }

    public void setTotalTrains(int totalTrains) {
        this.totalTrains = totalTrains;
    }

    public Train getHead() {
        return head;
    }

    public void setHead(Train head) {
        this.head = head;
    }

    public Train getTail() {
        return tail;
    }

    public void setTail(Train tail) {
        this.tail = tail;
    }

    public Train getCursor() {
        return cursor;
    }

    public void setCursor(Train cursor) {
        this.cursor = cursor;
    }

    public Track getNext() {
        return next;
    }

    public void setNext(Track next) {
        this.next = next;
    }

    public Track getPrev() {
        return prev;
    }

    public void setPrev(Track prev) {
        this.prev = prev;
    }

    public double getUtilizationRate() {
        return utilizationRate;
    }

    public void setUtilizationRate(double utilizationRate) {
        this.utilizationRate = utilizationRate;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Track(int trackNumber) {
        this.trackNumber = trackNumber;
        this.next = null;
        this.prev = null;
    }

    public Track() {
    }

    public void addTrain(Train newTrain) {
        if(newTrain.getArrivalTime() < 0 || newTrain.getArrivalTime() >= 2400 ||
                newTrain.getArrivalTime() % 100 >= 60)
            throw new  InvalidArrivalTimeException();
        else if(newTrain.getTransferTime() < 0 || newTrain.getTransferTime() >= 1400)
            throw new InvalidTransferTImeException();
        if (cursor == null) {
            head = newTrain;
            tail = newTrain;
            cursor = newTrain;
            totalTrains++;
        } else {
            Train nodePtr = head;
            for(;nodePtr != null; nodePtr = nodePtr.getNext()){
                if(newTrain.equals(nodePtr)){
                    throw new TrainAlreadyExistException();
                }
                else if((newTrain.getArrivalTime() > nodePtr.getArrivalTime() && newTrain.getArrivalTime() <
                        nodePtr.getDepartureTime()) || (newTrain.getDepartureTime() > nodePtr.getArrivalTime() &&
                        newTrain.getDepartureTime() < nodePtr.getDepartureTime())){
                    throw new OverlappingArrivalTimeException();
                }
            }
            nodePtr = head;
            for(;nodePtr != null; nodePtr = nodePtr.getNext()){
                if(newTrain.getArrivalTime() < nodePtr.getArrivalTime()){
                    if(nodePtr.getPrev() == null){
                        newTrain.setNext(head);
                        head.setPrev(newTrain);
                        head = head.getPrev();
                        cursor = head;
                        break;
                    }else{
                        newTrain.setPrev(nodePtr.getPrev());
                        newTrain.setNext(nodePtr);
                        newTrain.getPrev().setNext(newTrain);
                        newTrain.getNext().setPrev(newTrain);
                        cursor = nodePtr.getPrev();
                        break;
                    }
                }
            }
            if (nodePtr == null){
                nodePtr = tail;
                nodePtr.setNext(newTrain);
                newTrain.setPrev(nodePtr);
                tail = newTrain;
                cursor = newTrain;
            }
            totalTrains++;
        }
        totalWaitTime += newTrain.getTransferTime();
        utilizationRate = (totalWaitTime/minInDay) * 100;
    }



    public void printSelectedTrain() {
        if(cursor == null)
            System.out.println("There are no trains in this station.");
        else {
            String arrival = "" + cursor.getArrivalTime();

            while (arrival.length() != 4) {
                arrival = "0" + arrival;
            }

            String departure = "" + cursor.getDepartureTime();
            while (departure.length() != 4) {
                departure = "0" + departure;
            }
            System.out.println(String.format("Selected Train \nTrain Number: %d\n" +
                            "Train Destination : %s \nTrain Arrival Time: %s\n" +
                            "Train Departure Time: %s\n\n", cursor.getTrainNumber(), cursor.getDestination(),
                    arrival, departure));
        }
    }

    public Train removeSelectedTrain() {
        totalWaitTime -= cursor.getTransferTime();
        utilizationRate = (totalWaitTime / minInDay) * 100;
        totalTrains--;
        if(cursor.getNext() == null && cursor.getPrev() == null) {
            head = null;
            tail = null;
            return head;
        }
        if (cursor.equals(tail)) {
            tail = cursor.getPrev();
            cursor.getPrev().setNext(null);
            cursor = tail;
            return cursor;
        } else if (cursor.equals(head)) {
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
        } else {
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursor = cursor.getPrev();
            return cursor;
        }
    }

    public boolean selectNextTrain() {
        if (cursor.equals(tail))
            throw new InvalidSelectTrainException();
        cursor = cursor.getNext();
        return true;
    }

    public boolean selectPrevTrain() {
        if (cursor.equals(head))
            throw new InvalidSelectTrainException();
        cursor = cursor.getPrev();
        return true;
    }

    public String toString() {
        Train nodePtr = head;
        String trackString = "";
        while(nodePtr != null) {
            if (nodePtr.equals(cursor)) {
                trackString = trackString + "*" + nodePtr.toString() + "\n";
            }
            else{
                trackString += " " + nodePtr.toString() + "\n";
            }
            nodePtr = nodePtr.getNext();
        }
        return trackString;
    }
}

