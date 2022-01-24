public class Train {
    private Train prev;
    private Train next;
    private int trainNumber = 0;
    private String destination;
    private int arrivalTime = 0;
    private int transferTime = 0;
    private int departureTime = 0;

    public int getDepartureTime() {
        int hours = transferTime / 60;
        int minutes = transferTime % 60;
        int arriveHours = arrivalTime / 100;
        int arriveMinutes = arrivalTime % 100;
        int departMinutes = arriveMinutes + minutes;
        int departHours = arriveHours + hours;
        while (departMinutes > 60){
            departMinutes -= 60;
            departHours++;
        }
        departureTime = (departHours*100) + departMinutes;
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public Train getPrev() {
        return prev;
    }

    public void setPrev(Train prev) {
        this.prev = prev;
    }

    public Train getNext() {
        return next;
    }

    public void setNext(Train next) {
        this.next = next;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(int transferTime) {
        this.transferTime = transferTime;
    }

    public Train(){}

    public Train(int trainNumber, String destination, int arrivalTime, int transferTime){
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.transferTime = transferTime;

    }

    public boolean equals(Object o){
        if(o instanceof Train){
            Train t = (Train) o;
            if (t.getTrainNumber() == this.trainNumber)
                return true;
        }
        return false;
    }

    public String toString(){
        String trainString = "";
        String arrival = "" + arrivalTime;
        while (arrival.length() != 4) {
            arrival = "0" + arrival;
        }

        String departure = "" + getDepartureTime();
        while(departure.length() != 4){
            departure = "0" + departure;
        }
        trainString += String.format("%10d%19s%27s%17s", trainNumber, destination,
                arrival, departure);
        return trainString;
    }
}
