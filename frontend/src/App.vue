<template>

		<!-- <div id="app"><router-view /> -->
		<!--
		<div class="header">
			<toggler></toggler>
			<div class="header-title">
				<router-link to='/'> DocHolder </router-link><br>
			</div>
			<div class="header-search">
				<input type="text" placeholder="Введите запрос...">
				<div class="my-2">
					<v-btn
						color="primary"
						fab
						small
						dark
					>
						<v-icon>mdi-pencil</v-icon>
					</v-btn>
				</div>
			</div>
			<div class="header-authorization" ref="headerAuthorization">
				<router-link to='/login-user'>
					<v-btn>Войти
						<v-icon>mdi-login</v-icon>
					</v-btn>
				</router-link>
				<router-link to='/reg-user'>
					<v-btn >
						Регистрация
						<v-icon>mdi-account_circle</v-icon>
					</v-btn>
				</router-link>
			</div>
			<div class="header-account" ref="headerAccount">
				<router-link to='/account'>
					{{name}} {{surname}}
				</router-link>
				<v-btn v-on:click="logout"
					color="pink"
					elevation="2"
				><v-icon>mdi-logout</v-icon>
					Выйти</v-btn>
					-->
				<!--<button v-on:click="logout">Выйти</button>-->
		<!--
			</div>
		</div>
		-->
		<!-- <HelloWorld msg="Home page"/> -->
		<!--
		<transition name="fade">
			<router-view />
		</transition>
		-->
		<!-- <bottom-bar/>
	</div>-->
	<div class="page-container">
		<md-app md-waterfall md-mode="overlap" class="waterfall">
			<md-app-toolbar class="md-primary md-large">
				<div class="md-toolbar-row">
					<md-button class="md-icon-button" @click="menuVisible = !menuVisible">
						<md-icon>menu</md-icon>
					</md-button>

					<span class="md-title">DocHolder</span>
				</div>
			</md-app-toolbar>

			<md-app-drawer :md-active.sync="menuVisible">
				<md-toolbar class="md-transparent" md-elevation="0">
					Navigation
				</md-toolbar>

				<md-card md-with-hover ref="profile" class="profile">
					<md-ripple>
	
						<div class="profile-mover" @click="moveToProfile();">
						<md-card-content>
							<!-- <md-avatar class="md-avatar-icon md-primary">{{name[0]}}</md-avatar> -->
							<md-avatar class="md-avatar-icon">
								<img :src="useravatar">
							</md-avatar>
							<span class="md-subheading profile-name">{{name}} {{surname}}</span>
						</md-card-content>
						</div>
	
						<md-card-actions>
							<md-button @click="createCompany();">Create company</md-button>
							<md-button @click="moveToCompany();">Go to company</md-button>
							<md-button @click="logout();">Logout</md-button>
						</md-card-actions>
					</md-ripple>
				</md-card>

				<md-list>
					<md-list-item ref="buttonRegistration">
						<font-awesome-icon icon="user-plus" />
						<router-link to='/reg-user'>
							<span class="md-list-item-text">Sign Up</span>
						</router-link>
					</md-list-item>

					<md-list-item ref="buttonLogin">
						<font-awesome-icon icon="sign-in-alt" />
						<router-link to='/login-user'>
							<span class="md-list-item-text">Sign In</span>
						</router-link>
					</md-list-item>

					<md-list-item>
						<font-awesome-icon icon="info-circle" />
						<router-link to='/about'>
							<span class="md-list-item-text">About</span>
						</router-link>
					</md-list-item>
					<md-list-item>
						<font-awesome-icon icon="home" />
						<router-link to='/'>
							<span class="md-list-item-text">Home</span>
						</router-link>
					</md-list-item>
				</md-list>

			</md-app-drawer>

			<md-app-content>
				<!-- Dynamically update content -->
				<router-view/>
			</md-app-content>
		</md-app>
		<!-- <font-awesome-icon icon="user-secret" /> -->
	</div>
</template>

<script>
export default {
	name: 'App',
	data: () => ({
		menuVisible: false,
		name: "",
		surname: "",
		useravatar: '',
	}),
	// data() {
	//     return{
	//         name: "",
	//         surname: ""
	//     }
	// },
	mounted: function () {
		console.log(localStorage);
		this.login();
	},
	methods: {
		// test methods, need to refactor before upload to prod.
		createCompany: function(){
			if(localStorage == undefined){
				alert("Authorize before");
				return;
			}
			if(localStorage.company_id != undefined && localStorage.company_id != "undefined" && localStorage.company_id != "" && localStorage.company_id != null){
				alert("You already have a job");
				return;
			}
			var query = this.$http(
			{
				method: 'post',
				url: 'http://40.69.86.206:8082/company?token='+localStorage.token,
				data: {
					"name": "Company name",
					"description": "Description",
					"CompanyStatus": "DRAFT"
				},
				headers: {
					// authorization
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 404){
					// console.log("catch error 404");
					return;
				}
					
				if(status == 200){
					let data = response.data;
					this.employees = data;
					// console.log(data);
				}

				if(status == 201){
					console.log(response);
					alert("Company was successfully created.");
					var token = response.data.split('.');
					var payloadBase64 = token[1].replace(/-/g, '+').replace(/_/g, '/');
					var payload = JSON.parse(decodeURIComponent(atob(payloadBase64).split('').map(function (c) {
						return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
					}).join('')));

					// console.log(payload);

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
					
					this.moveToCompany();
				}
			});
		},
		moveToCompany: function(){
			if(localStorage.company_id == "undefined" || localStorage.company_id == undefined){
				alert("You are not employee of any company. Get job or create company!");
				return;
			}
			this.$router.push('/company?id='+localStorage.getItem('company_id')).catch(()=>{});
			this.$router.go();
		},
		moveToProfile: function(){
			if(localStorage.id == "undefined" || localStorage.id == undefined){
				alert("You should authorize before!");
				return;
			}
			this.$router.push('/profile?id='+localStorage.getItem('id')).catch(()=>{});
			this.$router.go();
		},

		// is user authorized
		login: function (){
			if(localStorage.getItem('id') != null){
				// if token had expired we should logout user
				let timeNow = Math.floor(Date.now() / 1000);
				if(timeNow > localStorage.getItem('exp')){
					this.logout();
					return;
				}
				// change header style
				this.name = localStorage.getItem('name');
				this.surname = localStorage.getItem('surname');
				
				this.$refs.profile.$el.style.display = "block";
				this.$refs.buttonRegistration.style.display = "none";
				this.$refs.buttonLogin.style.display = "none";

				this.getUserLogo();
			}
		},
		// logout user if logout button had clicked or JWT expired
		logout: function (){
			localStorage.clear();
			this.$refs.profile.$el.style.display = "none";
			this.$refs.buttonRegistration.style.display = "block";
			this.$refs.buttonLogin.style.display = "block";
			this.$router.push('/').catch(()=>{});
		},

		getUserLogo(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://40.69.86.206:8082/user/'+localStorage.id,
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;
				if(status == 200){
					let data = response.data;
					console.log(data);
					if(data.avatar == null){
						this.useravatar = "https://rgsport.ru/templates/sports/dleimages/noavatar.png";
					}else{
						this.useravatar = "data:image/jpeg;base64," + data.avatar;
					}
				}
			});
		}
	}
}
</script>

<style>
.profile{
	display: none;
}
.profile-mover{
	cursor: pointer;
}
.md-app-toolbar{
	background-color: #1C305C !important;
}
.md-layout-column{
	background-color: #E8EAF6 !important;
}
.waterfall{
	min-height: 100vh;
}
.profile-name{
	padding-left: 15px;
}

.md-avatar img{
	width: auto !important;
	max-width: none !important;
}

</style>