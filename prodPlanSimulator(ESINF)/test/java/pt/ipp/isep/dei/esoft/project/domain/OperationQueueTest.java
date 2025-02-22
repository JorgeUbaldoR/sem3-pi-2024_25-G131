package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.Priority;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;


class OperationQueueTest {

    private ID id1;
    private ID id2;
    private ID id3;
    private ID id4;

    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item5;

    private Operation cutting;
    private Operation painting;
    private Operation welling;

    OperationQueue operationQueue1;
    OperationQueue operationQueue2;
    OperationQueue operationQueue3;
    private final ID ido1 = new ID(1,TypeID.OPERATION);
    private final ID ido2 = new ID(2,TypeID.OPERATION);
    private final ID ido3 = new ID(3,TypeID.OPERATION);

    @BeforeEach
    void setUp() {

        cutting = new Operation("Cutting", ido1,"Cutting raw materials");
        painting = new Operation("Painting", ido2,"Welding metal parts");
        welling = new Operation("Welling", ido3,"Painting the surface");


        id1 = new ID(101, TypeID.ITEM);
        id2 = new ID(102, TypeID.ITEM);
        id3 = new ID(103, TypeID.ITEM);
        id4 = new ID(104, TypeID.ITEM);


        Queue<Operation> operationListItem1 = new LinkedList<>();
        operationListItem1.add(cutting);
        operationListItem1.add(welling);
        operationListItem1.add(painting);

        Queue<Operation> operationListItem2 = new LinkedList<>();
        operationListItem2.add(painting);
        operationListItem2.add(cutting);

        Queue<Operation> operationListItem3 = new LinkedList<>();
        operationListItem3.add(cutting);

        Queue<Operation> operationListItem4 = new LinkedList<>();


        item1 = new Item(id1, Priority.HIGH, operationListItem1);
        item2 = new Item(id2, Priority.HIGH, operationListItem2);
        item3 = new Item(id3, Priority.HIGH, operationListItem3);
        item4 = null;
        item5 = new Item(id4, Priority.HIGH, operationListItem4);


        operationQueue1 = new OperationQueue(cutting,false);
        operationQueue2 = new OperationQueue(painting,false);
        operationQueue3 = new OperationQueue(welling,false);


    }

    @Test
    void getOperation() {
        System.out.println("Test Get Operation");
        assertEquals(cutting, operationQueue1.getOperation());
        assertEquals(painting, operationQueue2.getOperation());
        assertEquals(welling, operationQueue3.getOperation());
    }

    @Test
    void addItemToQueue() {
        System.out.println("Test Add Item To Queue");
        operationQueue1.addItemToQueue(item1);
        assertEquals(item1, operationQueue1.getNextItem());
        operationQueue2.addItemToQueue(item2);
        assertEquals(item2, operationQueue2.getNextItem());

        assertThrows(IllegalArgumentException.class, () -> operationQueue1.addItemToQueue(item2));
        assertThrows(IllegalArgumentException.class, () -> operationQueue1.addItemToQueue(item4));

        operationQueue1.addItemToQueue(item1);
        assertThrows(IllegalArgumentException.class, () -> operationQueue1.addItemToQueue(item1));
        assertThrows(IllegalArgumentException.class, () -> operationQueue1.addItemToQueue(item5));
    }

    @Test
    void getNextItem() {
        System.out.println("Test Get Next Item From Queue");
        operationQueue1.addItemToQueue(item3);
        assertEquals(item3, operationQueue1.getNextItem());
    }

    @Test
    void isEmpty() {
        System.out.println("Test Empty Queue");
        assertTrue(operationQueue1.isEmpty());
        operationQueue2.addItemToQueue(item2);
        assertFalse(operationQueue2.isEmpty());
        try{
            operationQueue3.addItemToQueue(item3);
        }catch (IllegalArgumentException e){
            assertTrue(operationQueue3.isEmpty());
        }
    }

    @Test
    void testEquals() {
        System.out.println("Test Equals");
        assertNotEquals(operationQueue1, operationQueue2);
        assertNotEquals(operationQueue1, operationQueue3);
        assertEquals(operationQueue1, operationQueue1);
        assertNotEquals(operationQueue1, new Object());

        OperationQueue operationQueue3 = operationQueue1;
        assertEquals(operationQueue1, operationQueue3);

        OperationQueue operationQueue4 = new OperationQueue(painting,false);
        assertEquals(operationQueue2, operationQueue4);
    }

    @Test
    void testHashCode() {
        System.out.println("Test Hash Code");
        assertNotEquals(operationQueue1.hashCode(), operationQueue2.hashCode());
        assertNotEquals(operationQueue1.hashCode(), operationQueue3.hashCode());
        assertEquals(operationQueue1.hashCode(), operationQueue1.hashCode());

        OperationQueue operationQueue3 = operationQueue1;
        assertEquals(operationQueue1.hashCode(), operationQueue3.hashCode());

        OperationQueue operationQueue4 = new OperationQueue(painting,false);
        assertEquals(operationQueue2.hashCode(), operationQueue4.hashCode());
    }

    @Test
    void testToString(){
        System.out.println("Test ToString");
        OperationQueue operationQueue = new OperationQueue(cutting,false);
        operationQueue.addItemToQueue(item1);
        String result = operationQueue.toString();
        assertEquals(result, operationQueue.toString());

        OperationQueue operationQueue2 = new OperationQueue(painting,true);
        operationQueue2.addItemToQueue(item2);
        String result2 = operationQueue2.toString();
        assertEquals(result2, operationQueue2.toString());

        OperationQueue operationQueue3 = new OperationQueue(painting,false);
        assertThrows(IllegalArgumentException.class, () -> operationQueue3.addItemToQueue(item5));
        String result3 = operationQueue3.toString();
        assertEquals(result3, operationQueue3.toString());

        OperationQueue operationQueue4 = new OperationQueue(painting,false);
        String result4 = operationQueue4.toString();
        assertEquals(result4, operationQueue4.toString());
    }

    @Test
    void testGetItemList(){
        System.out.println("Test Get Item List");
        OperationQueue operationQueue = new OperationQueue(cutting,false);
        operationQueue.addItemToQueue(item1);
        operationQueue.addItemToQueue(item3);
        Queue<Item> expected = new LinkedList<>();
        expected.add(item1);
        expected.add(item3);
        assertEquals(expected, operationQueue.getItemList());
    }
}