<template>
	<div class="main-reguser">
		<form>
		<div class="container-reguser">
			<div class="title-reguser">Авторизация</div>
			<input type="text" ref="email" placeholder="E-mail">
			<input type="password" ref="password" placeholder="Пароль">
			<button v-on:click="validation">Войти</button>
		</div>
		</form>
	</div>
</template>

<script>

// const axios = require('axios');
document.title = "User Reg";

export default {
	name: 'reguser',
	props: {},
	data() {return{}},
	mounted: function() {},
	methods: {
		// Set red border and background of input if validation wasn't success
		setStyles: function(elem, setRight){
			if(setRight){
				elem.style.border = "1px solid #333";
				elem.style.backgroundColor = "#fff";
			}
			else{
				elem.style.border = "1px solid #B00020";
				elem.style.backgroundColor = "#fee";
			}
			return setRight;
		},
		// data validation by regex
		validation: function(e) {
			e.preventDefault();

			let regex;
			let isCorrect; // is correct current input
			let isFullCorrect = true; // is correct all inputs

			regex = /\S+@\S+\.\S+/;
			isCorrect = regex.test(this.$refs.email.value);
			isFullCorrect &&= isCorrect;
			this.setStyles(this.$refs.email, isCorrect);
			
			regex = /^[a-zA-Z0-9.,]{6,24}$/;
			isCorrect = regex.test(this.$refs.password.value);
			isFullCorrect &&= isCorrect;
			this.setStyles(this.$refs.password, isCorrect);

			// if all inputs are correct we form data for sending
			if(isFullCorrect){
				this.sendForm(
					this.$refs.email.value,
					this.$refs.password.value
					);
			}
		},
		// send json to backend server
		// for now here is just example
		sendForm: function(email, password) {
			console.log(email);
			console.log(password);
			this.$router.push('/');
		}
	}
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

.main-reguser{
	margin: 0 auto;
	width: 500px;
	border: 1px solid #333;
}
.title-reguser{
	padding: 15px 0;

	text-align: center;
	font-size: 1.2em;
}
.main-reguser input[type="text"],
.main-reguser input[type="password"]{
	display: block;
	margin: 15px auto;

	font-size: 1.2em;
}
.gender-reguser{
	text-align: center;
	font-size: 1.2em;
}
.gender-reguser input{
	margin-top: 5px;
	margin-left: 15px;
	margin-right: 5px;
}
.gender-reguser label{
	margin-right: 15px;
}
.main-reguser button{
	display: block;
	margin: 25px auto;
}


</style>
