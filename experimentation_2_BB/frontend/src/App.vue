<template>
  <div class="page">
    <section class="main-section">
      <div class="right_blank"></div>
      <main class="main-home">
        <WelcomePage v-if="currentStep === 'welcome'" @proceed="currentStep = 'demographic'" />
        <DemographicForm v-if="currentStep === 'demographic'" @back="currentStep = 'welcome'" @formSubmitted="currentStep = 'experiment'" />
        <ExperimentInterface v-if="currentStep === 'experiment'" @formSubmitted="currentStep = 'experiment'" @back="currentStep = 'welcome'"/>
      </main>
      <div class="left_blank"></div>
    </section>
  </div>
</template>

<script>
import WelcomePage from './components/WelcomePage.vue';
import DemographicForm from './components/DemographicForm.vue';
import ExperimentInterface from './components/ExperimentInterface.vue';

export default {
  name: 'App',
  components: {
    WelcomePage,
    DemographicForm,
    ExperimentInterface,
  },
  data() {
    return {
      currentStep: 'welcome',
    };
  },
  
};
</script>

<style>

*{
  font-family: 'Preahvihear', sans-serif;
}

html {
  letter-spacing: 1px;
  
}

html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  width: 100%;
}

body::before {
  content: "";
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: url("assets/camel_meat.jpg");
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;
  opacity: 0.25;
  z-index: -1;
}

.page {
  font-size: 1em;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 1;
}


.main-home{
  flex: 3 3;
  padding: 20px;
}

.main-section
{
  display: flex;
  flex: 1;
}

.right_blank, .left_blank{
  flex: 1 1 0rem;
}

</style>