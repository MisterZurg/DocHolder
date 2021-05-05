<template>
	<div>
		<img src="https://melmagazine.com/wp-content/uploads/2021/01/66f-1.jpg" alt="People">
		<div class="md-title">{{name}} {{surname}}</div>
		<div class="md-subheading">Email: {{email}}</div>
		<div class="md-subheading">Post: {{post}}</div>
		<a :href="companyUrl"><md-button class="md-raised" ref="">Go to employee's company</md-button></a>
		<div class="clear"></div>
	</div>
</template>

<script>

document.title = "Profile";

export default {
	data() {
		return {
			name: '',
			surname: '',
			email: '',
			post: '',
			haveJob: false,
			companyUrl: ''
		}
	},
	mounted: function() {
		this.getUser();
	} ,
	methods: {
		getUser(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://localhost:8082/user/'+this.$route.query.id,
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
					let status = response.status;

					if(status == 404){
						alert("This user doesn't exist");
						return;
					}
					
					if(status == 200){
						let data = response.data;
						console.log(data);
						this.name = data.name;
						this.surname = data.surname;
						this.email = data.email;
						if(data.role == "ADMINISTRATOR") this.post = "Administrator";
						else if(data.role == "DIRECTOR") this.post = "Director";
						else if(data.role == "ACCOUNTANT") this.post = "Accountant";
						else if(data.role == "REGULAR") this.post = "Employee";
						else if(data.role == "REGULAR_UNEMPLOYED") this.post = "Unemployee";
						else this.post = "Unknown";
						this.companyUrl = "company?id="+data.company_id;

						// if(data.status == "DRAFT") this.status = "В процессе заполнения данных";
						// else if(data.status == "READY_TO_VERIFY") this.status = "На модерации";
						// else if(data.status == "APPROVED") this.status = "Проверенный";
						// else if(data.status == "NOT_APPROVED") this.status = "Не соответствующий требованиям";
						// else if(data.status == "PUBLISHED") this.status = "Проверенный";
						
						// console.log(data);
					}
				});
		},
	}
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

img{
	float: left;
	margin-right: 25px;
	max-width: 200px;
}
div{
	padding-bottom: 15px;
	vertical-align: top;
	
}
.md-raised{
	margin: 0;
}
.clear{
	clear: both;
}

</style>
