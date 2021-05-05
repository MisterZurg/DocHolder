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
			</md-tab>

			<md-tab id="tab-pages" md-label="Документы">
				<md-button class="md-primary md-raised button-edit" ref="newDocumentButton" @click="showUploadDocument = true">Upload new document</md-button>
				<div class="md-title">Директорат</div>
				<div class="document-container">
					<div class="document md-elevation-3" v-for="document in documentsDirector" :key="document.id">
						<div class="icon icon-pdf">
							<img :src="document.img" alt="">
						</div>
						<div class="name">{{document.name}}</div>
						<div class="actions-bar">
							<div class="action request">
								<font-awesome-icon icon="at" title="Request"
									@click="requestDocument(document.id, document.role_read)" />
							</div>
							<div class="action edit">
								<font-awesome-icon icon="eye" title="Preview (pdf only)"
									@click="readDocument(document.id, document.filename)" />
							</div>
							<div class="action download">
								<font-awesome-icon icon="download" title="Download" 
									@click="downloadDocument(document.id, document.filename)" />
							</div>
							<div class="action delete">
								<font-awesome-icon icon="trash" title="Delete"
									@click="deleteDocument(document.id)" />
							</div>
						</div>
					</div>
				</div>
				<div class="md-title">Бухгалтерия</div>
				<div class="document-container">
					<div class="document md-elevation-3" v-for="document in documentsAccountant" :key="document.id">
						<div class="icon icon-pdf">
							<img :src="document.img" alt="">
						</div>
						<div class="name">{{document.name}}</div>
						<div class="actions-bar">
							<div class="action request">
								<font-awesome-icon icon="at" title="Request"
									@click="requestDocument(document.id, document.role_read)" />
							</div>
							<div class="action edit">
								<font-awesome-icon icon="eye" title="Preview (pdf only)"
									@click="readDocument(document.id, document.filename)" />
							</div>
							<div class="action download">
								<font-awesome-icon icon="download" title="Download" 
									@click="downloadDocument(document.id, document.filename)" />
							</div>
							<div class="action delete">
								<font-awesome-icon icon="trash" title="Delete"
									@click="deleteDocument(document.id)" />
							</div>
						</div>
					</div>
				</div>
				<div class="md-title">Остальные сотрудники</div>
				<div class="document-container">
					<div class="document md-elevation-3" v-for="document in documentsRegular" :key="document.id">
						<div class="icon icon-pdf">
							<img :src="document.img" alt="">
						</div>
						<div class="name">{{document.name}}</div>
						<div class="actions-bar">
							<div class="action request">
								<font-awesome-icon icon="at" title="Request"
									@click="requestDocument(document.id, document.role_read)" />
							</div>
							<div class="action edit">
								<font-awesome-icon icon="eye" title="Preview (pdf only)"
									@click="readDocument(document.id, document.filename)" />
							</div>
							<div class="action download">
								<font-awesome-icon icon="download" title="Download" 
									@click="downloadDocument(document.id, document.filename)" />
							</div>
							<div class="action delete">
								<font-awesome-icon icon="trash" title="Delete"
									@click="deleteDocument(document.id)" />
							</div>
						</div>
					</div>
				</div>
				<div class="md-title">Общедоступные документы</div>
				<div class="document-container">
					<div class="document md-elevation-3" v-for="document in documentsRegularUnemployed" :key="document.id">
						<div class="icon icon-pdf">
							<img :src="document.img" alt="">
						</div>
						<div class="name">{{document.name}}</div>
						<div class="actions-bar">
							<div class="action request">
								<font-awesome-icon icon="at" title="Request"
									@click="requestDocument(document.id, document.role_read)" />
							</div>
							<div class="action edit">
								<font-awesome-icon icon="eye" title="Preview (pdf only)"
									@click="readDocument(document.id, document.filename)" />
							</div>
							<div class="action download">
								<font-awesome-icon icon="download" title="Download" 
									@click="downloadDocument(document.id, document.filename)" />
							</div>
							<div class="action delete">
								<font-awesome-icon icon="trash" title="Delete"
									@click="deleteDocument(document.id)" />
							</div>
						</div>
					</div>
				</div>

				<md-dialog :md-active.sync="showUploadDocument">
					<md-dialog-title>Upload document</md-dialog-title>
					<md-dialog-content>
						<md-field>
							<label>Document</label>
							<md-file v-model="newDocumentFile" @md-change="setDocumentBinary($event)" />
						</md-field>
						<md-field>
							<label>Document name</label>
							<md-input ref="newDocumentName"></md-input>
							<!-- <span class="md-helper-text">Document Name</span> -->
						</md-field>
						<md-checkbox v-model="newDocumentPublic">Public</md-checkbox>
					</md-dialog-content>
					<md-dialog-actions>
						<md-button class="md-primary" @click="closeUploadDocument();">Close</md-button>
						<md-button class="md-primary" @click="uploadDocument();">Upload</md-button>
					</md-dialog-actions>
				</md-dialog>
				<md-dialog :md-active.sync="showReadDocument">
					<md-dialog-title>Reading document</md-dialog-title>
					<md-dialog-content>
						<iframe frameborder="0" :src="urlPdf" class="document-reader"></iframe>
					</md-dialog-content>
					<md-dialog-actions>
						<md-button class="md-primary" @click="showReadDocument=false">Close</md-button>
					</md-dialog-actions>
				</md-dialog>

			</md-tab>

			<md-tab id="tab-posts" md-label="Сотрудники">
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
			</md-tab>
		</md-tabs>
</template>

<script>

export default {
	name: 'HelloWorld',
	data() {
		return {
			companyName: '',
			description: '',
			status: '',
			adminMessage: '',

			employees: [],
			documents: [],

			is_editing: false,

			showReadDocument: false,
			showUploadDocument: false,
			newDocumentPublic: false,
			newDocumentFile: null,
			documentBinary: null,
			urlPdf: null
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
			this.showEditButtons();
			this.getEmployees();
			this.getDocuments();
			this.showUploadButton();
			this.showAdminButtons();
		}
		// let text = JSON.stringify({hello:'example'});
		// this.downloadAsFile(text);
	},
	methods: {
		downloadAsFile(data, filename) {
			let a = document.createElement("a");
			let file = new Blob([data]);
			// console.log(data);

			a.href = URL.createObjectURL(file);
			a.download = filename;
			a.click();
		},
		downloadDocument(id, filename){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://localhost:8082/document/download?id='+id+'&token='+localStorage.token,
				responseType: 'blob'
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 403){
					alert("You haven't permission");
					return;
				}
					
				if(status == 200){
					let data = response.data;
					// console.log(response);
					// console.log(response.data);
					this.downloadAsFile(data, filename);
				}
			});
		},
		setDocumentBinary(evt) {
			this.documentBinary = evt[0];
		},
		showUploadButton(){
			if(localStorage.company_id == this.$route.query.id){
				this.$refs.newDocumentButton.$el.style.display = "inline-block";
			}
		},
		uploadDocument(){
			let name = this.$refs.newDocumentName.$el.value;
			if(this.documentBinary == null || name == ""){
				// console.log(this.documentBinary);
				// console.log(name);
				alert("The inputs mustn't be empty");
				return;
			}
			if(localStorage.token == undefined){
				alert("Don't try hack us");
				return;
			}

			let roleRead = localStorage.role;
			if(this.newDocumentPublic){
				roleRead = "REGULAR_UNEMPLOYED";
			}

			let formData = new FormData();
			formData.append('file', this.documentBinary);
			formData.append('token', localStorage.token);
			var documentDto = {
				"name": name,
				"company_id": localStorage.company_id,
				"user_id": localStorage.id,
				"role_modify": localStorage.role,
				"role_read": roleRead
			};
			documentDto = new Blob([JSON.stringify(documentDto)], {
				type: 'application/json'
			});
			formData.append('documentDto', documentDto);
			var query = this.$http.post( 'http://localhost:8082/document/upload',
				formData,
				{
					headers: {
						'Content-Type': 'multipart/form-data'
					}
				}
			).then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 403){
					alert("You haven't permission");
					return;
				}
				
				if(status == 200){
					// let data = response.data;
					
					this.closeUploadDocument();
					this.getDocuments();
					alert("Document was successfully uploaded");
				}
			});
		},
		closeUploadDocument(){
			this.showUploadDocument = false;
			this.newDocumentName = null;
			this.newDocumentFile = null;
			this.documentBinary = null;
		},
		readDocument(id, filename){
			if(filename.split('.').pop() != "pdf"){
				alert("Preview avaliable only for PDF files");
				return;
			}

			var query = this.$http(
			{
				method: 'get',
				url: 'http://localhost:8082/document/download?id='+id+'&token='+localStorage.token,
				responseType: 'blob'
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 403){
					alert("You haven't permission");
					return;
				}
					
				if(status == 200){
					let data = response.data;
					// console.log(response);
					// console.log(data);

					
					
					// var blob = new Blob([data], { type: 'application/pdf' });
					// var fileURL = URL.createObjectURL(blob);
					// var newWin = window.open(fileURL);
					// newWin.focus();

					this.showReadDocument = true;
					var blob = new Blob([data], { type: 'application/pdf' });
					this.urlPdf = URL.createObjectURL(blob);
				}
			});
		},
		requestDocument(id, documentRole){
			console.log(id);
			if(localStorage.role == documentRole ||
				localStorage.role == "DIRECTOR" ||
				localStorage.role == "ADMINISTRATOR")
			{
				alert("You already have permission to this file");
				return;
			}
			alert("This functional is not avaliable now");
		},
		deleteDocument(id){
			if (!confirm("Are you sure you want to delete this document?")) return;

			var query = this.$http(
			{
				method: 'delete',
				url: 'http://localhost:8082/document/delete?id='+id+'&token='+localStorage.token,
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 403){
					alert("You haven't permission");
					return;
				}
					
				if(status == 200){
					// let data = response.data;
					// console.log(response);
					// console.log(data);
					alert("The document was successfully deleted");
					this.getDocuments();
				}
			});
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
						data.forEach(function(item) {
							let extension = item.filename.split('.').pop();
							if(extension == "pdf"){
								item.img = "https://pics.freeicons.io/uploads/icons/png/15519179861536080156-512.png";
							}else if(extension == "doc" || extension == "docx"){
								item.img = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/.docx_icon.svg/1200px-.docx_icon.svg.png";
							}else{
								item.img = "https://img.icons8.com/plasticine/2x/document.png";
							}
						});
						// console.log(data);
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
			if(localStorage.role == "DIRECTOR"){
				this.$refs.changeInfo.$el.style.display = "inline-block";
				this.$refs.saveInfo.$el.style.display = "inline-block";
				this.$refs.cancelInfo.$el.style.display = "inline-block";
			}
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
					// let data = response.data;
					// this.companyName = data.name;
					// this.description = data.description;
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
/*#tab-home,
#tab-pages,
#tab-posts{
	overflow: hidden;
}*/

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
.button-edit{
	display: none;
}
.button-admin{
	display: none;
}

.document-container{
	display: grid;
	/*grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;*/
	grid-template-columns: repeat( auto-fill, minmax(150px, 1fr) );
	grid-gap: 2vw;
}
.document{
	/*display: inline-block;*/
	width: 150px;
	height: 160px;
	/*border: 1px solid #333;*/
}
.icon{
	margin-top: 10px;
	height: 70px;
}
.icon img{
	display: block;
	margin: 0 auto;
	max-height: 100%;
	max-width: 100%;
}
/*.icon-pdf{
	background-image: url("https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/PDF_file_icon.svg/1200px-PDF_file_icon.svg.png");
	background-size: auto 100%;
	background-repeat: no-repeat;
	background-position: center;
}*/
.name{
	margin: 5px 0;
	height: 40px;
	overflow: hidden;

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
	transition: background 0.2s ease-in-out;
}
.action:hover{
	background-color: #222;
}
.person-container{
/*	display: flex;
	flex-direction: row;
	justify-content: flex-start;
	flex-shrink: 0;*/
	/*max-width: 100px;*/
	/*border: 1px solid red;*/

	display: grid;
  /*grid-template-columns: 1fr 1fr 1fr 1fr;*/
	grid-template-columns: repeat( auto-fill, minmax(400px, 1fr) );
	grid-gap: 2vw;
}
.person{
	margin-right: 20px;

	white-space: nowrap;
	overflow: hidden;
	/*display: inline-block;;*/
}
.person > span{
	margin-left: 10px;
}

.document-reader{
	height: 60vh;
	width: 70vw;
}
</style>