<template>
  <div class="main-container">
    <!-- Header -->
    <header class="header">
      <h1 class="header-title">🧪 Experiment Questions</h1>
    </header>

    <!-- Content Section -->
    <section class="content-section">
      <div class="question-card">
        <p class="current-sentence">
          <b>Sentence:</b> "{{ currentSentence }}"
        </p>
        <p class="instruction-text">Select the right option:</p>
        <ul class="options-list">
          <li v-for="(item, index) in currentItems" :key="index" @click="selectItem(item)"
            :class="{ 'disabled': allTasksCompleted }" class="option-item">
            {{ item.identifier }}
          </li>
        </ul>

        <p v-if="selectedAnswers.length > 0" class="time-text">
          Time taken: <b>{{ getLastTaskTime() }}</b> milliseconds
        </p>

        <h1 v-if="allTasksCompleted" class="completion-message">
          🎉 Congratulations! You completed the experiment. Your results have
          been saved in your Downloads folder.
        </h1>

        <div v-if="allTasksCompleted" class="center-button-container">
          <button class="form-button go-back-button" @click="goBackToWelcome">
            Go Back to Welcome Page
          </button>
        </div>

      </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
      <p>Created with ❤️ for research purposes.</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: "ExperimentInterface",
  data() {
    return {
      tasks: [
        { sentence: "right left", options: ["rightLeft", "rightleft", "Rightleft", "Right Left"], correct: "rightLeft" },
        { sentence: "bubble sort algorithm", options: ["bubbleSortalgorithm", "bubbleSortAlgorithm", "Bubble Sort algorithm", "bubble Sor Talgorithm"], correct: "bubbleSortAlgorithm" },
        { sentence: "tree traversal", options: ["treeTraversal", "TreeTraversal", "treetraversal", "Tree Traversal"], correct: "treeTraversal" },
        { sentence: "binary search", options: ["BinarySearch", "binarySearch", "binarysearch", "Binary search"], correct: "binarySearch" },
        { sentence: "merge sort", options: ["mergeSort", "MergeSort", "mergesort", "Merge Sort"], correct: "mergeSort" },
        { sentence: "hash table", options: ["hashTable", "HashTable", "hash table", "hashtable"], correct: "hashTable" },
        { sentence: "depth first search", options: ["DepthFirstSearch", "depthfirstsearch", "depthFirstSearch", "depth First Search"], correct: "depthFirstSearch" },
        { sentence: "quick sort", options: ["quickSort", "QuickSort", "quicksort", "Quick Sort"], correct: "quickSort" },
        { sentence: "priority queue", options: ["priorityQueue", "PriorityQueue", "priorityqueue", "Priority Queue"], correct: "priorityQueue" },
        { sentence: "linked list", options: ["linkedList", "LinkedList", "linkedlist", "Linked List"], correct: "linkedList" },
        { sentence: "right left", options: ["right-left", "rightleft", "right-left", "right left"], correct: "right-left" },
        { sentence: "bubble sort algorithm", options: ["bubble-sort-algorithm", "bubblesort-algorithm", "bubble-sort-algorithm", "bubble Sort Algorithm"], correct: "bubble-sort-algorithm" },
        { sentence: "tree traversal", options: ["tree-traversal", "treeTraversal", "tree traversal", "tree-traversal"], correct: "tree-traversal" },
        { sentence: "binary search", options: ["binary-search", "binary-search", "binary-search", "Binary search"], correct: "binary-search" },
        { sentence: "merge sort", options: ["merge-sort", "mergeSort", "merge sort", "merge Sort"], correct: "merge-sort" },
        { sentence: "hash table", options: ["hash-table", "hashTable", "hash-table", "hashtable"], correct: "hash-table" },
        { sentence: "depth first search", options: ["depth-first-search", "depthFirstSearch", "depth-first-search", "depth First Search"], correct: "depth-first-search" },
        { sentence: "quick sort", options: ["quick-sort", "quickSort", "quick-sort", "Quick Sort"], correct: "quick-sort" },
        { sentence: "priority queue", options: ["priority-queue", "priorityQueue", "priority-queue", "Priority Queue"], correct: "priority-queue" },
        { sentence: "linked list", options: ["linked-list", "linkedList", "linked-list", "Linked List"], correct: "linked-list" },
      ],
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
      return this.tasks[this.currentTaskIndex].options.map((option) => {
        return {
          identifier: option,
          isCorrect: option === this.tasks[this.currentTaskIndex].correct,
        };
      });
    },
  },
  mounted() {
    this.startTask();
  },
  methods: {
    storeAnswerAndTime() {
      const answersData = this.selectedAnswers.map((answer) => ({
        taskIndex: answer.taskIndex,
        selectedIdentifier: answer.selectedIdentifier,
        isCorrect: answer.isCorrect,
        timeTaken: answer.timeTaken,
      }));

      const csvContent =
        "Task Index,Selected Identifier,Is Correct,Time Taken (ms)\n" +
        answersData
          .map(
            (answer) =>
              `${answer.taskIndex},${answer.selectedIdentifier},${answer.isCorrect},${answer.timeTaken}`
          )
          .join("\n");

      const blob = new Blob([csvContent], { type: "text/csv" });
      const link = document.createElement("a");
      link.href = window.URL.createObjectURL(blob);
      link.download = "experiment_results.csv";
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    startTask() {
      this.startTime = new Date();
    },
    selectItem(selectedItem) {
      const endTime = new Date();
      const timeTaken = endTime - this.startTime;
      this.selectedAnswers.push({
        taskIndex: this.currentTaskIndex,
        selectedIdentifier: selectedItem.identifier,
        isCorrect: selectedItem.isCorrect,
        timeTaken,
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
      console.log("Experiment Completed", this.selectedAnswers);
      this.storeAnswerAndTime();
    },
    goBackToWelcome() {
      this.$emit("back");
    },
  },
};
</script>

<style>
body {
  margin: 0;
  padding: 0;
  font-family: "Roboto", sans-serif;
  background-color: #1e1e2f;
  color: #ffffff;
}

.main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.header {
  width: 100%;
  background-color: #ff6363;
  padding: 20px;
  text-align: center;
  border-radius: 8px;
  margin-bottom: 30px;
}

.header-title {
  color: #ffffff;
  font-size: 28px;
  font-weight: bold;
  margin: 0;
  text-transform: uppercase;
  animation: pulse 2s infinite;
}

.content-section {
  width: 100%;
  display: flex;
  justify-content: center;
}

.question-card {
  background-color: #2e2e3e;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ff6363;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: 600px;
}

.current-sentence {
  font-size: 18px;
  color: #ffffff;
  margin-bottom: 15px;
}

.instruction-text {
  font-size: 16px;
  color: #cfcfcf;
  margin-bottom: 10px;
}

.options-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.option-item {
  background-color: #1e1e2f;
  color: #ffffff;
  border: 1px solid #ff6363;
  padding: 10px;
  border-radius: 6px;
  margin-bottom: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  user-select: none;
}

.option-item:hover {
  background-color: #ff6363;
  color: #ffffff;
}

.option-item.disabled {
  opacity: 0.5;
  pointer-events: none;
}

.time-text {
  font-size: 16px;
  color: #ff6363;
  margin-top: 15px;
  user-select: none;
}

.completion-message {
  font-size: 20px;
  color: #ff6363;
  margin-top: 20px;
  text-align: center;
}

.footer {
  margin-top: 40px;
  padding: 10px;
  text-align: center;
  color: #cfcfcf;
  font-size: 14px;
}

.center-button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.go-back-button {
  background-color: #1e1e2f;
  color: #ffffff;
  border: 1px solid #ff6363;
  padding: 12px 24px;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.go-back-button:hover {
  background-color: #ff6363;
  color: #ffffff;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }

  50% {
    transform: scale(1.05);
  }

  100% {
    transform: scale(1);
  }
}

@media (max-width: 768px) {
  .header-title {
    font-size: 24px;
  }
}
</style>