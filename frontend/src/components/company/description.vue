<template>
	<div>
		<div class="logo">
			<img :src="logo">
			<div>
				<md-dialog :md-active.sync="showLogoUpdateDialog">
					<md-dialog-title>Upload logo</md-dialog-title>
					<md-dialog-content>
						<md-field>
							<label>Image</label>
							<md-file @md-change="setLogoBinary($event)" accept="image/*"/>
						</md-field>
					</md-dialog-content>
					<md-dialog-actions>
						<md-button class="md-primary"
						@click="cancelLogo();">Close</md-button>
						<md-button class="md-primary"
						@click="saveLogo();">Save</md-button>
					</md-dialog-actions>
				</md-dialog>

				<md-button class="md-raised md-primary button-edit" ref="changeLogo" @click="showLogoUpdateDialog = true;">
					Upload new logo
				</md-button>
			</div>
		</div>

		<div class="md-title" id="nowCompanyName" ref="nowCompanyName">{{companyName}}<span class="status not-verified">{{status}}</span></div>
		<md-field id="changeCompanyName" ref="changeCompanyName">
			<!-- <label>{{companyName}}</label> -->
			<md-input :value="companyName" ref="changeCompanyNameInput"></md-input>
			<span class="md-helper-text">Company name</span>
		</md-field>

		<div class="md-body-1" id="nowDescription" ref="nowDescription">{{description}}</div>
		<md-field id="changeDescription" ref="changeDescription">
			<label>Description</label>
			<md-textarea :value="description" ref="changeDescriptionInput"></md-textarea>
		</md-field>

		<div class="not-verified">{{adminMessage}}</div>

		<md-button class="md-raised button-edit" v-on:click="editInfo();" ref="changeInfo">Редактировать</md-button>
		<md-button class="md-raised button-edit md-primary" v-on:click="saveInfo();" ref="saveInfo" disabled>Отправить на проверку</md-button>
		<md-button class="md-raised button-edit md-accent" v-on:click="cancelInfo();" ref="cancelInfo" disabled>Отменить</md-button>
		<md-button class="md-raised button-admin md-primary" v-on:click="publishCompany();" ref="publishInfo">Publish</md-button>
		<md-button class="md-raised button-admin md-accent" v-on:click="declineCompany();" ref="declineInfo">Decline</md-button>
		<md-field ref="declineNote" class="button-admin">
			<md-input ref="declineNoteInput"></md-input>
			<span class="md-helper-text">Reason of decline</span>
		</md-field>
	</div>
</template>

<script>

export default {
	name: 'company_documents',
	props: {
		// msg: String
	},
	data() {
		return {
			logo: '',
			companyName: '',
			description: '',
			status: '',
			adminMessage: '',

			is_editing: false,

			showLogoUpdateDialog: false,
		}
	},
	computed: {
		
	},
	mounted: function() {
		if(this.$route.query.id != undefined){
			this.getCompany();
			this.showEditButtons();
			this.showAdminButtons();
		}
	},
	methods:{
		setLogoBinary(evt) {
			console.log("setLogoBinary()");
			this.logoBinary = evt[0];
		},
		uploadLogo() {
			if (this.logoBinary == undefined) {
				return;
			}
			// console.log(this.logoBinary);
			let formData = new FormData();
			formData.append('id', localStorage.company_id);
			formData.append('token', localStorage.token);
			formData.append('file', this.logoBinary);
			var query = this.$http.post('http://localhost:8082/company/logo',
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
				if (status == 409) {
					// console.log("catch error 404");
					return;
				}
				if (status == 200) {
					console.log("Logo was successfully uploaded");
				}else{
					alert("Something went wrong! The logo hasn't been uploaded");
				}
			});
		},
		cancelLogo() {
			// console.log("this.logoBinary");
			this.logoBinary = undefined;
			console.log(this.logoBinary);
			this.showLogoUpdateDialog = false;
		},
		saveLogo(){
			console.log(this.logoBinary);
			// if(this.logoBinary == undefined){
			// 	alert("We can't save empty image");
			// 	return;
			// }
			this.showLogoUpdateDialog = false;
		},


		getCompany(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://localhost:8082/company/read?id='+this.$route.query.id,
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
					let status = response.status;

					if(status == 404){
						alert("This company doesn't exist");
						return;
					}
					
					if(status == 200){
						let data = response.data;
						this.companyName = data.name;
						this.description = data.description;
						if(data.logo == null){
							this.logo = "https://github.com/MisterZurg/DocHolder/blob/main/frontend/src/icons/nologo.png?raw=true";
						}else{
							this.logo = "data:image/jpeg;base64," + data.logo;
						}

						if(data.status == "DRAFT") this.status = "В процессе заполнения данных";
						else if(data.status == "READY_TO_VERIFY") this.status = "На модерации";
						else if(data.status == "APPROVED") this.status = "Проверенный";
						else if(data.status == "NOT_APPROVED") this.status = "Не соответствующий требованиям";
						else if(data.status == "PUBLISHED") this.status = "Проверенный";
						
						// console.log(data);
						if(data.errorId != null) this.adminMessage = data.errorId.message;
						else this.adminMessage = "";
					}
				});
		},

		showAdminButtons(){
			if(localStorage.role == "ADMINISTRATOR"){
				this.$refs.publishInfo.$el.style.display = "inline-block";
				this.$refs.declineInfo.$el.style.display = "inline-block";
				this.$refs.declineNote.$el.style.display = "inline-block";
			}
		},
		publishCompany(){
			if (!confirm("Are you sure you want to approve this company?")) return;
			var query = this.$http(
			{
				method: 'put',
				url: 'http://localhost:8082/company/updateStatus?id='+this.$route.query.id+'&message=null&status=PUBLISHED&token='+localStorage.token,
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
					alert("Company was approved");
					this.getCompany();
				}
			});
		},
		declineCompany(){
			if (!confirm("Are you sure you want to decline this company?")) return;
			var query = this.$http(
			{
				method: 'put',
				url: 'http://localhost:8082/company/updateStatus?id='+this.$route.query.id+'&message='+this.$refs.declineNoteInput.$el.value+'&status=NOT_APPROVED&token='+localStorage.token,
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
					alert("Company was declined");
					this.getCompany();
				}
			});
		},
		showEditButtons(){
			if(localStorage.role == "DIRECTOR" && localStorage.company_id == this.$route.query.id){
				console.log("we show edit button");
				// this.$refs.changeInfo.$el.style.display = "inline-block";
				// this.$refs.saveInfo.$el.style.display = "inline-block";
				// this.$refs.cancelInfo.$el.style.display = "inline-block";
				this.$refs.changeInfo.$el.style.visibility = "visible";
				this.$refs.saveInfo.$el.style.visibility = "visible";
				this.$refs.cancelInfo.$el.style.visibility = "visible";
			}
		},
		editInfo(){
			this.beginEditing();
		},
		saveInfo(){

			this.uploadLogo();

			var query = this.$http(
			{
				method: 'put',
				url: 'http://localhost:8082/company?token='+localStorage.token,
				data: {
					"id": localStorage.company_id,
					"name": this.$refs.changeCompanyNameInput.$el.value,
					"description": this.$refs.changeDescriptionInput.$el.value
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
					alert("The company information hasn't been changed. Maybe your data is'n valid");
					return;
				}else if(status == 200){
					this.getCompany();
					alert("The company information has been changed successfully");
				}else{
					console.log("catch unresolve error:"+status);
					alert("The company information hasn't been changed. Maybe your data is'n valid");
				}

			});

			this.endEditing();
		},
		cancelInfo(){
			this.$refs.changeCompanyNameInput.$el.value = this.companyName;
			this.$refs.changeDescriptionInput.$el.value = this.description;
			this.logoBinary = undefined;

			this.endEditing();
		},
		beginEditing(){
			this.$refs.changeCompanyName.$el.style.display = "block";
			this.$refs.changeDescription.$el.style.display = "block";
			this.$refs.nowCompanyName.style.display = "none";
			this.$refs.nowDescription.style.display = "none";

			this.$refs.changeLogo.$el.style.visibility = "visible";
			this.$refs.changeLogo.$el.style.visibility = "block";
			this.$refs.changeInfo.$el.disabled = true;
			this.$refs.saveInfo.$el.disabled = false;
			this.$refs.cancelInfo.$el.disabled = false;
		},
		endEditing(){
			this.$refs.changeCompanyName.$el.style.display = "none";
			this.$refs.changeDescription.$el.style.display = "none";
			this.$refs.nowCompanyName.style.display = "block";
			this.$refs.nowDescription.style.display = "block";

			this.$refs.changeLogo.$el.style.display = "none";
			this.$refs.changeInfo.$el.disabled = false;
			this.$refs.saveInfo.$el.disabled = true;
			this.$refs.cancelInfo.$el.disabled = true;
		}
	}
}

</script>

<style scoped="true">

.md-title{
	padding: 25px 0;
}

.verified{
	color: #aaf255;
}
.not-verified{
	color: #E57373;
}
.in-progress{
	color: #FFF176;
}

#changeCompanyName,
#changeDescription{
	display: none;
}

.md-textarea{
	width: 500px;
	resize: none !important;
}
.logo img{
	margin-top: 25px;
	max-height: 150px;
}
.md-body-1{
	width: 50%;
}
.status{
	padding-left: 5px;
	font-size: 0.4em;
	vertical-align: super;
}
#nowDescription{
	margin-bottom: 25px;
}
.button-edit{
	/*display: none;*/
	visibility: hidden;
}
.button-admin{
	/*display: none;*/
	visibility: hidden;
}


</style>
