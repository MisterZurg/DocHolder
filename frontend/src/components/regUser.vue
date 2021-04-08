<template>
	<div class="main-reguser">
		<form>
		<div class="container-reguser">
			<div class="title-reguser">Регистрация</div>
			<input type="text" ref="name" placeholder="Имя">
			<input type="text" ref="surname" placeholder="Фамилия">
			<input type="text" ref="email" placeholder="E-mail">
			<input type="password" ref="password" placeholder="Пароль">
			<input type="password" ref="dubpassword" placeholder="Повторите пароль">
			<input type="text" ref="number" placeholder="Номер">
			<div class="gender-reguser">
				<p>Пол:</p>
				<input type="radio" name="gender" value="MALE" id="male" ref="male" checked>
				<label for="male">Мужчина</label>
				<input type="radio" name="gender" value="FEMALE" id="female">
				<label for="female">Женщина</label>
				<!-- <p>фотография</p> -->
			</div>

			<p class="error">{{errorText}}</p>

			<button v-on:click="validation">Зарегистрироваться</button>
		</div>
		</form>
	</div>
</template>

<script>

// const axios = require('axios');
document.title = "Регистрация";

export default {
	name: 'reguser',
	props: {},
	data() {
		return{
			errorText: ""
		}
	},
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
				this.errorText = "Все поля необходимо заполнить корректно";
			}
			return setRight;
		},
		// data validation by regex
		validation: function(e) {
			e.preventDefault();

			let regex;
			let isCorrect; // is correct current input
			let isFullCorrect = true; // is correct all inputs

			regex = /^[a-zA-ZА-Яа-яёЁ]{1,24}$/;
			isCorrect = regex.test(this.$refs.name.value);
			isFullCorrect &&= isCorrect;
			this.setStyles(this.$refs.name, isCorrect);
			isCorrect = regex.test(this.$refs.surname.value);
			isFullCorrect &&= isCorrect;
			this.setStyles(this.$refs.surname, isCorrect);

			regex = /\S+@\S+\.\S+/;
			isCorrect = regex.test(this.$refs.email.value);
			isFullCorrect &&= isCorrect;
			this.setStyles(this.$refs.email, isCorrect);
			
			regex = /^[a-zA-Z0-9.,]{6,24}$/;
			isCorrect = regex.test(this.$refs.password.value);
			isFullCorrect &&= isCorrect;
			this.setStyles(this.$refs.password, isCorrect);
			isCorrect = this.$refs.password.value==this.$refs.dubpassword.value;
			isFullCorrect &&= isCorrect;
			this.setStyles(this.$refs.dubpassword, isCorrect);
			
			regex = /^[0-9+]{11,12}$/;
			isCorrect = regex.test(this.$refs.number.value);
			isFullCorrect &&= isCorrect;
			this.setStyles(this.$refs.number, isCorrect);

			// if all inputs are correct we form data for sending
			if(isFullCorrect){
				// delete error text
				this.errorText = "";
				// name and surname should begin with uppercase letter
				let name = this.$refs.name.value[0].toUpperCase() + this.$refs.name.value.slice(1);
				let surname = this.$refs.surname.value[0].toUpperCase() + this.$refs.surname.value.slice(1);
				// cut '8' or '+7' from phone number
				let number = "";
				if(this.$refs.number.value[0] =='+') number = this.$refs.number.value.slice(2);
				else if(this.$refs.number.value[0] =='8') number = this.$refs.number.value.slice(1);
				else if(this.$refs.number.value[0] =='7') number = this.$refs.number.value.slice(1);
				// gender
				let gender = "";
				if (this.$refs.male.checked) gender = "MALE";
				else gender = "FEMALE";

				this.sendForm(
					name,
					surname,
					this.$refs.email.value,
					this.$refs.password.value,
					number,
					gender
					);
			}
		},
		// send json to backend server
		sendForm: function(name, surname, email, password, number, gender) {
			var query = this.$http(
				{
					method: 'post',
					url: 'http://localhost:8082/user',
					data: {
						"name": name,
						"surname": surname,
						"email": email,
						"password": password,
						"phone": number,
						"gender": gender
					},
					headers: {
						"Content-type": "application/json; charset=UTF-8"
					}
				})
				.then(function(response) {
					console.log(response.data);
				})
				.catch(function(error) {
					// console.log(error);
					return error;
				});

			query.then((error) => {
					// if error happened
					if(error != undefined){
						let status = error.response.status;
						if(status == 409){
							this.errorText = "Пользователь с такой почтой уже зарегистрирован";
						}else{
							this.errorText = "Произошла внутренняя ошибка. Попробуйте позже";
						}
						return;
					}

					// if no error
					this.$router.push('/login-user');
				});
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
.error{
	margin-top: 15px;
	margin-bottom: -10px;
	text-align: center;
	color: #B00020;
}


</style>
