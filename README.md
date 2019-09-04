# Programmers.Level3_Java_FarthestNode

## 프로그래머스 그래프 > 가장 먼 노드

### 1. 문제설명
input으로 Node의 개수 n, Node간 edge들에 대한 정보 int[][] edge가 들어온다. edge는 양방향이며 길이는 1리다. 1번 노드로부터 가장 멀리 떨어진 노드가 몇 개인지를 return하는 문제. 

### 2. 풀이
문제: https://www.welcomekakao.com/learn/courses/30/lessons/49189

1번 노드로 부터 시작하여 BFS 알고리즘을 통해 해결한 문제. BFS를 사용하였으므로 Queue를 이용하였다. 시작 노드로 부터 인접한 노드로 퍼저나갈 때, 아직 지나가지 않은 노드면 count를 +1 해주며 1번 노드로 부터의 거리를 계산 하였다.
```java
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
```

### 3. 어려웠던점
처음 시도는 DP중 Floyd 알고리즘을 조금 변형하여 시작 노드가 1인 경우로만 탐색하는 코드를 작성하였다. 
```java
    void floyd(int n, int[][] weight) { // only start from node 1
    	for (int k = 0; k < n; k++)
			for (int j = 0; j < n; j++)
				weight[0][j] = Math.min(weight[0][j], weight[0][k]+weight[k][j]);
    }
```
하지만 그 마저도 시간초과가 떳는데 BFS알고리즘으로 시작점으로 부터 뻗어나가 방문하지 않은 인접한 노드에 대해서만 탐색을 진행하도록 한것이 DP에 비해 생길수있는 불필요한 계산들을 많이 줄여주었던 것이 큰것 같다.
