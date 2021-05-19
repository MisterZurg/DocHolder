<template>
	<div>
		<md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card>
			<md-table-toolbar>
				<div class="md-toolbar-section-start">
					<h1 class="md-title">Companies</h1>
				</div>
				<md-field md-clearable class="md-toolbar-section-end">
					<md-input v-on:keyup.enter="updateTable()" placeholder="Search company by name..." v-model="search"></md-input>
				</md-field>
				<md-button class="md-toolbar-section-end search-button" @click="updateTable()">
						Search
				</md-button>
			</md-table-toolbar>

			<md-table-row slot="md-table-row" slot-scope="{ item }"
						@click="goToCompany(item.company_id);">
				<!-- <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell> -->
				<md-table-cell md-label="Logo" class="logo" >
					<img :src="item.logo">
				</md-table-cell>
				<md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
				<md-table-cell md-label="Description" md-sort-by="description">{{ item.description }}</md-table-cell>
				<md-table-cell md-label="Status" md-sort-by="status">{{ item.status }}</md-table-cell>
			</md-table-row>

			<md-table-empty-state
				md-label="No company found for this search query"
				:md-description="`Try a different search term or create a new company.`">
			</md-table-empty-state>

		</md-table>
		<div class="v-table__pagination">
			<div class="page"
				v-for="page in pageColumn"
				:key="page"
				:class="{'page_selected': page === pageNumber}"
				@click="pageClick(page)"
			>{{ page }}
			</div>

			<md-button
				class="md-icon-button"
				:disabled="pageNumber === 1"
				@click="prevPage">
				<md-icon>keyboard_arrow_left</md-icon>
			</md-button>

			<md-button
				class="md-icon-button"
				:disabled="pageNumber >= pageColumn"
				@click="nextPage">
				<md-icon>keyboard_arrow_right</md-icon>
			</md-button>
		</div>
	</div>
</template>

<script>
export default {
	name: 'TableSearch',
	data: () => ({
		search: '',
		searched: [],
		companiesPerPage: 4,
		pageNumber: 1,
		pageColumn: 1,
		companies_data: []
	}),
	created: function () {
		document.title = "Home Page";
	},
	mounted: function () {
		this.updateTable();
	},
	methods: {
		updateTable(){
			this.pageNumber = 1;
			this.pageColumn = 1;
			this.getCompanies();
		},

		goToCompany(id){
			this.$router.push("company?id="+id);
		},
		getCompanies() {
			let data = [];
			var query = this.$http(
				{
					method: 'get',
					url: 'http://localhost:8082/company?limit=' + this.companiesPerPage + '&page=' + (this.pageNumber - 1) + '&name=' + this.search,
					headers: {
						"Content-type": "application/json; charset=UTF-8"
					}
				})
				.then(function(response) {return response;})
				.catch(function(error) {return error.response;});

			query.then((response) => {
				let status = response.status;
				if(status != 200){
					this.searched = [];
					this.pageColumn = 1;
					return;
				}
				
				let content = response.data.content;
				console.log(content);
				for (let i = 0; i < content.length; i++) {
					if(content[i].logo == null){
							content[i].logo = "https://github.com/MisterZurg/DocHolder/blob/main/frontend/src/icons/nologo.png?raw=true";
					}else{
						content[i].logo = "data:image/jpeg;base64," + content[i].logo;
					}

					data.push({
						id: i,
						company_id: content[i].id,
						name: content[i].name,
						description: content[i].description,
						logo: content[i].logo,
						status: content[i].status
					});
				}

				let maxpages = Math.abs(parseInt(response.headers.range.match(/-\d+/)));
				this.pageColumn = maxpages;
				this.searched = data;
			});
			return null;
		},
		pageClick(page) {
			this.pageNumber = page;
			this.getCompanies();
		},
		nextPage() {
			this.pageNumber++;
			this.getCompanies();
		},
		prevPage() {
			this.pageNumber--;
			this.getCompanies();
		},
	},
}
</script>

<style lang="scss" scoped>
.md-table{
	min-height: 550px;
}
.md-table-row {
	height: 100px !important;
	cursor: pointer;
}
.logo img{
	display: block;
	/*margin: 0 auto;*/
	max-height: 80px;
	max-width: 200px;
}
.md-field {
	max-width: 300px;
}
.v-table__pagination {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	margin-top: 30px;
	margin-right: 5px;
}
.page {
	padding: 8px;
	cursor: pointer;
}
.page:hover {
	background: #42b983;
}
.page_selected {
	background: #2c3e50;
}

.search-button{
	max-width: 100px !important;
}
/*md-content md-table-content md-scrollbar*/
.md-table .md-content{
	/*border: 10px solid red !important;*/
	/*max-height: none !important;*/
}
.md-empty-state{
	/*display: none;*/
	transition: none !important;
}
</style>