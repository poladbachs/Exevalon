<template>
  <div class="main-container">
    <header class="header">
      <h1 class="header-title">🧪 Experiment Questions</h1>
    </header>
    <section class="content-section">
      <div class="question-card">
        <p class="current-sentence">
          <b>Label:</b> "{{ currentSentence }}"
        </p>
        <p class="instruction-text">Select the correct written format:</p>
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
    <footer class="footer">
      <p>Created with ❤️ for research purposes.</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: "ExperimentInterface",
  props: {
    demographicData: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      tasks: [
        { sentence: "right left", options: ["rightLeft", "rightleft", "Rightleft", "Right Left"], correct: "rightLeft" },
        { sentence: "linked list", options: ["linker-list", "linkes-list", "linkedlist", "linked-list"], correct: "linked-list" },
        { sentence: "bubble sort algorithm", options: ["Bubble Sort algorithm", "bubbleSortAlgorithm", "bubble sort algorithm", "bubble Sor Talgorithm"], correct: "bubbleSortAlgorithm" },
        { sentence: "hash table", options: ["hash-tabel", "hash-tablee", "hash-tabble", "hash-table"], correct: "hash-table" },
        { sentence: "tree traversal", options: ["Tree Traversal", "treeTraversal", "treetraversal", "TreeTraversal"], correct: "treeTraversal" },
        { sentence: "priority queue", options: ["priority-qeue", "priorty-queue", "prioity-queue", "priority-queue"], correct: "priority-queue" },
        { sentence: "binary search", options: ["binarysearch", "BinarySearch", "binarySearch", "Binary search"], correct: "binarySearch" },
        { sentence: "merge sort", options: ["merge-sot", "merg-sort", "merge-sortt", "merge-sort"], correct: "merge-sort" },
        { sentence: "merge sort", options: ["merge Sort", "mergeSort", "MergeSort", "mergesort"], correct: "mergeSort" },
        { sentence: "bubble sort algorithm", options: ["bubble-Sor-Algorithm", "bublle-sort-algorithm", "bubble-sort-algoorithm", "bubble-sort-algorithm"], correct: "bubble-sort-algorithm" },
        { sentence: "depth first search", options: ["depth First Search", "DepthFirstSearch", "depthfirstsearch", "depthFirstSearch"], correct: "depthFirstSearch" },
        { sentence: "tree traversal", options: ["tree-Travesal", "tre-traversal", "tree-traversel", "tree-traversal"], correct: "tree-traversal" },
        { sentence: "hash table", options: ["HashTable", "hashTable", "hashtable", "hash table"], correct: "hashTable" },
        { sentence: "binary search", options: ["binary-search", "Binary-Searchh", "binnary-search", "binary-search"], correct: "binary-search" },
        { sentence: "depth first search", options: ["Depth-First-Search", "depth-First-Serach", "deph-first-search", "depth-first-search"], correct: "depth-first-search" },
        { sentence: "priority queue", options: ["priorityQueue", "Priority Queue", "priorityqueue", "PriorityQueue"], correct: "priorityQueue" },
        { sentence: "quick sort", options: ["Quick-Sorrt", "qick-sort", "quick-sorrt", "quick-sort"], correct: "quick-sort" },
        { sentence: "right left", options: ["right-left", "rigth-left", "righ-left", "right-left"], correct: "right-left" },
        { sentence: "quick sort", options: ["QuickSort", "quicksort", "quickSort", "Quick Sort"], correct: "quickSort" },
        { sentence: "linked list", options: ["Linked List", "linkedlist", "linkedList", "LinkedList"], correct: "linkedList" },
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
      return this.tasks[this.currentTaskIndex].options.map((option) => ({
        identifier: option,
        isCorrect: option === this.tasks[this.currentTaskIndex].correct,
      }));
    },
  },
  mounted() {
    this.startTask();
  },
  methods: {
    storeAnswerAndTime() {
      const userDataHeader = "Parameter,Value\n";
      const userDataTable = [
        `Age,${this.demographicData.age}`,
        `Native Language,${this.demographicData.nativeLanguage}`,
        `Education,${this.demographicData.education}`,
        `Programming Experience,${this.demographicData.experience}`,
        `Country,${this.demographicData.country}`,
        `Input Method,${this.demographicData.inputMethod}`,
      ].join("\n");
      const userDataContent = userDataHeader + userDataTable;

      const experimentResultsHeader = "Task Index,Selected Identifier,Is Correct,Time Taken (ms)\n";
      const experimentResultsTable = this.selectedAnswers
        .map(
          (answer) =>
            `${answer.taskIndex},${answer.selectedIdentifier},${answer.isCorrect},${answer.timeTaken}`
        )
        .join("\n");
      const experimentResultsContent = experimentResultsHeader + experimentResultsTable;

      const userDataBlob = new Blob([userDataContent], { type: "text/csv" });
      const userDataLink = document.createElement("a");
      userDataLink.href = window.URL.createObjectURL(userDataBlob);
      userDataLink.download = "user_data.csv";
      document.body.appendChild(userDataLink);
      userDataLink.click();
      document.body.removeChild(userDataLink);

      const experimentResultsBlob = new Blob([experimentResultsContent], { type: "text/csv" });
      const experimentResultsLink = document.createElement("a");
      experimentResultsLink.href = window.URL.createObjectURL(experimentResultsBlob);
      experimentResultsLink.download = "experiment_results.csv";
      document.body.appendChild(experimentResultsLink);
      experimentResultsLink.click();
      document.body.removeChild(experimentResultsLink);
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
        this.storeAnswerAndTime();
      }
    },
    getLastTaskTime() {
      if (this.selectedAnswers.length > 0) {
        return this.selectedAnswers[this.selectedAnswers.length - 1].timeTaken;
      }
      return null;
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