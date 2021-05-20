<template>
	<div>
		<!-- <img src="https://melmagazine.com/wp-content/uploads/2021/01/66f-1.jpg" alt="People"> -->
		<img :src="avatar">
		<md-dialog :md-active.sync="showAvatarUpdateDialog">
			<md-dialog-title>Change avatar</md-dialog-title>
			<md-dialog-content>
				<md-field>
					<label>Image</label>
					<md-file @md-change="setAvatarBinary($event)" accept="image/*"/>
				</md-field>
			</md-dialog-content>
			<md-dialog-actions>
				<md-button class="md-primary"
				@click="cancelAvatar();">Close</md-button>
				<md-button class="md-primary"
				@click="saveAvatar();">Upload</md-button>
			</md-dialog-actions>
		</md-dialog>


		<div class="md-title">{{name}} {{surname}}</div>
		<div class="md-subheading">Email: {{email}}</div>
		<div class="md-subheading">Post: {{post}}</div>
		<a :href="companyUrl"><md-button class="md-raised" ref="">Go to employee's company</md-button></a>
		<div class="clear"></div>
		<md-button class="md-raised button-edit" ref="changeAvatar" @click="showAvatarUpdateDialog = true;">
			Change avatar
		</md-button>

		<md-table v-model="offers" md-sort="name" md-sort-order="asc" md-card ref="offerTable">
			<md-table-toolbar>
				<div class="md-toolbar-section-start">
					<h1 class="md-title">Job Offers</h1>
				</div>
			</md-table-toolbar>

			<md-table-empty-state
				md-label="No job offers found"
				:md-description="`Maybe later someone send you a offer`">
			</md-table-empty-state>

			<md-table-row slot="md-table-row" slot-scope="{ item }">
				<md-table-cell md-label="Company" md-sort-by="name">
					<a :href="'company?id='+item.companyId">{{ item.companyName }}</a>
				</md-table-cell>
				<md-table-cell md-label="Employer" md-sort-by="employer">
					<a :href="'profile?id='+item.employerId">{{ item.employerFullName }}</a>
				</md-table-cell>
				<md-table-cell md-label="For the position" md-sort-by="role">{{ item.role }}</md-table-cell>
				<md-table-cell md-label="Message from employer">{{ item.message }}</md-table-cell>
				<md-table-cell md-label="Status">{{ item.status }}</md-table-cell>
				<md-table-cell md-label="Actions">
					<md-button class="md-raised md-primary" :class="'buttons'+item.status" @click="setJobOfferStatus(item.id, 'ACCEPTED')">
						Accept
					</md-button>
					<md-button class="md-raised md-accent" :class="'buttons'+item.status" @click="setJobOfferStatus(item.id, 'DECLINED')">
						Decline
					</md-button>
				</md-table-cell>
			</md-table-row>
		</md-table>
		<md-table v-model="requests" md-sort="name" md-sort-order="asc" md-card ref="requestTable">
			<md-table-toolbar>
				<div class="md-toolbar-section-start">
					<h1 class="md-title">Document requests</h1>
				</div>
			</md-table-toolbar>

			<md-table-empty-state
				md-label="No document requests found"
				:md-description="`You can request any document of any company`">
			</md-table-empty-state>

			<md-table-row slot="md-table-row" slot-scope="{ item }">
				<md-table-cell md-label="Company" md-sort-by="company">
					<a :href="'company?id='+item.companyId">{{ item.companyName }}</a>
				</md-table-cell>
				<md-table-cell md-label="Document" md-sort-by="document">{{ item.documentName }}</md-table-cell>
				<md-table-cell md-label="Message">{{ item.message }}</md-table-cell>
				<md-table-cell md-label="Status">{{ item.status }}</md-table-cell>
			</md-table-row>
		</md-table>

	</div>
</template>

<script>

export default {
	data() {
		return {
			avatar: '',
			name: '',
			surname: '',
			email: '',
			post: '',
			haveJob: false,
			companyUrl: '',

			offers: [],
			requests: [],

			showAvatarUpdateDialog: false,
		}
	},
	created: function () {
		document.title = "Profile";
	},
	mounted: function() {
		this.getUser();

		if (this.$route.query.id == localStorage.id) {
			this.getJobOffer();
			this.getRequest();
			this.showTablesAndAvatar();
		}
	} ,
	methods: {

		setAvatarBinary(evt) {
			this.avatarBinary = evt[0];
			// console.log(this.avatarBinary.type);
		},
		uploadAvatar() {
			if (this.avatarBinary == undefined) {
				return;
			}
			
			console.log(this.avatarBinary);

			let formData = new FormData();
			// formData.append('id', localStorage.id);
			// formData.append('token', localStorage.token);
			formData.append('file', this.avatarBinary);
			var query = this.$http.put('http://40.69.86.206:8082/user/'+localStorage.id+'/avatar',
			formData,
				{
					headers: {
						'Content-Type': 'multipart/form-data'
					}
				}
			).then(function (response) {return response;})
			.catch(function (error) {return error.response;});
			query.then((response) => {
				let status = response.status;
				if (status == 413) {
					alert("Avatar size is too large. Max size is 5MB.");
				}else if (status == 200) {
					console.log("Avatar was successfully uploaded");
					// this.getUser();
					this.$router.go();
				}else{
					alert("Something went wrong! The avatar hasn't been uploaded");
				}
			});
		},
		cancelAvatar() {
			// console.log("this.logoBinary");
			this.avatarBinary = undefined;
			// console.log(this.avatarBinary);
			this.showAvatarUpdateDialog = false;
		},
		saveAvatar(){
			// console.log(this.avatarBinary);
			// if(this.logoBinary == undefined){
			// 	alert("We can't save empty image");
			// 	return;
			// }
			this.uploadAvatar();
			this.cancelAvatar();
			// this.showAvatarUpdateDialog = false;
		},

		getUser(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://40.69.86.206:8082/user/'+this.$route.query.id,
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
					// console.log(data);
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

					if(data.avatar == null){
						this.avatar = "https://rgsport.ru/templates/sports/dleimages/noavatar.png";
					}else{
						this.avatar = "data:image/jpeg;base64," + data.avatar;
					}

					// if(this.isAvatarChanged){
					// 	localStorage.setItem('avatar', data.avatar);
					// 	this.isAvatarChanged = false;
					// 	this.$router.go();
					// }

				}
			});
		},

		showTablesAndAvatar(){
			this.$refs.changeAvatar.$el.style.display = "block";
			this.$refs.offerTable.$el.style.display = "block";
			this.$refs.requestTable.$el.style.display = "block";
		},

		getJobOffer(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://40.69.86.206:8082/user/'+this.$route.query.id+'/invite',
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 404){
					// alert("This user doesn't exist");
					return;
				}
					
				if(status == 200){
					let data = response.data;
					console.log(data);
					this.offers = data;

					let forNotices = [];
					data.forEach(function(item) {
						forNotices.push({
							"id": item.id,
							"status": item.status,
							"text": "You recieve a job offer from "+item.employer_name+" of the "+item.company_name+" company for the "+item.role+" position. There was message for you is '"+item.message+"'",
							"isAction": "YES"
						});
					});
				}
			});
		},
		setJobOfferStatus(id, status){

			if (status == "ACCEPTED"){
				if (!confirm("Are you sure you want to accept this offer?")) return;
			}else if (status == "DECLINED"){
				if (!confirm("Are you sure you want to decline this offer?")) return;
			}

			var query = this.$http(
			{
				method: 'put',
				url: 'http://40.69.86.206:8082/user/invite/'+id+'/status?status='+status,
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;
				console.log(response);
				if(status == 304){
					alert("This status can't be modified. It may be error. Please contact with administrator");
					return;
				}else if(status == 403){
					alert("You haven't permission for this action");
					return;
				}else if(status == 200){
					alert("Success");
					this.getJobOffer();
					console.log(response.data);


					if (response.data == "") return;


					var token = response.data.split('.');
					var payloadBase64 = token[1].replace(/-/g, '+').replace(/_/g, '/');
					var payload = JSON.parse(decodeURIComponent(atob(payloadBase64).split('').map(function (c) {
						return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
					}).join('')));
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

					this.getUser();
				}else{
					console.log("catch unresolve error:"+status);
				}

			});
		},

		getRequest(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://40.69.86.206:8082/document/requests?user_id='+this.$route.query.id,
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 404){
					// alert("This user doesn't exist");
					return;
				}
					
				if(status == 200){
					let data = response.data;
					console.log(data);
					this.requests = data;

					let forNotices = [];
					data.forEach(function(item) {
						forNotices.push({
							"id": item.id,
							"status": item.status,
							"text": "You requested the "+item.document_name+" from "+item.company_name+" company. Now the status of request is "+item.status,
							"isAction": "NO"
						});
					});

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
/*div{
	padding-bottom: 15px;
	vertical-align: top;
}*/
.md-raised{
	margin: 0;
}
.clear{
	clear: both;
}

.md-table{
	display: none;
	margin-top: 50px;
}
.md-toolbar-section-start h1{
	/*text-align: center;*/
}

.button-edit{
	margin-top: 15px;
	display: none;
	/*visibility: hidden;*/
}

.buttonsACCEPTED,
.buttonsDECLINED,
.actionNO{
	visibility: hidden;
}
</style>
