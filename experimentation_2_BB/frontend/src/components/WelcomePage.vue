<template>
  <div>
    <h1>Welcome</h1>
    <div class="text-box">
      <h3>Experiment Explanation:</h3>
      <p>
        The purpose of the experiment is to find out if there is any difference
        in natural language reading between camelCase and kebab-case.
      </p>
    </div>
    <div class="text-box">
      <h3>What is the difference between these 2 fonts?</h3>
      <ul>
        <li>
          <p>
            <b>camelCase:</b> always starts with lowercase letter and then for
            every word after the first one uses a Capital letter for the first
            letter of the word. For example: (1) doSomething; (2)
            thePenIsOnTheTable.
          </p>
        </li>
        <li>
          <p>
            <b>kebab-case</b>words are separeted by a dash (-). For example,
            (1) do-something. (2) the-pen-is-one-the-table.
          </p>
        </li>
      </ul>
    </div>

    <div class="text-box">
      <h3>The experiment will work in the following way:</h3>
      <p>During this very short experiment, you will be presented with a sentence and for options either in camelCase
        or in kebab-case, you will need to select the sentence that transofrm correctly the header sentence to either
        one of this fonts.</p>
      <h3>What is the purpose?</h3>
      <p>We will collect the time taken and see which font is faster to read and undestand.</p>
    </div>
    <div class="text-box">
      <h3>What is expected?</h3>
      <ol>
        <li>
          <p><b>Focus:</b> This experiment takes literally minutes so please be focused during this time.</p>
        </li>
        <li>
          <p><b>First instinct:</b> Make your selections based on your first instinct.</p>
        </li>
        <li>
          <p><b>Honesty:</b> Do this alone.</p>
        </li>
        <li>
          <p><b>Completness:</b> Please complete the quiz until the end.</p>
        </li>
      </ol>
    </div>

    <div class="text-box">
      <h3>Use of Data</h3>
      <ul>
        <li>
          <p><b>Data Management:</b> By doing the survey you allows us to use data gathered in our experiemnt.</p>
        </li>
        <li>
          <p><b>Confidentiality:</b> Apart from being used in our experiment and on experiments trying to
            replicate this study the data given is confidential, no name or way to refence this data to a
            someone is possible.</p>
        </li>
        <li>
          <p><b>Usage of Data:</b> The data collected will be used only for experiemnt purposes.</p>
        </li>
      </ul>
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