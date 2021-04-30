<template>
	<div class="main-reguser">
			<md-card md-with-hover>
				<md-ripple>
					<md-card-header>
						<div class="md-title">Sign In</div>
						<div class="md-subhead">Input your data</div>
					</md-card-header>

					<md-card-content>
						<input type="text" ref="email" placeholder="E-mail">
						<input type="password" ref="password" placeholder="Password">
					</md-card-content>

					<md-card-actions>
						<md-button v-on:click="validation">
							Login
						</md-button>
					</md-card-actions>
				</md-ripple>
			</md-card>
		<bottomHomeBar/>
	</div>
</template>

<script>
document.title = "Авторизация";

import bottomHomeBar from './bottom-home-bar'

export default {
	name: 'reguser',
	components: {bottomHomeBar},
	props: {},
	data() {
		return {
			errorText: ""
		}
	},
	mounted: function () {
		// localStorage.clear();
		console.log(localStorage);
	},
	methods: {
		// Set red border and background of input if validation wasn't success
		setStyles: function (elem, setRight) {
			if (setRight) {
				elem.style.border = "1px solid #333";
				elem.style.backgroundColor = "#fff";
			} else {
				elem.style.border = "1px solid #B00020";
				elem.style.backgroundColor = "#fee";
				this.errorText = "Все поля необходимо заполнить корректно";
			}
			return setRight;
		},
		// data validation by regex
		validation: function (e) {
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
			if (isFullCorrect) {
				// delete error text
				this.errorText = "";

				this.sendForm(
					this.$refs.email.value,
					this.$refs.password.value
				);
			}
		},
		// send json to backend server
		// for now here is just example
		sendForm: function (email, password) {
			var query = this.$http(
				{
					method: 'post',
					url: 'http://localhost:8082/user/auth',
					data: {
						"email": email,
						"password": password
					},
					headers: {
						"Content-type": "application/json; charset=UTF-8"
					}
				})
				.then(function (response) {
					// console.log(response.data);

					// decrypt JWT payload to simple json with UTF-8 support
					var token = response.data.split('.');
					var payloadBase64 = token[1].replace(/-/g, '+').replace(/_/g, '/');
					var payload = JSON.parse(decodeURIComponent(atob(payloadBase64).split('').map(function (c) {
						return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
					}).join('')));

					console.log(payload);

					// save user data in local storage
					localStorage.setItem('token', response.data);
					localStorage.setItem('exp', payload.exp);
					localStorage.setItem('id', payload.id);
					localStorage.setItem('name', payload.name);
					localStorage.setItem('surname', payload.surname);
					localStorage.setItem('email', payload.email);
					localStorage.setItem('phone', payload.phone);
					localStorage.setItem('role', payload.role);
					localStorage.setItem('gender', payload.gender);
					localStorage.setItem('company_id', payload.company_id);
				})
				.catch(function (error) {
					// console.log(error);
					return error;
				});

			query.then((error) => {
				// if error happened
				if (error != undefined) {
					let status = error.response.status;
					if (status == 404) {
						this.errorText = "Пользователя с такими данными не существует";
					} else {
						this.errorText = "Произошла внутренняя ошибка. Попробуйте позже";
					}
					return;
				}

				// if no error
				this.$parent.login();
				this.$router.push('/');
			});
		}
	}
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

.main-reguser {
	margin: 0 auto;
	width: 500px;
	border: 1px solid #333;
}

.title-reguser {
	padding: 15px 0;

	text-align: center;
	font-size: 1.2em;
}

.main-reguser input[type="text"],
.main-reguser input[type="password"] {
	display: block;
	margin: 15px auto;

	font-size: 1.2em;
}

.gender-reguser {
	text-align: center;
	font-size: 1.2em;
}

.gender-reguser input {
	margin-top: 5px;
	margin-left: 15px;
	margin-right: 5px;
}

.gender-reguser label {
	margin-right: 15px;
}

.main-reguser button {
	display: block;
	margin: 25px auto;
}

.error {
	margin-top: 15px;
	margin-bottom: -10px;
	text-align: center;
	color: #B00020;
}


</style>
