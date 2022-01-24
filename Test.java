import java.util.*;
public class Test {
    public static void main (String [] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Input");
        int input = stdin.nextInt();
        String output = "";
        int divide = 10;
        int count = 0;
        while(input > 0){
            String postfix = "";
            String single = "";
            switch(input % divide){
                case 1: single = "one";
                break;
                case 2: single = "two";
                break;
                case 3: single = "three";
                break;
                case  4: single = "four";
                break;
                case 5: single = "five";
                break;
                case 6: single = "six";
                break;
                case 7: single = "seven";
                break;
                case 8: single = "eight";
                break;
                case 9: single = "nine";
                break;
            }
            switch (count){
                case 1:
                    if(input % divide == 1)
                        postfix = "teen";
                    else {
                        if(single.equals("two"))
                            single = "twen";
                        postfix = "ty";
                    }
                    break;
                case 2: postfix = "hundred";
            }
            count++;
            input /= 10;
            output = single + postfix + " " + output;
        }
        System.out.println(output);
    }
}



/*if(newTrain.getArrivalTime() < 0 || newTrain.getArrivalTime() >= 2400 ||
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
            while(nodePtr != null){
                if(newTrain.equals(nodePtr)){
                    throw new TrainAlreadyExistException();
                }
                if((newTrain.getArrivalTime() > nodePtr.getArrivalTime() && newTrain.getArrivalTime() <
                nodePtr.getDepartureTime()) || (newTrain.getDepartureTime() > nodePtr.getArrivalTime() &&
                        newTrain.getDepartureTime() < nodePtr.getDepartureTime())){
                    throw new OverlappingArrivalTimeException();
                }
                nodePtr = nodePtr.getNext();
            }
            nodePtr = head;
            while(nodePtr != null){
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
                nodePtr = nodePtr.getNext();
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
        utilizationRate = (totalWaitTime/minInDay) * 100;*/

/*Train nodePtr = head;
        if (nodePtr == null) {
            head = newTrain;
            tail = newTrain;
            tail.setNext(null);
            cursor = newTrain;
            totalTrains++;
        }
        else if (nodePtr.getTrainNumber() == newTrain.getTrainNumber())
            throw new TrainAlreadyExistException();
        else if (newTrain.getArrivalTime() < 0 || newTrain.getArrivalTime() >= 2400 ||
                newTrain.getArrivalTime() % 100 >= 60)
            throw new  InvalidArrivalTimeException();
        else if(newTrain.getTransferTime() < 0 || newTrain.getTransferTime() >= 1400)
            throw new InvalidTransferTImeException();
        else if (nodePtr.getNext() == null && nodePtr.getPrev() == null) {
            if (nodePtr.getTrainNumber() == newTrain.getTrainNumber())
                throw new TrainAlreadyExistException();
            else if (nodePtr.getTrainNumber() > newTrain.getTrainNumber()) {
                head.setPrev(newTrain);
                newTrain.setNext(head);
                head = newTrain;
                head.setPrev(null);
                cursor = head;
                totalTrains++;
            }
        }
        else {
            while (!(nodePtr.getNext() == null)) {
                if (nodePtr.getTrainNumber() == newTrain.getTrainNumber())
                    throw new TrainAlreadyExistException();
                else if ((newTrain.getArrivalTime() > nodePtr.getArrivalTime() && newTrain.getArrivalTime() <
                        nodePtr.getDepartureTime()) || (newTrain.getDepartureTime() > nodePtr.getArrivalTime() &&
                        newTrain.getDepartureTime() < nodePtr.getDepartureTime())){
                    throw new OverlappingArrivalTimeException();
                }
                else if (nodePtr.getNext().getArrivalTime() > newTrain.getArrivalTime()) {
                    break;
                } else {
                    nodePtr = nodePtr.getNext();
                }
            }
            newTrain.setNext(nodePtr.getNext());
            if(nodePtr.getNext() != null){
                nodePtr.getNext().setPrev(newTrain);
            }
            nodePtr.setNext(newTrain);
            newTrain.setPrev(nodePtr);
            cursor = newTrain;
            if(cursor.getNext() == null)
                tail = cursor;
            else if(cursor.getPrev() == null)
                head = cursor;
            totalTrains++;
        }*/