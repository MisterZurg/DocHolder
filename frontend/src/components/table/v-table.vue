<template>
    <div class="v-table">
        <div class="v-table__header">
            <p>Name</p>
            <p>Description</p>
            <p>Logo</p>
        </div>
        <div class="v-table__body">
            <v-table-row
                v-for="row in companies_data"
                :key="row.id"
                :row_data="row"
            />
        </div>
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

import vTableRow from './v-table-row'
/*
<md-table-pagination
class="table-pagination"
md-limit=10
md-page=1
md-total=30
:class="{'md-table-pagination-next': @click='prevPage'}"
    >
    </md-table-pagination>
*/export default {
    name: "v-table",
    components: {
        vTableRow
    },
    props: {},
    data() {
        return {
            companiesPerPage: 2,
            pageNumber: 1,
            pageColumn: 1,
            companies_data: []
        }
    },
    mounted: function(){
        this.companies_data = this.getCompanies();
        this.pageColumn = this.getPageColumn();
    },
    /*
    computed: {

        pages() {
            return Math.ceil(this.companies_data.length / 2);
        },
        paginatedCompanies() {
            let from = (this.pageNumber - 1) * this.companiesPerPage;
            let to = from + this.companiesPerPage;
            return this.companies_data.slice(from, to);
        }
    },
    */
    methods: {
        getCompanies() {
            let data = [];
            this.$http(
                {
                    method: 'get',
                    url: 'http://localhost:8082/company?limit='+ this.companiesPerPage +'&page='+ (this.pageNumber-1),
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(function(response) {
                    // console.log(response.data.content);
                    let content = response.data.content;
                    for (let i = 0; i < content.length ; i++) {
                        data.push({
                            id: i,
                            name: content[i].name,
                            description: content[i].description,
                            logotype: content[i].logo
                        });
                    }
                    return data;
                })
                .catch(function(error) {
                    console.log("catch error : " + error);
                    return error;
                });

            // THIS NEED ONLY FOR CATCH ERROR RESPONSE
            // query.then((response) => {
            // 	// if error happened
            // 	if(response.status != undefined){
            // 		// console.log(response);
            // 		let status = response.response.status;
            // 		if(status == 404){
            // 			this.errorText = "Компаний не существует";
            // 		}else{
            // 			this.errorText = "Произошла внутренняя ошибка. Попробуйте позже";
            // 		}
            // 		return;
            // 	}
            // });
            return data;
        },
        getPageColumn() {
            var query = this.$http(
                {
                    method: 'get',
                    url: 'http://localhost:8082/company/count',
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(function(response) {
                    let column = response.data;
                    return Math.ceil(column);
                })
                .catch(function(error) {
                    console.log("catch error : " + error);
                    // return error;
                });
            query.then((response) => {
                // if error happened
                if(response.status != undefined){
                    console.log(response);
                    let status = response.response.status;
                    if(status == 404){
                        this.errorText = "Компаний не существует";
                    }else{
                        this.errorText = "Произошла внутренняя ошибка. Попробуйте позже";
                    }
                    return;
                }
                this.pageColumn = Math.ceil(response / this.companiesPerPage);
            });
            return 1;
        },
        pageClick(page) {
            this.pageNumber = page;
            this.companies_data = this.getCompanies();
        },
        /*
        pageClick(page) {
            this.pageNumber = page;
        },*/
        nextPage(){
            this.pageNumber++;
            this.companies_data = this.getCompanies();
        },
        prevPage(){
            this.pageNumber--;
            this.companies_data = this.getCompanies();
        }
    }
}
</script>

<style scoped>
.v-table {
    max-width: 900px;
    margin: 0 auto;
}

.v-table__header {
    display: flex;
    justify-content: space-around;

}

.v-table__header p {
    flex-basis: 33%;
    text-align: left;
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

}

.page:hover {
    background: #42b983;
}

.page_selected {
    background: #2c3e50;
}
</style>