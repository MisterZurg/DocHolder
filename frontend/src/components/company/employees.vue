<template>
	<div>
		<div class="md-title">Директорат</div>
		<div class="person-container">
			<div class="person" v-for="employee in employeesDirector" :key="employee.id">
				<md-avatar class="md-primary">
					<img src="https://melmagazine.com/wp-content/uploads/2021/01/66f-1.jpg" alt="People">
				</md-avatar>
				<span class="">{{employee.name}} {{employee.surname}}</span>
				<span class="">({{employee.email}})</span>
			</div>
		</div>
		<div class="md-title">Бухгалтерия</div>
		<div class="person-container">
			<div class="person" v-for="employee in employeesAccountant" :key="employee.id">
				<md-avatar class="md-primary">
					<img src="https://melmagazine.com/wp-content/uploads/2021/01/66f-1.jpg" alt="People">
				</md-avatar>
				<span class="">{{employee.name}} {{employee.surname}}</span>
				<span class="">({{employee.email}})</span>
			</div>
		</div>
		<div class="md-title">Остальные сотрудники</div>
		<div class="person-container">
			<div class="person" v-for="employee in employeesRegular" :key="employee.id">
				<md-avatar class="md-primary">
					<img src="https://melmagazine.com/wp-content/uploads/2021/01/66f-1.jpg" alt="People">
				</md-avatar>
				<span class="">{{employee.name}} {{employee.surname}}</span>
				<span class="">({{employee.email}})</span>
			</div>
		</div>
	</div>
</template>

<script>

export default {
	name: 'company_employees',
	props: {
		// msg: String
	},
	data() {
		return {
			employees: []
		}
	},
	computed: {
		employeesDirector: function(){
			return this.employees.filter(function (employee) {
				return employee.role == "DIRECTOR";
			})
		},
		employeesAccountant: function(){
			return this.employees.filter(function (employee) {
				return employee.role == "ACCOUNTANT";
			})
		},
		employeesRegular: function(){
			return this.employees.filter(function (employee) {
				return employee.role == "REGULAR";
			})
		}
	},
	mounted: function() {
		if(this.$route.query.id != undefined){
			this.getEmployees();
		}
	},
	methods:{
		getEmployees(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://localhost:8082/user/byCompany?id='+this.$route.query.id,
				headers: {
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
				});
		},
	}
}

</script>

<style scoped>

.md-title{
	padding: 25px 0;
}

.person-container{
	display: grid;
	grid-template-columns: repeat( auto-fill, minmax(400px, 1fr) );
	grid-gap: 2vw;
}
.person{
	margin-right: 20px;

	white-space: nowrap;
	overflow: hidden;
}
.person > span{
	margin-left: 10px;
}

</style>
