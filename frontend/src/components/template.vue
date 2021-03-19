<template>
	<div class="hello">
	<h3>MAIN PAGE</h3>
	<!-- <p>----( {{ text0 }} )----</p> -->
	</div>
</template>

<script>

document.title = "Home Page"

const axios = require('axios');
const url = 'http://localhost:8081';
// console.log(url);
axios.get(url).then(function(axiosTestResult) {
	console.log(axiosTestResult.data);
	return axiosTestResult.data;
});

export default {
	name: 'HelloWorld',
	props: {
		msg: String
	},
	data() {
		return {
			text0: 'No'
		}
	},
	mounted: function() {
		axios
			.get(url)
			.then(response => {
				this.text0 = response.data;
			})
			.catch(function(e){
				console.log(e.toJSON());
				this.error = e;
		});
	}
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
	margin: 40px 0 0;
}
ul {
	list-style-type: none;
	padding: 0;
}
li {
	display: inline-block;
	margin: 0 10px;
}
a {
	color: #42b983;
}
</style>
