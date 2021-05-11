<template>
	<div>
		<md-button class="md-primary md-raised button-edit" ref="newEmployeeButton" @click="showJobOffer = true">Hire a new employee </md-button>

		<div class="md-title">Directors</div>
		<div class="person-container">
			<div class="person" v-for="employee in employeesDirector" :key="employee.id">
				<md-avatar class="md-avatar-icon">
					<img :src="employee.avatar">
				</md-avatar>
				<span class="">
					<a :href="'profile?id='+employee.id">
						{{employee.name}} {{employee.surname}}
					</a>
				</span>
			</div>
		</div>
		<div class="md-title">Accountants</div>
		<div class="person-container">
			<div class="person" v-for="employee in employeesAccountant" :key="employee.id">
				<md-avatar class="md-avatar-icon">
					<img :src="employee.avatar">
				</md-avatar>
				<span class="">
					<a :href="'profile?id='+employee.id">
						{{employee.name}} {{employee.surname}}
					</a>
				</span>
			</div>
		</div>
		<div class="md-title">Other employees</div>
		<div class="person-container">
			<div class="person" v-for="employee in employeesRegular" :key="employee.id">
				<md-avatar class="md-avatar-icon">
					<img :src="employee.avatar">
				</md-avatar>
				<span class="">
					<a :href="'profile?id='+employee.id">
						{{employee.name}} {{employee.surname}}
					</a>
				</span>
			</div>
		</div>

		<md-dialog :md-active.sync="showJobOffer">
			<md-dialog-title>Hiring</md-dialog-title>
			<md-dialog-content>
				<md-field>
					<label>E-mail</label>
					<md-input v-model="offerEmail"></md-input>
				</md-field>
				<md-field>
					<label for="offerRole">Position</label>
					<md-select v-model="offerRole" name="offerRole">
						<md-option value="DIRECTOR">Director</md-option>
						<md-option value="ACCOUNTANT">Accountant</md-option>
						<md-option value="REGULAR">Regular employee</md-option>
					</md-select>
				</md-field>
				<md-field>
					<label>Message</label>
					<md-textarea v-model="offerMessage"></md-textarea>
				</md-field>
			</md-dialog-content>
			<md-dialog-actions>
				<md-button class="md-primary" @click="closeOfferBox();">Close</md-button>
				<md-button class="md-primary" @click="sendOfferBox();">Send</md-button>
			</md-dialog-actions>
		</md-dialog>
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
			employees: [],

			showJobOffer: false,
			offerEmail: null,
			offerMessage: null,
			offerRole: 'REGULAR',
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
			this.showEditButtons();
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
					
					if(status == 200){
						let data = response.data;			
						data.forEach(function(item) {
							if(item.avatar == null){
								item.avatar = "https://rgsport.ru/templates/sports/dleimages/noavatar.png";
							}else{
								item.avatar = "data:image/jpeg;base64," + item.avatar;
							}
						});
						this.employees = data;
					}

			});
		},

		closeOfferBox(){
			this.showJobOffer = false;
			this.offerEmail = null;
			this.offerMessage = null;
			this.offerRole = null;
		},

		sendOfferBox(){

			console.log(this.offerEmail);
			console.log(this.offerMessage);

			var query = this.$http(
			{
				method: 'post',
				url: 'http://localhost:8082/company/invite?email='+this.offerEmail+'&token='+localStorage.token,
				data: {
					"company_id": localStorage.company_id,
					"employer_id": localStorage.id,
					"role": this.offerRole,
					"message": this.offerMessage
				},
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 304){
					alert("User with this email doesn't exist");
					return;
				}else if(status == 403){
					alert("You haven't permission for this action");
					return;
				}else if(status == 200){
					alert("Success");
					console.log(response.data);
				}else{
					console.log("catch unresolve error:"+status);
				}
			});
			
			this.closeOfferBox();
		},

		showEditButtons(){
			if(localStorage.role == "DIRECTOR" && localStorage.company_id == this.$route.query.id){
				console.log("we show edit button");
				this.$refs.newEmployeeButton.$el.style.display = "block";
				// this.$refs.newEmployeeButton.$el.style.visibility = "visible";
			}
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

.button-edit{
	display: none;
	/*visibility: hidden;*/
}

a {
  color: inherit !important; /* blue colors for links too */
  text-decoration: inherit !important; /* no underline */

}

/*.md-avatar img{
	width: auto !important;
	max-width: none !important;
}*/
</style>
