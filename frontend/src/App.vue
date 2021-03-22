<template>
	<div id="app">
		<div class="header">
			<div class="header-title">
				<router-link to='/'> DocHolder </router-link><br>
			</div>
			<div class="header-search">
				<input type="text" placeholder="Искать">
			</div>
			<div class="header-login">
				<router-link to='/login-user'>
					<button>Войти</button>
				</router-link>
				<router-link to='/reg-user'>
					<button>Регистрация</button>
				</router-link>
			</div>
		</div>
	<!-- <HelloWorld msg="Home page"/> -->
	<transition name="fade">
		<router-view /> 
	</transition>
	</div>
</template>

<script>

export default {
	name: 'App',
}
</script>

<style>
	*{
		margin: 0;
		padding: 0;

		font-family: Avenir, sans-serif, Helvetica, Arial;
	}
	a{
		/*color: black;*/
		color: #2c3e50;
		text-decoration: none;
	}
	input[type="text"],
	input[type="password"],
	input[type="text"]:focus,
	input[type="password"]:focus {
		/*width: 300px;*/
		height: 30px;
		padding-left: 15px;
		border: 1px solid #333;
		outline: none;
		caret-color: #2c3e50;
		box-sizing: border-box;

		/*font-size: 1.4em;*/
		color: #2c3e50;
	}
	input[type="text"]::placeholder,
	input[type="password"]::placeholder {
		color: #aaa;
	}
	button{
		padding: 8px;
		background-color: #fff;
		border: 1px solid #333;
		cursor: pointer;
		box-sizing: border-box;
		transition: background 0.3s ease-in-out, color 0.3s ease-in-out;

		font-size: 1.2em;
	}
	button:hover{
		background-color: #2c3e50;
		color: #fff;
	}

	.header{
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin-bottom: 50px;
		height: 60px;

		border-bottom: 1px solid #333;
	}
	.header-title{
		display: flex;
		align-items: center;
		padding-left: 50px;
		width: 300px;
		/*border: 1px solid red;*/
		box-sizing: border-box;

		font-size: 1.4em;
	}
	.header-title a:hover{
		text-decoration: underline;
	}
	.header-search{
		display: flex;
		align-items: center;
		/*border: 1px solid blue;*/
	}
	.header-search input,
	.header-search input:focus {
		width: 300px;
		height: 40px;
/*		padding-left: 15px;
		border: 1px solid #333;
		outline: none;
		caret-color: #2c3e50;
		box-sizing: border-box;*/

		font-size: 1.4em;
		/*color: #2c3e50;*/
	}
/*	.header-search input::placeholder {
		color: #aaa;
	}*/
	.header-login{
		display: flex;
		align-items: center;
		width: 300px;
		/*border: 1px solid green;*/
	}
	.header-login a{
		margin-right: 25px;
		/*border: 1px solid red;*/
	}
	/*.header-login button{
		padding: 8px;
		background-color: #fff;
		border: 1px solid #333;
		cursor: pointer;
		box-sizing: border-box;
		transition: background 0.3s ease-in-out, color 0.3s ease-in-out;

		font-size: 1.2em;
	}
	.header-login button:hover{
		background-color: #2c3e50;
		color: #fff;
	}*/
/*#app {
	font-family: Avenir, sans-serif, Helvetica, Arial;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
	margin-top: 60px;
}*/
.fade-enter-active,
.fade-leave-active {
	transition-property: opacity;
	transition-duration: 0.25s;
}

.fade-enter-active {
	transition-delay: 0.25s;
}

.fade-enter,
.fade-leave-active {
	opacity: 0;
}
</style>
