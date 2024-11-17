package pt.ipp.isep.dei.esoft.project.domain;


import pt.ipp.isep.dei.esoft.project.domain.TreeClasses.Node;
import pt.ipp.isep.dei.esoft.project.domain.TreeClasses.ProductionTree;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

import java.util.*;

public class QualityChecks {

    private final PriorityQueue<Map<Integer, List<ID>>> priorityQueue;


    public QualityChecks() {
        priorityQueue = fillOperationsPriorityQueue();

    }

    public PriorityQueue<Map<Integer, List<ID>>> fillOperationsPriorityQueue() {
        ProductionTree pdt = new ProductionTree();
        pdt.getInformations("prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/boo.csv");
        Map<Integer, List<Node>> heightMap = pdt.getHeightMap();

        PriorityQueue<Map<Integer, List<ID>>> operationsPriorityQueue =
                new PriorityQueue<>((map1, map2) ->
                        Integer.compare(map2.keySet().iterator().next(), map1.keySet().iterator().next()));


        for (Map.Entry<Integer, List<Node>> entry : heightMap.entrySet()) {
            int height = entry.getKey();
            List<Node> nodesAtHeight = entry.getValue();

            Map<Integer, List<ID>> pqNode = new HashMap<>();
            List<ID> operations = new ArrayList<>();

            for (Node node : nodesAtHeight) {
                operations.add(node.getOperationID());
            }

            pqNode.put(height, operations);

            operationsPriorityQueue.add(pqNode);
        }

        return operationsPriorityQueue;
    }

    private Map<Map<Integer, List<ID>>, Boolean> prepareQualityChecks() {
        Map<Map<Integer, List<ID>>, Boolean> checksMap = new LinkedHashMap<>();
        PriorityQueue<Map<Integer, List<ID>>> pq = fillOperationsPriorityQueue();

        while (!pq.isEmpty()) {
            Map<Integer, List<ID>> operations = pq.poll();
            checksMap.put(operations, false);
        }

        return checksMap;
    }


    public void performQualityChecks(String confirmation) {
        Map<Map<Integer, List<ID>>, Boolean> checksMap = prepareQualityChecks();

        if (confirmation.equalsIgnoreCase("y")) {

            for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry : checksMap.entrySet()) {
                Map<Integer, List<ID>> operationsMap = entry.getKey();
                Boolean isChecked = entry.getValue();
                int p = operationsMap.keySet().iterator().next();
                List<ID> operations = operationsMap.get(p);
                System.out.println("=======================================================");
                System.out.println(ANSI_BRIGHT_WHITE + "Performing checks: " + ANSI_RESET);
                if (!isChecked) {
                    entry.setValue(true);
                    System.out.printf("[%d] %s%s were checked%s%n", p, operations, ANSI_BRIGHT_GREEN, ANSI_RESET);
                    System.out.println("=======================================================");
                    System.out.println(ANSI_BRIGHT_WHITE + "Checks to be performed: " + ANSI_RESET);
                }

                for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry2 : checksMap.entrySet()) {
                    operationsMap = entry2.getKey();
                    Boolean isChecked2 = entry2.getValue();
                    int p2 = operationsMap.keySet().iterator().next();
                    List<ID> operations2 = operationsMap.get(p2);

                    if (!isChecked2) {
                        System.out.printf("[%d] %s%s were not check yet%s%n", p2, operations2, ANSI_BRIGHT_YELLOW, ANSI_RESET);
                    }
                }
                System.out.println("=======================================================");

                System.out.println("Operations that have already been checked:");
                for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry3 : checksMap.entrySet()) {
                    operationsMap = entry3.getKey();
                    Boolean isChecked3 = entry3.getValue();
                    int p3 = operationsMap.keySet().iterator().next();
                    List<ID> operations3 = operationsMap.get(p3);

                    if (isChecked3) {
                        System.out.printf("[%d] %s%s were checked%s%n", p3, operations3, ANSI_BRIGHT_GREEN, ANSI_RESET);
                    }
                }



            }

            System.out.printf("%n%sAll operations have been checked!%s%n", ANSI_BRIGHT_GREEN, ANSI_RESET);

        } else {
            System.out.println(ANSI_LIGHT_RED + "Quality checked not performed!" + ANSI_RESET);
        }

    }
}
