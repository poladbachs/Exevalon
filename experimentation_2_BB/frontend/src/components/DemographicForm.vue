<template>
  <div class="info-box">
    <h2>Demographic Information</h2>
    <form @submit.prevent="submitForm">
      <label for="age">Age:</label>
      <input type="number" id="age" v-model="age">
      <br>

      <label for="gender">Gender:</label>
      <select id="gender" v-model="gender">
      <option value="female">Female</option>
      <option value="male">Male</option>
      </select>
      <br>

      <label for="education">Educational Background:</label>
      <input type="text" id="education" v-model="education">
      <br>

      <label for="experience">Programming Experience (years):</label>
      <input type="number" id="experience" v-model="experience">
      <br>

      <button type="submit">Submit</button>
      <button type="button" @click="goBack">Go Back</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'DemographicForm',
  data() {
    return {
      age: null,
      gender: '', 
      education: '',
      experience: null
    };
  },
    // Your Vue component making an API request
methods: {
  submitForm() {
    const formData = {
      age: this.age,
      gender: this.gender,
      education: this.education,
      experience: this.experience,
    };

    fetch('http://localhost:3000/api/submit-form', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
    .then(response => response.json())
    .then(data => {
      console.log('Response from server:', data);
      this.$emit('formSubmitted'); // Emit event after successful submission
    })
    .catch(error => {
      console.error('Error communicating with the server:', error);
    });
 
  },
  goBack() {
          // Emit an event to go back
          this.$emit('back');
          }
},

};
</script>

<style>

.info-box{
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
  border: 2px solid  #E4A700;
}


input, select {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: white;
}

button {
width: 100%;
background-color: #FFCB3A;
color: black;
padding: 14px 20px;
margin: 8px 0;
border: none;
border-radius: 4px;
cursor: pointer;
}

button:hover {
background-color: #E4A700;
}

/*
label{
  text-align: left;
  display: block;

}



input, select{
  display: block;
  width:50%;
  border-radius: 8px;
}*/



</style>