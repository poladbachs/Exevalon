<template>
  <div>
    <h2>Questions</h2>
    <p>Sentence: "{{ currentSentence }}"</p>
    <p>Select the right option:</p>
    <ul>
      <li v-for="(item, index) in currentItems" :key="index" @click="selectItem(item)" :class="{ 'disabled': allTasksCompleted }">
        {{ item.identifier }}
      </li>
    </ul>
    <p v-if="selectedAnswers.length > 0">Time taken: {{ getLastTaskTime() }} milliseconds</p>
    <h1 v-if="allTasksCompleted">Congratulations! You completed our experiment. Your results are stored in the Downloads folder of your device.</h1>
  </div>
</template>

<script>
export default {
  name: 'ExperimentInterface',
  data() {
    return {
      tasks: [
  { 
    sentence: "right left", 
    options: ["rightLeft", "rightleft", "Rightleft", "Right Left"], 
    correct: "rightLeft" },
  { 
    sentence: "bubble sort algorithm", 
    options: ["bubbleSortalgorithm", "bubbleSortAlgorithm", "Bubble Sort algorithm", "bubble Sor Talgorithm"], 
    correct: "bubbleSortAlgorithm" 
  },
  {
    sentence: "tree traversal",
    options: ["treeTraversal", "TreeTraversal", "treetraversal", "Tree Traversal"],
    correct: "treeTraversal"
  },
  {
    sentence: "binary search",
    options: ["BinarySearch", "binarySearch", "binarysearch", "Binary search"],
    correct: "binarySearch"
  },
  {
    sentence: "merge sort",
    options: ["mergeSort", "MergeSort", "mergesort", "Merge Sort"],
    correct: "mergeSort"
  },
  {
    sentence: "hash table",
    options: ["hashTable", "HashTable", "hash table", "hashtable"],
    correct: "hashTable"
  },
  {
    sentence: "depth first search",
    options: ["DepthFirstSearch", "depthfirstsearch", "depthFirstSearch", "depth First Search"],
    correct: "depthFirstSearch"
  },
  {
    sentence: "quick sort",
    options: ["quickSort", "QuickSort", "quicksort", "Quick Sort"],
    correct: "quickSort"
  },
  {
    sentence: "priority queue",
    options: ["priorityQueue", "PriorityQueue", "priorityqueue", "Priority Queue"],
    correct: "priorityQueue"
  },
  {
    sentence: "linked list",
    options: ["linkedList", "LinkedList", "linkedlist", "Linked List"],
    correct: "linkedList"
  },
        
  {
    sentence: "right left",
    options: ["right-left", "rightleft", "right-left", "right left"],
    correct: "right-left"
  },
  {
    sentence: "bubble sort algorithm",
    options: ["bubble-sort-algorithm", "bubblesort-algorithm", "bubble-sort-algorithm", "bubble Sort Algorithm"],
    correct: "bubble-sort-algorithm"
  },
  {
    sentence: "tree traversal",
    options: ["tree-traversal", "treeTraversal", "tree traversal", "tree-traversal"],
    correct: "tree-traversal"
  },
  {
    sentence: "binary search",
    options: ["binary-search", "binary-search", "binary-search", "Binary search"],
    correct: "binary-search"
  },
  {
    sentence: "merge sort",
    options: ["merge-sort", "mergeSort", "merge sort", "merge Sort"],
    correct: "merge-sort"
  },
  {
    sentence: "hash table",
    options: ["hash-table", "hashTable", "hash-table", "hashtable"],
    correct: "hash-table"
  },
  {
    sentence: "depth first search",
    options: ["depth-first-search", "depthFirstSearch", "depth-first-search", "depth First Search"],
    correct: "depth-first-search"
  },
  {
    sentence: "quick sort",
    options: ["quick-sort", "quickSort", "quick-sort", "Quick Sort"],
    correct: "quick-sort"
  },
  {
    sentence: "priority queue",
    options: ["priority-queue", "priorityQueue", "priority-queue", "Priority Queue"],
    correct: "priority-queue"
  },
  {
    sentence: "linked list",
    options: ["linked-list", "linkedList", "linked-list", "Linked List"],
    correct: "linked-list"
  }],
      currentTaskIndex: 0,
      startTime: null,
      selectedAnswers: [],
    };
  },
  computed: {
    allTasksCompleted() {
      return this.selectedAnswers.length === this.tasks.length;
    },
    currentSentence() {
      return this.tasks[this.currentTaskIndex].sentence;
    },
    currentItems() {
      return this.tasks[this.currentTaskIndex].options.map(option => {
        return { identifier: option, isCorrect: option === this.tasks[this.currentTaskIndex].correct };
      });
    }
  },
  mounted() {
    this.startTask();
  },
  methods: {
    storeAnswerAndTime() {
      const answersData = this.selectedAnswers.map(answer => ({
        taskIndex: answer.taskIndex,
        selectedIdentifier: answer.selectedIdentifier,
        isCorrect: answer.isCorrect,
        timeTaken: answer.timeTaken,
      }));

      // Convert the data to CSV format
      const csvContent = "Task Index,Selected Identifier,Is Correct,Time Taken (ms)\n" +
        answersData.map(answer =>
          `${answer.taskIndex},${answer.selectedIdentifier},${answer.isCorrect},${answer.timeTaken}`
        ).join("\n");

      // Create a Blob containing the CSV data
      const blob = new Blob([csvContent], { type: "text/csv" });

      // Create a link to download the CSV file
      const link = document.createElement("a");
      link.href = window.URL.createObjectURL(blob);
      link.download = "experiment_results.csv";
      document.body.appendChild(link);

      // Trigger the download
      link.click();

      // Remove the link from the DOM
      document.body.removeChild(link);
    },
    startTask() {
      this.startTime = new Date();
    },
    selectItem(selectedItem) {
      const endTime = new Date();
      const timeTaken = endTime - this.startTime; // time in milliseconds
      this.selectedAnswers.push({
        taskIndex: this.currentTaskIndex,
        selectedIdentifier: selectedItem.identifier,
        isCorrect: selectedItem.isCorrect,
        timeTaken
      });
      this.moveToNextTask();
    },
    moveToNextTask() {
      if (this.currentTaskIndex < this.tasks.length - 1) {
        this.currentTaskIndex++;
        this.startTask();
      } else {
        this.submitResults();
      }
    },
    getLastTaskTime() {
      if (this.selectedAnswers.length > 0) {
        return this.selectedAnswers[this.selectedAnswers.length - 1].timeTaken;
      }
      return null;
    },
    submitResults() {
      // Submit results to backend or handle them as required
      console.log('Experiment Completed', this.selectedAnswers);
      // Redirect to a completion page or show a completion message

      // Display time taken for the last task
      this.getLastTaskTime();

      this.storeAnswerAndTime();

    }
  }
};
</script>

<style>
/* Add styles for your list and items */
ul {
  list-style: none;
  padding: 0;
}
li {
  cursor: pointer;
  margin: 5px 0;
  background-color: #f0f0f0;
  padding: 10px;
  border-radius: 5px;
}
li:hover {
  background-color: #e0e0e0;
}
li.disabled {
  opacity: 0.5; /* Adjust the style to visually indicate that the item is disabled */
  pointer-events: none; /* Disable pointer events on the disabled item */
}
</style>