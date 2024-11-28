<template>
  <div class="main-container">
    <!-- Header -->
    <header class="header">
      <h1 class="header-title">📝 Demographic Information</h1>
    </header>

    <!-- Form Section -->
    <section class="form-section">
      <div class="form-card">
        <form @submit.prevent="submitForm">
          <!-- Age -->
          <label for="age" class="form-label">Age:</label>
          <input type="number" id="age" v-model="age" class="form-input" />

          <!-- Gender -->
          <label for="gender" class="form-label">Gender:</label>
          <select id="gender" v-model="gender" class="form-select">
            <option value="" disabled selected>Select your gender</option>
            <option value="female">Female</option>
            <option value="male">Male</option>
          </select>

          <!-- Last Education -->
          <label for="education" class="form-label">Last Education:</label>
          <input
            type="text"
            id="education"
            v-model="education"
            class="form-input"
          />

          <!-- Programming Experience -->
          <label for="experience" class="form-label">
            Years of Programming Experience:
          </label>
          <input
            type="number"
            id="experience"
            v-model="experience"
            class="form-input"
          />

          <!-- Country -->
          <label for="country" class="form-label">
            Country where you lived the most:
          </label>
          <input
            type="text"
            id="country"
            v-model="country"
            class="form-input"
          />

          <!-- Buttons -->
          <div class="button-container">
            <button type="submit" class="form-button submit-button">
              Submit
            </button>
            <button
              type="button"
              class="form-button go-back-button"
              @click="goBack"
            >
              Go Back
            </button>
          </div>
        </form>
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
  name: "DemographicForm",
  data() {
    return {
      age: null,
      gender: "",
      education: "",
      experience: null,
      country: "",
    };
  },
  methods: {
    submitForm() {
      const formData = {
        age: this.age,
        gender: this.gender,
        education: this.education,
        experience: this.experience,
        country: this.country,
      };

      fetch("http://localhost:3000/api/submit-form", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Response from server:", data);
          this.$emit("formSubmitted"); // Emit event after successful submission
        })
        .catch((error) => {
          console.error("Error communicating with the server:", error);
        });
    },
    goBack() {
      // Emit an event to go back
      this.$emit("back");
    },
  },
};
</script>

<style>
/* General Styles */
body {
  margin: 0;
  padding: 0;
  font-family: "Roboto", sans-serif;
  background-color: #1e1e2f;
  color: #ffffff;
}

/* Main Container */
.main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

/* Header */
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

/* Form Section */
.form-section {
  width: 100%;
  display: flex;
  justify-content: center;
}

.form-card {
  background-color: #2e2e3e;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ff6363;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: 600px;
}

.form-label {
  display: block;
  margin-bottom: 8px;
  font-size: 16px;
  color: #ff6363;
}

.form-input,
.form-select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ff6363;
  border-radius: 6px;
  background-color: #1e1e2f;
  color: #ffffff;
  margin-bottom: 20px;
  font-size: 16px;
  box-sizing: border-box;
}

.form-input:focus,
.form-select:focus {
  outline: none;
  border-color: #ff4747;
  box-shadow: 0 0 4px rgba(255, 99, 99, 0.8);
}

/* Buttons */
.button-container {
  display: flex;
  gap: 10px;
}

.form-button {
  flex: 1;
  padding: 12px;
  border-radius: 6px;
  border: none;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.submit-button {
  background-color: #ff6363;
  color: #ffffff;
}

.submit-button:hover {
  background-color: #ff4747;
}

.go-back-button {
  background-color: #1e1e2f;
  color: #ffffff;
  border: 1px solid #ff6363;
}

.go-back-button:hover {
  background-color: #ff6363;
  color: #ffffff;
}

/* Footer */
.footer {
  margin-top: 40px;
  padding: 10px;
  text-align: center;
  color: #cfcfcf;
  font-size: 14px;
}

/* Animations */
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