<template>
    <div>
      <h1>Welcome to the Code Reading Experiment</h1>
      <div class="text-box">
        <h3>Purpose of the Experiment</h3>
        <p>
            Welcome to our research study on coding styles and their impact on readability and comprehension. This experiment is designed to investigate how different naming conventions in source code can affect the speed and accuracy of code understanding. Specifically, we are comparing two popular naming conventions: camelCase and kebab-case.
        </p>
      </div>
      <div class="text-box">
        <h3>What are camelCase and kebab-case?</h3>
        <ul>
            <li>
                <p> 
                    <b>camelCase:</b> In this style, multi-word identifiers are written as a single word, starting with a lowercase letter, and each subsequent word starts with an uppercase letter, <b>e.g., exampleIdentifier.</b>
                </p>
            </li>
            <li>
                <p> 
                    <b>kebab-case</b>Here, words are written in lowercase and separated by hyphens, <b>e.g., example-identifier.</b>
                </p>
            </li>
        </ul>
      </div>
      
      <div class="text-box">
        <h3>How Will the Experiment Work?</h3>
        <p>During this experiment, you will be presented with a series of tasks. Each task involves reading a short phrase and then selecting the corresponding code identifier from a list. These identifiers will be in either camelCase or kebab-case format.</p>
        <h3>What We Will Measure</h3>
        <p>We will measure the time it takes for you to correctly identify the corresponding code identifier. Your accuracy in selecting the correct identifier will also be recorded.</p>
      </div>
      <div class="text-box">
        <h3>What Do We Expect from You?</h3>
        <ol>
            <li><p><b>Focus and attention:</b> We kindly ask for your full attention during the experiment to ensure the accuracy of the results.</p></li>
            <li><p><b>Honesty:</b> Please make your selections based on your first instinct. This will help us gather authentic data.</p></li>
            <li><p><b>Completion:</b> Try to complete all the tasks presented to you.</p></li>
        </ol>
      </div>

      <div class="text-box">
        <h3>Confidentiality and Use of Data</h3>
        <ul>
            <li>
                <p><b>Confidentiality:</b> Your participation in this experiment is completely voluntary, and all information you provide will remain confidential. No personal data will be disclosed.</p>
            </li>
            <li>
                <p><b>Data Usage:</b> The data collected will be used solely for academic purposes, specifically to evaluate the impact of naming conventions in programming on readability and comprehension.</p>
            </li>
        </ul>
      </div>
      <div class="text-box">
        <h2>Thank You for Your Participation!</h2>
        <p>
            Your contribution is invaluable to this research and will help enhance our understanding of best practices in programming. If you have any questions or concerns, please feel free to ask.
        </p>
      </div>
      <button @click="$emit('proceed')">Proceed</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'WelcomePage',
  };
  </script>
  

<style>

.text-box{
    border-radius: 8px;
    background-color: #f2f2f2;
    padding: 20px;
    border: 2px solid  #E4A700;
    margin-top:10px;
    margin-bottom: 10px;
}

.text-box:hover{
    scale: 0.97;
    transition: 1.5s;
}

</style>