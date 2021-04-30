<template>
	<md-tabs>
		<md-tab id="tab-home" md-label="Описание">
				<!-- <div class="status">
					<div class="md-subheading">Статус:</div>
					<div class="md-subheading" id="status">Проверенный</div>
					<md-button class="md-raised">Редактировать</md-button>
				</div> -->
				<div class="logo"><img src="https://www.netcracker.com/assets/templates/v.3.0/img/logo.png" alt=""></div>
				
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
				
				<md-button class="md-raised button-edit" v-on:click="editInfo();" ref="changeInfo">Редактировать</md-button>
				<md-button class="md-raised button-edit md-primary" v-on:click="saveInfo();" ref="saveInfo" disabled>Сохранить</md-button>
				<md-button class="md-raised button-edit md-accent" v-on:click="cancelInfo();" ref="cancelInfo" disabled>Отменить</md-button>
				<!-- <md-button class="md-raised">Отправить на проверку</md-button> -->
			</md-tab>

			<md-tab id="tab-pages" md-label="Документы">
				<div class="md-title">Директорат</div>
				<div class="document-container">
					<div class="document md-elevation-3" v-for="document in documentsDirector" :key="document.id">
						<div class="icon icon-pdf"></div>
						<div class="name">{{document.name}}</div>
						<div class="actions-bar">
							<div class="action request">
								<font-awesome-icon icon="eye" title="Запросить" />
							</div>
							<div class="action download">
								<font-awesome-icon icon="download" title="Скачать" />
							</div>
							<div class="action edit">
								<font-awesome-icon icon="pen-square" title="Редактировать" />
							</div>
							<div class="action delete">
								<font-awesome-icon icon="trash" title="Удалить" />
							</div>
						</div>
					</div>
				</div>
				<div class="md-title">Бухгалтерия</div>
				<div class="document-container">
					<div class="document md-elevation-3" v-for="document in documentsAccountant" :key="document.id">
						<div class="icon icon-pdf"></div>
						<div class="name">{{document.name}}</div>
						<div class="actions-bar">
							<div class="action request">
								<font-awesome-icon icon="eye" title="Запросить" />
							</div>
							<div class="action download">
								<font-awesome-icon icon="download" title="Скачать" />
							</div>
							<div class="action edit">
								<font-awesome-icon icon="pen-square" title="Редактировать" />
							</div>
							<div class="action delete">
								<font-awesome-icon icon="trash" title="Удалить" />
							</div>
						</div>
					</div>
				</div>
				<div class="md-title">Остальные сотрудники</div>
				<div class="document-container">
					<div class="document md-elevation-3" v-for="document in documentsRegular" :key="document.id">
						<div class="icon icon-pdf"></div>
						<div class="name">{{document.name}}</div>
						<div class="actions-bar">
							<div class="action request">
								<font-awesome-icon icon="eye" title="Запросить" />
							</div>
							<div class="action download">
								<font-awesome-icon icon="download" title="Скачать" />
							</div>
							<div class="action edit">
								<font-awesome-icon icon="pen-square" title="Редактировать" />
							</div>
							<div class="action delete">
								<font-awesome-icon icon="trash" title="Удалить" />
							</div>
						</div>
					</div>
				</div>
				<div class="md-title">Общедоступные документы</div>
				<div class="document-container">
					<div class="document md-elevation-3" v-for="document in documentsRegularUnemployed" :key="document.id">
						<div class="icon icon-pdf"></div>
						<div class="name">{{document.name}}</div>
						<div class="actions-bar">
							<div class="action request">
								<font-awesome-icon icon="eye" title="Запросить" />
							</div>
							<div class="action download">
								<font-awesome-icon icon="download" title="Скачать" />
							</div>
							<div class="action edit">
								<font-awesome-icon icon="pen-square" title="Редактировать" />
							</div>
							<div class="action delete">
								<font-awesome-icon icon="trash" title="Удалить" />
							</div>
						</div>
					</div>
				</div>
			</md-tab>

			<md-tab id="tab-posts" md-label="Сотрудники">
				<div class="md-title">Директорат</div>
				<div class="person-container">
					<div class="person" v-for="employee in employeesDirector" :key="employee.id">
						<md-avatar class="md-primary">
							<img src="https://melmagazine.com/wp-content/uploads/2021/01/66f-1.jpg" alt="People">
						</md-avatar>
						<span class="">{{employee.name}} {{employee.surname}}</span>
					</div>
				</div>
				<div class="md-title">Бухгалтерия</div>
				<div class="person-container">
					<div class="person" v-for="employee in employeesAccountant" :key="employee.id">
						<md-avatar class="md-primary">
							<img src="https://melmagazine.com/wp-content/uploads/2021/01/66f-1.jpg" alt="People">
						</md-avatar>
						<span class="">{{employee.name}} {{employee.surname}}</span>
					</div>
				</div>
				<div class="md-title">Остальные сотрудники</div>
				<div class="person-container">
					<div class="person" v-for="employee in employeesRegular" :key="employee.id">
						<md-avatar class="md-primary">
							<img src="https://melmagazine.com/wp-content/uploads/2021/01/66f-1.jpg" alt="People">
						</md-avatar>
						<span class="">{{employee.name}} {{employee.surname}}</span>
					</div>
				</div>
			</md-tab>
		</md-tabs>
</template>

<script>
export default {
	name: 'HelloWorld',
	props: {
		msg: String
	},
	data() {
		return {
			companyName: '',
			description: '',
			status: '',

			employees: [],
			documents: [],

			is_editing: false
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
		},
		documentsDirector: function(){
			return this.documents.filter(function (document) {
				return document.role_read == "DIRECTOR";
			})
		},
		documentsAccountant: function(){
			return this.documents.filter(function (document) {
				return document.role_read == "ACCOUNTANT";
			})
		},
		documentsRegular: function(){
			return this.documents.filter(function (document) {
				return document.role_read == "REGULAR";
			})
		},
		documentsRegularUnemployed: function(){
			return this.documents.filter(function (document) {
				return document.role_read == "REGULAR_UNEMPLOYED";
			})
		}
	},
	mounted: function() {
		console.log(localStorage);
		console.log(this.$route.query.id);
		if(this.$route.query.id != undefined){
			this.getCompany();
			this.getEmployees();
			this.getDocuments();
		}
	},
	methods: {
		getCompany(){
			// test 
			if(localStorage.token == undefined) return;

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
						// console.log("catch error 404");
						return;
					}
					
					if(status == 200){
						let data = response.data;
						this.companyName = data.name;
						this.description = data.description;

						if(data.status == "DRAFT") this.status = "В процессе заполнения данных";
						else if(data.status == "READY_TO_VERIFY") this.status = "На модерации";
						else if(data.status == "APPROVED") this.status = "Проверенный";
						else if(data.status == "NOT_APPROVED") this.status = "Не соответствующий требованиям";
						else if(data.status == "PUBLISHED") this.status = "Проверенный";
						
						// console.log(data);
					}
				});
		},
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
		getDocuments(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://localhost:8082/document/byCompany?id='+this.$route.query.id,
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
						this.documents = data;
						console.log(data);
					}
				});
		},

		editInfo(){
			this.beginEditing();
		},
		saveInfo(){
			var query = this.$http(
			{
				method: 'put',
				url: 'http://localhost:8082/company?token='+localStorage.token,
				data: {
					"id": localStorage.company_id,
					"name": this.$refs.changeCompanyNameInput.$el.value,
					"description": this.$refs.changeDescriptionInput.$el.value,

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
					let data = response.data;
					this.companyName = data.name;
					this.description = data.description;
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

			this.endEditing();
		},
		beginEditing(){
			this.$refs.changeCompanyName.$el.style.display = "block";
			this.$refs.changeDescription.$el.style.display = "block";
			this.$refs.nowCompanyName.style.display = "none";
			this.$refs.nowDescription.style.display = "none";

			this.$refs.changeInfo.$el.disabled = true;
			this.$refs.saveInfo.$el.disabled = false;
			this.$refs.cancelInfo.$el.disabled = false;
		},
		endEditing(){
			this.$refs.changeCompanyName.$el.style.display = "none";
			this.$refs.changeDescription.$el.style.display = "none";
			this.$refs.nowCompanyName.style.display = "block";
			this.$refs.nowDescription.style.display = "block";

			this.$refs.changeInfo.$el.disabled = false;
			this.$refs.saveInfo.$el.disabled = true;
			this.$refs.cancelInfo.$el.disabled = true;
		}
	}
}
</script>

<style lang="scss" scoped>

#changeCompanyName,
#changeDescription{
	display: none;
}

.md-textarea{
	width: 500px;
	resize: none !important;
}

.action > *{
	margin: 4px;
	width: 70%;
	height: 70%;
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
/*.md-field {
	max-width: 300px;
	height: 500px;
}*/

.md-title{
	padding: 25px 0;
}
.logo img{
	margin-top: 25px;
	height: 70px;
}
.md-body-1{
	/*margin-top: 25px;*/
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

.document-container{
	display: grid;
	grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
	grid-gap: 2vw;
}
.document{
	/*display: inline-block;*/
	width: 150px;
	height: 150px;
	/*border: 1px solid #333;*/
}
.icon{
	height: 70px;
}
.icon-pdf{
	background-image: url("https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/PDF_file_icon.svg/1200px-PDF_file_icon.svg.png");
	background-size: auto 100%;
	background-repeat: no-repeat;
	background-position: center;
}
.name{
	margin: 5px 0;
	height: 40px;

	text-align: center;
}
.actions-bar{
	height: 30px;
}
.action{
	display: inline-block;
	height: 100%;
	width: 25%;
	border: 1px solid #333;
	box-sizing: border-box;
	cursor: pointer;
}
.person-container{
/*	display: flex;
	flex-direction: row;
	justify-content: flex-start;
	flex-shrink: 0;*/
	/*max-width: 100px;*/
	/*border: 1px solid red;*/

	display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-gap: 2vw;
}
.person{
	margin-right: 20px;
	/*display: inline-block;;*/
}
.person > span{
	margin-left: 10px;
}
</style>