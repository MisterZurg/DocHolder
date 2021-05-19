<template>
	<div>
		<md-button class="md-primary md-raised button-edit" ref="newDocumentButton" @click="showUploadDocument = true">Upload new document</md-button>
		<div class="md-title">Directors</div>
		<div class="document-container">
			<div class="document md-elevation-3" v-for="document in documentsDirector" :key="document.id">
				<div class="icon icon-pdf">
					<img :src="document.img" alt="">
				</div>
				<div class="name">{{document.name}}</div>
				<div class="actions-bar">
					<div class="action request">
						<font-awesome-icon icon="at" title="Request"
						@click="requestDocument(document.id, document.name, document.role_read)" />
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
		<div class="md-title">Accountants</div>
		<div class="document-container">
			<div class="document md-elevation-3" v-for="document in documentsAccountant" :key="document.id">
				<div class="icon icon-pdf">
					<img :src="document.img" alt="">
				</div>
				<div class="name">{{document.name}}</div>
				<div class="actions-bar">
					<div class="action request">
						<font-awesome-icon icon="at" title="Request"
						@click="requestDocument(document.id, document.name, document.role_read)" />
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
		<div class="md-title">Other employees</div>
		<div class="document-container">
			<div class="document md-elevation-3" v-for="document in documentsRegular" :key="document.id">
				<div class="icon icon-pdf">
					<img :src="document.img" alt="">
				</div>
				<div class="name">{{document.name}}</div>
				<div class="actions-bar">
					<div class="action request">
						<font-awesome-icon icon="at" title="Request"
						@click="requestDocument(document.id, document.name, document.role_read)" />
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
		<div class="md-title">Public documents</div>
		<div class="document-container">
			<div class="document md-elevation-3" v-for="document in documentsRegularUnemployed" :key="document.id">
				<div class="icon icon-pdf">
					<img :src="document.img" alt="">
				</div>
				<div class="name">{{document.name}}</div>
				<div class="actions-bar">
					<div class="action request">
						<font-awesome-icon icon="at" title="Request"
						@click="requestDocument(document.id, document.name, document.role_read)" />
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


		<md-table v-model="requests" md-sort="name" md-sort-order="asc" md-card ref="requestTable">
			<md-table-toolbar>
				<div class="md-toolbar-section-start">
					<h1 class="md-title">Documents request</h1>
				</div>
			</md-table-toolbar>

			<md-table-empty-state
				md-label="No document requests found"
				:md-description="`Maybe later someone send a request`">
			</md-table-empty-state>

			<md-table-row slot="md-table-row" slot-scope="{ item }">
				<md-table-cell md-label="Who" md-sort-by="namename">
					<a :href="'profile?id='+item.userId">{{ item.userFullName }}</a>
				</md-table-cell>
				<md-table-cell md-label="Document" md-sort-by="document">{{ item.documentName }}</md-table-cell>
				<md-table-cell md-label="Message">{{ item.message }}</md-table-cell>
				<md-table-cell md-label="Status">{{ item.status }}</md-table-cell>
				<md-table-cell md-label="Actions">
					<md-button class="md-raised md-primary" :class="'buttons'+item.status" @click="setDocumentRequestStatus(item.id, 'ACCEPTED')">
						Accept
					</md-button>
					<md-button class="md-raised md-accent" :class="'buttons'+item.status" @click="setDocumentRequestStatus(item.id, 'DECLINED')">
						Decline
					</md-button>
				</md-table-cell>
			</md-table-row>
		</md-table>


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
		<md-dialog :md-active.sync="showDocumentRequest">
			<md-dialog-title>Request</md-dialog-title>
			<md-dialog-content>
				<md-field>
					<label>Document name</label>
					<md-input v-model="requestName" disabled></md-input>
				</md-field>
				<md-field>
					<label>Message</label>
					<md-textarea v-model="requestMessage"></md-textarea>
				</md-field>
			</md-dialog-content>
			<md-dialog-actions>
				<md-button class="md-primary" @click="closeRequestBox();">Close</md-button>
				<md-button class="md-primary" @click="sendRequestBox();">Send</md-button>
			</md-dialog-actions>
		</md-dialog>
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
			documents: [],
			showReadDocument: false,
			showUploadDocument: false,
			newDocumentPublic: false,
			newDocumentFile: null,
			documentBinary: null,
			urlPdf: null,

			showDocumentRequest: false,
			requestId: null,
			requestName: null,
			requestMessage: null,
			requests: [],
		}
	},
	computed: {
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
		if(this.$route.query.id != undefined){
			this.getDocuments();
			this.showUploadButtonAndTable();
			this.getRequest();
		}
	},
	methods:{
		getDocuments(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://localhost:8082/document/company?company_id='+this.$route.query.id,
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
				url: 'http://localhost:8082/document/'+id+'/download?token='+localStorage.token,
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
		showUploadButtonAndTable(){
			if(localStorage.company_id == this.$route.query.id){
				this.$refs.newDocumentButton.$el.style.display = "inline-block";
				this.$refs.requestTable.$el.style.display = "block";
			}
		},
		uploadDocument(){
			let name = this.$refs.newDocumentName.$el.value;
			if(this.documentBinary == null || name == ""){
				alert("The inputs mustn't be empty");
				return;
			}
			if(localStorage.token == undefined){
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

				if(status == 500){
					alert("The maximum file size of 20 MB has probably been exceeded");
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
				url: 'http://localhost:8082/document/'+id+'/download?token='+localStorage.token,
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

		requestDocument(id, name, documentRole){
			// if (!confirm("Are you sure you want to request this document?")) return;

			if (localStorage.token == undefined) {
				alert("You should authorized before");
				return;
			}
			
			if(this.$route.query.id == localStorage.company_id &&
				(
					localStorage.role == documentRole ||
					localStorage.role == "DIRECTOR" ||
					localStorage.role == "ADMINISTRATOR"
				) ||
				documentRole == "REGULAR_UNEMPLOYED")
			{
				alert("You already have permission to this file");
				return;
			}

			this.showDocumentRequest = true;

			this.requestName = name;
			this.requestId = id;
		},
		sendRequestBox(){
			var query = this.$http(
			{
				method: 'post',
				url: 'http://localhost:8082/document/request?token='+localStorage.token,
				data: {
					"userId": localStorage.id,
					"companyId": this.$route.query.id,
					"documentId": this.requestId,
					"message": this.requestMessage
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
					alert("This document can't be requested. It may be error. Please contact with administrator");
					return;
				}else if(status == 200){
					alert("The request was send. All your requests you can see at your profile");
					
					if(localStorage.company_id == this.$route.query.id){
						this.getRequest();
					}
				}else{
					console.log("catch unresolve error:"+status);
				}
			});
			
			this.closeRequestBox();
		},
		closeRequestBox(){
			this.requestId = null;
			this.requestName = null;
			this.requestMessage = null;
			this.showDocumentRequest = false;
		},

		getRequest(){
			var query = this.$http(
			{
				method: 'get',
				url: 'http://localhost:8082/document/requests?company_id='+this.$route.query.id,
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
				}
			});
		},
		setDocumentRequestStatus(id, status){

			if (status == "ACCEPTED"){
				if (!confirm("Are you sure you want to accept this request?")) return;
			}else if (status == "DECLINED"){
				if (!confirm("Are you sure you want to decline this request?")) return;
			}

			var query = this.$http(
			{
				method: 'put',
				url: 'http://localhost:8082/document/request/status?id='+id+'&status='+status+'&token='+localStorage.token,
				headers: {
					"Content-type": "application/json; charset=UTF-8"
				}
			})
			.then(function(response) {return response;})
			.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;

				if(status == 304){
					alert("This request can't be modified. It may be error. Please contact with administrator");
					return;
				}else if(status == 403){
					alert("You haven't permission for this action");
					return;
				}else if(status == 200){
					alert("Success");
					this.getRequest();
					console.log(response.data);
				}else{
					console.log("catch unresolve error:"+status);
				}

			});
		},


		deleteDocument(id){
			if (!confirm("Are you sure you want to delete this document?")) return;

			var query = this.$http(
			{
				method: 'delete',
				url: 'http://localhost:8082/document/'+id+'/delete?token='+localStorage.token,
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
	}
}

</script>

<style scoped>

.md-title{
	padding: 25px 0;
}

.action > *{
	margin: 4px;
	width: 70%;
	height: 70%;
}

.document-container{
	display: grid;
	grid-template-columns: repeat( auto-fill, minmax(150px, 1fr) );
	grid-gap: 2vw;
}
.document{
	width: 150px;
	height: 160px;
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

.md-table{
	display: none;
	margin-top: 50px;
}
.md-toolbar-section-start h1{
	text-align: center;
}

.document-reader{
	height: 60vh;
	width: 70vw;
}

.buttonsACCEPTED,
.buttonsDECLINED{
	visibility: hidden;
}
.button-edit{
	display: none;
	/*visibility: hidden;*/
}


</style>
