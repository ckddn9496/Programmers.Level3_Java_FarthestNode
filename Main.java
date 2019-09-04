import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		int n = 6;
		int[][] vertex = {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}};
		System.out.println(new Solution().solution(n, vertex));
	}
}

class Solution {
    public int solution(int n, int[][] edge) {
        Node[] nodes = new Node[n];
        
        // init nodes
        for (int i = 0; i < n; i++) {
        	nodes[i] = new Node(i);
        }
        
        // set adjacentNode using edges
        for (int i = 0; i < edge.length; i++) {
        	nodes[edge[i][0]-1].addAdjacentNode(nodes[edge[i][1]-1]);
        	nodes[edge[i][1]-1].addAdjacentNode(nodes[edge[i][0]-1]);
        }
        
        boolean[] visit = new boolean[n];
        Queue<Node> q = new LinkedList<>();
        nodes[0].count = 0;
        visit[0] = true;
        q.add(nodes[0]);
        // start BFS
        while (!q.isEmpty()) {
        	Node node = q.poll();
        	for (Node adjacent : node.adjacentNodes) {
        		if (!visit[adjacent.vertex]) {
        			visit[adjacent.vertex] = true;
        			adjacent.count = node.count+1;
        			q.add(adjacent);
        		}
        	}
        }
        // find max and count nodes having max value
        int max = Arrays.stream(nodes).mapToInt(node -> node.count).max().getAsInt();
        return (int) Arrays.stream(nodes).filter(node -> node.count == max).count();
    }
    
    class Node {
    	private int vertex;
    	private int count = Integer.MAX_VALUE;
    	private List<Node> adjacentNodes = new LinkedList<>();
    	public Node(int vertex) {
    		this.vertex = vertex;
    	}
    	public void addAdjacentNode(Node node) {
    		this.adjacentNodes.add(node);
    	}
    }
}


/* 시간초과로 실패 Floyd알고리즘을 이용*/
class Solution_TimeOver {
	static int INF = 20000;
    public int solution(int n, int[][] edge) {
        int[][] weight = new int[n][n];
        setWeight(weight, edge);
        floyd(n, weight);
        
        int max = 0;
        int maxCount = 0;
        for (int l : weight[0]) {
        	if (max < l) {
        		max = l;
        		maxCount = 0;
        	}
        	if (l == max) maxCount++;
        }

        return maxCount;
    }
    
    void setWeight(int[][] weight, int[][] edges) {
    	for (int i = 0; i < weight.length; i++)
    		for (int j = 0; j < weight.length; j++) {
    			if (i == j) weight[i][j] = 0;
    			else weight[i][j] = INF;
    		}
    	for (int[] edge : edges) {
    		weight[edge[0]-1][edge[1]-1] = 1;
    		weight[edge[1]-1][edge[0]-1] = 1;
    	}
    }
    
    void floyd(int n, int[][] weight) { // only start from node 1
    	for (int k = 0; k < n; k++)
			for (int j = 0; j < n; j++)
				weight[0][j] = Math.min(weight[0][j], weight[0][k]+weight[k][j]);
    }

    
    void printMap(int[][] map) {
    	for (int[] row : map) {
    		for (int attr : row)
    			if (attr == INF)
    				System.out.print("INF ");
    			else
    				System.out.print(String.format("%3d ", attr));
    		System.out.println();
    	}
    	System.out.println();
    }
}