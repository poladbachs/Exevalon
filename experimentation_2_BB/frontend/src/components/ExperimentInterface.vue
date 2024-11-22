<template>
  <div>
    <h2>Experiment Task</h2>
    <p>Given sentence: "{{ currentSentence }}"</p>
    <p>Select the corresponding identifier:</p>
    <ul>
      <li v-for="(item, index) in currentItems" :key="index" @click="selectItem(item)" :class="{ 'disabled': allTasksCompleted }">
        {{ item.identifier }}
      </li>
    </ul>
    <p v-if="selectedAnswers.length > 0">Time taken for the last task: {{ getLastTaskTime() }} milliseconds</p>
    <h1 v-if="allTasksCompleted">Congratulations! You completed our experiment. Your results are stored in the Downloads folder of your device.</h1>
  </div>
</template>

<script>
export default {
  name: 'ExperimentInterface',
  data() {
    return {
      tasks: [
  { sentence: "navigate to the southern region", options: ["navigateSouthernRegion", "navigateSouthernRealm", "navigateSouthernReserve", "navigate-southern-region"], correct: "navigate-southern-region" },
  { sentence: "access the main entrance door", options: ["access-main-entrance-depot", "access-main-entrance-drape", "accessMainEntranceDoor", "access-main-entrance-dock"], correct: "accessMainEntranceDoor" },
  { sentence: "permanently store the new file", options: ["permanentlyStoreNewFolder", "permanently-store-new-film", "permanentlyStoreNewFile", "permanently-store-new-fabric"], correct: "permanently-store-new-file" },
  { sentence: "irreversibly erase the outdated record", options: ["irreversiblyEraseOutdatedRecord", "irreversibly-erase-outdated-relic", "irreversibly-erase-outdated-rumor", "irreversiblyEraseOutdatedReport"], correct: "irreversiblyEraseOutdatedRecord" },
  { sentence: "immediately upload the high-resolution image", options: ["immediately-upload-high-resolution-illusion", "immediatelyUploadHighResolutionImage", "immediately-upload-high-resolution-item", "immediatelyUploadHighResolutionImpact"], correct: "immediately-upload-high-resolution-image" },
  { sentence: "modify the existing user profile settings", options: ["modify-existing-user-profile-system", "modify-existing-user-profile-scheme", "modifyExistingUserProfileSettings", "modify-existing-user-profile-survey"], correct: "modifyExistingUserProfileSettings" },
  { sentence: "thoroughly read the entire book", options: ["thoroughlyReadEntireBooklet", "thoroughly-read-entire-bulletin", "thoroughlyReadEntireBook", "thoroughly-read-entire-brochure"], correct: "thoroughly-read-entire-book" },
  { sentence: "quickly send a confidential email", options: ["quicklySendConfidentialEmail", "quickly-send-confidential-excerpt", "quickly-send-confidential-essay", "quicklySendConfidentialEpistle"], correct: "quicklySendConfidentialEmail" },
  { sentence: "systematically create a brand new account", options: ["systematically-create-brand-new-arrangement", "systematicallyCreateBrandNewAccount", "systematically-create-brand-new-agenda", "systematicallyCreateBrandNewAppointment"], correct: "systematically-create-brand-new-account" },
  { sentence: "play the classical music loudly", options: ["play-classical-music-loudspeaker", "playClassicalMusicLoudly", "play-classical-music-loft", "playClassicalMusicLounge"], correct: "playClassicalMusicLoudly" },
  { sentence: "accurately check the current account balance", options: ["accuratelyCheckCurrentAccountBill", "accurately-check-current-account-budget", "accuratelyCheckCurrentAccountBalance", "accurately-check-current-account-bankroll"], correct: "accurately-check-current-account-balance" },
  { sentence: "neatly print the important document", options: ["neatly-print-important-declaration", "neatlyPrintImportantDocument", "neatly-print-important-dossier", "neatlyPrintImportantDraft"], correct: "neatlyPrintImportantDocument" },
  { sentence: "efficiently scan the unique QR code", options: ["efficientlyScanUniqueQrCraft", "efficiently-scan-unique-qr-clip", "efficientlyScanUniqueQrCode", "efficiently-scan-unique-qr-cue"], correct: "efficiently-scan-unique-qr-code" },
  { sentence: "regularly update the project status report", options: ["regularlyUpdateProjectStatusReport", "regularly-update-project-status-recap", "regularly-update-project-status-review", "regularlyUpdateProjectStatusRecord"], correct: "regularlyUpdateProjectStatusReport" },
  { sentence: "securely lock the computer screen", options: ["securely-lock-computer-surface", "securelyLockComputerScreen", "securely-lock-computer-slate", "securelyLockComputerShield"], correct: "securely-lock-computer-screen" },
  { sentence: "organize the office workspace efficiently", options: ["organizeOfficeWorkspace", "organize-office-workspace", "officeWorkspaceOrganize", "organizeWorkspaceOffice"], correct: "organize-office-workspace" }],
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