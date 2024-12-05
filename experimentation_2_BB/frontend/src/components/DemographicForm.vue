<template>
  <div class="main-container">
    <header class="header">
      <h1 class="header-title">📝 Demographic Information</h1>
    </header>
    <section class="form-section">
      <div class="form-card">
        <form @submit.prevent="submitForm">
          <label for="age" class="form-label">Age:</label>
          <input type="number" id="age" v-model="age" class="form-input" />
          <div v-if="errors.age" class="error-message">{{ errors.age }}</div>

          <label for="nativeLanguage" class="form-label">Native Language:</label>
          <input type="text" id="nativeLanguage" v-model="nativeLanguage" class="form-input" />
          <div v-if="errors.nativeLanguage" class="error-message">{{ errors.nativeLanguage }}</div>

          <label for="education" class="form-label">Last Education:</label>
          <select id="education" v-model="education" class="form-select">
            <option value="" disabled>Select your education level</option>
            <option value="High School">High School</option>
            <option value="Bachelor's">Bachelor's</option>
            <option value="Master's">Master's</option>
            <option value="PhD">PhD</option>
          </select>
          <div v-if="errors.education" class="error-message">{{ errors.education }}</div>

          <label for="experience" class="form-label">Years of Programming Experience:</label>
          <input type="number" id="experience" v-model="experience" class="form-input" />
          <div v-if="errors.experience" class="error-message">{{ errors.experience }}</div>

          <label for="country" class="form-label">Country where you lived the most:</label>
          <input type="text" id="country" v-model="country" class="form-input" />
          <div v-if="errors.country" class="error-message">{{ errors.country }}</div>

          <div class="button-container">
            <button type="submit" class="form-button submit-button">Submit</button>
            <button type="button" class="form-button go-back-button" @click="goBack">Go Back</button>
          </div>
        </form>
      </div>
    </section>
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
      nativeLanguage: "",
      education: "",
      experience: null,
      country: "",
      errors: {},
    };
  },
  methods: {
    validateForm() {
      this.errors = {};
      if (!this.age || this.age < 1 || this.age > 120) {
        this.errors.age = "Please enter a valid age between 1 and 120.";
      }
      if (!this.nativeLanguage || !isNaN(this.nativeLanguage)) {
        this.errors.nativeLanguage = "Please enter a valid language (letters only).";
      }
      if (!this.education) {
        this.errors.education = "Please select your education level.";
      }
      if (this.experience === null || this.experience < 0 || isNaN(this.experience)) {
        this.errors.experience = "Please enter valid years of experience (0 or more).";
      }
      if (!this.country || !isNaN(this.country)) {
        this.errors.country = "Please enter a valid country (letters only).";
      }
      return Object.keys(this.errors).length === 0;
    },
    submitForm() {
      if (!this.validateForm()) return;
      const formData = {
        age: this.age,
        nativeLanguage: this.nativeLanguage,
        education: this.education,
        experience: this.experience,
        country: this.country,
      };
      this.$emit("formSubmitted", formData);
    },
    goBack() {
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
  background-color: #1e1e2f;
}

.error-message {
  color: #ff4747;
  font-size: 14px;
  margin-top: -15px;
  margin-bottom: 10px;
}


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

.footer {
  margin-top: 40px;
  padding: 10px;
  text-align: center;
  color: #cfcfcf;
  font-size: 14px;
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